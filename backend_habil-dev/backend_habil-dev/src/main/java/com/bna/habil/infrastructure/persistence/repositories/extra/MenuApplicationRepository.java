package com.bna.habil.infrastructure.persistence.repositories.extra;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bna.habil.domain.entities.MenuApplication;
import com.bna.habil.domain.entities.entitiesId.MenuApplicationId;
import org.springframework.data.jpa.repository.Query;


public interface MenuApplicationRepository extends JpaRepository<MenuApplication, MenuApplicationId> {

    List<MenuApplication> findByCodAppApp(String codAppApp);

    MenuApplication findByCodAppAppAndCodMenuMenu(String codAppApp, String codMenuMenu);

    // OPTIMIZED: Single query with JOIN to get everything at once
    @Query("""
            SELECT 
                a.cod_app_app as appCode,
                a.lib_app_app as appLabel,
                COUNT(m.codMenuMenu) as menuCount
            FROM Application a
            LEFT JOIN MenuApplication m ON m.codAppApp = a.cod_app_app
            GROUP BY a.cod_app_app, a.lib_app_app
            ORDER BY COUNT(m.codMenuMenu) DESC
            """)
    List<Object[]> getApplicationMenuStatistics();
}
