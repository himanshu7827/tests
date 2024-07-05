package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.login.domain.Role;
import in.pms.master.model.ThirdPartyMasterModel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditTable(value="pms_employee_master_log",schema="pms_log")
@Table(name="pms_employee_master")
public class EmployeeMasterDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//Corporate employee Id will be treated as primary Key
	
	@Id
	@Column(name="emp_id",length=10)	
	private long numId;
	@Column(name="str_emp_name" , length=150)
	private String employeeName;
	@Temporal(TemporalType.DATE)
	@Column(name="dt_emp_dob")
	private Date dateOfBirth;
	@Column(name="dt_resignation_date")
	private Date dateOfResignation;
	@Column(name="dt_release_date")
	private Date dateOfRelease;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_joining")
	private Date dateOfJoining;
	
	@Column(name="str_contact_number",length=15)
	private String contactNumber;
	
	@Column(name="str_mobile_number")
	private String mobileNumber;
	
	@Column(name="str_emp_gender",length=15)
	private String gender;
	
	@Column(name="str_office_email",length=100)
	private String officeEmail;
	
	@Column(name="str_alternate_email",length=100)
	private String alternateEmail;
	
	@Column(name="str_emp_category")
	private String category;
	
	@Column(name="str_employment_status")
	private String employmentStatus;
	
	@Column(name="str_release_Remark",length= 2000)
	private String releaseRemark;
	
	@Column(name="organisation_id")
	private long organisationid;
	
	@Column(length = 300)
    private String password;

    private boolean enabled;
    
    private String secret;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "emp_id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
	 
  
    @ManyToOne(optional = false)
   	@JoinColumn(name="group_id_fk")
   	private GroupMasterDomain groupMasterDomain;
    
    @ManyToOne(optional = false)
   	@JoinColumn(name="num_emp_type_id")
   	private EmpTypeMasterDomain empTypeMasterDomain;
    
    @ManyToOne(optional = false)
   	@JoinColumn(name="num_designation_id")
    public DesignationMasterDomain designationMaster;
    
    @ManyToOne(cascade=CascadeType.ALL,optional = true)
	@JoinColumn(name="num_third_party_id")
	private ThirdPartyMasterDomain thirdPartyMasterDomain;
    
    @ManyToOne(optional = false)
   	@JoinColumn(name="num_post_id")
    private PostTrackerMasterDomain postTrackerMaster;
    
    @OneToMany(fetch=FetchType.LAZY,mappedBy="employeeMasterDomain",cascade=CascadeType.ALL)   
    public List<EmployeeContractDetailDomain> employeeContractDetailDomain;
    
    @Column(name="num_deputed_at")
    private int numDeputedAt;
    
    
    
}
