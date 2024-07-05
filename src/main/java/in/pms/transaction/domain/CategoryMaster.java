package in.pms.transaction.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import in.pms.global.domain.TransactionInfoDomain;
import lombok.Data;

@Data
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value = "pms_category_master_log", schema = "pms_log")
@Table(name = "pms_category_master")
public class CategoryMaster extends TransactionInfoDomain implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 4752109126675592330L;

	@Id
	@Column(name="num_category_id",length=10)	
	private long numCategoryId;
	@Column(name="str_category_name",length=150)
	private String strCategoryName;
	@Column(name="num_category_sequence",length=150)
	private int numCategorySequence;
	@Column(name="num_parent_category",length=150)
	private int numParentCategory;
	@Column(name="str_description",length=150)
	private String strDescription;
	@Column(name="is_upload_required",length=1)
	private String isUploadRequired;
	@Column(name="str_project_group_flag",length=1)
	private String strProjectGroupFlag;
    @Column(name="str_category_controller",length=1)
	private String strCategoryController;
	
	
}
