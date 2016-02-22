package org.self.learn.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class CalcServiceTest {
	private CalcService service;
	
	@Before
	public void setUp() {
		this.service = new CalcService();
	}
	
	@Test
	public void itShouldAddTheNumbers() throws Exception {
		assertThat(this.service.calculate("12.34", "54.01", "addition"),
				is(equalTo(66.35F)));
	}
	
	@Test
	public void itShouldSubtractTheNumbers() throws Exception {
		assertThat(this.service.calculate("40", "20", "subtraction"),
				is(equalTo(20F)));
	}
	
	@Test
	public void itShouldMultiplyTheNumbers() throws Exception {
		assertThat(this.service.calculate("2", "3.5", "multiplication"),
				is(equalTo(7F)));
	}
	
	@Test
	public void itShouldDivideTheNumbers() throws Exception {
		assertThat(this.service.calculate("20", "10", "division"),
				is(equalTo(2F)));
	}
}
