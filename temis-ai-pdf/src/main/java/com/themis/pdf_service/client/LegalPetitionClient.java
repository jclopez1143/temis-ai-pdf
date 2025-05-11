package com.themis.pdf_service.client;

import com.themis.pdf_service.dto.client.LegalPetitionDto;
import com.themis.pdf_service.dto.client.UserPetitionDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "legalPetitionClient", url = "${client.legal_petition.host}" + "${client.legal_petition.context.v1}")
public interface LegalPetitionClient {

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LegalPetitionDto> generateLegalPetition(@Valid @RequestBody UserPetitionDto userPetitionDto);

}
