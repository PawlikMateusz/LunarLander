package LunarLander;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Scores {
	/**
	 * Plik konfiguracyjny.
	 */
	private File file;
	/**
	 * Bufor do czytania pliku.
	 */
	private Scanner input;
	/**
	 * Obiekt do zapisu pliku.
	 */
	private PrintWriter output;
	/**
	 * Tablica zawierajaca imiona 10 najlepszych graczy.
	 */
	private String[] names = new String[10];
	/**
	 * Tablica zawierajaca punkty 10 najlepszych graczy.
	 */
	private int[] points = new int[10];
	
	/**
	 * Pobiera imiona i punkty 10 najlepszych graczy.
	 * @throws FileNotFoundException Brak pliku o danej nazwie.
	 */
	public Scores() throws FileNotFoundException {
		String fileName="scores.txt";
		file=new File(fileName);
		if (! new File(fileName).exists()) {
			throw new IllegalArgumentException("Plik " + fileName + " nie istnieje.");
		}
		
		input = new Scanner(file);
		Scanner buffer=null;
		int i = 0;
		String s="";
		while(input.hasNextLine()) {
			s=input.nextLine();
			buffer = new Scanner(s);
			while(buffer.hasNext()) {
				if (buffer.hasNextInt()) {
					points[i]=buffer.nextInt();
				} else {
					names[i]=buffer.next();
				}
			}
			i++;
			if (i>=10) break;
		}
		input.close();
		buffer.close();
	}
//	public static void main(String[] args) throws FileNotFoundException {
//		Scores scores = new Scores();
//	}
	/**
	 * Zapisanie wynikow do pliku.
	 * @throws FileNotFoundException Brak pliku.
	 */
	public void saveScores() throws FileNotFoundException {
		output = new PrintWriter("scores.txt");
		for(int i =0;i<10;i++) output.println(names[i]+" "+points[i]);
		output.close();
	}
	/**
	 * Dodanie nowego wyniku jesli jest lepszy od ktoregos w najlepszej 10.
	 * @param name Nazwa gracza.
	 * @param points Jego wynik.
	 * @throws FileNotFoundException Brak pliku.
	 */
	public void changeScores(String name, int points) throws FileNotFoundException {
		int temp;
		String tempS;
		for(int i=0;i<10;i++) {
			if (points>=this.points[i]) {
				temp=this.points[i];
				this.points[i]=points;
				points=temp;
				tempS=this.names[i];
				this.names[i]=name;
				name=tempS;
			} else {
				
			}
		}
		saveScores();
	}
	/**
	 * Pokazanie wynikow.
	 */
	public void show() {
		for (int x:points) System.out.println(x);
		for (String x:names) System.out.println(x);
	}
	
}
