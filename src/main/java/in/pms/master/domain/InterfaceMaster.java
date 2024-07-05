package in.pms.master.domain;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import in.pms.global.domain.TransactionInfoDomain;

@Entity
@Table(name="PMS_INTERFACE_MASTER")
/*@Audited
@AuditTable(schema="serb_logs",value="SERB_INTERFACE_MASTER")*/
public class InterfaceMaster extends TransactionInfoDomain implements Serializable
	{

	private static final long serialVersionUID = 3202808022428939898L;

	@Id
	@Column(name="NUM_INTERFACE_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private int numInterfaceId;
	
	@Column(name="STR_INTERFACE_NAME")
	private String strInterfacename;
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="interfaceMaster")
	private List<ReportMaster> reportMaster;

	public List<ReportMaster> getReportMaster() {
		return reportMaster;
	}

	public void setReportMaster(List<ReportMaster> reportMaster) {
		this.reportMaster = reportMaster;
	}

	public int getNumInterfaceId() {
		return numInterfaceId;
	}

	public void setNumInterfaceId(int numInterfaceId) {
		this.numInterfaceId = numInterfaceId;
	}

	public String getStrInterfacename() {
		return strInterfacename;
	}

	public void setStrInterfacename(String strInterfacename) {
		this.strInterfacename = strInterfacename;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	

	
	
	
	
	
	
	
		
}