package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bna.commun.model.CarteBancaire;
import com.bna.commun.model.DemandeCarte;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Restitution carte bancaire.
 * @author Ramzi
 * @param CarteBancaire 
 * @return CarteBancaire
 * @since 26/07/2007
 * 
 */
public class RestitutionCarteBancaireTrt extends Traitement{
    public RestitutionCarteBancaireTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
                
            //modification de la carte           
            crudService.update(carteBancaire); 
            
            //sauvgarde de l'historique
            InsertDetailOperCarteTrt insertDetailOperCarteTrt = new InsertDetailOperCarteTrt();
           // DetailOperCarte detailOperCarte = (DetailOperCarte) insertDetailOperCarteTrt.execute(carteBancaire);
            ValueObject voRetour = (ValueObject)insertDetailOperCarteTrt.exec(carteBancaire);
            if (voRetour == null || voRetour.hasError()) {
                   List listErreur = voRetour.getErrors();
                   for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                       com.oxia.fwk.core.Error erreur = 
                           (com.oxia.fwk.core.Error)it.next();
                       carteBancaire.addError(erreur);
                       throw new RuntimeException(); 
                   }
            }else{               
                    this.sychronisationPascal(carteBancaire);
            }

       
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("RestitutionCarteBancaireTrt "+e.getMessage());;
                carteBancaire.addError(erreur); 
                logger.error("Exception : ",e);
                throw new RuntimeException(e);   
        }
        return carteBancaire;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        return carteBancaire.getDemandeCarte().getTache().getTacheId().getCodOperOper().toString()+
             StrHandler.lpad(carteBancaire.getDemandeCarte().getTache().getTacheId().getCodTachTach().toString(),'0',2);
    
    }
    public IValueObject getNumeroDomaine(IValueObject vo){
        CarteBancaire  carteBancaire  = (CarteBancaire)vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(carteBancaire.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
    public void genererSynchronisationPascal(ValueObject vo) {
    
        CarteBancaire carteBancaire = (CarteBancaire) vo;
        DemandeCarte demandeCarte = carteBancaire.getDemandeCarte();   
        
        DateFormat myformat1 = new SimpleDateFormat("yyMMdd");
        DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
             
        //partie fixe
        this.setCodeOperationSynch(Constants.COD_OPER_OPER_AnnulRenouvel.longValue());
        this.setCodeTacheSynch(Constants.COD_TACH_TACH_AnnulRenouvel);
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        
        //partie variable
        String numCompte = StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6); 
        
        String dateValidation = "        ";
       
        dateValidation =  myformat2.format(new Date());

        String typeCarte = " ";
        if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_CIBT))||
        demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_CIBT_ANCIEN))){
            typeCarte = "S";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_ELECTRON) )){
            typeCarte = "E";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_MAST_NAT))){
            typeCarte = "M";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_VISA_NAT))){
            typeCarte = "V";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_VISAGOLD_NAT))){
            typeCarte = "G";
        }
        
        String tauxRetrait= "00";
        if(demandeCarte.getMontDretDcar() != null){
            tauxRetrait = StrHandler.lpad(""+demandeCarte.getMontPretDcar().intValue()/50,'0',2);
        }
        
        String tauxAchat= "00";
        if(demandeCarte.getMontDachDcar() != null){
            tauxAchat = StrHandler.lpad(""+demandeCarte.getMontPachDcar().intValue()/50,'0',2);
        }
        
        String RProf= " ";
        if(demandeCarte.getBoolSalDcar() != null && demandeCarte.getBoolSalDcar().equals("1")){
            RProf = "O";
        }else{
            RProf = "N";
        }
        
        String Rdecis= " ";
        if(demandeCarte.getDatEnvpDcar() != null){
            Rdecis = "D";
        }else{
            Rdecis = "A";
        }
        
        String Rvenu= "      ";
        if(demandeCarte.getMontSalDcar() != null){
            Rvenu = StrHandler.lpad(demandeCarte.getMontSalDcar().toString(),'0',6);
        }
        
        String RNom = "                          ";
        GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
        PersonneStrc personneStrc = new PersonneStrc();
        personneStrc.setCodTpceTpce(demandeCarte.getCodTpceDcar());
        personneStrc.setNumPcePers(demandeCarte.getNumPceDcar());
        Personne demandeur = (Personne) getPersonneTrt.exec(personneStrc);
        
         //nom prenom porteur de la carte
         if(demandeur != null){
             RNom = StrHandler.rpad(demandeur.getNomNomPers().replaceAll("  ","") + " " + demandeur.getNomPrnPers().replaceAll("  ",""),' ',26);
             if(RNom.length()>26){
                 RNom = RNom.substring(0,26);
             }
         }
        
        String typcpte = " ";
        Context context = ContextHandler.getContext();
        DemandeCarteDAO demandeCarteDAO = 
            (DemandeCarteDAO)context.getBean("demandeCarteDAO");
        typcpte = demandeCarteDAO.getTypeCptCarte(demandeCarte.getContratCpt().getProduit().getCodPrdPrd().toString());
        /* if(demandeCarte.getCodDemDcar().equals("C")){
                typcpte = demandeCarte.getCoTitulaire().getCodTcotCoti();
        }*/
        
        //intitulé du compte
        String IntitCompt = "                          ";
        if(demandeCarte.getContratCpt().getNomIntiCcpt() != null){
            IntitCompt = StrHandler.rpad(demandeCarte.getContratCpt().getNomIntiCcpt(),' ',26);
            if(IntitCompt.length()>26){
                IntitCompt = IntitCompt.substring(0,26);
            }
        }
        
        String RFonc = "A";
        
        String tarif = "00";
          
        String motifAnnul ="3";
        
        String typeDemandeur = demandeCarte.getCodDemDcar();
        
        Long typePiece = demandeCarte.getCodTpceDcar();
        String codTypePiece = " ";
        if(typePiece.equals(Constants.COD_CIN)){
            codTypePiece = "C";
        }else if(typePiece.equals(Constants.COD_PASS)){
            codTypePiece = "P";
        }else if(typePiece.equals(Constants.COD_CSEJ)){
            codTypePiece = "S";
        }
        
        String numPiece = StrHandler.lpad(demandeCarte.getNumPceDcar(),' ',13);
        
        String numDemande = StrHandler.lpad(demandeCarte.getNumDemDcar(),' ',13);
          
        String blanc = " ";
        
        String partieVariable =  numCompte + dateValidation + typeCarte + tauxRetrait + tauxAchat + RProf + Rdecis + Rvenu + RNom + typcpte + IntitCompt +RFonc + tarif + motifAnnul + typeDemandeur + codTypePiece + numPiece + numDemande + blanc;
        
        this.setTextSynch(partieVariable);
        
    }
}
