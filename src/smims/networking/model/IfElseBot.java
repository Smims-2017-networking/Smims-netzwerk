package smims.networking.model;

public class IfElseBot extends Bot {

	public IfElseBot(Player player, Game game) {
		super(player, game);
		// TODO Auto-generated constructor stub
	}

	protected void moveCharacter() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException {
		log("Bewege Charakter...überlege...");
		if (myCharacters.stream().anyMatch((character) -> character.isAtStartingPosition())) {// Wenn einer auf dem Startfeld ist, muss der gezogen werden
			if (wouldHitAny(myGame.getBoard().getCharacterAt(myGame.getBoard().getStartingPositionBuilderFor(myPlayer).atStartingPosition()),myCharacters)) {
				Position potentialCharacterPosition = myGame.getStartingPositionBuilder(myPlayer).atStartingPosition().movedBy(myGame.getDiceResult()).get();
				while(wouldHitAny(myGame.getBoard().getCharacterAt(potentialCharacterPosition), myCharacters)) {
						potentialCharacterPosition = potentialCharacterPosition.movedBy(myGame.getDiceResult()).get(); 
				}
				log("StartingPosition: " + potentialCharacterPosition.getFieldNumber().get());
				myGame.moveCharacter(myPlayer.getPlayerId(), potentialCharacterPosition);
			} else {
				myGame.moveCharacter(myPlayer.getPlayerId(), myCharacters.stream()
						.filter((character) -> character.isAtStartingPosition()).findFirst().get().getPosition());
			}
		} else if (!getmovableCharactersInHouse().isEmpty()) {// Wenn sich irgendein Charakter im Haus bewegen lässt,
			// soll das getan werden
			log("Kann einen im Haus bewegen");
			myGame.moveCharacter(myPlayer.getPlayerId(),
					getmovableCharactersInHouse().stream().findAny().get().getPosition());

		} else if (!myGame.getBoard().playerHasCharactersOnBoard(myPlayer)) {
			// Wenn alle Charaktere in der Basis sind ist es egal welcher bewegt wird, daher
			// einfach den ersten aus einer Collection von Characters nehmen, die in der
			// Base stehen
			myGame.moveCharacter(myPlayer.getPlayerId(),
					getCharactersInBase().stream().findFirst().get().getPosition());
			log("Bewege Charakter aus der Basis");
		} else if (myGame.getDiceResult() == 6 && getCharactersInBase().stream()
				.anyMatch((character) -> myGame.getBoard().canMoveByDistance(character, myGame.getDiceResult()))) {/// Wenn
			/// eine
			/// 6
			/// gewürfelt
			/// wurde,
			/// Charaktere
			/// in
			/// der
			/// Basis
			/// sind
			/// und
			/// kein
			/// Teamcharakter
			/// die
			/// Position
			/// versperrt
			myGame.moveCharacter(myPlayer.getPlayerId(),
					getCharactersInBase().stream().findFirst().get().getPosition());
			log("mindestens einer aufm feld");
			log("6 und noch ein Charakter in der Basis. Rausholen");
		} else if (!getCharactersMovableIntoHouse().isEmpty()) { // Wenn es Charaktere gibt, die ins Haus bewegt werden
																	// können
			log("Da kann einer rein");
			myGame.moveCharacter(myPlayer.getPlayerId(),
					getCharactersMovableIntoHouse().stream().findAny().get().getPosition());
		} else if (myCharacters.stream().anyMatch(
				(character) -> wouldHitAny(character, enemyCharacters) && !wouldSkipAny(character, enemyCharacters))) { // Wenn
			log("Schlage Charakter");
			myGame.moveCharacter(myPlayer.getPlayerId(), myCharacters.stream().filter(
					(character) -> wouldHitAny(character, enemyCharacters) && !wouldSkipAny(character, enemyCharacters))
					.findAny().get().getPosition());
		} else if (myCharacters.stream().anyMatch((character) -> wouldHitAny(character, enemyCharacters))) {
			log("Schlage einen aber setze mich vor gegner");
			myGame.moveCharacter(myPlayer.getPlayerId(), myCharacters.stream()
					.filter((character) -> wouldHitAny(character, enemyCharacters)).findAny().get().getPosition());
		} else {
			myGame.moveCharacter(myPlayer.getPlayerId(),
					getMovableCharacters().stream().findFirst().get().getPosition());
		}
		// }
	}

	@Override
	public void decide() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException {
		// TODO Auto-generated method stub
		
	}

}
