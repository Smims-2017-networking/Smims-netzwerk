package smims.networking.model;

import java.util.ArrayList;

public class SpielServer extends Server {

	// TODO: Objekt des Spiels erzeugen
	private GameLobby lobby;
	private int playerId = 0;
	private String[] ips;
	private int spieleranzahl, boardgroesse;
	private Game game;
	
	public static void main(String[] args) {
		new SpielServer(4242, 2, 2);
	}

	public SpielServer(int port, int pSpieleranzahl, int pBoardgroesse) {
		super(port);
		lobby = new GameLobby();
		spieleranzahl = pSpieleranzahl;
		boardgroesse = pBoardgroesse;
		if(boardgroesse < spieleranzahl) {
			boardgroesse = spieleranzahl;
		}
		ips = new String[spieleranzahl];
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		System.out.println(pMessage);
		String[] array = pMessage.split(Protokoll.Splitter);
		switch (array[0]) {
		case Protokoll.CS_Welcome:
			
			break;
		case Protokoll.CS_GetBoard:
			
			break;
		case Protokoll.CS_GetDiceResult:
			String message = Protokoll.SC_SendDiceResult + Protokoll.Splitter + game.getDiceResult();
			System.out.println(message);
			send(pClientIP, pClientPort, message);
			break;
		case Protokoll.CS_MoveCharacter:
			int pos;
			try {
				pos = Integer.parseInt(array[1]);
			} catch (NumberFormatException e) {
				
				break;
			}
			try {
				game.moveCharacter(getPlayerId(pClientIP), pos);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			break;
		case Protokoll.CS_RollDice:
			String message2;
			try {
				game.rollDice(getPlayerId(pClientIP));
				message2 = Protokoll.SC_SendDiceResult + Protokoll.Splitter + game.getDiceResult();
			} catch (Exception e) {
				message2 = Protokoll.SC_SendDiceResult + Protokoll.Splitter + Protokoll.SC_Exception;
			}
			System.out.println(message2);
			send(pClientIP, pClientPort, message2);
			break;
		case Protokoll.CS_WhoseTurn:
			String message1 = Protokoll.SC_PlayerTurn + Protokoll.Splitter + game.whoseTurn();
			System.out.println(message1);
			send(pClientIP, pClientPort, message1);
			break;
		case Protokoll.CS_Ready:
			int j = getPlayerId(pClientIP);
			lobby.getPlayerAt(j).makePlayerWantToStartGame();
			if (lobby.readyToStart()) {
				game = lobby.startGame(boardgroesse);
				System.out.println(Protokoll.SC_GameStarts);
				sendToAll(Protokoll.SC_GameStarts);
			}
			break;
		default:
			break;
		}
	}

	private int getPlayerId(String ip) {
		int i = 0;
		while (!ips[i].equals(ip) && i < spieleranzahl) {
			i++;
		}
		return i;
	}

	@Override
	public void processNewConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub
		if (playerId < spieleranzahl) {
			lobby.registerPlayer(new Player(playerId));
			ips[playerId] = pClientIP;
			playerId++;
			System.out.println(Protokoll.SC_Welcome);
			send(pClientIP, pClientPort, Protokoll.SC_Welcome);
		}
	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub

	}

}
