package smims.networking.messages;

import smims.networking.model.Player;

public class TurnOverResponse extends SmimsMessage {
	private Player nextPlayer;
	private Player previousPlayer;
	
	// Provided for gson support
	protected TurnOverResponse() { }
	
	public TurnOverResponse(Player nextPlayer, Player previousPlayer) {
		this.nextPlayer = nextPlayer;
		this.previousPlayer=previousPlayer;
	}
	
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	public Player getPreviousPlayer() {
		return previousPlayer;
	}
}
