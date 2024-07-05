/*package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_project_master_log",schema="pms_log")
@Table(name="pms_project_master")
public class ProjectMasterDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//Corporate employee Id will be treated as primary Key
	
	@Id
	@Column(name="num_project_id")	
	private long numId;
	@Column(name="str_project_name")
	private String strProjectName;
	@Column(name="dt_work_order")
	private Date dtWorkOrderDate;
	@Column(name="num_project_cost")
	private long ProjectCost;
	@Column(name="dt_mou")
	private Date dtMOU;	
	@Column(name="num_project_duration")
	private int ProjectDuration;
	@Column(name="dt_project_start")
	private Date dtProjectStartDate;
	@Column(name="dt_project_end")
	private Date dtProjectEndDate;
	@Column(name="str_project_status")
	private String strProjectStatus;
	@Column(name="num_project_type")
	private int ProjectType;
	@Column(name="str_brief_description")
	private String strBriefDescription;
	@Column(name="str_project_objective")
	private String strProjectObjective;
	@Column(name="str_project_scope")
	private String strProjectScope;
	@Column(name="str_project_aim")
	private String strProjectAim;
	
}
*/


package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

import org.hibernate.annotations.Formula;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@Data
@Entity
@Audited
@AuditTable(value="pms_project_budget_master_log",schema="pms_log")
@Table(name="pms_project_budget_master")
public class ProjectBudgetDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;	

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectBudgetMaster")
	@TableGenerator(name="projectBudgetMaster", initialValue=1, allocationSize=1)
	@Column(name="num_id")	
	private long numId;
	
	@Column(name="num_amount")
	private Float numAmount;	
	@Column(name="num_year")
	private int numYear;
	@Column(name="num_budget_head_id")
	private int numBudgetHeadId;
	@ManyToOne(optional = false)
	@JoinColumn(name="num_project_id_fk")
	private ProjectMasterDomain projectMasterDomain;	

	
}
