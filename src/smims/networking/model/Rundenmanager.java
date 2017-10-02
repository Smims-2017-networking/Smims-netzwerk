package smims.networking.model;

import java.util.ArrayList;

public class Rundenmanager {

	private final ArrayList<Player> allPlayers;
	private final ArrayList<IPlayerChoice> allChoices;

	public Rundenmanager(ArrayList<Player> allPlayers, ArrayList<IPlayerChoice> allChoices) {
		this.allPlayers = allPlayers;
		this.allChoices = allChoices;
	}

	/**
	 * Diese Methode sorgt f�r die Ausf�hrung einer Spielrunde
	 */
	public void round() {
		// Dies wird f�r jeden Player in der Reihenfolge des Beitretens
		// durchgef�hrt
		for (int i=0; i<allPlayers.size(); i++) 
		{
			Player p = allPlayers.get(i);
			IPlayerChoice iPlayer = allChoices.get(i);
			
			if (p.charactersCanMove() == 0) {
				this.doThreeTurns(p);
			}

			else {
				turn(p,iPlayer);
			}
			// bei einer 6 darf nochmal gezogen werden
			while (p.myDice.getResult() == 6) {
				turn(p, iPlayer);
			}
		}
	}

	/**
	 * fuehrt einen Zug f�r einen beliebigen Spieler aus: wuerfeln und ziehen
	 * 
	 * @param Player p ist der Spieler
	 */
	private void turn(Player p, IPlayerChoice iPlayer) {
		p.rollDice();
		int x = iPlayer.chooseCharacter;	//Feld, auf dem die zu bewegende Figur steht
		p.moveCharacters(x);
	}

	/**
	 * Diese Methode f�hrt bis zu drei Z�ge aus, bis der Spieler eine sechs
	 * gew�rfelt hat
	 * 
	 * @param Player p ist der Spieler
	 */

	public void doThreeTurns(Player p) {
		for (int i = 0; i < 3; i++) {
			turn(p);
			if (p.myDice.getResult() == 6) {
				break;
			}
		}
	}
}
