package lv.iljakorneckis.webloans.rest;

import lv.iljakorneckis.webloans.component.producer.DateTimeProducer;
import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanApplication;
import lv.iljakorneckis.webloans.domain.LoanExtension;
import lv.iljakorneckis.webloans.exceptions.RiskAssessmentException;
import lv.iljakorneckis.webloans.service.LoanService;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/loans")
public class LoanRestService {

    @Autowired
    private DateTimeProducer dateTimeProducer;

    @Autowired
    private LoanService loanService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Loan> getLoanHistory(HttpServletRequest request) {
        final String ip = request.getRemoteAddr();

        return loanService.getLoanHistory(ip);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Loan applyForLoan(@RequestParam("amount") BigDecimal amount,
                             @RequestParam("term") Integer term,
                             HttpServletRequest request) throws RiskAssessmentException {

        final String ip = request.getRemoteAddr();

        LoanApplication application = new LoanApplication();
        application.setAmount(Money.of(CurrencyUnit.EUR, amount));
        application.setApplicationDate(dateTimeProducer.getCurrentDateTime());
        application.setTerm(term);
        application.setUserId(ip);

        return loanService.applyForLoan(application);
    }

    @RequestMapping(value="/{loanId}/extend", method = RequestMethod.POST)
    public Loan extendLoan(@PathVariable("loanId") Long loanId,
                           HttpServletRequest request) {
        final String ip = request.getRemoteAddr();

        return loanService.extendLoan(loanId, ip);
    }

}