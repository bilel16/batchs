package com.bna.smile.model.domainecontratcompte.moyensPaiement.traitement;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bna.commun.model.DemandeCheque;
import com.bna.commun.model.Personne;
import com.bna.commun.model.StructureDomaine;
import com.bna.commun.traitements.Traitement;
import com.bna.commun.util.ContextHandler;
import com.bna.commun.util.StrHandler;
import com.bna.smile.model.constant.Constants;
import com.bna.smile.model.domainecommun.model.PersonneStrc;
import com.bna.smile.model.domainecommun.service.CRUDservice;
import com.bna.smile.model.domainecommun.traitement.GetPersonneTrt;
import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.IValueObject;
import com.oxia.fwk.core.ValueObject;

public class ValidationDemandeChequeTrt extends Traitement{
   

    public ValidationDemandeChequeTrt() {
    }

    /**
     * Methode permettant la validation d'une demande cheque
     * @param vo : DemandeCheque
     * @return DemandeCheque
     */
    public IValueObject perform(IValueObject vo) {
    
        Context context = ContextHandler.getContext();
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        

  try { /* MAJ du demandeCheque dans la BD */
     if(this.checkClotureJournee()){
         this.setCroFlag(false);
            CRUDservice crudService = 
            (CRUDservice)context.getBean("crudservice");            
            crudService.update(demandeCheque);
            if(!demandeCheque.getCodEtatDchq().equals(Constants.DEM_CHQ_REJETEE))
              this.sychronisationPascal(demandeCheque);
              
        }else{
                com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
                StringBuffer text = new StringBuffer("La journée est déja clôturée...");            
                erreur.setCode("100");
                erreur.setDescription(text.toString());
                erreur.setKey("InsertDemandeCheque");
                demandeCheque.addError(erreur);        
            }   
        } catch (Exception e) {
            com.oxia.fwk.core.Error erreur = new com.oxia.fwk.core.Error();
            StringBuffer text = 
                new StringBuffer("Erreur dans ValidationDemandeChequeTrt : ");
            text.append(e.toString());
            erreur.setCode("100");
            erreur.setDescription(text.toString());
            erreur.setKey("ValidationDemandeCheque");
            demandeCheque.addError(erreur);
            logger.error("Erreur au niveau de l'agence <<" + demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc() + ">>. Exception : ",e);                   
            throw new RuntimeException(e);  
            
        }
        return (demandeCheque);

    }
    
    public void genCroText(ValueObject vo) {
    
    }
    
    public String getNumeroTache(IValueObject vo) {
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        
      return (demandeCheque.getTache().getTacheId().getCodOperOper().toString() + 
              StrHandler.lpad(demandeCheque.getTache().getTacheId().getCodTachTach().toString(),'0',2));    
    }
    
    public IValueObject getNumeroDomaine(IValueObject vo){
        StructureDomaine structureDomaine = new StructureDomaine();
        DemandeCheque demandeCheque = (DemandeCheque)vo;
        structureDomaine.setCodDomDomm(Constants.COD_DOM_CONTRATCOMPTE);
        structureDomaine.setCodStrcStrc(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc());
        return structureDomaine;
    }
    
    
    public void genererSynchronisationPascal(ValueObject vo) {
    
        DemandeCheque demandeCheque = (DemandeCheque)vo; 
            
            DateFormat myformat1 = new SimpleDateFormat("yyMMdd");
            DateFormat myformat2 = new SimpleDateFormat("ddMMyyyy");
                 
            this.setCodeOperationSynch(new Long(9));
            this.setCodeTacheSynch(demandeCheque.getTache().getTacheId().getCodTachTach());
            this.setDateOperationSynch(new Date());
            this.setCodeStructureSynch(demandeCheque.getContratCpt().getContratCptId().getCodStrcStrc());
            
            String numCompte = StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getCodPrdPrd().toString(),'0',4) +
                               StrHandler.lpad(demandeCheque.getContratCpt().getContratCptId().getNumCcptCcpt().toString(),'0',6);
             
            String dateDemande = myformat1.format(demandeCheque.getDatDemDchq());
            String dateValidation = "      ";
            if(demandeCheque.getDatEnvDchq() != null )            
                dateValidation =  myformat1.format(demandeCheque.getDatEnvDchq());
            
            String nbreCarnet = StrHandler.lpad(demandeCheque.getNbrChqiDchq().toString(),'0',2);
            
            String typeCarnet = " ";
            if(demandeCheque.getNbrChqDchq().equals(Long.valueOf(25)))
               typeCarnet = "A";
            else if(demandeCheque.getNbrChqDchq().equals(Long.valueOf(50)))
                typeCarnet = "B";
            else typeCarnet = "C";
            
          /*  String dateReception = "        ";
            if(demandeCheque.getChequiers().size() > 0){            
                for (Iterator iterator = demandeCheque.getChequiers().iterator(); iterator.hasNext(); ) {
                    Chequier chequier = (Chequier)iterator.next();
                    if(chequier.getDatRecpChqi() != null){
                        dateReception = myformat2.format(chequier.getDatRecpChqi());
                        break;
                    }
                }
            }
            
            String dateRemise = "        ";
            if(demandeCheque.getChequiers().size() > 0){            
                for (Iterator iterator = demandeCheque.getChequiers().iterator(); iterator.hasNext(); ) {
                    Chequier chequier = (Chequier)iterator.next();
                    if(chequier.getCodEtatChqi().equals(Long.valueOf("2"))){
                        dateRemise = myformat2.format(chequier.getDatRemiChqi());
                        break;
                    }
                }
            }
            */
            
            String forcage = " ";
            if(demandeCheque.getBoolForcDchq()!= null && demandeCheque.getBoolForcDchq().equals(Long.valueOf("1")))
                forcage = "1";
            else forcage = " ";
            
           /* String flagRemise = " ";
            
            if((demandeCheque.getCodEtatDchq().equals(Long.valueOf("6") ) ||demandeCheque.getCodEtatDchq().equals(Long.valueOf("7") ) ) &&  demandeCheque.getChequiers().size() > 0){            
                int compteur = 0;
                for (Iterator iterator = demandeCheque.getChequiers().iterator(); iterator.hasNext(); ) {
                    Chequier chequier = (Chequier)iterator.next();
                    if(chequier.getCodEtatChqi().equals(Long.valueOf("2"))){                    
                        compteur++;                                    
                    }
                }
                
                if (compteur > 0)  flagRemise = "S";
                else flagRemise = "R"; 
            }*/
            
            String typeCheque = " " ;
            if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_STANDARD))
               typeCheque = "0";
            else if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_LETTRE_CHEQUE))
               typeCheque = "1";  
            else if(demandeCheque.getTypeConfection().getCodConfConf().equals(Constants.CODE_CHEQUE_PERSONALISE))
                typeCheque = "2";  
            
            GetPersonneTrt getPersonneTrt = new GetPersonneTrt();
            PersonneStrc personneStrc = new PersonneStrc();
            personneStrc.setCodTpceTpce(demandeCheque.getCodTpceDchq());
            personneStrc.setNumPcePers(demandeCheque.getNumPceDchq());
            Personne pers = (Personne)getPersonneTrt.exec(personneStrc);
            
            String nomDemandeur = "                                        ";            
            nomDemandeur = StrHandler.rpad(demandeCheque.getContratCpt().getNomIntiCcpt(),' ',40);
            
            
            String rue = "                                        ";
            String ville = "                    ";              
            String codePostal = "     ";
                  
            if(demandeCheque.getContratCpt().getAdresseCorresp().getRue() != null)
              rue = StrHandler.rpad(demandeCheque.getContratCpt().getAdresseCorresp().getRue(),' ',40); 
            
                
            if(demandeCheque.getContratCpt().getAdresseCorresp().getVille() != null)
             ville = StrHandler.rpad(demandeCheque.getContratCpt().getAdresseCorresp().getVille(),' ',20); 
            
            
            if(demandeCheque.getContratCpt().getAdresseCorresp().getCodCpCp() != null)
             codePostal = StrHandler.lpad(demandeCheque.getContratCpt().getAdresseCorresp().getCodCpCp(),'0',5); 
            
            
            //String partieVariable =  numCompte + dateDemande + dateValidation + nbreCarnet + typeCarnet +  forcage +  typeCheque + nomDemandeur + prenomDemandeur + rue + ville + codePostal;
            String partieVariable =  numCompte + nbreCarnet + typeCarnet + nomDemandeur +  rue + ville + codePostal + "  ";
            
            this.setTextSynch(partieVariable);
            
        }
        
    
}
