package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import enchere.*;
import javax.swing.JPanel;

public final class LabelEnchere extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private JLabel _nom_produit;
	private JLabel _vendeur;
	private JLabel _prix;
	private JLabel _acheteur;
	private JLabel _duree;
	private Enchere _enchere;

	// Pour afficher les légendes des colonnes
	public LabelEnchere() {
		this.add(new JLabel("Produit"));
        this.add(new JLabel("Vendeur"));
        this.add(new JLabel("Dernière offre"));
        this.add(new JLabel("Acheteur"));
        this.add(new JLabel("Temps restant"));
        this.add(new JLabel("Voir l'enchère"));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new GridLayout(0, 6));
        this.setBackground(new Color(225, 225, 225));
	}
	
	// Afficher une enchère dans le tableau
	public LabelEnchere(Enchere enchere, Window window) {
		this.setLayout(new GridLayout(0, 6));
		this.setPreferredSize(new Dimension((int)(window.getPreferredSize().width * 0.6), 25));   
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(new Color(225, 225, 225));
		this._enchere = enchere;
		
		this._nom_produit = new JLabel(enchere.getProduit().getNom() + " x" + enchere.getProduit().getQuantite());
		this.add(_nom_produit);
		
		this._vendeur = new JLabel(enchere.getVendeur().getUsername());
		this.add(this._vendeur);
		
		this._prix = new JLabel(Float.toString(enchere.getPrix()) + "€");
		this.add(this._prix);
		
		this._acheteur = new JLabel("Aucun");
		this.add(this._acheteur);
		
		this._duree = new JLabel(String.format("%02d:%02d:%02d", enchere.getTempsRestant().getSeconds() / 3600, (enchere.getTempsRestant().getSeconds() % 3600 ) / 60, (enchere.getTempsRestant().getSeconds() % 60)));
		this.add(_duree);
        
        JButton button = new JButton("Voir l'enchère");
        button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setDisplayedEnchere(enchere);
				window.revalidate();
				window.repaint();
			}
        });
        
        this.add(button);
	}

	@Override
	public void run() {
		while(true) {
			if(_enchere.getTempsRestant().getSeconds() == 0) {
				this._duree.setText("Terminé !");
			} else {
				this._duree.setText(String.format("%02d:%02d:%02d", _enchere.getTempsRestant().getSeconds() / 3600, (_enchere.getTempsRestant().getSeconds() % 3600 ) / 60, (_enchere.getTempsRestant().getSeconds() % 60)));
			}
			
			this._prix.setText(Float.toString(_enchere.getPrix()) + "€");
			
			if(this._enchere.getAcheteur() != null) {
				this._acheteur.setText(this._enchere.getAcheteur().getUsername());
			}
			
			this.revalidate();
			this.repaint();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				//Log.getInstance().addError(e.getMessage());
			}
		}
	}
}