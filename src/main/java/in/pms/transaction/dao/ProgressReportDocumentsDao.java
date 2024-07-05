package in.pms.transaction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.pms.transaction.domain.ProgressReportDocumentsDomain;

@Repository
public interface ProgressReportDocumentsDao extends JpaRepository<ProgressReportDocumentsDomain, Long>{

	@Query("from ProgressReportDocumentsDomain e where e.numDocumentId in(:longDocList) and e.numIsValid=1")
	List<ProgressReportDocumentsDomain> getUplodedImages(@Param("longDocList") List<Long> longDocList);

/*	@Query("from ProgressReportDocumentsDomain e where e.numDocumentId in(:strDocumentIds) e.numIsValid=1")
	public List<ProgressReportDocumentsDomain> getUplodedImages(@Param("strDocumentIds") String strDocumentIds);
*/
	
}
