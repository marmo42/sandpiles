package de.stylextv.sandpiles.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.stylextv.sandpiles.world.World;

public class Main {
	
	public static void main(String[] args) throws IOException {
		for(int n = 0; n <= 30; n++) {
			
			System.out.println("n = " + n);
			
			long amount = 1l << n;
			
			World world = World.ofSeed(amount);
			
			BufferedImage image = world.toImage();
			
			String path = "sandpiles/" + n + ".png";
			
			File file = new File(path);
			
			file.mkdirs();
			
			ImageIO.write(image, "PNG", file);
		}
	}
	
}
