package in.pms.transaction.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_deploymentTot_Details")
public class DeploymentTotDetailsDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 5500123230083297636L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="deployementTotDetails")
	@TableGenerator(name="deployementTotDetails", initialValue=1, allocationSize=1)
	@Column(name="num_deployment_id",length=10)	
	private long numDeploymentId;
	@Column(name="str_service_name",length=250)
	private String strServiceName;
	@Column(name="str_description",length=2000)
	private String strDescription;
	@Column(name="num_units_deployed_or_sold",length=10)
	private long numUnitsDeployedOrSold;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_deploymenttot_date")
	private Date dtDeploymenTotDate;
	@Column(name="str_agency_name",length=500)
	private String strAgencyName;
	@Column(name="num_agency_city")
	private String strAgencyCity;
	@Column(name="num_agency_state_id")
	private int numAgencyStateId;
	@Column(name="str_deployment_city")
	private String strDeploymentCity;
	@Column(name="num_deployment_state_id")
	private int numDeploymentStateId;
	/*@Column(name="num_group_category_id",length=18)
	private long numGroupCategoryId;*/
	@Column(name="str_document_ids",length=500)
	private String strDocumentIds;
	
	@ManyToOne
	@JoinColumn(name="num_progress_details_id_fk")
	private MonthlyProgressDetails monthlyProgressDetails;
	
	
	
	
	
	
	
	
}
