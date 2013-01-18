package de.unifrankfurt.informatik.dbis.dia.util;

public class StringUtil {
	
	private StringUtil() {}
	
	private static final String GET = "get";
	private static final String SET = "set";  
	
	public static String createGetter(String name) {
		return createAccessMethodName(GET, name);
	}

	public static String createSetter(String name) {
		return createAccessMethodName(SET, name);
	}
	
	private static String createAccessMethodName(String prefix, String name) {
		return prefix + 
				name.substring(0, 1).toUpperCase() +
				name.substring(1);
	}
	
}
