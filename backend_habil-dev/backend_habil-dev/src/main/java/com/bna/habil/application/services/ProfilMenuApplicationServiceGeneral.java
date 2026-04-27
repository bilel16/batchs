package com.bna.habil.application.services;


import java.util.List;

import com.bna.habil.domain.entities.ProfilMenuApplication;


public interface ProfilMenuApplicationServiceGeneral {
    List<ProfilMenuApplication> findByProfilMenuApplicationIdCodAppApp(String codAppApp);

    List<ProfilMenuApplication> getMenus(Boolean boolEtatPma, String codAppApp, String codPflPfl, String codTstrcTstrc);


}
