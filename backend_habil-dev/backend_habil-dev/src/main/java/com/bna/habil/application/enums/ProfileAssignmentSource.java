package com.bna.habil.application.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Defines the source of a profile assignment.
 * Determines whether the profile is custom or assigned from a pack.
 */
@Getter
@RequiredArgsConstructor
public enum ProfileAssignmentSource {

    /**
     * Profile assigned manually by a manager (custom assignment)
     * boolCustomProfil = 1
     */
    CUSTOM(1, "Custom profile assignment"),

    /**
     * Profile assigned automatically as part of a pack
     * boolCustomProfil = 0
     */
    FROM_PACK(0, "Profile assigned from pack");

    private final int customFlag;
    private final String description;

    /**
     * @return true if this is a custom (manual) assignment
     */
    public boolean isCustom() {
        return this == CUSTOM;
    }

    /**
     * @return true if this assignment comes from a pack
     */
    public boolean isFromPack() {
        return this == FROM_PACK;
    }
}