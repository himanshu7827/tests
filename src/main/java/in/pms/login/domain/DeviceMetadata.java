package in.pms.login.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Audited
@AuditTable(value="pms_device_deta_data_log",schema="pms_log")
public class DeviceMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long employeeId;
    private String deviceDetails;
    private String location;
    private Date lastLoggedIn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceMetadata that = (DeviceMetadata) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(deviceDetails, that.deviceDetails) &&
                Objects.equals(location, that.location) &&
                Objects.equals(lastLoggedIn, that.lastLoggedIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, employeeId, deviceDetails, location, lastLoggedIn);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceMetadata{");
        sb.append("id=").append(id);
        sb.append(", employeeIdId=").append(employeeId);
        sb.append(", deviceDetails='").append(deviceDetails).append('\'');
        sb.append(", location='").append(location).append('\'');
        sb.append(", lastLoggedIn=").append(lastLoggedIn);
        sb.append('}');
        return sb.toString();
    }

}
