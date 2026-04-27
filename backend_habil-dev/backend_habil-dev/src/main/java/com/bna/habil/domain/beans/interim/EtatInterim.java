package com.bna.habil.domain.beans.interim;

public enum EtatInterim {
    EN_ATTENTE,    // Created for a future date
    ACTIF,         // Currently active (dateDebut reached)
    TERMINE,       // Completed (dateFin passed)
    ANNULE         // Cancelled manually
}