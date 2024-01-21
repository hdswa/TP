package org.ucm.tp1.control.commands;

import org.ucm.tp1.exception.CommandExecuteException;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;

public class SerializeCommand extends Command {

	public SerializeCommand() {
		super("serialize","z", "seriali[z]e: ", "Serializes the board.");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		System.out.println(game.serialize());
		return false;
	}
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}

	
	
}
