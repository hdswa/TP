package org.ucm.tp1.control.commands;

import org.ucm.tp1.exception.CommandExecuteException;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.exception.GameException;
import org.ucm.tp1.exception.NotEnoughCoinsException;
import org.ucm.tp1.logic.Game;

public class GarlicPushCommand extends Command {

	
	public GarlicPushCommand() {
		super("garlic", "g","[g]arlic","push all the vampires one position backwards");
	}
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean did=false;
		try{
		did=game.garlicPushConditions();
		
		if(did) {
		game.update();
		}
		}
		catch(NotEnoughCoinsException ex) {
			
			throw new CommandExecuteException(ex.getMessage());
		}
		return did;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}
	
}
