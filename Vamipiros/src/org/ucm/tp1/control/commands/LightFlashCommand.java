package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandExecuteException;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.exception.NotEnoughCoinsException;
import org.ucm.tp1.logic.Game;
public class LightFlashCommand extends Command {

	public LightFlashCommand() {
		//name , shortcut, details, help
		super("light", "l", "[l]ight", "kills all the vampires except Dracula");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean did=false;
		try{
		did=game.lightFlashConditions();
		if(did) {
		game.removeDead();
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
		
		if (commandWords[0].contentEquals("")) {//otro posible caso de updatea
			commandWords[0] = "n";
		}
		return this.parseNoParamsCommand(commandWords);
	}

	
}
