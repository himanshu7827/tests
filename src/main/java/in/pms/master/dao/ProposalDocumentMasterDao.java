package in.pms.master.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.ProjectDocumentDetailsDomain;
import in.pms.master.domain.ProjectDocumentMasterDomain;
import in.pms.master.domain.ProposalDocumentDetailsDomain;
import in.pms.master.domain.ProposalDocumentMasterDomain;
import in.pms.master.domain.ProposalMasterDomain;
import in.pms.master.model.ProjectDocumentMasterModel;

@Repository
public class ProposalDocumentMasterDao {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	@Autowired
	DaoHelper daoHelper;
	
	
	public ProposalDocumentMasterDomain merge(ProposalDocumentMasterDomain proposalDocumentMasterDomain){
		return daoHelper.merge(ProposalDocumentMasterDomain.class, proposalDocumentMasterDomain);
	}

	public ProposalDocumentMasterDomain uploadedProposalDocumentById(ProjectDocumentMasterModel projectDocumentMasterModel){
		String query = "select p from ProposalDocumentMasterDomain p join fetch p.proposalDocumentDetailsDomainList where p.numId="+projectDocumentMasterModel.getNumId();
		List<ProposalDocumentMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;		
	}
	
	public ProposalDocumentDetailsDomain getProposalDocumentDetail(long parentId,long documentFormatId){
		String query= "from ProposalDocumentDetailsDomain where proposalDocumentMasterDomain.numId="+parentId+" and documentFormatMaster.numId="+documentFormatId;
		List<ProposalDocumentDetailsDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	public List<ProposalDocumentMasterDomain> uploadedProposalDocument(ProjectDocumentMasterModel projectDocumentMasterModel){
		String query = "select p from ProposalDocumentMasterDomain p join fetch p.proposalDocumentDetailsDomainList where   p.numIsValid=1 and  p.proposalId="+projectDocumentMasterModel.getProposalId() +" and p.documentTypeMasterDomain="+projectDocumentMasterModel.getDocumentTypeId();
		return daoHelper.findByQuery(query);
	}

	public List<ProposalMasterDomain> getApplicationIdbyProposalId(long proposalId){
			String query = "from ProposalMasterDomain where numId="+proposalId;
			return daoHelper.findByQuery(query);

		}
	public ProposalDocumentDetailsDomain getProposalDocumentDetailsById(long id){
		return daoHelper.findById(ProposalDocumentDetailsDomain.class, id);
	}
	
	//Added by devesh on 27-09-23 to get document by Rev Id
	public List<Object[]> getProposalDocumentDetailsbyRevId(long id, long numId){
		StringBuilder query = new StringBuilder("SELECT str_document_name, str_original_doc_name, d.num_proposal_id, t.num_group_id_fk");
		query.append(" FROM pms_log.pms_proposal_document_details_log p, pms_log.pms_proposal_document_master_log d, pms.application_proposal a, pms.trans_application t");
		query.append(" where p.numid='"+id+"' and p.num_id='"+numId+"' ");
		query.append(" and p.num_id=d.num_id and p.num_proposal_document_id=d.num_proposal_document_id and d.num_proposal_id=a.proposal_id and a.application_id=t.num_application_id");
	    
	    return daoHelper.runNative(query.toString());
	}
	//End of document
	
	public List<ProposalDocumentMasterDomain> uploadedDocumentByProposalId(long proposalId){
		StringBuilder query = new StringBuilder("select distinct p from ProposalDocumentMasterDomain p join fetch p.proposalDocumentDetailsDomainList ");
		/*query.append(" where p.numIsValid=1 and  p.proposalId="+proposalId +" order by p.documentDate desc");*/
		query.append(" where p.numIsValid=1 and  p.proposalId="+proposalId +" order by p.dtTrDate desc");//Added by devesh on 05-10-23 to change order to descending transaction time
		return daoHelper.findByQuery(query.toString());
	}
	
	//Added by devesh on 26-09-23 to get document history
	@SuppressWarnings("unchecked")
	public List<Object[]> uploadedDocumentHistoryByRevId(long numId){
		System.out.println("In function uploadedDocumentHistoryByRevId");
		StringBuilder query = new StringBuilder("SELECT DISTINCT proposaldo0_.num_proposal_document_id AS num_prop1_11_0_,");
		query.append(" proposaldo1_.numId AS numId1_178_1_, proposaldo0_.DT_TR_DATE AS DT_TR_DA2_11_0_,");
		query.append(" proposaldo0_.NUM_ISVALID AS NUM_ISVA3_11_0_, proposaldo0_.NUM_TR_USER_ID AS NUM_TR_U4_11_0_,");
		query.append(" proposaldo0_.str_description AS str_desc5_11_0_, proposaldo0_.dt_document AS dt_docum6_11_0_,");
		query.append(" proposaldo0_.document_type_id AS documen12_11_0_, proposaldo0_.str_Document_version AS str_Docu7_11_0_,");
		query.append(" proposaldo0_.dt_period_from AS dt_perio8_11_0_, proposaldo0_.dt_period_to AS dt_perio9_11_0_,");
		query.append(" proposaldo0_.num_proposal_id AS num_pro10_11_0_, proposaldo0_.version AS version11_11_0_,");
		query.append(" proposaldo1_.DT_TR_DATE AS DT_TR_DA2_178_1_, proposaldo1_.NUM_ISVALID AS NUM_ISVA3_178_1_,");
		query.append(" proposaldo1_.NUM_TR_USER_ID AS NUM_TR_U4_178_1_, proposaldo1_.str_Document_format AS str_Docu5_178_1_,");
		query.append(" (SELECT f.str_format_name FROM pms.pms_document_format_master f WHERE f.num_id=proposaldo1_.document_format_id) AS document8_178_1_,");
		query.append(" proposaldo1_.str_Document_Name AS str_Docu6_178_1_,");
		query.append(" (SELECT f.str_icon FROM pms.pms_document_format_master f WHERE f.num_id=proposaldo1_.document_format_id),");
		query.append(" proposaldo1_.str_original_Doc_Name AS str_orig7_178_1_, proposaldo1_.num_proposal_document_Id AS num_prop9_178_1_,");
		query.append(" proposaldo1_.num_proposal_document_Id AS num_prop9_178_0__, proposaldo1_.numId AS numId1_178_0__,");
		query.append(" (SELECT dtype.num_doc_type_name FROM pms.pms_doc_type_mst dtype WHERE dtype.num_doc_type_id=proposaldo0_.document_type_id),");
		query.append(" (SELECT dtype.str_icon FROM pms.pms_doc_type_mst dtype WHERE dtype.num_doc_type_id=proposaldo0_.document_type_id) AS Document_Icon, proposaldo0_.num_id");
		query.append(" FROM pms_log.pms_proposal_document_master_log proposaldo0_");
		query.append(" INNER JOIN pms_log.pms_proposal_document_details_log proposaldo1_");
		query.append(" ON proposaldo0_.num_proposal_document_id=proposaldo1_.num_proposal_document_Id");
		query.append(" where proposaldo0_.NUM_ISVALID=1 and proposaldo0_.num_id='"+numId+"' and proposaldo0_.num_id=proposaldo1_.num_id order by proposaldo0_.dt_document desc");
	    
	    return daoHelper.runNative(query.toString());
		
	}
	//End of document history
	
	public ProposalDocumentMasterDomain uploadedProjectDocumentById(ProjectDocumentMasterModel projectDocumentMasterModel){
		String query = "select p from ProposalDocumentMasterDomain p join fetch p.proposalDocumentDetailsDomainList where p.numIsValid=1 and p.numId="+projectDocumentMasterModel.getNumId();
		List<ProposalDocumentMasterDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			return list.get(0);
		}
		return null;		
	}
	public  List<ProposalDocumentMasterDomain> showProposalDocumentRevision(long documentId){
		return daoHelper.getPreviousRevisions(ProposalDocumentMasterDomain.class,documentId);
	}
	
	public List<Object[]> getProposalDocumentForDashboard(long proposalId,long docTypeId){
		/*StringBuilder query = new StringBuilder("select max(numId),a.documentTypeMasterDomain.docTypeName,a.documentTypeMasterDomain.classColor from ");
		query.append(" ProposalDocumentMasterDomain a where a.documentTypeMasterDomain.showOnDashboard=1");
		query.append(" and a.numIsValid=1 and a.proposalId="+proposalId +" and a.documentTypeMasterDomain.numId="+docTypeId +" group by 2,3");
		*/
		
		StringBuilder query = new StringBuilder("select max(numId),a.proposalDocumentMasterDomain.documentTypeMasterDomain.docTypeName,a.proposalDocumentMasterDomain.documentTypeMasterDomain.classColor from ");
		query.append(" ProposalDocumentDetailsDomain a where a.proposalDocumentMasterDomain.documentTypeMasterDomain.showOnDashboard=1");
		query.append(" and a.proposalDocumentMasterDomain.numIsValid=1 and a.proposalDocumentMasterDomain.proposalId="+proposalId +" and a.proposalDocumentMasterDomain.documentTypeMasterDomain.numId="+docTypeId +" group by 2,3");
		
		
		return daoHelper.findByQuery(query.toString());
	}
	
	public List<ProposalDocumentMasterDomain> documentDetailsCategoryWiseByProposalId(long proposalId){
		StringBuilder query = new StringBuilder("select distinct p from ProposalDocumentMasterDomain p join fetch p.proposalDocumentDetailsDomainList ");
		query.append(" join fetch p.documentTypeMasterDomain x left join fetch x.documentClassification b ");
		query.append(" where p.numIsValid=1 and  p.proposalId="+proposalId +" order by b.displaySequence,p.documentDate desc");
		return daoHelper.findByQuery(query.toString());
	}
	
	// Dao for count the document from proposaldocumentmaster table by proposal id 
	public List<Long> checkDocumentMaster(long id)
	{
		String query = "SELECT COUNT(*) FROM ProposalDocumentMasterDomain WHERE proposalId = "+id+" AND numIsValid =1";
		return daoHelper.findByQuery(query);
	}
	
	/*----------------------- Query For Get Proposal Document Details [18-03-2024] ------------------------*/
	public List<ProposalDocumentMasterDomain> documentDetails(ProjectDocumentMasterModel projectDocumentMasterModel) {
	    String query = "";
	    query = "select p from ProposalDocumentMasterDomain p " +
                "join fetch p.proposalDocumentDetailsDomainList " +
                "where p.numIsValid = 1 ";
	    query += " and p.proposalId IN (" +projectDocumentMasterModel.getProjectIds()+ ")";
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
	/*-----------------------End of Query For Get Proposal Document Details [18-03-2024] ------------------------*/
}
