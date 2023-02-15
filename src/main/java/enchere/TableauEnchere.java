package enchere;

import java.util.Vector;

import display.Window;
import utilisateur.Vendeur;

public final class TableauEnchere {
	private Vector<Enchere> _listeEnchere;
	private static TableauEnchere tableauEnchere = new TableauEnchere();
	
	// Singleton
	private TableauEnchere() {
		this._listeEnchere = new Vector<Enchere>();
	}
	
	public static TableauEnchere getInstance() {
		synchronized(TableauEnchere.tableauEnchere) {			
			return TableauEnchere.tableauEnchere;
		}
	}
	
	// Méthodes
	public synchronized void addEnchere(Vendeur vendeur, Produit produitAVendre, float prix_mini, int heureAvantFin, int minuteAvantFin) {
		//(this._listeEnchere) {
			Enchere temp = new Enchere(vendeur, produitAVendre, prix_mini, heureAvantFin, minuteAvantFin);
			this._listeEnchere.add(temp);	
			Thread t = new Thread(temp);
			t.start();	
			
			// Mettre a jour la fenêtre
			Window.getInstance().getDisplayTableauEnchere().addLabelEnchere(temp, Window.getInstance());
		//}
	}
	
	public Enchere getEnchere(int id) {
		return this._listeEnchere.get(id);
	}
	
	public Vector<Enchere> getAllEnchere() {
		return _listeEnchere;
	}
	
	public void removeEnchere(int id) {
		synchronized(_listeEnchere) {
			this._listeEnchere.remove(id);			
		}
	}
	
	public void removeEnchere(Enchere ench) {
		synchronized(_listeEnchere) {
			this._listeEnchere.remove(ench);			
		}
	}
}