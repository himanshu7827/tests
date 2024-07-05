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
@Table(name="pms_project_budget_master_temp")
public class ProjectBudgetDomainTemp extends TransactionInfoDomain implements
		Serializable {


	private static final long serialVersionUID = 5401956659802734704L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectBudgetMasterTemp")
	@TableGenerator(name="projectBudgetMasterTemp", initialValue=1, allocationSize=1)
	@Column(name="num_id")	
	private long numId;
	
	@Column(name="num_amount")
	private Float numAmount;	
	@Column(name="num_year")
	private int numYear;
	@Column(name="num_budget_head_id")
	private int numBudgetHeadId;
	@JoinColumn(name="num_project_id")
	private long projectId;	
	
	
}
