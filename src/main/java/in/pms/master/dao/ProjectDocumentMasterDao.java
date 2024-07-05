package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.EmployeeMasterDomain;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.TempProjectDocumentDetailsDomain;
import in.pms.master.domain.TempProjectDocumentMasterDomain;
import in.pms.master.model.ProjectDocumentMasterModel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDocumentMasterDao {
	
	@Autowired
	DaoHelper daoHelper;
	

	
	public ProjectDocumentMasterDomain merge(ProjectDocumentMasterDomain projectDocumentMasterDomain){
		return daoHelper.merge(ProjectDocumentMasterDomain.class, projectDocumentMasterDomain);
	}

	public List<ProjectDocumentMasterDomain> uploadedProjectDocument(ProjectDocumentMasterModel projectDocumentMasterModel){
		String query = "select p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList where p.numIsValid=1 and p.projectId="+projectDocumentMasterModel.getProjectId() +" and p.documentTypeMasterDomain="+projectDocumentMasterModel.getDocumentTypeId();
		return daoHelper.findByQuery(query);
	}
	
	// GET PROJECT DOCUMENT BY PROJECT ID
	public List<ProjectDocumentMasterDomain> getuploadedProjectDocumentByID(long projectId){
		String query = "select p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList where p.numIsValid=1 and p.projectId="+projectId;
		return daoHelper.findByQuery(query);
	}	
	//
	
	public ProjectDocumentMasterDomain uploadedProjectDocumentById(ProjectDocumentMasterModel projectDocumentMasterModel){
		String query = "select p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList where p.numIsValid=1 and p.numId="+projectDocumentMasterModel.getNumId();
		List<ProjectDocumentMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;		
	}
	
	public ProjectDocumentDetailsDomain getProjectDocumentDetailsById(long id){
		return daoHelper.findById(ProjectDocumentDetailsDomain.class, id);
	}
	
	/*Added by devesh on 29-12-23 to get DocumentId from projectId for Reco Sheet Document*/
	public ProjectDocumentMasterDomain getProjectDocumentIdFromProjectId(long projectId){
		String query = "select distinct p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList ";
		query += " where p.numIsValid=1 and  p.projectId="+projectId +" and  p.documentTypeMasterDomain.numId=56 order by p.dtTrDate desc";
		List<ProjectDocumentMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/*End of Function*/
	
	public ProjectDocumentDetailsDomain getProjectDocumentDetail(long parentId,long documentFormatId){
		String query= "from ProjectDocumentDetailsDomain where projectDocumentMasterDomain.numId="+parentId+" and documentFormatMaster.numId="+documentFormatId;
		List<ProjectDocumentDetailsDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<ProjectDocumentMasterDomain> uploadedDocumentByProjectId(long projectId){
		StringBuilder query = new StringBuilder("select distinct p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList ");
		query.append(" where p.numIsValid=1 and  p.projectId="+projectId +" order by p.documentDate desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	public  List<ProjectDocumentMasterDomain> showProjectDocumentRevision(long documentId){
		return daoHelper.getPreviousRevisions(ProjectDocumentMasterDomain.class,documentId);
	}
	
	public List<Object[]> getProjectDocumentForDashboard(long projectId){
		StringBuilder query = new StringBuilder("select max(numId),a.projectDocumentMasterDomain.documentTypeMasterDomain.docTypeName,a.projectDocumentMasterDomain.documentTypeMasterDomain.classColor,a.projectDocumentMasterDomain.documentTypeMasterDomain.numId from ");
		query.append(" ProjectDocumentDetailsDomain a where a.projectDocumentMasterDomain.documentTypeMasterDomain.showOnDashboard=1");
		query.append(" and a.projectDocumentMasterDomain.numIsValid=1 and a.projectDocumentMasterDomain.projectId="+projectId +" group by 2,3,4");
		
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<ProjectDocumentMasterDomain> documentDetailsCategoryWiseByProjectId(long projectId){
		StringBuilder query = new StringBuilder("select distinct p from ProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList ");
		query.append(" join fetch p.documentTypeMasterDomain x left join fetch x.documentClassification b ");
		query.append(" where p.numIsValid=1 and  p.projectId="+projectId +" order by b.displaySequence,p.documentDate desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<Object[]> getProjectDocumentWithDocTypeId(long projectId,List<Long> docTypeIds){
		StringBuilder query = new StringBuilder("select max(numId),a.projectDocumentMasterDomain.documentTypeMasterDomain.docTypeName,a.projectDocumentMasterDomain.documentTypeMasterDomain.classColor,a.projectDocumentMasterDomain.documentTypeMasterDomain.numId from ");
		query.append(" ProjectDocumentDetailsDomain a where a.projectDocumentMasterDomain.documentTypeMasterDomain.showOnDashboard=1");
		query.append(" and a.projectDocumentMasterDomain.numIsValid=1 and a.projectDocumentMasterDomain.documentTypeMasterDomain.numId in :docTypeIds and a.projectDocumentMasterDomain.projectId="+projectId +" group by 2,3,4");
		
		return daoHelper.findByIdList(query.toString(),docTypeIds);
	}
	
	public TempProjectDocumentMasterDomain merge(TempProjectDocumentMasterDomain projectDocumentMasterDomain){
		return daoHelper.merge(TempProjectDocumentMasterDomain.class, projectDocumentMasterDomain);
	}
	
	public List<TempProjectDocumentMasterDomain> uploadedTempDocumentByProjectId(long projectId){
		StringBuilder query = new StringBuilder("select distinct p from TempProjectDocumentMasterDomain p join fetch p.projectDocumentDetailsDomainList ");
		query.append(" where p.numIsValid=1 and  p.projectId="+projectId +" order by p.documentDate desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	public TempProjectDocumentDetailsDomain getTempProjectDocumentDetailsById(long id){
		return daoHelper.findById(TempProjectDocumentDetailsDomain.class, id);
	}
	
	public ProjectDocumentDetailsDomain updateProjectDocDetails(ProjectDocumentDetailsDomain projectDocumentDetailsDomain){
		return daoHelper.merge(ProjectDocumentDetailsDomain.class, projectDocumentDetailsDomain);
	}
	
	public List<TempProjectDocumentMasterDomain> getDetailsOfTempProjectDoc(long projectId, long employeeId, long docTypeId,int formatId){
		StringBuilder query = new StringBuilder("SELECT pdm.* FROM pms_temp_project_document_master pdm, pms_temp_project_document_details pdt WHERE pdm.num_project_id = "+projectId +" AND pdm.document_type_id = "+docTypeId+" AND pdm.num_tr_user_id = "+employeeId+" AND pdm.num_project_document_id=pdt.num_project_document_id and pdt.document_format_id="+formatId+" and pdm.num_isvalid=1");
		
		 List<TempProjectDocumentMasterDomain> tempProjectDocumentMasterDomain = daoHelper.runNative(query.toString(), TempProjectDocumentMasterDomain.class);
		 return tempProjectDocumentMasterDomain;
	}
	
	public List<TempProjectDocumentMasterDomain> getTempProjectDocumentMasterDetails(long projectId,  long docTypeId){
		StringBuilder query = new StringBuilder("SELECT pdm.* FROM pms_temp_project_document_master pdm,pms_doc_stage_map dsm WHERE dsm.doc_type_id=pdm.document_type_id and dsm.is_mandatory='t' and doc_stage_id=3 and pdm.num_project_id = "+projectId +" AND pdm.document_type_id = "+docTypeId+" and pdm.num_isvalid=1");
		
		 List<TempProjectDocumentMasterDomain> tempProjectDocumentMasterDomain = daoHelper.runNative(query.toString(), TempProjectDocumentMasterDomain.class);
		 return tempProjectDocumentMasterDomain;
	}
	
	/*----------------------- Query For Get All Projects Document Details [18-03-2024] ------------------------*/
	public List<ProjectDocumentMasterDomain> getUploadedProjectDocumentForTile(ProjectDocumentMasterModel projectDocumentMasterModel) {
	    String query = "";
	    query = "select p from ProjectDocumentMasterDomain p " +
                "join fetch p.projectDocumentDetailsDomainList " +
                "where p.numIsValid = 1 ";
	    if(projectDocumentMasterModel.getProjectIds() != null && !projectDocumentMasterModel.getProjectIds().isEmpty()){
	    	query += " and p.projectId IN (" +projectDocumentMasterModel.getProjectIds()+ ")";
	    }       
	    
	    if(projectDocumentMasterModel.getDocumentTypeIds() != null && !projectDocumentMasterModel.getDocumentTypeIds().isEmpty()){
	    	query += " and p.documentTypeMasterDomain IN (" +projectDocumentMasterModel.getDocumentTypeIds()+ ")";
	    }else{
	    	query += " and p.documentTypeMasterDomain IN (1,2,3,48)";
	    }
	    
	    if (projectDocumentMasterModel.getDocumentDate() != null && !projectDocumentMasterModel.getDocumentDate().equals("0")) {
	        String year = projectDocumentMasterModel.getDocumentDate();
	        query += " and YEAR(p.documentDate) = '" + year + "'";
	    }
	    return daoHelper.findByQuery(query);
	}
	/*----------------------- End of Query function For Get All Projects Document Details [18-03-2024] ------------------------*/
}
