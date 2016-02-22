package org.self.learn.service;

import static java.lang.Float.parseFloat;

import org.springframework.stereotype.Service;

@Service
public class CalcService {
	private static final String ADDITION = "addition";
	private static final String SUBTRACTION = "subtraction";
	private static final String MULTIPLICATION = "multiplication";
	private static final String DIVISION = "division";
	
	public Float calculate(String firstOperand, String secondOperand, String operation) {
		Float result = null;
		
		switch(operation) {
		case ADDITION:
			result = parseFloat(firstOperand)+parseFloat(secondOperand);
			break;
		case SUBTRACTION:
			result = parseFloat(firstOperand)-parseFloat(secondOperand);
			break;
		case MULTIPLICATION:
			result = parseFloat(firstOperand)*parseFloat(secondOperand);
			break;
		case DIVISION:
			result = parseFloat(firstOperand)/parseFloat(secondOperand);
			break;
		default:
			break;
		}
			
		return result;
	}
	
}