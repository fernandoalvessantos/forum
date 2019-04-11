package br.com.alura.forum.validator.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationsErrorsOutputDto {
	
	private List<String> globalErrorMessages = new ArrayList<String>();
	private List<FieldErrorOutputDto> fieldErros = new ArrayList<FieldErrorOutputDto>();
	
	public void addError(String message) {
		globalErrorMessages.add(message);
	}
	
	public void addFieldError(String field, String message) {
		FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
		fieldErros.add(fieldError);
	}
	
	public int getNumberOfErrors() {
		return this.globalErrorMessages.size() + this.fieldErros.size();
	}

	public List<String> getGlobalErrorMessages() {
		return globalErrorMessages;
	}

	public List<FieldErrorOutputDto> getErros() {
		return fieldErros;
	}

	
}
