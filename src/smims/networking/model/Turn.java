package smims.networking.model;

public class Turn {
	private final IPlayer player;
	private final IBoard board;
	private final IDiceRoller diceRoller;
	private int maxRollCount;
	private int rolledCount = 0;
	private TurnState turnState = TurnState.ExpectRoll;
	
	public Turn(IPlayer player, IBoard board, IDiceRoller diceRoller) {
		this.player = player;
		this.board = board;
		this.diceRoller = diceRoller;
		maxRollCount = calculateMaxRollCount();
	}
	
	public IPlayer getPlayer() {
		return player;
	}

	public void moveCharacter(Position position) throws MoveNotAllowedException, NoSuchCharacterException {
		EnsurePlayerCanMoveCharacter();
		moveCharacterAtPosition(position);
		if (diceRoller.getResult() == 6) {
			transitionToRollingState();
		} else {
			transitionToFinishedState();
		}
	}
	
	private void moveCharacterAtPosition(Position position) throws MoveNotAllowedException, NoSuchCharacterException {
		Character selectedCharacter = board.getCharacterAt(position);
		if (selectedCharacter == null) {
			throw new NoSuchCharacterException("No character at position" + position);
		}
		
		board.moveCharacter(selectedCharacter, diceRoller.getResult());
	}

	private void EnsurePlayerCanMoveCharacter() throws MoveNotAllowedException {
		if (turnState != TurnState.ExpectMove) {
			throw new MoveNotAllowedException();
		}
		
	}

	private void transitionToRollingState() {
		maxRollCount = calculateMaxRollCount();
		rolledCount = 0;
		turnState = TurnState.ExpectRoll;
		
	}

	private int calculateMaxRollCount() {
		return board.playerHasCharactersOnBoard(player) || board.anyCharacterInHouseCanMove(player) ? 1 : 3;
	}

	private void transitionToFinishedState() {
		turnState = TurnState.Finished;
		
	}

	public void rollDice() throws MoveNotAllowedException {
		EnsurePlayerCanRollDice();
		diceRoller.rollDice();
		rolledCount++;
		if ((maxRollCount != 1 && diceRoller.getResult() == 6) || maxRollCount == 1) {
			attemptToTransitionToMoveState();
		} else if (maxRollCount == rolledCount) {
			transitionToFinishedState();
		}
	}

	private void attemptToTransitionToMoveState() {
		if (anyMoveIsValid()) {
			turnState = TurnState.ExpectMove;
		} else {
			turnState = TurnState.Finished;
		}
		
	}

	private boolean anyMoveIsValid() {
		return board.getAllCharacters().stream()
				.anyMatch(c -> c.isOfPlayer(player) && board.canMoveByDistance(c, diceRoller.getResult()));
	}

	private void EnsurePlayerCanRollDice() throws MoveNotAllowedException {
		if(turnState != TurnState.ExpectRoll) {
			throw new MoveNotAllowedException();
		}
	}

	public boolean isOfPlayer(int playerId) {
		return getPlayer().getPlayerId() == playerId;
	}
	
	boolean isOver() {
		return turnState == TurnState.Finished;
	}

	public TurnState getCurrentTurnState() {
		return turnState;
	}

	public int getRemainingRolls() {
		return getCurrentTurnState() == TurnState.ExpectRoll ? maxRollCount - rolledCount : 0;
	}
}
