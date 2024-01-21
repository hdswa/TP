package org.ucm.tp1.control.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.ucm.tp1.exception.CommandExecuteException;
import org.ucm.tp1.exception.CommandParseException;
import org.ucm.tp1.logic.Game;

public class SaveCommand extends Command {
	protected String fileName;

	public SaveCommand() {
		super("save", "s", "[s]ave <fileName> : ", "Save the state of the game to a file.");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try( BufferedWriter outStream = new BufferedWriter(new FileWriter(fileName))) {
			
				outStream.write("Buffy the Vampire Slayer v3.0");
				
				outStream.newLine();
				game.store(outStream);
				System.out.println("[DEBUG] Game succesfully saved in file "+fileName);
				} catch (IOException ioe) {
					throw new CommandExecuteException(ioe.getMessage(),ioe);
				}
		return false;
	}
	
	@Override
	public Command parse(String[] commandWords) throws CommandParseException {
		if (this.matchCommandName(commandWords[0])) {
			if (commandWords.length == 2) {
				fileName = commandWords[1] + ".dat";
				return this;
			}
			else if (commandWords.length == 1) {
				throw new CommandParseException("[ERROR] " + "Save must be followed by a name");
			}
			else {
				throw new CommandParseException("[ERROR] " + "Unvalid filename: the filename contains spaces");
			}
		}
		return null;
	}

}

