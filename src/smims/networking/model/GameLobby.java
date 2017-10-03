package smims.networking.model;

import java.util.ArrayList;

public class GameLobby {
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
	
	public Player getPlayerAt(int playerId) {
		return players.get(playerId);
	}
	
	public Game startGame(int boardgroesse) {
		return new Game(players, boardgroesse);
	}
}
