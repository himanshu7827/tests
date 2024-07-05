package in.pms.master.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="employee_contract_detail_mst_log",schema="pms_log")
@Table(name="pms_employee_contract_detail_mst")
public class EmployeeContractDetailDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_contract_detail_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="empContractDetailMst")
	@TableGenerator(name="empContractDetailMst", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="dt_start")
	private Date startDate;
	@Column(name="dt_end")
	private Date endDate;
	
	@Column(name="str_remarks",length=50)
	private String strRemarks;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="num_employee_id_fk")
	private EmployeeMasterDomain employeeMasterDomain;
	
	
}
