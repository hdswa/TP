package org.ucm.tp1.control.commands;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;
public class SuperCoinsCommand extends Command {

	public SuperCoinsCommand() {
		super("coins","c","[c]oins", "gives the player 1000 coins");
	}

	@Override
	public boolean execute(Game game) {
		game.superCoins();
		
		return true;
	}

	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		return this.parseNoParamsCommand(commandWords);
	}

}
