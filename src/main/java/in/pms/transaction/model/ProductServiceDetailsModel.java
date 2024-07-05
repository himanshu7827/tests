package in.pms.transaction.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductServiceDetailsModel {


	
	
		private long numProductServiceDetailsId;
		private String strServiceTitle;
		private String strDescription;
		private String dtProductServiceDate;
		private String strProductServiceDate;
		private String strObjective;
		private String strInauguratedBy;
		private String strCoordinator;
		private String strCollaborator; 
		private String strCdacRole;
		private String strPastDeploymentDetails;
		private String strCollaboratorsTotParners;
		private String strState;
		private int numStateId;
		private String strCity;

		/*private long numGroupCategoryId;*/
		private String strDocumentCaption;
		private long numCategoryId;
		private long[] numProductServiceDetailsIds;
		private MultipartFile productServiceQualityImages;
		private String strDocumentName;
		private long[] numDocumentsIds;
		private String encMonthlyProgressId;
		private String encCategoryId;
		private String strDocumentIds;

}
