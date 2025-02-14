package com.gigasma.hospital.service;


import com.gigasma.hospital.models.Doctor;
import com.gigasma.hospital.models.Patient;
import com.gigasma.hospital.models.PatientDoctorRelationship;
import com.gigasma.hospital.repository.DoctorRepository;
import com.gigasma.hospital.repository.PatientDoctorRelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final PatientDoctorRelationshipRepository relationshipRepository;
    private final DoctorRepository doctorRepository;

      public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }


    public List<Patient> getAssignedPatients(Long doctorId) {
        return relationshipRepository.findByDoctorId(doctorId)
                .stream()
                .map(PatientDoctorRelationship::getPatient)
                .toList();
    }
}

