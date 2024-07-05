package in.pms.transaction.domain;

import in.pms.global.domain.TransactionInfoDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Data;

@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_designation_for_client_log", schema = "pms_log")
@Table(name = "pms_designation_for_client")
@Data
public class DesignationForClient extends TransactionInfoDomain {

	@Id
	@Column(name = "num_designation_id")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "DesignationForClient")
	@TableGenerator(name = "DesignationForClient", initialValue = 1, allocationSize = 1)
	public int numDesignationId;

	@Column(name = "designation_name", length = 200)
	String designationName;

	@Column(name = "designation_short_code", length = 20)
	public String designationShortCode;

	@Column(name = "description", length = 500)
	String description;
	
	@Column(name="num_hierarchy", columnDefinition="int default 0")
	private int hierarchy;
	
}
