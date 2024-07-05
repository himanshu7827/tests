package in.pms.transaction.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectBudgetForm {
	
	private long numId;
	private long numProjectId;

	private int year;	
	private Float amount;
	private long budgetId;
	private String budgetName;
	private String projectName;
	private String strProjectName;
	private String validateError;
	
	private String key;
	
	
	Map<String,Map<String,Float>> dataMap = new LinkedHashMap<String, Map<String,Float>>();
	
	
	

	
	
	
}