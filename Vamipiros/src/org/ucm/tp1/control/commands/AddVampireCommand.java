package org.ucm.tp1.control.commands;
import org.ucm.tp1.logic.Game;
import org.ucm.tp1.exception.*;

public class AddVampireCommand extends Command {
	private int x,y;
	private String tipo;
	private String tipos[]= {"v","e","d"};
	public AddVampireCommand() {
		super("vampire","v","[v]ampire <type> <x> <y>","add a <type>vampire in position x, y");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean added=false;
			try{
				game.addVampireConditions(tipo, x, y);
				added=true;
			}catch(UnvalidPositionException ex) {
				throw new CommandExecuteException(ex.getMessage());
			}
			catch(NoMoreVampiresException es) {
				throw new CommandExecuteException(es.getMessage());
				
			}
			catch(DraculaAliveException ea) {
				throw new CommandExecuteException(ea.getMessage());
			}
		

		return added;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if(this.matchCommandName(commandWords[0])) {
		if(commandWords.length==3) {
			if(isStringInt(commandWords[1])&&isStringInt(commandWords[2])) {
				x = Integer.parseInt(commandWords[1]);
				y = Integer.parseInt(commandWords[2]);
				tipo="v";
				return this;
			}
			else {
				
				throw new CommandParseException("[ERROR]:Incorrect type of arguments");
			}
			
		}
		else if(commandWords.length==4) {
			if(isStringInt(commandWords[2])&&isStringInt(commandWords[3])) {
				x = Integer.parseInt(commandWords[2]);
				y = Integer.parseInt(commandWords[3]);
					for(int i=0;i<tipos.length;i++) {
						if(commandWords[1].contentEquals(tipos[i])) {
							
							tipo=commandWords[1];
							return this;
						}
						
					}
					
					throw new CommandParseException("[ERROR]:Incorrect type of Vampire");
			
					
			}	
			throw new CommandParseException("[ERROR]:Incorrect type of argument");
		}
		else {
			throw new CommandParseException("[ERROR]:Incorrect number of arguments");
		}
		}
		
		return null;
		
		
	
		
	}
	public boolean isStringInt(String s){//funcion de comprobacion
		boolean is=false;
	    try  {
	        Integer.parseInt(s);
	        is=true;
	    } 
	    catch (NumberFormatException ex){
	        is=false;
	
	  
	    }
	    return is;
	}
}
