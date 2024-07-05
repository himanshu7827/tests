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
@AuditTable(value="pms_doc_stage_mst_log",schema="pms_log")
@Table(name="pms_doc_stage_mst")
public class DocStageMasterDomain extends TransactionInfoDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_stage_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="docStageMaster")
	@TableGenerator(name="docStageMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_stage_name",length=150)
	private String strStageName;
	@Column(name="str_stage_description",length=500)
	private String docStageDescription;
	
	
	
	

}
