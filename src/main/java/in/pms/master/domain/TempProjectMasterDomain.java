package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_temp_project_master_log", schema = "pms_log")
@Table(name = "pms_temp_project_master")
public class TempProjectMasterDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = 3527993074609024305L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "projectMaster")
	@TableGenerator(name = "projectMaster", initialValue = 1, allocationSize = 1)
	@Column(name = "num_id")
	private long numId;
	
	@Column(name = "num_project_id", length = 300)
	private long numProjectId;
	
	@Column(name = "num_role_id", length = 300)
	private long numRoleId;
	
	@Column(name = "num_emp_id", length = 300)
	private long numEmpId;
	
	@Column(name="dt_project_closure")
	@Temporal(TemporalType.DATE)
	private Date projectClosureDate;
	
	@Column(name="str_closure_Remark",length = 2000)
	private String closureRemarks;
	
	
}
