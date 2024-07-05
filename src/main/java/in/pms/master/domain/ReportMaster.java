package in.pms.master.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="PMS_REPORT_MASTER")
@Getter
@Setter
public class ReportMaster extends TransactionInfoDomain implements Serializable
	{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3202808022428939898L;

	@Id
	@Column(name="NUM_QUERY_ID")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private int numQueryId;
	
	@Column(name="STR_QUERY")
	private String strQuery;
	
	@Column(name="STR_DESC")
	private String strDesc;
	
	@Column(name="STR_ECODE", nullable=false, unique=true)
	private String strECode;
	
	
	@Column(name="IS_SCHEME_SPECIFIC")
	private int IsSchemeSpecific;
	
	@Column(name="STR_NAME")
	private String strName;
	
	@Column(name="STR_SHEET_NAMES")
	private String strSheetNames;
	
	
	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "NUM_INTERFACE_ID" ,nullable = false)
	    public InterfaceMaster interfaceMaster;
	 
	 @ManyToMany(mappedBy = "reports")
	    private Collection<RoleMasterDomain> roles;
		
	

	 
		
}