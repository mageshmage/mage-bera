package com.cargotracker.service.util;

import org.apache.poi.ss.usermodel.Cell;

public class HelperUtils {

	public static boolean isNull(String str) {
		return str == null ? true : false;
	}

	public static boolean isNullOrBlank(String param) {
		if (isNull(param) || param.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isExcelCellEmpty(Cell cell) {
		if (cell == null) {
			return true;
		}
		return false;
	}

}
