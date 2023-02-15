package enchere;

import utilisateur.Acheteur;

public final class Offre {
	private Acheteur _acheteur;
	private float _prix;
	
	public Offre(Acheteur acheteur, float prix) {
		this._acheteur = acheteur;
		if(prix < 0.f) {
			throw new IllegalArgumentException("Le prix doit être égale ou supérieur à 0.");
		}
		this._prix = prix;
	}
	
	public Acheteur getAcheteur() {
		return this._acheteur;
	}
	
	public float getPrix() {
		return this._prix;
	}
}