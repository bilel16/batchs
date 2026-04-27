package com.bna.habil.application.enums;

import com.bna.habil.application.dto.StructureTypeOptionDto;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum StructureTypeEnum {
    AGENCE(1, "AGENCE"),
    DIRECTION_REGIONALE(2, "DIRECTION REGIONALE"),
    DIRECTION_CENTRALE(3, "DIRECTION CENTRALE"),
    DIVISION(4, "DIVISION"),
    DIRECTION(5, "DIRECTION"),
    SUCCURSALE(6, "SUCCURSALE"),
    BOX_DE_CHANGE(7, "Box de Change");

    private final Integer code;
    private final String label;

    StructureTypeEnum(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public static String getLabelByCode(Integer code) {
        if (code == null) return "N/A";
        for (StructureTypeEnum type : values()) {
            if (type.code.equals(code)) {
                return type.label;
            }
        }
        return "Type " + code;
    }

    public static List<StructureTypeOptionDto> getAllOptions() {
        return Arrays.stream(values())
                .map(t -> new StructureTypeOptionDto(t.getCode(), t.getLabel()))
                .sorted(Comparator.comparing(StructureTypeOptionDto::getLabel))
                .collect(Collectors.toList());
    }
}