package smims.networking.model;

import java.util.ArrayList;

// Starts and stops games; tracks their progress
public class Game {
	final ArrayList<IPlayerChoice> players;

	public Game(ArrayList<IPlayerChoice> players) {
		this.players = players;
	}
	
	public void play() {
		playGame(players);
		signOffPlayers(players);
	}

	private static void signOffPlayers(ArrayList<IPlayerChoice> players) {
		// TODO Auto-generated method stub
	}

	private static void playGame(ArrayList<IPlayerChoice> players) {
		// TODO Auto-generated method stub
	}
}
