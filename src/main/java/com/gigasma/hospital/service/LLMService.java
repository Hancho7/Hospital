
package com.gigasma.hospital.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigasma.hospital.dtos.ActionStep;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMService {
    private final AzureOpenAiChatModel azureOpenAiChatModel;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON parser

    public List<ActionStep> processDoctorNote(String note) {
        Message systemMessage = new SystemMessage("""
            You are a medical AI assistant.
            Your task is to extract actionable steps from doctor notes and classify them as:
            - CHECKLIST: Tasks that need to be done (e.g., "Check blood pressure daily").
            - PLAN: General treatment or monitoring plans (e.g., "Monitor glucose levels for a week").
            Format the response as a JSON array:
            [{"type": "CHECKLIST", "description": "Check blood pressure daily"},
             {"type": "PLAN", "description": "Monitor glucose levels for a week"}]
        """);

        Message userMessage = new UserMessage(note);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));

        ChatResponse chatResponse = azureOpenAiChatModel.call(prompt);
        log.info("Raw AI Response: {}", chatResponse);

        // Extract text content from the first generation result
        String responseText = chatResponse.getResults().get(0).getOutput().getText();
        log.info("Extracted Text: {}", responseText);

        try {
            // Remove triple backticks and potential "json" tag
            String cleanedJson = responseText.replaceAll("```json", "").replaceAll("```", "").trim();
            log.info("Cleaned JSON: {}", cleanedJson);

            // Convert JSON string to Java List
            return objectMapper.readValue(cleanedJson, new TypeReference<List<ActionStep>>() {});

        } catch (Exception e) {
            log.error("Error parsing AI response JSON: {}", e.getMessage());
            return List.of(); // Return empty list if parsing fails
        }
    }
}
