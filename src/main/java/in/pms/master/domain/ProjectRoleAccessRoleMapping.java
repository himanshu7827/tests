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
@AuditTable(value="pms_project_role_access_mapping_log",schema="pms_log")
@Table(name="pms_project_role_access_mapping")
public class ProjectRoleAccessRoleMapping  extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -3724774084828868997L;

	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="role_id")
	private long roleId;
	@Column(name="role_mst_id")
	private long roleMst;

}
