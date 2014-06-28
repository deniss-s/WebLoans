package lv.iljakorneckis.webloans.integration;

import lv.iljakorneckis.webloans.Launcher;
import lv.iljakorneckis.webloans.component.LoanRiskAssessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Launcher.class)
@Transactional
public class LoanRiskAssessorIntegrationTest {

    @Autowired
    private LoanRiskAssessor assessor;

    @Test
    public void testAssessRisk() {

    }
}
