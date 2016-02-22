package org.self.learn.featuretest;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
public class HomePageTests extends SeleniumTest {
	
	@Value("${local.server.port}")
    int port;
	
	@Test
	public void itShouldDisplayHomePage() {
		goTo(getDefaultBaseUrl());
		
		assertThat(title(), is(equalTo("Home")));
		find("h1#home-page-heder").should().contain("Calculator");
	}
	
	@Test
	public void itShouldDisplayFormWithOperand1AndOperand2AndOperationAndCalculateButton() {
		goTo(getDefaultBaseUrl());
		
		find("form").should()
			.find("#operand1_container").should().exist()
			.find("#operand2_container").should().exist()
			.find("#operation_container").should().exist()
			.find("#calculate_button_container").should().exist();
	}
	
	@Test
	public void itShouldDisplayOperand1LabelAndInputOfTypeText() {
		goTo(getDefaultBaseUrl());
		
		//CSS selectors style
		find("div#operand1_container > label#operand1_label").withText("Operand 1:").should().beDisplayed();
		find("div#operand1_container > input[type=text]").should().exist();
	}
	
	@Test
	public void itShouldDisplayOperand2LabelAndInputOfTypeText() {
		goTo(getDefaultBaseUrl());
		
		//Object Style
		find("div#operand2_container").should()
			.find("label#operand2_label").withText("Operand 2:").should().beDisplayed()
			.find("input[type=text]").should().exist();
	}
	
	@Test
	public void itShouldDisplayAppropriateDropdownOptions() {
		goTo(getDefaultBaseUrl());
		
		find("#operation_container").should()
			.find("#operation_label").should().contain("Operation:")
			.find("#operation").should()
				.find("option[value='']").withText("Specify operation").should().exist()
				.find("option[value=addition]").withText("Addition (+)").should().exist()
				.find("option[value=subtraction]").withText("Subtraction (-)").should().exist()
				.find("option[value=multiplication]").withText("Multiplication (*)").should().exist()
				.find("option[value=division]").withText("Division (/)").should().exist();
	}
	
	@Test
	public void itShouldDisplayCalculateButtonInputOfTypeSubmit() {
		goTo(getDefaultBaseUrl());
		
		find("#calculate_button_container").should()
			.find("input[type=submit][value=Calculate]").should().exist();
	}
	
	@Test
	public void itShouldTransitionAwayFromHomePageUponClickingCalculateButton() {
		goTo(getDefaultBaseUrl());
		
		find("input[name=first_operand]").fill("12.23");
		find("input[name=second_operand]").fill("54.01");
		find("select[name=operation]")
			.click()
			.select("Addition (+)");
		find("#calculate_button_container").should()
			.find("input[type=submit]").click();
		assertThat(title(), is(not(equalTo("Home"))));
	}
	
	@Override
	protected String getDefaultBaseUrl() {
		final String BASE_URL = "http://localhost:"+port+"/"; 
		return BASE_URL;
	}
	
}
