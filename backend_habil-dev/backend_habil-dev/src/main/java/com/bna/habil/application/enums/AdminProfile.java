package com.bna.habil.application.enums;

import com.bna.habil.application.dto.AddApplicationDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum AdminProfile {
    CENTRALE("Centrale", 3),
    REGIONAL("Regional", 2),
    AGENCE("Agence", 1);

    private static final String ADMIN_PREFIX = "_Admin_";

    private final String suffix;
    private final int level;

    private static final Map<Integer, String> STRUCTURE_TO_PROFILE_SUFFIX = Map.of(
            5, CENTRALE.getProfileCode(""),
            4, CENTRALE.getProfileCode(""),
            3, CENTRALE.getProfileCode(""),
            2, REGIONAL.getProfileCode(""),
            1, AGENCE.getProfileCode("")
    );

    public String getProfileCode(String codApp) {
        return codApp + ADMIN_PREFIX + suffix;
    }

    public boolean isEnabled(AddApplicationDto dto) {
        return switch (this) {
            case CENTRALE -> dto.isCentral();
            case REGIONAL -> dto.isRegional();
            case AGENCE -> dto.isAgence();
        };
    }

    public static String getProfileSuffixForStructure(int codeTypeStructure) {
        return STRUCTURE_TO_PROFILE_SUFFIX.get(codeTypeStructure);
    }
}