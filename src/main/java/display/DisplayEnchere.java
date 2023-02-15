package display;

import enchere.*;
import utilisateur.ListeUtilisateur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class DisplayEnchere extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private JLabel _prix;
	private JLabel _nom_produit;
	private JLabel _vendeur;
	private JLabel _duree;
	private JLabel _acheteur;
	private JButton _submit;
	private JTextField _zone_offre;
	private Enchere _enchere;
	
	public DisplayEnchere(Enchere enchere, Window window) {
		this.setLayout(new GridLayout(0, 1));
		this.setPreferredSize(new Dimension((int)(window.getPreferredSize().width * 0.6), window.getPreferredSize().height));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setBackground(new Color(180, 180, 180));
		this._enchere = enchere;
		
		// Produit
		this._nom_produit = new JLabel("Produit : " + enchere.getProduit().getNom() + " x" + enchere.getProduit().getQuantite());
		this.add(_nom_produit);
		
		// Vendeur
		this._vendeur = new JLabel("Vendeur : " + enchere.getVendeur().getUsername());
		this.add(this._vendeur);
		
		// prix
		this._prix = new JLabel("Prix : " + Float.toString(enchere.getPrix())  + "€");
		this.add(this._prix);
		
		// acheteur
		this._acheteur = new JLabel("Acheteur : Aucun");
		this.add(this._acheteur);
		
		// Temps restant
		this._duree = new JLabel("Temps restant : " + String.format("%02d:%02d:%02d", enchere.getTempsRestant().getSeconds() / 3600, (enchere.getTempsRestant().getSeconds() % 3600 ) / 60, (enchere.getTempsRestant().getSeconds() % 60)));
		this.add(_duree);
		
		// Input pour ajouter une enchere
		this._zone_offre = new JTextField();
		this._zone_offre.setFont(new Font("SansSerif", Font.BOLD, 24));
		this._zone_offre.setHorizontalAlignment(JTextField.CENTER);
		this.add(_zone_offre);
		
		// Bouton d'envoi
		this._submit = new JButton("Faire une offre");
		this._submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					float offre = Float.parseFloat(_zone_offre.getText());
					_zone_offre.setText("");
					_enchere.ajouterOffre(ListeUtilisateur.getMainUser(), offre);					
				} catch (Exception ec) {
					Log.getInstance().addError(ec.getMessage());
				}
			}
		});
		this.add(this._submit);
	}
	
	public void updateEnchere(Enchere ench) {
		this._enchere = ench;
		this._nom_produit.setText("Produit : " + _enchere.getProduit().getNom() + " x" + _enchere.getProduit().getQuantite());
		this._vendeur.setText("Vendeur : " + _enchere.getVendeur().getUsername());
		this._prix.setText("Prix : " + Float.toString(_enchere.getPrix())  + "€");
		this._duree.setText("Temps restant : " + String.format("%02d:%02d:%02d", _enchere.getTempsRestant().getSeconds() / 3600, (_enchere.getTempsRestant().getSeconds() % 3600 ) / 60, (_enchere.getTempsRestant().getSeconds() % 60)));
		if(_enchere.getAcheteur() != null) {				
			this._acheteur.setText("Acheteur : " + _enchere.getAcheteur().getUsername());
		} else {
			this._acheteur.setText("Acheteur : Aucun");
		}
		this._zone_offre.setText("");
	}

	@Override
	public void run() {
		while(true) {
			if(_enchere.getTempsRestant().getSeconds() == 0) {
				this._duree.setText("Temps restant : Terminé !");
			} else {
				this._duree.setText("Temps restant : " + String.format("%02d:%02d:%02d", _enchere.getTempsRestant().getSeconds() / 3600, (_enchere.getTempsRestant().getSeconds() % 3600 ) / 60, (_enchere.getTempsRestant().getSeconds() % 60)));
			}
			this._prix.setText("Prix : " + Float.toString(_enchere.getPrix()) + "€");
			
			if(_enchere.getAcheteur() != null) {				
				this._acheteur.setText("Acheteur : " + _enchere.getAcheteur().getUsername());
			}
			
			this.revalidate();
			this.repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//Log.getInstance().addError(e.getMessage());
			}
		}
	}
}