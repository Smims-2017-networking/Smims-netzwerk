package smims.networking.model;

import java.util.ArrayList;
import java.util.Collection;

public class GameCreator {
	public Game startNewGame(int nPlayers) {
		GameLobby lobby = new GameLobby();
		Collection<Player> players = new ArrayList<>();
		
		for (int i = 0; i < nPlayers; ++i) {
			players.add(new Player());
		}
		
		for (Player player : players) {
			lobby.registerPlayer(player);
			player.makePlayerWantToStartGame();
		}
		
		return lobby.startGame();
	}
}
