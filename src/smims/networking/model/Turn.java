package smims.networking.model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
		maxRollCount = playerHasCharactersOnBoard(player, board) ? 1 : 3;
	}
	
	private static boolean playerHasCharactersOnBoard(IPlayer player, IBoard board) {
		return board.playerHasCharactersOnBoard(player);
	}

	public IPlayer getPlayer() {
		return player;
	}
	
	public void moveCharacter(int characterPos, int playerId) throws MoveNotAllowedException, NoSuchCharacterException {
		EnsurePlayerCanMoveCharacter();
		moveCharacterAtPosition(characterPos, playerId);
		if (diceRoller.getResult() == 6) {
			transitionToRollingState();
		} else {
			transitionToFinishedState();
		}
	}
	
	private void moveCharacterAtPosition(int characterPos, int playerId) throws MoveNotAllowedException, NoSuchCharacterException {
		Character selectedCharacter = board.getCharacterAt(characterPos, playerId);
		if (selectedCharacter == null) {
			throw new NoSuchCharacterException("No character at position" + characterPos);
		}
		
		board.moveCharacter(selectedCharacter, diceRoller.getResult());
	}

	private void EnsurePlayerCanMoveCharacter() throws MoveNotAllowedException {
		if (turnState != TurnState.ExpectMove) {
			throw new MoveNotAllowedException();
		}
		
	}

	private void transitionToRollingState() {
		maxRollCount = playerHasCharactersOnBoard(player, board) ? 1 : 3;
		rolledCount = 0;
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
}
