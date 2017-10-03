package smims.networking.messages;

import smims.networking.model.TurnState;

public class RollDiceResponse extends SmimsMessage {
	private int diceResult;
	private TurnState turnState;
	
	// Provided for gson compatibility
	protected RollDiceResponse() { }
	
	public RollDiceResponse(int diceResult, TurnState turnState) {
		this.diceResult = diceResult;
		this.turnState = turnState;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}
	public int getDiceResult() {
		return diceResult;
	}
}
