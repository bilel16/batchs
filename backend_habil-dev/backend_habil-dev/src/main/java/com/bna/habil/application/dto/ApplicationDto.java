package com.bna.habil.application.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationDto {

    @NotBlank(message = "Le code application est obligatoire")
    @Size(min = 1, max = 50, message = "Le code application doit contenir entre 1 et 50 caractères")
    private String codApp;

    @NotBlank(message = "Le libellé application est obligatoire")
    @Size(min = 1, max = 100, message = "Le libellé application doit contenir entre 1 et 100 caractères")
    private String libApp;

    @Size(max = 100, message = "Le libellé label ne doit pas dépasser 100 caractères")
    private String libLab;  // Optional field
}
