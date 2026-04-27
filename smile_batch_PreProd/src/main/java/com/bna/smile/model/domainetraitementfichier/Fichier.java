package com.bna.smile.model.domainetraitementfichier;

import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.util.Date;

public class Fichier
    implements FileFilter
{

    private String nomFichier;
    private String codeStructure;
    private Date datePECFichier;
    private int codeTraitFichier;
    private final String okFilePrefixes[] = {
        "10", "VP", "81", "82", "83", "84", "30", "31", "32", "33", 
        "41", "47"
    };

    public Fichier(String nomFichier, int codeTraitFichier)
    {
        this.nomFichier = nomFichier;
        this.codeTraitFichier = codeTraitFichier;
    }

    public Fichier()
    {
    }

    public void setNomFichier(String nomFichier)
    {
        this.nomFichier = nomFichier;
    }

    public String getNomFichier()
    {
        return nomFichier;
    }

    public void setCodeStructure(String codeStructure)
    {
        this.codeStructure = codeStructure;
    }

    public String getCodeStructure()
    {
        return codeStructure;
    }

    public void setDatePECFichier(Date datePECFichier)
    {
        this.datePECFichier = datePECFichier;
    }

    public Date getDatePECFichier()
    {
        return datePECFichier;
    }

    public void setCodeTraitFichier(int codeTraitFichier)
    {
        this.codeTraitFichier = codeTraitFichier;
    }

    public int getCodeTraitFichier()
    {
        return codeTraitFichier;
    }

    public boolean accept(File file)
    {
        DateFormat dateFormat = DateFormat.getDateInstance(3);
        String dateSystem = dateFormat.format(new Date());
        String date[] = dateSystem.split("/");
        String arr[] = okFilePrefixes;
        int len = arr.length;
        for(int i = 0; i < len; i++)
        {
            String extension = arr[i];
            if(file.getName().startsWith((new StringBuilder()).append(extension).append(date[0]).append(date[1]).append(date[2]).toString()))
            {
                return true;
            }
        }

        return false;
    }
}