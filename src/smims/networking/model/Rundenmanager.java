package smims.networking.model;

import java.util.ArrayList;

public class Rundenmanager {

	private final ArrayList<Player> allPlayers;
	private final GameState gameState;

	public Rundenmanager(ArrayList<Player> allPlayers, GameState gameState) {
		this.allPlayers = allPlayers;
		this.gameState = gameState;
	}

	/**
	 * Diese Methode sorgt f�r die Ausf�hrung einer Spielrunde
	 * @throws Exception
	 */
	public void round() throws Exception {
		// Dies wird f�r jeden Player in der Reihenfolge des Beitretens
		// durchgef�hrt
		for (int i = 0; i < allPlayers.size(); i++) {
			Player p = allPlayers.get(i);

			if (p.numberOfMovableCharacters() == 0) {
				this.doThreeTurns(p);
			}

			else {
				turn(p);
			}
			// bei einer 6 darf nochmal gezogen werden
			while (gameState.myDice.getResult() == 6) {
				turn(p);
			}
			//Am Ende des Zuges wird �berpr�ft, ob der Spieler gewonnen hat.
			if (playerHasWon(allPlayers.get(i)) == true) {
				throw new PlayerWonException();
			}
		}
	}
	
	/**
	 * @param p der zu �berpr�fende Spieler
	 * @return true, wenn Spieler p gewonnen hat.
	 */
	private boolean playerHasWon(Player p) {
		if(p.charactersInBase()==4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * fuehrt einen Zug f�r einen beliebigen Spieler aus: wuerfeln und ziehen
	 * 
	 * @param Player
	 *            p ist der Spieler
	 * @throws Exception 
	 */
	private void turn(Player player) throws Exception {
		gameState.rollDice();
		turnWithoutRoll(player);
	}

	private void turnWithoutRoll(Player player) throws Exception {
		int x = player.chooseCharacter(); // Feld, auf dem die zu bewegende Figur steht
		player.moveCharacters(x);
	}

	/**
	 * Diese Methode f�hrt bis zu drei Z�ge aus, bis der Spieler eine sechs
	 * gew�rfelt hat
	 * 
	 * @param Player
	 *            p ist der Spieler
	 * @throws Exception
	 */

	
}
