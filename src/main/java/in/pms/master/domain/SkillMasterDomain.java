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
@AuditTable(value="pms_skill_mst_log",schema="pms_log")
@Table(name="pms_skill_mst")
public class SkillMasterDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="num_skill_id",length=50)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="skillGenerator")
	@TableGenerator(name="skillGenerator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_skill_name",length=50)
	private String strSkillName;
	
	}
