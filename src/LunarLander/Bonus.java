package LunarLander;

import java.awt.image.BufferedImage;

public class Bonus extends Unit {
	/**
	 * Wartoœæ bonusa
	 */
	private int bonus;
	/**
	 * Nazwa bonusa
	 */
	private String name;
	/**
	 * Wielkosc bonusa
	 */
	public static final int SIZE = 50;
	/**
	 * Utworzenie nowego bonusa
	 * @param x wspolrzednia x
	 * @param y wspolrzedna y
	 * @param img Obraz bonusa
	 * @param bonus Wartosc bonusa
	 * @param name Nazwa bonusa
	 */
	public Bonus(int x, int y, BufferedImage img, int bonus, String name) {
		super(img,SIZE,SIZE,x,y);
		this.bonus=bonus;
		this.name=name;
	}
	/**
	 * Zwrocenie wartosci bonusa
	 * @return Wartosc bonusa
	 */
	public int getBonus() {
		return bonus;
	} 
	/**
	 * Zwrocenie nazwy bonusa
	 * @return nazwa bonusa
	 */
	public String getName() {
		return name;
	}
}
