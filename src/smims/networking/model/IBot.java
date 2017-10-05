package smims.networking.model;

public interface IBot {
	public void decide() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException;
	public Position getAimedPosition();
	public void makeTurn() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException;
	public void rollDice() throws NotYourTurnException, MoveNotAllowedException;
}
