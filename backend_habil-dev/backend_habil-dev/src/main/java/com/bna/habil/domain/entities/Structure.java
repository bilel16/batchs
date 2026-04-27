package com.bna.habil.domain.entities;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "STRUCTURE")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Structure implements Serializable {
    @Serial
    private static final long serialVersionUID = -4474779409159867089L;

    @Id
    @Column(name = "cod_strc_strc")
    private Integer id;

    @Column(name = "lib_strc_strc")
    private String libelleStructure;

    @Column(name = "COD_TSTR_TSTR")
    private Integer codeTypeStructure;

    @Column(name = "COD_STRM_STRC")
    private Integer codeStructureMere;

    @Column(name = "LIB_MAIL_STRC")
    private String libMailStrc;

    @Column(name = "COD_CAT_STRC")
    private String codCatStrc;

}
