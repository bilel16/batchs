package com.bna.habil.application.services.impl;


import java.util.ArrayList;
import java.util.List;

import com.bna.habil.application.dto.statistics.ApplicationMenuCountDto;
import com.bna.habil.application.dto.statistics.ApplicationStatsDto;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.entities.MenuApplication;
import com.bna.habil.domain.entities.entitiesId.MenuApplicationId;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.persistence.repositories.extra.MenuApplicationRepository;
import com.bna.habil.application.mappers.MenuApplicationMapper;
import com.bna.habil.application.dto.MenuApplicationDto;
import com.bna.habil.application.services.MenuApplicationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MenuApplicationServiceImpl
        extends AbstractCrudService<MenuApplication, MenuApplicationDto, MenuApplicationId>
        implements MenuApplicationService {


    private static final Logger log = LoggerFactory.getLogger(MenuApplicationServiceImpl.class);

    private final MenuApplicationRepository menuApplicationRepository;

    public MenuApplicationServiceImpl(MenuApplicationRepository menuApplicationRepository,
                                      MenuApplicationMapper mapper) {
        super(
                menuApplicationRepository,
                mapper,
                // ID Extractor
                dto -> new MenuApplicationId(dto.getCodAppApp(), dto.getCodMenuMenu()),
                // ID Stringifier
                id -> String.format("App=%s,Menu=%s", id.getCodAppApp(), id.getCodMenuMenu()),
                // Create Validator
                dto -> {
                    if (dto.getCodAppApp() == null || dto.getCodAppApp().trim().isEmpty()) {
                        throw new ValidationException("Application code cannot be empty");
                    }
                    if (dto.getCodMenuMenu() == null || dto.getCodMenuMenu().trim().isEmpty()) {
                        throw new ValidationException("Menu code cannot be empty");
                    }
                },
                // Update Validator
                (id, dto, existing) -> {
                    log.debug("Validating update for MenuApplication with ID: {}", id);

                    // Check if composite key fields are changing
                    boolean keyWillChange =
                            !existing.getCodAppApp().equals(dto.getCodAppApp()) ||
                                    !existing.getCodMenuMenu().equals(dto.getCodMenuMenu());

                    if (keyWillChange) {
                        log.warn("Attempting to change composite key fields - this may create a new entity");
                        throw new ValidationException(
                                "Cannot change composite key fields (CodAppApp, CodMenuMenu). " +
                                        "Delete and recreate instead."
                        );
                    }
                }
        );
        this.menuApplicationRepository = menuApplicationRepository;
    }


    @Override
    protected String getEntityName() {
        return "MenuApplication";
    }

    @Override
    public List<MenuApplicationDto> getMenuApplicationListBycodAppApp(String codeApp) throws ValidationException {
        log.info("Getting MenuApplication list by codeApp: {}", codeApp);

        if (codeApp == null || codeApp.trim().isEmpty()) {
            throw new ValidationException("Application code cannot be empty");
        }

        List<MenuApplication> menuApplicationList = menuApplicationRepository.findByCodAppApp(codeApp);
        log.info("Found {} MenuApplications for codeApp: {}", menuApplicationList.size(), codeApp);

        return menuApplicationList.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ApplicationStatsDto getApplicationStatistics() {
        log.info("Calculating application and menu statistics");

        // SINGLE QUERY: Get all data with JOIN (applications + menu counts)
        List<Object[]> results = menuApplicationRepository.getApplicationMenuStatistics();

        // Calculate totals while building the list (single pass)
        int totalApplications = results.size();
        int totalMenus = 0;

        List<ApplicationMenuCountDto> perApplication = new ArrayList<>(results.size());

        for (Object[] row : results) {
            String appCode = (String) row[0];
            String appLabel = row[1] != null ? (String) row[1] : "N/A";
            Long menuCount = (Long) row[2];
            int count = menuCount != null ? menuCount.intValue() : 0;

            totalMenus += count;

            perApplication.add(new ApplicationMenuCountDto(appCode, appLabel, count));
        }

        log.info("Statistics calculated: {} applications, {} menus", totalApplications, totalMenus);

        return new ApplicationStatsDto(totalApplications, totalMenus, perApplication);
    }

}
