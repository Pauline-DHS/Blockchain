package display;

import utilisateur.ListeUtilisateur;
import enchere.*;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public final class Window extends JFrame{
    private static final long serialVersionUID = 1L;
	private int _width;
    private int _height;
    private String _name;
    
    private DisplayTableauEnchere _tabEnchere;
    private Thread _userInfo;
    private DisplayEnchere _displayedEnchere = null;
    private Thread _displayedEnchereThread = null;
    private static Window _instance = null;
    
    private Window(int width, int height, String name) {
    	// Données de base de la fenêtre
        this._width = width;
        this._height = height;
        this._name = name;
        
        // Définition du style
        this.setTitle(_name);
        this.setSize(_width, _height);
        this.setLayout(new BorderLayout());
        
        //Fermer le programme a la fermeture de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tableau d'enchère
        this._tabEnchere = new DisplayTableauEnchere(this);
        JScrollPane scrollPaneTabEnchere = new JScrollPane(this._tabEnchere);
        scrollPaneTabEnchere.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPaneTabEnchere.setBackground(new Color(225, 225, 225));

        // Log
        JScrollPane scrollPaneLog = new JScrollPane(Log.getInstance());
        scrollPaneLog.setPreferredSize(new Dimension(500, 150));
        scrollPaneLog.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPaneLog.setBackground(new Color(200, 200, 200));
        
        // Event listener sur le log pour l'auto scroll
        scrollPaneLog.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
            public void adjustmentValueChanged(AdjustmentEvent e) {  
                e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
            }
        });
       
        // Donnée de l'utilisateur de l'application
        DisplayUserInfo userInfo = new DisplayUserInfo(ListeUtilisateur.getMainUser());
        this._userInfo = new Thread(userInfo);
        this._userInfo.start();
		
		// Ajouter le nouveau tableau à la même place que celui d'avant
		this.add(scrollPaneLog, BorderLayout.SOUTH);
		this.add(userInfo, BorderLayout.NORTH);
		this.add(scrollPaneTabEnchere,BorderLayout.CENTER);

        this.setVisible(true);
        Log.getInstance().addInfo("Initialisation de la fenêtre");
    }
    
    public static Window getInstance() {
		if(Window._instance == null) {
			Window._instance = new Window(1600, 900, "Enchere");
		}    
		return Window._instance;
    }
    
    public DisplayTableauEnchere getDisplayTableauEnchere() {
    	return this._tabEnchere;
    }
    
	public void setDisplayedEnchere(Enchere enchere) {
    	if(this._displayedEnchereThread == null) {
    		this._displayedEnchere = new DisplayEnchere(enchere, this);
    		this._displayedEnchereThread = new Thread(_displayedEnchere);
        	this._displayedEnchereThread.start();
        	this.add(this._displayedEnchere, BorderLayout.WEST);
    	} else {
    		this._displayedEnchere.updateEnchere(enchere);
    	}
    }
    
    public DisplayEnchere getDisplayEnchere() {
    	return this._displayedEnchere;
    }
}