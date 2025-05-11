package com.themis.pdf_service.dto.client;

import java.time.LocalDateTime;

public class LegalPetitionDto {

    private String content;
    private LocalDateTime dateTime;

    public LegalPetitionDto(String content, LocalDateTime dateTime) {
        this.content = content;
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
