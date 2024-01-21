package org.ucm.tp1.logic.gameObject;

import org.ucm.tp1.logic.Game;
import java.util.Random;
import org.ucm.tp1.logic.Level;
public class Vampire extends GameObject{
	//propiedades de vampiro basico que heredara a EV y DR	
	protected final static int inicial_turn=2;
	protected static Random random;
	protected static int remainingVampires;
	protected static int vampiresOnBoard=0;
	protected static double spawnRate;
	private int turn=inicial_turn;
	
	protected static boolean is_Dracula_alive=false;
	public Vampire(Game game,int x,int y){//crea un vampiro
		super(game,x,y);
		this.nombre="v";
		this.hp=5;
		this.dmg=1;
		
	}
	//-------------------------------------------------------
	//-------------------------------------------------------
	public static void setRemainingVampires(int a) {
		remainingVampires=a;	
	}
	public static void setRandom(Random a) {
		random=a;
	}
	public static void resetVampiresOnBoard() {
		vampiresOnBoard=0;
	}
	public static int getVampiresOnBoard() {
		
		return vampiresOnBoard;
	}
	public static void minusVampiresOnBoard() {
		
		vampiresOnBoard--;
	}
	public static void minusRemainingVampires() {
		
		remainingVampires--;
	}
	
	public static String draculaAliveMessage() {
		String a="";
		if(is_Dracula_alive) {
			
			a="Dracula is alive";
			
		}
		return a;
	}
	public static void generateVampire(Game game) {
		if(remainingVampires>0) {
			double freq=random.nextDouble();
			if(freq<game.getVFreq()) {
				int x=game.getTamx()-1;
				int y=random.nextInt(game.getTamy());//variable que determina donde estara horizontalmente
				if(game.isPositionEmpty(x, y)) {
					game.addVampire(x, y);//<<<<<<<<<<<<<<<<<<<<<<<<<<!!!V EV DR
					remainingVampires--;
					vampiresOnBoard++;
					
				}
			}
			}
		
	}
	public static int getRemainingVampires() {
		
		return remainingVampires;
	}
	//------------------------------------------------
	public int getRemainingV() {
		
		return remainingVampires;
	}
	public static void plusVampiresOnBoard() {
		
		vampiresOnBoard++;
	}
	public static boolean getDracula() {
		
		return is_Dracula_alive;
	}
	
	@Override
	public void entityTurn() {//siempre se le restara turno interno y atacara a su posicion de delante
		this.turn--;
		
		if(turn==0) {//si tiene turno 0 se mueve
			vampireMove();	
			turn=inicial_turn;
		}
		attack();//no es necesario que ataque cada dos turnos
		
	}
	@Override
	public boolean alive() {
		boolean a=true;
		if(this.hp<=0) {
			
			a=false;
		}
		return a;
	}

	public void vampireMove() {
		
		if(game.getObjectInPosition(this.pos_x-1, this.pos_y)==null) {
			
			this.pos_x--;
		}
	}
	@Override
	public void attack() {
		
		if (alive () ) {
			IAttack other = game.getAttackableInPosition(pos_x-1,pos_y);
			if (other != null )
			other. receiveVampireAttack(dmg);
			}
	}
	public boolean receiveSlayerAttack(int damage) {
		this.receivedmg(damage);
		return true;
	}
	public boolean receiveGarlicPush() {
		if(game.isPositionEmpty(this.pos_x+1, this.pos_y)) {
		this.pos_x++;
		}
		if(this.turn==1) {
			this.turn++;
			
		}
		return true;
	}
	public boolean receiveLightFlash() {
		
		this.hp=0;
		return true;
	}
	public boolean receiveExplosiveDMG(int damage) {
		this.receivedmg(damage);
		return true;
	}
	@Override
	public String serialize() {
		
		return nombre + ";" + Integer.toString(pos_x) + ";" + Integer.toString(pos_y) + ";" + Integer.toString(hp)+";"+Integer.toString(turn);
	}	
	
}
