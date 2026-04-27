package com.bna.habil.application.services;


import com.bna.habil.domain.entities.interim.Interim;

public interface InterimHabilitationService {

    void grantInterimHabilitations(Interim interim);

    void revokeInterimHabilitations(Interim interim);
}
