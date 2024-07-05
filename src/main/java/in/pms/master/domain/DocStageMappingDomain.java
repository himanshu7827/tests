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

import org.hibernate.envers.AuditOverride;
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
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_doc_stage_map_log",schema="pms_log")
@Table(name="pms_doc_stage_map")

public class DocStageMappingDomain extends TransactionInfoDomain implements Serializable  {/**
	 * 
	 */
	

	private static final long serialVersionUID = 1209644488465623773L;

	@Id
	@Column(name="num_doc_stage_map_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="is_mandatory")
	private boolean mandatory;
	
	@Column(name="num_doc_seq")
	private long docSeq;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="doc_type_id")
	private DocumentTypeMasterDomain documentTypeMasterDomain;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="doc_stage_id")
	private DocStageMasterDomain docStageMasterDomain;
	
	

}
