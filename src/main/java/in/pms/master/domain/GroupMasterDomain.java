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

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_group_master_log",schema="pms_log")
@Table(name="pms_group_master")
public class GroupMasterDomain extends TransactionInfoDomain implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -986986791115553119L;
	
	@Id
	@Column(name="group_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="groupMaster")
	@TableGenerator(name="groupMaster", initialValue=1, allocationSize=1)
	private long numId;
	@Column(name="str_group_name")
	private String groupName;
	@Column(name="str_group_short_code")
	private String groupShortName;
	@Column(name="str_contact_number")
	private String contactNumber;
	@Column(name="str_group_address")
	private String groupAddress;
	
	@Column(name="str_bg_color", length=50)
	private String bgColor;
	
	@Column(name="num_show_in_projects")
	private int showProjects;
	
	@ManyToOne(optional = false)
	@JoinColumn(name="organisation_id")
	private OrganisationMasterDomain organisationMasterDomain;
	
	@Column(name="str_code",length=30)
	private String strCode;
	
	@Column(name="group_type_flag")
	private String strGroupTypeFlag;
	
	
	/*@OneToMany(mappedBy = "groupMaster", cascade = CascadeType.ALL)
	 private List<Application> applications;*/

	/*--------- Store the value of proposal year check [12-04-2024] --------*/
	@Column(name="num_proposal_year_check", columnDefinition = "int default 0")
	private long proposalYearCheck;
}
