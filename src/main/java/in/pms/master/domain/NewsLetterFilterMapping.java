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
@Table(name="PMS_NEWSLETTER_FILTER_MAPPING")
public class NewsLetterFilterMapping extends TransactionInfoDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3279430064453904135L;

	@Id
	@Column(name="NUM_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	public int numId;
	
	@Column(name="NUM_NEWSLETTER_ID")
	public int numNewsletterId;
	
	@Column(name="NUM_FILTER_ID")
	public int numFilterId;

	public int getNumId() {
		return numId;
	}

	public void setNumId(int numId) {
		this.numId = numId;
	}

	public int getNumNewsletterId() {
		return numNewsletterId;
	}

	public void setNumNewsletterId(int numNewsletterId) {
		this.numNewsletterId = numNewsletterId;
	}

	public int getNumFilterId() {
		return numFilterId;
	}

	public void setNumFilterId(int numFilterId) {
		this.numFilterId = numFilterId;
	}
	
	
	
}
