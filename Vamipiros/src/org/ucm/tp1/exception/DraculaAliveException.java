package org.ucm.tp1.exception;

public class DraculaAliveException extends CommandExecuteException{
	String message="There cannot be two Draculas on board";
	public DraculaAliveException(){
		super();
	}
	public DraculaAliveException(String msg) {
		
		super(msg);
	}
	public DraculaAliveException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
	public String getMessage() {
		
		return message;
	}
}
