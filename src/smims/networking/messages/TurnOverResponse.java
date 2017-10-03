package smims.networking.messages;

import smims.networking.model.Player;

public class TurnOverResponse extends SmimsMessage {
	private Player nextPlayer;
	
	// Provided for gson support
	protected TurnOverResponse() { }
	
	public TurnOverResponse(Player nextPlayer) {
		this.nextPlayer = nextPlayer;
	}
	
	public Player getNextPlayer() {
		return nextPlayer;
	}
}
