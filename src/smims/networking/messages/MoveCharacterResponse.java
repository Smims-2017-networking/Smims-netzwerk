package smims.networking.messages;

import smims.networking.model.TurnState;

public class MoveCharacterResponse extends SmimsMessage {
	private TurnState turnState;
	
	// Can be null
	private TurnOverResponse turnOver;
	
	// Can be null
	private Character kickedCharacter;
	
	// Provided for gson support
	protected MoveCharacterResponse() { }
	
	public MoveCharacterResponse(TurnState turnState, TurnOverResponse turnOver, Character kickedCharacter) {
		this.turnState = turnState;
		this.turnOver = turnOver;
		this.kickedCharacter = kickedCharacter;
	}
	
	public TurnState getTurnState() {
		return turnState;
	}
	
	public boolean characterWasKicked() {
		return kickedCharacter != null;
	}
	
	public boolean turnIsOver() {
		return turnOver != null;
	}
	
	// Null iff !characterWasKicked
	public Character getKickedCharacter() {
		return kickedCharacter;
	}
	
	// Null iff !turnIsOver
	public TurnOverResponse getTurnOverResponse() {
		return turnOver;
	}
}
