package com.bna.habil.application.services.impl;


import java.util.*;
import java.util.stream.Collectors;


import com.bna.habil.application.dto.*;
import com.bna.habil.application.dto.ldap.UpdateUserRequest;
import com.bna.habil.application.dto.ldap.UserRequest;
import com.bna.habil.application.dto.statistics.PersonnelStatsDto;
import com.bna.habil.application.dto.statistics.StructurePersonnelCountDto;
import com.bna.habil.application.mappers.PersonnelMapper;
import com.bna.habil.application.services.HrPersonnelService;
import com.bna.habil.application.services.LdapService;
import com.bna.habil.application.services.crud.AbstractCrudService;
import com.bna.habil.domain.exceptions.EntityNotFoundException;
import com.bna.habil.domain.exceptions.ValidationException;
import com.bna.habil.infrastructure.security.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bna.habil.application.services.PersonneService;
import com.bna.habil.domain.entities.Personne;
import com.bna.habil.domain.entities.Personnel;
import com.bna.habil.infrastructure.persistence.repositories.PersonneRepository;
import com.bna.habil.infrastructure.persistence.repositories.PersonnelCustomRepository;

@Slf4j
@Service
public class PersonneServiceImpl extends AbstractCrudService<Personnel, PersonnelDto, String> implements PersonneService {

    private final PersonneRepository personneRepository;
    private final PersonnelCustomRepository personnelCustomRepository;
    private final PersonnelMapper personnelMapper;
    private final LdapService ldapService;
    private final HrPersonnelService hrPersonnelService;

    public PersonneServiceImpl(PersonnelCustomRepository personnelRepository,
                               PersonnelMapper personnelMapper,
                               PersonneRepository personneRepository, LdapService ldapService, HrPersonnelService hrPersonnelService) { // Add PersonneRepository
        super(
                personnelRepository, // This should be PersonnelRepository, not PersonneRepository for Personnel entity
                personnelMapper,
                PersonnelDto::getMatricule, // ID extractor for PersonnelDto
                id -> "Mat=" + id,           // ID stringifier
                dto -> { // Create validator for PersonnelDto
                    if (dto.getMatricule() == null || dto.getMatricule().trim().isEmpty()) {
                        throw new ValidationException("Matricule cannot be empty");
                    }
                },
                (id, dto, existing) -> {
                }    // Update validator
        );
        // Note: There's a conceptual mismatch here. AbstractCrudService is for Personnel entity,
        // but you're passing PersonneRepository. It should be PersonnelCustomRepository.
        // Correcting the assumption that personnelRepository is for Personnel entity.
        this.personnelCustomRepository = personnelRepository;
        this.personneRepository = personneRepository; // Inject PersonneRepository separately
        this.personnelMapper = personnelMapper;
        this.ldapService = ldapService;
        this.hrPersonnelService = hrPersonnelService;
    }

    @Override
    protected String getEntityName() {
        return "Personnel";
    }

    // Override the generic create method to use PersonnelDto
    @Override
    public PersonnelDto create(PersonnelDto dto) {
        return super.create(dto);
    }

    @Transactional
    public PersonnelDetailsDto createPersonnel(PersonnelDto dto) {
        if (dto == null) {
            throw new ValidationException("DTO cannot be null");
        }

        String id = dto.getMatricule();
        String idStr = "Mat=" + id;
        log.info("Creating personnel: {}", idStr);

        if (personnelCustomRepository.existsById(id)) {
            throw new ValidationException("Personnel with id " + idStr + " already exists");
        }

        // Fetch nom/prenom from HR system
        HrPersonnelDto hrPerson = hrPersonnelService.findHrPersonnelByCin(dto.getCin())
                .orElseThrow(() -> {
                    log.error("HR record not found for cin: {}", dto.getCin());
                    return new EntityNotFoundException("No HR record found for cin: " + dto.getCin());
                });

        log.info("HR data resolved — prenom: [{}], nom: [{}], cin: [{}]",
                hrPerson.getPrenom(), hrPerson.getNomuse(), hrPerson.getCin());

        // Step 1: Save to database
        Personnel entity = personnelMapper.toEntity(dto);
        entity.setCod_typ("M");
        entity.setDat_stat(new Date());
        Personnel saved = personnelCustomRepository.save(entity);
        log.info("Personnel saved to database: {}", idStr);

        // Step 2: Create user in LDAP — failure rolls back DB
        try {
            UserRequest ldapRequest = UserRequest.builder()
                    .matricule(dto.getMatricule())
                    .nom(hrPerson.getNomuse())
                    .prenom(hrPerson.getPrenom())
//                    .email(dto.getEmail())
                    .cin(dto.getCin())
                    .structure(dto.getStructureId().toString())
//                    .password(dto.getPassword())
                    .build();

            ldapService.addUser(ldapRequest);
            log.info("User created in LDAP for: {}", idStr);
        } catch (Exception e) {
            log.error("LDAP creation failed for: {}. Rolling back DB transaction.", idStr, e);
            throw new RuntimeException("Failed to create user in LDAP for " + idStr + ". DB rolled back.", e);
        }

        return personnelCustomRepository.findPersonnelDetailsByMat(saved.getMat())
                .orElseGet(() -> {
                    PersonnelDetailsDto basicDto = new PersonnelDetailsDto();
                    basicDto.setMat(saved.getMat());
                    basicDto.setCod_stat_user(saved.getCod_stat_user());
                    basicDto.setCod_strc_strc(saved.getCod_strc_strc());
                    basicDto.setNom_prenom(null);
                    basicDto.setEmail(null);
                    basicDto.setCod_tstr_tstr(null);
                    return basicDto;
                });
    }


    @Override
    public Page<Personne> getAll(int page, int size) {
        return personneRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public List<Personnel> findPersonnelByMatricule(String matricule) {
        return personnelCustomRepository.findPersonnelByMat(matricule);
    }

    @Override
    public Optional<Personne> findByNumMatrUser(String matricule) {
        return personneRepository.findTheUser(matricule);
    }

    public List<PersonnelDetailsDto> getAllPersonelles() {
        return personnelCustomRepository.findAllPersonnelDetails();
    }

    public Page<PersonnelDetailsDto> getPersonelllesPageble(Pageable pageable) {
        return personnelCustomRepository.findAllPersonnelDetails(pageable);
    }
    public Page<PersonnelDetailsDto> getPersonnelPageable(Pageable pageable, PersonnelFilterDto filter) {

        // ✅ Admin users get ALL personnel with filters
        if (SecurityUtils.isAdmin()) {
            return personneRepository.findAllPersonnelDetailsWithFilters(
                    filter.getSearch(),
                    filter.getCodStatUser(),
                    filter.getCodStrcStrcList(),
                    filter.getCodTstrTstr(),
                    pageable
            );
        }
            return Page.empty(pageable);

    }

    @Transactional
    @Override
    public PersonnelDetailsDto updatePersonelle(String mat, PersonnelDetailsDto dto) {
        log.info("Updating personnel: Mat={}", mat);

        Personnel p = personnelCustomRepository.findById(mat)
                .orElseThrow(() -> new EntityNotFoundException("Personnel not found: " + mat));

        boolean changed = false;

        if (dto.getCod_stat_user() != null && !Objects.equals(p.getCod_stat_user(), dto.getCod_stat_user())) {
            log.debug("Updating cod_stat_user: [{}] → [{}]", p.getCod_stat_user(), dto.getCod_stat_user());
            p.setCod_stat_user(dto.getCod_stat_user());
            changed = true;
        }

        if (dto.getCod_strc_strc() != null && !Objects.equals(p.getCod_strc_strc(), dto.getCod_strc_strc())) {
            log.debug("Updating cod_strc_strc: [{}] → [{}]", p.getCod_strc_strc(), dto.getCod_strc_strc());
            p.setCod_strc_strc(dto.getCod_strc_strc());
            changed = true;
        }

        if (changed) {
            p.setDat_stat(new Date());
            personnelCustomRepository.save(p);
            log.info("Personnel updated in database: Mat={}", mat);
        } else {
            log.info("No database changes detected for: Mat={}", mat);
        }

        // Sync to LDAP — failure rolls back DB
        try {
            UpdateUserRequest ldapUpdate = UpdateUserRequest.builder()
//                    .nom(dto.getNom_prenom())
//                    .email(dto.getEmail())
                    .structure(dto.getCod_strc_strc().toString())
                    .build();

            ldapService.updateUser(mat, ldapUpdate);
            log.info("User updated in LDAP for: Mat={}", mat);
        } catch (Exception e) {
            log.error("LDAP update failed for: Mat={}. Rolling back DB transaction.", mat, e);
            throw new RuntimeException("Failed to update user in LDAP for Mat=" + mat + ". DB rolled back.", e);
        }

        return personnelCustomRepository.findPersonnelDetailsByMat(mat)
                .orElseThrow(() -> new EntityNotFoundException("Personnel details not found after update for: " + mat));
    }

    @Override
    public PersonnelStatsDto getPersonnelStatistics() {
        List<StructurePersonnelCountDto> perStructure = personnelCustomRepository.countPersonnelByStructure();

        long total = 0L;
        long active = 0L;
        long inactive = 0L;

        for (StructurePersonnelCountDto s : perStructure) {
            if (s.getTotal() != null) total += s.getTotal();
            if (s.getActive() != null) active += s.getActive();
            if (s.getInactive() != null) inactive += s.getInactive();
        }

        return new PersonnelStatsDto(total, active, inactive, perStructure);
    }

    @Override
    public List<EmployeeDTO> getEmployeesByDate() {
        List<Object[]> personelles = personnelCustomRepository.findEmployeesByDate();
        System.out.println(personelles.size());
        return personnelCustomRepository.findEmployeesByDate().stream()
                .map(row -> new EmployeeDTO(
                        (String) row[0],  // MATCLE
                        (String) row[1],  // PRENOM
                        (String) row[2],  // NOMUSE
                        (String) row[3],  // CIN
                        (String) row[4],  // IDJB00
                        (String) row[5],  // LBJBLG
                        (String) row[6],  // LBOULG
                        (String) row[7]   // IDOU00
                ))
                .collect(Collectors.toList());
    }
}