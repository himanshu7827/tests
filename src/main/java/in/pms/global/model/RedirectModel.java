package in.pms.global.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Data
public class RedirectModel {
	
	String path;
	int workflowTypeId;
	int uniqueId;
	int moduleTypeId;
}
