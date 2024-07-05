package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.model.RoleMasterModel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_temp_employee_role_log",schema="pms_log")
@Table(name="pms_temp_employee_role_mst")
public class TempEmployeeRoleMasterDomain extends TransactionInfoDomain implements Serializable  {/**
	 * 
	 */ 
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="num_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="num_emp_id",length=12)
	private long numEmpId ;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	private RoleMasterDomain roleMasterDomain;
	
	@Column(name="num_project_id",length=12)
	private int numProjectId;
	
	@Column(name="num_group_id",length=12)
	private int numGroupId;
	
	@Column(name="num_organisation_id",length=12)
	private int numOrganisationId;
	
	/*@Column(name="num_application_id",length=12)
	private int numApplicationId;*/
	
	
	@ManyToOne(fetch = FetchType.LAZY,optional = true)
	@JoinColumn(name="num_id_fk")
	private ManpowerRequirementDomain manpowerRequirementDomain;
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_startDate")
	private Date dtStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_endDate")
	private Date dtEndDate;
	
	@Column(name="num_primary_project",columnDefinition="int default 0")
	private int primaryProject;
	
	
	@Column(name="str_manreq_details",length=500)
	private String strManReqDetails ;
	

	@Column(name="str_remarks",length=500)
	private String remarks;
	
	@Column(name="num_utilization",columnDefinition="int default 0")
	private double utilization;	
	
	@Column(name="num_emp_role_id")
	private long numEmpRoleId;
}