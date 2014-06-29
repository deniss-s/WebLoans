package lv.iljakorneckis.webloans.component.converter;

import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanExtension;
import lv.iljakorneckis.webloans.domain.dto.LoanExtensionResponse;
import lv.iljakorneckis.webloans.domain.dto.LoanResponse;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.co.it.modular.hamcrest.date.DateMatchers.sameDay;

public class LoanToLoanResponseConverterTest {

    private LoanToLoanResponseConverter converter = new LoanToLoanResponseConverter();

    @Test
    public void testConversion() {

        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn(1L);
        when(loan.getAmount()).thenReturn(Money.of(CurrencyUnit.EUR, new BigDecimal("100.00")));
        when(loan.getApplicationDate()).thenReturn(DateTime.now().withDate(2013, 04, 03));
        when(loan.getEndDate()).thenReturn(DateTime.now().withDate(2013, 05, 03));
        when(loan.getInterest()).thenReturn(new BigDecimal("1.50"));
        when(loan.getUserId()).thenReturn("USER_ID");

        LoanExtension extension = mock(LoanExtension.class);
        when(extension.getId()).thenReturn(1L);
        when(extension.getExtensionDate()).thenReturn(DateTime.now().withDate(2013, 04, 15));

        when(loan.getExtensionHistory()).thenReturn(Arrays.asList(extension));

        List<Loan> loans = Arrays.asList(loan);

        List<LoanResponse> responseList = converter.convert(loans);

        assertThat(responseList, hasSize(1));

        for(LoanResponse response : responseList) {
            assertThat(response.getAmount(), equalTo(loan.getAmount().getAmount()));
            assertThat(response.getCurrency(), equalTo(loan.getAmount().getCurrencyUnit().getCurrencyCode()));
            assertThat(response.getInterest(), equalTo(loan.getInterest()));
            assertThat(response.getApplicationDate(), sameDay(loan.getApplicationDate().toDate()));
            assertThat(response.getEndDate(), sameDay(loan.getEndDate().toDate()));
            assertThat(response.getUserId(), equalTo(loan.getUserId()));
            assertThat(response.getId(), equalTo(loan.getId()));
        }

        assertThat(responseList.get(0).getExtensionHistory(), hasSize(1));

        LoanExtensionResponse extensionResponse = responseList.get(0).getExtensionHistory().get(0);

        assertThat(extensionResponse.getId(), equalTo(extension.getId()));
        assertThat(extensionResponse.getDate(), sameDay(extension.getExtensionDate().toDate()));
    }
}