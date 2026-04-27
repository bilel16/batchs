package com.bna.habil.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "TYPE_CLIENT")
public class TypeClient implements Serializable {
    @Serial
    private static final long serialVersionUID = -7079816131983069573L;
    @Id
    @Column(name = "COD_TYPE_CLIENT", nullable = false)
    private Long id;

    @Column(name = "LIB_TYPE_CLIENT", length = 50)
    private String libTypeClient;

}
