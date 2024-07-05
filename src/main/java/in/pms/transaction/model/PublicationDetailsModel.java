package in.pms.transaction.model;

import java.util.*;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicationDetailsModel 
{
	
	private int numProjectPublicationId;
	
	private String strPublicationType;
	private String dtpubdt;
	private String strPublicaionTitle;
	private String strAuthorDetails;
	private String strJournalName;
	private String strConferenceCity;
	private String strReferenceNumber;
	private String strPublisher;
	private String strOrganisation;
	private String strPublicationDescription;
    private String encMonthlyProgressId;
	private String encCategoryId;
	
}
