package org.ucm.tp1.control;

import java.util.Scanner;
import org.ucm.tp1.logic.Game;
import org.ucm.tp1.control.commands.Command;
import org.ucm.tp1.control.commands.CommandGenerator;
import org.ucm.tp1.exception.*;

public class Controller {
	public final String prompt = "Command > ";
	public static final String helpMsg = String.format(
			"Available commands:%n" +
			"[a]dd <x> <y>: add a slayer in position x, y%n" +
			"[h]elp: show this help%n" + 
			"[r]eset: reset game%n" + 
			"[e]xit: exit game%n"+ 
			"[n]one | []: update%n"+
			"[g]arlic : pushes back vampires%n"+
			"[l]ight : kills all the vampires%n"+
			"[b]ank <x> <y> <z>: add a blood bank with cost z in position x, y.%n"+
			"[c]oins: add 1000 coins%n"+
			"[v]ampire <type> <x> <y>\",\"add a <type>vampire in position x, y");
	
	public static final String unknownCommandMsg = String.format("  Unknown command");
	public static final String invalidCommandMsg = String.format("  Invalid command");
	public static final String invalidPositionMsg = String.format("  Invalid position");

    private Game game;
    private Scanner scanner;
    
    public Controller(Game game, Scanner scanner) {
	    this.game = game;
	    this.scanner = scanner;
    }
    
    public void  printGame() {
   	 System.out.println(game);
   }
    
    public void run()  {
   
    	boolean refreshDisplay = true;

	    while (!game.isFinished()){
	    		
        	  if (refreshDisplay) printGame();
        	  refreshDisplay = false;
        	  System.out.println(System.getProperty("line.separator")+prompt);	
			  String s = scanner.nextLine();
			  String[] parameters = s.toLowerCase().trim().split(" ");
			  System.out.println("[DEBUG] Executing: " + s);
			  try {
				  Command command = CommandGenerator.parse(parameters);
				
				  if (command != null)
				  refreshDisplay = command.execute(game);
				  else System.out.println("[ERROR]:" + unknownCommandMsg);
				  }
				  catch(GameException ex) {
				  System.out.format(ex.getMessage());
				  }
				  
		
	    
        	if (refreshDisplay) printGame();
        
    		
        	
			  }
	    System.out.println ("[Game over] " + game.getWinnerMessage());
	    }
    }