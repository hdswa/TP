package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;
public class UpdateCommand extends Command {

	public UpdateCommand() {
		//name , shortcut, details, help
		super("", "n", "[n]one | []", "update");
	}

	@Override
	public boolean execute(Game game) {
		game.update();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		
		if (commandWords[0].contentEquals("")) {//otro posible caso de updatea
			commandWords[0] = "n";
		}
		return this.parseNoParamsCommand(commandWords);
	}

	
}
