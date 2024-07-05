package in.pms.transaction.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.pms.master.domain.States;

@Repository
public interface StatesDao extends JpaRepository<States,Integer> {

	@Query("from States s where s.numStateId!=0 order by s.strStateName")
	public List<States> GetStateList();
	
}
