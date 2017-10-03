package smims.networking.messages;

public class RollDiceResponse extends SmimsMessage {
	private int diceResult;
	private boolean mustRollAgain;
	
	// Provided for gson compatibility
	protected RollDiceResponse() { }
	
	public RollDiceResponse(int diceResult, boolean mustRollAgain) {
		this.diceResult = diceResult;
		this.mustRollAgain = mustRollAgain;
	}
}
