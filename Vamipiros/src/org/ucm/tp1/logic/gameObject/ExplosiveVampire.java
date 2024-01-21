package org.ucm.tp1.logic.gameObject;
import org.ucm.tp1.logic.Game;
public class ExplosiveVampire extends Vampire {
	private int turn=inicial_turn;
	private final int explosive_dmg=1;
	
	public ExplosiveVampire(Game game,int x,int y){//crea un vampiro
		super(game,x,y);
		this.nombre="ev";
		this.hp=5;
		this.dmg=1;
		
	}
	@Override
	public boolean alive() {
		boolean a=true;
		if(this.hp<=0) {
			a=false;
		}
		return a;
	}
	
	public boolean receiveSlayerAttack(int damage) {
		this.receivedmg(damage);
		if(!this.alive()) {
			deathExplosion();
		}
		return true;
	}
	public void deathExplosion() {//explosion
		
		for(int i=this.pos_x-1;i<=this.pos_x+1;i++) {
			for(int j=this.pos_y-1;j<=this.pos_y+1;j++) {
				IAttack other=game.getAttackableInPosition(i, j);
					if(other!=null) {
						other.receiveExplosiveDMG(explosive_dmg);//no hace falta crear una nueva fuente de daño
					}
			}
		
		}
	}
	public static void generateExplosiveVampire(Game game) {
		if(remainingVampires>0) {
			double freq=random.nextDouble();
			if(freq<game.getVFreq()) {
				int x=game.getTamx()-1;
				int y=random.nextInt(game.getTamy());//variable que determina donde estara horizontalmente
				if(game.isPositionEmpty(x, y)) {
					game.addExplosiveVampire(x, y);//<<<<<<<<<<<<<<<<<<<<<<<<<<!!!V EV DR
					remainingVampires--;
					vampiresOnBoard++;
					
				}
			}
			}
		
	}
	
}

