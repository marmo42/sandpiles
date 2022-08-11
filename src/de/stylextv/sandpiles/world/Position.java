package de.stylextv.sandpiles.world;

public class Position {
	
	public static long ofCoordinates(int x, int y) {
		return (long) x << 32 | y;
	}
	
	public static int getX(long pos) {
		return getY(pos >> 32);
	}
	
	public static int getY(long pos) {
		return (int) (pos & -1l);
	}
	
}
