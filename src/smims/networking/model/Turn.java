package smims.networking.model;

public class Turn {
	private final Player player;
	private final Board board;
	private final DiceRoller diceRoller;
	private final int maxRollCount;
	private int rolledCount = 0;
	private TurnState turnState;
	
	
	public Turn(Player player, Board board, DiceRoller diceRoller) {
		this.player = player;
		this.board = board;
		this.diceRoller = diceRoller;
		maxRollCount = playerHasCharactersOnBoard(player, board) ? 1 : 3;
	}
	
	private static boolean playerHasCharactersOnBoard(Player player, Board board) {
		return board.getAllCharacters().stream().anyMatch(character -> character.getPlayer() == player);
	}

	public Player getPlayer() {
		return player;
	}
	
	public void moveCharacter(int characterPos, int playerId) throws MoveNotAllowedException {
		EnsurePlayerCanMoveCharacter();
		moveCharacterAtPosition(characterPos, playerId);
		if (diceRoller.getResult() == 6) {
			transitionToRollingState();
		} else {
			transitionToFinishedState();
		}
	}
	
	private void moveCharacterAtPosition(int characterPos, int playerId) {
		board.moveCharacter();
	}

	private void EnsurePlayerCanMoveCharacter() throws MoveNotAllowedException {
		if (turnState != TurnState.ExpectMove) {
			throw new MoveNotAllowedException();
		}
		
	}

	private void transitionToRollingState() {
		turnState = TurnState.ExpectRoll;
		
	}

	private void transitionToFinishedState() {
		turnState = TurnState.Finished;
		
	}

	public void rollDice() throws MoveNotAllowedException {
		EnsurePlayerCanRollDice();
		diceRoller.rollDice();
		rolledCount++;
		if ((maxRollCount != 1 && diceRoller.getResult() == 6) || maxRollCount == 1) {
			transitionToMoveState();
		} else if (maxRollCount == rolledCount) {
			transitionToFinishedState();
		}
	}

	private void transitionToMoveState() {
		turnState = TurnState.ExpectMove;
		
	}

	private void EnsurePlayerCanRollDice() throws MoveNotAllowedException {
		if(!(turnState == TurnState.ExpectRoll) && rolledCount < maxRollCount) {
			throw new MoveNotAllowedException();
		}
	}

	public boolean isOfPlayer(int playerId) {
		return getPlayer().getPlayerId() == playerId;
	}
	
	boolean isOver() {
		return turnState == TurnState.Finished;
	}
}
