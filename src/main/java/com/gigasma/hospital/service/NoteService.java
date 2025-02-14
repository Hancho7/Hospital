package com.gigasma.hospital.service;

import com.gigasma.hospital.dtos.ActionStep;
import com.gigasma.hospital.models.*;
import com.gigasma.hospital.repository.ActionItemRepository;
import com.gigasma.hospital.repository.NoteRepository;
import com.gigasma.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final PatientRepository patientRepository;
    private final ActionItemRepository actionItemRepository;
    private final EncryptionService encryptionService;
    private final LLMService llmService;

    @Transactional
    public Note saveNote(Long patientId, Long doctorId, String content) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = (Doctor) patient.getDoctorRelationships().stream()
                .map(PatientDoctorRelationship::getDoctor)
                .filter(d -> d.getId().equals(doctorId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Doctor not assigned to patient"));

        String encryptedContent = encryptionService.encrypt(content);

        Note note = new Note();
        note.setPatient(patient);
        note.setDoctor(doctor);
        note.setEncryptedContent(encryptedContent);

        return noteRepository.save(note);
    }

    @Transactional
public List<ActionItem> processDoctorNote(Long noteId) {
    Note note = noteRepository.findById(noteId)
            .orElseThrow(() -> new RuntimeException("Note not found"));

    String decryptedContent = encryptionService.decrypt(note.getEncryptedContent());

    List<ActionStep> actionSteps = llmService.processDoctorNote(decryptedContent);

    return actionSteps.stream().map(actionStep -> {
        ActionItem actionItem = new ActionItem();
        actionItem.setNote(note);
        actionItem.setType(ActionType.valueOf(actionStep.getType().toUpperCase()));
        actionItem.setDescription(actionStep.getDescription());
        actionItem.setScheduledFor(LocalDateTime.now().plusDays(1));
        actionItem.setCompleted(false);

        return actionItemRepository.save(actionItem);
    }).toList();
}
}

