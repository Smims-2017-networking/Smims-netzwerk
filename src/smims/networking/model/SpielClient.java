package smims.networking.model;

import Client.Client;

public class SpielClient extends Client {

	public SpielClient(String pIPAdresse, int pPortNr) {
		super(pIPAdresse, pPortNr);
	}

	@Override
	public void processMessage(String pMessage) {
		// TODO Auto-generated method stub
		
	}
	
	public void sendMessage(String message) {
		send(message);
	}
	
}
