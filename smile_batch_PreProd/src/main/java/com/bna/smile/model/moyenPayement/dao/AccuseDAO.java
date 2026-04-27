package com.bna.smile.model.moyenPayement.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bna.commun.model.ConsultationRapport;
import com.bna.commun.util.DateHandler;
import com.bna.smile.model.moyenPayement.model.Accuse;

public class AccuseDAO {

	protected String sqlQuery;
	protected JdbcTemplate jt;
	protected DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public List getAccuseByStructure(Long codeStructure, Date dateChargement) {
		List<Accuse> listAccuses = new ArrayList<Accuse>();
		jt = new JdbcTemplate(dataSource);
		String req1 = "select COD_BCT_STRC from structure where COD_STRC_STRC="
				+ codeStructure;
		Long codeBct = jt.queryForLong(req1);

		 String req
		 ="select cod_age,cod_val,lib_val_val, cod_enr, num_lot, sum(nbr_tot) nbr_tot, sum(mnt_tot) mnt_tot "
		 +
		 " from (select v.cod_age,v.cod_val,val.lib_val_val , v.cod_enr, v.num_lot, v.nbr_tot, v.mnt_tot "
		 +
		 " from ad_virement v,valeur val where v.cod_val=val.cod_val_val " +
		 " and v.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'" +
		 " and v.cod_sen=1 and v.cod_age="+codeBct +
		 "  and v.cod_enr=11 " +
		 " and v.ref_lot is not null " +
		 " UNION " +
		 " select vs.cod_age,vs.cod_val,val.lib_val_val , vs.cod_enr,vs.num_lot, vs.nbr_tot, vs.mnt_tot "
		 +
		 " from ad_virement_sgmt vs,valeur val where vs.cod_val=val.cod_val_val "
		 +
		 " and vs.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"+
		 " and vs.cod_sen=1 and vs.cod_age="+codeBct +
		 "  and vs.cod_enr=11 " +
		 " and vs.ref_lot is not null " +
		 " union all " +
		 " select p.cod_age,p.cod_val,val.lib_val_val, p.cod_enr,p.num_lot,p.nbr_tot ,p.mnt_tot  "
		 +
		 " from ad_prelevement p ,valeur val where p.cod_val=val.cod_val_val and p.cod_sen=1 "
		 +
		 " and p.cod_enr=12 and p.cod_age="+codeBct +
		 " and p.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'" +
		 " and p.ref_lot is not null " +
		 " union all " +
		
		 " select distinct ac30.cod_age,ac30.cod_val, val.lib_val_val,ac30.cod_enr,sum(ac30.num_lot) as num_lot,"
		 +
		 " sum(ac30.nbr_tot) as nbr_tot ,sum(ac30.mnt_tot) as mnt_tot  " +
		 " from ad_cheque_30 ac30 ,valeur val where ac30.cod_val=val.cod_val_val "
		 +
		 " and ac30.cod_sen=1 and ac30.cod_enr=11 and ac30.cod_age="+codeBct +
		 "  and ac30.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"
		 +
		 " and ac30.ref_fic not in (select new_lot from ad_lot_recycle)" +
		 "group by ac30.cod_age,ac30.cod_val, val.lib_val_val,ac30.cod_enr" +
		 // " and ac30.ref_lot is not null " +
		 " union all" +
		 " select distinct ac31.cod_age,ac31.cod_val, val.lib_val_val,ac31.cod_enr,sum(ac31.num_lot) as num_lot,"
		 +
		 " sum(ac31.nbr_tot) as nbr_tot ,sum(ac31.mnt_tot) as mnt_tot  " +
		 " from ad_cheque_31 ac31 ,valeur val where ac31.cod_val=val.cod_val_val"
		 +
		 " and ac31.cod_sen=1 and ac31.cod_enr=11 and ac31.cod_age="+codeBct +
		 "   and ac31.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"
		 +
		 "  and ac31.ref_fic not in (select new_lot from ad_lot_recycle)" +
		 "group by ac31.cod_age,ac31.cod_val, val.lib_val_val,ac31.cod_enr" +
		 // " and ac31.ref_lot is not null " +
		 " union all " +
		 " select distinct ac32.cod_age,ac32.cod_val, val.lib_val_val,ac32.cod_enr,sum(ac32.num_lot) as num_lot,"
		 +
		 " sum(ac32.nbr_tot) as nbr_tot ,sum(ac32.mnt_tot) as mnt_tot  " +
		 " from ad_cheque_32 ac32 ,valeur val where ac32.cod_val=val.cod_val_val"
		 +
		 " and  ac32.cod_sen=1 and ac32.cod_enr=11 and ac32.cod_age="+codeBct
		 +
		 "  and ac32.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"
		 +
		 "  and ac32.ref_fic not in (select new_lot from ad_lot_recycle) " +
		 "group by ac32.cod_age,ac32.cod_val, val.lib_val_val,ac32.cod_enr" +
		 //" and ac32.ref_lot is not null " +
		 " union all" +
		 " select distinct ac33.cod_age,ac33.cod_val, val.lib_val_val,ac33.cod_enr,sum(ac33.num_lot) as num_lot,"
		 +
		 " sum(ac33.nbr_tot) as nbr_tot ,sum(ac33.mnt_tot) as mnt_tot  " +
		 " from ad_cheque_33 ac33 ,valeur val where ac33.cod_val=val.cod_val_val"
		 +
		 " and ac33.cod_sen=1 and ac33.cod_enr=11 and ac33.cod_age="+codeBct +
		 "  and ac33.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"
		 +
		 " and ac33.ref_fic not in (select new_lot from ad_lot_recycle) " +
		 "group by ac33.cod_age,ac33.cod_val, val.lib_val_val,ac33.cod_enr" +
		 //" and ac33.ref_lot is not null " +
		 " union all" +
		 " select acnp.cod_age,acnp.cod_val,val.lib_val_val, acnp.cod_enr,acnp.num_lot,acnp.nbr_tot ,acnp.mnt_tot"
		 +
		 " from ad_cnp acnp  ,valeur val where acnp.cod_val=val.cod_val_val" +
		 " and acnp.cod_sen=1 and acnp.cod_enr=11 and acnp.cod_age="+codeBct +
		 " and acnp.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'" +
		 //" and acnp.ref_lot is not null " +
		 " union all" +
		 " select arp.cod_age,arp.cod_val,val.lib_val_val, arp.cod_enr,arp.num_lot,arp.nbr_tot ,arp.mnt_tot"+
		 " from ad_arp arp,valeur val where arp.cod_val=val.cod_val_val" +
		 " and arp.cod_sen=1 and arp.cod_enr=11 and arp.cod_age="+codeBct +
		 "  and arp.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'" +
		 //" and arp.ref_lot is not null " +
		 " union all"+
		 " select ap.cod_age,ap.cod_val,val.lib_val_val, ap.cod_enr,ap.num_lot,ap.nbr_tot ,ap.mnt_tot"+
		 " from ad_papillon ap ,valeur val where ap.cod_val=val.cod_val_val" +
		 " and ap.cod_sen=1 and ap.cod_enr=11 and ap.cod_age="+codeBct +
		 "  and ap.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'" +
		
		 //" and ap.ref_lot is not null " +
		 " union all" +
		 " select apre.cod_age,apre.cod_val,val.lib_val_val,apre.cod_enr,apre.num_lot,apre.nbr_tot ,apre.mnt_tot"
		 +
		 " from ad_preavis apre,valeur val where apre.cod_val=val.cod_val_val"
		 +
		 " and apre.cod_sen=1 and apre.cod_enr=11 and apre.cod_age="+codeBct +
		 "  and apre.dat_ope='" + DateHandler.dateToStr(dateChargement) + "'"
		 +
		 //" and apre.ref_lot is not null " +
		 " ) group by  cod_val,num_lot, cod_enr,lib_val_val,cod_age  order by cod_val asc";

		

		listAccuses = jt.query(req, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Accuse accuse = new Accuse();
				NumberFormat format = new DecimalFormat("#,##0.000");
				accuse.setCodeValeur(rs.getLong("COD_VAL"));
				accuse.setLibValeur(rs.getString("LIB_VAL_VAL"));
				accuse.setCodeTypeValeur(rs.getLong("COD_ENR"));
				if (accuse.getCodeValeur() == 30
						|| accuse.getCodeValeur() == 31
						|| accuse.getCodeValeur() == 32
						|| accuse.getCodeValeur() == 33) {
					accuse.setNumeroLot(null);
				} else {
					accuse.setNumeroLot(rs.getLong("NUM_LOT"));
				}

				accuse.setMntPresentation(format.format(Double.parseDouble(rs
						.getString("MNT_TOT"))));
				accuse.setNbrePresentation(rs.getLong("NBR_TOT"));
				accuse.setCodeBct(rs.getString("COD_AGE"));

				return accuse;
			}

		});

		return listAccuses;
	}

	public Long getSequenceRefConsultation() {
		jt = new JdbcTemplate(dataSource);
		Long numeroSequence = (Long) jt.queryForObject(
				"select SEQ_REF_CONS_ACCU.NEXTVAL from dual ", Long.class);
		return numeroSequence;
	}

	public Long getRapportIdByName(String nomRapport) {
		jt = new JdbcTemplate(dataSource);
		String requete = "select ID_RAPPORT from CONSULTATION_RAPPORT where NOM_NOM_RAPP='"
				+ nomRapport + "'";
		Long numeroSequence = (Long) jt
				.queryForLong("select ID_RAPPORT from CONSULTATION_RAPPORT where NOM_NOM_RAPP='"
						+ nomRapport + "'");
		return numeroSequence;
	}

	public ConsultationRapport getRapportByName(String nomRapport) {
		jt = new JdbcTemplate(dataSource);
		String requete = "select ID_RAPPORT from CONSULTATION_RAPPORT where NOM_NOM_RAPP='"
				+ nomRapport + "'";
		ConsultationRapport rapport = (ConsultationRapport) jt.queryForObject(
				"select * from CONSULTATION_RAPPORT where NOM_NOM_RAPP='"
						+ nomRapport + "'", ConsultationRapport.class);

		return rapport;
	}
}
