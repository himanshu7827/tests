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

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_client_master_log",schema="pms_log")
@Table(name="pms_client_master")
public class ClientMasterDomain extends TransactionInfoDomain implements
		Serializable {

	private static final long serialVersionUID = -2096559171934144855L;
	@Id
	@Column(name="client_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_client_name")
	private String clientName;
	@Column(name="num_parent_client_id")
	private long parentClientId;
	@Column(name="str_client_address")
	private String clientAddress;
	@Column(name="str_contact_number")
	private String contactNumber;
	@Column(name="str_short_code")
	private String shortCode;	
}
