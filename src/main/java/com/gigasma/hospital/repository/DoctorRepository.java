package com.gigasma.hospital.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gigasma.hospital.models.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    
}
