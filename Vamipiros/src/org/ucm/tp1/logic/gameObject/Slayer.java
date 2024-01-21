package org.ucm.tp1.logic.gameObject;
import org.ucm.tp1.logic.Game;
public class Slayer extends GameObject{
									// implements IAttack
	private static int SlayerOnBoard=0;
	private final static int dmg=1;
	private static int range;
	public Slayer(Game game,int x,int y){//crea slayer
		super(game,x,y);
		this.hp=3;
		this.nombre="s";
		
	}
	
	
	public int getSlayerOnBoard() {
		
		return SlayerOnBoard;
	}
	
	
	public final static void setRange(int a) {//define hasta donde puede disparar el slayer
		range=a;
		
	}
	@Override
	public void attack() {
		boolean shooted=false;
		for(int i=this.pos_x;i<range&&shooted==false;i++) {//recorre x+1 hasta tamx
															//buscando si existe algo
															//que pueda recibir daño de el
			IAttack other=game.getAttackableInPosition(i+1, this.pos_y);
					if(other!=null&&other.receiveSlayerAttack(dmg)&&!shooted) {
						shooted=true;
			}
			}
		}
	@Override
	public void entityTurn() {
		attack();
		
	}
	@Override
	public boolean alive() {
		
		boolean a=true;
		if(this.hp<=0) {
			
			a=false;
		}
		return a;
	
	}
	
	@Override
	public boolean receiveVampireAttack(int damage) {
		this.receivedmg(damage);
		return true;
	}
	@Override
	public String serialize() {
		
		return nombre + ";" + Integer.toString(pos_x) + ";" + Integer.toString(pos_y) + ";" + Integer.toString(hp);
	}
	
}
