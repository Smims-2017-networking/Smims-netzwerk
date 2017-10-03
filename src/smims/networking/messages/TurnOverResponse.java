package smims.networking.messages;

import smims.networking.model.IPlayer;

public class TurnOverResponse extends SmimsMessage {
	private IPlayer nextPlayer;
	
	// Can be null
	private IPlayer winningPlayer;
	
	// Provided for gson support
	protected TurnOverResponse() { }
	
	public TurnOverResponse(IPlayer nextPlayer, IPlayer winningPlayer) {
		this.nextPlayer = nextPlayer;
		this.winningPlayer = winningPlayer;
	}
	
	public IPlayer getNextPlayer() {
		return nextPlayer;
	}
	
	public boolean playerHasWon() {
		return winningPlayer != null;
	}
	
	// Null iff !playerHasWon
	public IPlayer getWinner() {
		return winningPlayer;
	}
}
