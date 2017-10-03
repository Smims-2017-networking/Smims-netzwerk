package smims.networking.model;

import java.util.Collection;

public class Player {
	private int playerID;
	public void makePlayerWantToStartGame() {
		wantsToStartGame = true;
	}
	
	private boolean wantsToStartGame = false;

	public int getPlayerId() {
		return playerID;
	}

	public int charactersInBase() {
		int i=0;
		for(charactersOnBoard c:each) {
			if(c.myPlayer==playerID&&c.)
		}
		return i;
	}

	public void moveCharacters(int x) {
		// TODO Auto-generated method stub
		
	}

	public int numberOfMovableCharacters() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean wantsToStartGame() {
		// TODO Auto-generated method stub
		return false;
	}

	public void greet() {
		// TODO Auto-generated method stub
		
	}

	public int chooseCharacter() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void signOff() {
		// TODO Auto-generated method stub
		
	}	
}
