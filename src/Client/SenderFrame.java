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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Ein Frame, der mit Hilfe des RechenClients Strings an den Server sendet.
 * 
 * @version 2.1 vom 8.8.2014
 * @author Klaus Bovermann
 */
public class SenderFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1856529007936600751L;
	// Anfang Attribute
	private JLabel label = new JLabel();
	private JTextField Eingabe = new JTextField();
	private JButton schicken = new JButton();

	private StartClient meinClient;

	// Ende Attribute

	public SenderFrame(String title, StartClient pRechenClient) {
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

		this.meinClient = pRechenClient;

		setResizable(false);
		setVisible(true);
	}

	public void schicken_ActionPerformed(ActionEvent evt) {
		String s = this.Eingabe.getText();
		if (this.meinClient.isConnected()) {
			this.meinClient.send(s);
		} else {
			this.meinClient.ausgabe("Nicht verbunden!");
		}
	}
}
