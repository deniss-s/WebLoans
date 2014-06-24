package lv.iljakorneckis.webloans.domain;

import org.joda.money.Money;
import org.joda.time.DateTime;

/**
 * Represents a user's loan application attempt
 */
public class LoanApplication {

    private String userId;
    private Money amount;
    private DateTime term;
    private DateTime applicationDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public DateTime getTerm() {
        return term;
    }

    public void setTerm(DateTime term) {
        this.term = term;
    }

    public DateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(DateTime applicationDate) {
        this.applicationDate = applicationDate;
    }
}