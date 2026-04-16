package com.bna.batches.infrastructure.security.model;

import com.bna.batches.domain.entities.extra.MenuData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuResponse {
    private int returnCode;
    private String message;
    private MenuData menuData;
}
