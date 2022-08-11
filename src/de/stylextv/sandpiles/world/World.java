package de.stylextv.sandpiles.world;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;

public class World {
	
	private static final double AVOGADRO_CONSTANT = Math.sqrt(2 / Math.PI);
	
	private static final int[] SAND_PILE_COLORS = new int[] {
			0xFFFFFF, 0x408000, 0x7608AA, 0xFFD600
	};
	
	private int size;
	
	private long[][] sandPiles;
	
	private HashMap<Long, Long> accumulatedChange = new HashMap<>();
	private HashSet<Long> positionsToTopple = new HashSet<>();
	
	public World(int size) {
		this.size = size;
		this.sandPiles = new long[size][size];
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
		
		for(long pos : positionsToTopple) {
			
			int x = Position.getX(pos);
			int y = Position.getY(pos);
			
			long amount = getSandPile(x, y);
			
			if(amount >= 4) {
				
				toppleSandPile(x, y);
				
				toppled = true;
			}
		}
		
		positionsToTopple.clear();
		
		applyChange();
		
		System.out.println(getSandPile(size / 2, size / 2));
		
		return toppled;
	}
	
	private void toppleSandPile(int x, int y) {
		changeSandPile(x, y, -4);
		
		changeSandPile(x - 1, y, 1);
		changeSandPile(x + 1, y, 1);
		changeSandPile(x, y - 1, 1);
		changeSandPile(x, y + 1, 1);
	}
	
	private void changeSandPile(int x, int y, long amount) {
		long pos = Position.ofCoordinates(x, y);
		
		long l = accumulatedChange.getOrDefault(pos, 0l);
		
		accumulatedChange.put(pos, l + amount);
	}
	
	private void applyChange() {
		for(long pos : accumulatedChange.keySet()) {
			
			long amount = accumulatedChange.get(pos);
			
			int x = Position.getX(pos);
			int y = Position.getY(pos);
			
			long l = getSandPile(x, y);
			
			setSandPile(x, y, l + amount);
		}
		
		accumulatedChange.clear();
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
		
		if(amount >= 4) {
			
			long pos = Position.ofCoordinates(x, y);
			
			positionsToTopple.add(pos);
		}
	}
	
	private boolean isOutOfBounds(int x, int y) {
		return x < 0 || y < 0 || x >= size || y >= size;
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
	
	public long[][] getSandPiles() {
		return sandPiles;
	}
	
	public static World ofSeed(long amount) {
		int size = (int) Math.ceil(AVOGADRO_CONSTANT * Math.sqrt(amount));
		
		World world = new World(size);
		
		int pos = size / 2;
		
		world.setSandPile(pos, pos, amount);
		
		return world;
	}
	
}
