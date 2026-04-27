package com.bna.habil.application.services;

import java.util.List;

import com.bna.habil.application.dto.ProfilDto;
import com.bna.habil.application.dto.statistics.ProfileStatsDto;
import com.bna.habil.domain.entities.Profil;
import com.bna.habil.application.services.crud.CrudService;
import com.bna.habil.domain.exceptions.ValidationException;

public interface ProfilService extends CrudService<ProfilDto, String> {

    List<Profil> getProfilByCodApp(String codAppApp) throws ValidationException;


    List<ProfilDto> getAvailableProfilesForUser(String appCode, String targetUserMatricule) throws ValidationException;

    List<ProfilDto> getManagerProfiles(String appCode) throws ValidationException;

    List<ProfilDto> getAvailableProfilesForUserNotAssgined(String appCode, String targetUserMatricule) throws ValidationException;

    List<ProfilDto> getProfilesByStructureId(Integer structureId);

    ProfileStatsDto getProfileStatistics();
}
