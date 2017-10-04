package smims.networking.model;

import java.util.ArrayList;

public class GameLobby {
	private final ArrayList<Player> players = new ArrayList<>();
	private int spieleranzahl;

	public GameLobby(int spieleranzahl) {
		this.spieleranzahl = spieleranzahl;
	}

	public void registerPlayer(Player player) {
		// player.greet();
		players.add(player);
	}

	public void unregisterPlayer(IPlayer player) {
		// player.signOff();
		players.remove(player);
	}

	public boolean readyToStart() {
		if (players.size() == spieleranzahl) {
			for (IPlayer player : players) {
				if (!player.wantsToStartGame())
					return false;
			}
			return true;
		} else {
			return false;
		}
		
	}

	public IPlayer getPlayerAt(int playerId) {
		return players.get(playerId);
	}

	public Game startGame(int boardgroesse) {
		return new Game(players, boardgroesse);
	}
}
