package com.bna.smile.model.reporting.model;

public class Nombre {

    public static final String ZERO = "zéro";
    public static final String UN = "un";
    public static final String DEUX = "deux";
    public static final String TROIS = "trois";
    public static final String QUATRE = "quatre";
    public static final String CINQ = "cinq";
    public static final String SIX = "six";
    public static final String SEPT = "sept";
    public static final String HUIT = "huit";
    public static final String NEUF = "neuf";
    public static final String DIX = "dix";
    public static final String ONZE = "onze";
    public static final String DOUZE = "douze";
    public static final String TREIZE = "treize";
    public static final String QUATORZE = "quatorze";
    public static final String QUINZE = "quinze";
    public static final String SEIZE = "seize";
    public static final String VINGT = "vingt";
    public static final String TRENTE = "trente";
    public static final String QUARANTE = "quarante";
    public static final String CINQUANTE = "cinquante";
    public static final String SOIXANTE = "soixante";
    public static final String CENT = "cent";
    public static final String MILLE = "mille";
    public static final String MILLION = "million";
    public static final String MILLIARD = "milliard";
    public static final String MOINS = "moins";
    private static final String[] tab = 
    { "", MILLE, MILLION, MILLIARD, MILLE + " " + MILLIARD, 
      MILLION + " de " + MILLIARD, MILLIARD + " de " + MILLIARD };

    public static String getChiffre(int l) {
        if ((l < 0) || (l > 9))
            throw new IllegalArgumentException("Un chiffre est entre 0 et 9, donc " + 
                                               l + " est interdit");
        switch (l) {
        case 0:
            return ZERO;
        case 1:
            return UN;
        case 2:
            return DEUX;
        case 3:
            return TROIS;
        case 4:
            return QUATRE;
        case 5:
            return CINQ;
        case 6:
            return SIX;
        case 7:
            return SEPT;
        case 8:
            return HUIT;
        case 9:
            return NEUF;
        }
        return null;
    }

    private static String paquet(int p) {
        String reponse = "";
        if (p > 100) {
            if (p / 100 > 1)
                reponse = getChiffre(p / 100) + " ";
            if (p / 100 >= 2) {
                reponse += CENT + "s ";
            } else {
                reponse += CENT + " ";
            }
            p = p % 100;
        }
        int c = p / 10;
        int u = p % 10;
        switch (c) {
        case 0:
            if (u != 0)
                reponse += getChiffre(u);
            break;
        case 1:
            switch (u) {
            case 0:
                reponse += DIX;
                break;
            case 1:
                reponse += ONZE;
                break;
            case 2:
                reponse += DOUZE;
                break;
            case 3:
                reponse += TREIZE;
                break;
            case 4:
                reponse += QUATORZE;
                break;
            case 5:
                reponse += QUINZE;
                break;
            case 6:
                reponse += SEIZE;
                break;
            default:
                reponse += DIX + " " + getChiffre(u);
            }
            break;
        case 2:
            reponse += VINGT;
            if (u == 1)
                reponse += " et";
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 3:
            reponse += TRENTE;
            if (u == 1)
                reponse += " et";
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 4:
            reponse += QUARANTE;
            if (u == 1)
                reponse += " et";
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 5:
            reponse += CINQUANTE;
            if (u == 1)
                reponse += " et";
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 6:
            reponse += SOIXANTE;
            if (u == 1)
                reponse += " et";
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 7:
            reponse += SOIXANTE + " ";
            if (u == 1)
                reponse += " et";
            switch (u) {
            case 0:
                reponse += DIX;
                break;
            case 1:
                reponse += ONZE;
                break;
            case 2:
                reponse += DOUZE;
                break;
            case 3:
                reponse += TREIZE;
                break;
            case 4:
                reponse += QUATORZE;
                break;
            case 5:
                reponse += QUINZE;
                break;
            case 6:
                reponse += SEIZE;
                break;
            default:
                reponse += DIX + " " + getChiffre(u);
            }
            break;
        case 8:
            reponse += QUATRE + " " + VINGT;
            if (u > 0)
                reponse += " " + getChiffre(u);
            break;
        case 9:
            reponse += QUATRE + " " + VINGT + " ";
            switch (u) {
            case 0:
                reponse += DIX;
                break;
            case 1:
                reponse += ONZE;
                break;
            case 2:
                reponse += DOUZE;
                break;
            case 3:
                reponse += TREIZE;
                break;
            case 4:
                reponse += QUATORZE;
                break;
            case 5:
                reponse += QUINZE;
                break;
            case 6:
                reponse += SEIZE;
                break;
            default:
                reponse += DIX + " " + getChiffre(u);
            }
            break;
        }
        return (reponse.trim());

    }

    public static String getLettre(long l) {

        int Nombre = 0;
        if (l == 0L)
            return ZERO;
        String signe = "";
        //Cas négatif
        if (l < 0L) {
            l = -l;
            signe = MOINS + " ";
        }

        String reponse = "";
        int rang = 0;
        while (l > 0L) {

            Nombre = (int)(l % 1000L);
            if (rang >= 1 && Nombre == 1) {
                reponse = tab[rang] + " " + reponse;
            } else {
                reponse = paquet(Nombre) + " " + tab[rang] + " " + reponse;
            }

            l = l / 1000L;
            rang++;
        }

        reponse = signe + reponse;
        return reponse.trim();
    }

 
}



