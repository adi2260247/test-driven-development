package org.self.learn.controller;

import static java.lang.Float.parseFloat;

import java.util.Arrays;
import java.util.List;

import org.self.learn.service.CalcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CalcWebAppController {
	private static final String DIVISION = "division";
	
	@Autowired
	private CalcService service;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView buildHomeView() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/calculate", method=RequestMethod.POST)
	public ModelAndView calculate(@RequestParam(name="first_operand") String firstOperand, 
			@RequestParam(name="second_operand") String secondOperand, 
			@RequestParam String operation) {
		
		String error = null;
		boolean isError = false;
		
		if(StringUtils.isEmpty(firstOperand) || StringUtils.isEmpty(secondOperand)
				|| StringUtils.isEmpty(operation)) {
			
			error = "Missing Input.";
			isError = true;
		} else if(!instanceOfFloat(firstOperand) || !instanceOfFloat(secondOperand)
				|| !validOperation(operation)) {
			
			error = "Invalid Input.";
			isError = true;
		} else if(operation.equals(DIVISION) && parseFloat(secondOperand)==0.0f) {
			error = "Denominator cannot be zero.";
			isError = true;
		}
		
		return isError? new ModelAndView("result")
								.addObject("output", error)
								.addObject("isError", isError):
						new ModelAndView("result")
								.addObject("output", this.service
								.calculate(firstOperand, secondOperand, operation));
	}

	private boolean validOperation(String operation) {
		final List<String> validOperations = Arrays.asList("addition", "subtraction", "multiplication", "division");
		return validOperations.contains(operation);
	}

	private boolean instanceOfFloat(String secondOperand) {
		
		try {
			parseFloat(secondOperand);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
}