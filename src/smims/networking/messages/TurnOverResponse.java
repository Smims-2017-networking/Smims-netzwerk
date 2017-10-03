package smims.networking.messages;

import smims.networking.model.Player;

public class TurnOverResponse extends SmimsMessage {
	private Player nextPlayer;
	
	// Can be null
	private Player winningPlayer;
	
	// Provided for gson support
	protected TurnOverResponse() { }
	
	public TurnOverResponse(Player nextPlayer, Player winningPlayer) {
		this.nextPlayer = nextPlayer;
		this.winningPlayer = winningPlayer;
	}
	
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	public boolean playerHasWon() {
		return winningPlayer != null;
	}
	
	// Null iff !playerHasWon
	public Player getWinner() {
		return winningPlayer;
	}
}
