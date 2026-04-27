package com.bna.smile.model.traitementCompensationRecu.dao;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.Cheque;
import com.bna.commun.model.Cnp;
import com.bna.commun.model.ComplementCnp;
import com.bna.commun.model.ComplementCnpId;
import com.bna.commun.model.ComplementPapillon;
import com.bna.commun.model.ComplementPapillonId;
import com.bna.commun.model.Papillon;
import com.bna.commun.model.PapillonId;
import com.bna.commun.model.Personne;
import com.bna.commun.model.PieceAnnexe;
import com.bna.commun.model.Preavis;
import com.bna.commun.model.TypePiece;
import com.bna.commun.util.DateHandler;

public class RejetDAO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	protected String sqlQuery;
	/**
	 * 
	 */
	protected JdbcTemplate jt;
	/**
	 * 
	 */
	protected DataSource dataSource;

	/**
	 * 
	 */
	SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat formatter1=new SimpleDateFormat("ddMMyyyy");


	public RejetDAO() {
	}

	/**
	 * @param dataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param sqlQuery
	 */
	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	/**
	 * @return
	 */
	public Long getSequenceNumPapillon() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject(
				"select SEQ_NUM_PAP_PAP.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}
	
	/**
	 * @return
	 */
	public Long getSequenceNumCnp() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject(
				"select SEQ_NUM_CNP_CNP.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}
	public Long getSequenceMvtCompensation() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject("select SEQ_MVT_COM.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}
	public Long getSequenceBlocCheque() {
		jt = new JdbcTemplate(dataSource);

		Long numeroSequence = (Long) jt.queryForObject("select SEQ_BLOC_CHQ.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}
	public String getNumLotCheque(Long strc) {
		jt = new JdbcTemplate(dataSource);
		Long numLot = 1L;
		// SqlRowSet rs =
		// jt.queryForRowSet("select NUM_VAL_SEQA from SEQ_AGENCE where COD_STRC_STRC= "
		// + strc
		// + " and LIB_SEQ_SEQA='NUM_LOT_CHQ'");
		// if (rs.next()) {
		// numLot = rs.getLong("NUM_VAL_SEQA");
		// executerTransaction("update SEQ_AGENCE set NUM_VAL_SEQA=" + (numLot +
		// 1) + "  where COD_STRC_STRC= " + strc
		// + " and LIB_SEQ_SEQA='NUM_LOT_CHQ'");
		// return String.format("%04d", numLot);
		//
		// } else {
		// executerTransaction("insert into SEQ_AGENCE(COD_STRC_STRC,LIB_SEQ_SEQA,NUM_VAL_SEQA,NBR_FREQ_SEQA) values("
		// + strc + ",'NUM_LOT_CHQ'," + (numLot + 1) + ",'A')");
		// }

		return "0001";
	}
	
	public void initDbForMigration(String codStrc) {
//		Context context = ContextHandler.getContext();
//		HibernateTemplate htemplate = (HibernateTemplate) context.getBean("hibernateTemplate");
//		htemplate.
		jt = new JdbcTemplate(dataSource);
		
		
		
		System.out.println("End deleting from database .. OK");

		System.out.println("delete from error_migration  where substr(rribtir,6,3)='"+codStrc+"'");
		System.out.println("delete from migration_cheque where substr(rribtir,6,3)='"+codStrc+"'");
		System.out.println("delete from preavis where dat_mig_pre is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from decompte where dat_mig_dec is not null  and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from suivi_hn where dat_mig_hn is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from amende where dat_mig_amd is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from anr where dat_mig_anr is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from blocage_cheque where dat_mig_bloc is not null and cod_strc_strc="+codStrc);
		System.out.println("delete from arp where dat_mig_arp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from  complement_papillon where dat_mig_cmp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from papillon where dat_mig_pap is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from complement_cnp where dat_mig_cmp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from cnp where dat_mig_cnp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from trace_cheque where dat_mig_tch is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		System.out.println("delete from cheque where dat_mig_chq is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");

		
		jt.execute("delete from signataire  where substr(rib_tir_chq,6,3)='"+codStrc+"' and dat_mig_sig is not null");
		jt.execute("delete from error_migration  where substr(rribtir,6,3)='"+codStrc+"'");
		jt.execute("delete from migration_cheque where substr(rribtir,6,3)='"+codStrc+"'");
		jt.execute("delete from preavis where dat_mig_pre is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from decompte where dat_mig_dec is not null  and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from suivi_hn where dat_mig_hn is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from amende where dat_mig_amd is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from anr where dat_mig_anr is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from blocage_cheque where dat_mig_bloc is not null and cod_strc_strc="+codStrc);
		jt.execute("delete from arp where dat_mig_arp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from  complement_papillon where dat_mig_cmp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from papillon where dat_mig_pap is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from complement_cnp where dat_mig_cmp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from cnp where dat_mig_cnp is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from trace_cheque where dat_mig_tch is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");
		jt.execute("delete from cheque where dat_mig_chq is not null and substr(rib_tir_chq,6,3)='"+codStrc+"'");

		


		
		
	}
	
	public String getRibBenAdt(String numChq,String ribTir, String codStrcBct) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select rib_ben from ad_detail_cheque_30 where num_chq='"+numChq+"' and rib_tir='"+ribTir+"' and cod_sen = 2 and cod_age_des="+codStrcBct;
		System.out.println(sql);
		try {
			String ribBen = (String) jt.queryForObject(sql, String.class);
			if (ribBen!=null)
				return ribBen;
			else 
				return "00000000000000000000";
		}
		catch(Exception e) {
			return "00000000000000000000" ;
		}
		

}
	
	public String getNumPapAdt(String numChq,String ribTir,String codAge,String datOp ) throws ParseException {
		Date dateOp =formatter.parse(datOp) ;
		jt = new JdbcTemplate(dataSource);
		String sql = "select num_pap from ad_detail_papillon where rib_tir='"+ribTir+"' and num_chq='"+numChq+"' and dat_ope='"+DateHandler.dateToStr(dateOp)+"' and cod_age='"+codAge+"' and cod_sen=1";
		Long numPap= null;
		numPap = (Long) jt.queryForObject(sql, Long.class);
		if (numPap ==null)
			numPap=9999L;
		return ""+numPap;

}

	
	public String getDatPapAdt(String numChq,String ribTir,String codAge,String numPap ) throws ParseException {
		jt = new JdbcTemplate(dataSource);
		String sql = "select dat_ope from ad_detail_papillon where rib_tir='"+ribTir+"' and num_chq="+numChq+" and num_pap="+Long.valueOf(numPap)+" and cod_age='"+codAge+"' and cod_sen=1";
		List<Date>  datPap = new ArrayList<Date>();
		datPap = (List<Date>) jt.queryForList(sql, Date.class);
		if (datPap.isEmpty())
			return "";
		return formatter1.format(datPap.get(0));
			

			

}	
	
	public List<ComplementPapillon> getComplementPapillonAdt(final String numChq,final String ribTir,final String ribBen ,final String numPap, String codAge, String datOpe) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select COD_NAT_PER,COD_POS,NUM_ADR,COD_QUA,COD_TYP_PER,DAT_ETA,IDE_RCS,NOM_RS,NOM_RUE,NUM_BTE,NUM_PIE ,RAN_PAP,RIB_TIR,NUM_CHQ ,NUM_PAP from ad_complement_papillon where NUM_CHQ="+numChq+"  and RIB_TIR='"+ribTir+"' and COD_AGE="+codAge+" and COD_SEN=1 and DAT_OPE='"+datOpe+"'" ;//and NUM_PAP="+numPap+" 
		//System.out.println(sql);
		List<ComplementPapillon> complementPapillon= new ArrayList<ComplementPapillon>();
		complementPapillon =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			ComplementPapillon  complement=new ComplementPapillon();	
			complement.setCodNperCpap(rs.getLong("COD_NAT_PER"));
			complement.setCodPosCpap(rs.getString("COD_POS"));
			complement.setCodQuaCpap(rs.getLong("COD_QUA"));
			complement.setCodTperCpap(rs.getString("COD_TYP_PER"));
			complement.setDatOpeChq(rs.getDate("DAT_ETA"));
			complement.setIdeRcsCpap(rs.getString("IDE_RCS"));
			complement.setNomRsCpap(rs.getString("NOM_RS"));
			if (rs.getString("NOM_RUE") !=null && !rs.getString("NOM_RUE").equals(""))
				complement.setNomRueCpap(rs.getString("NOM_RUE"));
			else
				complement.setNomRueCpap(".");
			if (rs.getString("NUM_ADR")!= null)
				complement.setNumAdrCpap(rs.getString("NUM_ADR"));
			else
				complement.setNumAdrCpap(".");
			if (rs.getString("NUM_BTE")!=null)
				complement.setNumBteCpap(rs.getString("NUM_BTE"));
			else
				complement.setNumBteCpap(".");
			if (rs.getString("NUM_PIE")!=null)
				complement.setNumPieCpap(rs.getString("NUM_PIE"));
			else
				complement.setNumPieCpap(".");
			complement.setDatMigCmp(new Date());
			ComplementPapillonId id = new ComplementPapillonId(Long.valueOf(numChq), ribBen, ribTir, Long.valueOf(numPap), rs.getLong("RAN_PAP")) ;
			complement.setComplementPapillonId(id) ;

			return complement;
		}
	});	
	   if  (complementPapillon.isEmpty() )
		   return null;
	return complementPapillon;
}
	
	
	
	public List<ComplementCnp> getComplementCnpAdt(final String numChq,final String ribTir,final String ribBen ,final String numCnp, String codAge ,String datOpe) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select COD_NAT_PER,COD_POS,COD_QUA,COD_TYP_PER,DAT_OPE,NOM_PRN,NOM_RUE,NUM_PIE,NUM_ADR,REF_FIC,RAN_COM_CNP,IDE_RCS from ad_complement_cnp where num_chq="+numChq+"and num_cnp="+numCnp+"  and rib_tir='"+ribTir+"' and cod_age="+codAge+" and cod_sen=1 " ;
		//System.out.println(sql);
		List<ComplementCnp> complementCnp= new ArrayList<ComplementCnp>();
		complementCnp =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			ComplementCnp  complement=new ComplementCnp();	
			complement.setCodNperCcnp(0L);
			complement.setCodPosCcnp(rs.getString("COD_POS"));
			complement.setCodProf(".");
			complement.setCodQuaCcnp(rs.getLong("COD_QUA"));
			complement.setCodTperCcnp(rs.getString("COD_TYP_PER"));
			complement.setDatOpeChq(rs.getDate("DAT_OPE"));
			complement.setNatPersCcnp(rs.getString("COD_NAT_PER"));
			complement.setNomPrnCcnp(rs.getString("NOM_PRN"));
			complement.setNomRueCcnp(rs.getString("NOM_RUE"));
			if (rs.getString("NUM_ADR")!=null)
				complement.setNumAdrCcnp(rs.getString("NUM_ADR"));
			else
				complement.setNumAdrCcnp(".");
			complement.setNumBposCcnp(rs.getString("COD_POS"));
			if(rs.getString("NUM_PIE")!= null)
				complement.setNumPieCcnp(rs.getString("NUM_PIE"));
			else
				complement.setNumPieCcnp(".");
			complement.setRefFicCcnp(rs.getString("REF_FIC"));
			complement.setIdeRcsCcnp(rs.getString("IDE_RCS"));
			complement.setSigle(".");
			ComplementCnpId id = new ComplementCnpId(rs.getLong("RAN_COM_CNP"), Long.valueOf(numChq),ribTir, ribBen);
			complement.setComplementCnpId(id);

			return complement;
		}
	});	
	   if  (complementCnp.isEmpty() )
		   return null;
	return complementCnp;
}

	public Preavis getPreavisAdt(final String numChq,final String ribTir,final String ribBen , String codAge ,String datOpe) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select COD_MOT_REJ,DAT_OPE,DAT_PRE_AVI,MNT_PRO from ad_detail_preavis where num_chq="+numChq+"and rib_tir='"+ribTir+"' and cod_age="+codAge+" and cod_sen=1 " ;
		//System.out.println(sql);
		List<Preavis> lPreavis= new ArrayList<Preavis>();
		lPreavis =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			Preavis  preavis=new Preavis();	
			preavis.setCodMrejPre(rs.getString("COD_MOT_REJ"));
			preavis.setDatOpeChq(rs.getDate("DAT_OPE"));
			preavis.setDatPrePre(rs.getDate("DAT_PRE_AVI"));
			preavis.setMntProPre(rs.getLong("MNT_PRO"));
			return preavis;
		}
	});	
	   if  (lPreavis.isEmpty() )
		   return null;
	return lPreavis.get(0);
}
	
	public Cnp getCnpAdt(final String numChq,final String ribTir,final String ribBen , String codAge ,String datOpe) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select COD_MOT_REJ,DAT_ETA_CNP,DAT_OPE,NBR_ENR_COM,NUM_CNP,RAN_CHQ,REF_FIC from ad_detail_cnp where num_chq="+numChq+"and rib_tir='"+ribTir+"' and cod_age="+codAge+" and cod_sen=1 " ;
		//System.out.println(sql);
		List<Cnp> lCnp= new ArrayList<Cnp>();
		lCnp =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			Cnp cnp= new Cnp();
			cnp.setCodMotRej(rs.getString("COD_MOT_REJ"));
			cnp.setDatCnpCnp(rs.getDate("DAT_ETA_CNP"));
			cnp.setDatOpeChq(rs.getDate("DAT_OPE"));
			cnp.setNbrEnrCom(rs.getLong("NBR_ENR_COM"));
			cnp.setNumCnpCnp(rs.getLong("NUM_CNP"));
			cnp.setRanChqCnp(rs.getLong("RAN_CHQ"));
			cnp.setRefFic(rs.getString("REF_FIC"));
			cnp.setSigCheAge("?");
			cnp.setRefClePub(0L);
			
			
			return cnp;
		}
	});	
	   if  (lCnp.isEmpty() )
		   return null;
	return lCnp.get(0);
}
	
	
	public Papillon getPapillonAdt(final String numChq,final String ribTir,final String ribBen , String codAge ,String datOpe) {
		jt = new JdbcTemplate(dataSource);
		String sql = "select NUM_PAP,COD_MOT_REJ,DAT_OPE,DAT_ETA,NBR_ENR,RAN_PAP,REF_FIC from ad_detail_papillon where num_chq="+numChq+"and rib_tir='"+ribTir+"' and cod_age="+codAge+" and cod_sen=1 " ;
		//System.out.println(sql);
		List<Papillon> lPapillon= new ArrayList<Papillon>();
		lPapillon =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			Papillon papillon= new Papillon();
			if (rs.getString("COD_MOT_REJ")!=null)
				papillon.setCodMrejPap(rs.getString("COD_MOT_REJ"));
			else
				papillon.setCodMrejPap("99999999");
			papillon.setDatOpeChq(rs.getDate("DAT_OPE"));
			papillon.setDatPapPap(rs.getDate("DAT_ETA"));
			papillon.setNbrEnrPap(rs.getLong("NBR_ENR"));
			papillon.setRanPapPap(rs.getLong("RAN_PAP"));
			papillon.setRefFicPap(rs.getString("REF_FIC"));
			PapillonId id = new PapillonId(Long.valueOf(numChq), ribBen, ribTir, rs.getLong("NUM_PAP"));
			papillon.setValeurCodValVal(0L);
			papillon.setPapillonId(id);			
			return papillon;
		}
	});	
	   if  (lPapillon.isEmpty() )
		   return null;
	return lPapillon.get(0);
}

	
	public List<ComplementCnp> getComplementCnpAdt(final Cnp cnp, String datOpeAdt) throws ParseException { // datOpAdt : dat credit cnp
		jt = new JdbcTemplate(dataSource);
		//String sql = "select COD_NAT_PER,COD_POS,COD_QUA,COD_TYP_PER,DAT_OPE,NOM_PRN,NOM_RUE,NUM_PIE,NUM_ADR,REF_FIC,RAN_COM_CNP,IDE_RCS from ad_complement_cnp where num_chq="+cnp.getChequeId().getNumChqChq()+"and num_cnp="+cnp.getNumCnpCnp()+"  and rib_tir='"+cnp.getChequeId().getRibTirChq()+"' and cod_age="+cnp.getCheque().getCodAgdeChq();//+" and cod_sen=1 and REF_FIC='"+cnp.getRefFic()+"'" ;
		//String sql = "select COD_NAT_PER,COD_POS,COD_QUA,COD_TYP_PER,DAT_OPE,NOM_PRN,NOM_RUE,NUM_PIE,NUM_ADR,REF_FIC,RAN_COM_CNP,IDE_RCS from ad_complement_cnp where num_chq="+cnp.getChequeId().getNumChqChq()+"and num_cnp="+cnp.getNumCnpCnp()+"  and rib_tir='"+cnp.getChequeId().getRibTirChq()+"' and cod_age="+cnp.getCheque().getCodAgdeChq()+" and dat_ope='"+formatter.format(formatter1.parse(datOpeAdt))+"' and cod_sen=1";//+" and cod_sen=1 and REF_FIC='"+cnp.getRefFic()+"'" ;
		
		
		String sql = "select COD_NAT_PER,COD_POS,COD_QUA,COD_TYP_PER,DAT_OPE,NOM_PRN,NOM_RUE,NUM_PIE,NUM_ADR,REF_FIC,RAN_COM_CNP,IDE_RCS from ad_complement_cnp where num_chq="+cnp.getChequeId().getNumChqChq()+"and num_cnp="+cnp.getNumCnpCnp()+"  and rib_tir='"+cnp.getChequeId().getRibTirChq()+"' and cod_age="+cnp.getCheque().getCodAgdeChq()+" and cod_sen=1";//+" and cod_sen=1 and REF_FIC='"+cnp.getRefFic()+"'" ;
		List<ComplementCnp> complementCnp= new ArrayList<ComplementCnp>();
		complementCnp =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			ComplementCnp  complement=new ComplementCnp();	
			complement.setCodNperCcnp(0L);
			complement.setCodPosCcnp(rs.getString("COD_POS"));
			complement.setCodProf("0000");
			complement.setCodQuaCcnp(rs.getLong("COD_QUA"));
			complement.setCodTperCcnp(rs.getString("COD_TYP_PER"));
			complement.setDatOpeChq(rs.getDate("DAT_OPE"));
			complement.setNatPersCcnp(rs.getString("COD_NAT_PER"));
			complement.setNomPrnCcnp(rs.getString("NOM_PRN"));
			if (rs.getString("NOM_RUE")!=null)
				complement.setNomRueCcnp(rs.getString("NOM_RUE"));
			else
				complement.setNomRueCcnp("0000");
			if (rs.getString("NUM_ADR")!=null)
				complement.setNumAdrCcnp(rs.getString("NUM_ADR"));
			else
				complement.setNumAdrCcnp("0000");
			complement.setNumBposCcnp(rs.getString("COD_POS"));
			if(rs.getString("NUM_PIE")!= null)
				complement.setNumPieCcnp(rs.getString("NUM_PIE"));
			else
				complement.setNumPieCcnp("0000");
			complement.setRefFicCcnp(rs.getString("REF_FIC"));
			complement.setIdeRcsCcnp(rs.getString("IDE_RCS"));
			complement.setSigle("0000");
			ComplementCnpId id = new ComplementCnpId(rs.getLong("RAN_COM_CNP"), cnp.getChequeId().getNumChqChq(),cnp.getChequeId().getRibTirChq(), cnp.getChequeId().getRibBenChq());
			complement.setComplementCnpId(id);

			return complement;
		}
	});	
	   if  (complementCnp.isEmpty() )
		   return null;
	return complementCnp;
}	

	public List<ComplementPapillon> getComplementPapillonAdt(final Papillon pap) {
		jt = new JdbcTemplate(dataSource);
		//String sql = "select COD_NAT_PER,COD_POS,NUM_ADR,COD_QUA,COD_TYP_PER,DAT_ETA,IDE_RCS,NOM_RS,NOM_RUE,NUM_BTE,NUM_PIE ,RAN_PAP,RIB_TIR,NUM_CHQ ,NUM_PAP from ad_complement_papillon where NUM_CHQ="+pap.getPapillonId().getNumChqChq()+"  and RIB_TIR='"+pap.getPapillonId().getRibTirChq()+"' and COD_AGE="+pap.getCheque().getCodAgdeChq()+" and COD_S*EN=1 /*and REF_FIC='"+pap.getRefFicPap()+"'*/ and num_pap="+pap.getPapillonId().getNumPapPap();
		String sql = "select COD_NAT_PER,COD_POS,NUM_ADR,COD_QUA,COD_TYP_PER,DAT_ETA,IDE_RCS,NOM_RS,NOM_RUE,NUM_BTE,NUM_PIE ,RAN_PAP,RIB_TIR,NUM_CHQ ,NUM_PAP from ad_complement_papillon where NUM_CHQ="+pap.getPapillonId().getNumChqChq()+"  and RIB_TIR='"+pap.getPapillonId().getRibTirChq()+"' and COD_AGE="+pap.getCheque().getCodAgdeChq()+" and COD_SEN=1 and num_pap="+pap.getPapillonId().getNumPapPap()+" and  dat_ope='"+formatter.format(pap.getDatOpeChq())+"'";
		//System.out.println(sql);
		List<ComplementPapillon> complementPapillon= new ArrayList<ComplementPapillon>();
		complementPapillon =  jt.query(sql, new RowMapper() {		
		public Object mapRow(ResultSet rs, int rownum) throws SQLException {
			ComplementPapillon  complement=new ComplementPapillon();	
			complement.setCodNperCpap(rs.getLong("COD_NAT_PER"));
			complement.setCodPosCpap(rs.getString("COD_POS"));
			complement.setCodQuaCpap(rs.getLong("COD_QUA"));
			complement.setCodTperCpap(rs.getString("COD_TYP_PER"));
			complement.setDatOpeChq(rs.getDate("DAT_ETA"));
			complement.setIdeRcsCpap(rs.getString("IDE_RCS"));
			complement.setNomRsCpap(rs.getString("NOM_RS"));
			if(rs.getString("NOM_RUE")!= null)
				complement.setNomRueCpap(rs.getString("NOM_RUE"));
			else
				complement.setNomRueCpap("0000");
			if (rs.getString("NUM_ADR")!= null)
				complement.setNumAdrCpap  (rs.getString("NUM_ADR"));
			else
				complement.setNumAdrCpap("0000");
			if (rs.getString("NUM_BTE")!=null)
				complement.setNumBteCpap(rs.getString("NUM_BTE"));
			else
				complement.setNumBteCpap("0000");
			if (rs.getString("NUM_PIE")!=null)
				complement.setNumPieCpap(rs.getString("NUM_PIE"));
			else
				complement.setNumPieCpap("0000");
			ComplementPapillonId id = new ComplementPapillonId(pap.getPapillonId().getNumChqChq(), pap.getPapillonId().getRibBenChq(), pap.getPapillonId().getRibTirChq(), pap.getPapillonId().getNumPapPap(), rs.getLong("RAN_PAP")) ;
			complement.setComplementPapillonId(id) ;

			return complement;
		}
	});	
	   if  (complementPapillon.isEmpty() )
		   return null;
	return complementPapillon;
}
	
	
	public boolean verifExist32Adt(Cheque chq ) throws ParseException {
		jt = new JdbcTemplate(dataSource);
		String sql = "select * from ad_detail_cheque_32 where rib_tir='"+chq.getChequeId().getRibTirChq()+"' and num_chq="+chq.getChequeId().getNumChqChq()+" and rib_ben='"+chq.getChequeId().getRibBenChq()+"' and cod_age_des='"+chq.getCodAgdeChq()+"' and  cod_sen=2 and cod_enr=21";
		System.out.println(sql);
		List  lChq= new ArrayList();
		lChq =  jt.queryForList(sql);
		if (!lChq.isEmpty()) {
			String sql2 = "select * from ad_detail_cheque_32 where rib_tir='"+chq.getChequeId().getRibTirChq()+"' and num_chq="+chq.getChequeId().getNumChqChq()+" and rib_ben='"+chq.getChequeId().getRibBenChq()+"' and cod_age='"+chq.getCodAgdeChq()+"' and  cod_sen=1 and cod_enr=22";
			System.out.println(sql2);
			List  lChq2= new ArrayList();
			lChq2 =  jt.queryForList(sql2);
			if(lChq2.isEmpty()){
				return true;
			}

		}
			return false;
		}
	
	public boolean verifExist33Adt(Cheque chq ) throws ParseException {
		jt = new JdbcTemplate(dataSource);
		String sql = "select * from ad_detail_cheque_33 where rib_tir='"+chq.getChequeId().getRibTirChq()+"' and num_chq="+chq.getChequeId().getNumChqChq()+" and rib_ben='"+chq.getChequeId().getRibBenChq()+"' and cod_age_des='"+chq.getCodAgdeChq()+"' and  cod_sen=2 and cod_enr=21";
		System.out.println(sql);
		List  lChq= new ArrayList();
		lChq =  jt.queryForList(sql);
		if (!lChq.isEmpty()) {
			String sql2 = "select * from ad_detail_cheque_33 where rib_tir='"+chq.getChequeId().getRibTirChq()+"' and num_chq="+chq.getChequeId().getNumChqChq()+" and rib_ben='"+chq.getChequeId().getRibBenChq()+"' and cod_age='"+chq.getCodAgdeChq()+"' and cod_sen=1 and cod_enr=22";
			System.out.println(sql2);
			List  lChq2= new ArrayList();
			lChq2 =  jt.queryForList(sql2);
			if(lChq2.isEmpty()){
				return true;
			}

		}
			return false;
		}
	
	public boolean verifArpCci(Cheque chq ) throws ParseException {
		jt = new JdbcTemplate(dataSource);
		String sql = "select * from det_arp where rib_tir="+chq.getChequeId().getRibTirChq()+" and num_chq="+chq.getChequeId().getNumChqChq()+" and rib_benef="+chq.getChequeId().getRibBenChq()+"";
		System.out.println(sql);
		List  lChq= new ArrayList();
		lChq =  jt.queryForList(sql);
		if (!lChq.isEmpty()) {
				return true;
			}
			return false;
		}

	public Personne getPersonneByNumPiece(Personne pers) {
		
		String sql= "select num_seq_pers, cod_tpce_tpce  from personne where lpad('"+pers.getNumPcePers()+"',15,'0')= lpad(num_pce_pers,15,'0')" ;
		System.out.println(sql);
		List<Personne> personne =   jt.query(sql, new RowMapper() {		
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				Personne  p=new Personne();	
				PieceAnnexe piece = new PieceAnnexe();
				TypePiece typePiece= new TypePiece();
				typePiece.setCodTpceTpce(rs.getLong("cod_tpce_tpce")) ;
				p.setTypePiece(typePiece) ;
				p.setNumSeqPers(rs.getLong("num_seq_pers"));
		return p;
			}
		});	
		return personne.get(0);
	}
	
	
	public String getAgentEconomique(Personne person) {

		jt = new JdbcTemplate(dataSource);
		String sqlReq = "select nvl(cod_nat_clt,'0')  from agent_economique  a inner join personne  p on "
				+ "a.cod_cag_econ = p.cod_cag_econ and p.cod_ag_econ=a.cod_ag_econ where p.num_seq_pers="
				+ person.getNumSeqPers();
		String cod_nat = (String) jt.queryForObject(sqlReq, String.class);
		if (cod_nat != null && cod_nat.equals("F")) {
				return "2";
		}
			return "1";
		
	}
}
