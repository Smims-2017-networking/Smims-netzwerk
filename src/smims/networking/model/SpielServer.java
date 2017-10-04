package smims.networking.model;

import com.google.gson.Gson;

public class SpielServer extends Server {

	// TODO: Objekt des Spiels erzeugen
	private GameLobby lobby;
	private int playerId = 0;
	private String[] ips;
	private int spieleranzahl, boardgroesse;
	private Game game;

	public static void main(String[] args) {
		new SpielServer(4242, 3, 3);
	}

	public SpielServer(int port, int pSpieleranzahl, int pBoardgroesse) {
		super(port);
		lobby = new GameLobby(pSpieleranzahl);
		spieleranzahl = pSpieleranzahl;
		boardgroesse = pBoardgroesse;
		if (boardgroesse < spieleranzahl) {
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
		case Protokoll.CS_GetBoard: {
			Gson gson = new Gson();
			String message = Protokoll.SC_Board + Protokoll.Splitter + gson.toJson(game.getBoard());
			System.out.println(message);
			send(pClientIP, pClientPort, message);
			break;
		}

		case Protokoll.CS_GetDiceResult:
			String message = Protokoll.SC_DiceResult + Protokoll.Splitter + game.getDiceResult();
			System.out.println(message);
			send(pClientIP, pClientPort, message);
			break;
		case Protokoll.CS_MoveCharacter:
			int pos;
			String message5;
			try {
				pos = Integer.parseInt(array[1]);
			} catch (NumberFormatException e) {
				send(pClientIP, pClientPort, Protokoll.SC_Exception + Protokoll.Splitter + "parse");
				break;
			}
			try {
				message5 = Protokoll.SC_MoveOk;
				game.moveCharacter(getPlayerId(pClientIP), pos);
			} catch (MoveNotAllowedException e) {
				message5 = Protokoll.SC_MoveNotAllowed;
			} catch (NotYourTurnException e) {
				message5 = Protokoll.SC_NotYourTurn;
			} catch (NoSuchCharacterException e) {
				message5 = Protokoll.SC_NoSuchCharacter;
			}
			System.out.println(message5);
			send(pClientIP, pClientPort, message5);
			break;
		case Protokoll.CS_GetTurnState:
			String message3 = Protokoll.SC_TurnState + Protokoll.Splitter + game.getCurrentTurnState();
			System.out.println(message3);
			send(pClientIP, pClientPort, message3);
			break;
		case Protokoll.CS_RollDice:
			String message2;
			try {
				game.rollDice(getPlayerId(pClientIP));
				message2 = Protokoll.SC_DiceResult + Protokoll.Splitter + game.getDiceResult();
			} catch (MoveNotAllowedException e) {
				message2 = Protokoll.SC_MoveNotAllowed;
			} catch (NotYourTurnException e) {
				message2 = Protokoll.SC_NotYourTurn;
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
			if (game == null) {
				int j = getPlayerId(pClientIP);
				lobby.getPlayerAt(j).makePlayerWantToStartGame();
				if (lobby.readyToStart()) {
					game = lobby.startGame(boardgroesse);
					System.out.println(Protokoll.SC_GameStarts);
					sendToAll(Protokoll.SC_GameStarts);
				}
			} else {
				System.out.println(Protokoll.SC_Error);
				send(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_GetOwnPlayerId:
			String message4 = Protokoll.SC_OwnPlayerId + Protokoll.Splitter + getPlayerId(pClientIP);
			System.out.println(message4);
			send(pClientIP, pClientPort, message4);
			break;
		case Protokoll.CS_GetThrowsLeft:

			break;
		default:
			System.out.println(Protokoll.SC_Error);
			send(pClientIP, pClientPort, Protokoll.SC_Error);
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
