package org.mifosplatform.dataimport.services.utils;

public class StringUtils {
	
	public static final boolean isBlank(String input) {
		return input == null || input.trim().equals("");
	}

}
