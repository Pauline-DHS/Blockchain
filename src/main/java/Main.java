import enchere.*;
import utilisateur.*;

public final class Main {

	public static void main(String[] args) throws InterruptedException {
		ListeUtilisateur lUser = ListeUtilisateur.getInstance();
		
		lUser.createVendeur("Maxime", new Produit("Manette"), new Produit("Jeux", 5), new Produit("Chaussures"));
		lUser.createVendeur("Cyril", new Produit("Bière", 12), new Produit("Téléphone"));
		lUser.createVendeur("Pauline", new Produit("Arbre à chat"), new Produit("Clé USB"));
		lUser.createVendeur("Raphaël", new Produit("Maillot", 5), new Produit("Pantalon"), new Produit("Ecran"), new Produit("Gazinière"));
		
		lUser.createAcheteur("Elodie");
		lUser.createAcheteur("Thomas");
		lUser.createAcheteur("Eliott");
		lUser.createAcheteur("Mathilde");
		lUser.createAcheteur("Elise");
		
		lUser.createAcheteur("Alexandre");
		lUser.createAcheteur("Anaïs");
		lUser.createAcheteur("Coralie");
		lUser.createAcheteur("Nastasia");
		lUser.createAcheteur("Julia");
		
		lUser.createMineur("Teddy");
		lUser.createMineur("Christophe");
		lUser.createMineur("Florence");
		lUser.createMineur("Jérôme");
		lUser.createMineur("Carole");
		lUser.createMineur("Loïc");
		lUser.createMineur("Mélanie");
		lUser.createMineur("Agathe");
	}
}