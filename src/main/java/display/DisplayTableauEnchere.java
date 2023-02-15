package display;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Vector;

import enchere.Enchere;

public final class DisplayTableauEnchere extends JPanel{
	private static final long serialVersionUID = 1L;
	private Vector<Thread> _list_enchere = new Vector<Thread>();
	
    public DisplayTableauEnchere(Window window) {
    	this.setLayout(new GridLayout(0, 1));
    	this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	this.setBackground(new Color(225, 225, 225));
        
        // Ajouter les légendes
        this.add(new LabelEnchere());
    }
    
    public void addLabelEnchere(Enchere enchere, Window window) {
    	synchronized(this._list_enchere) {    		
    		LabelEnchere temp = new LabelEnchere(enchere, window);
    		this.add(temp);
    		
    		Thread temp_thread = new Thread(temp);
    		_list_enchere.add(temp_thread);
    		temp_thread.start();
    		
    		this.revalidate();
    		this.repaint();
    	}
    }
}