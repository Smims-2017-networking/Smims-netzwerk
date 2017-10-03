package smims.networking.model;

public class Turn {
	private final Player player;
	private final IBoard board;
	private final DiceRoller diceRoller;
	private int rolledCount = 0;
	private int maxRollCount = 1;
	private TurnState turnState;
	
	
	public Turn(Player player, IBoard board, DiceRoller diceRoller) {
		this.player = player;
		this.board = board;
		this.diceRoller = diceRoller;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void moveCharacter(int characterPos) throws MoveNotAllowedException {
		EnsurePlayerCanMoveCharacter();
		moveCharacterAtPosition(characterPos);
		if (diceRoller.getResult() == 6) {
			transitionToRollingState();
		} else {
			transitionToFinishedState();
		}
	}
	
	private void moveCharacterAtPosition(int characterPos) {
		board.moveCharacter(iCharacter, distance);
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
