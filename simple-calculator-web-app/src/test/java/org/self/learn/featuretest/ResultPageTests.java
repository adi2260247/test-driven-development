package org.self.learn.featuretest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.self.learn.SimpleCalculatorWebApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.codestory.simplelenium.SeleniumTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SimpleCalculatorWebApplication.class)
@WebIntegrationTest(randomPort = true)
public class ResultPageTests extends SeleniumTest {
	
	@Value("${local.server.port}")
    int port;
	
	@Test
	public void itShouldCaculateResultWithValidInput() {
		goTo(getDefaultBaseUrl());
		enterInputAndClickCalculate("20", "20", "Addition (+)");
		verifyResutPageHeaderAndHomeLink();
		verifyDisplayedResult("40");
	}
	
	@Test
	public void itShouldDisplayMissingInputError() {
		goTo(getDefaultBaseUrl());
		enterInputAndClickCalculate("20", "", "Addition (+)");
		verifyResutPageHeaderAndHomeLink();
		verifyDisplayedResult("Missing Input");
	}
	
	@Test
	public void itShouldDisplayInvalidInputError() {
		goTo(getDefaultBaseUrl());
		enterInputAndClickCalculate("12.23.45", "12", "Addition (+)");
		verifyResutPageHeaderAndHomeLink();
		verifyDisplayedResult("Invalid Input");
	}
	
	@Test
	public void itShouldDisplayZeroDenominatorError() {
		goTo(getDefaultBaseUrl());
		enterInputAndClickCalculate("67.23", "0", "Division (/)");
		verifyResutPageHeaderAndHomeLink();
		verifyDisplayedResult("Denominator cannot be zero.");
	}
	
	private void verifyResutPageHeaderAndHomeLink() {
		find("h1#result-page-heder").withText("Calculator").should().beDisplayed();
		find("div#home_link_container").should()
			.find("a#home_link").withText("Home").should().exist();
	}
	
	private void enterInputAndClickCalculate(String firstOperand, String secondOperand, String operation) {
		find("input[name=first_operand]").fill(firstOperand);
		find("input[name=second_operand]").fill(secondOperand);
		find("select[name=operation]")
			.click()
			.select(operation);
		
		find("#calculate_button_container").should()
			.find("input[type=submit]").click();
	}
	
	private void verifyDisplayedResult(String check) {
		find("div#result >p").should()
			.not().beEmpty()
			.contain(check);
	}
	
	@Override
	protected String getDefaultBaseUrl() {
		final String BASE_URL = "http://localhost:"+port+"/"; 
		return BASE_URL;
	}
	
}