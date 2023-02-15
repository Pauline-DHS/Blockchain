package utilisateur;

import java.util.Vector;
import enchere.Produit;
import enchere.TableauEnchere;

public final class Vendeur extends Utilisateur implements Runnable {
	private Vector<Produit> _listeProduit;
	private Vector<Produit> _listeProduitEnVente;
	private boolean _active;
	
	public Vendeur(String nom, Produit...produits) {
		this._username = nom;
		this._solde = 0.0f;
		this._listeProduit = new Vector<Produit>();
		this._listeProduitEnVente = new Vector<Produit>();
		this._active = true;
		
		for(Produit p : produits) {
			this._listeProduit.add(p);
		}
	}
	
	public void removeProduit(Produit p) {
		synchronized(this._listeProduitEnVente) {
			this._listeProduitEnVente.remove(p);
		}
	}
	
	public void removeProduit(int id) {
		synchronized(this._listeProduitEnVente) {
			this._listeProduitEnVente.remove(id);
		}
	}

	@Override
	public void run() {
		while(this._active) {
			// Si la liste des produit qui ne sont pas encore en vente n'est pas vide, on créé l'enchère associée
			try {
				if(!this._listeProduit.isEmpty()) {
					TableauEnchere tabEnchere = TableauEnchere.getInstance();
					for(Produit p : this._listeProduit) {
						int random = (int) (Math.random() * 9);
						
						tabEnchere.addEnchere(this, p, 0.0f + (float)random, 0, 1 + random);
						this._listeProduitEnVente.add(p);
						this._listeProduit.remove(p);
					}
				}
				Thread.sleep(1500 + ((int)Math.random() * 2500));
			} catch (Exception e) {
				//Log.getInstance().addError(e.getMessage());
			}
		}
	}
}