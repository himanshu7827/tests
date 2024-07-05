package in.pms.global.dao;

import in.pms.global.misc.HibernateUtil;
import in.pms.master.domain.DesignationMaster;
import in.pms.master.domain.DocStageMasterDomain;
import in.pms.master.model.NewsLetterFilterForm;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.procedure.ProcedureOutputs;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class DaoHelper {
	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
    public <T> List<T> runNative(String query, final Class<T> entityClass) {
        if (null == entityClass)
            throw new IllegalArgumentException("entityClass can't be null");
        Query query1 = entityManager.createNativeQuery(query, entityClass);
        return castResultList(query1.getResultList());
    }

    public <T> List<T> runNative(String query) {      
        Query query1 = entityManager.createNativeQuery(query);
        return castResultList(query1.getResultList());
    }
    
    //New function added by devesh on 03-06-24 to accept params in query
    public <T> List<T> runNativeQueryWithParams(String query, List<Object> paraList) {
    	List<T> list = new ArrayList<>();
        try {
            Query query1 = entityManager.createNativeQuery(query);
            for (int i = 0; i < paraList.size(); i++) {
                query1.setParameter(i, paraList.get(i)); // Using 0-based index for positional parameters
            }
            list = castResultList(query1.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //End of function

/*	public <T> T persist(final Class<T> entityClass, final T entity) {

		if (null == entityClass){
			throw new IllegalArgumentException("entityClass can't be null");
		}
		if (null == entity){
			throw new IllegalArgumentException("entity can't be null");
		}
		Session session = null;
	    Transaction transaction = null;
	    try {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	transaction = session.beginTransaction();
	    	session.persist(entity);
	    	transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	        	transaction.rollback();
	        }
	        e.printStackTrace();
	      } finally {
	    	  if (session != null) {
	    		  session.close();
	    	  }
	      }
//	      entityManager.persist(entity);
		return entity;
	}*/

	public <T> T persist(final Class<T> entityClass, final T entity) {
		System.out.println("first");
		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == entity)
			throw new IllegalArgumentException("entity can't be null");
		System.out.println("second");
		entityManager.persist(entity);
		return entity;
	}
	public <T> T persistNew(final Class<T> entityClass, final T entity) {

		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == entity)
			throw new IllegalArgumentException("entity can't be null");

		Session session = null;
	    Transaction transaction = null;
	    try {
		session = HibernateUtil.getSessionFactory().openSession();
	      transaction = session.getTransaction();
	      transaction.begin();
	      session.persist(entity);
//	      entityManager.persist(entity);
	      transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	          transaction.rollback();
	        }
	        e.printStackTrace();
	      } finally {
	        if (session != null) {
	          session.close();
	        }
	      }

		return entity;
	}
	
	public <T> List<T> findByAttribute(final Class<T> entityClass,
			final String attributeName, final Object attributeValue) {
		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == attributeName)
			throw new IllegalArgumentException("attributeName can't be null");
		if (null == attributeValue)
			throw new IllegalArgumentException("attributeValue can't be null");
		EntityManager em=HibernateUtil.getSessionFactory().createEntityManager().getEntityManagerFactory().createEntityManager();
		System.out.println("select e from " + entityClass.getSimpleName()
								+ " e where e."
								+ attributeName + " = ?1");
		return castResultList(em.createQuery(
						"select e from " + entityClass.getSimpleName()
								+ " e where e."
								+ attributeName + " = ?1")
				.setParameter(1, attributeValue).getResultList());
	}
	
/*	public <T> T merge(final Class<T> entityClass, final T entity) {
		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == entity)
			throw new IllegalArgumentException("entity can't be null");

		Session session = null;
	    Transaction transaction = null;
	    try {
		session = HibernateUtil.getSessionFactory().openSession();
	      transaction = session.getTransaction();
	      transaction.begin();
	      session.merge(entity);
	      transaction.commit();
	    } catch (Exception e) {
	        if (transaction != null) {
	          transaction.rollback();
	        }
	        e.printStackTrace();
	      } finally {
	        if (session != null) {
	          session.close();
	        }
	      }
		
//		return entityManager.merge(entity);
	    return entity;
	}
	*/
	public <T> T merge(final Class<T> entityClass, final T entity) {
		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == entity)
			throw new IllegalArgumentException("entity can't be null");

		return entityManager.merge(entity);
	}
	/**
	 * Casts the result to a particular class type
	 * 
	 * @param <T>
	 * @param results
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> castResultList(List<?> results) {
		return (List<T>) results;
	}

	
	
	/**
	 * Casts the result to a particular integer type
	 * 
	 * @param <T>
	 * @param results
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <Integer> List<Integer> castResultListInt(List<?> results) {
		return (List<Integer>) results;
	}
	
 
  /* public <T> List<T> findByQuery(final String query, ArrayList<Object> paraList) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query queryObj = session.createQuery(query);
		List<T> datalist=new ArrayList<T>();
		int count=1;
		try {
		if(paraList.size()>0){
	 		for(int i=0;i<paraList.size();i++){
	 			queryObj.setParameter(count, paraList.get(i));
	 			count++;
	 		}
	 		}
		datalist=queryObj.getResultList();
		} catch (Exception e) {
			e.getMessage();
		}finally{
			try {
				session.close();
			} catch (Exception e) {
				e.getMessage();
			    }
		  }  	 
  	 
		return datalist;
	}*/
	
	  public <T> List<T> findByQuery(final String query, ArrayList<Object> paraList) {
		  	 List<T> list=new ArrayList<T>();
			 		try{ 
				Query query1 = entityManager.createQuery(query);
				if(paraList.size()>0){
			 		for(int i=0;i<paraList.size();i++){
			 			query1.setParameter(i, paraList.get(i));
			 		}
			 	}
				list=castResultList(query1.getResultList());
			 		}catch(Exception e){
			 			e.printStackTrace();
			 		}
				return list;
			}
/*	public <T> List<T> findByQuery(final String query, ArrayList<Object> paraList) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query queryObj = session.createQuery(query);
		List<T> datalist=new ArrayList<T>();
		int count=1;
		try {
		if(paraList.size()>0){
	 		for(int i=0;i<paraList.size();i++){
	 			queryObj.setParameter(count, paraList.get(i));
	 			count++;
	 		}
	 	
	 		}
		datalist=queryObj.getResultList();
		System.out.println("datalist===="+datalist.size());
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
		}finally{
			try {
				session.close();
			} catch (Exception e) {
				e.getMessage();
			    }
		  }  	 
  	 
		return datalist;
	}*/
   
	public List<Object[]> getData(String query,ArrayList<Object> paraList) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query queryObj = session.createNativeQuery(query);
		List<Object[]> datalist=new ArrayList<Object[]>();
		int count=1;
		try {
		if(paraList.size()>0){
	 		for(int i=0;i<paraList.size();i++){
	 			queryObj.setParameter(count, paraList.get(i));
	 			count++;
	 		}
	 		}

		datalist=queryObj.getResultList();
		} catch (Exception e) {
			e.getMessage();
		}finally{
			try {
				session.close();
			} catch (Exception e) {
				e.getMessage();
			    }
		  }
		return datalist;
	}
	
	public String GenerateHashvalue(String funname, String token,long userid  )
	{
		System.err.println("GenerateHashvalue");
		String resulString ="";
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(funname);
			procedureCall.registerParameter(1, String.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Long.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(token);
			procedureCall.getParameterRegistration(2).bindValue(userid);                
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		System.err.println("GenerateHashvalue "+resulString);
		return resulString;
	}
	
	
	public int  findChecklistStatus(String fun, int formNo, int formId) {
		

		
		int resulString = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formNo);
			procedureCall.getParameterRegistration(2).bindValue(formId);                
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			List results = resultSetOutput.getResultList();
			resulString =(Integer)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			resulString =0;
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString;
	}
	
	public String getListJSON(String fun, int divisionId )
	{
		
		String resulString ="";
		Session session = (Session) entityManager.getDelegate();
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		conn = HibernateUtil.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
		conn.setAutoCommit(false);
		stmt = conn.prepareStatement("select "+fun+"(?)");
		stmt.setInt(1,divisionId);
		ResultSet rs = stmt.executeQuery(); 
	    rs.next(); 

	    resulString=rs.getObject(1).toString();
	    rs.close(); 
	   
		conn.commit();
		} catch (SQLException e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
				
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return resulString;
	}

	public String checklistJSON(String fun, String searchTerm,String Query,int sAmount,int sStart )
	{
		
		String resulString ="";
		Session session = (Session) entityManager.getDelegate();
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		conn = HibernateUtil.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
		conn.setAutoCommit(false);
		stmt = conn.prepareStatement("select "+fun+"(?,?,?,?)");
		stmt.setString(1,searchTerm);
		stmt.setString(2,Query);
		stmt.setInt(3,sAmount);
		stmt.setInt(4,sStart);
		ResultSet rs = stmt.executeQuery(); 
	    rs.next(); 

	    resulString=rs.getObject(1).toString();
	    rs.close(); 
	   
		conn.commit();
		} catch (SQLException e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
				
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return resulString;
	}
	

	public String callProcedure(String fun, int ...intargs) {
		String resulString ="";
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			int i=0;
			for (int arg : intargs) {
				i++;
				procedureCall.registerParameter(i, Integer.class, ParameterMode.IN);
				procedureCall.getParameterRegistration(i).bindValue(arg);
		    }
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
			if(resulString==null || resulString.trim() ==""){
				resulString = "NA"; 
			}
		} catch (Exception e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString;
	}
	
	
	//added by shruti on 24-11-2017
	public int deleteByQuery(final String query,ArrayList<Object> paraList) {
		Query query1=entityManager.createQuery(query);
		if(paraList.size()>0){
			for(int i=0;i<paraList.size();i++){
				query1.setParameter(i+1, paraList.get(i));
			}
			}
		return	query1.executeUpdate();
		
	}
	
	public String Viewxml(String fun,int formno, int formid) {

		String resulString ="";
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formno);
			procedureCall.getParameterRegistration(2).bindValue(formid);
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();

			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString;		
}


	public List<String>  DashboardCount(String fun,int formno, int formid) {
		List<String> resulString = new ArrayList<String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formno);
			procedureCall.getParameterRegistration(2).bindValue(formid);                
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			resulString = resultSetOutput.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
}

	public void fileSubmission(String fun, int formNo, int formId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formNo);
			procedureCall.getParameterRegistration(2).bindValue(formId);                
			procedureCall.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}
	
	public void insertToQueryMst(String procedureName, String queryText,int seqNo,int numFormId,int numFormNo,int decision,int userId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(procedureName);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(3, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(4, String.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(seqNo);
			procedureCall.getParameterRegistration(2).bindValue(decision);                
			procedureCall.getParameterRegistration(3).bindValue(userId);
			procedureCall.getParameterRegistration(4).bindValue(queryText);                
			procedureCall.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
	}
	

  // ADDED BY DEEPSHIKHA
	public String callProcedurePortData(String funName, String  username) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(funName);
			procedureCall.registerParameter(1, String.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(username);
			procedureCall.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return username;
 	}
	
	public <T> List<T> findByQuery(final String query) {
		Query query1 = entityManager.createQuery(query);
		return castResultList(query1.getResultList());
	}
	
	public int deleteByAttribute(final Class<?> entityClass,
			final String attributeName, final Object attributeValue) {
		if (null == entityClass)
			throw new IllegalArgumentException("entityClass can't be null");
		if (null == attributeName)
			throw new IllegalArgumentException("attributeName can't be null");
		if (null == attributeValue)
			throw new IllegalArgumentException("attributeValue can't be null");

		return entityManager
				.createQuery(
						"delete from " + entityClass.getSimpleName()
								+ " e where e." + attributeName + " = ?1")
				.setParameter(1, attributeValue).executeUpdate();
	}
	
	//added by sonam
	public String GenerateAppNo(String fun,long formNo, long formId,long divisionId,long caseId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Long.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Long.class, ParameterMode.IN);
			procedureCall.registerParameter(3, Long.class, ParameterMode.IN);
			procedureCall.registerParameter(4, Long.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formNo);
			procedureCall.getParameterRegistration(2).bindValue(formId);                
			procedureCall.getParameterRegistration(3).bindValue(divisionId);
			procedureCall.getParameterRegistration(4).bindValue(caseId);                
			procedureCall.execute();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
	}
	

//added by sonam
	public String findByFun(String fun, int formid,int formno,Timestamp dt) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(3, Timestamp.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formid);
			procedureCall.getParameterRegistration(2).bindValue(formno);                
			procedureCall.getParameterRegistration(3).bindValue(dt);
			procedureCall.executeUpdate();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
			if(resulString==null || resulString.trim() ==""){
				resulString = "Approval No"; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
	}

	public String getName(String fun,int ...args) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);

			int i=0;
			for (int arg : args) {
				i++;
				procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
				procedureCall.getParameterRegistration(i).bindValue(arg);
		    }
			
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
			if(resulString==null || resulString.trim() ==""){
				resulString = "No Data"; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString;
}

	
	public int dmlFileTransfer(String fun,int formNo,int formId,int transferSeatId,int numStatus, String remarks ) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		int resulString=0;
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(3, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(4, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(5, String.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(formNo);
			procedureCall.getParameterRegistration(2).bindValue(formId);                
			procedureCall.getParameterRegistration(3).bindValue(transferSeatId);
			procedureCall.getParameterRegistration(4).bindValue(numStatus);
			procedureCall.getParameterRegistration(5).bindValue(remarks);
			procedureCall.execute();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(Integer)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
	}
	
	//by priyanka 09-01-18
	public String Viewxml(String fun,int userid) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(userid);
			procedureCall.execute();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
}

	
	//added by sonam
	public String findByFun1(String fun, Long applicationId) {
		Connection connection = null;
	    CallableStatement statement = null;
		String result ="";

	    try {
			 connection = getConnection();

	    	connection.setAutoCommit(false);
	        statement = connection.prepareCall("{? = CALL " + fun + "(?)}");
	        statement.registerOutParameter(1, Types.VARCHAR);
	        statement.setLong(2, applicationId);
	       
	        statement.executeUpdate();

	        result = statement.getString(1);
	        connection.commit();
	        
	    } catch (SQLException e) {
	    	result = "";
	        e.printStackTrace();
	    } finally {
	        try {

	        	connection.setAutoCommit(true);
	            statement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	/*	Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Long.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(attributeValue);
			procedureCall.executeUpdate();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
			if(resulString==null || resulString.trim() ==""){
				resulString = "No Data"; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}*/
		return result; 
	}

	public String generateFun(String fun, String format) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		String resulString="";
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, String.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(format);
			procedureCall.execute();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(String)resultSetOutput.getSingleResult();
			if(resulString==null || resulString.trim() ==""){
				resulString = "No Data"; 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
		
	}
	
	// Added By Deepshikha (21-08-2018)
	
	public int ReportStatusFunction(String fun,int specId, int frwdMode) 
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		int resulString=0;
		try {
			ProcedureCall procedureCall = session.createStoredProcedureCall(fun);
			procedureCall.registerParameter(1, Integer.class, ParameterMode.IN);
			procedureCall.registerParameter(2, Integer.class, ParameterMode.IN);
			procedureCall.getParameterRegistration(1).bindValue(specId);
			procedureCall.getParameterRegistration(2).bindValue(frwdMode);
			procedureCall.execute();
			ProcedureOutputs procedureOutputs = procedureCall.getOutputs();
			ResultSetOutput resultSetOutput = (ResultSetOutput) procedureOutputs.getCurrent();
			List results = resultSetOutput.getResultList();
			resulString =(int)resultSetOutput.getSingleResult();
			System.out.println("result String == "+resulString);
			/*if(resulString==null || resulString.trim() ==""){
				resulString = "No Data"; 
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
			    session.close();
			} catch (Exception e) {
				e.getMessage();
			}
		}
		return resulString; 
		
	}
	public String getListJSONByInstId(String fun, int instId, int divisionId) {
		
		String resulString ="";
		Session session = (Session) entityManager.getDelegate();
		SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
		conn = HibernateUtil.getSessionFactory().getSessionFactoryOptions().getServiceRegistry().getService(ConnectionProvider.class).getConnection();
		conn.setAutoCommit(false);
		stmt = conn.prepareStatement("select "+fun+"(?,?)");
		stmt.setInt(1,instId);
		stmt.setInt(2,divisionId);
		ResultSet rs = stmt.executeQuery(); 
	    rs.next(); 

	    resulString=rs.getObject(1).toString();
	    rs.close(); 
	   
		conn.commit();
		} catch (SQLException e) {
			resulString ="";
			e.printStackTrace();
		}finally{
			try {
				
				conn.setAutoCommit(true);
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return resulString;
	}

	public Integer callStoredProcedure(String procName,Integer a)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx=session.beginTransaction();
		ProcedureCall pc=session.createStoredProcedureCall(procName);
		pc.registerParameter(1, Integer.class,ParameterMode.IN);
		pc.registerParameter(2,Integer.class, ParameterMode.OUT);
		pc.setParameter(1, a);
		pc.execute();
		Integer out=(Integer)pc.getOutputParameterValue(2);
		tx.commit();
		return out;
	}
	public void merge(Class<DesignationMaster> class1, DocStageMasterDomain sch) {
		// TODO Auto-generated method stub
		
	}
	
	public <T> T findById(final Class<T> entityClass, final Object id) {
        if (null == entityClass)
            throw new IllegalArgumentException("entityClass can't be null");
        if (null == id)
            throw new IllegalArgumentException("id can't be null");

        return entityManager.find(entityClass, id);
    }
	
	
	  public <T> List<T> findByIdList(final String query, List<Long> ids) {
		  	 List<T> list=new ArrayList<T>();
			 		try{ 
				Query query1 = entityManager.createQuery(query);
				query1.setParameter("ids", ids);
				list=castResultList(query1.getResultList());
			 		}catch(Exception e){
			 			e.printStackTrace();
			 		}
				return list;
			}
	  
	  public <T> List<T> findByIdListInteger(final String query, List<Integer> ids) {
		  	 List<T> list=new ArrayList<T>();
			 		try{ 
				Query query1 = entityManager.createQuery(query);
				query1.setParameter("ids", ids);
				list=castResultList(query1.getResultList());
			 		}catch(Exception e){
			 			e.printStackTrace();
			 		}
				return list;
			}
	  
	  public  <T> List<T> getPreviousRevisions(Class<T> entityClass, Object entityId) {
		  Session  session = (Session)entityManager.getDelegate();
		    AuditReader reader = AuditReaderFactory.get(session);		  
		    List<T> list=new ArrayList<T>();		    
		    AuditQuery q = reader.createQuery().forRevisionsOfEntity(entityClass, true, true);		  
		    q.add(AuditEntity.id().eq(entityId));		  
		    list = castResultList(q.getResultList());
		    return list;
		}

	  public Connection getConnection(){	   
	    	Session session = (Session) entityManager.getDelegate();
	    	SessionFactory sessionFactory= session.getSessionFactory();
	    	Connection connection=null;
	    	try {
	    		connection = sessionFactory.getSessionFactoryOptions().getServiceRegistry().
				getService(ConnectionProvider.class).getConnection();
			} catch (SQLException e1) {				
				e1.printStackTrace();
			}
	   
	    	//System.out.println("Connection Obj "+connection);
	    	return connection;
	    }

	  public <T> List<T> findByAttributes(final Class<T> entityClass, final String[] attributeNames,
	            final Object[] attributeValues) {
	        if (null == entityClass)
	            throw new IllegalArgumentException("entityClass can't be null");
	        if (null == attributeNames)
	            throw new IllegalArgumentException("attributeName can't be null");
	        if (null == attributeValues)
	            throw new IllegalArgumentException("attributeValue can't be null");

	        String whereClause = " e where  ";
	        int n = attributeNames.length;
	        int i = 0;

	        for (; i < (n - 1); i++) {
	            whereClause += "e." + attributeNames[i] + "='" + attributeValues[i] + "' and ";
	        }
	        whereClause += "e." + attributeNames[i] + "='" + attributeValues[i] + "'";

	        return castResultList(entityManager.createQuery("select e from " + entityClass.getSimpleName() + whereClause)
	                .getResultList());
	    }
	  
		public String findByFun2(String fun, long numTransactionId, int numMonthlyProgrssId) {

	        String result="error";
	
	        Connection conn = null;
	        CallableStatement stmt = null;

	        try {
	            conn = getConnection();
	            conn.setAutoCommit(false);
	            stmt = conn.prepareCall("{? = CALL " + fun + "(?,?)}");
	            stmt.registerOutParameter(1, Types.VARCHAR);
	            stmt.setLong(2, numTransactionId);
	            stmt.setInt(3, numMonthlyProgrssId);
	            stmt.executeUpdate();

	            result = stmt.getString(1);

	            conn.commit();
	            
	        } catch (SQLException e) {
	        	result = "error";
	            e.printStackTrace();
	        } finally {
	            try {

	                conn.setAutoCommit(true);
	                stmt.close();
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return result;
	    
		}
		
		 public <T> List<T> findByAttributes(final Class<T> entityClass, final String attributeName,
		            final Object attributeValue) {
		        if (null == entityClass)
		            throw new IllegalArgumentException("entityClass can't be null");
		        if (null == attributeName)
		            throw new IllegalArgumentException("attributeName can't be null");
		        if (null == attributeValue)
		            throw new IllegalArgumentException("attributeValue can't be null");

		        return castResultList(entityManager
		                .createQuery("select e from " + entityClass.getSimpleName() + " e where e." + attributeName + " = ?1")
		                .setParameter(1, attributeValue).getResultList());
		    }
	  

		 public <T> List<T> runNative(String query,List<Long> ids) {      
		        Query query1 = entityManager.createNativeQuery(query);
		        query1.setParameter("ids", ids);
		        return castResultList(query1.getResultList());
		    }
		 
		 public List<NewsLetterFilterForm> getUsersForNewsLetter(String query) {
			    /*    Session session = (Session) entityManager.getDelegate();
			        SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
			        ConnectionProvider cp = sfi.getConnectionProvider();*/
			        List<NewsLetterFilterForm> datalist = new ArrayList<NewsLetterFilterForm>();

			        Connection conn = null;
			        Statement stmt = null;
			        try {
			            conn = getConnection();
			            stmt = conn.createStatement();
			            ResultSet rs = stmt.executeQuery(query);
			            while (rs.next()) {
			                NewsLetterFilterForm scmp = new NewsLetterFilterForm();

			                scmp.setStrEmailId(rs.getString("str_user_name"));

			                scmp.setNumUserId(rs.getInt("num_user_id"));

			                scmp.setStrMobileNumber(rs.getString("mobile_no"));

			                datalist.add(scmp);

			            }

			        } catch (SQLException e) {
			            e.printStackTrace();
			        } finally {
			            try {
			                conn.setAutoCommit(true);
			                stmt.close();
			                conn.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }

			        return datalist;
			    }
		 
		 
		 public List<NewsLetterFilterForm> gePropNewsLetter(String query) {
		      
		        List<NewsLetterFilterForm> datalist = new ArrayList<NewsLetterFilterForm>();

		        Connection conn = null;
		        Statement stmt = null;
		        try {
		            conn = getConnection();
		            stmt = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(query);
		            while (rs.next()) {
		                NewsLetterFilterForm scmp = new NewsLetterFilterForm();

		               // scmp.setStrEmailId("anshujain@cdac.in");
		                scmp.setNumProjectId(rs.getInt("num_project_id"));

		           
		                datalist.add(scmp);

		            }

		        } catch (SQLException e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                conn.setAutoCommit(true);
		                stmt.close();
		                conn.close();
		            } catch (SQLException e) {
		                e.printStackTrace();
		            }
		        }

		        return datalist;
		    }
		 
		    public boolean delete(final Object entity) {
		        if (null == entity)
		            throw new IllegalArgumentException("entity can't be null");

		        entityManager.remove(entity);
		        return true;
		    }
}
