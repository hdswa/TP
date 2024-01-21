package org.ucm.tp1.logic;



import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Random;
import org.ucm.tp1.exception.*;

import org.ucm.tp1.view.GamePrinter;
import org.ucm.tp1.view.IPrintable;
import org.ucm.tp1.lists.GameObjectList;
import org.ucm.tp1.logic.gameObject.Dracula;
import org.ucm.tp1.logic.gameObject.ExplosiveVampire;
import org.ucm.tp1.logic.gameObject.GameObject;
import org.ucm.tp1.logic.gameObject.IAttack;
import org.ucm.tp1.logic.gameObject.Player;
import org.ucm.tp1.logic.gameObject.Slayer;
import org.ucm.tp1.logic.gameObject.Vampire;
public class Game implements IPrintable {
	
	private GamePrinter printer;
	private Player player;
	private Gob gob;
	private int tamx,tamy;
	private int vTotal;
	private double vFreq;
	private Random random;
	private int cicle;
	private Level level;
	private boolean exit=false;
	private long localseed;
	public Game(long seed, Level diff) {//inicializacion,construccion...
		System.out.println("Dificultad:"+diff);
		random=new Random(seed);//seed dentro para no random
		localseed=seed;
		printer=new GamePrinter((IPrintable)this,diff.getx(),diff.gety());
		this.player=new Player(random);
		level=diff;
		
		gob=new Gob(new GameObjectList(this));//gameobjectlist
		tamy=diff.gety();//dar valores para no tener que estar haciendo get()todo el rato
		tamx=diff.getx();
		vTotal=diff.getVTotal();
		vFreq=diff.getVFrecuency();
		
		//.....
		Vampire.setRandom(random);
		initialize();
		
	}
	public void initialize() {//inicializa los valores static
		cicle=0;
		Slayer.setRange(tamx);
		
		Vampire.setRemainingVampires(vTotal);
		Vampire.resetVampiresOnBoard();
		
	}
	//--------------GET's
	public Level getLevel() {
		return level;
		
	}
	
	public double getVFreq() {
		
		return vFreq;
	}
	public boolean getCoins() {//comprueba si tiene mas de 50 monedas para el add
		boolean tiene=false;
		if(player.getCoins()>=50) {
			tiene=true;
			
		}
		return tiene;
		
	}
	@Override
	public String getPositionToString(int x, int y) {// v [5]
		String a=" ";
		if(gob.getObjectInPosition(x, y)!=null) {
		a = gob.getObjectInPosition(x, y).toString();
	}
		return a;
	}
	@Override
	public String getInfo() {// \n salto de linea
		
		String a = "Number of cycles: " + cicle + "\n" + "Coins: " + player.getCoins() + "\n" +
				  	"Remaining Vampires: " + Vampire.getRemainingVampires()+ "\n" +
				  	"Vampires on Board: " + Vampire.getVampiresOnBoard()+ "\n" +
				  	Vampire.draculaAliveMessage();
					
	return a;
	
	}
	public int getTamx() {
		return tamx;
	}

	public int getVTotal() {

		return vTotal;
	}
	

	public int getTamy() {

		return tamy;
	}

	public Random getRandom() {

	return random;
	}
	//----------------------------------------------
		
		//------------------------transiciones a otras clases
		public void addVampire(int x,int y) {
		
			gob.addVampire(x, y);
		}
		public void addSlayer(int x,int y) {
		
			gob.addSlayer(x,y);
		}
		
		public void addExplosiveVampire(int x,int y) {
			
			gob.addExplosiveVampire(x,y);
		}
		public void addDracula(int x,int y) {
			
			gob.addDracula(x,y);
		}
		
		
		public void superCoins() {
			
			player.removeCoins(-1000);//player coins -(-1000)=+1000
		}
		public void bloodBankCoins(int z) {
			player.removeCoins(-1*z);
			
		}
		
		
		//-----------------------------------------funciones
		public void Coins() {//funcion que lleva las monedas
			
			player.Coins();//predefinido por la semilla
			System.out.println("Coins:"+player.getCoins());
			
		}
		
		public String toString() {
			
			return printer.toString();
			
		}
			public boolean isPositionEmpty(int x,int y) {
				
				return gob.isPositionEmpty(x, y);
				
			}
			public GameObject getObjectInPosition(int x,int y) {//devuelve el objeto en xy
			
				return gob.getObjectInPosition(x, y);
			
			}
		
			public void update() {//.........................................--------------------
				player.Coins();
				gob.entityTurn();
				Vampire.generateVampire(this);
				Dracula.generateDracula(this);
				ExplosiveVampire.generateExplosiveVampire(this);
				cicle++;
			}
		
			public boolean removeDead() {
			
				return gob.removeDead();
			
			}
		public void addVampireType(String tipo,int x,int y) throws NoMoreVampiresException{
			boolean added=false;
			if(Vampire.getRemainingVampires()>0) {
			if(tipo.contentEquals("v")) {
				added=true;
				this.addVampire(x, y);
			}
			else if(tipo.contentEquals("e")) {
				added=true;
				this.addExplosiveVampire(x, y);
			}
			else if(tipo.contentEquals("d")){
				added=true;
				this.addDracula(x,y);
			}
			if(added) {
			Vampire.plusVampiresOnBoard();
			Vampire.minusRemainingVampires();
			
			}
			}
			else {
				throw new NoMoreVampiresException();
			}
			}
		
		//condiciones para ciertos comandos y ejecuta el comando
		public boolean garlicPushConditions() throws NotEnoughCoinsException {
			boolean a=false;
			if(player.getCoins()>=10) {
			gob.garlicPush();//push
			player.removeCoins(10);
			a=true;
			}
			else {
				
				throw new NotEnoughCoinsException("[ERROR]:GarlicPush cost is 10:"+" Not enough coins" 
				+System.getProperty("line.separator") + "[ERROR]:Failed to do GarlicPush");
			}
			return a;
		}
		public boolean addBloodBankCondition(int x,int y,int z) throws NotEnoughCoinsException, UnvalidPositionException {
			boolean can=false;
			if((x>=0&&x<tamx-1)&&(y>=0&&y<tamy)) {//tamano
				if(gob.isPositionEmpty(x, y)) {//vacio
					if(player.getCoins()>=z) {//moneda
						gob.addBloodBank(x,y,z);
						player.removeCoins(z);
						can=true;
					}
					
					else {
						
						
						throw new NotEnoughCoinsException("[ERROR]:BloodBank cost is "+z+":"+" Not enough coins" 
						+System.getProperty("line.separator") + "[ERROR]:Failed to add BloodBank");
					}
				}
				else {
					
					throw new UnvalidPositionException("[ERROR]:Position("+x+","+y+"): Unvalid Position"
					+System.getProperty("line.separator")+"[ERROR]:Failed to add BloodBank");
				}
			
			}
			else {
				throw new UnvalidPositionException("[ERROR]:Position("+x+","+y+"):is out of boundary"
				+System.getProperty("line.separator")+"[ERROR]:Failed to add BloodBank");
				
			}
			return can;
		}
		public boolean addSlayerCondition(int x,int y) throws NotEnoughCoinsException, UnvalidPositionException {//condiciones para hacer un add y hace el add
			boolean can=false;
			if((x>=0&&x<tamx-1)&&(y>=0&&y<tamy)) {//tamano
				if(gob.isPositionEmpty(x, y)) {//vacio
					if(player.getCoins()>=50) {//moneda
					player.removeCoins(50);
					gob.addSlayer(x, y);//add
					can=true;
					}
					else {//error monedas
					
						throw new NotEnoughCoinsException( "[ERROR]:Slayer cost is 50 : Not enough coins" 
						+System.getProperty("line.separator") + "[ERROR]:Failed to add Slayer");
						
					}
				}
				else {//error ocupado
			
					throw new UnvalidPositionException("[ERROR]:Position("+x+","+y+"): Unvalid Position"
					+System.getProperty("line.separator")+"[ERROR]:Failed to add Slayer");
				}
				
			}
			else {//error posicion
				
				throw new UnvalidPositionException("[ERROR]:Position("+x+","+y+"):is out of boundary"
				+System.getProperty("line.separator")+"[ERROR]:Failed to add Slayer");
			}
	
	
		return can;
		}	
		
		public boolean addVampireConditions(String tipo,int x,int y) throws UnvalidPositionException, NoMoreVampiresException, DraculaAliveException{
			boolean can=false;
			
				if(Vampire.getDracula()&&tipo.contentEquals("d")) {//solo no se hace cuando..
					
					throw new DraculaAliveException();
				}
				//loght garlic
				else{
					if((x>=0&&x<tamx)&&(y>=0&&y<tamy)) {
					
						if(gob.isPositionEmpty(x, y)) {
							can=true;
							addVampireType(tipo, x, y);
						}
						else {
						
							throw new UnvalidPositionException("[ERROR]:Position("+x+","+y+"): Unvalid Position"
							+System.getProperty("line.separator")+"[ERROR]:Failed to add this Vampire");
						}
					}
					else {
					
						throw new UnvalidPositionException("[ERROR]:"+"Position("+x+","+y+"):is out of boundary"
						+System.getProperty("line.separator")+"[ERROR]:Failed to add this Vampire");
					}
				
				}
				
			
			return can;
		}
		public void exit() {
	
			this.exit=true;
	
		}
		public void reset() {
			cicle=0;
			gob = new Gob(new GameObjectList(this));
			player = new Player(random);
			Vampire.setRandom(random);
			Dracula.ResetDracula();
			initialize();
	
		}
		
		public boolean lightFlashConditions() throws NotEnoughCoinsException {
			boolean a=false;
			if(player.getCoins()>=50) {
			player.removeCoins(50);
			gob.lightFlash();
			a=true;
			}
			else {
				throw new NotEnoughCoinsException( "[ERROR]:LightFlash cost is 50 : Not enough coins" 
				+System.getProperty("line.separator") + "[ERROR]:Failed to LightFlash");
			}
			return a;
		}
		public IAttack getAttackableInPosition(int x,int y) {//IAttack
	
			IAttack other=getObjectInPosition(x,y);
			return other;
		}
		
		public boolean isFinished() {
			boolean finish=this.exit;//exit variable local
			for(int i=0;i<tamy;i++) {//vampire win
				
				if(!this.isPositionEmpty(-1, i)){
					finish=true;
				}
				
			}
			//Slayer win
			if(Vampire.getRemainingVampires()==0&&Vampire.getVampiresOnBoard()==0) {
				
				finish=true;
			}
			
		return finish;	
		}
		
		public String getWinnerMessage() {
			String message="You have now exited the program,please restart.";
			for(int i=0;i<tamy;i++) {//vampire win
				
				if(!this.isPositionEmpty(-1, i)){
				message="The vampires have arrived to your base,you have lost.";
			}
				
		}
			if(Vampire.getRemainingVampires()==0&&Vampire.getVampiresOnBoard()==0) {
				
				message="You have killed all the vampires,you win.";
			}
			return message;
		}
		public String serialize() {
			String s = "";
			s += "Cycles: " + cicle + System.getProperty("line.separator");
			s += "Coins: " + player.getCoins() + System.getProperty("line.separator");
			s += "Level: " + level.getName().toUpperCase() + System.getProperty("line.separator");
			s += "Remaining vampires: " + Vampire.getRemainingVampires() + System.getProperty("line.separator");
			s += "Vampires on board: " + Vampire.getVampiresOnBoard() + System.getProperty("line.separator");
			s += "Game Object List: " + System.getProperty("line.separator");
			s += gob.serialize();
			return s;
		}
		public void store(BufferedWriter outStream) throws IOException {
			outStream.write(this.serialize());
			
		}
}
