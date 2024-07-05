package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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
@AuditTable(value="project_budget_head_mst_log",schema="pms_log")
@Table(name="pms_project_budget_head_mst")
public class BudgetHeadMasterDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_budget_head_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_budget_head_name", length=100)
	private String strBudgetHeadName;
	@Column(name="str_description" , length=1000)
	private String strDescription;
	
	
}
