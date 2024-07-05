package in.pms.master.domain;

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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Domain for Subtask table by Himanshu on 10-05-2024
@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_timesheet_project_sub_task_master_log",schema="pms_log")
@Table(name="pms_timesheet_project_sub_task_master")
public class SubTaskMasterDomain extends TransactionInfoDomain implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -986986791115553119L;
	
	@Id
	@Column(name="num_project_sub_task_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="subtaskMaster")
	@TableGenerator(name="subtaskMaster", initialValue=1, allocationSize=1)
	private long subtaskId;
	@Column(name="str_project_sub_task_name")
	private String subtaskName;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="num_project_task_id")
	private TaskMasterDomain taskMasterDomain;
	

}
