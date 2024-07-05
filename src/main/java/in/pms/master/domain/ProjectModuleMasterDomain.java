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

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_proj_mod_log",schema="pms_log")
@Table(name="pms_proj_mod_mst")
public class ProjectModuleMasterDomain extends TransactionInfoDomain implements Serializable  {/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="num_module_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="str_module_name",length=250)
	private String strModuleName;
	
	@Column(name="str_module_description",length=2500)
	private String strModuleDescription;
	
	@Column(name="num_project_id",length=12)
	private long projectId;

}





	
	
	
	

