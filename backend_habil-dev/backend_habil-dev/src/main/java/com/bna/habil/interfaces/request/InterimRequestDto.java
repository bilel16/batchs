package com.bna.habil.interfaces.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class InterimRequestDto {

    @NotNull
    private Integer matriculeSource;

    @NotNull
    private Integer matriculeCible;

    @NotNull
    private Date dateDebutInterim;

    @NotNull
    private Date dateFinInterim;

    @NotBlank
    private String codBctDestination;

    @NotBlank
    private String libMenuMenu;
}