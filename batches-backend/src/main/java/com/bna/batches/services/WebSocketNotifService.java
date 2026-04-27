package com.bna.batches.services;

import com.bna.batches.interfaces.response.AgenceProgressMessage;
import com.bna.batches.interfaces.response.BatchStatusMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebSocketNotifService {

    private final SimpMessagingTemplate messagingTemplate;

    /** Push per-agency progress update to all subscribed Angular clients */
    public void pushAgenceProgress(AgenceProgressMessage msg) {
        try {
            messagingTemplate.convertAndSend("/topic/batch/progress", msg);
            log.debug("WS progress → agence={} etat={}", msg.getStructure(), msg.getEtat());
        } catch (Exception e) {
            log.warn("WS push failed for agence {}: {}", msg.getStructure(), e.getMessage());
        }
    }

    /** Push global batch status update */
    public void pushBatchStatus(BatchStatusMessage msg) {
        try {
            messagingTemplate.convertAndSend("/topic/batch/status", msg);
            log.debug("WS status → executionId={} status={}", msg.getExecutionId(), msg.getStatus());
        } catch (Exception e) {
            log.warn("WS status push failed: {}", e.getMessage());
        }
    }
}
