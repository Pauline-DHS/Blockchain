package utilisateur;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import enchere.*;

import java.util.Vector;

import display.Log;

public final class Acheteur extends Utilisateur implements Observer<String>, Runnable {
	private Vector<Produit> _listeProduitAchete;
	private boolean _active = true;
	private Enchere _enchereEnVue;
	
	public Acheteur(String name, float solde) {
		this._username = name;
		if(solde < 0) {
			throw new IllegalArgumentException("La quantité doit être égale ou supérieur à 0.");
		}
		this._solde = solde;
		this._listeProduitAchete = new Vector<Produit>();
		this._enchereEnVue = null;
	}
	
	public Acheteur(String name) {
		this._username = name;
		this._solde = 25.0f + ((float)Math.random() * 225.0f);
	}
	
	public void addProduit(Produit prod) {
		synchronized(this._listeProduitAchete) {			
			this._listeProduitAchete.add(prod);
		}
	}
	
	// Observer
	public void onSubscribe(Disposable d) {}

	public void onError(Throwable e) {}
	
	public void onComplete() {}
	
	// Utilisé pour les observables directement
	public void onNext(String t) {
		if(this == ListeUtilisateur.getMainUser()) {
			Log.getInstance().addDebug(this._username + ", " + t);
		} else {
			if(this._enchereEnVue != null) {				
				if(this._enchereEnVue.getAcheteur() != this && (this._enchereEnVue.getPrix() + 2.f) < this._solde) {
					try {
						this._enchereEnVue.ajouterOffre(this, this._enchereEnVue.getPrix() + 2.f);
					} catch(Exception e) {
						
					}
				} else if (this._enchereEnVue.getAcheteur() != this && (this._enchereEnVue.getPrix() + 0.5f) >= this._solde) {
					this._enchereEnVue = null;
				}
			} else {
				try {					
					TableauEnchere tabEnchere = TableauEnchere.getInstance();
					int indice = (int) (Math.random() * tabEnchere.getAllEnchere().size());
					this._enchereEnVue = tabEnchere.getEnchere(indice);
					this._enchereEnVue.ajouterOffre(this, this._enchereEnVue.getPrix() + 2.f);
				} catch(Exception e) {
					
				}
			}
		}
	}

	// Fonctionnement d'un acheteur (thread simulation)
	@Override
	public void run() {
		try {
			TableauEnchere tabEnchere = TableauEnchere.getInstance();
			//Attente d'ajout des enchères
			Thread.sleep(1500 + ((int)Math.random() * 2500));
			while(_active) {
					if(this._enchereEnVue == null) {
						int indice = (int) (Math.random() * tabEnchere.getAllEnchere().size());
						this._enchereEnVue = tabEnchere.getEnchere(indice);
						this._enchereEnVue.ajouterOffre(this, this._enchereEnVue.getPrix() + 2.f);
					}
					Thread.sleep(1500 + ((int)Math.random() * 2500));
			}
		} catch (Exception e) {
			//Log.getInstance().addError(e.getMessage());
		}
	}
}