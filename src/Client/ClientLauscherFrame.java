package Client;

import java.awt.Dimension;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JLabel;

import smims.networking.model.Protokoll;

/**
 * Beschreibung
 * 
 * @version 2.0 vom 20.09.2010
 * @author Klaus Bovermann
 */
public class ClientLauscherFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4975721924388356123L;

	// Anfang Attribute
	private BufferedReader vomServer;

	private JLabel label = new JLabel();
	private JLabel Inhalt = new JLabel();

	// Ende Attribute

	public ClientLauscherFrame(String title, Socket s) {
		// Frame-Initialisierung
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				dispose();
			}
		});
		int frameWidth = 400;
		int frameHeight = 200;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		Panel cp = new Panel(null);
		add(cp);
		// Anfang Komponenten

		label.setBounds(40, 40, 55, 16);
		label.setText("Ausgabe");
		label.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(label);
		Inhalt.setBounds(0, 68, 387, 16);
		Inhalt.setText("?????");
		Inhalt.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(Inhalt);
		// Ende Komponenten

		setResizable(false);
		setVisible(true);
		try {
			this.vomServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
			kommunication();
		} catch (IOException e) {
			Inhalt.setText("SocketFehler!");
		}
	}

	public void kommunication() {
		String nachricht = "";
		do {
			try {
				nachricht = vomServer.readLine();
				this.Inhalt.setText("(ClientLauscher) Nachricht vom Server: " + nachricht);
			} catch (IOException io) {
				this.Inhalt.setText("(ClientLauscher) " + io.getMessage());
			}
		} while (nachricht != null);
		Inhalt.setText("Beendet!");
	}
}
