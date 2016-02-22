package org.self.learn.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.self.learn.SimpleCalculatorWebApplication;
import org.self.learn.controller.CalcWebAppController;
import org.self.learn.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(SimpleCalculatorWebApplication.class)
@WebAppConfiguration
public class CalcWebAppControllerTests {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Mock
	private CalcService service;
	@InjectMocks
	private CalcWebAppController controller;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void itShouldReturnHomeView() throws Exception {
		this.mockMvc.perform(get("/"))
		.andExpect(view().name("home"));
	}

	@Test
	public void itShouldReturnResultView() throws Exception {
		MockHttpServletRequestBuilder request = post("/calculate");
		request.param("first_operand", "12.23");
		request.param("second_operand", "54.01");
		request.param("operation", "addition");
		this.mockMvc.perform(request)
		.andExpect(view().name("result"));
	}

	@Test
	public void itShouldReturnComputationResultByCallingCalcService() throws Exception {
		when(this.service.calculate(any(String.class), any(String.class), any(String.class)))
		.thenReturn(new Float(66.24));
		assertThat(this.controller.calculate("12.23", "54.01", "addition")
				.getModel().get("output"), is(equalTo(66.24F)));
		verify(this.service, atLeastOnce()).calculate("12.23", "54.01", "addition");
	}

	@Test
	public void itShouldReturnErrorMessageAndFlagForMissingInput() throws Exception {
		when(this.service.calculate(any(String.class), any(String.class), any(String.class)))
				.thenReturn(10F);
		assertThat(this.controller.calculate("", "54.01", "addition")
				.getModel().get("output"), is(equalTo("Missing Input.")));
		assertThat(this.controller.calculate("", "54.01", "addition")
				.getModel().get("isError"), is(equalTo(true)));
		assertThat(this.controller.calculate("12.23", "", "addition")
				.getModel().get("output"), is(equalTo("Missing Input.")));
		assertThat(this.controller.calculate("12.23", "", "addition")
				.getModel().get("isError"), is(equalTo(true)));
		assertThat(this.controller.calculate("12.23", "54.01", "")
				.getModel().get("output"), is(equalTo("Missing Input.")));
		assertThat(this.controller.calculate("12.23", "54.01", "")
				.getModel().get("isError"), is(equalTo(true)));
		verify(this.service, never())
				.calculate(any(String.class), any(String.class), any(String.class));
	}


	@Test
	public void itShouldReturnErrorMessageAndFlagForInvalidInput() throws Exception {
		when(this.service.calculate(any(String.class), any(String.class), any(String.class)))
				.thenReturn(10F);
		assertThat(this.controller.calculate("12@$rd", "54.01", "addition")
				.getModel().get("output"), is(equalTo("Invalid Input.")));
		assertThat(this.controller.calculate("12@$rd", "54.01", "addition")
				.getModel().get("isError"), is(equalTo(true)));
		assertThat(this.controller.calculate("12.23", "123.34.45", "addition")
				.getModel().get("output"), is(equalTo("Invalid Input.")));
		assertThat(this.controller.calculate("12.23", "123.34.45", "addition")
				.getModel().get("isError"), is(equalTo(true)));
		assertThat(this.controller.calculate("12.23", "54.01", "add")
				.getModel().get("output"), is(equalTo("Invalid Input.")));
		assertThat(this.controller.calculate("12.23", "54.01", "add")
				.getModel().get("isError"), is(equalTo(true)));
		verify(this.service, never())
				.calculate(any(String.class), any(String.class), any(String.class));
	}
	
	@Test
	public void itShouldReturnErrorMessageAndFlagFrorZeroDenominator() throws Exception {
		when(this.service.calculate(any(String.class), any(String.class), any(String.class)))
			.thenReturn(10F);
		assertThat(this.controller.calculate("12.23", "0", "division")
				.getModel().get("output"), is(equalTo("Denominator cannot be zero.")));
		assertThat(this.controller.calculate("12.23", "0", "division")
				.getModel().get("isError"), is(equalTo(true)));
		verify(this.service, never())
			.calculate(any(String.class), any(String.class), any(String.class));
	}
}
