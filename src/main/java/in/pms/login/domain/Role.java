package in.pms.login.domain;

import in.pms.master.domain.EmployeeMasterDomain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Audited
@AuditTable(value="pms_role_log",schema="pms_log")
public class Role {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.TABLE,generator="roleMaster")
		@TableGenerator(name="roleMaster", initialValue=1, allocationSize=1)	
	    private Long id;

	    @ManyToMany(mappedBy = "roles")
	    private Collection<EmployeeMasterDomain> employees;

	    @ManyToMany(fetch = FetchType.EAGER)
	    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	    private Collection<Privilege> privileges;

	    private String name;
	    
	    @Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        return result;
	    }

	    @Override
	    public boolean equals(final Object obj) {
	        if (this == obj) {
	            return true;
	        }
	        if (obj == null) {
	            return false;
	        }
	        if (getClass() != obj.getClass()) {
	            return false;
	        }
	        final Role role = (Role) obj;
	        if (!role.equals(role.name)) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        final StringBuilder builder = new StringBuilder();
	        builder.append("Role [name=").append(name).append("]").append("[id=").append(id).append("]");
	        return builder.toString();
	    }
}
