package lv.iljakorneckis.webloans.component;

import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanApplication;
import lv.iljakorneckis.webloans.domain.LoanRiskAssessment;
import lv.iljakorneckis.webloans.enums.RiskStatus;
import lv.iljakorneckis.webloans.repository.LoanRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class LoanRiskAssessorImpl implements LoanRiskAssessor {

    public static final Money MAX_AMOUNT_EUR = Money.of(CurrencyUnit.EUR, new BigDecimal(1000));
    public static final Integer MAX_LOANS_PER_USER = 3;
    /**
     * Hour of day when "morning" starts
     */
    private static final Integer MORNING_HOUR = 7;

    @Autowired
    private LoanRepository loanRepo;

    @Override
    public LoanRiskAssessment assessRisk(LoanApplication loanApplication) {
        List<Loan> loanList = loanRepo.findByUserId(loanApplication.getUserId());

        if(loanList.size() == MAX_LOANS_PER_USER) {
            return new LoanRiskAssessment(RiskStatus.TOO_MANY_APPLICATIONS, "Maximum number of loans reached");
        }

        // If application is made at night with max possible amount
        if(isNightTime(loanApplication.getApplicationDate()) && MAX_AMOUNT_EUR.isEqual(loanApplication.getAmount())) {
            return new LoanRiskAssessment(RiskStatus.APPLIED_AFTER_MIDNIGHT, "Application made after midnight with maximum allowed amount");
        }

        return new LoanRiskAssessment(RiskStatus.OK, "Loan risk is acceptable");
    }


    private boolean isNightTime(DateTime applicationDate) {
        DateTime midnight = DateTime.now();
        DateTime morning = midnight.plusHours(MORNING_HOUR);

        Interval allowedApplicationInterval = new Interval(midnight, morning);

        return allowedApplicationInterval.contains(applicationDate);
    }
}
