package com.gigasma.hospital.models;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
public class Doctor extends User {
    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private Set<PatientDoctorRelationship> patientRelationships = new HashSet<>();
}