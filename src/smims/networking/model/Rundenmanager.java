package smims.networking.model;

import java.util.ArrayList;

public class Rundenmanager {
	
	public Rundenmanager(ArrayList <Player> allPlayers)
	{
		for(Player p:allPlayers) {
			if(p.charactersCanMove()==0) {
			for(int i=0;i<3;i++) {
				turn(p);
				if(p.myDice.getResult()==6) {
					break;
				}
			}
			}
			else {
				
			}
		}
	}
	
	public void turn(Player p) {
		p.rollDice();
		
	}
}
