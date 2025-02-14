package com.gigasma.hospital.controller;

import com.gigasma.hospital.models.ActionItem;
import com.gigasma.hospital.models.Note;
import com.gigasma.hospital.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Map<String, String> request) {
        Long patientId = Long.parseLong(request.get("patientId"));
        Long doctorId = Long.parseLong(request.get("doctorId"));
        String content = request.get("content");

        Note note = noteService.saveNote(patientId, doctorId, content);
        return ResponseEntity.ok(note);
    }

    @PostMapping("/{id}/process")
    public ResponseEntity<List<ActionItem>> processNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.processDoctorNote(id));
    }
}

