package blockchain;

import java.util.Vector;
import enchere.Transaction;

public class Blockchain {
    private Vector<Block> blocks;
    private Block block;
    
    // Dualton -> Pour que les mineurs puissent intéragir avec pendant leur exécution + enchère
    private static Blockchain blockchain_std = null;
    private static Blockchain blockchain_verif = null;

    private Blockchain() {
    	this.block = null;
    	this.blocks = new Vector<Block>();
    }
    
    public static synchronized Blockchain getVerifiedBlockchain() {
    	if(Blockchain.blockchain_verif == null) {
    		Blockchain.blockchain_verif = new Blockchain();
    	}
    	return Blockchain.blockchain_verif;
    }
    
    public static synchronized Blockchain getStandardBlockchain() {
    	if(Blockchain.blockchain_std == null) {
    		Blockchain.blockchain_std = new Blockchain();
    	}
    	return Blockchain.blockchain_std;
    }

    public Block getBlock() {
        return this.block;
    }
    
    public synchronized void addTransaction(Transaction t) {
    	if(this.block == null) {
    		this.block = new Block(t); 
    		if(this.block.getSize() == this.block.getMaxSize()) {
    			this.blocks.add(block);
    			this.block = null;
    		}
    	}
    	else {
    		if(this.block.getSize() == this.block.getMaxSize()) {
    			this.blocks.add(block);
    			this.block = new Block(t);
    		} else {
    			this.block.addTransaction(t);
    		}
    	}
    }

    public int size() {
        return this.blocks.size();
    }

    public synchronized void addBlock(Block block) {
        this.blocks.add(block);
    }
    
    public Block getBlockComplete() {
    	synchronized(this.blocks) {
    		return blocks.get(0);
    	}
    }
    
    public void popBlock() {
    	synchronized(this.blocks) {
    		blocks.remove(0);    		
    	}
    }
    
    public boolean TryGetBlock() {
    	return this.blocks.size() > 0;
    }
}
