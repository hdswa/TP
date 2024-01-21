package org.ucm.tp1.logic.gameObject;

import org.ucm.tp1.logic.Game;

public class Dracula extends Vampire {
	
	public Dracula(Game game,int x,int y){//crea un vampiro
		super(game,x,y);
		this.nombre="d";
		this.hp=5;
		this.dmg=5;
		is_Dracula_alive=true;
		
	}
	
	@Override
	public boolean alive() {
		boolean a=true;
		if(this.hp<=0) {
			a=false;
			is_Dracula_alive=false;
		}
		return a;
	}
	public boolean receiveLightFlash() {
		
		return false;
	}
	public boolean receiveExplosiveDMG(int damage) {
		this.receivedmg(damage);
		return true;
	}
	public static void ResetDracula() {
		
		is_Dracula_alive=false;
	}
	
	public static void generateDracula(Game game) {
		if(!is_Dracula_alive) {
		if(remainingVampires>0) {
			double freq=random.nextDouble();
			if(freq<game.getVFreq()) {
				int x=game.getTamx()-1;
				int y=random.nextInt(game.getTamy());//variable que determina donde estara horizontalmente
				if(game.isPositionEmpty(x, y)) {
					game.addDracula(x, y);
					
					remainingVampires--;
					vampiresOnBoard++;
				}
			}
			}
		}
	}
}
