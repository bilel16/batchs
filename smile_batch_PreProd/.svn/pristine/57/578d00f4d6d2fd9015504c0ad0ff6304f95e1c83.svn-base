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
import com.bna.commun.vo.PrimitiveVO;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.bna.smile.model.domainecontratcompte.moyensPaiement.dao.DemandeCarteDAO;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

/**
 * Valider une demande de carte.
 * @author Ramzi
 * @param DemandeCarte 
 * @return DemandeCarte
 * @since 21/06/2007
 * 
 */
public class ValidationDemandeCarteTrt extends Traitement{
    public ValidationDemandeCarteTrt() {
    }

    public IValueObject perform(IValueObject vo) throws Exception{
        DemandeCarte  demandeCarte  = (DemandeCarte )vo;
        try {
            Context context = ContextHandler.getContext();
            CRUDservice crudService = 
                (CRUDservice)context.getBean("crudservice");
            /*
            //si cas de modif plafond par 
            if(demandeCarte.getBoolModpDcar() != null && demandeCarte.getBoolModpDcar().equals(Long.valueOf("1"))){
                //recherche de la carte ancienne         
                PrimitiveVO voCarte = new PrimitiveVO();
                voCarte.setVString(demandeCarte.getNumCarDcar().toString());
                GetCarteBancaireTrt getCarteBancaireTrt = new GetCarteBancaireTrt();
                CarteBancaire carteBancaireOld = (CarteBancaire)getCarteBancaireTrt.exec(voCarte);
                carteBancaireOld.setMontPretCarb(demandeCarte.getMontPretDcar());
                carteBancaireOld.setMontPachCarb(demandeCarte.getMontPachDcar());            
                crudService.update(carteBancaireOld);
            }
            */
            //modification de la demande            
            crudService.update(demandeCarte);  
            
            //sauvgarde de l'historique
            InsertDetailOperDemCartTrt insertDetailOperDemCartTrt = new InsertDetailOperDemCartTrt();
            //DetailOperDemCart detailOperDemCart = (DetailOperDemCart) insertDetailOperDemCartTrt.execute(demandeCarte);
             ValueObject voRetour = (ValueObject)insertDetailOperDemCartTrt.exec(demandeCarte);
             if (voRetour == null || voRetour.hasError()) {
                    List listErreur = voRetour.getErrors();
                    for (Iterator it = listErreur.iterator(); it.hasNext(); ) {
                        com.oxia.fwk.core.Error erreur = 
                            (com.oxia.fwk.core.Error)it.next();
                        demandeCarte.addError(erreur);
                        throw new RuntimeException(); 
                    }
             }else{               
                 this.sychronisationPascal(demandeCarte);
             }
       
        } catch (Exception e) {
                com.oxia.fwk.core.Error erreur=new com.oxia.fwk.core.Error();
                erreur.setCode("Technique");
                erreur.setDescription("ValidationDemandeCarteTrt "+e.getMessage());;
                demandeCarte.addError(erreur);
                logger.error("Exception : ",e);
                throw new RuntimeException(e);       
        }
        return demandeCarte;
    }
    public void genCroText(ValueObject vo) {
    
    }
    public String getNumeroTache(IValueObject vo){
        DemandeCarte  demandeCarte  = (DemandeCarte )vo;
        return demandeCarte.getTache().getTacheId().getCodOperOper().toString()+
             StrHandler.lpad(demandeCarte.getTache().getTacheId().getCodTachTach().toString(),'0',2);
        
    }
    public void genererSynchronisationPascal(ValueObject vo) {
    
        DemandeCarte demandeCarte = (DemandeCarte)vo;   
        
        DateFormat myformat1 = new SimpleDateFormat("yyMMdd");
        DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
             
         //partie fixe toujour envoi 33 et 1 pour syncro 
        this.setCodeOperationSynch(Constants.COD_OPER_OPER_ValidDemande.longValue());
        this.setCodeTacheSynch(Constants.COD_TACH_TACH_ValidDemande.longValue());
        this.setDateOperationSynch(new Date());
        this.setCodeStructureSynch(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        
        //partie variable
        String numCompte = StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                           StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6); 
        
        String dateValidation = "        ";
        if(demandeCarte.getDatEnvDcar() != null )            
            dateValidation =  myformat2.format(demandeCarte.getDatEnvDcar());
        else
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
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_MAST_INT))){
            typeCarte = "I";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_VISA_INT))){
            typeCarte = "W";
        }else if(demandeCarte.getTypeCarte().getCodTcarTcar().equals(Long.valueOf(Constants.COD_TCAR_TCAR_VISAGOLD_INT))){
            typeCarte = "J";
        }
        
        String tauxRetrait= "00";
        if(demandeCarte.getMontPretDcar() != null){
            tauxRetrait = StrHandler.lpad(""+demandeCarte.getMontPretDcar().intValue()/50,'0',2);
        }
        
        String tauxAchat= "00";
        if(demandeCarte.getMontPachDcar() != null){
            tauxAchat = StrHandler.lpad(""+demandeCarte.getMontPachDcar().intValue()/50,'0',2);
        }
        
        String RProf= " ";
        if(demandeCarte.getBoolSalDcar() != null && demandeCarte.getBoolSalDcar().intValue()==1){
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
        
        String Rvenu= "000000";
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
            
            if(RNom.replaceAll(" ","").equals("") && demandeCarte.getNumCarDcar() != null){
                //recherche du nom de la carte avant migration         
                String numCarte=demandeCarte.getNumCarDcar().toString();
                Context context = ContextHandler.getContext();
                DemandeCarteDAO demandeCarteDAO = 
                    (DemandeCarteDAO)context.getBean("demandeCarteDAO");
                RNom = demandeCarteDAO.getNomAncCarte(numCarte) ;     
            }
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
        
        //String codeAgence=StrHandler.lpad(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc().toString(),'0',3);
        
        String RFonc = " ";
        if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_Valider)){
            //si cas de modif plafond par 
            if(demandeCarte.getBoolModpDcar() != null && demandeCarte.getBoolModpDcar().equals(Long.valueOf("1"))){
                RFonc = "M";
            }else{
                RFonc = "C";
            }
        }else if(demandeCarte.getCodEtatDcar().equals(Constants.COD_ETAT_DCAR_DemandeRemplValide)){
            RFonc = "R";
            /////montant retrait et achat de remplacement est celui de l'ancienne carte
            //recherche de la carte ancienne         
            PrimitiveVO voCarte = new PrimitiveVO();
            voCarte.setVString(demandeCarte.getNumCarDcar().toString());
            GetCarteBancaireTrt getCarteBancaireTrt = new GetCarteBancaireTrt();
            CarteBancaire carteBancaire = (CarteBancaire)getCarteBancaireTrt.exec(voCarte);
            if(carteBancaire.getMontPretCarb() != null){
                tauxRetrait = StrHandler.lpad(""+carteBancaire.getMontPretCarb().intValue()/50,'0',2);
            }
            if(carteBancaire.getMontPachCarb() != null){
                tauxAchat = StrHandler.lpad(""+carteBancaire.getMontPachCarb().intValue()/50,'0',2);
            }    
            
        }
        
        String tarif = "00";
        
        String motifAnnul =" ";
        
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
    public IValueObject getNumeroDomaine(IValueObject vo){
        DemandeCarte  demandeCarte  = (DemandeCarte )vo;
        StructureDomaine  structureDomaine  = new StructureDomaine();
        structureDomaine.setCodStrcStrc(demandeCarte.getContratCpt().getContratCptId().getCodStrcStrc());
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        return structureDomaine;
    
    }
    
}
