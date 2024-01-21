package org.ucm.tp1.control.commands;
import org.ucm.tp1.logic.Game;
import org.ucm.tp1.exception.*;
public class AddCommand extends Command {
	private int x,y;
	
	public AddCommand() {
		super("add","a","[a]dd <x> <y>","add a slayer in position x, y");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean added=false;
		try{
			added=game.addSlayerCondition(x, y);//condiciones de add y hace el add directamente
			if(added) {
			game.update();//actualiza el juego para el print que vendra detras
			}
		}
		catch(NotEnoughCoinsException ex) {
			throw new CommandExecuteException(ex.getMessage());
		}
		catch(UnvalidPositionException es) {
			
			throw new CommandExecuteException(es.getMessage());
		}
		return added;
	}

	@Override
	public Command parse(String[] commandWords) {
		if (commandWords.length==3 && this.matchCommandName(commandWords[0])) {
			//comprobar que los dos elementos x y son de tipo INT
			
			if(isStringInt(commandWords[1])&&isStringInt(commandWords[2])) {
			x = Integer.parseInt(commandWords[1]);
			y = Integer.parseInt(commandWords[2]);
				return this;
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
