package blockchain;

import java.util.Vector;
import enchere.Transaction;

public class Block {
    private int max_transaction = 1 + (int)(Math.random() + 3);
    private byte[] hash;
    private byte[] previousHash;
    private Vector<Transaction> transactions;
    private boolean verified = false;

    // Copy constructor
    public Block(Block block) {
        this.hash = block.hash;
        this.transactions = block.transactions;
        this.previousHash = block.previousHash;
    }

    public Block(Transaction transaction) {
    	this.previousHash = null;
        this.transactions = new Vector<Transaction>();
        this.transactions.add(transaction);
    }

    public synchronized void setHash(byte[] hash) {
    	this.hash = hash;
    }
    
    public void addTransaction(Transaction t) {
    	synchronized(this.transactions) {
    		this.transactions.add(t);
    	}
    }
    
    public String getStringToHash() {
    	String transaction_concat = "";
    	for(Transaction t : this.transactions) {
    		transaction_concat = transaction_concat + t.getTransactionData();
    	}
    	
    	return transaction_concat;
    }

    public String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}

    public byte[] getHash() {
        return this.hash;
    }

    public byte[] getPreviousHash() {
        return this.previousHash;
    }
    
    public synchronized void setPreviousHash(byte[] hash) {
        this.previousHash = hash;
    }
    
    public int getMaxSize() {
    	return this.max_transaction;
    }
    
    public int getSize() {
    	return this.transactions.size();
    }
    
    public boolean getVerified() {
    	return this.verified;
    }
    
    public synchronized void setVerified(boolean value) {
    	this.verified = value;
    }
}