package org.ucm.tp1.logic.gameObject;

import java.util.Random;


public class Player {
	
	private int coins=50;
	
	private Random random;
		
	public Player(Random random) {
		
		this.random=random;
		
	}
	 
	public void Coins() {
		
		float prob=random.nextFloat();
		if(prob>0.5) {//50% para 10 monedas
		this.coins=coins+10;
	
	}
	}
	public int getCoins() {
		return this.coins;
		
	}
	public void removeCoins(int amount) {
		coins=coins-amount;
	
	}
	
}
