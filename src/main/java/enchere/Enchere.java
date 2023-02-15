package enchere;

import io.reactivex.Observable;
import java.time.Duration;
import java.util.Vector;

import blockchain.Blockchain;

import display.Log;
import utilisateur.Acheteur;
import utilisateur.Vendeur;

public final class Enchere implements Runnable {
	private float _prix_mini;
	private Vendeur _vendeur;
	private Produit _produit;
	private Offre _offreActuelle;
	private Duration _tempsRestant;
	private Vector<Acheteur> _listeAcheteur;
	private boolean _active;	

	public Enchere(Vendeur vendeur, Produit produitAVendre, float prix_mini, int heureAvantFin, int minuteAvantFin) {
		this._vendeur = vendeur;
		this._prix_mini = prix_mini;
		this._produit = produitAVendre;
		this._listeAcheteur = new Vector<Acheteur>();
		this._tempsRestant = Duration.ofSeconds((heureAvantFin * 60 * 60) + (minuteAvantFin * 60));
		this._offreActuelle = null;
		this._active = true;
	}
	
	public void ajouterOffre(Acheteur acheteur, float prix) {
		if(_active) {	
			if(prix <= 0.0 || prix < this._prix_mini) {
				throw new IllegalArgumentException("Vous devez saisir un prix supérieur au prix minimum."); 
			}
			
			// S'il y a déjà eu au moins une offre, on renvoie la monnaie sur le compte de l'ancien potentiel acheteur
			if(_offreActuelle != null) {
				if(this._offreActuelle.getPrix() >= prix) {
					throw new IllegalArgumentException("Vous devez saisir un prix supérieur à la dernière offre.");
				}
				this._offreActuelle.getAcheteur().updateSolde(this._offreActuelle.getAcheteur().getSolde() + this._offreActuelle.getPrix());
			}
			
			if(acheteur.getSolde() < prix) {
				throw new IllegalArgumentException("Vous devez disposer des ressources nécessaires pour investir."); 
			}
			
			// On remplace l'ancienne offre par la nouvelle
			this._offreActuelle = new Offre(acheteur, prix);
			acheteur.updateSolde(acheteur.getSolde() - prix);
			
			// On ajoute le nouvel acheteur à la liste des gens à notifier s'il n'existe pas déjà
			if(!this._listeAcheteur.contains(acheteur)) {
				this._listeAcheteur.add(acheteur);
			}
			
			// On notifie l'ensemble des autres qu'une nouvelle offre est apparue, sauf la personne qui vient d'enchérir
			for(Acheteur ach : this._listeAcheteur) {
				if(!ach.equals(acheteur)) {
					Observable.just("une personne a déposé une offre plus élevé que toi sur l'objet \"" + this._produit.getNom() + "\" vendu par \"" + this._vendeur.getUsername() + "\" !").subscribe(ach);				
				}
			}	
		} else {
			Log.getInstance().addError("L'enchère est clôturée, vous ne pouvez plus enchérir dessus !");
		}
	}
	
	// Récupérer les infos de l'enchère
	public float getPrix() {
		if(this._offreActuelle != null) {	
			return this._offreActuelle.getPrix();
		}
		return this._prix_mini;
	}
	
	public Acheteur getAcheteur() {
		if(this._offreActuelle != null) {			
			return this._offreActuelle.getAcheteur();
		}
		return null;
	}
	
	public Offre getOffre() {
		if(this._offreActuelle != null) {
			return this._offreActuelle;
		}
		return null;
	}
	
	public Vendeur getVendeur() {
		return this._vendeur;
	}
	
	public Produit getProduit() {
		return this._produit;
	}
	
	public Duration getTempsRestant() {
		return this._tempsRestant;
	}

	// Va refresh le timer de l'enchère toute les secondes (ou les 5 secondes pour soulager le proco)
	@Override
	public void run() {
		while(this._active) {
			synchronized(this) {
				try {
					Thread.sleep(1000);
					this._tempsRestant = this._tempsRestant.minusSeconds(1);
					if(this._tempsRestant.isZero()) {
						this._active = false;
						if(this._offreActuelle != null) {
							for(Acheteur ach : this._listeAcheteur) {
								if(ach.equals(this._offreActuelle.getAcheteur())) {
									Observable.just("vous venez d'acquérir l'objet \"" + this._produit.getNom() + "\" vendu par \"" + this._vendeur.getUsername() + "\" pour " + this.getPrix() + "€ !").subscribe(ach);				
								}
							}
							// Ajouter la transaction à un block
							Blockchain.getStandardBlockchain().addTransaction(new Transaction(this._vendeur, this._offreActuelle.getAcheteur(), this._produit, this._offreActuelle.getPrix()));
						}
					}
				} catch (InterruptedException e) {
					Log.getInstance().addError(e.getMessage());
				}
			}
		}
	}
}