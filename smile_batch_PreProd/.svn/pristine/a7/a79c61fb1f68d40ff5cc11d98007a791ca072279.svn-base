

package com.bna.smile.model.encaissementEffet.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bna.commun.model.EffetRecu;
import com.bna.commun.model.EffetRecuTmp;
import com.bna.smile.web.commun.model.ParamAgence;
import com.oxia.fwk.core.ValueObject;


public class ReceptionEffetVo  extends ValueObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date dateComptable;
	private String structure;
	private String codStrcBct ;
	private ParamAgence paramAgence;
	private List<EffetRecuTmp> listeEffetRecu = new ArrayList<EffetRecuTmp>();
	private List<EffetRecuTmp> listeEffetReglAuto = new ArrayList<EffetRecuTmp>();
	private Long mntTotEffetRecu = 0L ;
	private Long nbrTotEffetRecu = 0L ;
	private Long mntTotEffetReglAuto =0L ;
	private String etatEffet ;
	
	
	/**
	 * @return the dateComptable
	 */
	public Date getDateComptable() {
		return dateComptable;
	}
	
	/**
	 * @param dateComptable the dateComptable to set
	 */
	public void setDateComptable(Date dateComptable) {
		this.dateComptable = dateComptable;
	}
	
	/**
	 * @return the structure
	 */
	public String getStructure() {
		return structure;
	}
	
	/**
	 * @param structure the structure to set
	 */
	public void setStructure(String structure) {
		this.structure = structure;
	}
	
	/**
	 * @return the paramAgence
	 */
	public ParamAgence getParamAgence() {
		return paramAgence;
	}
	
	/**
	 * @param paramAgence the paramAgence to set
	 */
	public void setParamAgence(ParamAgence paramAgence) {
		this.paramAgence = paramAgence;
	}
	

	/**
	 * @param listeEffetRecu the listeEffetRecu to set
	 */
	public void setListeEffetRecu(List<EffetRecuTmp> listeEffetRecu) {
		this.listeEffetRecu = listeEffetRecu;
	}

	/**
	 * @return the listeEffetRecu
	 */
	public List<EffetRecuTmp> getListeEffetRecu() {
		return listeEffetRecu;
	}

	/**
	 * @param listeEffetReglAuto the listeEffetReglAuto to set
	 */
	public void setListeEffetReglAuto(List<EffetRecuTmp> listeEffetReglAuto) {
		this.listeEffetReglAuto = listeEffetReglAuto;
	}

	/**
	 * @return the listeEffetReglAuto
	 */
	public List<EffetRecuTmp> getListeEffetReglAuto() {
		return listeEffetReglAuto;
	}

	/**
	 * @param mntTotEffetRecu the mntTotEffetRecu to set
	 */
	public void setMntTotEffetRecu(Long mntTotEffetRecu) {
		this.mntTotEffetRecu = mntTotEffetRecu;
	}

	/**
	 * @return the mntTotEffetRecu
	 */
	public Long getMntTotEffetRecu() {
		return mntTotEffetRecu;
	}

	/**
	 * @param mntTotEffetReglAuto the mntTotEffetReglAuto to set
	 */
	public void setMntTotEffetReglAuto(Long mntTotEffetReglAuto) {
		this.mntTotEffetReglAuto = mntTotEffetReglAuto;
	}

	/**
	 * @return the mntTotEffetReglAuto
	 */
	public Long getMntTotEffetReglAuto() {
		return mntTotEffetReglAuto;
	}
	
	
	/**
	 * @return the mntTotEffetReglAuto
	 */
	public Integer getNbretTotEffetReglAuto() {
		return listeEffetReglAuto.size();
	}
	
	
	/**
	 * @return the mntTotEffetReglAuto
	 */
	public Integer getNbretTotEffetRecu() {
		return listeEffetRecu.size();
	}

	/**
	 * @param etatEffet the etatEffet to set
	 */
	public void setEtatEffet(String etatEffet) {
		this.etatEffet = etatEffet;
	}

	/**
	 * @return the etatEffet
	 */
	public String getEtatEffet() {
		return etatEffet;
	}

	/**
	 * @param codStrcBct the codStrcBct to set
	 */
	public void setCodStrcBct(String codStrcBct) {
		this.codStrcBct = codStrcBct;
	}

	/**
	 * @return the codStrcBct
	 */
	public String getCodStrcBct() {
		return codStrcBct;
	}

	public void setNbrTotEffetRecu(Long nbrTotEffetRecu) {
		this.nbrTotEffetRecu = nbrTotEffetRecu;
	}

	public Long getNbrTotEffetRecu() {
		return nbrTotEffetRecu;
	}




}
