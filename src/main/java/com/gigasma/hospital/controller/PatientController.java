package com.gigasma.hospital.controller;


import com.gigasma.hospital.models.Doctor;
import com.gigasma.hospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/{patientId}/select-doctor/{doctorId}")
    public ResponseEntity<String> selectDoctor(@PathVariable Long patientId, @PathVariable Long doctorId) {
        patientService.selectDoctor(patientId, doctorId);
        return ResponseEntity.ok("Doctor selected successfully.");
    }

    @GetMapping("/{patientId}/doctors")
    public ResponseEntity<List<Doctor>> getDoctors(@PathVariable Long patientId) {
        return ResponseEntity.ok(patientService.getDoctorsForPatient(patientId));
    }
}
