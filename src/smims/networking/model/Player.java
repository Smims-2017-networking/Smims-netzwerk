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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + playerID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return equals((Player) obj);
	}

	@Override
	public boolean equals(Player other) {
		return playerID == other.playerID;
	}
	
	
}
