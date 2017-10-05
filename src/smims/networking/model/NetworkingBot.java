package smims.networking.model;

public class NetworkingBot extends Bot {

	public NetworkingBot(Player player, Game game) {
		super(player, game);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void decide() throws MoveNotAllowedException, NotYourTurnException, NoSuchCharacterException {
		log("Bewege Charakter...überlege...");
		if (myCharacters.stream().anyMatch((character) -> character.isAtStartingPosition())) {// Wenn einer auf dem Startfeld ist, muss der gezogen werden
			if (wouldHitAny(myBoard.getCharacterAt(myBoard.getStartingPositionBuilderFor(myPlayer).atStartingPosition()),myCharacters)) {
				Position potentialCharacterPosition = myGame.getStartingPositionBuilder(myPlayer).atStartingPosition().movedBy(myGame.getDiceResult()).get();
				while(wouldHitAny(myBoard.getCharacterAt(potentialCharacterPosition), myCharacters)) {
						potentialCharacterPosition = potentialCharacterPosition.movedBy(myGame.getDiceResult()).get(); 
				}
				log("StartingPosition: " + potentialCharacterPosition.getFieldNumber().get());
				aimedPosition =  potentialCharacterPosition;
			} else {
				aimedPosition =  myCharacters.stream()
						.filter((character) -> character.isAtStartingPosition()).findFirst().get().getPosition();
			}
		} else if (!getmovableCharactersInHouse().isEmpty()) {// Wenn sich irgendein Charakter im Haus bewegen lässt,
			// soll das getan werden
			log("Kann einen im Haus bewegen");
			aimedPosition = 
					getmovableCharactersInHouse().stream().findAny().get().getPosition();

		} else if (!myBoard.playerHasCharactersOnBoard(myPlayer)) {
			// Wenn alle Charaktere in der Basis sind ist es egal welcher bewegt wird, daher
			// einfach den ersten aus einer Collection von Characters nehmen, die in der
			// Base stehen
			aimedPosition = 
					getCharactersInBase().stream().findFirst().get().getPosition();
			log("Bewege Charakter aus der Basis");
		} else if (myGame.getDiceResult() == 6 && getCharactersInBase().stream()
				.anyMatch((character) -> myBoard.canMoveByDistance(character, myGame.getDiceResult()))) {/// Wenn
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
			aimedPosition = 
					getCharactersInBase().stream().findFirst().get().getPosition();
			log("mindestens einer aufm feld");
			log("6 und noch ein Charakter in der Basis. Rausholen");
		} else if (!getCharactersMovableIntoHouse().isEmpty()) { // Wenn es Charaktere gibt, die ins Haus bewegt werden
																	// können
			log("Da kann einer rein");
			aimedPosition = 
					getCharactersMovableIntoHouse().stream().findAny().get().getPosition();
		} else if (myCharacters.stream().anyMatch(
				(character) -> wouldHitAny(character, enemyCharacters) && !wouldSkipAny(character, enemyCharacters))) { // Wenn
			log("Schlage Charakter");
			aimedPosition =  myCharacters.stream().filter(
					(character) -> wouldHitAny(character, enemyCharacters) && !wouldSkipAny(character, enemyCharacters))
					.findAny().get().getPosition();
		} else if (myCharacters.stream().anyMatch((character) -> wouldHitAny(character, enemyCharacters))) {
			log("Schlage einen aber setze mich vor gegner");
			aimedPosition =  myCharacters.stream()
					.filter((character) -> wouldHitAny(character, enemyCharacters)).findAny().get().getPosition();
		} else {
			aimedPosition = 
					getMovableCharacters().stream().findFirst().get().getPosition();
		}
		// }
	}

}
