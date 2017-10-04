package Client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Beschreibung
 * 
 * @version 1.0 vom 20.09.2010
 * @author Klaus Bovermann
 */
public class ClientSenderFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6682979272885862251L;
	// Anfang Attribute
	private JLabel label = new JLabel();
	private JTextField Eingabe = new JTextField();
	private PrintWriter zumServer;
	private JButton schicken = new JButton();

	private Socket socketZumServer;

	// Ende Attribute

	public ClientSenderFrame(String title, Socket s) {
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

		label.setBounds(8, 48, 51, 16);
		label.setText("Eingabe");
		label.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(label);
		Eingabe.setBounds(8, 88, 121, 24);
		Eingabe.setText("");
		cp.add(Eingabe);
		schicken.setBounds(176, 88, 99, 25);
		schicken.setText("Abschicken!");
		schicken.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				schicken_ActionPerformed(evt);
			}
		});
		cp.add(schicken);
		// Ende Komponenten

		setResizable(false);
		setVisible(true);
		this.socketZumServer = s;
		try {
			this.zumServer = new PrintWriter(this.socketZumServer.getOutputStream(), true);
		} catch (IOException e) {
			Eingabe.setText("SockertFehler!");
		}
	}

	public void schicken_ActionPerformed(ActionEvent evt) {
		String s = this.Eingabe.getText();
		if (!this.socketZumServer.isClosed()) {
			zumServer.println(s);
		} else {
			Eingabe.setText("Socket nicht mehr verbunden!");
		}
	}
}
