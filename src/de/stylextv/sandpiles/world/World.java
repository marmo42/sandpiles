package de.stylextv.sandpiles.world;

import java.awt.image.BufferedImage;

public class World {
	
	private static final double AVOGADRO_CONSTANT = Math.sqrt(2 / Math.PI);
	
	private static final int[] SAND_PILE_COLORS = new int[] {
			0xFFFFFF, 0x408000, 0x7608AA, 0xFFD600
	};
	
	private int size;
	
	private int[][] sandPiles;
	
	public World(int size) {
		this.size = size;
		this.sandPiles = new int[size][size];
	}
	
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				
				int amount = sandPiles[x][y];
				
				int color = SAND_PILE_COLORS[amount];
				
				image.setRGB(x, y, color);
			}
		}
		
		return image;
	}
	
	public void increaseSandPile(int x, int y, long amount) {
		for(int i = 0; i < amount; i++) {
			
			increaseSandPile(x, y);
		}
	}
	
	public void increaseSandPile(int x, int y) {
		if(isOutOfBounds(x, y)) return;
		
		int amount = sandPiles[x][y];
		
		if(amount < 3) {
			
			sandPiles[x][y]++;
			
			return;
		}
		
		sandPiles[x][y] -= 3;
		
		increaseSandPile(x - 1, y);
		increaseSandPile(x + 1, y);
		increaseSandPile(x, y - 1);
		increaseSandPile(x, y + 1);
	}
	
	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || y < 0 || x >= size || y >= size;
	}
	
	public int getSize() {
		return size;
	}
	
	public int[][] getSandPiles() {
		return sandPiles;
	}
	
	public static World ofSeed(long amount) {
		int size = (int) Math.ceil(AVOGADRO_CONSTANT * Math.sqrt(amount));
		
		World world = new World(size);
		
		int pos = size / 2;
		
		world.increaseSandPile(pos, pos, amount);
		
		return world;
	}
	
}
