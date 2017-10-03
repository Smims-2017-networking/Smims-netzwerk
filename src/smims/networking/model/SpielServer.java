package smims.networking.model;

import java.util.ArrayList;

public class SpielServer extends Server {

	// TODO: Objekt des Spiels erzeugen
	private GameLobby lobby;
	private int playerId = 0;
	private String[] ips;
	public static final int SPIELERANZAHL = 4;
	private Game game;

	public SpielServer(int port) {
		super(port);
		lobby = new GameLobby();
		ips = new String[SPIELERANZAHL];
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		String[] array = pMessage.split(Protokoll.Splitter);
		switch (array[0]) {
		case Protokoll.CS_Welcome:
			lobby.registerPlayer(new Player(playerId));
			ips[playerId] = pClientIP;
			playerId++;
			send(pClientIP, pClientPort, Protokoll.SC_Welcome);
			break;
		case Protokoll.CS_GetBoard:

			break;
		case Protokoll.CS_GetDiceResult:
			String message = Protokoll.SC_SendDiceResult + Protokoll.Splitter + game.getDiceResult();
			send(pClientIP, pClientPort, message);
			break;
		case Protokoll.CS_MoveCharacter:
			
			break;
		case Protokoll.CS_RollDice:
			String message2;
			try {
				game.rollDice(getPlayerId(pClientIP));
				message2 = Protokoll.SC_SendDiceResult + Protokoll.Splitter + game.getDiceResult();
			} catch (Exception e) {
				message2 = Protokoll.SC_SendDiceResult + Protokoll.Splitter + game.getDiceResult();
			}
			send(pClientIP, pClientPort, message2);
			break;
		case Protokoll.CS_WhoseTurn:
			String message1 = Protokoll.SC_PlayerTurn + Protokoll.Splitter + game.whoseTurn();
			send(pClientIP, pClientPort, message1);
			break;
		case Protokoll.CS_Ready:
			int j = getPlayerId(pClientIP);
			lobby.getPlayerAt(j).makePlayerWantToStartGame();
			if (lobby.readyToStart()) {
				game = lobby.startGame();
				sendToAll(Protokoll.SC_GameStarts);
			}
			break;
		default:
			break;
		}
	}

	private int getPlayerId(String ip) {
		int i = 0;
		while (!ips[i].equals(ip) && i < SPIELERANZAHL) {
			i++;
		}
		return i;
	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub

	}

}
