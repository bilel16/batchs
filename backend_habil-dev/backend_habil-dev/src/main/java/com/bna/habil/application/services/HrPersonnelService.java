package com.bna.habil.application.services;

import com.bna.habil.application.dto.HrPersonnelDto;
import com.bna.habil.application.dto.filters.HrPersonnelFilterDto;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface HrPersonnelService {
     List<HrPersonnelDto> getHrPersonnel(String cin) throws EntityNotFoundException;
    Page<HrPersonnelDto> getHrPersonnelWithFilters(HrPersonnelFilterDto filter, Pageable pageable);
    Page<HrPersonnelDto> getHrPersonnelPageable(HrPersonnelFilterDto filter, Pageable pageable);
    Optional<HrPersonnelDto> findHrPersonnelByCin(String cin);

    String getPosteByMatricule(String matcle);

    boolean verifyPosteByMatricule(String matcle, String codePoste);
}
