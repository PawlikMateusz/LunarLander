package LunarLander;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
	/**
	 * Plik konfiguracyjny.
	 */
	private File file;
	/**
	 * Bufor do czytania pliku.
	 */
	private Scanner input;
	/**
	 * Tablica zwieraj¹ca rodzaje kafelek, w zale¿noœci od pozycji. 1 - kafelek pe³ny, 2 - zbocze lewe, 
	 * 3 - zbocze prawe, 4 - strefa ladowania, 0 - kafelek pusty.
	 */
	private int[][] allBlocks;
	/**
	 * Obszary ladowania
	 */
	private int[][] landingArea = new int[6][2];
	/**
	 * Szybkosc zmian predkosci w zaleznosci od grawitacji
	 */
	private double gravity;
	/**
	 * Szybkosc zmian predkosci przy sterowaniu
	 */
	private double enginePower;
	/**
	 * Minimalna predkosc statku potrzebna zeby sie nie rozbic
	 */
	private double minimumV;
	/**
	 * Liczba plikow konfiguracyjnych - liczba map
	 */
	private int fileNumber;
	/**
	 * Utworzenie nowego parsera.
	 * @param fileName Nazwa pliku konfiguracyjnego.
	 * @throws FileNotFoundException Brak pliku o danej nazwie.
	 */
	public Parser(String fileName) throws FileNotFoundException {
		file=new File(fileName);
		if (! new File(fileName).exists()) {
			throw new IllegalArgumentException("Plik " + fileName + " nie istnieje.");
		}
		
		input = new Scanner(file);
		/*
		 * Liczy ile jest kafelek w wierszu i kolumnie.
		 */
		int v=0,u=0; String s="";
		Scanner buffer=null;
		/*while (input.hasNextLine()) {
			s=input.nextLine();
			buffer = new Scanner(s);
			u=0;
			while (buffer.hasNextInt()) {
				u++;
				buffer.nextInt();
			}
			v++;
		}
		if (v!=Ground.HEIGHT | u!=Ground.WIDTH) {
			throw new RuntimeException("Plik konfiguracyjny " + fileName + " nie jest poprawny!");
		}*/
		allBlocks= new int [Ground.HEIGHT][Ground.WIDTH];
		//buffer.close();
		input.close();
		input = new Scanner(file);
		
		while (input.hasNextLine()) {
			s=input.nextLine();
			buffer = new Scanner(s);
			if (s.equals("map (")) {
				setBlocks();
			}
			if (s.equals("landing area (")) {
				setLandingArea();
			}
			if (s.equals("gravity (")) {
				setGravity();
			}
			if (s.equals("engine power (")) {
				setEnginePower();
			}
			if (s.equals("minimum V (")) {
				setMinimumV();
			}
		}
		input.close();
	}
	/**
	 * Odczytanie pliku i zmiana tablicy zwieraj¹cej rodzaje kafelek, w zale¿noœci od pozycji.
	 */
	private void setBlocks() {
		int v=0,u=0; String s=input.nextLine();
		Scanner buffer;
		while (!(s.equals(")"))) {
			buffer = new Scanner(s);
			u=0;
			while (buffer.hasNextInt()) {
				allBlocks[v][u]=buffer.nextInt();
				u++;
			}
			v++;
			s=input.nextLine();
		}
	}
	/**
	 * Odczytanie pliku i zmiana tablicy zwieraj¹cej informacje o obszarach ladowania.
	 */
	private void setLandingArea() {
		int v=0,u=0; String s=input.nextLine();
		Scanner buffer;
		while (!(s.equals(")"))) {
			buffer = new Scanner(s);
			u=0;
			while (buffer.hasNextInt()) {
				landingArea[v][u]=buffer.nextInt();
				u++;
			}
			v++;
			s=input.nextLine();
		}
	}
	/**
	 * Odczytanie pliku i zmiana wartosci grawitacji
	 */
	private void setGravity() {
		String s=input.nextLine();
		Scanner buffer;
		while (!(s.equals(")"))) {
			buffer = new Scanner(s);
			while (buffer.hasNextDouble()) {
				gravity=buffer.nextDouble();
			}
			s=input.nextLine();
		}
	}
	/**
	 * Odczytanie pliku i edycja szybkosci zmian predkosci przy sterowaniu.
	 */
	private void setEnginePower() {
		String s=input.nextLine();
		Scanner buffer;
		while (!(s.equals(")"))) {
			buffer = new Scanner(s);
			while (buffer.hasNextDouble()) {
				enginePower=buffer.nextDouble();
			}
			s=input.nextLine();
		}
	}
	/**
	 * Odczytanie pliku i zmiana minimalnej predkosci potrzebnej aby nie rozbic statku.
	 */
	private void setMinimumV() {
		String s=input.nextLine();
		Scanner buffer;
		while (!(s.equals(")"))) {
			buffer = new Scanner(s);
			while (buffer.hasNextDouble()) {
				minimumV=buffer.nextDouble();
			}
			s=input.nextLine();
		}
	}
	/**
	 * Zwrócenie tablicy zwieraj¹cej rodzaje kafelek, w zale¿noœci od pozycji.
	 * @return Tablica zwieraj¹ca rodzaje kafelek, w zale¿noœci od pozycji
	 */
	public int[][] getBlocks() {
		return allBlocks;
	}	
	/**
	 * Zwrocenie polozenia obszarow ladowania
	 * @return obszary ladowania
	 */
	public int[][] getLandingArea() {
		return landingArea;
	}
	/**
	 * Zwrocenie wartosci grawitacji
	 * @return wartosc grawitacji
	 */
	public double getGravity() {
		return gravity;
	}
	/**
	 * Zwrocenie szybkosci zmian predkosci przy sterowaniu
	 * @return szybkosc zmian predkosci przy sterowaniu
	 */
	public double getEnginePower() {
		return enginePower;
	}
	/**
	 * Zwrocenie wartosci minimalnej predkosci potrzebnej aby nie rozbic statku
	 * @return minimalna predkosc potrzebna aby nie rozbic statku
	 */
	public double getMinimumV() {
		return minimumV;
	}
	
	/**
	 * Pomocnicza metoda do testowania
	 */
	public void say() {
		for (int v=0;v<Ground.HEIGHT;v++) {
			for (int u=0; u<Ground.WIDTH; u++) {
				System.out.print(allBlocks[v][u]+" ");
			}
			System.out.println();
		}
		for (int v=0;v<2;v++) {
			for (int u=0; u<2; u++) {
				System.out.print(landingArea[v][u]+" ");
			}
			System.out.println();
		}
		System.out.println(gravity);
		System.out.println(enginePower);
	}
	/**
	 * Zwraca liczba plikow konfiguracyjnych.
	 * @return Liczba plikow konfiguracyjnych.
	 */
	public int getFileNumber() {
		return fileNumber;
	}
	/**
	 * Oblicza liczba plikow konfiguracyjnych.
	 */
	public void countFileNumber() {
		String name="src/config";
		int i=0;
		while(new File(name+i+".txt").exists()) {
			i++;
		}
		fileNumber=i;
	}
}