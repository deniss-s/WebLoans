package lv.iljakorneckis.webloans.component;


import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanApplication;
import lv.iljakorneckis.webloans.domain.LoanRiskAssessment;
import lv.iljakorneckis.webloans.enums.RiskStatus;
import lv.iljakorneckis.webloans.repository.LoanRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoanRiskAssessorImplTest {

    private static final String VALID_USER_ID = "USER1";
    private static final String USER_TOO_MANY_LOANS = "USER_MAX_LOANS";


    @Mock
    private LoanRepository loanRepo;

    @InjectMocks
    private LoanRiskAssessorImpl riskAssessor;

    @Before
    public void init() {
        assertThat(riskAssessor, not(nullValue()));

        when(loanRepo.findByUserId(VALID_USER_ID)).thenReturn(Arrays.asList(new Loan[] {}));
        when(loanRepo.findByUserId(USER_TOO_MANY_LOANS)).
                thenReturn(Arrays.asList(new Loan[]{mock(Loan.class), mock(Loan.class), mock(Loan.class)}));
    }

    @Test
    public void testAssessRiskValidCase() {
        LoanApplication validApplication = mock(LoanApplication.class);
        when(validApplication.getAmount()).thenReturn(Money.of(CurrencyUnit.EUR, BigDecimal.TEN));
        when(validApplication.getApplicationDate()).thenReturn(DateTime.now());
        when(validApplication.getTerm()).thenReturn(DateTime.now().plusMonths(5));
        when(validApplication.getUserId()).thenReturn(VALID_USER_ID);

        LoanRiskAssessment assessment = riskAssessor.assessRisk(validApplication);

        assertThat(assessment, not(nullValue()));
        assertThat(assessment.getStatus(), equalTo(RiskStatus.OK));

        verify(loanRepo, times(1)).findByUserId(validApplication.getUserId());
    }

    @Test
    public void testAssessRiskTooManyApplications() {
        LoanApplication tooManyLoansApplication = mock(LoanApplication.class);
        when(tooManyLoansApplication.getAmount()).thenReturn(Money.of(CurrencyUnit.EUR, BigDecimal.TEN));
        when(tooManyLoansApplication.getApplicationDate()).thenReturn(DateTime.now());
        when(tooManyLoansApplication.getTerm()).thenReturn(DateTime.now().plusMonths(5));
        when(tooManyLoansApplication.getUserId()).thenReturn(USER_TOO_MANY_LOANS);

        LoanRiskAssessment assessment = riskAssessor.assessRisk(tooManyLoansApplication);

        assertThat(assessment, not(nullValue()));
        assertThat(assessment.getStatus(), equalTo(RiskStatus.TOO_MANY_APPLICATIONS));
    }

    @Test
    public void testAssessRiskMaxAmountAfterCutoffDate() {
        LoanApplication maxAmountAfterMidnightApplication = mock(LoanApplication.class);
        when(maxAmountAfterMidnightApplication.getAmount()).thenReturn(LoanRiskAssessorImpl.MAX_AMOUNT_EUR);
        when(maxAmountAfterMidnightApplication.getApplicationDate()).thenReturn(DateTime.now());
        when(maxAmountAfterMidnightApplication.getTerm()).thenReturn(DateTime.now().plusMonths(5));
        when(maxAmountAfterMidnightApplication.getUserId()).thenReturn(VALID_USER_ID);

        LoanRiskAssessment assessment = riskAssessor.assessRisk(maxAmountAfterMidnightApplication);

        assertThat(assessment, not(nullValue()));
        assertThat(assessment.getStatus(), equalTo(RiskStatus.APPLIED_AFTER_MIDNIGHT));
    }
}