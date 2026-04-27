package com.bna.habil.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "PERSONNE_SAGA", indexes = {
        @Index(name = "CLIENT_TYPE_FK", columnList = "COD_TYPE_CLIENT")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PersonneSaga implements Serializable {
    @Serial
    private static final long serialVersionUID = -1366776507097061357L;
    @Id
    @Column(name = "NUM_SEQ_PERS", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "COD_TYPE_CLIENT")
    private TypeClient typeClient;

}