package Client;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * Ein Frame, der in einem JLabel Ausgaben machen kann. Der Client benutzt
 * spaeter diesen Frame, um die Antworten, die er vom Server bekommt,
 * anzuzeigen.
 * 
 * @version 2.1 vom 8.8.2014
 * @author Klaus Bovermann
 */
public class AusgabeFrame extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8156138711427132281L;
	// Anfang Attribute

	/**
	 * 
	 */
	private JLabel label;
	private JLabel Inhalt;

	// Ende Attribute

	public AusgabeFrame(String title) {
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
		label = new JLabel();
		label.setBounds(40, 40, 55, 16);
		label.setText("Ausgabe");
		label.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(label);

		Inhalt = new JLabel();
		Inhalt.setBounds(0, 68, 387, 16);
		Inhalt.setText("?????");
		Inhalt.setFont(new Font("MS Sans Serif", Font.PLAIN, 13));
		cp.add(Inhalt);
		// Ende Komponenten

		setResizable(false);
		setVisible(true);
	}

	/**
	 * @return the inhalt
	 */
	public JLabel getInhaltLabel() {
		return Inhalt;
	}
}
