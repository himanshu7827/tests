package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;
import in.pms.transaction.domain.Application;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

@ToString
@Getter
@Setter
@Entity
@Audited
@AuditOverride(forClass = TransactionInfoDomain.class)
@AuditTable(value="pms_project_category_log",schema="pms_log")
@Table(name="pms_project_category_master",schema="pms")
public class ProjectCategoryMaster extends TransactionInfoDomain implements Serializable{

	private static final long serialVersionUID = -5077484059429321327L;
	
	@Id
	@Column(name="num_project_category_id",length=5)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="projectCategoryMaster")
	@TableGenerator(name="projectCategoryMaster", initialValue=1, allocationSize=1)	
	private int numId;
	
	@Column(name="str_category_name",length=100)
	private String categoryName;
	@Column(name="str_short_code",length=20)
	private String shortCode;
	@Column(name="str_description",length=2000)
	private String description;
	@Column(name="str_code",length=30)
	private String strCode;
	
	@OneToMany(mappedBy = "categoryMaster", cascade = CascadeType.ALL)
	@NotAudited
	 private List<Application> applications;

}
