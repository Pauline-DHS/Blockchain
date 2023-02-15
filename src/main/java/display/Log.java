package display;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public final class Log extends JPanel {
	private static final long serialVersionUID = 1L;
	private static Log instance = null;
	
	private Log() {
		this.setLayout(new GridLayout(0, 1));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(new Color(200, 200, 200));
	}
	
	public static Log getInstance() {
		if(Log.instance == null) {
			Log.instance = new Log();
		}
		
		return Log.instance;
	}
	
	public void addInfo(String texte) {
		JLabel temp = new JLabel("[INFO] " + texte);
		temp.setForeground(new Color(0, 102, 0));
		//temp.setPreferredSize(new Dimension(500, 25));   
		this.add(temp);
		
		this.revalidate();
		this.repaint();
	}
	
	public void addError(String texte) {
		JLabel temp = new JLabel("[ERREUR] " + texte);
		temp.setForeground(new Color(153, 0, 0));
		//temp.setPreferredSize(new Dimension(500, 25));   
		this.add(temp);
		
		this.revalidate();
		this.repaint();
	}
	
	public void addDebug(String texte) {
		JLabel temp = new JLabel("[NOTIF] " + texte);
		temp.setForeground(new Color(0, 0, 255));
		//temp.setPreferredSize(new Dimension(500, 25));   
		this.add(temp);
		
		this.revalidate();
		this.repaint();
	}
 	
	public void addMineurInfo(String texte) {
		JLabel temp = new JLabel("[MINEUR] " + texte);
		temp.setForeground(new Color(102, 0, 153));
		//temp.setPreferredSize(new Dimension(500, 25));   
		this.add(temp);
		
		this.revalidate();
		this.repaint();
	}
}