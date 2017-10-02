package smims.networking.model;

import java.util.ArrayList;

public class GameLobby {
	final ArrayList<IPlayerChoice> players = new ArrayList<>();
	
	public void registerPlayer(IPlayerChoice player) {
		player.greet();
		players.add(player);
	}
	
	public void unregisterPlayer(IPlayerChoice player) {
		player.signOff();
		players.remove(player);
	}
	
	public boolean readyToStart() {
		for (IPlayerChoice player : players) {
			if (!player.wantsToStartGame())
				return false;
		}
		return true;
	}
	
	public Game startGame() {
		return new Game(players);
	}
}
