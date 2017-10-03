package smims.networking.model;

import java.util.ArrayList;

public class SpielServer extends Server{
	
	//TODO: Objekt des Spiels erzeugen
	private GameLobby lobby;
	private int playerId = 0;
	private String[] ips;
	private boolean[] ready;
	public static final int SPIELERANZAHL = 4;
	
	public SpielServer(int port) {
		super(port);
		lobby = new GameLobby();
		ips = new String[SPIELERANZAHL];
		ready = new boolean[SPIELERANZAHL];
		for (int i = 0; i < ready.length; i++) {
			ready[i] = false;
		}
	}
	
	public void sendMessage(String clientIP, int clientPort, String message) {
		send(clientIP, clientPort, message);
	}
	
	public void sendMessageToAll(String message) {
		sendToAll(message);
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
			
			break;
		case Protokoll.CS_MoveCharacter:
			
			break;
		case Protokoll.CS_RollDice:
			
			break;
		case Protokoll.CS_WhoseTurn:
			
			break;
		case Protokoll.CS_Ready:
			int i = getPlayerId(pClientIP);
			ready[i] = true;
			int j = 0;
			boolean allReady = true;
			while(j < ready.length && allReady) {
				allReady = ready[j];
				j++;
			}
			if(allReady) {
				//TODO: Spielstart
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
