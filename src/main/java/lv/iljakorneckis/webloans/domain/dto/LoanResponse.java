package lv.iljakorneckis.webloans.domain.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LoanResponse {

    private Long id;
    private String userId;
    private BigDecimal amount;
    private String currency;
    private BigDecimal interest;
    private Date applicationDate;
    private Date endDate;

    private List<LoanExtensionResponse> extensionHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<LoanExtensionResponse> getExtensionHistory() {
        return extensionHistory;
    }

    public void setExtensionHistory(List<LoanExtensionResponse> extensionHistory) {
        this.extensionHistory = extensionHistory;
    }
}