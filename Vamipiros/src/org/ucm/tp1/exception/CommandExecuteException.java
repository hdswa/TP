package org.ucm.tp1.exception;

public class CommandExecuteException extends GameException{
	
	public CommandExecuteException() {
		super();
	}
	public CommandExecuteException(String msg) {
		
		super(msg);
	}
	public CommandExecuteException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
}
