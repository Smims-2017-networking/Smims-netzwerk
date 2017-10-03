package smims.networking.model;

import java.util.ArrayList;

public class TurnManager {
	private Turn currentTurn;
	private final ArrayList<Player> players;
	private final IBoard board;
	private final DiceRoller diceRoller;
	
	public TurnManager(ArrayList<Player> players, IBoard board, DiceRoller diceRoller) {
		this.players = players;
		this.board = board;
		this.diceRoller = diceRoller;
	}
	
	private void startNewTurn(Player player) {
		currentTurn = new Turn(player, board, diceRoller);
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}
	
	public void rollDice(int playerId) throws NotYourTurnException, MoveNotAllowedException {
		ensureIsPlayersTurn(playerId);
		getCurrentTurn().rollDice();
		changeTurnIfOver();
	}

	private void changeTurnIfOver() {
		if (getCurrentTurn().isOver()) {
			Player nextPlayer = getNextPlayer();
			startNewTurn(nextPlayer);
		}
	}

	private Player getNextPlayer() {
		int previousPlayerPosition = players.indexOf(getCurrentTurn().getPlayer());
		return players.get((previousPlayerPosition + 1) % players.size());
	}

	void ensureIsPlayersTurn(int playerId) throws NotYourTurnException {
		if (!currentTurn.isOfPlayer(playerId)) {
			throw new NotYourTurnException();
		}
	}
	
	public void moveCharacter(int playerId, int characterPos) throws NotYourTurnException, MoveNotAllowedException {
		ensureIsPlayersTurn(playerId);
		getCurrentTurn().moveCharacter(playerId, characterPos);
		changeTurnIfOver();
	}
	
	public int whoseTurn() {
		return getCurrentTurn().getPlayer().getPlayerId();
	}
}
