package com.cargotracker.service.dto;

import java.util.ArrayList;
import java.util.List;

public class ExcelResponse {

	Boolean isError;

	List<String> errorList = new ArrayList<String>();

	public ExcelResponse() {
		
	}
	public ExcelResponse(Boolean isError, List<String> errorList) {
		this.isError = isError;
		this.errorList = errorList;
	}

	public Boolean getIsError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

}
