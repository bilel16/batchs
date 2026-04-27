package com.bna.habil.domain.entities.extra;

import java.util.List;

import com.bna.habil.application.dto.RoleUpdateDTO;

public class RoleUpdateRequest {
    private List<RoleUpdateDTO> roles;

    public List<RoleUpdateDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleUpdateDTO> roles) {
        this.roles = roles;
    }

}
