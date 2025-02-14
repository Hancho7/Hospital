package com.gigasma.hospital.repository;


import com.gigasma.hospital.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
