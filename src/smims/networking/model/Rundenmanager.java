package smims.networking.model;

import java.util.ArrayList;

public class Rundenmanager {
	
	private ArrayList<Player> allPlayers;
	
	
	
	public Rundenmanager(ArrayList <Player> allPlayers)
	{
		this.allPlayers = allPlayers;
	}

	/**
	 * Diese Methode sorgt f�r die Ausf�hrung einer Spielrunde
	 */
	public void round()
	{
		//Dies wird f�r jeden Player in der Reihenfolge des Beitretens durchgef�hrt
				for(Player p:allPlayers)
				{
					if(p.charactersCanMove()==0)
					{
						this.doThreeTurns(p);
					}
					
					else
					{
						turn(p);	
					}
					//bei einer 6 darf nochmal gezogen werden
					while(p.myDice.getResult()==6)
					{
						turn(p);
					}
				}
	}
	
	
	/**
	 * fuehrt einen Zug f�r einen beliebigen Spieler aus: wuerfeln und ziehen
	 * @param Player p ist der Spieler
	 */
	public void turn(Player p)
	{
		p.rollDice();
		int x=2;//get from PlayerChoice after implemented
		p.moveCharacters(x);
	}
	
	/**
	 * Diese Methode f�hrt bis zu drei Z�ge aus, bis der Spieler eine sechs gew�rfelt hat
	 * @param Player p ist der Spieler
	 */
	
	public void doThreeTurns(Player p)
	{
		for(int i=0;i<3;i++)
		{
			turn(p);
			if(p.myDice.getResult()==6)
			{
				break;
			}
		}
	}
}
