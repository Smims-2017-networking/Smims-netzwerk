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
	 * Diese Methode sorgt fï¿½r die Ausfï¿½hrung einer Spielrunde
	 * @throws PlayerWonException 
	 */
	public void round() throws PlayerWonException {
		// Dies wird fï¿½r jeden Player in der Reihenfolge des Beitretens
		// durchgefï¿½hrt
		for (int i = 0; i < allPlayers.size(); i++) {
			Player p = allPlayers.get(i);
			IPlayerChoice iPlayer = allChoices.get(i);

			if (p.charactersCanMove() == 0) {
				this.doThreeTurns(p, iPlayer);
			}

			else {
				turn(p, iPlayer);
			}
			// bei einer 6 darf nochmal gezogen werden
			while (p.myDice.getResult() == 6) {
				turn(p, iPlayer);
			}
			//Am Ende des Zuges wird überprüft, ob der Spieler gewonnen hat.
			if (abortion(allPlayers.get(i)) == true) {
				throw new PlayerWonException();
			}
		}
	}
	
	/**
	 * @param p der zu überprüfende Spieler
	 * @return true, wenn Spieler p gewonnen hat.
	 */
	private boolean abortion(Player p) {
		if(p.charactersInBase()==4) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * fuehrt einen Zug fï¿½r einen beliebigen Spieler aus: wuerfeln und ziehen
	 * 
	 * @param Player
	 *            p ist der Spieler
	 */
	private void turn(Player p, IPlayerChoice iPlayer) {
		p.rollDice();
		turnWithoutRoll(p, iPlayer);
	}

	private void turnWithoutRoll(Player p, IPlayerChoice iPlayer) {
		int x = iPlayer.chooseCharacter(); // Feld, auf dem die zu bewegende Figur steht
		p.moveCharacters(x);
	}

	/**
	 * Diese Methode fï¿½hrt bis zu drei Zï¿½ge aus, bis der Spieler eine sechs
	 * gewï¿½rfelt hat
	 * 
	 * @param Player
	 *            p ist der Spieler
	 */

	private void doThreeTurns(Player p, IPlayerChoice iPlayer) {
		for (int i = 0; i < 3; i++) {
			p.rollDice();
			if (p.myDice.getResult() == 6) {
				turnWithoutRoll(p, iPlayer);
				break;
			}
		}
	}
}
