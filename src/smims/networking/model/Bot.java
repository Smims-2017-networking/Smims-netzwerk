package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class Bot implements IBot{
	protected Player myPlayer;
	protected IBoard myBoard;
	protected IGame myGame;
	protected Collection<Character> allCharacters;
	protected Collection<Character> myCharacters; // TODO: Zuweisung
	protected Collection<Character> enemyCharacters;
	protected Position aimedPosition;
	// TODO: Setupcode

	public Bot(Player player, Game game) {
		myPlayer = player;
		myGame = game;
		myBoard = myGame.getBoard();
		allCharacters = myGame.getBoard().getAllCharacters();
		myCharacters = new ArrayList<Character>(allCharacters);//myCharacters und enemyCharacters sind Kopien der Liste allCharacters
		myCharacters.removeIf((character) -> character.getPlayer().getPlayerId() != myPlayer.getPlayerId());
		enemyCharacters = new ArrayList<Character>(allCharacters);
		enemyCharacters.removeAll(myCharacters);
	}
	
	protected void refreshBoard() {
		myBoard = myGame.getBoard();
	}
	
	public void rollDice() throws NotYourTurnException, MoveNotAllowedException {
		myGame.rollDice(myPlayer.getPlayerId());
	}
	public Position getAimedPosition() {
		return aimedPosition;
	}
	public void makeTurn() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException {
		while (myGame.whoseTurn() == myPlayer.getPlayerId()) {
			refreshBoard();
			if(myGame.getWinner() != null) {
				if(myGame.getWinner().getPlayerId() == myPlayer.getPlayerId()) {
					log("Yay, gewonnen");
				} else {
					log("Schade");
				}
				return;
			}
			if (myGame.getCurrentTurnState() == TurnState.ExpectRoll) {
				try {
					myGame.rollDice(myPlayer.getPlayerId());
				} catch (NotYourTurnException | MoveNotAllowedException e) {
					// Es sollte keine Exception auftreten, da die Methode immer nur beim Zug des
					// Bots aufgerufen wird
					// und nur dann gewürfelt wird, wenn der TurnState dies explizit vorschreibt
					continue;
				}
				log(myGame.getDiceResult() + " gewürfelt");
			} else if (myGame.getCurrentTurnState() == TurnState.ExpectMove){ // Im Zug
				//try {
					decide();
					myGame.moveCharacter(myPlayer.getPlayerId(), aimedPosition);
				//} catch (Exception e) {
				//2	log("We fucked up: " + e.toString());
				//}
			} else {
				break;
			}
		}
		log("Nicht mehr mein Zug");
	}

	public abstract void decide() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException;
	/**
	 * Decide on the Character to move depending on Game condition (characters in
	 * base) using the local board and player objects
	 * 
	 * @throws NotYourTurnException
	 * @throws MoveNotAllowedException
	 */
	
	/**
	 * Determines if the Player has any Characters in his base
	 * 
	 * @return true, if there are Characters in the base
	 */
	protected boolean hasCharactersInBase() {
		return myCharacters.stream().anyMatch((character) -> character.isInBase());
	}

	protected boolean characterWouldHit(Character c) {
		return myGame.getBoard().getCharacterAt(c.getPosition().getFieldNumber().get() + myGame.getDiceResult(),
				myPlayer.getPlayerId()) != null/*
												 * && // Auf dem Feld, auf das gezogen wird, steht ein Charakter
												 * !myGame.getBoard().characterWouldHitTeammate(c,
												 * myGame.getDiceResult())
												 */; // Kein Teammate würde getroffen

	}

	protected Collection<Character> getCharactersInBase() {
		return this.myCharacters.stream().filter((character) -> character.isInBase()).collect(Collectors.toList());
	}

	protected void log(String message) {
		System.out.println("[Bot " + myPlayer.getPlayerId() + "]: " + message);
	}

	protected Collection<Character> getCharactersMovableIntoHouse() {
		return myCharacters.stream()
				.filter((character) -> (
						character.getPosition().movedBy(myGame.getDiceResult()).isPresent() ? 
						!wouldHitAny(character, myCharacters)
						&& !wouldSkipAny(character, myCharacters)
						&& character.getPosition().movedBy(myGame.getDiceResult()).get().isInHouse()
						: false)).collect(Collectors.toList());
	}

	protected Collection<Character> getmovableCharactersInHouse() {
		return this.getMovableCharacters().stream()
				.filter(/*(character) -> (character.isInHouse() && !wouldHitAny(character, myCharacters)
						&& !wouldSkipAny(character, myCharacters)
						&& character.getPosition().movedBy(myGame.getDiceResult()).get().getHouseFieldNumber()
								.get() < myGame.getBoard().getBoardDescriptor().getHouseSize())).collect(Collectors.toList()*/(character) -> character.getPosition().isInHouse()).collect(Collectors.toList());
	}

	/**
	 * Check if the character c would skip anyone of those in the collection
	 * 
	 * @return
	 */

	protected boolean wouldHitAny(Character c, Collection<Character> toCheck) {
		for (Character ch : toCheck) { // Soll für alle Charaktere der Collection überprüft werden
			if (ch.getPosition().equals(c.getPosition().movedBy(myGame.getDiceResult()).orElse(null))) {//Überprüfe, ob an der Position, wo der Character c nach dem Zug stehen würde, eine Figur steht. Wenn die Figur ins nichts ziehen würde, würde dort auch niemand stehen. gebe daher false zurück
				return true;
			}
		}
		return false;
	}

	protected boolean wouldSkipAny(Character c, Collection<Character> toCheck) {
		for (Character ch : toCheck) { // Soll für alle Charaktere der Collection überprüft werden
			for (int i = 1; i < myGame.getDiceResult(); i++) {// Es wird überprüft, ob irgendein Charakter auf den
																// Felden steht, die der Charakter c beim Zug
																// überschreitet
				if (ch.getPosition().equals(c.getPosition().movedBy(i).orElse(null))) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected Optional<Character> getCharacterAtStartingPosition() {
		if(myGame.getBoard().getCharacterAt(myGame.getStartingPositionBuilder(myPlayer).atStartingPosition()) == null) {
			return Optional.empty();
		} else {
			return Optional.of(myGame.getBoard().getCharacterAt(myGame.getStartingPositionBuilder(myPlayer).atStartingPosition()));
		}
	}
	protected boolean allCharactersInBase() {
		return (!myGame.getBoard().playerHasCharactersOnBoard(myPlayer)) && !myGame.getBoard().allCharactersInHouse(myPlayer.getPlayerId());
	}
	protected Collection<Character> getMovableCharacters() {
		return myCharacters.stream().filter((character) -> myGame.getBoard().canMoveByDistance(character, myGame.getDiceResult())).collect(Collectors.toList());
	}
}
