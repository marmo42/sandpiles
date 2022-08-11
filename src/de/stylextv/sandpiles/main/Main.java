package de.stylextv.sandpiles.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.stylextv.sandpiles.world.World;

public class Main {
	
	public static void main(String[] args) throws IOException {
		for(int n = 0; n <= 30; n++) {
			
			System.out.print("n = " + n);
			
			long amount = 1l << n;
			
			System.out.print(", amount = " + amount);
			
			World world = World.ofSeed(amount);
			
			world.stabilize();
			
			System.out.print(", stabilized");
			
			BufferedImage image = world.toImage();
			
			String path = "sandpiles/" + n + ".png";
			
			File file = new File(path);
			
			file.mkdirs();
			
			ImageIO.write(image, "PNG", file);
			
			System.out.println(" & rendered");
		}
	}
	
}
