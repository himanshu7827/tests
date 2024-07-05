package in.pms.timesheet.model;

import in.pms.global.domain.TransactionInfoDomain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name = "pms_employee_master")
public class EmployeeMasterModel extends TransactionInfoDomain{
	@Id
	@Column(name="emp_id",length=10)	
	private long numId;
	@Column(name="str_emp_name" , length=150)
	private String employeeName;
	
	@Column(name="dt_emp_dob")
	private Date dateOfBirth;
	@Column(name="dt_resignation_date")
	private Date dateOfResignation;
	@Column(name="dt_release_date")
	private Date dateOfRelease;
	
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
	
	@Column(name="group_id_fk")
	private long numGroupId;
	
	@Column(length = 300)
    private String password;

    private boolean enabled;
    
    private String secret;
    
    @Column(name="num_deputed_at")
    private int numDeputedAt;

	public long getNumId() {
		return numId;
	}

	public void setNumId(long numId) {
		this.numId = numId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfResignation() {
		return dateOfResignation;
	}

	public void setDateOfResignation(Date dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}

	public Date getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Date dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOfficeEmail() {
		return officeEmail;
	}

	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}

	public String getAlternateEmail() {
		return alternateEmail;
	}

	public void setAlternateEmail(String alternateEmail) {
		this.alternateEmail = alternateEmail;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getReleaseRemark() {
		return releaseRemark;
	}

	public void setReleaseRemark(String releaseRemark) {
		this.releaseRemark = releaseRemark;
	}

	public long getOrganisationid() {
		return organisationid;
	}

	public void setOrganisationid(long organisationid) {
		this.organisationid = organisationid;
	}
	
	public long getGroupid() {
		return numGroupId;
	}

	public void setGroupid(long numGroupId) {
		this.numGroupId = numGroupId;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getNumDeputedAt() {
		return numDeputedAt;
	}

	public void setNumDeputedAt(int numDeputedAt) {
		this.numDeputedAt = numDeputedAt;
	}

	@Override
	public String toString() {
		return "EmployeeMasterModel [numId=" + numId + ", employeeName=" + employeeName + ", dateOfBirth=" + dateOfBirth
				+ ", dateOfResignation=" + dateOfResignation + ", dateOfRelease=" + dateOfRelease + ", dateOfJoining="
				+ dateOfJoining + ", contactNumber=" + contactNumber + ", mobileNumber=" + mobileNumber + ", gender="
				+ gender + ", officeEmail=" + officeEmail + ", alternateEmail=" + alternateEmail + ", category="
				+ category + ", employmentStatus=" + employmentStatus + ", releaseRemark=" + releaseRemark
				+ ", organisationid=" + organisationid + ", password=" + password + ", enabled=" + enabled + ", secret="
				+ secret + ", numDeputedAt=" + numDeputedAt + "]";
	}
    
    
}
