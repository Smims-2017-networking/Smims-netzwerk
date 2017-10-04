package Client;

import java.net.*;
import java.io.*;

/**
 * The Class Client.
 */
public class Client {

	private Socket socket;

	public void starteLauscher() {
		new ClientLauscherFrame("Lauscher", socket);
	}

	public void starteSender() {
		new ClientSenderFrame("Sender", socket);
	}
	
	public Client()
	{
		new Client("localhost", 1111);
	}

	public Client(String serverIP, int serverPort) {
		try {
			InetAddress addr = InetAddress.getByName(serverIP);
			System.out.println("(Client Main) HostName: " + addr.getHostName());
			System.out.println("(Client Main) HostAddr: " + addr.getHostAddress());
			socket = new Socket(serverIP, serverPort);
			starteSender();
			starteLauscher();
		} catch (UnknownHostException e) {
			System.out.println("(Client Main) unknown Host");
		} catch (IOException io) {
			System.out.println("(Client Main)   Socket-Fehler io");
			System.out.println("(Client Main) " + io.getMessage());
		}

		try {
			socket.close();
		} catch (IOException io) {
			System.out.println("(Client Main)   Socket-Fehler io");
			System.out.println("(Client Main) " + io.getMessage());
		}
		System.out.println("(Client Main) Ich bin am Ende!");
	}

	public static void main(String[] args) {
		new IpEingabe();
	}
}
