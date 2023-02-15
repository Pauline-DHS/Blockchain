package utilisateur;

import java.util.ArrayList;

import enchere.Produit;

public final class ListeUtilisateur {
	private static ListeUtilisateur _instance = null;
	private static Acheteur _mainUser = null;
	private ArrayList<Thread> _listeThreadUtilisateur;
	private boolean _active = false;
	
	// Singleton
	private ListeUtilisateur() {
		this._listeThreadUtilisateur = new ArrayList<Thread>();
	}
	
	
	public static ListeUtilisateur getInstance() {
		if(ListeUtilisateur._instance == null) {
			ListeUtilisateur._instance = new ListeUtilisateur();
		}
		
		return ListeUtilisateur._instance;
	}
	
	public static Acheteur getMainUser() {
		if(ListeUtilisateur._mainUser == null) {
			ListeUtilisateur._mainUser = new Acheteur("Nicolas");
		}
		return ListeUtilisateur._mainUser;
	}
	
	// Lancer les threads des utilisateurs
	public void launch() {
		if(this._active == false) {			
			for(Thread t : this._listeThreadUtilisateur) {
				t.start();
			}
		}
	}
	
	// Créer les threads des utilisateurs qui feront fonctionner la simulation
	public void createAcheteur(String name, float solde) {
		Thread t = new Thread(new Acheteur(name, solde));
		t.start();
		this._listeThreadUtilisateur.add(t);
	}
	
	public void createAcheteur(String name) {
		Thread t = new Thread(new Acheteur(name));
		t.start();
		this._listeThreadUtilisateur.add(t);
	}
	
	public void createMineur(String nom) {
		Thread t = new Thread(new Mineur(nom));
		t.start();
		this._listeThreadUtilisateur.add(t);
	}
	
	public void createVendeur(String nom, Produit...produits) {
		Thread t = new Thread(new Vendeur(nom, produits));
		t.start();
		this._listeThreadUtilisateur.add(t);
	}
}