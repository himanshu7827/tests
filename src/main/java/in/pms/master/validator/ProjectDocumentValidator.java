package in.pms.master.validator;

import in.pms.master.domain.DocumentFormatMaster;
import in.pms.master.model.ProjectDocumentDetailsModel;
import in.pms.master.model.ProjectDocumentMasterModel;
import in.pms.master.service.DocumentFormatService;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("projectDocumentValidator")
@Slf4j
public class ProjectDocumentValidator implements Validator  {

	@Autowired
	DocumentFormatService documentFormatService;
	
	@Override
	public boolean supports(Class<?> paramClass) {		
		return ProjectDocumentMasterModel.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {		
		ProjectDocumentMasterModel projectDocumentMasterModel = (ProjectDocumentMasterModel) obj;
				
		if(projectDocumentMasterModel.getProjectId() == 0){
			errors.rejectValue("projectId", "dropdown.defaultval.errorMsg", new Object[]{"'projectId'"}, "Value 0 is not allowed");
		}
		
		if(projectDocumentMasterModel.getDocumentTypeId() == 0){
			errors.rejectValue("documentTypeId", "dropdown.defaultval.errorMsg", new Object[]{"'documentTypeId'"}, "Value 0 is not allowed");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "documentDate", "project_document.documentDate.required");
		
		int uploadedFileCount = projectDocumentMasterModel.getDetailsModels().size();

		
		if(projectDocumentMasterModel.getNumId() == 0){
			if(uploadedFileCount==0){
				errors.rejectValue("projectId", "docupload.file.required", new Object[]{"'projectId'"}, "Upload atleast one file");
			}
		}
		
		List<ProjectDocumentDetailsModel> list = projectDocumentMasterModel.getDetailsModels();
		for(int i=0;i<uploadedFileCount;i++){
			ProjectDocumentDetailsModel model = list.get(i);
			if(!model.getProjectDocumentFile().getOriginalFilename().equals("") && null !=model.getProjectDocumentFile().getOriginalFilename()){
			
				//	InputStream stream = model.getProjectDocumentFile().getInputStream();	
					String filetype = model.getProjectDocumentFile().getContentType();					
					   log.info(filetype);
				      DocumentFormatMaster documentFormatMaster = documentFormatService.getDocumentFormatById(model.getDocumentFormatId());
				     String[] allowedMimeTypes = documentFormatMaster.getMimeTypes().split(",");
				     boolean mimeMatched= false;
				     for(int j=0;j<allowedMimeTypes.length;j++){
				    	if(allowedMimeTypes[j].equalsIgnoreCase(filetype)){
				    		mimeMatched=true;
				    		break;
				    	}
				     }
				     if(!mimeMatched){
						errors.rejectValue("projectId", "docupload.file.invalidFormat", new Object[]{"'projectId'"}, "Unsupported File Format"); 
				     }
				
			}		
		}
	}
	
}
