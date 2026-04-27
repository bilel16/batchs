package com.bna.habil.domain.entities.entitiesId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

import org.hibernate.Hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class UtilisateurProfilId implements Serializable {
    @Serial
    private static final long serialVersionUID = -4722984216756695464L;
    @Column(name = "COD_PFL_PFL", nullable = false)
    private String codPflPfl;

    @Column(name = "NUM_MATR_USER", nullable = false)
    private String numMatrUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UtilisateurProfilId entity = (UtilisateurProfilId) o;
        return Objects.equals(this.numMatrUser, entity.numMatrUser) &&
                Objects.equals(this.codPflPfl, entity.codPflPfl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numMatrUser, codPflPfl);
    }

    // getters + setters
    public String getNumMatrUser() {
        return numMatrUser;
    }

    public void setNumMatrUser(String numMatrUser) {
        this.numMatrUser = numMatrUser;
    }

    public String getCodPflPfl() {
        return codPflPfl;
    }

    public void setCodPflPfl(String codPflPfl) {
        this.codPflPfl = codPflPfl;
    }
}
