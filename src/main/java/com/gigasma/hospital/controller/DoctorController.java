package com.gigasma.hospital.controller;

import com.gigasma.hospital.models.Doctor;
import com.gigasma.hospital.models.Patient;
import com.gigasma.hospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/doctor")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

     @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAssignedPatients(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorService.getAssignedPatients(doctorId));
    }
}

