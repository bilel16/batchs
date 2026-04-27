package com.bna.habil.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonnelFilterDto {
    private String search;           // Search by name, mat, or email
    private Boolean codStatUser;     // Filter by status (active/inactive)
    private List<Integer> codStrcStrcList;     // Filter by structure
    private Integer codTstrTstr;     // Filter by type structure

    // Keep backward compatibility with single value
    public PersonnelFilterDto(String search, Boolean codStatUser, Integer codStrcStrc, Integer codTstrTstr) {
        this.search = search;
        this.codStatUser = codStatUser;
        this.codStrcStrcList = codStrcStrc != null ? List.of(codStrcStrc) : null;
        this.codTstrTstr = codTstrTstr;
    }
}