package enchere;

public final class Produit {
	private String _nomProduit;
	private int _quantite;
	
	public Produit(String nomProd, int qtt) {
		this._nomProduit = nomProd;
		if(qtt < 1) {
			throw new IllegalArgumentException("La quantité doit être égale ou supérieur à 1.");
		}
		this._quantite = qtt;
	}
	
	public Produit(String nomProd) {
		this._nomProduit = nomProd;
		this._quantite = 1;
	}
	
	
	public String getNom() {
		return this._nomProduit;
	}
	
	
	public int getQuantite() {
		return this._quantite;
	}
} 