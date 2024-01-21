package org.ucm.tp1.logic;


import org.ucm.tp1.lists.GameObjectList;
import org.ucm.tp1.logic.Game;
import org.ucm.tp1.logic.gameObject.*;
public class Gob {
	
	private GameObjectList golist;
	public Gob(GameObjectList golist){
		
		this.golist=golist;
		
	}
	public int getVTotal() {
		
		return Vampire.getRemainingVampires();
	}
	
	public boolean isPositionEmpty(int x,int y) {
		
		return golist.isPositionEmpty(x, y);
		
	}
	
	public GameObject getObjectInPosition(int x,int y) {
		
		return golist.getObjectInPosition(x,y);
	}
	public void addVampire(int x,int y) {
		
		golist.addVampire(x, y);
	}
	public void addSlayer(int x,int y) {
		
		golist.addSlayer(x, y);
	}
	public void addExplosiveVampire(int x,int y) {
		golist.addExplosiveVampire(x,y);
		
	}
	public void lightFlash() {
		
		golist.lightFlash();
		
	}
	public void garlicPush() {
		
		golist.garlicPush();
	}
	public void addDracula(int x,int y) {
		
		golist.addDracula(x,y);
	}
	public void addBloodBank(int x,int y,int z) {
		
		golist.addBloodBank(x,y,z);
	}
	public void entityTurn() {
		
		golist.entityTurn();
		
	}
	
	public boolean removeDead() {
		
		return golist.removeDead();
	}
	public String serialize() {
		
		return golist.serialize();
	}

}
