package com.bna.smile.model.domainetraitementfichier;

import com.bna.commun.util.StrHandler;
import java.io.*;
import java.text.*;
import java.util.*;
import org.apache.log4j.Logger;

// Referenced classes of package com.bna.smile.model.domainetraitementfichier:
//            GetCodeBctAgence, Util, Parametres, Fichier

public class TraitementFile implements Serializable
{

    public TraitementFile()
    {
        getCodeBctAgence = new GetCodeBctAgence();
        util = new Util();
        parametres = new Parametres();
    }

    public void traiterFichiers(String cheminrepsource, String cheminreptravail, String cheminrepdest, String cheminrepdestlocale, String cheminreptraite, String cheminreptest, String cheminrepdest2)
    {
        File repSource = new File(cheminrepsource);
        File list[] = repSource.listFiles(new Fichier());
        if(list != null)
        {
            for(int i = 0; i < list.length;)
            {
                String pathFileInTravail = (new StringBuilder()).append(cheminreptravail).append("\\").append(list[i].getName()).toString();
                String pathFileInTraite = (new StringBuilder()).append(cheminreptraite).append("\\").append(list[i].getName()).toString();
                File fichierTraite = new File(pathFileInTraite);
                File fichierTravail = new File(pathFileInTravail);
                List listFichier = getCodeBctAgence.getFichier(list[i].getName());
                System.out.println(list[i].getName());
                if(list[i].length() == 0L || listFichier.size() != 0 && ((Fichier)listFichier.get(0)).getCodeTraitFichier() == 2){
                    continue;
                }
                String codeValeur = list[i].getName().substring(0, 2);
                String codeStrc = list[i].getName().substring(9, list[i].getName().length());
                String jj = list[i].getName().substring(2, 4);
                String mm = list[i].getName().substring(4, 6);
                String aa = list[i].getName().substring(6, 8);
                String nomdest = "";
                String nomdest10 = "";
                String nomdest20 = "";
                String nomdest4121 = "";
                String nomdest4122 = "";
                String nomdest47 = "";
                String nomdest30 = "";
                String nomdest8x = "";
                String nomdest4021 = "";
                String nomdest4022 = "";
                String nomdest45 = "";
                String codeEnrGlobal = "Code Enregistrement Global ";
                String codeEnrDetail = "Code Enregistrement D\351tail ";
                String typeValeur = null;
                String codeEnrGlobalValeur = null;
                String codeEnrDetailValeur = null;
                String typeValeur10 = null;
                String typeValeur20 = null;
                String typeValeur41 = null;
                String codeEnrGlobal10 = null;
                String codeEnrDetail10 = null;
                String codeEnrGlobal20 = null;
                String codeEnrDetail20 = null;
                String codeEnrDetailPres41 = null;
                String codeEnrDetailRejet41 = null;
                String codeEnrDetailPres40 = null;
                String codeEnrDetailRejet40 = null;
                String codeEnrGlobal41 = null;
                String codeEnrGlobal40 = null;
                String typeValeur30 = null;
                String codeEnrGlobal30 = null;
                String codeEnrDetail30 = null;
                String typeValeur8x = null;
                String codeEnrGlobal8x = null;
                String codeEnrDetail8x = null;
                String codeBct = getCodeBctAgence.getCodeBctAgence(codeStrc);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat dateFormat = DateFormat.getDateInstance(3);
                Date datePec = null;
                String dateJourFile = (new StringBuilder()).append(jj).append("/").append(mm).append("/").append(aa).toString();
                String dateSystem = dateFormat.format(new Date());
                try
                {
                    sdf.setLenient(false);
                    datePec = sdf.parse((new StringBuilder()).append(jj).append("/").append(mm).append("/20").append(aa).toString());
                }
                catch(ParseException e)
                {
                    logger.info((new StringBuilder()).append("Probl\350me de parsing date du fichier").append(list[i].getAbsolutePath()).toString());
                }
                boolean isdatevalide;
                try
                {
                    isdatevalide = util.testDateValide(Integer.parseInt((new StringBuilder()).append("20").append(aa).toString()), Integer.parseInt(mm) - 1, Integer.parseInt(jj));
                }
                catch(NumberFormatException e)
                {
                    logger.info((new StringBuilder()).append("Date non valide!!!! du fichier").append(list[i].getAbsolutePath()).toString());
                    isdatevalide = false;
                }
                if(dateJourFile.equals(dateSystem) && codeValeur.equals("10") && codeBct != null && isdatevalide)
                    try
                    {
                        typeValeur10 = parametres.getTypeValeur(String.valueOf(codeValeur));
                        codeEnrGlobalValeur = parametres.getTypeValeur(String.valueOf("Code Enregistrement Global 10"));
                        codeEnrDetailValeur = parametres.getTypeValeur(String.valueOf("Code Enregistrement D\351tail 10"));
                    }
                    catch(InvalidPropertiesFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                if(typeValeur10 != null && codeEnrGlobalValeur != null && codeEnrDetailValeur != null)
                {
                    if(listFichier.size() == 0)
                        getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                    logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 1);
                    logger.info((new StringBuilder()).append("------------Control 10 du fichier").append(pathFileInTravail).append("------------").toString());
                    if(file10Control(pathFileInTravail, codeValeur, codeEnrGlobalValeur, codeEnrDetailValeur).booleanValue())
                    {
                        logger.info((new StringBuilder()).append("------------Control 10 du fichier").append(pathFileInTravail).append(" est vrai------------").toString());
                        nomdest = (new StringBuilder()).append("03-").append(codeBct).append("-").append(typeValeur10).append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        String pathFile10InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest).toString();
                        File file10InDestLocale = new File(pathFile10InDestLocale);
                        try
                        {
                            logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFile10InDestLocale).append("------------").toString());
                            util.copy(pathFileInTravail, pathFile10InDestLocale);
                            logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFile10InDestLocale).append("------------").toString());
                            if(file10InDestLocale.exists() && fichierTravail.exists())
                            {
                                int nbreligne10Orig = getNombreLignes(pathFileInTravail, "10");
                                int nbreligne10Trai = getNombreLignes(pathFile10InDestLocale, "10");
                                if(nbreligne10Orig == nbreligne10Trai)
                                {
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile10InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest).append("------------").toString());
                                    int traite = util.copy(pathFile10InDestLocale, (new StringBuilder()).append(cheminrepdest).append("\\").append(nomdest).toString());
                                    if(traite == 1)
                                    {
                                        getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                                        File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("Code10Traite"))).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                                        list[i].renameTo(fileTraite);
                                    }
                                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile10InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest).append("------------").toString());
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                    util.copy(pathFileInTravail, pathFileInTraite);
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                } else
                                {
                                    if(file10InDestLocale.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file10InDestLocale).append("------------").toString());
                                        file10InDestLocale.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                    if(fichierTravail.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                                        fichierTravail.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                }
                            }
                            logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                        }
                        catch(Exception e)
                        {
                            logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                        }
                        continue;
                    }
                    logger.info((new StringBuilder()).append("------------Control 10 du fichier").append(pathFileInTravail).append(" est faux------------").toString());
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                    if(fichierTravail.exists())
                        fichierTravail.delete();
                    logger.info("------------Fichier supprim\351------------");
                    continue;
                }
                if(dateJourFile.equals(dateSystem) && codeBct != null && codeValeur.toUpperCase().equals("VP") && isdatevalide)
                {
                    try
                    {
                        typeValeur10 = parametres.getTypeValeur(String.valueOf(10));
                        typeValeur20 = parametres.getTypeValeur(String.valueOf(20));
                        codeEnrGlobal10 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("VP10").toString()));
                        codeEnrDetail10 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("VP10").toString()));
                        codeEnrGlobal20 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("VP20").toString()));
                        codeEnrDetail20 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("VP20").toString()));
                    }
                    catch(InvalidPropertiesFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    if(typeValeur10 == null || typeValeur20 == null || codeEnrGlobal10 == null || codeEnrDetail10 == null || codeEnrGlobal20 == null || codeEnrDetail20 == null){
                        continue;
                    }
                    if(listFichier.size() == 0){
                        getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                    }
                    logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 1);
                    logger.info((new StringBuilder()).append("------------Control 10 du fichier").append(pathFileInTravail).append("------------").toString());
                    if(file10Control(pathFileInTravail, "10", codeEnrGlobal10, codeEnrDetail10).booleanValue() && file20Control(pathFileInTravail, "20", codeEnrGlobal20, codeEnrDetail20).booleanValue())
                    {
                        logger.info((new StringBuilder()).append("------------Control 10 et 20 du fichier").append(pathFileInTravail).append(" est vrai------------").toString());
                        nomdest10 = (new StringBuilder()).append("03-").append(codeBct).append("-").append(typeValeur10).append("-0002-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        nomdest20 = (new StringBuilder()).append("03-").append(codeBct).append("-").append(typeValeur20).append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        String pathFile10InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest10).toString();
                        String pathFile20InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest20).toString();
                        File file10InDestLocale = new File(pathFile10InDestLocale);
                        File file20InDestLocale = new File(pathFile20InDestLocale);
                        try
                        {
                            logger.info((new StringBuilder()).append("------------D\351falcation du fichier ").append(pathFileInTravail).append(" en ").append(pathFile20InDestLocale).append(" et ").append(pathFile10InDestLocale).append("------------").toString());
                            defalquerVP(pathFileInTravail, pathFile10InDestLocale, pathFile20InDestLocale);
                            logger.info((new StringBuilder()).append("------------Fin D\351falcation du fichier ").append(pathFileInTravail).append(" en ").append(pathFile20InDestLocale).append(" et ").append(pathFile10InDestLocale).append("------------").toString());
                            if(!file10InDestLocale.exists() || !file20InDestLocale.exists() || !fichierTravail.exists())
                                continue;
                            int nbreligne10invp = getNombreLignes(pathFileInTravail, "10");
                            int nbreligne10 = getNombreLignes(pathFile10InDestLocale, "10");
                            int nbreligne20invp = getNombreLignes(pathFileInTravail, "20");
                            int nbreligne20 = getNombreLignes(pathFile20InDestLocale, "20");
                            if(nbreligne10invp == nbreligne10 && nbreligne20invp == nbreligne20)
                            {
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile10InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest10).append("------------").toString());
                                int traite10 = util.copy(pathFile10InDestLocale, (new StringBuilder()).append(cheminrepdest).append("\\").append(nomdest10).toString());
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile10InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest10).append("------------").toString());
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile20InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest20).append("------------").toString());
                                int traite20 = util.copy(pathFile20InDestLocale, (new StringBuilder()).append(cheminrepdest).append("\\").append(nomdest20).toString());
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile20InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest20).append("------------").toString());
                                if(traite10 == 1 && traite20 == 1)
                                {
                                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                                    File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("CodeVPTraite"))).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                                    list[i].renameTo(fileTraite);
                                }
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                util.copy(pathFileInTravail, pathFileInTraite);
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                                continue;
                            }
                            if(file10InDestLocale.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file10InDestLocale).append("------------").toString());
                                file10InDestLocale.delete();
                                logger.info("------------Fichier supprim\351------------");
                            }
                            if(file20InDestLocale.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file20InDestLocale).append("------------").toString());
                                file20InDestLocale.delete();
                                logger.info("------------Fichier supprim\351------------");
                            }
                            if(fichierTravail.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                                fichierTravail.delete();
                                logger.info("------------Fichier supprim\351------------");
                                logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).toString());
                            }
                        }
                        catch(Exception e)
                        {
                            logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                        }
                        continue;
                    }
                    logger.info((new StringBuilder()).append("------------Control 10 et 20 du fichier").append(pathFileInTravail).append(" est faux------------").toString());
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                    if(fichierTravail.exists())
                        fichierTravail.delete();
                    logger.info("------------Fichier supprim\351------------");
                    continue;
                }
                if(dateJourFile.equals(dateSystem) && codeBct != null && codeValeur.equals("41") && isdatevalide)
                {
                    try
                    {
                        codeEnrGlobal41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("41").toString()));
                        codeEnrDetailPres41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("Pres41").toString()));
                        codeEnrDetailRejet41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("Rejet41").toString()));
                    }
                    catch(InvalidPropertiesFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    if(codeEnrGlobal41 == null || codeEnrDetailPres41 == null || codeEnrDetailRejet41 == null){
                        continue;
                    }
                    if(listFichier.size() == 0){
                        getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                    }
                    logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Control 41 du fichier").append(pathFileInTravail).append("------------").toString());
                    Map map = new HashMap();
                    map.putAll(file41Control(pathFileInTravail, codeValeur, codeEnrGlobal41, codeEnrDetailPres41, codeEnrDetailRejet41));
                    String montantDetailPres = map.get("montantDetailPres").toString();
                    String montantDetailRejet = map.get("montantDetailRejet").toString();
                    String nombreDetailPres = map.get("nombreDetailPres").toString();
                    String nombreDetailRejet = map.get("nombreDetailRejet").toString();
                    if(map.get("test").toString().equals("true"))
                    {
                        logger.info((new StringBuilder()).append("------------Control 41 du fichier").append(pathFileInTravail).append(" est vrai------------").toString());
                        nomdest4121 = (new StringBuilder()).append("03-").append(codeBct).append("-").append(codeValeur).append("-21").append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        nomdest4122 = (new StringBuilder()).append("03-").append(codeBct).append("-").append(codeValeur).append("-22").append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        String pathFile4121InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest4121).toString();
                        String pathFile4122InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest4122).toString();
                        File file4121InDestLocale = new File(pathFile4121InDestLocale);
                        File file4122InDestLocale = new File(pathFile4122InDestLocale);
                        try
                        {
                            logger.info((new StringBuilder()).append("------------D\351falcation du fichier ").append(pathFileInTravail).append(" en ").append(pathFile4121InDestLocale).append(" et ").append(pathFile4122InDestLocale).append("------------").toString());
                            defalquer41(pathFileInTravail, pathFile4121InDestLocale, pathFile4122InDestLocale, montantDetailPres, montantDetailRejet, nombreDetailPres, nombreDetailRejet);
                            logger.info((new StringBuilder()).append("------------Fin D\351falcation du fichier ").append(pathFileInTravail).append(" en ").append(pathFile4121InDestLocale).append(" et ").append(pathFile4122InDestLocale).append("------------").toString());
                            if(!file4121InDestLocale.exists() || !file4122InDestLocale.exists() || !fichierTravail.exists())
                                continue;
                            int nbreligne41 = getNombreLignes(pathFileInTravail, "41");
                            int nbreligne4121 = getNombreLignes(pathFile4121InDestLocale, "41");
                            int nbreligne4122 = getNombreLignes(pathFile4122InDestLocale, "41");
                            if(nbreligne41 == (nbreligne4121 + nbreligne4122) - 1)
                            {
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile4121InDestLocale).append(" vers ").append(cheminreptest).append("\\").append(nomdest4121).append("------------").toString());
                                int traite4121 = util.copy(pathFile4121InDestLocale, (new StringBuilder()).append(cheminreptest).append("\\").append(nomdest4121).toString());
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile4121InDestLocale).append(" vers ").append(cheminreptest).append("\\").append(nomdest4121).append("------------").toString());
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile4122InDestLocale).append(" vers ").append(cheminrepdest2).append("\\").append(nomdest4122).append("------------").toString());
                                int traite4122 = util.copy(pathFile4122InDestLocale, (new StringBuilder()).append(cheminrepdest2).append("\\").append(nomdest4122).toString());
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile4122InDestLocale).append(" vers ").append(cheminrepdest2).append("\\").append(nomdest4122).append("------------").toString());
                                if(traite4121 == 1 && traite4122 == 1)
                                {
                                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                                    File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("Code41Traite"))).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                                    list[i].renameTo(fileTraite);
                                }
                                logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                util.copy(pathFileInTravail, pathFileInTraite);
                                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                                continue;
                            }
                            if(file4121InDestLocale.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file4121InDestLocale).append("------------").toString());
                                file4121InDestLocale.delete();
                                logger.info("------------Fichier supprim\351------------");
                            }
                            if(file4122InDestLocale.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file4122InDestLocale).append("------------").toString());
                                file4122InDestLocale.delete();
                                logger.info("------------Fichier supprim\351------------");
                            }
                            if(fichierTravail.exists())
                            {
                                logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                                fichierTravail.delete();
                                logger.info("------------Fichier supprim\351------------");
                                logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).toString());
                            }
                        }
                        catch(Exception e)
                        {
                            logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                        }
                        continue;
                    }
                    logger.info((new StringBuilder()).append("------------Control 41 du fichier").append(pathFileInTravail).append(" est faux------------").toString());
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                    if(fichierTravail.exists())
                        fichierTravail.delete();
                    logger.info("------------Fichier supprim\351------------");
                    continue;
                }
                if(dateJourFile.equals(dateSystem) && codeBct != null && codeValeur.equals("30") && isdatevalide)
                {
                    try
                    {
                        typeValeur30 = parametres.getTypeValeur(String.valueOf(30));
                        codeEnrGlobal30 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("30").toString()));
                        codeEnrDetail30 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("30").toString()));
                    }
                    catch(InvalidPropertiesFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    if(typeValeur30 == null || codeEnrGlobal30 == null || codeEnrDetail30 == null){
                        continue;
                    }
                    if(listFichier.size() == 0){
                        getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                    }
                    logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 1);
                    logger.info((new StringBuilder()).append("------------Control 30 du fichier").append(pathFileInTravail).append("------------").toString());
                    if(fileControl(pathFileInTravail, codeValeur, codeEnrGlobal30, codeEnrDetail30).booleanValue())
                    {
                        logger.info((new StringBuilder()).append("------------Control 30 du fichier").append(pathFileInTravail).append(" est vrai------------").toString());
                        nomdest30 = (new StringBuilder()).append("03-").append(codeBct).append("-").append(typeValeur30).append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        String pathFile30InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest30).toString();
                        File file30InDestLocale = new File(pathFile30InDestLocale);
                        try
                        {
                            logger.info((new StringBuilder()).append("------------Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile30InDestLocale).append("------------").toString());
                            Traiter30(pathFileInTravail, pathFile30InDestLocale);
                            logger.info((new StringBuilder()).append("------------Fin Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile30InDestLocale).append("------------").toString());
                            if(file30InDestLocale.exists() && fichierTravail.exists())
                            {
                                int nbreligne30Orig = getNombreLignes(pathFileInTravail, "30");
                                int nbreligne30Trai = getNombreLignes(pathFile30InDestLocale, "30");
                                if(nbreligne30Orig == nbreligne30Trai)
                                {
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile30InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest30).append("------------").toString());
                                    int traite30 = util.copy(pathFile30InDestLocale, (new StringBuilder()).append(cheminrepdest).append("\\").append(nomdest30).toString());
                                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile30InDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest30).append("------------").toString());
                                    if(traite30 == 1)
                                    {
                                        getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                                        File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("Code30Traite"))).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                                        list[i].renameTo(fileTraite);
                                    }
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                    util.copy(pathFileInTravail, pathFileInTraite);
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                } else
                                {
                                    if(file30InDestLocale.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file30InDestLocale).append("------------").toString());
                                        file30InDestLocale.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                    if(fichierTravail.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                                        fichierTravail.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                }
                            }
                            logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                        }
                        catch(Exception e)
                        {
                            logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                        }
                        continue;
                    }
                    logger.info((new StringBuilder()).append("------------Control 30 du fichier").append(pathFileInTravail).append(" est faux------------").toString());
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                    if(fichierTravail.exists())
                        fichierTravail.delete();
                    logger.info("------------Fichier supprim\351------------");
                    continue;
                }
                if(dateJourFile.equals(dateSystem) && codeBct != null && (codeValeur.equals("81") || codeValeur.equals("82") || codeValeur.equals("83") || codeValeur.equals("84")) && isdatevalide)
                {
                    try
                    {
                        typeValeur8x = parametres.getTypeValeur(String.valueOf(codeValeur));
                        codeEnrGlobal8x = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("8x").toString()));
                        codeEnrDetail8x = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("8x").toString()));
                    }
                    catch(InvalidPropertiesFormatException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch(IOException e1)
                    {
                        e1.printStackTrace();
                    }
                    if(typeValeur8x == null || codeEnrGlobal8x == null || codeEnrDetail8x == null){
                        continue;
                    }
                    if(listFichier.size() == 0){
                        getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                    }
                    logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                    getCodeBctAgence.updateFichier(list[i].getName(), datePec, 1);
                    logger.info((new StringBuilder()).append("------------Control du fichier").append(pathFileInTravail).append("------------").toString());
                    if(fileControl8x(pathFileInTravail, codeValeur, codeEnrGlobal8x, codeEnrDetail8x).booleanValue())
                    {
                        logger.info((new StringBuilder()).append("------------Control du fichier").append(pathFileInTravail).append(" est vrai------------").toString());
                        nomdest8x = (new StringBuilder()).append("03-").append(codeBct).append("-").append(typeValeur8x).append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                        String pathFile8xInDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest8x).toString();
                        File file8xInDestLocale = new File(pathFile8xInDestLocale);
                        try
                        {
                            logger.info((new StringBuilder()).append("------------Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile8xInDestLocale).append("------------").toString());
                            Traiter8x(pathFileInTravail, pathFile8xInDestLocale, codeValeur);
                            logger.info((new StringBuilder()).append("------------Fin Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile8xInDestLocale).append("------------").toString());
                            if(file8xInDestLocale.exists() && fichierTravail.exists())
                            {
                                int nbreligne8xOrig = getNombreLignes(pathFileInTravail, codeValeur);
                                int nbreligne8xTrai = getNombreLignes(pathFile8xInDestLocale, codeValeur);
                                if(nbreligne8xOrig == nbreligne8xTrai)
                                {
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile8xInDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest8x).append("------------").toString());
                                    int traite8x = util.copy(pathFile8xInDestLocale, (new StringBuilder()).append(cheminrepdest).append("\\").append(nomdest8x).toString());
                                    logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile8xInDestLocale).append(" vers ").append(cheminrepdest).append("\\").append(nomdest8x).append("------------").toString());
                                    if(traite8x == 1)
                                    {
                                        getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                                        File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("Code8xTraite"))).append(list[i].getName().substring(1, 2)).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                                        list[i].renameTo(fileTraite);
                                    }
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                    util.copy(pathFileInTravail, pathFileInTraite);
                                    logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                                } else
                                {
                                    if(file8xInDestLocale.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file8xInDestLocale).append("------------").toString());
                                        file8xInDestLocale.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                    if(fichierTravail.exists())
                                    {
                                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                                        fichierTravail.delete();
                                        logger.info("------------Fichier supprim\351------------");
                                    }
                                }
                            }
                            logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                        }
                        catch(Exception e)
                        {
                            logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                        }
                        continue;
                    }
                    logger.info((new StringBuilder()).append("------------Control  du fichier").append(pathFileInTravail).append(" est faux------------").toString());
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append("------------").toString());
                    logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                    if(fichierTravail.exists())
                        fichierTravail.delete();
                    logger.info("------------Fichier supprim\351------------");
                    continue;
                }
                if(!dateJourFile.equals(dateSystem) || codeBct == null || !codeValeur.equals("47") || !isdatevalide){
                    continue;
                }
                try
                {
                    codeEnrGlobal41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrGlobal).append("41").toString()));
                    codeEnrDetailPres41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("Pres41").toString()));
                    codeEnrDetailRejet41 = parametres.getTypeValeur(String.valueOf((new StringBuilder()).append(codeEnrDetail).append("Rejet41").toString()));
                }
                catch(InvalidPropertiesFormatException e1)
                {
                    e1.printStackTrace();
                }
                catch(IOException e1)
                {
                    e1.printStackTrace();
                }
                if(codeEnrGlobal41 == null || codeEnrDetailPres41 == null || codeEnrDetailRejet41 == null){
                    continue;
                }
                if(listFichier.size() == 0){
                    getCodeBctAgence.ajouterFichier(list[i].getName(), codeStrc, datePec, 0);
                }
                logger.info((new StringBuilder()).append("------------Prise en charge du fichier:").append(list[i].getAbsolutePath()).append("------------").toString());
                logger.info((new StringBuilder()).append("------------Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                util.copy(list[i].getAbsolutePath(), pathFileInTravail);
                logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(list[i].getAbsolutePath()).append(" vers ").append(pathFileInTravail).append("------------").toString());
                nomdest47 = (new StringBuilder()).append("03-").append(codeBct).append("-").append("40").append("-22").append("-0001-").append(jj).append(mm).append("20").append(aa).append("-").append(util.timeForNameFile()).append("-788.ENV").toString();
                String pathFile47InDestLocale = (new StringBuilder()).append(cheminrepdestlocale).append("\\").append(nomdest47).toString();
                File file47InDestLocale = new File(pathFile47InDestLocale);
                try
                {
                    logger.info((new StringBuilder()).append("------------Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile47InDestLocale).append("------------").toString());
                    Traiter47(pathFileInTravail, pathFile47InDestLocale);
                    logger.info((new StringBuilder()).append("------------Fin Traitement et transfert du fichier").append(pathFileInTravail).append(" vers ").append(pathFile47InDestLocale).append("------------").toString());
                    if(file47InDestLocale.exists() && fichierTravail.exists())
                    {
                        logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFile47InDestLocale).append(" vers ").append(cheminrepdest2).append("\\").append(nomdest47).append("------------").toString());
                        int traite47 = util.copy(pathFile47InDestLocale, (new StringBuilder()).append(cheminrepdest2).append("\\").append(nomdest47).toString());
                        logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFile47InDestLocale).append(" vers ").append(cheminrepdest2).append("\\").append(nomdest47).append("------------").toString());
                        if(traite47 == 1)
                        {
                            getCodeBctAgence.updateFichier(list[i].getName(), datePec, 2);
                            File fileTraite = new File((new StringBuilder()).append(cheminrepsource).append("\\").append(parametres.getTypeValeur(String.valueOf("Code47Traite"))).append(jj).append(mm).append(aa).append(".").append(codeStrc).toString());
                            list[i].renameTo(fileTraite);
                        }
                        logger.info((new StringBuilder()).append("------------Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                        util.copy(pathFileInTravail, pathFileInTraite);
                        logger.info((new StringBuilder()).append("------------Fin Copie du fichier").append(pathFileInTravail).append(" vers ").append(pathFileInTraite).append("------------").toString());
                        logger.info((new StringBuilder()).append("------------Succ\350s de traitement du fichier ").append(fichierTravail).append("------------").toString());
                        continue;
                    }
                    if(file47InDestLocale.exists())
                    {
                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file47InDestLocale).append("------------").toString());
                        file47InDestLocale.delete();
                        logger.info("------------Fichier supprim\351------------");
                    }
                    if(fichierTravail.exists())
                    {
                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                        fichierTravail.delete();
                        logger.info("------------Fichier supprim\351------------");
                        logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).toString());
                    }
                    continue;
                }
                catch(Exception e)
                {
                    if(file47InDestLocale.exists())
                    {
                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(file47InDestLocale).append("------------").toString());
                        file47InDestLocale.delete();
                        logger.info("------------Fichier supprim\351------------");
                    }
                    if(fichierTravail.exists())
                    {
                        logger.info((new StringBuilder()).append("------------Suppression du fichier").append(fichierTravail).append("------------").toString());
                        fichierTravail.delete();
                        logger.info("------------Fichier supprim\351------------");
                        logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).toString());
                    }
                    logger.info((new StringBuilder()).append("------------Echec de traitement du fichier ").append(fichierTravail).append(" ").append(e).toString());
                    i++;
                }
            }

            list = null;
        }
    }

    public int getNombreLignes(String cheminFichier, String codeVal)
    {
        String codeValeur = "";
        int nbre = 0;
        int numeroLigne = 0;
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(new File(cheminFichier));
            logger.info((new StringBuilder()).append("--getNombreLignes- D\351marrage recherche nombre des lignes du fichier ").append(cheminFichier).toString());
            do
            {
                if(!scanner.hasNextLine())
                    break;
                String line = scanner.nextLine();
                try
                {
                    numeroLigne++;
                    codeValeur = line.substring(1, 3);
                    if(codeValeur.equals(codeVal))
                        nbre++;
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--getNombreLignes--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier).append(" : ").append(e).toString());
                }
            } while(true);
            scanner.close();
            logger.info((new StringBuilder()).append("--getNombreLignes--Fin recherche nombre des lignes du fichier ").append(cheminFichier).toString());
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--getNombreLignes--Probl\350me le fichier ").append(cheminFichier).append(" n'existe pas!!! : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--getNombreLignes--Probl\350me lors de lecture du fichier ").append(cheminFichier).append(" : ").append(e).toString());
        }
        return nbre;
    }

    /*public Boolean file10Control(String cheminfichier, String codeVal, String typeValGlobal, String typeValDetail)
    {
        Long montantTotal;
        Long montantDetail;
        int nombreTotal;
        int nombreDetail;
        int numeroLigne;
        Scanner scanner;
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        String nbreTotal = "";
        montantTotal = new Long(0L);
        montantDetail = new Long(0L);
        nombreTotal = 0;
        nombreDetail = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Boolean boolean1;
        scanner = new Scanner(new File(cheminfichier));
        logger.info((new StringBuilder()).append("--Controle 10--Ouverture du fichier ").append(cheminfichier).append(" en lecture").toString());
       
            if(!scanner.hasNextLine())
                break;
            try
            {
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                String code = ligne.substring(26, 28);
                String montant = ligne.substring(28, 43);
                String nbreTotal = ligne.substring(43, 53);
                if(codeValeur.equals(codeVal)){
                    if(typeValeur.equals(typeValGlobal))
                    {
                        montantTotal = new Long(montant);
                        nombreTotal = Integer.parseInt(nbreTotal);
                    } else{
                    if(typeValeur.equals(typeValDetail) && code.equals("00"))
                    {
                        montantDetail = Long.valueOf(montantDetail.longValue() + (new Long(montant)).longValue());
                        nombreDetail++;
                    }
                    }
            }
            }catch(FileNotFoundException ee)
            {
                logger.error((new StringBuilder()).append("--Controle 10--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichier).append(" : ").append(e).toString());
            }
         
        if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail){
        	return true;
        }
        else{
        	return false;
        }
        }
        logger.error((new StringBuilder()).append("--Controle 10--Probl\350me le fichier ").append(cheminfichier).append(" n'existe pas!!!! : ").append(boolean1).toString());
        finally{
        	scanner.close();
            logger.info((new StringBuilder()).append("--Controle 10--Fermeture du fichier ").append(cheminfichier).append(" en lecture").toString());

        }

    }*/
    public Boolean file10Control(String cheminfichier, String codeVal,String typeValGlobal, String typeValDetail) {

        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        String nbreTotal = "";
        Long montantTotal = new Long(0);
        Long montantDetail = new Long(0);
        int nombreTotal = 0;
        int nombreDetail = 0;
        int numeroLigne = 0;
        Scanner scanner = null;
        try {


            scanner = new Scanner(new File(cheminfichier));
            logger.info((new StringBuilder()).append("--Controle 10--Ouverture du fichier " + 
                        cheminfichier + " en lecture"));


            // On boucle sur chaque ligne detecté
            while (scanner.hasNextLine()) {
                try {
                    String ligne = scanner.nextLine();
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    code = ligne.substring(26, 28);
                    montant = ligne.substring(28, 43);
                    nbreTotal = ligne.substring(43, 53);


                    if (codeValeur.equals(codeVal)) {
                        if (typeValeur.equals(typeValGlobal)) {

                            montantTotal = new Long(montant);
                            nombreTotal = Integer.parseInt(nbreTotal);

                        } else if (typeValeur.equals(typeValDetail) && 
                                   code.equals("00")) {
                            montantDetail = montantDetail + new Long(montant);
                            nombreDetail++;

                        }
                    }
                } catch (Exception e) {
                    logger.error((new StringBuilder()).append("--Controle 10--Problème lors du lecture de la ligne " + 
                                 numeroLigne + " dans le fichier " + 
                                 cheminfichier + " : " + e));
                }
            }

            if ((montantTotal.longValue() == montantDetail.longValue()) && 
                (nombreTotal == nombreDetail)) {
                return true;
            } else {
                return false;
            }


        } catch (FileNotFoundException e) {
            logger.error((new StringBuilder()).append("--Controle 10--Problème le fichier " + 
                         cheminfichier + " n'existe pas!!!! : " + e));
            return false;


        } finally {
            scanner.close();
            logger.info((new StringBuilder()).append("--Controle 10--Fermeture du fichier " + 
                        cheminfichier + " en lecture"));
        }

    }

    /*public Boolean file20Control(String cheminfichier, String codeVal, String typeValGlobal, String typeValDetail)
    {
        Long montantTotal;
        Long montantDetail;
        int nombreTotal;
        int nombreDetail;
        int numeroLigne;
        Scanner scanner;
        String typeValeur = "";
        String codeValeur = "";
        String montant = "";
        String nbreTotal = "";
        montantTotal = new Long(0L);
        montantDetail = new Long(0L);
        nombreTotal = 0;
        nombreDetail = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Boolean boolean1;
        scanner = new Scanner(new File(cheminfichier));
        logger.info((new StringBuilder()).append("--Controle 20--Ouverture du fichier ").append(cheminfichier).append(" en lecture").toString());
        do
        {
            if(!scanner.hasNextLine())
                break;
            try
            {
                numeroLigne++;
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                String montant = ligne.substring(26, 41);
                String nbreTotal = ligne.substring(41, 51);
                if(codeValeur.equals(codeVal))
                    if(typeValeur.equals(typeValGlobal))
                    {
                        montantTotal = new Long(montant);
                        nombreTotal = Integer.parseInt(nbreTotal);
                    } else
                    if(typeValeur.equals(typeValDetail))
                    {
                        montantDetail = Long.valueOf(montantDetail.longValue() + (new Long(montant)).longValue());
                        nombreDetail++;
                    }
            }
            // Misplaced declaration of an exception variable
            catch(FileNotFoundException e)
            {
                logger.error((new StringBuilder()).append("--Controle 20--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichier).append(" : ").append(e).toString());
            }
        } while(true);
        if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail)
            break MISSING_BLOCK_LABEL_316;
        boolean1 = Boolean.valueOf(true);
        return boolean1;
        boolean1 = Boolean.valueOf(false);
        return boolean1;
        boolean1;
        Boolean boolean2;
        logger.error((new StringBuilder()).append("--Controle 20--Probl\350me le fichier ").append(cheminfichier).append(" n'existe pas!!!! : ").append(boolean1).toString());
        boolean2 = Boolean.valueOf(false);
        return boolean2;
        local;
        scanner.close();
        logger.info((new StringBuilder()).append("--Controle 20--Fermeture du fichier ").append(cheminfichier).append(" en lecture").toString());
        JVM INSTR ret 18;
    }*/
public Boolean file20Control(String cheminfichier, String codeVal,String typeValGlobal, String typeValDetail) {

    String typeValeur = "";
    String codeValeur = "";
    String montant = "";
    String nbreTotal = "";
    Long montantTotal = new Long(0);
    Long montantDetail = new Long(0);
    int nombreTotal = 0;
    int nombreDetail = 0;
    int numeroLigne = 0;
    Scanner scanner = null;

    try {

        scanner = new Scanner(new File(cheminfichier));
        logger.info((new StringBuilder()).append("--Controle 20--Ouverture du fichier " + 
                    cheminfichier + " en lecture"));


        // On boucle sur chaque champ detecté
        while (scanner.hasNextLine()) {
            try {
                numeroLigne++;
                String ligne = scanner.nextLine();
                codeValeur = ligne.substring(1, 3);
                typeValeur = ligne.substring(21, 23);

                montant = ligne.substring(26, 41);
                nbreTotal = ligne.substring(41, 51);

                if (codeValeur.equals(codeVal)) {
                    if (typeValeur.equals(typeValGlobal)) {
                        montantTotal = new Long(montant);
                        nombreTotal = Integer.parseInt(nbreTotal);

                    } else if (typeValeur.equals(typeValDetail)) {
                        montantDetail = montantDetail + new Long(montant);
                        nombreDetail++;
                    }
                }
            } catch (Exception e) {
                logger.error("--Controle 20--Problème lors du lecture de la ligne " + 
                             numeroLigne + " dans le fichier " + 
                             cheminfichier + " : " + e);
            }
        }

//        if ((montantTotal.longValue() == montantDetail) && 
//            (nombreTotal == nombreDetail)) {
        if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail){
            return false;
        } else {
            return true;
        }


    } catch (FileNotFoundException e) {

        logger.error((new StringBuilder()).append("--Controle 20--Problème le fichier " + 
                     cheminfichier + " n'existe pas!!!! : " + e));
        return false;
    } finally {
        scanner.close();

        logger.info((new StringBuilder()).append("--Controle 20--Fermeture du fichier " + 
                    cheminfichier + " en lecture"));
    }
}
    public String modifierLigne(String ligne, String codeValeur, String typeValeur, String nouvTypeVal)
    {
        String p1 = ligne.substring(0, 6);
        String p2 = "   ";
        String p3 = ligne.substring(9, 21);
        String p5 = "   ";
        String p7 = ligne.substring(9, 20);
        String p8 = ligne.substring(1, 6);
        if(codeValeur.equals("10"))
        {
            if(typeValeur.equals("21"))
            {
                String p4 = ligne.substring(23, 102);
                String p6 = ligne.substring(105, ligne.length());
                ligne = (new StringBuilder()).append(p1).append(p2).append(p3).append(nouvTypeVal).append(p4).append(p5).append(p6).toString();
            } else
            if(typeValeur.equals("19"))
            {
                String p4 = ligne.substring(23, ligne.length());
                ligne = (new StringBuilder()).append(p1).append(p2).append(p3).append(nouvTypeVal).append(p4).toString();
            }
        } else
        if(codeValeur.equals("20"))
            if(typeValeur.equals("22"))
            {
                String p4 = ligne.substring(23, 70);
                String p6 = ligne.substring(73, ligne.length());
                ligne = (new StringBuilder()).append("1").append(p8).append(p2).append(p3).append(nouvTypeVal).append(p4).append(p5).append(p6).toString();
            } else
            if(typeValeur.equals("29"))
            {
                String p4 = ligne.substring(23, ligne.length());
                ligne = (new StringBuilder()).append(p1).append(p2).append(p7).append("1").append(nouvTypeVal).append(p4).toString();
            }
        return ligne;
    }

    public void defalquerVP(String cheminfichierVP, String cheminfichier10, String cheminfichier20)
    {
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedWriter bufferedWriter10 = null;
        BufferedWriter bufferedWriter20 = null;
        FileWriter fstream10 = null;
        FileWriter fstream20 = null;
        Scanner scanner1 = null;
        Scanner scanner2 = null;
        Scanner scanner3 = null;
        Scanner scanner4 = null;
        try
        {
            scanner1 = new Scanner(new File(cheminfichierVP));
            scanner2 = new Scanner(new File(cheminfichierVP));
            scanner3 = new Scanner(new File(cheminfichierVP));
            scanner4 = new Scanner(new File(cheminfichierVP));
            logger.info((new StringBuilder()).append("--defalquer--Ouverture du fichier ").append(cheminfichierVP).append(" en lecture").toString());
            fstream10 = new FileWriter(cheminfichier10);
            bufferedWriter10 = new BufferedWriter(fstream10);
            logger.info((new StringBuilder()).append("--defalquer--Ouverture du fichier ").append(cheminfichier10).append(" en \351criture").toString());
            do
            {
                if(!scanner1.hasNextLine())
                    break;
                String line = scanner1.nextLine();
                numeroLigne++;
                try
                {
                    codeValeur = line.substring(1, 3);
                    typeValeur = line.substring(21, 23);
                    if(codeValeur.equals("10") && typeValeur.equals("19"))
                    {
                        logger.info((new StringBuilder()).append("--defalquer--D\351marrage d'\351criture du fichier ").append(cheminfichier10).toString());
                        line = modifierLigne(line, "10", "19", "11");
                        bufferedWriter10.write(line);
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichierVP).append(" : ").append(e).toString());
                }
            } while(true);
            scanner1.close();
            numeroLigne = 0;
            do
            {
                if(!scanner2.hasNextLine())
                    break;
                String line = scanner2.nextLine();
                numeroLigne++;
                try
                {
                    codeValeur = line.substring(1, 3);
                    typeValeur = line.substring(21, 23);
                    if(codeValeur.equals("10") && typeValeur.equals("21"))
                    {
                        bufferedWriter10.newLine();
                        line = modifierLigne(line, "10", "21", "21");
                        bufferedWriter10.write(line);
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichierVP).append(" : ").append(e).toString());
                }
            } while(true);
            scanner2.close();
            bufferedWriter10.flush();
            bufferedWriter10.close();
            logger.info((new StringBuilder()).append("--defalquer--Fin d'\351criture du fichier ").append(cheminfichier10).toString());
            fstream20 = new FileWriter(cheminfichier20);
            bufferedWriter20 = new BufferedWriter(fstream20);
            logger.info((new StringBuilder()).append("--defalquer--Ouverture du fichier ").append(cheminfichier20).append(" en \351criture").toString());
            numeroLigne = 0;
            do
            {
                if(!scanner3.hasNextLine())
                    break;
                String line = scanner3.nextLine();
                numeroLigne++;
                try
                {
                    codeValeur = line.substring(1, 3);
                    typeValeur = line.substring(21, 23);
                    if(codeValeur.equals("20") && typeValeur.equals("29"))
                    {
                        logger.info((new StringBuilder()).append("--defalquer--D\351marrage d'\351criture du fichier ").append(cheminfichier20).toString());
                        line = modifierLigne(line, "20", "29", "12");
                        bufferedWriter20.write(line);
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichierVP).append(" : ").append(e).toString());
                }
            } while(true);
            scanner3.close();
            numeroLigne = 0;
            do
            {
                if(!scanner4.hasNextLine())
                    break;
                String line = scanner4.nextLine();
                numeroLigne++;
                try
                {
                    codeValeur = line.substring(1, 3);
                    typeValeur = line.substring(21, 23);
                    if(codeValeur.equals("20") && typeValeur.equals("22"))
                    {
                        bufferedWriter20.newLine();
                        line = modifierLigne(line, "20", "22", "22");
                        bufferedWriter20.write(line);
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminfichierVP).append(" : ").append(e).toString());
                }
            } while(true);
            scanner4.close();
            bufferedWriter20.flush();
            bufferedWriter20.close();
            logger.info((new StringBuilder()).append("--defalquer--Fin d'\351criture du fichier ").append(cheminfichier20).toString());
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--defalquer--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--defalquer--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
    }

    /*public Map file41Control(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetailPres, String typeValDetailRejet)
    {
        Map map;
        Long montantTotal;
        Long montantTotalRejet;
        Long montantDetailPres;
        Long montantDetailRejet;
        int nombreTotal;
        int nombreDetailPres;
        int nombreDetailRejet;
        int numeroLigne;
        Scanner scanner;
        map = new HashMap();
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        montantTotal = new Long(0L);
        montantTotalRejet = new Long(0L);
        montantDetailPres = new Long(0L);
        montantDetailRejet = new Long(0L);
        nombreTotal = 0;
        nombreDetailPres = 0;
        nombreDetailRejet = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Map map1;
        scanner = new Scanner(new File(cheminFichier));
        logger.info((new StringBuilder()).append("--Controle 41--Ouverture du fichier ").append(cheminFichier).append(" en lecture").toString());
        do
        {
            if(!scanner.hasNextLine())
                break;
            try
            {
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                String code = ligne.substring(26, 28);
                String montant = ligne.substring(28, 43);
                if(codeValeur.equals(codeVal))
                    if(typeValeur.equals(typeValGlobal))
                    {
                        montantTotal = new Long(montant);
                        montantTotalRejet = new Long(ligne.substring(62, 77));
                        nombreTotal = Integer.parseInt(ligne.substring(43, 53));
                    } else
                    if(typeValeur.equals(typeValDetailPres) && code.equals("00"))
                    {
                        montantDetailPres = Long.valueOf(montantDetailPres.longValue() + (new Long(montant)).longValue());
                        nombreDetailPres++;
                    } else
                    if(typeValeur.equals(typeValDetailRejet) && code.equals("00"))
                    {
                        montantDetailRejet = Long.valueOf(montantDetailRejet.longValue() + (new Long(montant)).longValue());
                        nombreDetailRejet++;
                    }
            }
            // Misplaced declaration of an exception variable
            catch(FileNotFoundException e)
            {
                logger.error((new StringBuilder()).append("--Controle 41--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier).append(" : ").append(e).toString());
            }
        } while(true);
        if(montantTotal.longValue() != montantDetailPres.longValue() + montantDetailRejet.longValue() || nombreTotal != nombreDetailPres + nombreDetailRejet || montantTotalRejet.longValue() != montantDetailRejet.longValue())
            break MISSING_BLOCK_LABEL_518;
        map.put("test", Boolean.valueOf(true));
        map.put("montantDetailPres", montantDetailPres);
        map.put("montantDetailRejet", montantDetailRejet);
        map.put("nombreDetailPres", Integer.valueOf(nombreDetailPres));
        map.put("nombreDetailRejet", Integer.valueOf(nombreDetailRejet));
        map1 = map;
        return map1;
        map.put("test", Boolean.valueOf(false));
        map1 = map;
        return map1;
        map1;
        Map map2;
        map.put("test", Boolean.valueOf(false));
        logger.error((new StringBuilder()).append("--Controle 41--Probl\350me le fichier ").append(cheminFichier).append(" n'existe pas!!!! : ").append(map1).toString());
        map2 = map;
        return map2;
        local;
        logger.info((new StringBuilder()).append("--Controle 41--Fermeture du fichier ").append(cheminFichier).append(" en lecture").toString());
        if(scanner != null)
            scanner.close();
        JVM INSTR ret 23;
    }*/
/************************** Partie Traitements Fichiers 41 **********************/
    
    public Map file41Control(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetailPres, String typeValDetailRejet) {

        Map map = new HashMap();
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        Long montantTotal = new Long(0);
        Long montantTotalRejet = new Long(0);
        Long montantDetailPres = new Long(0);
        Long montantDetailRejet = new Long(0);
        int nombreTotal = 0;
        int nombreDetailPres = 0;
        int nombreDetailRejet = 0;
        int numeroLigne = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(cheminFichier));
            logger.info((new StringBuilder()).append("--Controle 41--Ouverture du fichier " + 
                        cheminFichier + " en lecture"));

            // On boucle sur chaque ligne detecté
            while (scanner.hasNextLine()) {
                try {
                    String ligne = scanner.nextLine();
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    code = ligne.substring(26, 28);
                    montant = ligne.substring(28, 43);

                    if (codeValeur.equals(codeVal)) {
                        if (typeValeur.equals(typeValGlobal)) {

                            montantTotal = new Long(montant);
                            montantTotalRejet = 
                                    new Long(ligne.substring(62, 77));
                            nombreTotal = 
                                    Integer.parseInt(ligne.substring(43, 53));

                        } else if (typeValeur.equals(typeValDetailPres) && 
                                   code.equals("00")) {
                            montantDetailPres = 
                                    montantDetailPres + new Long(montant);
                            nombreDetailPres++;

                        } else if (typeValeur.equals(typeValDetailRejet) && 
                                   code.equals("00")) {
                            montantDetailRejet = 
                                    montantDetailRejet + new Long(montant);
                            nombreDetailRejet++;

                        }

                    }
                } catch (Exception e) {
                    logger.error((new StringBuilder()).append("--Controle 41--Problème lors du lecture de la ligne " + 
                                 numeroLigne + " dans le fichier " + 
                                 cheminFichier + " : " + e));
                }
            }

            if ((montantTotal.longValue() == 
                 (montantDetailPres.longValue() + montantDetailRejet.longValue())) && 
                (nombreTotal == nombreDetailPres + nombreDetailRejet) && 
                (montantTotalRejet.longValue() == montantDetailRejet.longValue())) {

                map.put("test", true);
                map.put("montantDetailPres", montantDetailPres);
                map.put("montantDetailRejet", montantDetailRejet);
                map.put("nombreDetailPres", nombreDetailPres);
                map.put("nombreDetailRejet", nombreDetailRejet);
                return map;


            } else {

                map.put("test", false);

                return map;
            }

        } catch (FileNotFoundException e) {

            map.put("test", false);
            logger.error((new StringBuilder()).append("--Controle 41--Problème le fichier " + 
                         cheminFichier + " n'existe pas!!!! : " + e));
            return map;


        } finally {
            logger.info((new StringBuilder()).append("--Controle 41--Fermeture du fichier " + 
                        cheminFichier + " en lecture"));
            if (scanner != null) {
                scanner.close();

            }
        }

    }
    public void defalquer41(String cheminFichier41, String cheminFichier4121, String cheminFichier4122, String montantDetailPres, String montantDetailRejet, String nombreDetailPres, String nombreDetailRejet)
    {
        String ligne = null;
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedReader bufferedReader41 = null;
        BufferedWriter bufferedWriter4121 = null;
        BufferedWriter bufferedWriter4122 = null;
        try
        {
            bufferedReader41 = new BufferedReader(new FileReader(cheminFichier41));
            logger.info((new StringBuilder()).append("--defalquer41--Ouverture du fichier ").append(cheminFichier41).append(" en lecture").toString());
            bufferedWriter4121 = new BufferedWriter(new FileWriter(cheminFichier4121, true));
            logger.info((new StringBuilder()).append("--defalquer41--Ouverture du fichier ").append(cheminFichier4121).append(" en \351criture").toString());
            bufferedWriter4122 = new BufferedWriter(new FileWriter(cheminFichier4122, true));
            logger.info((new StringBuilder()).append("--defalquer41--Ouverture du fichier ").append(cheminFichier4122).append(" en \351criture").toString());
            do
            {
                if((ligne = bufferedReader41.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("41") && typeValeur.equals("11"))
                    {
                        logger.info((new StringBuilder()).append("--defalquer41--D\351marrage d'\351criture du fichier ").append(cheminFichier4121).toString());
                        String ligneGlobalePres = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, 28)).append(StrHandler.lpad(montantDetailPres, '0', 15)).append(StrHandler.lpad(nombreDetailPres, '0', 10)).append(ligne.substring(53, 62)).toString();
                        bufferedWriter4121.write(ligneGlobalePres);
                        bufferedWriter4121.newLine();
                        logger.info((new StringBuilder()).append("--defalquer41--D\351marrage d'\351criture du fichier ").append(cheminFichier4122).toString());
                        String ligneGlobaleRejet = (new StringBuilder()).append(ligne.substring(0, 20)).append("112").append(ligne.substring(23, 28)).append(StrHandler.lpad(montantDetailRejet, '0', 15)).append(StrHandler.lpad(nombreDetailRejet, '0', 10)).append(ligne.substring(53, 62)).toString();
                        bufferedWriter4122.write(ligneGlobaleRejet);
                        bufferedWriter4122.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer41--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier41).append(" : ").append(e).toString());
                }
            } while(true);
            bufferedReader41 = new BufferedReader(new FileReader(cheminFichier41));
            numeroLigne = 0;
            do
            {
                if((ligne = bufferedReader41.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("41") && typeValeur.equals("21"))
                    {
                        String ligneDetail21 = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter4121.write(ligneDetail21);
                        bufferedWriter4121.newLine();
                    } else
                    if(codeValeur.equals("41") && typeValeur.equals("22"))
                    {
                        String ligneDetail22 = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter4122.write(ligneDetail22);
                        bufferedWriter4122.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer41--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier41).append(" : ").append(e).toString());
                }
            } while(true);
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--defalquer41--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--defalquer41--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
        finally
        {
            try
            {
                if(bufferedWriter4121 != null && bufferedWriter4122 != null && bufferedReader41 != null)
                {
                    bufferedWriter4121.flush();
                    bufferedWriter4122.flush();
                    bufferedReader41.close();
                    logger.info((new StringBuilder()).append("--defalquer41--Fermeture du fichier ").append(cheminFichier41).append(" en lecture").toString());
                    bufferedWriter4121.close();
                    logger.info((new StringBuilder()).append("--defalquer41--Fin d'\351criture du fichier ").append(cheminFichier4121).toString());
                    bufferedWriter4122.close();
                    logger.info((new StringBuilder()).append("--defalquer41--Fin d'\351criture du fichier ").append(cheminFichier4122).toString());
                }
            }
            catch(IOException e)
            {
                logger.error((new StringBuilder()).append("--defalquer41--Probl\350me lors du fermeture d'un fichier : ").append(e).toString());
            }
        }
    }

    public void Traiter47(String cheminFichier47, String cheminFichier4722)
    {
        String ligne = null;
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedReader bufferedReader47 = null;
        BufferedWriter bufferedWriter4722 = null;
        try
        {
            bufferedReader47 = new BufferedReader(new FileReader(cheminFichier47));
            logger.info((new StringBuilder()).append("--Traiter47--Ouverture du fichier ").append(cheminFichier47).append(" en lecture").toString());
            bufferedWriter4722 = new BufferedWriter(new FileWriter(cheminFichier4722, true));
            logger.info((new StringBuilder()).append("--Traiter47--Ouverture du fichier ").append(cheminFichier4722).append(" en \351criture").toString());
            do
            {
                if((ligne = bufferedReader47.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("40") && typeValeur.equals("11"))
                    {
                        logger.info((new StringBuilder()).append("--Traiter47--D\351marrage d'\351criture du fichier ").append(cheminFichier4722).toString());
                        String ligneGlobale = (new StringBuilder()).append(ligne.substring(0, 20)).append("112").append(ligne.substring(23, ligne.length())).toString();
                        bufferedWriter4722.write(ligneGlobale);
                        bufferedWriter4722.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter47--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier47).append(" : ").append(e).toString());
                }
            } while(true);
            bufferedReader47 = new BufferedReader(new FileReader(cheminFichier47));
            numeroLigne = 0;
            do
            {
                if((ligne = bufferedReader47.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("40") && typeValeur.equals("22"))
                    {
                        String ligneDetail = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter4722.write(ligneDetail);
                        bufferedWriter4722.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter47--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier47).append(" : ").append(e).toString());
                }
            } while(true);
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--Traiter47--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--Traiter47--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
        finally
        {
            try
            {
                if(bufferedWriter4722 != null && bufferedReader47 != null)
                {
                    bufferedWriter4722.flush();
                    bufferedReader47.close();
                    logger.info((new StringBuilder()).append("--Traiter47--Fermeture du fichier ").append(cheminFichier47).append(" en lecture").toString());
                    bufferedWriter4722.close();
                    logger.info((new StringBuilder()).append("--Traiter47--Fin d'\351criture du fichier ").append(cheminFichier4722).toString());
                    bufferedWriter4722.close();
                    logger.info((new StringBuilder()).append("--Traiter47--Fin d'\351criture du fichier ").append(cheminFichier4722).toString());
                }
            }
            catch(IOException e)
            {
                logger.error((new StringBuilder()).append("--Traiter47--Probl\350me lors du fermeture d'un fichier : ").append(e).toString());
            }
        }
    }

    /*public Map file40Control(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetailPres, String typeValDetailRejet)
    {
        Map map;
        Long montantTotal;
        Long montantTotalRejet;
        Long montantDetailPres;
        Long montantDetailRejet;
        int nombreTotal;
        int nombreDetailPres;
        int nombreDetailRejet;
        int numeroLigne;
        Scanner scanner;
        map = new HashMap();
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        montantTotal = new Long(0L);
        montantTotalRejet = new Long(0L);
        montantDetailPres = new Long(0L);
        montantDetailRejet = new Long(0L);
        nombreTotal = 0;
        nombreDetailPres = 0;
        nombreDetailRejet = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Map map1;
        scanner = new Scanner(new File(cheminFichier));
        logger.info((new StringBuilder()).append("--Controle 40--Ouverture du fichier ").append(cheminFichier).append(" en lecture").toString());
        do
        {
            if(!scanner.hasNextLine())
                break;
            try
            {
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                String code = ligne.substring(26, 28);
                String montant = ligne.substring(28, 43);
                if(codeValeur.equals(codeVal))
                    if(typeValeur.equals(typeValGlobal))
                    {
                        montantTotal = new Long(montant);
                        montantTotalRejet = new Long(ligne.substring(62, 77));
                        nombreTotal = Integer.parseInt(ligne.substring(43, 53));
                    } else
                    if(typeValeur.equals(typeValDetailPres) && code.equals("00"))
                    {
                        montantDetailPres = Long.valueOf(montantDetailPres.longValue() + (new Long(montant)).longValue());
                        nombreDetailPres++;
                    } else
                    if(typeValeur.equals(typeValDetailRejet) && code.equals("00"))
                    {
                        montantDetailRejet = Long.valueOf(montantDetailRejet.longValue() + (new Long(montant)).longValue());
                        nombreDetailRejet++;
                    }
            }
            // Misplaced declaration of an exception variable
            catch(FileNotFoundException e)
            {
                logger.error((new StringBuilder()).append("--Controle 40--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier).append(" : ").append(e).toString());
            }
        } while(true);
        if(montantTotal.longValue() != montantDetailPres.longValue() + montantDetailRejet.longValue() || nombreTotal != nombreDetailPres + nombreDetailRejet || montantTotalRejet.longValue() != montantDetailRejet.longValue())
            break MISSING_BLOCK_LABEL_518;
        map.put("test", Boolean.valueOf(true));
        map.put("montantDetailPres", montantDetailPres);
        map.put("montantDetailRejet", montantDetailRejet);
        map.put("nombreDetailPres", Integer.valueOf(nombreDetailPres));
        map.put("nombreDetailRejet", Integer.valueOf(nombreDetailRejet));
        map1 = map;
        return map1;
        map.put("test", Boolean.valueOf(false));
        map1 = map;
        return map1;
        map1;
        Map map2;
        map.put("test", Boolean.valueOf(false));
        logger.error((new StringBuilder()).append("--Controle 40--Probl\350me le fichier ").append(cheminFichier).append(" n'existe pas!!!! : ").append(map1).toString());
        map2 = map;
        return map2;
        local;
        logger.info((new StringBuilder()).append("--Controle 40--Fermeture du fichier ").append(cheminFichier).append(" en lecture").toString());
        if(scanner != null)
            scanner.close();
        JVM INSTR ret 23;
    }
*/
/************************** Partie Traitements Fichiers 40 **********************/
    
    public Map file40Control(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetailPres, String typeValDetailRejet) {

        Map map = new HashMap();
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        Long montantTotal = new Long(0);
        Long montantTotalRejet = new Long(0);
        Long montantDetailPres = new Long(0);
        Long montantDetailRejet = new Long(0);
        int nombreTotal = 0;
        int nombreDetailPres = 0;
        int nombreDetailRejet = 0;
        int numeroLigne = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(cheminFichier));
            logger.info((new StringBuilder()).append("--Controle 40--Ouverture du fichier " + 
                        cheminFichier + " en lecture"));

            // On boucle sur chaque ligne detecté
            while (scanner.hasNextLine()) {
                try {
                    String ligne = scanner.nextLine();
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    code = ligne.substring(26, 28);
                    montant = ligne.substring(28, 43);

                    if (codeValeur.equals(codeVal)) {
                        if (typeValeur.equals(typeValGlobal)) {

                            montantTotal = new Long(montant);
                            montantTotalRejet = 
                                    new Long(ligne.substring(62, 77));
                            nombreTotal = 
                                    Integer.parseInt(ligne.substring(43, 53));

                        } else if (typeValeur.equals(typeValDetailPres) && 
                                   code.equals("00")) {
                            montantDetailPres = 
                                    montantDetailPres + new Long(montant);
                            nombreDetailPres++;

                        } else if (typeValeur.equals(typeValDetailRejet) && 
                                   code.equals("00")) {
                            montantDetailRejet = 
                                    montantDetailRejet + new Long(montant);
                            nombreDetailRejet++;

                        }

                    }
                } catch (Exception e) {
                    logger.error((new StringBuilder()).append("--Controle 40--Problème lors du lecture de la ligne " + 
                                 numeroLigne + " dans le fichier " + 
                                 cheminFichier + " : " + e));
                }
            }

//            if ((montantTotal.longValue() == 
//                 (montantDetailPres + montantDetailRejet)) && 
//                (nombreTotal == nombreDetailPres + nombreDetailRejet) && 
//                (montantTotalRejet.longValue() == montantDetailRejet)) {
            if((montantTotal.longValue() == (montantDetailPres.longValue() + 
            		montantDetailRejet.longValue())) && (nombreTotal != (nombreDetailPres + nombreDetailRejet)) &&
            		montantTotalRejet.longValue() != montantDetailRejet.longValue()){


                map.put("test", true);
                map.put("montantDetailPres", montantDetailPres);
                map.put("montantDetailRejet", montantDetailRejet);
                map.put("nombreDetailPres", nombreDetailPres);
                map.put("nombreDetailRejet", nombreDetailRejet);
                return map;


            } else {

                map.put("test", false);

                return map;
            }

        } catch (FileNotFoundException e) {

            map.put("test", false);
            logger.error((new StringBuilder()).append("--Controle 40--Problème le fichier " + 
                         cheminFichier + " n'existe pas!!!! : " + e));
            return map;


        } finally {
            logger.info((new StringBuilder()).append("--Controle 40--Fermeture du fichier " + 
                        cheminFichier + " en lecture"));
            if (scanner != null) {
                scanner.close();

            }
        }

    }
    public void defalquer40(String cheminFichier40, String cheminFichier4021, String cheminFichier4022, String montantDetailPres, String montantDetailRejet, String nombreDetailPres, String nombreDetailRejet)
    {
        String ligne = null;
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedReader bufferedReader40 = null;
        BufferedWriter bufferedWriter4021 = null;
        BufferedWriter bufferedWriter4022 = null;
        try
        {
            bufferedReader40 = new BufferedReader(new FileReader(cheminFichier40));
            logger.info((new StringBuilder()).append("--defalquer40--Ouverture du fichier ").append(cheminFichier40).append(" en lecture").toString());
            bufferedWriter4021 = new BufferedWriter(new FileWriter(cheminFichier4021, true));
            logger.info((new StringBuilder()).append("--defalquer40--Ouverture du fichier ").append(cheminFichier4021).append(" en \351criture").toString());
            bufferedWriter4022 = new BufferedWriter(new FileWriter(cheminFichier4022, true));
            logger.info((new StringBuilder()).append("--defalquer40--Ouverture du fichier ").append(cheminFichier4022).append(" en \351criture").toString());
            do
            {
                if((ligne = bufferedReader40.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("40") && typeValeur.equals("11"))
                    {
                        logger.info((new StringBuilder()).append("--defalquer40--D\351marrage d'\351criture du fichier ").append(cheminFichier4021).toString());
                        String ligneGlobalePres = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, 28)).append(StrHandler.lpad(montantDetailPres, '0', 15)).append(StrHandler.lpad(nombreDetailPres, '0', 10)).append(ligne.substring(53, 62)).toString();
                        bufferedWriter4021.write(ligneGlobalePres);
                        bufferedWriter4021.newLine();
                        logger.info((new StringBuilder()).append("--defalquer40--D\351marrage d'\351criture du fichier ").append(cheminFichier4022).toString());
                        String ligneGlobaleRejet = (new StringBuilder()).append(ligne.substring(0, 20)).append("112").append(ligne.substring(23, 28)).append(StrHandler.lpad(montantDetailRejet, '0', 15)).append(StrHandler.lpad(nombreDetailRejet, '0', 10)).append(ligne.substring(53, 62)).toString();
                        bufferedWriter4022.write(ligneGlobaleRejet);
                        bufferedWriter4022.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer40--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier40).append(" : ").append(e).toString());
                }
            } while(true);
            bufferedReader40 = new BufferedReader(new FileReader(cheminFichier40));
            numeroLigne = 0;
            do
            {
                if((ligne = bufferedReader40.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("40") && typeValeur.equals("21"))
                    {
                        String ligneDetail21 = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter4021.write(ligneDetail21);
                        bufferedWriter4021.newLine();
                    } else
                    if(codeValeur.equals("40") && typeValeur.equals("22"))
                    {
                        String ligneDetail22 = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter4022.write(ligneDetail22);
                        bufferedWriter4022.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--defalquer40--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier40).append(" : ").append(e).toString());
                }
            } while(true);
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--defalquer40--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--defalquer40--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
        finally
        {
            try
            {
                if(bufferedWriter4021 != null && bufferedWriter4022 != null && bufferedReader40 != null)
                {
                    bufferedWriter4021.flush();
                    bufferedWriter4022.flush();
                    bufferedReader40.close();
                    logger.info((new StringBuilder()).append("--defalquer40--Fermeture du fichier ").append(cheminFichier40).append(" en lecture").toString());
                    bufferedWriter4021.close();
                    logger.info((new StringBuilder()).append("--defalquer40--Fin d'\351criture du fichier ").append(cheminFichier4021).toString());
                    bufferedWriter4022.close();
                    logger.info((new StringBuilder()).append("--defalquer40--Fin d'\351criture du fichier ").append(cheminFichier4022).toString());
                }
            }
            catch(IOException e)
            {
                logger.error((new StringBuilder()).append("--defalquer40--Probl\350me lors du fermeture d'un fichier : ").append(e).toString());
            }
        }
    }

    /*public Boolean fileControl(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetail)
    {
        Long montantTotal;
        Long montantDetail;
        int nombreTotal;
        int nombreDetail;
        int numeroLigne;
        Scanner scanner;
        String typeValeur = "";
        String codeValeur = "";
        String montant = "";
        String nbreTotal = "";
        montantTotal = new Long(0L);
        montantDetail = new Long(0L);
        nombreTotal = 0;
        nombreDetail = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Boolean boolean1;
        scanner = new Scanner(new File(cheminFichier));
        logger.info((new StringBuilder()).append("--Controle Fichier--Ouverture du fichier ").append(cheminFichier).append(" en lecture").toString());
        do
        {
            if(!scanner.hasNextLine())
                break;
            numeroLigne++;
            try
            {
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                String montant = ligne.substring(26, 41);
                String nbreTotal = ligne.substring(41, 51);
                if(codeValeur.equals(codeVal))
                    if(typeValeur.equals(typeValGlobal))
                    {
                        montantTotal = new Long(montant);
                        nombreTotal = Integer.parseInt(nbreTotal);
                    } else
                    if(typeValeur.equals("21") || typeValeur.equals("22"))
                    {
                        montantDetail = Long.valueOf(montantDetail.longValue() + (new Long(montant)).longValue());
                        nombreDetail++;
                    }
            }
            // Misplaced declaration of an exception variable
            catch(FileNotFoundException e)
            {
                logger.error((new StringBuilder()).append("--Controle Fichier--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier).append(" : ").append(e).toString());
            }
        } while(true);
        if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail)
            break MISSING_BLOCK_LABEL_326;
        boolean1 = Boolean.valueOf(true);
        return boolean1;
        boolean1 = Boolean.valueOf(false);
        return boolean1;
        boolean1;
        Boolean boolean2;
        logger.error((new StringBuilder()).append("--Controle Fichier--Probl\350me le fichier ").append(cheminFichier).append(" n'existe pas!!!! : ").append(boolean1).toString());
        boolean2 = Boolean.valueOf(false);
        return boolean2;
        local;
        if(scanner != null)
        {
            scanner.close();
            logger.info((new StringBuilder()).append("--Controle Fichier--Fermeture du fichier ").append(cheminFichier).append(" en lecture").toString());
        }
        JVM INSTR ret 18;
    }*/
    
    public Boolean fileControl(String cheminFichier, String codeVal,String typeValGlobal, String typeValDetail) {

        String typeValeur = "";
        String codeValeur = "";
        String montant = "";
        String nbreTotal = "";
        Long montantTotal = new Long(0);
        Long montantDetail = new Long(0);
        int nombreTotal = 0;
        int nombreDetail = 0;
        int numeroLigne = 0;
        Scanner scanner = null;
        try {


            scanner = new Scanner(new File(cheminFichier));
            logger.info((new StringBuilder()).append("--Controle Fichier--Ouverture du fichier " + 
                        cheminFichier + " en lecture"));


            // On boucle sur chaque ligne detecté
            while (scanner.hasNextLine()) {
                numeroLigne++;
                try {
                    String ligne = scanner.nextLine();
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    montant = ligne.substring(26, 41);
                    nbreTotal = ligne.substring(41, 51);


                    if (codeValeur.equals(codeVal)) {
                        if (typeValeur.equals(typeValGlobal)) {

                            montantTotal = new Long(montant);
                            nombreTotal = Integer.parseInt(nbreTotal);

                        } else if (typeValeur.equals("21") || 
                                   typeValeur.equals("22")) {
                            montantDetail = montantDetail + new Long(montant);
                            nombreDetail++;

                        }
                    }
                } catch (Exception e) {
                    logger.error((new StringBuilder()).append("--Controle Fichier--Problème lors du lecture de la ligne " + 
                                 numeroLigne + " dans le fichier " + 
                                 cheminFichier + " : " + e));
                }
            }

            if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail){
                return false;
            } else {
                return true;
            }

        } catch (FileNotFoundException e) {
            logger.error((new StringBuilder()).append("--Controle Fichier--Problème le fichier " + 
                         cheminFichier + " n'existe pas!!!! : " + e));
            return false;


        } finally {
            if (scanner != null) {
                scanner.close();
                logger.info((new StringBuilder()).append("--Controle Fichier--Fermeture du fichier " + 
                            cheminFichier + " en lecture"));
            }
        }

    }


    public void Traiter30(String cheminFichier30, String cheminFichier3022)
    {
        String ligne = null;
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedReader bufferedReader30 = null;
        BufferedWriter bufferedWriter3022 = null;
        try
        {
            bufferedReader30 = new BufferedReader(new FileReader(cheminFichier30));
            logger.info((new StringBuilder()).append("--Traiter30--Ouverture du fichier ").append(cheminFichier30).append(" en lecture").toString());
            bufferedWriter3022 = new BufferedWriter(new FileWriter(cheminFichier3022, true));
            logger.info((new StringBuilder()).append("--Traiter30--Ouverture du fichier ").append(cheminFichier3022).append(" en \351criture").toString());
            do
            {
                if((ligne = bufferedReader30.readLine()) == null)
                    break;
                numeroLigne++;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("30") && typeValeur.equals("11"))
                    {
                        logger.info((new StringBuilder()).append("--Traiter30--D\351marrage d'\351criture du fichier ").append(cheminFichier3022).toString());
                        String ligneGlobale = (new StringBuilder()).append(ligne.substring(0, 20)).append("112").append(ligne.substring(23, ligne.length())).toString();
                        bufferedWriter3022.write(ligneGlobale);
                        bufferedWriter3022.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter30--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier30).append(" : ").append(e).toString());
                }
            } while(true);
            bufferedReader30 = new BufferedReader(new FileReader(cheminFichier30));
            numeroLigne = 0;
            do
            {
                if((ligne = bufferedReader30.readLine()) == null)
                    break;
                numeroLigne++;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals("30") && (typeValeur.equals("21") || typeValeur.equals("22")))
                    {
                        String ligneDetail = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter3022.write(ligneDetail);
                        bufferedWriter3022.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter30--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier30).append(" : ").append(e).toString());
                }
            } while(true);
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--Traiter30--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--Traiter30--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
        finally
        {
            try
            {
                if(bufferedWriter3022 != null && bufferedReader30 != null)
                {
                    bufferedWriter3022.flush();
                    bufferedReader30.close();
                    logger.info((new StringBuilder()).append("--Traiter30--Fermeture du fichier ").append(cheminFichier30).append(" en lecture").toString());
                    bufferedWriter3022.close();
                    logger.info((new StringBuilder()).append("--Traiter30--Fin d'\351criture du fichier ").append(cheminFichier3022).toString());
                    bufferedWriter3022.close();
                    logger.info((new StringBuilder()).append("--Traiter30--Fin d'\351criture du fichier ").append(cheminFichier3022).toString());
                }
            }
            catch(IOException e)
            {
                logger.error((new StringBuilder()).append("--Traiter30--Probl\350me lors du fermeture d'un fichier : ").append(e).toString());
            }
        }
    }

    public void Traiter8x(String cheminFichier8x, String cheminFichier8x21, String codeVal)
    {
        String ligne = null;
        String codeValeur = "";
        String typeValeur = "";
        int numeroLigne = 0;
        BufferedReader bufferedReader8x = null;
        BufferedWriter bufferedWriter8x21 = null;
        String code = "";
        try
        {
            bufferedReader8x = new BufferedReader(new FileReader(cheminFichier8x));
            logger.info((new StringBuilder()).append("--Traiter8x--Ouverture du fichier ").append(cheminFichier8x).append(" en lecture").toString());
            bufferedWriter8x21 = new BufferedWriter(new FileWriter(cheminFichier8x21, true));
            logger.info((new StringBuilder()).append("--Traiter8x--Ouverture du fichier ").append(cheminFichier8x21).append(" en \351criture").toString());
            do
            {
                if((ligne = bufferedReader8x.readLine()) == null)
                    break;
                numeroLigne++;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    if(codeValeur.equals(codeVal) && typeValeur.equals("11"))
                    {
                        logger.info((new StringBuilder()).append("--Traiter8x--D\351marrage d'\351criture du fichier ").append(cheminFichier8x21).toString());
                        String ligneGlobale = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter8x21.write(ligneGlobale);
                        bufferedWriter8x21.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter8x--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier8x).append(" : ").append(e).toString());
                }
            } while(true);
            bufferedReader8x = new BufferedReader(new FileReader(cheminFichier8x));
            numeroLigne = 0;
            do
            {
                if((ligne = bufferedReader8x.readLine()) == null)
                    break;
                try
                {
                    codeValeur = ligne.substring(1, 3);
                    typeValeur = ligne.substring(21, 23);
                    code = ligne.substring(28, 30);
                    if(codeValeur.equals(codeVal) && typeValeur.equals("21"))
                    {
                        String ligneDetail = (new StringBuilder()).append(ligne.substring(0, 20)).append("1").append(ligne.substring(21, ligne.length())).toString();
                        bufferedWriter8x21.write(ligneDetail);
                        bufferedWriter8x21.newLine();
                    }
                }
                catch(Exception e)
                {
                    logger.error((new StringBuilder()).append("--Traiter8x--Probl\350me lors du traitement de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier8x).append(" : ").append(e).toString());
                }
            } while(true);
        }
        catch(FileNotFoundException e)
        {
            logger.error((new StringBuilder()).append("--Traiter8x--Probl\350me inexistance d'un fichier : ").append(e).toString());
        }
        catch(IOException e)
        {
            logger.error((new StringBuilder()).append("--Traiter8x--Probl\350me lors d'\351criture d'un fichier : ").append(e).toString());
        }
        finally
        {
            try
            {
                if(bufferedWriter8x21 != null && bufferedReader8x != null)
                {
                    bufferedWriter8x21.flush();
                    bufferedReader8x.close();
                    logger.info((new StringBuilder()).append("--Traiter8x--Fermeture du fichier ").append(cheminFichier8x).append(" en lecture").toString());
                    bufferedWriter8x21.close();
                    logger.info((new StringBuilder()).append("--Traiter8x--Fin d'\351criture du fichier ").append(cheminFichier8x21).toString());
                    bufferedWriter8x21.close();
                    logger.info((new StringBuilder()).append("--Traiter8x--Fin d'\351criture du fichier ").append(cheminFichier8x21).toString());
                }
            }
            catch(IOException e)
            {
                logger.error((new StringBuilder()).append("--Traiter8x--Probl\350me lors du fermeture d'un fichier : ").append(e).toString());
            }
        }
    }

    /*public Boolean fileControl8x(String cheminFichier, String codeVal, String typeValGlobal, String typeValDetail)
    {
        Long montantTotal;
        Long montantDetail;
        int nombreTotal;
        int nombreDetail;
        int numeroLigne;
        Scanner scanner;
        String typeValeur = "";
        String codeValeur = "";
        String code = "";
        String montant = "";
        String nbreTotal = "";
        montantTotal = new Long(0L);
        montantDetail = new Long(0L);
        nombreTotal = 0;
        nombreDetail = 0;
        numeroLigne = 0;
        scanner = null;
        FileNotFoundException e;
        Boolean boolean1;
        scanner = new Scanner(new File(cheminFichier));
        logger.info((new StringBuilder()).append("--Control--Ouverture du fichier ").append(cheminFichier).append(" en lecture").toString());
        do
        {
            if(!scanner.hasNextLine())
                break;
            numeroLigne++;
            try
            {
                String ligne = scanner.nextLine();
                String codeValeur = ligne.substring(1, 3);
                String typeValeur = ligne.substring(21, 23);
                if(codeValeur.equals(codeVal))
                    if(codeValeur.equals("81") || codeValeur.equals("83"))
                    {
                        String montant = ligne.substring(26, 41);
                        String nbreTotal = ligne.substring(41, 51);
                        if(typeValeur.equals(typeValGlobal))
                        {
                            montantTotal = new Long(montant);
                            nombreTotal = Integer.parseInt(nbreTotal);
                        } else
                        if(typeValeur.equals(typeValDetail))
                        {
                            montantDetail = Long.valueOf(montantDetail.longValue() + (new Long(montant)).longValue());
                            nombreDetail++;
                        }
                    } else
                    {
                        String code = ligne.substring(26, 28);
                        String montant = ligne.substring(28, 43);
                        String nbreTotal = ligne.substring(43, 53);
                        if(typeValeur.equals(typeValGlobal))
                        {
                            montantTotal = new Long(montant);
                            nombreTotal = Integer.parseInt(nbreTotal);
                        } else
                        if(typeValeur.equals(typeValDetail) && code.equals("00"))
                        {
                            montantDetail = Long.valueOf(montantDetail.longValue() + (new Long(montant)).longValue());
                            nombreDetail++;
                        }
                    }
            }
            // Misplaced declaration of an exception variable
            catch(FileNotFoundException e)
            {
                logger.error((new StringBuilder()).append("--Control--Probl\350me lors du lecture de la ligne ").append(numeroLigne).append(" dans le fichier ").append(cheminFichier).append(" : ").append(e).toString());
            }
        } while(true);
        if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail)
            break MISSING_BLOCK_LABEL_454;
        boolean1 = Boolean.valueOf(true);
        return boolean1;
        boolean1 = Boolean.valueOf(false);
        return boolean1;
        boolean1;
        Boolean boolean2;
        logger.error((new StringBuilder()).append("--Control--Probl\350me le fichier ").append(cheminFichier).append(" n'existe pas!!!! : ").append(boolean1).toString());
        boolean2 = Boolean.valueOf(false);
        return boolean2;
        local;
        if(scanner != null)
        {
            scanner.close();
            logger.info((new StringBuilder()).append("--Control--Fermeture du fichier ").append(cheminFichier).append(" en lecture").toString());
        }
        JVM INSTR ret 19;
    }*/
    public Boolean fileControl8x(String cheminFichier, String codeVal, 
            String typeValGlobal, String typeValDetail) {

String typeValeur = "";
String codeValeur = "";
String code = "";
String montant = "";
String nbreTotal = "";
Long montantTotal = new Long(0);
Long montantDetail = new Long(0);
int nombreTotal = 0;
int nombreDetail = 0;
int numeroLigne = 0;
Scanner scanner = null;
try {


scanner = new Scanner(new File(cheminFichier));
logger.info((new StringBuilder()).append("--Control--Ouverture du fichier " + cheminFichier + 
   " en lecture"));


// On boucle sur chaque ligne detecté
while (scanner.hasNextLine()) {
numeroLigne++;
try {
String ligne = scanner.nextLine();
codeValeur = ligne.substring(1, 3);
typeValeur = ligne.substring(21, 23);


if (codeValeur.equals(codeVal)) {
   if (codeValeur.equals("81") || 
       codeValeur.equals("83")) {
       montant = ligne.substring(26, 41);
       nbreTotal = ligne.substring(41, 51);
       if (typeValeur.equals(typeValGlobal)) {

           montantTotal = new Long(montant);
           nombreTotal = Integer.parseInt(nbreTotal);

       } else if (typeValeur.equals(typeValDetail)) {
           montantDetail = 
                   montantDetail + new Long(montant);
           nombreDetail++;

       }
   }

   else {

       code = ligne.substring(26, 28);
       montant = ligne.substring(28, 43);
       nbreTotal = ligne.substring(43, 53);

       if (typeValeur.equals(typeValGlobal)) {

           montantTotal = new Long(montant);
           nombreTotal = Integer.parseInt(nbreTotal);

       } else if (typeValeur.equals(typeValDetail) && 
                  code.equals("00")) {
           montantDetail = 
                   montantDetail + new Long(montant);
           nombreDetail++;

       }
   }


}


} catch (Exception e) {
logger.error((new StringBuilder()).append("--Control--Problème lors du lecture de la ligne " + 
            numeroLigne + " dans le fichier " + 
            cheminFichier + " : " + e));
}
}

//if ((montantTotal.longValue() == montantDetail) && 
//(nombreTotal == nombreDetail)) {
if(montantTotal.longValue() != montantDetail.longValue() || nombreTotal != nombreDetail){
return false;
} else {
return true;
}

} catch (FileNotFoundException e) {
logger.error((new StringBuilder()).append("--Control--Problème le fichier " + cheminFichier + 
    " n'existe pas!!!! : " + e));
return false;


} finally {
if (scanner != null) {
scanner.close();
logger.info((new StringBuilder()).append("--Control--Fermeture du fichier " + 
       cheminFichier + " en lecture"));
}
}

}

    private static Logger logger = Logger.getLogger(TraitementFile.class);
    GetCodeBctAgence getCodeBctAgence;
    Util util;
    Parametres parametres;

}
