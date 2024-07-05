package in.pms.timesheet.model;

import in.pms.global.domain.TransactionInfoDomain;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "pms_employee_role_mst")
public class EmployeeRoleMstModel extends TransactionInfoDomain{
	@Id
	@Column(name="num_emp_role_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numId;
	
	@Column(name="num_emp_id",length=12)
	private long numEmpId ;
	
	@Column(name="num_project_id",length=12)
	private int numProjectId;
	
	@Column(name="role_id")
	private long numRoleId;
	
	@Column(name="num_group_id",length=12)
	private int numGroupId;
	
	@Column(name="num_organisation_id",length=12)
	private int numOrganisationId;
	
	@Column(name="dt_startDate")
	private Date dtStartDate;
	
	@Column(name="dt_endDate")
	private Date dtEndDate;
	
	@Column(name="num_primary_project",columnDefinition="int default 0")
	private int primaryProject;
	
	
	@Column(name="str_manreq_details",length=500)
	private String strManReqDetails ;
	

	@Column(name="str_remarks",length=500)
	private String remarks;
	
	@Column(name="num_utilization",columnDefinition="int default 0")
	private double utilization;

	public long getNumId() {
		return numId;
	}

	public void setNumId(long numId) {
		this.numId = numId;
	}

	public long getNumEmpId() {
		return numEmpId;
	}

	public void setNumEmpId(long numEmpId) {
		this.numEmpId = numEmpId;
	}

	public int getNumProjectId() {
		return numProjectId;
	}

	public void setNumProjectId(int numProjectId) {
		this.numProjectId = numProjectId;
	}
	
	public long getNumRoleId() {
		return numRoleId;
	}

	public void setNumRoleId(long numRoleId) {
		this.numRoleId = numRoleId;
	}

	public int getNumGroupId() {
		return numGroupId;
	}

	public void setNumGroupId(int numGroupId) {
		this.numGroupId = numGroupId;
	}

	public int getNumOrganisationId() {
		return numOrganisationId;
	}

	public void setNumOrganisationId(int numOrganisationId) {
		this.numOrganisationId = numOrganisationId;
	}

	public Date getDtStartDate() {
		return dtStartDate;
	}

	public void setDtStartDate(Date dtStartDate) {
		this.dtStartDate = dtStartDate;
	}

	public Date getDtEndDate() {
		return dtEndDate;
	}

	public void setDtEndDate(Date dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public int getPrimaryProject() {
		return primaryProject;
	}

	public void setPrimaryProject(int primaryProject) {
		this.primaryProject = primaryProject;
	}

	public String getStrManReqDetails() {
		return strManReqDetails;
	}

	public void setStrManReqDetails(String strManReqDetails) {
		this.strManReqDetails = strManReqDetails;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public double getUtilization() {
		return utilization;
	}

	public void setUtilization(double utilization) {
		this.utilization = utilization;
	}

	@Override
	public String toString() {
		return "EmployeeRoleMstModel [numId=" + numId + ", numEmpId=" + numEmpId + ", numProjectId=" + numProjectId
				+ ", numGroupId=" + numGroupId + ", numOrganisationId=" + numOrganisationId + ", dtStartDate="
				+ dtStartDate + ", dtEndDate=" + dtEndDate + ", primaryProject=" + primaryProject
				+ ", strManReqDetails=" + strManReqDetails + ", remarks=" + remarks + ", utilization=" + utilization
				+ "]";
	}	
	
}
