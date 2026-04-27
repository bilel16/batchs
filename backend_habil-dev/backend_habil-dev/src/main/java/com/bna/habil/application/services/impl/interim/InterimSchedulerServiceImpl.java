package com.bna.habil.application.services.impl.interim;

import com.bna.habil.domain.entities.interim.Interim;
import com.bna.habil.infrastructure.persistence.repositories.interim.InterimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InterimSchedulerServiceImpl {

    private final InterimRepository interimRepository;
    private final InterimLifecycleServiceImpl lifecycleService;

    static Date getDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void processInterims() {

        Date today = truncateToDate(new Date());

        List<Interim> toActivate = interimRepository.findInterimsToActivate(today);
        List<Interim> toTerminate = interimRepository.findInterimsToTerminate(today);

        log.info("Interim scheduler: {} to activate, {} to terminate",
                toActivate.size(), toTerminate.size());

        toActivate.forEach(this::safeActivate);
        toTerminate.forEach(this::safeTerminate);
    }

    private void safeActivate(Interim interim) {
        try {
            lifecycleService.activateInterim(interim);
        } catch (Exception e) {
            log.error("Activation failed for interim {}", interim.getId(), e);
        }
    }

    private void safeTerminate(Interim interim) {
        try {
            lifecycleService.terminateInterim(interim);
        } catch (Exception e) {
            log.error("Termination failed for interim {}", interim.getId(), e);
        }
    }

    private Date truncateToDate(Date date) {
        return getDate(date);
    }
}