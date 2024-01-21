package org.ucm.tp1.exception;

public class NotEnoughCoinsException extends Exception {
	String message="";
	public NotEnoughCoinsException(){
		super();
	}
	public NotEnoughCoinsException(String msg) {
		
		super(msg);
		message=msg+message;
	}
	public NotEnoughCoinsException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
	public String getMessage() {
		
	return message;	
	}
	}

