package lv.iljakorneckis.webloans.domain;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @Columns(columns = { @Column(name = "CURRENCY"), @Column(name = "AMOUNT") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money amount;

    private BigDecimal interest;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime applicationDate;

    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanExtension> extensionHistrory;

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public DateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(DateTime applicationDate) {
        this.applicationDate = applicationDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public List<LoanExtension> getExtensionHistrory() {
        return extensionHistrory;
    }

    public void setExtensionHistrory(List<LoanExtension> extensionHistrory) {
        this.extensionHistrory = extensionHistrory;
    }
}
