package LunarLander;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ground {
	/**
	 * Tablica kafelek, z których sk³ada sie pod³o¿e.
	 */
	private Block[][] blocks = new Block[HEIGHT][WIDTH];
	/**
	 * Tablica zwieraj¹ca rodzaje kafelek, w zale¿noœci od pozycji.
	 */
	private int[][] allBlocks = new int[HEIGHT][WIDTH];
	/**
	 * Obrazek zawierajacy rodzaje kafelek
	 * (Sprite sheet)
	 */
	private BufferedImage img;
	/**
	 * Liczba kafelek w pionie.
	 */
	public static int HEIGHT=Stage.HEIGHT/(2*Block.BLOCK_SIZE);
	/**
	 * Liczba kafelek w poziomie.
	 */
	public static int WIDTH=Stage.WIDTH/Block.BLOCK_SIZE;
	
	/**
	 * Utworzenie pod³o¿a.
	 */
	public Ground(Config config, int level) {
		setImg("0.png");
		img=img.getSubimage(0, level*50, 250, 50);
		allBlocks=config.getAllBlocks();
		setBlocks();
	}
	/**
	 * Utworzenie tablicy kafelek.
	 */
	public void setBlocks() {	
		for (int v=0; v<HEIGHT; v++) {
			for (int u=0; u<WIDTH; u++) {
				BufferedImage blockImage=img.getSubimage(allBlocks[v][u]*50, 0, 50, 50);
				blocks[v][u]= new Block(allBlocks[v][u],u*Block.BLOCK_SIZE,v*Block.BLOCK_SIZE+300, blockImage);
				//System.out.print(allBlocks[v][u]);
			}
			//System.out.println();
		}
	}
	/**
	 * Zwrócenie kafelka.
	 * @param v Numer kolumny.
	 * @param u Numer wiersza.
	 * @return Rodzaj kafelka. Numer rodzaju kafelka. 1 - kafelek pe³ny, 2 - zbocze lewe, 
	 * 3 - zbocze prawe, 0 - kafelek pusty.
	 */
	public Block getBlock(int v, int u) {
		return blocks[v][u];
	}
	/**
	 * Zwrocenie rodzaju kafelka
	 * @param v Numer kolumny.
	 * @param u Numer wiersza.
	 * @return Rodzaj kafelka. Numer rodzaju kafelka. 1 - kafelek pe³ny, 2 - zbocze lewe, 
	 * 3 - zbocze prawe, 0 - kafelek pusty.
	 */
	public int getBlockType(int v, int u) {
		return allBlocks[v][u];
	}
	/**
	 * Zmiana obrazka zawierajacego ilustracje rodzaji kafelek.
	 * @param name Nazwa obrazka z pliku png.
	 */
	public void setImg(String name) {
		String imageFileName="img/"+name;
		try {
			img = ImageIO.read(new File(imageFileName));
		} catch (IOException e) {
			System.out.println("Plik " + imageFileName + " nie istnieje.");
			e.printStackTrace();
		}
	}
}
