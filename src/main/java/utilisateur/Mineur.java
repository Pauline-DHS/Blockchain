package utilisateur;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import display.Log;
import blockchain.*;

public class Mineur extends Utilisateur implements Runnable {
	private static int mineur_number = 0;
	private int mineur_id;
	
	public Mineur(String nom) {
        this._username = nom;
        this._solde = 0.f;
        this.mineur_id = mineur_number;
        mineur_number += 1;
    }

	@Override
	public void run() {
		try {			
			Blockchain blockchain_std = Blockchain.getStandardBlockchain();
			Blockchain blockchain_verified = Blockchain.getVerifiedBlockchain();
			Block blockToHash = null;
			byte[] hash = null;
			byte[] previous_hash = null;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			while(true) {	
				if(blockchain_std.TryGetBlock()) {
					if(previous_hash != null) {
						blockToHash.setPreviousHash(previous_hash);
					}
					blockToHash = blockchain_std.getBlockComplete();
					String chaine_complete = blockToHash.getStringToHash();
					long index = 0;
					
					while(blockToHash.getVerified() == false) {
						String to_hash = chaine_complete + ((index * mineur_number) + this.mineur_id);
						hash = md.digest(to_hash.getBytes(StandardCharsets.UTF_8));
						
				
						// On test les 5 premier digits de la chaine en hexa pour avoir les 20 premiers bits à 0
						// Source : https://en.wikipedia.org/wiki/Hashcash -> Sender's side
						String truncated = blockToHash.bytesToHex(hash).substring(0, 5);
						if (truncated.equals("00000")){
							if(blockToHash.getVerified() == false) {
								// On récompense le mineur
								this._solde += 50.f;
								
								// Ajoute le bloc vérifié à la nouvelle blockchain 
								blockchain_verified.addBlock(blockToHash);
								
								// On précise que le block est vérifié, puis on le hash pour l'ajouter au prochain block qui sera haché
								blockToHash.setVerified(true);
								blockToHash.setHash(hash);
								previous_hash = hash;
								blockchain_std.popBlock();
								
								// Affichage du hash et de la chaine qui à été hachée
								Log.getInstance().addMineurInfo(this._username + " : Ce hash est vérifié ! : " + blockToHash.bytesToHex(hash) + ", il a été ajouté à la blockchain vérifiée.");
								Log.getInstance().addMineurInfo(blockToHash.getStringToHash());
								
							}
							Thread.sleep(500 + (int)(Math.random() * 1000));
						}
						
						index++;
					}
				}
				Thread.sleep(5000);			
			}
		} catch (Exception e) {
			Log.getInstance().addError(e.getMessage());
		}
	}
}