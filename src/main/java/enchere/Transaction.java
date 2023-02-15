package enchere;

import utilisateur.*;

public class Transaction {
    private Vendeur vendeur;
    private Acheteur acheteur;
    private Produit produit;
    private float montant;

    public Transaction(Vendeur vendeur, Acheteur acheteur, Produit produit, float montant) {
        this.vendeur = vendeur;
        this.acheteur = acheteur;
        this.produit = produit;
        this.montant = montant;
    }

    public String getTransactionData() {
		return acheteur.getUsername() + " a acheté " + produit.getNom() + " pour " + this.montant + "€ a " + vendeur.getUsername();
	}
}