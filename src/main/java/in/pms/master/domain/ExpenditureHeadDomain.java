package in.pms.master.domain;

import java.io.Serializable;

import in.pms.global.domain.TransactionInfoDomain;

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

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_expenditure_head_mst_log",schema="pms_log")
@Table(name="pms_expenditure_head_master")

public class ExpenditureHeadDomain extends TransactionInfoDomain implements Serializable {
	
	/**
	 * 
	 */
	
private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_expenditure_head_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="expenditureHeadMaster")
	@TableGenerator(name="expenditureHeadMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_expenditure_head_name",length=150)
	private String strExpenditureHeadName;
	
	

}



	

	
	

