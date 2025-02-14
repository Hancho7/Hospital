package com.gigasma.hospital.repository;

import com.gigasma.hospital.models.PatientDoctorRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientDoctorRelationshipRepository extends JpaRepository<PatientDoctorRelationship, Long> {
    List<PatientDoctorRelationship> findByDoctorId(Long doctorId);
    List<PatientDoctorRelationship> findByPatientId(Long patientId);
}
