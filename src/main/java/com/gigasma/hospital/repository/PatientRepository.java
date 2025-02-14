package com.gigasma.hospital.repository;


import com.gigasma.hospital.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}

