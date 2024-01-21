package org.ucm.tp1.exception;

public class UnvalidPositionException extends CommandExecuteException {
	String message="";
	public	UnvalidPositionException(){
		super();
	}
	public UnvalidPositionException(String msg) {
		
		super(msg);
		message=msg;
	}
	public UnvalidPositionException(String msg,Throwable cause) {
		
		super(msg,cause);
		
	}
	public String getMessage() {
		
		
		return message;
	}
	
}
