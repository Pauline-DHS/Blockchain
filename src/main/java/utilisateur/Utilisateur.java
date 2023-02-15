package utilisateur;

public abstract class Utilisateur {
	protected String _username;
	protected float _solde;
	
	public String getUsername() {
		return this._username;
	}
	
	public float getSolde() {
		return this._solde;
	}
	
	public void updateSolde(float newSolde) {
		if(newSolde < 0.00) {
			throw new IllegalArgumentException("La quantité doit être égale ou supérieur à 0.");
		}
		this._solde = newSolde;
	}
}