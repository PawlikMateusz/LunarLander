package LunarLander;

import java.io.FileNotFoundException;

public class Config {
	/**
	 * Szybkosc zmian predkosci przy sterowaniu
	 */
	private double enginePower;
	/**
	 * Szybkosc zmian predkosci w zaleznosci od grawitacji
	 */
	private double gravity;
	/**
	 * Obszary ladowania
	 */
	private int[][] xLandingArea = new int[6][2];
	/**
	 * Liczba kafelkow w pionie i w poziomie.
	 */
	private int[][] allBlocks = new int[Stage.HEIGHT/(2*Block.BLOCK_SIZE)][Stage.WIDTH/Block.BLOCK_SIZE];
	/**
	 * Nowy obiekt parser
	 */
	private Parser parser;
	/**
	 * Minimalna predkosc statku potrzebna zeby sie nie rozbic
	 */
	private double minimumV;
	/**
	 * Liczba plikow konfiguracyjnych - liczba map
	 */
	private int fileNumber;
	/**
	 * Utworzenie nowego Poziomu
	 * @param levelNumber Numer poziomu
	 */
	public Config(int levelNumber) {
		parser = null;
		String path = "src/config"+Integer.toString(levelNumber)+".txt";
		try {
			parser = new Parser(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		parser.countFileNumber();
		fileNumber=parser.getFileNumber();
		allBlocks=parser.getBlocks();
		enginePower=parser.getEnginePower();
		gravity=parser.getGravity();
		xLandingArea=parser.getLandingArea();
		minimumV=parser.getMinimumV();
		
	}
	/**
	 * Wybranie Numeru Poziomu
	 * @param levelNumber Numer Poziomu
	 */
	public void setLevelNumber(int levelNumber) {
		parser = null;
		String path = "src/config"+Integer.toString(levelNumber)+".txt";
		try {
			parser = new Parser(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Zwrocenie szybkosci zmian predkosci przy sterowaniu
	 * @return szybkosc zmian predkosci przy sterowaniu
	 */
	public double getEnginePower() {
		return enginePower;
	}
	/**
	 * Zwrocenie wartosci grawitacji na planecie
	 * @return Wartosc Grawitacji
	 */
	
	public double getGravity() {
		return gravity;
	}
	/**
	 * Zwrocenie obszarow ladowania
	 * @param v Poczatek strefy ladowania
	 * @param u Koniec strefy ladowania
	 * @return Szerokosc strefu ladowania
	 */
	public int getXLandingArea(int v, int u) {
		return xLandingArea[v][u];
	}
	/**
	 * Zwrocenie liczby kafelkow w pionie i poziomie
	 * @return Liczba kafelkow
	 */
	public int[][] getAllBlocks() {
		return allBlocks;
	}
	/**
	 * Zwrocenie minimalnej predkosci statku potrzebnej zeby sie nie rozbic
	 * @return minimalna predkosc statku
	 */
	public double getMinimumV() {
		return minimumV;
	}
	/**
	 * Zwraca liczba plikow konfiguracyjnych.
	 * @return Liczba plikow konfiguracyjnych.
	 */
	public int getFileNumber() {
		return fileNumber;
	}

}
