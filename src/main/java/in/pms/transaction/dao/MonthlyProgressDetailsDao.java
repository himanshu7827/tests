package in.pms.transaction.dao;

import in.pms.transaction.domain.MonthlyProgressDetails;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MonthlyProgressDetailsDao extends JpaRepository<MonthlyProgressDetails, Integer>{

	@Query("from MonthlyProgressDetails a where a.numIsValid=1 and a.monthlyProgressDomain.numId=:monthlyProgressId and a.numCateoryId=:categoryId")
	public List<MonthlyProgressDetails> getMonthlyProgressDetails(@Param("monthlyProgressId")int monthlyProgressId,@Param("categoryId")long categoryId );

	@Query("from MonthlyProgressDetails e where e.monthlyProgressDomain.numId=:id and e.numIsValid=1 order by e.numCategorySequence")
	public List<MonthlyProgressDetails> getMonthlyProgressDetails(@Param("id") int numDecMonthlyProgressId);
	
	@Query("from MonthlyProgressDetails a where a.numIsValid=1 and a.monthlyProgressDomain.numId=:monthlyProgressId order by a.numCategorySequence")
	public List<MonthlyProgressDetails> getMonthlyProgressDetailsByPid(@Param("monthlyProgressId")int monthlyProgressId );
	
	@Query(value="select min(a.numCategorySequence) from MonthlyProgressDetails a where a.numIsValid=1 and a.numParentCateory=0 and a.monthlyProgressDomain.numId=:monthlyProgressId")
	public int getMinCategoryByPId(@Param("monthlyProgressId")int monthlyProgressId);
}
