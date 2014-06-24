package lv.iljakorneckis.webloans.exceptions;

import lv.iljakorneckis.webloans.enums.RiskStatus;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RiskAssessmentExceptionTest {

    @Test
    public void testRiskAssessmentException(){
        try {
            throw new RiskAssessmentException(RiskStatus.APPLIED_AFTER_MIDNIGHT, "TEST MESSAGE");
        } catch (RiskAssessmentException e) {
            assertThat(e.getStatus(), equalTo(RiskStatus.APPLIED_AFTER_MIDNIGHT));
        }
    }
}
