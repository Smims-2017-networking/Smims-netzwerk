package smims.networking.model;

import java.util.ArrayList;

public class Rundenmanager {

	private final ArrayList<GameState> allPlayers;
	private final ArrayList<IPlayerChoice> allChoices;

	public Rundenmanager(ArrayList<GameState> allPlayers, ArrayList<IPlayerChoice> allChoices) {
		this.allPlayers = allPlayers;
		this.allChoices = allChoices;
	}

	/**
	 * Diese Methode sorgt f�r die Ausf�hrung einer Spielrunde
	 * @throws PlayerWonException 
	 */
	public void round() throws PlayerWonException {
		// Dies wird f�r jeden Player in der Reihenfolge des Beitretens
		// durchgef�hrt
		for (int i = 0; i < allPlayers.size(); i++) {
			GameState p = allPlayers.get(i);
			IPlayerChoice iPlayer = allChoices.get(i);

			if (p.numberOfMovableCharacters() == 0) {
				this.doThreeTurns(p, iPlayer);
			}

			else {
				turn(p, iPlayer);
			}
			// bei einer 6 darf nochmal gezogen werden
			while (p.myDice.getResult() == 6) {
				turn(p, iPlayer);
			}
			//Am Ende des Zuges wird �berpr�ft, ob der Spieler gewonnen hat.
			if (abortion(allPlayers.get(i)) == true) {
				throw new PlayerWonException();
			}
		}
	}
	
	/**
	 * @param p der zu �berpr�fende Spieler
	 * @return true, wenn Spieler p gewonnen hat.
	 */
	private boolean abortion(GameState p) {
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
	 * @param GameState
	 *            p ist der Spieler
	 */
	private void turn(GameState p, IPlayerChoice iPlayer) {
		p.rollDice();
		turnWithoutRoll(p, iPlayer);
	}

	private void turnWithoutRoll(GameState p, IPlayerChoice iPlayer) {
		int x = iPlayer.chooseCharacter(); // Feld, auf dem die zu bewegende Figur steht
		p.moveCharacters(x);
	}

	/**
	 * Diese Methode f�hrt bis zu drei Z�ge aus, bis der Spieler eine sechs
	 * gew�rfelt hat
	 * 
	 * @param GameState
	 *            p ist der Spieler
	 */

	private void doThreeTurns(GameState p, IPlayerChoice iPlayer) {
		for (int i = 0; i < 3; i++) {
			p.rollDice();
			if (p.myDice.getResult() == 6) {
				turnWithoutRoll(p, iPlayer);
				break;
			}
		}
	}
}
