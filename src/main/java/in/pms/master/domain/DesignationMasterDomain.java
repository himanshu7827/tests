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
@AuditTable(value="pms_desg_mst_log",schema="pms_log")
@Table(name="pms_designation_master")
public class DesignationMasterDomain extends TransactionInfoDomain implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_designation_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="designationMaster")
	@TableGenerator(name="designationMaster", initialValue=1, allocationSize=1)	
	private long numId;
	
	
	@Column(name="designation_name",length=150)
	String designationName;
	
	@Column(name="designation_short_code",length=20)
	public String designationShortCode;


	@Column(name="description",length=500)
	String description;	
	@Column(name="is_group_specific",length=2,nullable = false, columnDefinition = "int default 0")
    int numIsGroupSpecific;
	@Column(name="is_organisation_specific",length=2,nullable = false, columnDefinition = "int default 0")
    int numIsOrganisationSpecific;
	@Column(name="num_hierarchy",columnDefinition="int default 0")
	private int hierarchy;
	
	@Column(name="is_ThirdParty_Designation",columnDefinition = "int default 0")
	public int numIsThirdPartyDesignation;
	
	@Column(name="num_project_specific")
	private int numProjectSpecific;
	
	@Column(name="num_order",columnDefinition="int default 0")
	private Integer designationorder;//added by devesh on 19/6/23 for setting designation order
	
/*	@Column(name="num_maker_id")
	private long makerId;
	@Column(name="num_checker_id")
    private long checkerId;    
	@Column(name="is_verified", columnDefinition = "boolean default true", nullable = false)
    private boolean verified;*/
}
