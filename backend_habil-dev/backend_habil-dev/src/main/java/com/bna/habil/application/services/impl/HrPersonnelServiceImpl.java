package com.bna.habil.application.services.impl;



import com.bna.habil.application.dto.HrPersonnelDto;
import com.bna.habil.application.dto.filters.HrPersonnelFilterDto;
import com.bna.habil.application.services.HrPersonnelService;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.infrastructure.persistence.repositories.extra.HrPersonnelRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HrPersonnelServiceImpl implements HrPersonnelService {
    private static final Logger log = LoggerFactory.getLogger(HrPersonnelServiceImpl.class);

    private final HrPersonnelRepository repository;

    /**
     * Get HR Personnel with pagination and search
     * Uses the general search approach (one search field for all)
     *
     * @param filter   Filter criteria (search, cin, matcle)
     * @param pageable Pagination parameters
     * @return Page of HrPersonnelDto
     */
    public Page<HrPersonnelDto> getHrPersonnelPageable(HrPersonnelFilterDto filter, Pageable pageable) {
        log.info("Starting paginated request to HR-Space! Page: {}, Size: {}, Filter: {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                filter);

        try {
            // Normalize empty strings to null for proper SQL handling
            String search = normalizeParam(filter.getSearch());
            String cin = normalizeParam(filter.getCin());
            String matcle = normalizeParam(filter.getMatcle());

            Page<HrPersonnelDto> result = repository.findHrPersonnelWithSearch(
                    search,
                    cin,
                    matcle,
                    pageable
            );

            log.info("Found {} records (Total: {}, Total Pages: {})",
                    result.getNumberOfElements(),
                    result.getTotalElements(),
                    result.getTotalPages());

            return result;

        } catch (Exception e) {
            log.error("Error fetching HR personnel: {}", e.getMessage(), e);
            throw new EntityNotFoundException("Failed to fetch HR personnel: " + e.getMessage());
        } finally {
            log.info("Paginated request completed!");
        }
    }

    /**
     * Get HR Personnel with pagination and individual field filters
     * Uses separate filters for each field
     *
     * @param filter   Filter criteria (cin, matcle, prenom, nomuse)
     * @param pageable Pagination parameters
     * @return Page of HrPersonnelDto
     */
    public Page<HrPersonnelDto> getHrPersonnelWithFilters(HrPersonnelFilterDto filter, Pageable pageable) {
        log.info("Starting filtered request to HR-Space! Page: {}, Size: {}, Filter: {}",
                pageable.getPageNumber(),
                pageable.getPageSize(),
                filter);

        try {
            String cin = normalizeParam(filter.getCin());
            String matcle = normalizeParam(filter.getMatcle());
            String prenom = normalizeParam(filter.getPrenom());
            String nomuse = normalizeParam(filter.getNomuse());

            Page<HrPersonnelDto> result = repository.findHrPersonnelWithFilters(
                    cin,
                    matcle,
                    prenom,
                    nomuse,
                    pageable
            );

            log.info("Found {} records (Total: {})",
                    result.getNumberOfElements(),
                    result.getTotalElements());

            return result;

        } catch (Exception e) {
            log.error("Error fetching HR personnel: {}", e.getMessage(), e);
            throw new EntityNotFoundException("Failed to fetch HR personnel: " + e.getMessage());
        }
    }

    /**
     * Get all HR Personnel without pagination (backward compatibility)
     */
    public List<HrPersonnelDto> getHrPersonnel(String cin) throws EntityNotFoundException {
        List<HrPersonnelDto> hrPersonnelDtos = new ArrayList<>();
        log.info("Starting request to HR-Space!");

        try {
            hrPersonnelDtos = repository.findHrPersonnel(normalizeParam(cin));
            return hrPersonnelDtos;
        } catch (Exception e) {
            log.error("Something went wrong: {}", e.getMessage());
            throw new EntityNotFoundException(e.getMessage());
        } finally {
            log.info("Request completed!");
        }
    }

    public Optional<HrPersonnelDto> findHrPersonnelByCin(String cin) {
        log.info("Looking up HR personnel by CIN: {}", cin);
        List<HrPersonnelDto> results = getHrPersonnel(cin);
        return results.stream().findFirst();
    }

    /**
     * Normalize parameter: trim and convert empty strings to null
     */
    private String normalizeParam(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }


    @Override
    public String getPosteByMatricule(String matcle) {
        log.info("Looking up poste for matricule: {}", matcle);
        try {
            String poste = repository.findPosteByMatricule(normalizeParam(matcle));
            if (poste == null) {
                throw new EntityNotFoundException("No poste found for matricule: " + matcle);
            }
            return poste;
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error fetching poste for matricule {}: {}", matcle, e.getMessage(), e);
            throw new EntityNotFoundException("Failed to fetch poste: " + e.getMessage());
        }
    }

    @Override
    public boolean verifyPosteByMatricule(String matcle, String codePoste) {
        log.info("Verifying poste {} for matricule: {}", codePoste, matcle);
        try {
            int count = repository.countByMatriculeAndPoste(
                    normalizeParam(matcle),
                    normalizeParam(codePoste)
            );
            return count > 0;
        } catch (Exception e) {
            log.error("Error verifying poste {} for matricule {}: {}", codePoste, matcle, e.getMessage(), e);
            throw new EntityNotFoundException("Failed to verify poste: " + e.getMessage());
        }
    }
}