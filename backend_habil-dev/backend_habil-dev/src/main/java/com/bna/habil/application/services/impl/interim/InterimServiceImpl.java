package com.bna.habil.application.services.impl.interim;

import com.bna.habil.application.dto.InterimDetailsDto;
import com.bna.habil.application.dto.InterimDto;
import com.bna.habil.application.dto.statistics.InterimStatsDto;
import com.bna.habil.application.mappers.InterimMapper;
import com.bna.habil.application.services.StructureHierarchyService;
import com.bna.habil.domain.beans.interim.EtatInterim;
import com.bna.habil.domain.entities.interim.Interim;
import com.bna.habil.domain.entities.interim.InterimProfilBackup;
import com.bna.habil.domain.entities.interim.InterimProfilGranted;
import com.bna.habil.domain.exceptions.ResourceNotFoundException;
import com.bna.habil.domain.exceptions.model.InterimBusinessException;
import com.bna.habil.infrastructure.persistence.repositories.PersonneRepository;
import com.bna.habil.infrastructure.persistence.repositories.extra.UtilisateurProfilRepository;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimProfilBackupRepository;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimProfilGrantedRepository;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimRepository;
import com.bna.habil.infrastructure.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.bna.habil.application.services.impl.interim.InterimSchedulerServiceImpl.getDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterimServiceImpl {

    private final InterimRepository interimRepository;
    private final InterimHabilitationServiceImpl habilitationService;
    private final InterimMapper interimMapper;
    private final InterimProfilGrantedRepository grantedRepository;
    private final InterimProfilBackupRepository backupRepository;
    private final PersonneRepository personneRepository;
    private final UtilisateurProfilRepository profilRepository;
    private final StructureHierarchyService hierarchyService;
    private static final String INTERIM_NOT_FOUND = "Intérim non trouvé";

    @Transactional
    public Interim createInterim(Interim interim) {
        Date today = truncateToDate(new Date());

        // Check for overlapping interims
        if (interimRepository.existsOverlappingInterim(
                interim.getMatriculeCible(),
                interim.getDateDebutInterim(),
                interim.getDateFinInterim(),
                null)) {
            throw new InterimBusinessException(
                    "Cet agent a déjà un intérim actif ou planifié sur cette période"
            );
        }

        // Set audit date
        interim.setDateOperation(new Date());

        // Determine initial state
        Date dateDebut = truncateToDate(interim.getDateDebutInterim());

        if (dateDebut.after(today)) {
            // ─── FUTURE INTERIM ───
            interim.setEtat(EtatInterim.EN_ATTENTE);
            log.info("Interim scheduled for future activation on {}", dateDebut);

        } else if (dateDebut.equals(today)) {
            // ─── IMMEDIATE INTERIM ───
            interim.setEtat(EtatInterim.ACTIF);

            // SAVE FIRST to get the ID
            interim = interimRepository.save(interim);

            // NOW grant habilitations (interim.getId() is no longer null)
            habilitationService.grantInterimHabilitations(interim);
            log.info("Interim activated immediately");

        } else {
            throw new InterimBusinessException(
                    "La date de début ne peut pas être dans le passé"
            );
        }

        return interimRepository.save(interim);
    }

    @Transactional
    public Interim cancelInterim(Long interimId) {
        Interim interim = interimRepository.findById(interimId)
                .orElseThrow(() -> new ResourceNotFoundException(INTERIM_NOT_FOUND));

        if (interim.getEtat() == EtatInterim.TERMINE) {
            throw new InterimBusinessException("Impossible d'annuler un intérim déjà terminé");
        }

        // If it was active, revoke habilitations
        if (interim.getEtat() == EtatInterim.ACTIF) {
            habilitationService.revokeInterimHabilitations(interim);
        }

        interim.setEtat(EtatInterim.ANNULE);
        interim.setDateOperation(new Date());

        return interimRepository.save(interim);
    }

    @Transactional
    public Interim updateInterim(Long id, InterimDto dto) {

        Interim existing = interimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(INTERIM_NOT_FOUND));

        //  BUSINESS RULE
        if (existing.getEtat() != EtatInterim.EN_ATTENTE) {
            throw new InterimBusinessException(
                    "Seuls les intérims en attente peuvent être modifiés"
            );
        }
        // overlap validation
        if (interimRepository.existsOverlappingInterim(
                dto.getMatriculeCible(),
                dto.getDateDebutInterim(),
                dto.getDateFinInterim(),
                id)) {

            throw new InterimBusinessException(
                    "Cet agent a déjà un intérim actif ou planifié sur cette période"
            );
        }


        // MapStruct update
        interimMapper.updateEntityFromDto(dto, existing);

        // Audit
        existing.setDateOperation(new Date());

        return interimRepository.save(existing);
    }

    /**
     * GET /api/interims/{id}
     */
    @Transactional(readOnly = true)
    public Interim getInterimById(Long id) {
        return interimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Intérim avec l'id " + id + " non trouvé"));
    }

    @Transactional(readOnly = true)
    public List<InterimDetailsDto> getAllInterims() {
        List<Interim> interims;
        String currentUser = SecurityUtils.getCurrentUserMatricule();
        if (isSuperAdmin(currentUser)) {
            interims = interimRepository.findAll();
        } else {
            Set<Integer> managedMatricules = getManagedMatricules(currentUser);
            if (managedMatricules.isEmpty()) {
                return Collections.emptyList();
            }
            interims = interimRepository.findByManagedMatricules(managedMatricules);
        }

        return enrichWithNames(interims);
    }

    @Transactional(readOnly = true)
    public List<InterimDetailsDto> searchInterims(Integer matriculeSource,
                                                  Integer matriculeCible,
                                                  EtatInterim etat,
                                                  Integer codStrc,
                                                  Date dateDebut,
                                                  Date dateFin) {
        List<Interim> results;
        String currentUser = SecurityUtils.getCurrentUserMatricule();
        if (isSuperAdmin(currentUser)) {
            results = interimRepository.searchInterims(
                    matriculeSource, matriculeCible, etat, codStrc, dateDebut, dateFin);
        } else {
            Set<Integer> managedMatricules = getManagedMatricules(currentUser);
            if (managedMatricules.isEmpty()) {
                return Collections.emptyList();
            }
            results = interimRepository.searchInterimsByManagedMatricules(
                    managedMatricules, matriculeSource, matriculeCible, etat, codStrc, dateDebut, dateFin);
        }

        return enrichWithNames(results);
    }

    private boolean isSuperAdmin(String currentUser) {
        return profilRepository.existsSuperAdminHabilAtLevel(
                currentUser,
                String.valueOf(personneRepository.findCodeTypeStructureByMat(currentUser))
        );
    }

    private Set<Integer> getManagedMatricules(String currentUser) {
        Set<Integer> managedStructureIds = hierarchyService.getManagedStructureIds(currentUser);
        if (managedStructureIds.isEmpty()) {
            return Collections.emptySet();
        }
        // Get all matricules from those structures
        return personneRepository.findPersonnelDetailsByStructureIds(managedStructureIds)
                .stream()
                .map(dto -> Integer.valueOf(dto.getMat()))
                .collect(Collectors.toSet());
    }

    private List<InterimDetailsDto> enrichWithNames(List<Interim> interims) {
        Set<Integer> allMatricules = new HashSet<>();
        for (Interim i : interims) {
            if (i.getMatriculeSource() != null) allMatricules.add(i.getMatriculeSource());
            if (i.getMatriculeCible() != null) allMatricules.add(i.getMatriculeCible());
        }

        Map<Integer, String> nameMap = personneRepository.findFullNamesByMatricules(allMatricules)
                .stream()
                .collect(Collectors.toMap(
                        row -> Integer.parseInt(String.valueOf(row[0])),
                        row -> (String) row[1],
                        (a, b) -> a
                ));

        return interims.stream().map(i -> {
            InterimDetailsDto dto = interimMapper.toDetailsDto(i);
            dto.setNomPrenomSource(nameMap.get(i.getMatriculeSource()));
            dto.setNomPrenomCible(nameMap.get(i.getMatriculeCible()));
            return dto;
        }).toList();
    }

    /**
     * GET /api/interims/by-source/{matricule}
     */
    @Transactional(readOnly = true)
    public List<Interim> getInterimsBySource(Integer matricule) {
        return interimRepository.findByMatriculeSourceOrderByDateDebutInterimDesc(matricule);
    }

    /**
     * GET /api/interims/by-cible/{matricule}
     */
    @Transactional(readOnly = true)
    public List<Interim> getInterimsByCible(Integer matricule) {
        return interimRepository.findByMatriculeCibleOrderByDateDebutInterimDesc(matricule);
    }

    /**
     * GET /api/interims/by-user/{matricule}
     * Returns all interims where user is source OR cible
     */
    @Transactional(readOnly = true)
    public List<Interim> getInterimsByUser(Integer matricule) {
        return interimRepository.findByUser(matricule);
    }

    /**
     * GET /api/interims/by-state/{etat}
     */
    @Transactional(readOnly = true)
    public List<Interim> getInterimsByState(EtatInterim etat) {
        return interimRepository.findByEtatOrderByDateDebutInterimDesc(etat);
    }

    /**
     * GET /api/interims/by-structure/{codStrc}
     */
    @Transactional(readOnly = true)
    public List<Interim> getInterimsByStructure(Integer codStrc) {
        return interimRepository.findByCodStrcDestinationOrderByDateDebutInterimDesc(codStrc);
    }

    /**
     * GET /api/interims/active/cible/{matricule}
     * Check if user currently has an active interim as cible
     */
    @Transactional(readOnly = true)
    public Optional<Interim> getActiveInterimForCible(Integer matricule) {
        return interimRepository.findActiveInterimForCible(matricule);
    }

    /**
     * GET /api/interims/active/source/{matricule}
     * Check if user is currently being replaced
     */
    @Transactional(readOnly = true)
    public Optional<Interim> getActiveInterimForSource(Integer matricule) {
        return interimRepository.findActiveInterimForSource(matricule);
    }

    /**
     * GET /api/interims/stats
     */
    @Transactional(readOnly = true)
    public InterimStatsDto getStatistics() {
        String currentUser = SecurityUtils.getCurrentUserMatricule();

        if (isSuperAdmin(currentUser)) {
            return InterimStatsDto.builder()
                    .totalEnAttente(interimRepository.countByEtat(EtatInterim.EN_ATTENTE))
                    .totalActif(interimRepository.countByEtat(EtatInterim.ACTIF))
                    .totalTermine(interimRepository.countByEtat(EtatInterim.TERMINE))
                    .totalAnnule(interimRepository.countByEtat(EtatInterim.ANNULE))
                    .build();
        }

        Set<Integer> managedMatricules = getManagedMatricules(currentUser);
        if (managedMatricules.isEmpty()) {
            return InterimStatsDto.builder()
                    .totalEnAttente(0L)
                    .totalActif(0L)
                    .totalTermine(0L)
                    .totalAnnule(0L)
                    .build();
        }

        return InterimStatsDto.builder()
                .totalEnAttente(interimRepository.countByEtatAndManagedMatricules(EtatInterim.EN_ATTENTE, managedMatricules))
                .totalActif(interimRepository.countByEtatAndManagedMatricules(EtatInterim.ACTIF, managedMatricules))
                .totalTermine(interimRepository.countByEtatAndManagedMatricules(EtatInterim.TERMINE, managedMatricules))
                .totalAnnule(interimRepository.countByEtatAndManagedMatricules(EtatInterim.ANNULE, managedMatricules))
                .build();
    }

    /**
     * GET /api/interims/{id}/granted-profiles
     * See which profiles were granted during this interim
     */
    @Transactional(readOnly = true)
    public List<InterimProfilGranted> getGrantedProfiles(Long interimId) {
        if (!interimRepository.existsById(interimId)) {
            throw new ResourceNotFoundException(INTERIM_NOT_FOUND);
        }
        return grantedRepository.findByInterimId(interimId);
    }

    /**
     * GET /api/interims/{id}/backed-up-profiles
     * See which profiles were backed up during this interim
     */
    @Transactional(readOnly = true)
    public List<InterimProfilBackup> getBackedUpProfiles(Long interimId) {
        if (!interimRepository.existsById(interimId)) {
            throw new ResourceNotFoundException(INTERIM_NOT_FOUND);
        }
        return backupRepository.findByInterimId(interimId);
    }

    private Date truncateToDate(Date date) {
        return getDate(date);
    }


}