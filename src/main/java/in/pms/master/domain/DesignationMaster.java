package in.pms.master.domain;

import in.pms.global.domain.TransactionInfoDomain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;


@Entity
@Table(name="pms_designation_master")
@Data
public class DesignationMaster extends TransactionInfoDomain implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="num_designation_id")
	@GeneratedValue(strategy=GenerationType.TABLE, generator="DesignationID")
	@TableGenerator(name="DesignationID", initialValue=100, allocationSize=1)	
	public int numDesignationId;
	
	
	@Column(name="designation_name")
	String designationName;
	
	@Column(name="designation_short_code")
	public String designationShortCode;


	@Column(name="description")
	String description;
	
	
		
}
