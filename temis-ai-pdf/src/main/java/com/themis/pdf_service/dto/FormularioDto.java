package com.themis.pdf_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FormularioDto {
    
    @Valid
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotNull(message = "La cédula no puede estar vacía")
    private String cedula;
    @NotBlank(message = "La ciudad no puede estar vacía")
    private String ciudad;
    @NotBlank(message = "La entidad demandada no puede estar vacía")
    private String entidadDemandada;
    private String fechaRadicado;
    @NotBlank(message = "Debe incluir los hechos.")
    private String hechos;

    public String getBoldNombre() {
        return formatValues(nombre);
    }
    public String getBoldCedula() {
        return formatValues(cedula);
    }
    public String getBoldCiudad() {
        return formatValues(ciudad);
    }
    public String getBoldEntidadDemandada() {
        return formatValues(entidadDemandada);
    }

    private String formatValues(String value) {
        return "*" + value.trim() + "*";
    }

}
