package in.pms.timesheet.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pms_holiday_master")
public class HolidayMasterModel {
	@Id
	@Column(name="holiday_id")	
	private long holidayId;
	
	@Column(name="str_day" , length=15)
	private String weekDay;
	
	@Column(name="num_day")
	private int day;
	
	@Column(name="str_holiday_name",length=50)
	private String holidayName;
	
	@Column(name="str_month",length=15)
	private String monthName;
	
	@Column(name="str_week",length=15)
	private String weekName;
	
	@Column(name="str_year",length=15)
	private String yearName;
	
	@Column(name="num_leave_type_id")
	private int leaveTypeId;
	
	@Column(name="num_leave_type_name",length=30)
	private String leaveTypeName;
	
	@Column(name="num_isvalid")
	private Integer isValid;
	
	@Column(name="dt_tr_date")
	private LocalDate transactionTime;
	
	@Column(name="num_tr_user_id")
	private Integer transactionUserId;
	
	@Column(name="str_date",length=15)
	private String date;
	
	@Column(name="str_holiday",length=15)
	private String holiday;

	public long getHolidayId() {
		return holidayId;
	}

	public void setHolidayId(long holidayId) {
		this.holidayId = holidayId;
	}

	public String getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(String weekDay) {
		this.weekDay = weekDay;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public String getMonthName() {
		return monthName;
	}

	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public String getYearName() {
		return yearName;
	}

	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	public int getLeaveTypeId() {
		return leaveTypeId;
	}

	public void setLeaveTypeId(int leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}

	public String getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(String leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public LocalDate getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDate transactionTime) {
		this.transactionTime = transactionTime;
	}

	public Integer getTransactionUserId() {
		return transactionUserId;
	}

	public void setTransactionUserId(Integer transactionUserId) {
		this.transactionUserId = transactionUserId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHoliday() {
		return holiday;
	}

	public void setHoliday(String holiday) {
		this.holiday = holiday;
	}

	@Override
	public String toString() {
		return "HolidayMasterModel [holidayId=" + holidayId + ", weekDay="
				+ weekDay + ", day=" + day + ", holidayName=" + holidayName
				+ ", monthName=" + monthName + ", weekName=" + weekName
				+ ", yearName=" + yearName + ", leaveTypeId=" + leaveTypeId
				+ ", leaveTypeName=" + leaveTypeName + ", isValid=" + isValid
				+ ", transactionTime=" + transactionTime
				+ ", transactionUserId=" + transactionUserId + ", date=" + date
				+ ", holiday=" + holiday + "]";
	}

	
	
	
}
