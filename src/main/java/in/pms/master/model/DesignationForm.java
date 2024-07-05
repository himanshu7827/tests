package in.pms.master.model;

import java.util.ArrayList;
import java.util.List;

public class DesignationForm {

	int numDesignationId;
	String strDesignationName;
	String strDesription;
	String strDesignationShortCode;
	
	String userExist;
	
	
	
	public String getUserExist() {
		return userExist;
	}
	public void setUserExist(String userExist) {
		this.userExist = userExist;
	}
	public int getNumIsValid() {
		return numIsValid;
	}
	public void setNumIsValid(int numIsValid) {
		this.numIsValid = numIsValid;
	}
	List checkbox =new ArrayList();
	int numIsValid;
	
	
	
	public List getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(List checkbox) {
		this.checkbox = checkbox;
	}
	public int getNumDesignationId() {
		return numDesignationId;
	}
	public void setNumDesignationId(int numDesignationId) {
		this.numDesignationId = numDesignationId;
	}
	public String getStrDesignationName() {
		return strDesignationName;
	}
	public void setStrDesignationName(String strDesignationName) {
		this.strDesignationName = strDesignationName;
	}
	public String getStrDesription() {
		return strDesription;
	}
	public void setStrDesription(String strDesription) {
		this.strDesription = strDesription;
	}
	public String getStrDesignationShortCode() {
		return strDesignationShortCode;
	}
	public void setStrDesignationShortCode(String strDesignationShortCode) {
		this.strDesignationShortCode = strDesignationShortCode;
	}
	
}
