package edu.ccrm.exception;

public class MaxCreditLimitExceededException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public MaxCreditLimitExceededException(String message) {
		super(message);
	}
}