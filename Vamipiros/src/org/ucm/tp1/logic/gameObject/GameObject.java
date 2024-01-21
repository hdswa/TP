package org.ucm.tp1.logic.gameObject;
import org.ucm.tp1.logic.Game;
import java.util.Random;
public abstract class GameObject implements IAttack{
	protected int dmg;
	protected int hp;
	protected int pos_x,pos_y;
	protected String nombre=null;
	protected Game game;
	protected static Random random;
	
	public GameObject (Game game,int x,int y) {
		this.game=game;
		this.pos_x=x;
		this.pos_y=y;
		
		
	}
	public String toString () {
		
		return nombre +" ["+Integer.toString(hp)+"]";
		
	}
	public String getID() {//"v" "s" "ev" "d"
		
		return nombre;
	}
	public void receivedmg(int dmg) {
		
		hp=hp-dmg;
	}
	
	public int getPosX() {
		
		return pos_x;
	}
	public int getPosY() {
		
		return pos_y;
	}
	
	
	//----------------------------------------funciones abstractas
	public abstract void entityTurn(); 
	public abstract boolean alive();
	public abstract String serialize();
	
}
