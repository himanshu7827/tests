package in.pms.master.validator;

import in.pms.global.misc.RegexValidationFile;
import in.pms.master.model.DocStageMappingModel;
import in.pms.master.model.GroupMasterModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Getter
@Setter
public class DocStageMappingValidator implements Validator {

	private Pattern namePattern;
	private Matcher nameMatcher;

	private String groupNameRegex = RegexValidationFile.getValueFromKey("name.regex");

	public DocStageMappingValidator() {
		namePattern = Pattern.compile(groupNameRegex);
	}

	@Override
	public boolean supports(Class<?> paramClass) {
		return DocStageMappingModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		DocStageMappingModel docStageMappingModel = (DocStageMappingModel) obj;
		if (docStageMappingModel.getStageId() == 0) {
			errors.rejectValue("stageId", "dropdown.defaultval.errorMsg", new Object[] { "'stageId'" },
					"Value 0 is not allowed");
		}
		
		if (docStageMappingModel.getDocumentId() == 0) {
			errors.rejectValue("documentId", "dropdown.defaultval.errorMsg", new Object[] { "'documentId'" },
					"Value 0 is not allowed");
		}
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docTypeName", "document.documentName.required");
		nameMatcher = namePattern.matcher(docStageMappingModel.getDocTypeName());
		if (docStageMappingModel.getDocTypeName() != null && !nameMatcher.matches()) {
			errors.rejectValue("docTypeName", "invalid.Name");
		}*/
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docSeq", "docstagemap.seqNo.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mandatory", "master.mandatory.required");
	}

}
