package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_Tot_Details")
public class TotDetailsDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5500123230083297636L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="totDetails")
	@TableGenerator(name="totDetails", initialValue=1, allocationSize=1)
	@Column(name="num_Tot_id",length=10)	
	private long numTotId;
	
	@Column(name="str_description",length=2000)
	private String strDescription;
	
	@Column(name="dt_deploymenttot_date")
	private Date dtDeploymenTotDate;
	@Column(name="str_agency_name",length=500)
	private String strAgencyName;
	@Column(name="num_product_name",length=500)
	private String strProductName;
	
	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
	
	
	
	
	
	
	
	
}
