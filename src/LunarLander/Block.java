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
	 * @param number Numer rodzaju kafelka. 1 - kafelek pe�ny, 2 - zbocze lewe, 
	 * 3 - zbocze prawe, 4 - strefa ladowania, 0 - kafelek pusty.
	 */
	public Block(int number, int x, int y, BufferedImage img) {	
		super(img, BLOCK_SIZE, BLOCK_SIZE, x, y);
	}
	/**
	 * Zwr�cenie obrazka kafelka.
	 * @return Obrazek kafelka.
	 */
	public BufferedImage getImg() {
		return super.getImg();
	}	
	/**
	 * Zwr�cenie pozycji w poziomie jednostki.
	 * @return Pozycja w poziomie.
	 */
	public int getX() {
		return super.getX();
	}
	/**
	 * Zwr�cenie pozycji w pionie jendostki.
	 * @return Pozycja w pionie.
	 */
	public int getY() {
		return super.getY();
	}
	
	/**
	 * Zwr�cenie wspolrz�dnej gornej krawedzi
	 * @return Pozycja gornej krawedzi
	 */
	public int getUpEdge() {
		return super.getUpEdge();
	}
	
	/**
	 * Zwr�cenie wspolrz�dnej dolnej krawedzi
	 * @return Pozycja dolnej krawedzi
	 */
	public int getDownEdge() {
		return super.getDownEdge();
	}
	/**
	 * Zwr�cenie wspolrz�dnej prawej krawedzi
	 * @return Pozycja prawej krawedzi
	 */
	public int getRightEdge() {
		return super.getRightEdge();
	}
	/**
	 * Zwr�cenie wspolrzednej lewej kraw�dzi
	 * @return Pozycja lewej kraw�dzi
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
