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
@Table(name="pms_Mou_Collaboration")
public class MouCollaborationDomain extends TransactionInfoDomain implements Serializable {
	
	private static final long serialVersionUID = 3527993074609024305L;
	
	//CopyRight Id will be treated as primary Key
	
	@Id
	@Column(name="Collab_id",length=10)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator")
	@TableGenerator(name="generator", initialValue=1, allocationSize=1)
	private long numCollabID;
	
	@Column(name="str_Objective" , length=1000)
	private String strObjective;
	
	@Column(name="dt_From_date")
	private Date dtFromDate;
	
	@Column(name="dt_Validity_date")
	private Date dtValidityDate;
	
	@Column(name="str_Collab_Agency" , length=500)
	private String strCollabAgency;
	
	 @ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;


	
}



