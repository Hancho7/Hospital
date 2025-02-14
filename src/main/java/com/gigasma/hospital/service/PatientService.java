package com.gigasma.hospital.service;


import com.gigasma.hospital.models.Doctor;
import com.gigasma.hospital.models.Patient;
import com.gigasma.hospital.models.PatientDoctorRelationship;
import com.gigasma.hospital.repository.DoctorRepository;
import com.gigasma.hospital.repository.PatientDoctorRelationshipRepository;
import com.gigasma.hospital.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientDoctorRelationshipRepository relationshipRepository;

    @Transactional
    public void selectDoctor(Long patientId, Long doctorId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        if (!relationshipRepository.findByPatientId(patientId).isEmpty()) {
            throw new RuntimeException("Patient already has a selected doctor.");
        }

        PatientDoctorRelationship relationship = new PatientDoctorRelationship();
        relationship.setPatient(patient);
        relationship.setDoctor(doctor);

        relationshipRepository.save(relationship);
    }

    public List<Doctor> getDoctorsForPatient(Long patientId) {
        return relationshipRepository.findByPatientId(patientId)
                .stream()
                .map(PatientDoctorRelationship::getDoctor)
                .toList();
    }
}
