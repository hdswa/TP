package org.ucm.tp1.exception;

public class GameException extends Exception{
	
	public GameException() {
		super();
	}
	public GameException(String msg) {
		
		super(msg);
	}
	public GameException(String msg,Throwable cause) {
		
		super(msg,cause);
	}
	
}
