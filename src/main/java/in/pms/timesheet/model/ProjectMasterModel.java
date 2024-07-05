package in.pms.timesheet.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "pms_project_master")
public class ProjectMasterModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "projectMaster")
	@TableGenerator(name = "projectMaster", initialValue = 1, allocationSize = 1)
	@Column(name = "num_project_id")
	private long numId;
	@Column(name = "num_isvalid")
	private int numIsValid;
	@Column(name = "str_project_name", length = 300)
	private String strProjectName;
	@Column(name = "dt_work_order")
	private Date dtWorkOrderDate;
	@Column(name = "num_project_cost")
	private double projectCost;
	@Column(name = "dt_mou")
	private Date dtMOU;
	@Column(name = "dt_project_start")
	private Date dtProjectStartDate;
	@Column(name = "dt_project_end")
	private Date dtProjectEndDate;
	@Column(name = "str_project_status", length = 20)
	private String strProjectStatus;
	@Column(name = "str_project_type", length = 20)
	private String projectType;
	@Column(name = "str_brief_description",columnDefinition = "TEXT")
	private String strBriefDescription;
	@Column(name = "str_project_objective",columnDefinition = "TEXT")
	private String strProjectObjective;
	@Column(name = "str_project_scope", columnDefinition = "TEXT")
	private String strProjectScope;
	@Column(name = "str_project_aim", length = 2000)
	private String strProjectAim;
	@Column(name = "str_project_remarks", length = 2000)
	private String strProjectRemarks;
	@Column(name = "str_funded_scheme", length = 2000)
	private String strFundedScheme;
	
	
	
	@Column(name="str_project_ref_no",length=30)
	private String strProjectRefNo;
	
	@Column(name="str_administration_no",length=50)
	private String administrationNo;
	
	@Column(name = "str_description", length = 6000)
	private String strdescription;
	
	@Column(name="dt_project_closure")
	private Date projectClosureDate;
	
	@Column(name="str_closure_Remark",length = 2000)
	private String closureRemarks;
	
	@Column(name="num_corp_monthly_sharing", columnDefinition = "int default 1")
	private int corpMonthlySharing;
	@Column(name = "str_gst", length = 25)
	private String strGST;
	@Column(name = "str_tan", length = 25)
	private String strTAN;
	
	@Column(name="dt_financial_closure")
	private Date financialClosureDate;
	
	@Column(name="str_financial_closure_remark",length=300)	
	private String financialClosureRemark;
	
	@Column(name="str_closure_status",length=20)
	private String closureStatus;
	
	@Column(name="dt_technical_closure")
	private Date TechnicalClosureDate;
	
	@Column(name = "str_timesheet_type")
	private String timesheetType;
	
	public long getNumId() {
		return numId;
	}
	
	public int getNumIsValid() {
		return numIsValid;
	}

	public void setNumIsValid(int numIsValid) {
		this.numIsValid = numIsValid;
	}

	public void setNumId(long numId) {
		this.numId = numId;
	}

	public String getStrProjectName() {
		return strProjectName;
	}

	public void setStrProjectName(String strProjectName) {
		this.strProjectName = strProjectName;
	}

	public Date getDtWorkOrderDate() {
		return dtWorkOrderDate;
	}

	public void setDtWorkOrderDate(Date dtWorkOrderDate) {
		this.dtWorkOrderDate = dtWorkOrderDate;
	}

	public double getProjectCost() {
		return projectCost;
	}

	public void setProjectCost(double projectCost) {
		this.projectCost = projectCost;
	}

	public Date getDtMOU() {
		return dtMOU;
	}

	public void setDtMOU(Date dtMOU) {
		this.dtMOU = dtMOU;
	}

	public Date getDtProjectStartDate() {
		return dtProjectStartDate;
	}

	public void setDtProjectStartDate(Date dtProjectStartDate) {
		this.dtProjectStartDate = dtProjectStartDate;
	}

	public Date getDtProjectEndDate() {
		return dtProjectEndDate;
	}

	public void setDtProjectEndDate(Date dtProjectEndDate) {
		this.dtProjectEndDate = dtProjectEndDate;
	}

	public String getStrProjectStatus() {
		return strProjectStatus;
	}

	public void setStrProjectStatus(String strProjectStatus) {
		this.strProjectStatus = strProjectStatus;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getStrBriefDescription() {
		return strBriefDescription;
	}

	public void setStrBriefDescription(String strBriefDescription) {
		this.strBriefDescription = strBriefDescription;
	}

	public String getStrProjectObjective() {
		return strProjectObjective;
	}

	public void setStrProjectObjective(String strProjectObjective) {
		this.strProjectObjective = strProjectObjective;
	}

	public String getStrProjectScope() {
		return strProjectScope;
	}

	public void setStrProjectScope(String strProjectScope) {
		this.strProjectScope = strProjectScope;
	}

	public String getStrProjectAim() {
		return strProjectAim;
	}

	public void setStrProjectAim(String strProjectAim) {
		this.strProjectAim = strProjectAim;
	}

	public String getStrProjectRemarks() {
		return strProjectRemarks;
	}

	public void setStrProjectRemarks(String strProjectRemarks) {
		this.strProjectRemarks = strProjectRemarks;
	}

	public String getStrFundedScheme() {
		return strFundedScheme;
	}

	public void setStrFundedScheme(String strFundedScheme) {
		this.strFundedScheme = strFundedScheme;
	}

	public String getStrProjectRefNo() {
		return strProjectRefNo;
	}

	public void setStrProjectRefNo(String strProjectRefNo) {
		this.strProjectRefNo = strProjectRefNo;
	}

	public String getAdministrationNo() {
		return administrationNo;
	}

	public void setAdministrationNo(String administrationNo) {
		this.administrationNo = administrationNo;
	}

	public String getStrdescription() {
		return strdescription;
	}

	public void setStrdescription(String strdescription) {
		this.strdescription = strdescription;
	}

	public Date getProjectClosureDate() {
		return projectClosureDate;
	}

	public void setProjectClosureDate(Date projectClosureDate) {
		this.projectClosureDate = projectClosureDate;
	}

	public String getClosureRemarks() {
		return closureRemarks;
	}

	public void setClosureRemarks(String closureRemarks) {
		this.closureRemarks = closureRemarks;
	}

	public int getCorpMonthlySharing() {
		return corpMonthlySharing;
	}

	public void setCorpMonthlySharing(int corpMonthlySharing) {
		this.corpMonthlySharing = corpMonthlySharing;
	}

	public String getStrGST() {
		return strGST;
	}

	public void setStrGST(String strGST) {
		this.strGST = strGST;
	}

	public String getStrTAN() {
		return strTAN;
	}

	public void setStrTAN(String strTAN) {
		this.strTAN = strTAN;
	}

	public Date getFinancialClosureDate() {
		return financialClosureDate;
	}

	public void setFinancialClosureDate(Date financialClosureDate) {
		this.financialClosureDate = financialClosureDate;
	}

	public String getFinancialClosureRemark() {
		return financialClosureRemark;
	}

	public void setFinancialClosureRemark(String financialClosureRemark) {
		this.financialClosureRemark = financialClosureRemark;
	}

	public String getClosureStatus() {
		return closureStatus;
	}

	public void setClosureStatus(String closureStatus) {
		this.closureStatus = closureStatus;
	}

	public Date getTechnicalClosureDate() {
		return TechnicalClosureDate;
	}

	public void setTechnicalClosureDate(Date technicalClosureDate) {
		TechnicalClosureDate = technicalClosureDate;
	}
	
	public String getTimesheetType() {
		return timesheetType;
	}

	public void setTimesheetType(String timesheetType) {
		this.timesheetType = timesheetType;
	}

	@Override
	public String toString() {
		return "ProjectMasterModel [numId=" + numId + ", numIsValid=" + numIsValid + ", strProjectName="
				+ strProjectName + ", dtWorkOrderDate=" + dtWorkOrderDate + ", projectCost=" + projectCost + ", dtMOU="
				+ dtMOU + ", dtProjectStartDate=" + dtProjectStartDate + ", dtProjectEndDate=" + dtProjectEndDate
				+ ", strProjectStatus=" + strProjectStatus + ", projectType=" + projectType + ", strBriefDescription="
				+ strBriefDescription + ", strProjectObjective=" + strProjectObjective + ", strProjectScope="
				+ strProjectScope + ", strProjectAim=" + strProjectAim + ", strProjectRemarks=" + strProjectRemarks
				+ ", strFundedScheme=" + strFundedScheme + ", strProjectRefNo=" + strProjectRefNo
				+ ", administrationNo=" + administrationNo + ", strdescription=" + strdescription
				+ ", projectClosureDate=" + projectClosureDate + ", closureRemarks=" + closureRemarks
				+ ", corpMonthlySharing=" + corpMonthlySharing + ", strGST=" + strGST + ", strTAN=" + strTAN
				+ ", financialClosureDate=" + financialClosureDate + ", financialClosureRemark="
				+ financialClosureRemark + ", closureStatus=" + closureStatus + ", TechnicalClosureDate="
				+ TechnicalClosureDate + ", timesheetType=" + timesheetType + "]";
	}

	
}
