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
@AuditTable(value="pms_module_type_log",schema="pms_log")
@Table(name="pms_module_type_master",schema="pms")
public class ModuleTypeMaster extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -5077484059429321327L;
	
	@Id
	@Column(name="num_module_type_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="moduleTypeMaster")
	@TableGenerator(name="moduleTypeMaster", initialValue=1, allocationSize=1)	
	private long numId;
	
	@Column(name="str_module_name",length=100)
	private String moduleName;
	@Column(name="str_short_code",length=20)
	private String shortCode;
	@Column(name="str_description",length=2000)
	private String description;


}
