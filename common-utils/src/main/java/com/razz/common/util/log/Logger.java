package com.razz.common.util.log;

public class Logger {

	public static void debug(Class<?> clazz, String method, String format, Object... args) {
		debug( String.format("%s.%s: ", clazz.getSimpleName(), method).concat(format), args );
	}
	
	public static void debug(String format, Object... args) {
		debug( String.format(format, args) );
	}
	
	public static void debug(String message) {
		System.out.println(message);
	}
	
	public static void error(String message) {
		error(message);
	}
	
	public static void error(String message, Exception e) {
		if(e != null)
			e.printStackTrace();
		System.err.println(message);
	}
	
}
