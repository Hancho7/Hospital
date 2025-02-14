package com.gigasma.hospital.repository;

import com.gigasma.hospital.models.ActionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {

    @Query("SELECT a FROM ActionItem a WHERE a.completed = false AND a.scheduledFor <= :now")
    List<ActionItem> findPendingReminders(@Param("now") LocalDateTime now);
}
