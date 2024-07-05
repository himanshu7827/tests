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

@Entity
@Table(name="PMS_NEWSLETTER_FILTER_MASTER")
public class NewsLetterFilterMaster extends TransactionInfoDomain implements Serializable {


	private static final long serialVersionUID = -3279430064453904135L;

	@Id
	@Column(name="NUM_FILTER_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	public int numFilterId;
	
	@Column(name="STR_FILTER_NAME")
	public String strFilterName;
	
	@Column(name="STR_FILTER_DESC")
	public String strFilterDesc;
	
	@Column(name="STR_FILTER_QUERY")
	public String strFilterQuery;
	
	@Column(name="STR_FILTER_TYPE")
	public String strFilterType;

	public String getStrFilterType() {
		return strFilterType;
	}

	public void setStrFilterType(String strFilterType) {
		this.strFilterType = strFilterType;
	}

	public int getNumFilterId() {
		return numFilterId;
	}

	public void setNumFilterId(int numFilterId) {
		this.numFilterId = numFilterId;
	}

	public String getStrFilterName() {
		return strFilterName;
	}

	public void setStrFilterName(String strFilterName) {
		this.strFilterName = strFilterName;
	}

	public String getStrFilterDesc() {
		return strFilterDesc;
	}

	public void setStrFilterDesc(String strFilterDesc) {
		this.strFilterDesc = strFilterDesc;
	}

	public String getStrFilterQuery() {
		return strFilterQuery;
	}

	public void setStrFilterQuery(String strFilterQuery) {
		this.strFilterQuery = strFilterQuery;
	}

	
}
