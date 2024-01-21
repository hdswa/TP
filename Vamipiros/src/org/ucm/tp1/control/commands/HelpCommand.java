package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		super("help", "h","[h]elp","show this help");
	}
	
	
	@Override
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}

}


