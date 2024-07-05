package in.pms.master.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.Application;
import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_project_master_log", schema = "pms_log")
@Table(name = "pms_project_master")
public class ProjectMasterDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = 3527993074609024305L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "projectMaster")
	@TableGenerator(name = "projectMaster", initialValue = 1, allocationSize = 1)
	@Column(name = "num_project_id")
	private long numId;
	@Column(name = "str_project_name", length = 300)
	private String strProjectName;
	@Column(name = "dt_work_order")
	private Date dtWorkOrderDate;
	@Column(name = "num_project_cost")
	private double projectCost;
	@Column(name = "dt_mou")
	private Date dtMOU;
	/*@Column(name = "num_project_duration")
	private int ProjectDuration;*/
	@Column(name = "dt_project_start")
	private Date dtProjectStartDate;
	@Column(name = "dt_project_end")
	private Date dtProjectEndDate;
	@Column(name = "str_project_status", length = 20)
	private String strProjectStatus;
	@Column(name = "str_project_type", length = 20)
	private String projectType;
	@Column(name = "str_brief_description",columnDefinition = "TEXT")
	private String strBriefDescription;
	@Column(name = "str_project_objective",columnDefinition = "TEXT")
	private String strProjectObjective;
	@Column(name = "str_project_scope", columnDefinition = "TEXT")
	private String strProjectScope;
	@Column(name = "str_project_aim", length = 2000)
	private String strProjectAim;
	@Column(name = "str_project_remarks", length = 2000)
	private String strProjectRemarks;
	@Column(name = "str_funded_scheme", length = 2000)
	private String strFundedScheme;
	
	@ManyToOne(optional = false)
	@JoinTable(name = "application_project",joinColumns=@JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "application_id"))
	@NotAudited
	private Application application;

	@ManyToMany
	@JoinTable(name = "project_contact_person", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "num_contact_id"))
	private Set<ContactPersonMasterDomain> contactMaster;
	
	@Column(name="str_project_ref_no",length=30)
	private String strProjectRefNo;
	
	@Column(name="str_administration_no",length=50)
	private String administrationNo;
	
	@Column(name = "str_description", length = 6000)
	private String strdescription;
	
	@Column(name="dt_project_closure")
	@Temporal(TemporalType.DATE)
	private Date projectClosureDate;
	
	@Column(name="str_closure_Remark",length = 2000)
	private String closureRemarks;
	
	@Column(name="num_corp_monthly_sharing", columnDefinition = "int default 1")
	private int corpMonthlySharing;
	@Column(name = "str_gst", length = 25)
	private String strGST;
	@Column(name = "str_tan", length = 25)
	private String strTAN;
	
	@Column(name="dt_financial_closure")
	@Temporal(TemporalType.DATE)
	private Date financialClosureDate;
	
	@Column(name="str_financial_closure_remark",length=300)	
	private String financialClosureRemark;
	
	@Column(name="str_closure_status",length=20)
	private String closureStatus;
	
	@Column(name="dt_technical_closure")
	@Temporal(TemporalType.DATE)
	private Date TechnicalClosureDate;
	
	@Column(name="str_financial_validate_remark",length=300)	
	private String validatedRemarks;
	
	@Column(name="str__validate_status")	
	private String validateStatus;
	
	@Column(name="dt__validate_date")	
	private Date validateDate;
	
}
