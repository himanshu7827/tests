package in.pms.global.domain;

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



@Getter
@Setter
@Entity
@Table(name = "pms_workflow_master")
public class WorkflowMasterDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 3040025054863659220L;
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="workflowMaster")
	@TableGenerator(name="workflowMaster", initialValue=1, allocationSize=1)
	@Column(name="num_id",length=10)	
	private long numWorkflowId;
	@Column(name="str_type",length=250,unique=true)
	private String strType;
	
	
}
