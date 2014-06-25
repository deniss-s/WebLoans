package lv.iljakorneckis.webloans.component;

import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanApplication;
import lv.iljakorneckis.webloans.domain.LoanRiskAssessment;
import lv.iljakorneckis.webloans.enums.RiskStatus;
import lv.iljakorneckis.webloans.repository.LoanRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class LoanRiskAssessorImpl implements LoanRiskAssessor {

    public static final Money MAX_AMOUNT_EUR = Money.of(CurrencyUnit.EUR, new BigDecimal(1000));
    public static final Integer MAX_LOANS_PER_USER_PER_DAY = 3;
    /**
     * Hour of day when "morning" starts
     */
    private static final Integer MORNING_HOUR = 7;

    @Autowired
    private LoanRepository loanRepo;

    @Override
    public LoanRiskAssessment assessRisk(LoanApplication loanApplication) {
        List<Loan> loanList = loanRepo.findByUserId(loanApplication.getUserId());

        if(isMaxNumberOfApplicationsPerDay(loanList, MAX_LOANS_PER_USER_PER_DAY)) {
            return new LoanRiskAssessment(RiskStatus.TOO_MANY_APPLICATIONS, "Maximum number of loans reached");
        }

        // If application is made at night with max possible amount
        if(isNightTime(loanApplication.getApplicationDate()) && MAX_AMOUNT_EUR.isEqual(loanApplication.getAmount())) {
            return new LoanRiskAssessment(RiskStatus.APPLIED_AFTER_MIDNIGHT, "Application made after midnight with maximum allowed amount");
        }

        return new LoanRiskAssessment(RiskStatus.OK, "Loan risk is acceptable");
    }


    private boolean isNightTime(DateTime applicationDate) {
        LocalDateTime localApplicationDate = applicationDate.toLocalDateTime();
        return localApplicationDate.getHourOfDay() < MORNING_HOUR;
    }

    private boolean isMaxNumberOfApplicationsPerDay(List<Loan> userLoans, Integer maxNumberPerDay) {
        LocalDate today = DateTime.now().toLocalDate();

        int numberOfApplicationsPerToday = 0;

        for(Loan loan : userLoans) {
            LocalDate loanDate = loan.getApplicationDate().toLocalDate();

            if(today.compareTo(loanDate) == 0) {
                numberOfApplicationsPerToday += 1;
            }
        }

        return numberOfApplicationsPerToday == maxNumberPerDay;
    }
}
