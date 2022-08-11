package de.stylextv.sandpiles.world;

public class SandPileMap {
	
	private int size;
	
	private long[][] sandPiles;
	
	public SandPileMap(int size) {
		this.size = size;
		this.sandPiles = new long[size][size];
	}
	
	public void decreaseSandPile(int x, int y, long amount) {
		increaseSandPile(x, y, -amount);
	}
	
	public void increaseSandPile(int x, int y, long amount) {
		long l = getSandPile(x, y);
		
		setSandPile(x, y, l + amount);
	}
	
	public long getSandPile(int x, int y) {
		if(isOutOfBounds(x, y)) return 0;
		
		return sandPiles[x][y];
	}
	
	public void setSandPile(int x, int y, long amount) {
		if(isOutOfBounds(x, y)) return;
		
		sandPiles[x][y] = amount;
	}
	
	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || y < 0 || x >= size || y >= size;
	}
	
	public long[][] getSandPiles() {
		return sandPiles;
	}
	
}
