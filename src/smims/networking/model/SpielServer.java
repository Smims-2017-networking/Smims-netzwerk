package smims.networking.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

import com.google.gson.Gson;

import smims.networking.model.Position.BoardPositionBuilder;
import smims.networking.model.Position.StartingPositionBuilder;
import smims.networking.testing.LambdaDiceRoller;

public class SpielServer extends Server {

	// TODO: Objekt des Spiels erzeugen
	private GameLobby lobby;
	private int playerId = 0;
	private String[] ips;
	private int spieleranzahl, boardgroesse;
	private Game game;
	private BoardPositionBuilder bpb;
	private int diceResult;
	private static final boolean WUERFELBETUPPEN = true;
	private boolean wuerfelresult;
	private ArrayList<NetworkingBot> bots;
	private ArrayList<Player> botPlayers;

	public static void main(String[] args) {
		new SpielServer(4242, 3, 4, 2);
	}

	public SpielServer(int port, int pSpieleranzahl, int pBoardgroesse, int botanzahl) {
		super(port);
		botPlayers = new ArrayList<>();
		bots = new ArrayList<>();
		for (int i = pSpieleranzahl - botanzahl; i < pSpieleranzahl; i++) {
			botPlayers.add(new Player(i));
		}
		if (WUERFELBETUPPEN) {
			lobby = new GameLobby(pSpieleranzahl, botanzahl, new LambdaDiceRoller(new Function<Integer, Integer>() {
				@Override
				public Integer apply(Integer t) {
					Random random = new Random();
					if (wuerfelresult) {
						wuerfelresult = false;
						return diceResult;
					} else {
						return random.nextInt(6) + 1;
					}
				}
			}));
		} else {
			lobby = new GameLobby(pSpieleranzahl);
		}
		spieleranzahl = pSpieleranzahl;
		boardgroesse = pBoardgroesse;
		if (boardgroesse < spieleranzahl) {
			boardgroesse = spieleranzahl;
		}
		ips = new String[spieleranzahl];
	}

	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		// TODO: Abfangen von NullPointerException, wenn Spiel noch nicht läuft
		// TODO: NullPointerException bei fehlernder Wertemitlieferung (array[1]
		// ==
		// null)
		System.out.println(pMessage);
		String[] array = pMessage.split(Protokoll.Splitter);
		Outer: switch (array[0]) {
		case Protokoll.CS_Chat:
			String message6 = Protokoll.SC_Chat + Protokoll.Splitter + getPlayerId(pClientIP) + Protokoll.Splitter
					+ array[1];
			System.out.println(message6);
			sendToAll(message6);
			break;
		case Protokoll.CS_Welcome:
			send(pClientIP, pClientPort, "Welkom speler " + getPlayerId(pClientIP) + "! Wij wensen je een vrolijke spel!");
			break;
		case Protokoll.CS_GetBoard: {
			if (game != null) {
				Gson gson = new Gson();
				String message = Protokoll.SC_Board + Protokoll.Splitter + gson.toJson(game.getBoard());
				sendMessage(pClientIP, pClientPort, message);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		}

		case Protokoll.CS_GetDiceResult:
			if (game != null) {
				String message = Protokoll.SC_DiceResult + Protokoll.Splitter + game.getDiceResult();
				sendMessage(pClientIP, pClientPort, message);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_MoveCharacter:
			if (game != null) {
				int pos;
				StartingPositionBuilder spb = game.getStartingPositionBuilder(getPlayerId(pClientIP));
				String message5;
				try {
					pos = Integer.parseInt(array[2]);
				} catch (Exception e) {
					String message1 = Protokoll.SC_ParseError;
					sendMessage(pClientIP, pClientPort, message1);
					break;
				}
				Position position;
				switch (array[1]) {
				case Protokoll.House:
					position = spb.atHousePosition(pos);
					break;
				case Protokoll.Base:
					position = spb.inBase();
					break;
				case Protokoll.Board:
					position = spb.atPosition(pos);
					break;
				case Protokoll.Starting:
					position = spb.atStartingPosition();
					break;
				default:
					String message1 = Protokoll.SC_ParseError;
					sendMessage(pClientIP, pClientPort, message1);
					break Outer;
				}
				try {
					message5 = Protokoll.SC_MoveOk;
					game.moveCharacter(getPlayerId(pClientIP), position);
				} catch (MoveNotAllowedException e) {
					message5 = Protokoll.SC_MoveNotAllowed;
				} catch (NotYourTurnException e) {
					message5 = Protokoll.SC_NotYourTurn;
				} catch (NoSuchCharacterException e) {
					message5 = Protokoll.SC_NoSuchCharacter;
				}
				sendMessage(pClientIP, pClientPort, message5);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_GetTurnState:
			if (game != null) {
				String message3 = Protokoll.SC_TurnState + Protokoll.Splitter + game.getCurrentTurnState();
				sendMessage(pClientIP, pClientPort, message3);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_RollDice:
			if (game != null) {
				String message2;
				try {
					game.rollDice(getPlayerId(pClientIP));
					message2 = Protokoll.SC_DiceResult + Protokoll.Splitter + game.getDiceResult();
				} catch (MoveNotAllowedException e) {
					message2 = Protokoll.SC_MoveNotAllowed;
				} catch (NotYourTurnException e) {
					message2 = Protokoll.SC_NotYourTurn;
				}
				sendMessage(pClientIP, pClientPort, message2);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_WhoseTurn:
			if (game != null) {
				String message1 = Protokoll.SC_PlayerTurn + Protokoll.Splitter + game.whoseTurn();
				sendMessage(pClientIP, pClientPort, message1);
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_Ready:
			if (game == null) {
				int j = getPlayerId(pClientIP);
				lobby.getPlayerAt(j).makePlayerWantToStartGame();
				if (lobby.readyToStart()) {
					game = lobby.startGame(boardgroesse, botPlayers);
					for (Player player : botPlayers) {
						bots.add(new NetworkingBot(player, game));
					}
					game.registerTurnChangedCallback(new Consumer<IPlayer>() {
						@Override
						public void accept(IPlayer t) {
							System.out.println("thread");
							if(game.getWinner() != null){
								sendToAll(Protokoll.SC_Winner + Protokoll.Splitter + game.getWinner().getPlayerId());
								sendToAll(Protokoll.CS_GetBoard + Protokoll.Splitter + game.getBoard());
								close();
							}
							sendToAll(Protokoll.SC_PlayerTurn + Protokoll.Splitter + game.whoseTurn());
							int turn = game.whoseTurn();
							if(turn == spieleranzahl - bots.size()) {
								executeBots();
							}
						}
					});
					bpb = Position.on(game.getBoard().getBoardDescriptor());
					System.out.println(Protokoll.SC_GameStarts);
					sendToAll(Protokoll.SC_GameStarts);
				}
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		case Protokoll.CS_GetOwnPlayerId:
			String message4 = Protokoll.SC_OwnPlayerId + Protokoll.Splitter + getPlayerId(pClientIP);
			sendMessage(pClientIP, pClientPort, message4);
			break;
		case Protokoll.CS_GetThrowsLeft:
			String message1;
			if (game != null) {
				message1 = Protokoll.SC_ThrowsLeft + Protokoll.Splitter + game.getRemainingRolls();
			} else {
				message1 = Protokoll.SC_Error;
			}
			sendMessage(pClientIP, pClientPort, message1);
			break;
		case Protokoll.CS_FakeDiceResult:
			if (game != null) {
				if (WUERFELBETUPPEN) {
					try {
						diceResult = Integer.parseInt(array[1]);
						wuerfelresult = true;
					} catch (Exception e) {
						String message7 = Protokoll.SC_ParseError;
						sendMessage(pClientIP, pClientPort, message7);
						break;
					}
				} else {
					sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
				}
			} else {
				sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			}
			break;
		default:
			sendMessage(pClientIP, pClientPort, Protokoll.SC_Error);
			break;
		}

	}
	
	private void executeBots() {
		System.out.println(Thread.currentThread().getName());
		try {
			for (NetworkingBot networkingBot : bots) {
				networkingBot.makeTurn();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMessage(String ip, int port, String message) {
		System.out.println(message);
		send(ip, port, message);
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
		} else {
			send(pClientIP, pClientPort, Protokoll.SC_ServerDicht);
			closeConnection(pClientIP, pClientPort);
		}
	}

	@Override
	public void processClosingConnection(String pClientIP, int pClientPort) {
		// TODO Auto-generated method stub

	}

}
