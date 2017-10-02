package smims.networking.model;

public class SpielServer extends Server{
	
	public SpielServer(int port) {
		super(port);
	}
	
	public void sendMessage(String clientIP, int clientPort, String message) {
		send(clientIP, clientPort, message);
	}
	
	public void sendMessageToAll(String message) {
		sendToAll(message);
	}
	
	@Override
	public void processMessage(String pClientIP, int pClientPort, String pMessage) {
		// TODO Auto-generated method stub
		
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
