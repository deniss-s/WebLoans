package lv.iljakorneckis.webloans.component.converter;

import lv.iljakorneckis.webloans.domain.Loan;
import lv.iljakorneckis.webloans.domain.LoanExtension;
import lv.iljakorneckis.webloans.domain.dto.LoanExtensionResponse;
import lv.iljakorneckis.webloans.domain.dto.LoanResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoanToLoanResponseConverter {

    public List<LoanResponse> convert(List<Loan> loans) {
        List<LoanResponse> list = new ArrayList<LoanResponse>();

        for(Loan loan : loans) {
            LoanResponse response = loanToResponse(loan);

            List<LoanExtensionResponse> responseList = new ArrayList<LoanExtensionResponse>();
            for(LoanExtension extension : loan.getExtensionHistory()) {
                responseList.add(extensionToResponse(extension));
            }

            response.setExtensionHistory(responseList);
            list.add(response);
        }

        return list;
    }

    private LoanResponse loanToResponse(Loan loan) {
        LoanResponse response = new LoanResponse();

        response.setId(loan.getId());
        response.setUserId(loan.getUserId());
        response.setAmount(loan.getAmount().getAmount());
        response.setCurrency(loan.getAmount().getCurrencyUnit().getCurrencyCode());
        response.setInterest(loan.getInterest());
        response.setApplicationDate(loan.getApplicationDate().toDate());
        response.setEndDate(loan.getEndDate().toDate());

        return response;
    }

    private LoanExtensionResponse extensionToResponse(LoanExtension extension) {
        LoanExtensionResponse response = new LoanExtensionResponse();

        response.setId(extension.getId());
        response.setDate(extension.getExtensionDate().toDate());

        return response;
    }
}
