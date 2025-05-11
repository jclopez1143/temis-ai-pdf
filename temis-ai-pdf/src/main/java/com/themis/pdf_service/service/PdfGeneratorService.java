package com.themis.pdf_service.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import com.themis.pdf_service.client.LegalPetitionClient;
import com.themis.pdf_service.dto.client.UserPetitionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.themis.pdf_service.dto.ComponentDTO;
import com.themis.pdf_service.dto.FormularioDto;
import com.themis.pdf_service.enums.ComponentsPDFEnum;
import com.themis.pdf_service.tools.constants.Formulario;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final IPdfGeneratorService pdfGeneratorService;
    private final LegalPetitionClient legalPetitionClient;

    public Mono<byte[]> generatePdf(FormularioDto dto) throws IOException {

        String legalPetitionContent = legalPetitionClient.generateLegalPetition(
                new UserPetitionDto(dto.getEntidadDemandada(), dto.getHechos())).getBody().getContent();

        return Mono.just(pdfGeneratorService.print(createComponents(legalPetitionContent, dto)));
    }

    private LinkedList<ComponentDTO> createComponents(String data, FormularioDto dto) {

        // Create a list of components to be added to the PDF
        // The order of the components is important for the layout
        // The first component will be printed first, and so on.
        LinkedList<ComponentDTO> components = new LinkedList<>();

        // The components must be added in the order you want them to print.
        components.add(ComponentDTO.builder()
                .text(Formulario.TITULO)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.TITLE)
                .build());

        components.add(ComponentDTO.builder()
                .text(Formulario.SALUDO)
                .bold(true)
                .componentEnum(ComponentsPDFEnum.HEADLINE)
                .build());

        components.add(ComponentDTO.builder()
                .text(Formulario.REFERENCIA.replaceAll(
                        "\\[ENTIDAD\\]", dto.getBoldEntidadDemandada()))
                .bold(false)
                .componentEnum(ComponentsPDFEnum.HEADLINE)
                .build());

        String texto = Formulario.PARRAFO
                .replaceAll("\\[NOMBRE COMPLETO\\]", dto.getBoldNombre())
                .replaceAll("\\[CÉDULA\\]", dto.getBoldCedula())
                .replaceAll("\\[CIUDAD\\]", dto.getBoldCiudad())
                .replaceAll("\\[FECHA\\]", LocalDate.now().toString())
                .replaceAll("\\[ENTIDAD\\]", dto.getBoldEntidadDemandada())
                .replaceAll("\\[CONTENIDO\\]", data);

        components.add(ComponentDTO.builder()
                .text(texto)
                .bold(null)
                .componentEnum(ComponentsPDFEnum.PARAGRAPH)
                .build());

        String firma = Formulario.FIRMA
                .replaceAll("\\[NOMBRE COMPLETO\\]", dto.getBoldNombre())
                .replaceAll("\\[CÉDULA\\]", dto.getCedula())
                .replaceAll("\\[CIUDAD\\]", dto.getCiudad())
                .replaceAll("\\[FECHA\\]", LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM yyyy")))
                .toString();


        components.add(ComponentDTO.builder()
                .text(firma)
                .bold(null)
                .componentEnum(ComponentsPDFEnum.SIGN)
                .build());

        return components;
    }
}
