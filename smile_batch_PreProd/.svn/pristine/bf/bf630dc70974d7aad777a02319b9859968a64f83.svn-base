package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.bna.commun.model.Personne;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Signature;
import com.bna.commun.model.SignatureId;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class InsertSignaturesTrt {
    

    public InsertSignaturesTrt() {
    }
   
   
    /**
     * méthode d'insertion des signatures Français et Arabe d'un client en prend en argument 
     * le contrat + personne + bufferedImage Fr + bufferedImage Ar
     * @param vo SignaturePersCpt:ContratPersonne, BufferedImage, BufferedImage 
     * @return vo SignaturePersCpt
     * @author :Ramzi
     */
    public ValueObject execute(ValueObject vo) {
        Context context = ContextHandler.getContext();
        CRUDservice crudService = (CRUDservice)context.getBean("crudservice");
        SignaturePersCpt signaturePersCpt = (SignaturePersCpt)vo;
        try {

            BufferedImage biF = null;
            BufferedImage biA = null;
            byte[] imageBytesF;
            byte[] imageBytesA;
            ByteArrayInputStream inStreamF = null;
            ByteArrayInputStream inStreamA = null;

            biF = signaturePersCpt.getBufferedImageFr();
            biA = signaturePersCpt.getBufferedImageAr();
            ContratPersonne contratPersonne = 
                signaturePersCpt.getContratPersonne();

            if ((biF != null || biA != null) && contratPersonne != null) {
                //generation de inputstream
                if (biF != null){ 
                    ByteArrayOutputStream baosF = new ByteArrayOutputStream();
                    ImageIO.write(biF, "jpeg", baosF);
                    imageBytesF = baosF.toByteArray();
                    inStreamF = new ByteArrayInputStream(imageBytesF);
                }
                if (biA != null){
                    ByteArrayOutputStream baosA = new ByteArrayOutputStream();
                    ImageIO.write(biA, "jpeg", baosA);
                    imageBytesA = baosA.toByteArray();
                    inStreamA = new ByteArrayInputStream(imageBytesA);
                } 
               
                if (inStreamA != null || inStreamF != null) {
                    Signature signature = new Signature();
                    SignatureId signatureID = new SignatureId();
                    signatureID.setCodStrcStrc(contratPersonne.getContratCptId().getCodStrcStrc());
                    signatureID.setCodPrdPrd(contratPersonne.getContratCptId().getCodPrdPrd());
                    signatureID.setNumCcptCcpt(contratPersonne.getContratCptId().getNumCcptCcpt());
                    //recherche num_seq_pers
                    GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
                    Personne personne = 
                        (Personne)getPersonneTrt.exec(contratPersonne.getPersonneId());
                    signatureID.setNumSeqPers(personne.getNumSeqPers());

                    signature.setSignatureId(signatureID);

                    if (inStreamF != null)
                        signature.setImgFrsiSignStream(inStreamF);
                    if (inStreamA != null)
                        signature.setImgArsiSignStream(inStreamA);
                    
                    //ajout date operation et matricule personnel
                    signature.setDatOperSign(signaturePersCpt.getDateModification()); 
                    Personnel personnel = new Personnel();
                    personnel.setNumMatrUser(signaturePersCpt.getNumMatricule().toString());   
                    signature.setPersonnel(personnel); 

                    crudService.create(signature);

                    System.out.println("Ok");

                } else {
                    //logger.error("Pas de zone de selection pour la zone FR et la zone AR!!!!!");
                    return null;
                }

            } else {
                //logger.error("Input Nullllllllllllllllll!!!!!");
                return null;
            }

        } catch (Exception e) {
           // logger.error("Exception : ",e);
            throw new RuntimeException(e);
        }
        return (signaturePersCpt);
    }
}
