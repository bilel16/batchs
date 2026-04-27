package com.bna.habil.application.services;


import java.util.List;

import com.bna.habil.application.dto.RoleUpdateDTO;
import com.bna.habil.application.dto.UserRoleDTO;
import com.bna.habil.domain.beans.ProfilMenuApplicationBean;
import com.bna.habil.domain.entities.entitiesId.ProfilMenuApplicationId;
import com.bna.habil.application.services.crud.CrudService;


public interface ProfilMenuApplicationService extends CrudService<ProfilMenuApplicationBean, ProfilMenuApplicationId> {


    List<ProfilMenuApplicationBean> getProfApplicationListBycodAppApp(String codAppApp);

    List<UserRoleDTO> getUserRolesForApplication(String numMatrUser, String codAppApp);

    void saveUserRoles(String numMatrUser, String codAppApp, List<RoleUpdateDTO> updates);

}
