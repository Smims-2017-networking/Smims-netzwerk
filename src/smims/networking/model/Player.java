package smims.networking.model;

public class Player {
	private int playerID;
	
	public Player(int playerId) {
		this.playerID = playerId;
	}
	
	public void makePlayerWantToStartGame() {
		wantsToStartGame = true;
	}
	
	private boolean wantsToStartGame = false;

	public int getPlayerId() {
		return playerID;
	}
	public boolean wantsToStartGame() {
		return wantsToStartGame;
	}
}
