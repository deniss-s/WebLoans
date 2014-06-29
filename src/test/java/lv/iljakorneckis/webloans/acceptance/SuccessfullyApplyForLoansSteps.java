package lv.iljakorneckis.webloans.acceptance;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lv.iljakorneckis.webloans.Launcher;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

public class SuccessfullyApplyForLoansSteps {

    private BigDecimal amount;
    private Integer days;

    @Given("^user wants a loan of EUR (\\d+) for (\\d+) days$")
    public void user_wants_loan(BigDecimal amount, Integer days) {
        this.amount = amount;
        this.days = days;
    }

    @When("^user applies for a loan with status (\\d+)$")
    public void user_applies_for_a_loan(Integer expectedStatus) {
        given()
            .param("amount", amount)
            .param("term", days)
        .when()
            .post("/rest/loans")
        .then()
            .assertThat().statusCode(expectedStatus);
    }

    @Then("^user has a new loan in his loan history$")
    public void user_has_new_loan_in_history(){
        when()
            .get("/rest/loans")
        .then()
            .body("[0]", not(nullValue()));
    }

}
