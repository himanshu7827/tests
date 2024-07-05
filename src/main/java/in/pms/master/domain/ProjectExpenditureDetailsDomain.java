package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.master.model.ProjectMasterModel;
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
import javax.validation.constraints.Digits;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_project_expenditure_details_log",schema="pms_log")
@Table(name="pms_project_expenditure_details")

public class ProjectExpenditureDetailsDomain extends TransactionInfoDomain implements Serializable {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="num_project_expenditure_details_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	
	//@ManyToOne(optional = false)
	/*@Column(name="num_project_id")
	private long numProjectId;*/
	
	@Column(name="num_budget_head_id",length=12)
	private long numBudgetHeadId;
	
	@Column(name="num_expenditure_head_id",length=12)
	private long numExpenditureHeadId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_expenditureDate")
	private Date dtExpenditureDate;
	
	@Column(name="num_year",length=12)
	private long numYear;
	
	@Digits(integer=10,fraction=2)
	@Column(name="num_expenditure_amount",precision=19,scale=2)
	private Double numExpenditureAmount;
	
	@Column(name="str_expenditure_description",length=2000)
	private String strExpenditureDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "num_project_id")	
	ProjectMasterDomain projectMasterDomain;

}
