package smims.networking.model;

public class Turn {
	private final Player player;
	private final IBoard board;
	private final DiceRoller diceRoller;
	private int rolledCount = 0;
	private int mustRollCount = 0;
	private TurnState turnState;
	
	
	public Turn(Player player, IBoard board, DiceRoller diceRoller) {
		this.player = player;
		this.board = board;
		this.diceRoller = diceRoller;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void moveCharacter(int characterPos) {
		
	}
	
	public void rollDice() throws MoveNotAllowedException {
		EnsurePlayerCanRollDice();
	}

	private void EnsurePlayerCanRollDice() throws MoveNotAllowedException {
		if(!(turnState == TurnState.ExpectRoll) && rolledCount < mustRollCount) {
			throw new MoveNotAllowedException();
		}
	}

	public boolean isOfPlayer(int playerId) {
		return getPlayer().getPlayerId() == playerId;
	}
	
	boolean isOver() {
		return turnState == TurnState.Finished;
	}
	
	private void doThreeTurns(Player p) throws Exception {
		for (int i = 0; i < 3; i++) {
			diceRoller.rollDice();
			if (gameState.myDice.getResult() == 6) {
				turnWithoutRoll(p);
				break;
			}
		}
	}
}
