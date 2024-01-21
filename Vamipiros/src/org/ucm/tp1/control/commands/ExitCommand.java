package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;
public class ExitCommand extends Command {

	public ExitCommand() {
		super("exit", "e", "[e]xit","exit game");

	}

	@Override
	public boolean execute(Game game) {
		game.exit();
	
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}

}
