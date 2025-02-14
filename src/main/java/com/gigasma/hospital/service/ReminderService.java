package com.gigasma.hospital.service;

import com.gigasma.hospital.models.ActionItem;
import com.gigasma.hospital.repository.ActionItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReminderService {
    private final ActionItemRepository actionItemRepository;
    private final EmailService emailService;

    @Scheduled(fixedRate = 60000)
    public void sendReminders() {
        log.info("Checking for upcoming reminders...");

        LocalDateTime now = LocalDateTime.now();
        List<ActionItem> pendingReminders = actionItemRepository.findPendingReminders(now);

        for (ActionItem actionItem : pendingReminders) {
            if (actionItem.getNote() == null || actionItem.getNote().getPatient() == null) {
                log.warn("Skipping reminder due to missing note or patient data");
                continue;
            }

            String patientEmail = actionItem.getNote().getPatient().getEmail();
            String patientName = actionItem.getNote().getPatient().getName();

            Map<String, Object> variables = new HashMap<>();
            variables.put("name", patientName);
            variables.put("reminderMessage", actionItem.getDescription());
            variables.put("appointmentDate", actionItem.getScheduledFor());

            emailService.sendEmail(patientEmail, "Reminder: Scheduled Action", "reminder-email", variables);
            log.info("Sent reminder email to {}", patientEmail);
        }
    }
}
