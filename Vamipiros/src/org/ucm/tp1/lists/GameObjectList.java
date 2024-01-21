package org.ucm.tp1.lists;
import org.ucm.tp1.logic.gameObject.*;

import org.ucm.tp1.logic.Game;
import java.util.ArrayList;

public class GameObjectList {
	
	private ArrayList<GameObject> go;
	private Game game;
	
	public GameObjectList(Game game) {
		this.game=game;
		go=new ArrayList<GameObject>();
	}
	
		public void addSlayer(int x,int y) {
			
			go.add(new Slayer(game,x,y));
		}
		public void addVampire(int x,int y) {
			
			go.add(new Vampire(game,x,y));
		}
		public void addExplosiveVampire(int x,int y) {
			
			go.add(new ExplosiveVampire(game,x,y));
		
		}
		public void addDracula(int x,int y) {
			
			go.add(new Dracula(game,x,y));
			
		}
		public void addBloodBank(int x,int y,int z) {
			
			go.add(new BloodBank(game,x,y,z));
		}
		
		
		public String getName(int i) {
			return go.get(i).toString();
			
		}
		
		public int getSize() {
			
			return go.size();
		}
		public boolean isPositionEmpty(int x,int y) {//comprueba si la posicion existe algo
			boolean exist=true;
			for(int i=0;i<getSize();i++) {
			
				if(go.get(i).getPosX()==x&&go.get(i).getPosY()==y) {
					
					exist=false;
				}
			}
			return exist;
			
		}
		
		public GameObject getObjectInPosition(int x,int y) {//devuelve el objeto en xy
			int indice;
			GameObject a=null;
			for(int i=0;i<go.size();i++) {
				
				if(go.get(i).getPosX()==x&&go.get(i).getPosY()==y) {
					
					indice=i;
					a=go.get(indice);
				}
				}
			
			return a;
			
		}
		public void entityTurn() {//turno de las entidades del juego
			int i=0;
			while(i<go.size()) {
			
				go.get(i).entityTurn();
				removeDead();
				i++;
				}
									
								
			}
			
		
		public void garlicPush() {
			for(int i=0;i<go.size();i++) {
				
				go.get(i).receiveGarlicPush();
			}
			
			
		}
		public boolean removeDead() {//remueve un elemento de GOList[]
		boolean removed=false;
		String a;
			for(int i=0;i<go.size();i++) {
				
				if(!go.get(i).alive()||go.get(i).getPosX()>=game.getTamx()) {
				a=go.get(i).getID();
				if(a!="s"&&a!="b") {
						Vampire.minusVampiresOnBoard();		
				}
					go.remove(i);
					removed=true;
				
				}
				
			}
			return removed;
		}
		
		public void lightFlash() {
			
			
			for(int i=0;i<go.size();i++) {
				
				go.get(i).receiveLightFlash();
			}
			
			
		}
		public String serialize() {
			String s = "";
			for (int i = 0;i < go.size();i++) {
				s += go.get(i).serialize() + System.getProperty("line.separator");
			}
			return s;
		}
		
	}	