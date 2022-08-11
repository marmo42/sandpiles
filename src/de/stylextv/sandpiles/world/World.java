package de.stylextv.sandpiles.world;

import java.awt.image.BufferedImage;

public class World {
	
	private static final double AVOGADRO_CONSTANT = Math.sqrt(2 / Math.PI);
	
	private static final int[] SAND_PILE_COLORS = new int[] {
			0x000000, 0xFFD800, 0x0026FF, 0xFF0000
	};
	
	private int size;
	
	private SandPileMap sandPiles;
	private SandPileMap accumulatedChange;
	
	private boolean changed;
	
	public World(int size) {
		this.size = size;
		this.sandPiles = new SandPileMap(size);
		this.accumulatedChange = new SandPileMap(size);
	}
	
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				
				int amount = (int) getSandPile(x, y);
				
				int color = SAND_PILE_COLORS[amount];
				
				image.setRGB(x, y, color);
			}
		}
		
		return image;
	}
	
	public void stabilize() {
		while(true) if(!update()) break;
	}
	
	public boolean update() {
		boolean toppled = false;
		
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				
				long amount = sandPiles.getSandPile(x, y);
				
				if(amount >= 4) {
					
					toppleSandPile(x, y);
					
					toppled = true;
				}
			}
		}
		
		applyChange();
		
		return toppled;
	}
	
	private void toppleSandPile(int x, int y) {
		accumulatedChange.decreaseSandPile(x, y, 4);
		
		accumulatedChange.increaseSandPile(x - 1, y, 1);
		accumulatedChange.increaseSandPile(x + 1, y, 1);
		accumulatedChange.increaseSandPile(x, y - 1, 1);
		accumulatedChange.increaseSandPile(x, y + 1, 1);
	}
	
	private void applyChange() {
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				
				long amount = accumulatedChange.getSandPile(x, y);
				
				sandPiles.increaseSandPile(x, y, amount);
				
				accumulatedChange.setSandPile(x, y, 0);
			}
		}
	}
	
	public long getSandPile(int x, int y) {
		return sandPiles.getSandPile(x, y);
	}
	
	public void setSandPile(int x, int y, long amount) {
		sandPiles.setSandPile(x, y, amount);
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				
				long amount = getSandPile(x, y);
				
				s += amount + " ";
			}
			
			s += "\n";
		}
		
		return s;
	}
	
	public int getSize() {
		return size;
	}
	
	public SandPileMap getSandPiles() {
		return sandPiles;
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public static World ofSeed(long amount) {
		int size = (int) Math.ceil(AVOGADRO_CONSTANT * Math.sqrt(amount));
		
		World world = new World(size);
		
		int pos = size / 2;
		
		world.setSandPile(pos, pos, amount);
		
		return world;
	}
	
}
