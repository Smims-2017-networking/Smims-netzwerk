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
	public boolean wantsToStartGame() {
		// TODO Auto-generated method stub
		return false;
	}
}
