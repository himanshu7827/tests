package in.pms.master.dao;

import in.pms.global.dao.DaoHelper;
import in.pms.master.domain.EmployeeSalaryDomain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeSalaryDao {

	@Autowired
	DaoHelper daoHelper;
	
	public List<EmployeeSalaryDomain> getAllEmployeeSalData(long empId){
		String query = "from EmployeeSalaryDomain where numIsValid=1 and employeeId="+empId;
		return  daoHelper.findByQuery(query);	
	}
	
	public long saveUpdateEmployeeSal(EmployeeSalaryDomain employeeSalaryDomain){
		employeeSalaryDomain = daoHelper.merge(EmployeeSalaryDomain.class, employeeSalaryDomain);
		return employeeSalaryDomain.getNumId();
	}
	
	public EmployeeSalaryDomain saveUpdateEmployeeSalary(EmployeeSalaryDomain employeeSalaryDomain){
		return employeeSalaryDomain = daoHelper.merge(EmployeeSalaryDomain.class, employeeSalaryDomain);
	}
	
	public EmployeeSalaryDomain getAllEmployeeSalDataById(long numId){
		EmployeeSalaryDomain employeeSalaryDomain =null;
		String query = "from EmployeeSalaryDomain where numId="+numId;
		List<EmployeeSalaryDomain> list = daoHelper.findByQuery(query);
		if(list.size()>0){
			employeeSalaryDomain =list.get(0);
		}
		return employeeSalaryDomain;	
	}
	
	public List<EmployeeSalaryDomain> getDatabyEndDate(long empId,long numId){
		String query = "from EmployeeSalaryDomain where employeeId='"+empId+"' and endDate IS NULL and numIsValid=1 and numId <> "+numId;
		return  daoHelper.findByQuery(query);	
	}
	
	public List<EmployeeSalaryDomain> getSameRecordData(long empId,String d1,String d2,long numId){
		StringBuffer buffer = new StringBuffer("select * from pms_employee_salary_mst e WHERE e.NUM_ISVALID=1  and e.num_employee_id="+empId+"and num_id <>"+numId);
		if(null != d2 && !d2.equals("")){
				/* buffer.append(" and ((cast(e.dt_start as date) between to_date('" +d1 +"','dd/mm/yy') and to_date('" +d2);
					buffer.append("','dd/mm/yy')  ) or (cast(e.dt_end as date) between to_date('"+ d1 + "','dd/mm/yy') and to_date('"+d2+"','dd/mm/yy') ))"); 
				*/buffer.append(" and ((cast(e.dt_start as date) between to_date('" +d1 +"','dd/mm/yy') and to_date('" +d2) ;
				buffer.append("','dd/mm/yy')  ) or (cast(e.dt_end as date) between to_date('"+ d1 + "','dd/mm/yy') and to_date('"+d2); 
				buffer.append("','dd/mm/yy') )) UNION select * from pms_employee_salary_mst e WHERE  e.NUM_ISVALID=1  and e.num_employee_id="+empId );
				buffer.append(" and ((to_date('" +d1 +"','dd/mm/yy') between cast(e.dt_start as date) and cast(e.dt_end as date)) ");
				buffer.append(" or (to_date('" +d2 +"','dd/mm/yy')between cast(e.dt_start as date) and cast(e.dt_end as date )))");
		}else{
			buffer.append(" and to_date('"+d1+"','dd/mm/yy') between cast(e.dt_start as date) and cast(e.dt_end as date)");
			
		}

		//System.out.println(buffer);
		return  daoHelper.runNative(buffer.toString(),EmployeeSalaryDomain.class);
	
		
	}
	
	public double getEmployeeSalary(long employeeId,String startDate,String endDate){
		StringBuilder query = new StringBuilder("select num_salary from pms_employee_salary_mst a where a.num_isvalid=1 and  a.num_employee_id="+employeeId);
		query.append(" and cast(a.dt_start as date) <= to_date('"+startDate+"','dd/MM/yyyy')");
		query.append("and (a.dt_end is null or cast(a.dt_end as date) >= to_date('"+endDate+"','dd/MM/yyyy'))");
		   
			List<Double> dataList = daoHelper.runNative(query.toString());	
		 if(dataList.size()>0){
			 return dataList.get(0);					 
		 }
			 
		return 0.0;
	}
}