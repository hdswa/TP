package org.ucm.tp1.exception;

public class NoMoreVampiresException extends Exception {
	String message="There cannot be more Vampires on the board";
	public NoMoreVampiresException(){
		super();
	}
	public NoMoreVampiresException(String msg) {
		
		super(msg);
	}
	public NoMoreVampiresException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
	public String getMessage() {
		
		return message;
		
	}

}
