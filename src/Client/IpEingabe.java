package Client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 04.10.2017
  * @author 
  */

public class IpEingabe extends Frame {

/**
	 * 
	 */
	private static final long serialVersionUID = 8889400361054584038L;
	
// Anfang Attribute
  private JLabel lIP = new JLabel();
  private JLabel lPort = new JLabel();
  private JTextField jTextFieldIp = new JTextField();
  private JTextField jTextFieldPort = new JTextField();
  private JButton bVerbinden = new JButton();
  private JLabel lBitteServerIpundPorteingeben1 = new JLabel();
  // Ende Attribute
  
  public IpEingabe() { 
    // Frame-Initialisierung
    super();
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) { dispose(); }
    });
    int frameWidth = 563; 
    int frameHeight = 237;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("ClientGUI");
    setResizable(false);
    Panel cp = new Panel(null);
    add(cp);
    // Anfang Komponenten
    
    lIP.setBounds(32, 80, 35, 33);
    lIP.setText("IP:");
    cp.add(lIP);
    lPort.setBounds(264, 88, 59, 25);
    lPort.setText("Port:");
    cp.add(lPort);
    jTextFieldIp.setBounds(80, 88, 145, 25);
    jTextFieldIp.setText("localhost");
    cp.add(jTextFieldIp);
    jTextFieldPort.setBounds(328, 88, 89, 25);
    jTextFieldPort.setText("4242");
    cp.add(jTextFieldPort);
    bVerbinden.setBounds(328, 136, 161, 41);
    bVerbinden.setText("verbinden");
    bVerbinden.setMargin(new Insets(2, 2, 2, 2));
    bVerbinden.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        bVerbinden_ActionPerformed(evt);
      }
    });
    cp.add(bVerbinden);
    lBitteServerIpundPorteingeben1.setBounds(24, 8, 315, 41);
    lBitteServerIpundPorteingeben1.setText("Bitte Server Ip und Port eingeben:");
    cp.add(lBitteServerIpundPorteingeben1);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public ClientGUI
  
  // Anfang Methoden
  
  
  public void bVerbinden_ActionPerformed(ActionEvent evt) {
	  new SpielClient(jTextFieldIp.getText(), Integer.parseInt(jTextFieldPort.getText()));

  } // end of bVerbinden_ActionPerformed

  public static void main(String[] args) {
		new IpEingabe();
	}
  
  // Ende Methoden
} // end of class ClientGUI
