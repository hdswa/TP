package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;
public class ResetCommand extends Command {

	public ResetCommand() {
		super("reset","r","[r]eset", "reset game");
	}

	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}

}
