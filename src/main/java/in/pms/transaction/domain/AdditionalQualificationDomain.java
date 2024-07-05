package in.pms.transaction.domain;

/**
 * @author amitkumarsaini
 *
 */
import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Entity
@Table(name="pms_Addditional_Qualification")
public class AdditionalQualificationDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="Qualification_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numQualID;
	
	@Column(name="str_Certificate_Name" , length=200)
	private String strCertificateName;
	
	@Column(name="str_Employee_Name" , length=200)
	private String strEmployeeName;
	
	@Column(name="str_Employee_Designation" , length=200)
	private String strEmployeeDesignation;
	
	@Column(name="Num_Employee_ID",length=10)	
	private long numEmployeeID;
	
	@Column(name="str_Focus_Area" , length=200)
	private String strFocusArea;
	
	@Column(name="str_Description_Program" , length=1000)
	private String strDescriptionProgram;
	
	@Column(name="num_group_category_id",length=10)
	private long numGroupCategoryId;

	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;
}



