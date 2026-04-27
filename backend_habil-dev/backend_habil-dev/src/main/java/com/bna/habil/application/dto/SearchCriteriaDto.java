package com.bna.habil.application.dto;


import com.bna.habil.domain.entities.Structure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SearchCriteriaDto {

    private Structure strcAg;
    private String year;
    private Structure strcDr;


}
