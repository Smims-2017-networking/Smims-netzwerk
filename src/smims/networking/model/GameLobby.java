package smims.networking.model;

import java.util.ArrayList;

public class GameLobby {
	private static final int BoardSize = 10;
	private final ArrayList<Player> players = new ArrayList<>();
	
	public void registerPlayer(Player player) {
		//player.greet();
		players.add(player);
	}
	
	public void unregisterPlayer(Player player) {
		//player.signOff();
		players.remove(player);
	}
	
	public boolean readyToStart() {
		for (Player player : players) {
			if (!player.wantsToStartGame())
				return false;
		}
		return true;
	}
	
	public Game startGame() {
		return new Game(players, BoardSize);
	}
}
