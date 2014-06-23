package lv.iljakorneckis.webloans.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * Created by Admin_2 on 2014-06-23.
 */

@Entity
public class LoanExtension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime extensionDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getExtensionDate() {
        return extensionDate;
    }

    public void setExtensionDate(DateTime extensionDate) {
        this.extensionDate = extensionDate;
    }
}
