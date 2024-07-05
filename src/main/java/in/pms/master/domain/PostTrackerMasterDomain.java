package in.pms.master.domain;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_post_tracker_log", schema = "pms_log")
@Table(name = "pms_post_tracker")

public class PostTrackerMasterDomain extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1556954284610107490L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "postTracker")
	@TableGenerator(name = "postTracker", initialValue = 1, allocationSize = 1)
	@Column(name = "num_post_id")
	 private long numId;
	
        @Column(name="str_post_name", length = 100)
	 private String strPostName;

        @Column(name="str_post_description", length = 1500)
	 private String strPostDescription;

        @Column(name = "num_base_salary")
	 private float  numBaseSalary;

        @Column(name="str_vacancy_type", length = 30)
	 private String strVacancyType;

        @Column(name = "num_min_experience")
	 private float numMinExperience;

        @Column(name = "num_approved_post")
	 private int numApprovedPost;
        
        @Column(name = "num_notice_period")
   	 private int numNoticePeriod;
	
        @Temporal(TemporalType.DATE)
        @Column(name="dt_post_start_date")
         private Date dtPostStartDate;
	
        @Column(name="dt_post_end_date")
         private Date dtPostEndDate;
        
        @Column(name="str_code", length = 100)
   	 private String strCode;
        
        @Column(name = "num_validity_in_months")
   	 private int numValidityInMonths;
}