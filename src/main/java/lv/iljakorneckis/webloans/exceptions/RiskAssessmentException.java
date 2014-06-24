package lv.iljakorneckis.webloans.exceptions;

import lv.iljakorneckis.webloans.enums.RiskStatus;

public class RiskAssessmentException extends Exception {
    private RiskStatus status;
    private String message;

    public RiskAssessmentException(RiskStatus status, String message) {
        super(message);
        this.status = status;
    }

    public RiskStatus getStatus() {
        return status;
    }
}
