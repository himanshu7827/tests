package in.pms.transaction.dao;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import in.pms.transaction.domain.CategoryMaster;

@Repository
public interface ProgressReportDao extends JpaRepository<CategoryMaster, Long>{
	
	@Query("from CategoryMaster c where c.numParentCategory=0 and c.strProjectGroupFlag=:strRole order by c.numCategorySequence")
	public List<CategoryMaster> getAllCategoryList(@Param("strRole") String strRole);
	
	@Query("from CategoryMaster c where c.numParentCategory=0 order by c.numCategorySequence")
	public List<CategoryMaster> getAllCategoryListForGC();
	
	@Query("from CategoryMaster c where c.numParentCategory=:numCategoryId order by c.numCategorySequence")
	public List<CategoryMaster> getSubCategoryList(@Param("numCategoryId") int numCategoryId);

	@Query("select c.numCategoryId,c.strCategoryController from CategoryMaster c where c.numCategorySequence in(:numPrevSeq,:numNextSeq) order by c.numCategorySequence")
	public List<Object[]> getPrevNextBtnController(@Param("numPrevSeq")int numPrevSeq, @Param("numNextSeq")int numNextSeq);
	
	@Query("from CategoryMaster c where c.numCategoryId =:numCategoryId")
	public List<CategoryMaster> getCategoryDetailsById(@Param("numCategoryId")long numCategoryId);
	
	
	
}
