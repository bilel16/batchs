package com.bna.habil.application.services;

import com.bna.habil.application.dto.AddApplicationDto;
import com.bna.habil.application.dto.ApplicationDto;
import com.bna.habil.application.services.crud.CrudService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;

public interface ApplicationService extends CrudService<ApplicationDto, String> {

    @Transactional
    ApplicationDto createWithProfiles(AddApplicationDto dto);

    @Transactional
    ApplicationDto updateWithProfiles(String codApp, AddApplicationDto dto);

    void createProfilesForApplication(AddApplicationDto dto);

    @Transactional
    void updateProfilesForApplication(String codApp, AddApplicationDto dto);

    AddApplicationDto getApplicationDetails(String codApp);

    List<ApplicationDto> getAllAuthorizedApplications();
}