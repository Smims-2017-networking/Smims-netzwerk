package smims.networking.messages;

public class MoveCharacterResponse extends SmimsMessage {
	private boolean mustRollAgain;
	
	// Can be null
	private TurnOverResponse turnOver;
	
	// Can be null
	private Character kickedCharacter;
	
	// Provided for gson support
	protected MoveCharacterResponse() { }
	
	public MoveCharacterResponse(boolean mustRollAgain, TurnOverResponse turnOver, Character kickedCharacter) {
		this.mustRollAgain = mustRollAgain;
		this.turnOver = turnOver;
		this.kickedCharacter = kickedCharacter;
	}
	
	public boolean getMustRollAgain() {
		return mustRollAgain;
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
