package in.pms.master.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeminarEventModel {	
	private long numId;
	private String seminarType;
	private String dateOfSeminar;	
	private String objectives;
	private boolean valid;
	private long[] numIds;
	private String coordinatingPerson;
	private int noOfParticipant;
	private String strCollaborators;
	private String cdacRoles;
	private String venue;
	private String strProfileOfParticipant;
	private String anyOtherTypeDetails;
	private String encMonthlyProgressId;
	private String encCategoryId;
}
