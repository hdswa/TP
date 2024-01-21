package org.ucm.tp1.logic.gameObject;

import org.ucm.tp1.logic.Game;

public class BloodBank extends GameObject{
	private int z;
	public BloodBank (Game game,int x,int y,int z) {
		
		super(game,x,y);
		this.hp=1;
		this.z=z;
		this.nombre="b";
		
		
	}
	public String toString () {
		
		return nombre +" ["+Integer.toString(z)+"]";
		
	}
	@Override
	public void attack() {
		// TODO Auto-generated method stub
		//no hace nada
	}

	@Override
	public void entityTurn() {
		int a=z/10;
		if(alive()) {
			
			game.bloodBankCoins(a);
			
		}
		
	}
	public int getZ() {
		
		return this.z;
	}
	@Override
	public boolean alive() {
		boolean alive=true;
		if(this.hp<=0) {
			
			alive=false;
		}
		return alive;
	}
	
	@Override
	public boolean receiveVampireAttack(int damage) {
		this.receivedmg(damage);
		return true;
	}
	@Override
	public String serialize() {
		
		return nombre + ";" + Integer.toString(pos_x) + ";" + Integer.toString(pos_y) + ";" + Integer.toString(hp)+";"+Integer.toString(z);
	}
}
