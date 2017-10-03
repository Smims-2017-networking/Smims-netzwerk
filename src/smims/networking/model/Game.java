package smims.networking.model;

import java.util.ArrayList;

// Starts and stops games; tracks their progress
public class Game {
	final ArrayList<Player> players;
	final GameState gameState;

	public Game(ArrayList<Player> players, GameState gameState) {
		this.players = players;
		this.gameState = gameState;
	}
	
	
}
