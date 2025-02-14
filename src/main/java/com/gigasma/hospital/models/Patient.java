package com.gigasma.hospital.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "patients")
@PrimaryKeyJoinColumn(name = "user_id")
public class Patient extends User {
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private Set<PatientDoctorRelationship> doctorRelationships = new HashSet<>();

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Note> notes = new ArrayList<>();
}