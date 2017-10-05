package smims.networking.model;

import java.util.ArrayList;
import java.util.function.Consumer;

public class TurnManager {
	private Turn currentTurn;
	private final ArrayList<Player> players;
	private final IBoard board;
	private final IDiceRoller diceRoller;
	private Consumer<IPlayer> onNewTurn;
	
	public TurnManager(ArrayList<Player> players, IBoard board, IDiceRoller diceRoller) {
		if (players == null || players.size() == 0 || board == null || diceRoller == null) {
			throw new IllegalArgumentException();
		}
		
		this.players = players;
		this.board = board;
		this.diceRoller = diceRoller;
		startNewTurn(players.get(0));
	}
	
	public void registerOnNewTurn(Consumer<IPlayer> callback) {
		onNewTurn = callback;
	}
	
	private void startNewTurn(IPlayer player) {
		currentTurn = new Turn(player, board, diceRoller);
		if (onNewTurn != null) {
			onNewTurn.accept(player);
		}
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
			IPlayer nextPlayer = getNextPlayer();
			startNewTurn(nextPlayer);
		}
	}

	private IPlayer getNextPlayer() {
		int previousPlayerPosition = players.indexOf(getCurrentTurn().getPlayer());
		return players.get((previousPlayerPosition + 1) % players.size());
	}

	void ensureIsPlayersTurn(int playerId) throws NotYourTurnException {
		if (!currentTurn.isOfPlayer(playerId)) {
			throw new NotYourTurnException();
		}
	}
	
	public void moveCharacter(int playerId, Position position) throws NotYourTurnException, MoveNotAllowedException, NoSuchCharacterException {
		ensureIsPlayersTurn(playerId);
		getCurrentTurn().moveCharacter(position);
		changeTurnIfOver();
	}
	
	public int whoseTurn() {
		return getCurrentTurn().getPlayer().getPlayerId();
	}

	public TurnState getCurrentTurnState() {
		return currentTurn.getCurrentTurnState();
	}

	public int getRemainingRolls() {
		return getCurrentTurn().getRemainingRolls();
	}
}
