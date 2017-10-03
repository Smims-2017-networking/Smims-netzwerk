package smims.networking.model;

import java.util.ArrayList;

// Starts and stops games; tracks their progress
public class Game {
	final ArrayList<Player> players;

	public Game(ArrayList<Player> players) {
		this.players = players;
	}
	
	public void play() {
		playGame(players);
		signOffPlayers(players);
	}

	private static void signOffPlayers(ArrayList<Player> players) {
		// TODO Auto-generated method stub
	}

	private static void playGame(ArrayList<Player> players) {
		// TODO Auto-generated method stub
	}
}
