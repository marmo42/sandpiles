package de.stylextv.sandpiles.world;

import java.awt.image.BufferedImage;

public class World {
	
	private static final double AVOGADRO_CONSTANT = Math.sqrt(2 / Math.PI);
	
	private static final int[] SANDPILE_COLORS = new int[] {
			0xFFFFFF, 0x408000, 0x7608AA, 0xFFD600
	};
	
	private int size;
	
	private byte[][] sandpiles;
	
	public World(int size) {
		this.size = size;
		this.sandpiles = new byte[size][size];
	}
	
	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				
				byte amount = sandpiles[x][y];
				
				int color = SANDPILE_COLORS[amount];
				
				image.setRGB(x, y, color);
			}
		}
		
		return image;
	}
	
	public void increaseSandpile(int x, int y, long amount) {
		for(int i = 0; i < amount; i++) {
			
			increaseSandpile(x, y);
		}
	}
	
	public void increaseSandpile(int x, int y) {
		if(isOutOfBounds(x, y)) return;
		
		byte amount = sandpiles[x][y];
		
		if(amount == 3) {
			
			sandpiles[x][y] = 0;
			
			increaseSandpile(x - 1, y);
			increaseSandpile(x + 1, y);
			increaseSandpile(x, y - 1);
			increaseSandpile(x, y + 1);
			
			return;
		}
		
		sandpiles[x][y]++;
	}
	
	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || y < 0 || x >= size || y >= size;
	}
	
	public int getSize() {
		return size;
	}
	
	public byte[][] getSandpiles() {
		return sandpiles;
	}
	
	public static World ofSeed(long amount) {
		int size = (int) Math.ceil(AVOGADRO_CONSTANT * Math.sqrt(amount));
		
		World world = new World(size);
		
		int pos = size / 2;
		
		world.increaseSandpile(pos, pos, amount);
		
		return world;
	}
	
}
