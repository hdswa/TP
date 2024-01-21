package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandExecuteException;
import org.ucm.tp1.exception.NotEnoughCoinsException;
import org.ucm.tp1.exception.UnvalidPositionException;
import org.ucm.tp1.logic.Game;
public class AddBloodBankCommand extends Command {
	private int x,y,z;
	
	public AddBloodBankCommand() {
		super("bank","b","[b]ank <x> <y> <z>","adds a blood bank with value <z> in pos x,y");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean added=false;
		try{
			added=game.addBloodBankCondition(x, y, z);//condiciones de add y hace el add directamente
			if(added) {
			game.update();
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
		if (commandWords.length==4 && this.matchCommandName(commandWords[0])) {
			
			
			if(isStringInt(commandWords[1])&&isStringInt(commandWords[2])) {
				if(isStringInt(commandWords[3])) {
					x = Integer.parseInt(commandWords[1]);
					y = Integer.parseInt(commandWords[2]);
					z = Integer.parseInt(commandWords[3]);
				return this;
				}
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
