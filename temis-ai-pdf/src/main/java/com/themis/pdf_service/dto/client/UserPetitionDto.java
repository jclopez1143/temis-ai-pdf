package com.themis.pdf_service.dto.client;

import jakarta.validation.constraints.Size;

public class UserPetitionDto {

    @Size(min = 8)
    private String institution;

    @Size(min = 50, max = 3000)
    private String description;

    public UserPetitionDto(String institution, String description) {
        this.institution = institution;
        this.description = description;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
