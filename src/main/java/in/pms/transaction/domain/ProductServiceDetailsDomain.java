package in.pms.transaction.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pms_product_service_details")
public class ProductServiceDetailsDomain extends TransactionInfoDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7268883669634372736L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE, generator="productServiceDetails")
	@TableGenerator(name="productServiceDetails", initialValue=1, allocationSize=1)
	@Column(name="num_product_service_id",length=10)	
	private long numProductServiceDetailsId;
	@Column(name="str_service_title",length=250)
	private String strServiceTitle;
	@Column(name="str_description",length=2000)
	private String strDescription;
	@Column(name="str_objective",length=2000)
	private String strObjective;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_product_service_date")
	private Date dtProductServiceDate;
	@Column(name="str_inaugurated_by",length=500)
	private String strInauguratedBy;
	@Column(name="str_coordinator")
	private String strCoordinator;
	@Column(name="str_collaborator")
	private String strCollaborator; 
	@Column(name="str_cdac_role")
	private String strCdacRole;
	@Column(name="str_past_deployment_details")
	private String strPastDeploymentDetails;
	@Column(name="str_collaborators_tot_parners")
	private String strCollaboratorsTotParners;
	@Column(name="num_state_id")
	private int numStateId;
	@Column(name="str_city")
	private String strCity;
	@Column(name="str_document_ids",length=500)
	private String strDocumentIds;

	@ManyToOne
	 @JoinColumn(name="num_progress_details_id_fk")
	 private MonthlyProgressDetails monthlyProgressDetails;
	
}
