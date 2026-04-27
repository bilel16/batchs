package com.bna.habil.application.services.impl.interim;

import com.bna.habil.domain.beans.interim.EtatInterim;
import com.bna.habil.domain.entities.interim.Interim;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class InterimLifecycleServiceImpl {

    private final InterimRepository interimRepository;
    private final InterimHabilitationServiceImpl habilitationService;

    @Transactional
    public void activateInterim(Interim interim) {

        log.info("Activating interim {}", interim.getId());

        habilitationService.grantInterimHabilitations(interim);

        interim.setEtat(EtatInterim.ACTIF);
        interim.setDateOperation(new Date());

        interimRepository.save(interim);
    }

    @Transactional
    public void terminateInterim(Interim interim) {

        log.info("Terminating interim {}", interim.getId());

        habilitationService.revokeInterimHabilitations(interim);

        interim.setEtat(EtatInterim.TERMINE);
        interim.setDateOperation(new Date());

        interimRepository.save(interim);
    }
}
