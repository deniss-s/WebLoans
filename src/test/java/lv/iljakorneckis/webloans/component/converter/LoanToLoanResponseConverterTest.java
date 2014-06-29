package lv.iljakorneckis.webloans.component.converter;

import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanExtension;
import lv.iljakorneckis.webloans.domain.dto.LoanExtensionResponse;
import lv.iljakorneckis.webloans.domain.dto.LoanResponse;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    private Loan mockLoan;

    @Before
    public void init() {
        mockLoan = mock(Loan.class);
        when(mockLoan.getId()).thenReturn(1L);
        when(mockLoan.getAmount()).thenReturn(Money.of(CurrencyUnit.EUR, new BigDecimal("100.00")));
        when(mockLoan.getApplicationDate()).thenReturn(DateTime.now().withDate(2013, 04, 03));
        when(mockLoan.getEndDate()).thenReturn(DateTime.now().withDate(2013, 05, 03));
        when(mockLoan.getInterest()).thenReturn(new BigDecimal("1.50"));
        when(mockLoan.getUserId()).thenReturn("USER_ID");
    }

    @Test
    public void testConversion() {
        LoanExtension extension = mock(LoanExtension.class);
        when(extension.getId()).thenReturn(1L);
        when(extension.getExtensionDate()).thenReturn(DateTime.now().withDate(2013, 04, 15));

        when(mockLoan.getExtensionHistory()).thenReturn(Arrays.asList(extension));

        List<Loan> loans = Arrays.asList(mockLoan);

        List<LoanResponse> responseList = converter.convert(loans);

        assertThat(responseList, hasSize(1));

        for(LoanResponse response : responseList) {
            assertThat(response.getAmount(), equalTo(mockLoan.getAmount().getAmount()));
            assertThat(response.getCurrency(), equalTo(mockLoan.getAmount().getCurrencyUnit().getCurrencyCode()));
            assertThat(response.getInterest(), equalTo(mockLoan.getInterest()));
            assertThat(response.getApplicationDate(), sameDay(mockLoan.getApplicationDate().toDate()));
            assertThat(response.getEndDate(), sameDay(mockLoan.getEndDate().toDate()));
            assertThat(response.getUserId(), equalTo(mockLoan.getUserId()));
            assertThat(response.getId(), equalTo(mockLoan.getId()));
        }

        assertThat(responseList.get(0).getExtensionHistory(), hasSize(1));

        LoanExtensionResponse extensionResponse = responseList.get(0).getExtensionHistory().get(0);

        assertThat(extensionResponse.getId(), equalTo(extension.getId()));
        assertThat(extensionResponse.getDate(), sameDay(extension.getExtensionDate().toDate()));
    }

    @Test
    public void testEmptyListConversion() {
        List<Loan> empty = new ArrayList<Loan>();

        List<LoanResponse> emptyResponseList = converter.convert(empty);

        assertThat(emptyResponseList, hasSize(0));
    }

    @Test
    public void testEmptyExtensionListConversion() {
        List<Loan> list = Arrays.asList(mockLoan);

        List<LoanResponse> responseList = converter.convert(list);

        assertThat(responseList, hasSize(1));

        LoanResponse response = responseList.get(0);
        assertThat(response.getExtensionHistory(), hasSize(0));
    }
}