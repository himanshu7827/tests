package in.pms.master.domain;

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

import in.pms.global.domain.TransactionInfoDomain;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_process_master_log",schema="pms_log")
@Table(name="pms_process_master")
public class ProcessMasterDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3724774084828868997L;
	
	@Id
	@Column(name="NUM_PROCESS_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="organisationMaster")
	@TableGenerator(name="organisationMaster", initialValue=1, allocationSize=1)
	private long numProcessId;
	
	@Column(name="STR_PROCESS_NAME")
	private String strProcessName;
	
	@Column(name="STR_PROCESS_PATH")
	private String strProcessPath;
	
	@Column(name="STR_PROCESS_ECODE")
	private String strProcessEcode;
	
	@Column(name="STR_PROCESS_DESCRIPTION")
	public String strProcessDescription;

}
