/*@author Deepshikha 18 /10*/


package in.pms.global.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="tiles_mst")
public class TileDomain extends TransactionInfoDomain {
	@Id
	@Column(name="num_tiles_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private int numTilesId;
	
	@Column(name="str_tiles_name")
	private String strTilesName;
	
	@Column(name="str_colour_name")
	private String strColourName;
	
	@Column(name="str_icon_name")
	private String strIconName;
	
	@Column(name="str_icon_colour")
	private String strIconColour;
	
	@Column(name="str_url")
	private String strUrl;
	
	@Column(name="num_role_id")
	private int numRoleId;
	
	@Column(name="num_seqno")
	private int numSeqNo;
	
	@Column(name="str_name")
	private String strName;

	public int getNumTilesId() {
		return numTilesId;
	}

	public void setNumTilesId(int numTilesId) {
		this.numTilesId = numTilesId;
	}

	public String getStrTilesName() {
		return strTilesName;
	}

	public void setStrTilesName(String strTilesName) {
		this.strTilesName = strTilesName;
	}

	public String getStrColourName() {
		return strColourName;
	}

	public void setStrColourName(String strColourName) {
		this.strColourName = strColourName;
	}

	public String getStrIconName() {
		return strIconName;
	}

	public void setStrIconName(String strIconName) {
		this.strIconName = strIconName;
	}

	public String getStrUrl() {
		return strUrl;
	}

	public void setStrUrl(String strUrl) {
		this.strUrl = strUrl;
	}

	public int getNumRoleId() {
		return numRoleId;
	}

	public void setNumRoleId(int numRoleId) {
		this.numRoleId = numRoleId;
	}

	public String getStrIconColour() {
		return strIconColour;
	}

	public void setStrIconColour(String strIconColour) {
		this.strIconColour = strIconColour;
	}

	public int getNumSeqNo() {
		return numSeqNo;
	}

	public void setNumSeqNo(int numSeqNo) {
		this.numSeqNo = numSeqNo;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}


	 
}
