package com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.traitement;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.bna.commun.model.DetailSignatures;
import com.bna.commun.model.DetailSignaturesId;
import com.bna.commun.model.Personnel;
import com.bna.commun.model.Signature;
import com.bna.commun.util.ContextHandler;
import com.bna.smile.model.domainecommun.model.ContratPersonne;
import com.bna.smile.model.domainecommun.model.Listes;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecontratcompte.souscriptioncontratcompte.model.SignaturePersCpt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ValueObject;

public class ModifSignaturesTrt {
    
    private static final Logger logger = Logger.getLogger(ModifSignaturesTrt.class);

    public ModifSignaturesTrt() {
    }

    /**
     * méthode de modification des signatures Français et Arabe d'un client en prend en argument 
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
            // Test si le signataire existe dana la table signature
            GetSignaturesTrt  getSignaturesTrt = new GetSignaturesTrt();
            Signature signature = (Signature) getSignaturesTrt.execute(contratPersonne);
            //si non existe signature --> arret de l'opération
            if(signature == null){
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                erreur.setCode("1");
                erreur.setDescription("Signataire Inexistante");
                erreur.setKey("signaturesContratCompte.errors.signInexistant");
                Listes list = new Listes();
                list.addError(erreur);
                return list;
            }
            
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
               //modifier signatures
                if (inStreamA != null || inStreamF != null) {
                    
                    // insertion dans la table Detail_signature pour historique
                    DetailSignatures detailSignatures = new DetailSignatures();
                    DetailSignaturesId detailSignaturesId = new DetailSignaturesId();
                    detailSignaturesId.setCodStrcStrc(signature.getSignatureId().getCodStrcStrc());
                    detailSignaturesId.setCodPrdPrd(signature.getSignatureId().getCodPrdPrd());
                    detailSignaturesId.setNumCcptCcpt(signature.getSignatureId().getNumCcptCcpt());
                    detailSignaturesId.setNumSeqPers(signature.getSignatureId().getNumSeqPers());
                    detailSignaturesId.setDatModDsig(signaturePersCpt.getDateModification());
                    
                    detailSignatures.setDetailSignaturesId(detailSignaturesId);
                    
                    String numMatrAnc = signature.getPersonnel().getNumMatrUser();
                    Personnel personnelAn = new Personnel();
                    personnelAn.setNumMatrUser(numMatrAnc);
                    detailSignatures.setPersonnel(personnelAn);
                    
                    // clonage ou construction  des nouveaux inputStreams
                     BufferedImage biF0 = null;
                     BufferedImage biA0 = null;
                     byte[] imageBytesF0;
                     byte[] imageBytesA0;
                     ByteArrayInputStream inStreamF0 = null;
                     ByteArrayInputStream inStreamA0 = null;
                     
                    if(signature.getImgArsiSignStream()!=null){
                        biA0 = ImageIO.read(signature.getImgArsiSignStream());
                        ByteArrayOutputStream baosA0 = new ByteArrayOutputStream();
                        ImageIO.write(biA0, "jpeg", baosA0);
                        imageBytesA0 = baosA0.toByteArray();
                        inStreamA0 = new ByteArrayInputStream(imageBytesA0);
                        detailSignatures.setImgArsiDsigStream(inStreamA0);
                    }
                    
                    if(signature.getImgFrsiSignStream()!=null){
                        biF0 = ImageIO.read(signature.getImgFrsiSignStream());
                        ByteArrayOutputStream baosF0 = new ByteArrayOutputStream();
                        ImageIO.write(biF0, "jpeg", baosF0);
                        imageBytesF0 = baosF0.toByteArray();
                        inStreamF0 = new ByteArrayInputStream(imageBytesF0);
                        detailSignatures.setImgFrsiDsigStream(inStreamF0);
                    }

                    
                    /*ObjectInputStream ImgAr0 = new ObjectInputStream(signature.getImgArsiSignStream());
                    ObjectInputStream ImgFr0 = new ObjectInputStream(signature.getImgFrsiSignStream());*/
                    
                    
                    
                    crudService.create(detailSignatures);
                    
                    //modifier signature Fr
                    if (inStreamF != null)
                        signature.setImgFrsiSignStream(inStreamF);
                    //modifier signature Ar
                    if (inStreamA != null)
                        signature.setImgArsiSignStream(inStreamA);

                    //ajout date operation et matricule personnel
                    signature.setDatOperSign(signaturePersCpt.getDateModification()); 
                    Personnel personnel = new Personnel();
                    personnel.setNumMatrUser(signaturePersCpt.getNumMatricule().toString());   
                    signature.setPersonnel(personnel); 
                    
                    crudService.update(signature);
                    System.out.println("Ok");

                } else {
                    System.out.println("Pas de zone de selection pour la zone FR et la zone AR!!!!!");
                    return null;
                }

            } else {
                System.out.println("Input Nullllllllllllllllll!!!!!");
                return null;
            }

        } catch (Exception e) {
            logger.error("Exception : ",e);
            throw new RuntimeException(e);
        }
        return (signaturePersCpt);
    }
}
