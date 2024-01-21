package org.ucm.tp1.exception;

public class CommandParseException extends GameException{
	String message="[ERROR]:Incorrect Number of arguments";
	public CommandParseException() {
		super();
	}
	public CommandParseException(String msg) {
		
		super(msg);
		message=msg;
	}
	public CommandParseException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
	public String getMessage() {
		
		return message;
	}
}
