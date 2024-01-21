package org.ucm.tp1.control.commands;
import org.ucm.tp1.logic.Game;
import org.ucm.tp1.exception.*;
public class CommandGenerator {
	private static Command[] availableCommands = {
			new AddCommand(),
			new HelpCommand(),
			new ResetCommand(),
			new ExitCommand(),
			new UpdateCommand(),
			new GarlicPushCommand(),
			new LightFlashCommand(),
			new SuperCoinsCommand(),
			new AddBloodBankCommand(),
			new AddVampireCommand(),
			new SerializeCommand(),
			new SaveCommand()
	
			};
	public static Command parse(String[] commandWords) throws CommandParseException{
		for (int i = 0; i < 12;i++) {
			if(availableCommands[i].parse(commandWords) == availableCommands[i]) {
				return availableCommands[i];
			}
			
			
		}
		throw new CommandParseException("[ERROR]:Unknown Command");
	
	
	}
	public static String commandHelp() {
		StringBuilder HelpMsg = new StringBuilder();
		for (int i = 0; i < 10;i++) {
			HelpMsg.append(availableCommands[i].helpText());
		}
		return HelpMsg.toString();
	}
	

}
