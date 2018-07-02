package LunarLander;

import java.awt.image.BufferedImage;

/**
 * 
 * 
 *
 */
public class Block extends Unit {
	/**
	 * Rozmiar kafelka.
	 */
	public static int BLOCK_SIZE=25;
	/**
	 * Utworzenie kafelka.
	 * @param number Numer rodzaju kafelka. 1 - kafelek pe³ny, 2 - zbocze lewe, 
	 * 3 - zbocze prawe, 4 - strefa ladowania, 0 - kafelek pusty.
	 */
	public Block(int number, int x, int y, BufferedImage img) {	
		super(img, BLOCK_SIZE, BLOCK_SIZE, x, y);
	}
	/**
	 * Zwrócenie obrazka kafelka.
	 * @return Obrazek kafelka.
	 */
	public BufferedImage getImg() {
		return super.getImg();
	}	
	/**
	 * Zwrócenie pozycji w poziomie jednostki.
	 * @return Pozycja w poziomie.
	 */
	public int getX() {
		return super.getX();
	}
	/**
	 * Zwrócenie pozycji w pionie jendostki.
	 * @return Pozycja w pionie.
	 */
	public int getY() {
		return super.getY();
	}
	
	/**
	 * Zwrócenie wspolrzêdnej gornej krawedzi
	 * @return Pozycja gornej krawedzi
	 */
	public int getUpEdge() {
		return super.getUpEdge();
	}
	
	/**
	 * Zwrócenie wspolrzêdnej dolnej krawedzi
	 * @return Pozycja dolnej krawedzi
	 */
	public int getDownEdge() {
		return super.getDownEdge();
	}
	/**
	 * Zwrócenie wspolrzêdnej prawej krawedzi
	 * @return Pozycja prawej krawedzi
	 */
	public int getRightEdge() {
		return super.getRightEdge();
	}
	/**
	 * Zwrócenie wspolrzednej lewej krawêdzi
	 * @return Pozycja lewej krawêdzi
	 */
	public int getLeftEdge() {
		return super.getLeftEdge();
	}
	
	/**
	 * Wyswietlenie wspolrzednych kafelka
	 */
	public void show() {
		super.showUnit();
	}
}
