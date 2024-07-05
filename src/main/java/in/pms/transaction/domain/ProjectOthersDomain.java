package in.pms.transaction.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.Application;


@Setter
@Getter
@Entity
@Table(name="pms_project_others")
public class ProjectOthersDomain extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -8160190031918235356L;
	@Id
	@Column(name="num_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private int numId;
	
	
	
	@Column(name="str_project_others_html",columnDefinition= "TEXT")
	private String strProjectOthersHtml;
	
	@Column(name="str_project_others",columnDefinition= "TEXT")
	private String strProjectOthers;
	
	
	//@JoinColumn(name="num_report_id_fk")
	

	
	@Column(name="str_status")
	private String strStatus;
	
	@ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;
}