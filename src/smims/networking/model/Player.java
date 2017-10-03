package smims.networking.model;

public class Player implements IPlayer {
	private int playerID;
	
	public Player(int playerId) {
		this.playerID = playerId;
	}
	
	@Override
	public void makePlayerWantToStartGame() {
		wantsToStartGame = true;
	}
	
	private boolean wantsToStartGame = false;

	@Override
	public int getPlayerId() {
		return playerID;
	}
	
	@Override
	public boolean wantsToStartGame() {
		return wantsToStartGame;
	}
}
