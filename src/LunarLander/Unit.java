package LunarLander;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Unit {
	/**
	 * Pozycja jednostki.
	 */
	private int x,y;
	/**
	 * Nazwa obrazka jednostki.
	 */
	private String imageFileName;
	/**
	 * Obrazek jednostki.
	 */
	private BufferedImage img;
	/**
	 * Krawedzie jednostki:
	 * a - krawedz gorna
	 * b - krawedz prawa
	 * c - krawedz dolna
	 * d - krawedz lewa
	 */
	private int a,b,c,d;
	/**
	 * Wysokoœæ jednoski.
	 */
	private int height;
	/**
	 * Szeokoœæ jednostki.
	 */
	private int width;
	
	/**
	 * Stworzenie jednostki.
	 * @param img Obrazek jednostki.
	 * @param width Szerokosc jednostki.
	 * @param height Wysokosc jednostki.
	 * @param x Pozycja x jednostki.
	 * @param y Pozycja y jednostki.
	 */
	public Unit(BufferedImage img, int width, int height, int x, int y) {
		this.x=x;
		this.y=y;
		this.img=img;
		this.width=width;
		this.height=height;
		a=y;
		b=x+width;
		c=y+height;
		d=x;
		//System.out.println(imageFileName);
		//showUnit();
		//System.out.println(imageFileName);
	}
	/**
	 * Zwrócenie pozycji w poziomie jednostki.
	 * @return Pozycja w poziomie.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Zwrócenie pozycji w pionie jendostki.
	 * @return Pozycja w pionie.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Zwrocenie obrazka jednostki.
	 * @return Obrazek.
	 */
	public BufferedImage getImg() {
		return img;
	}
	/**
	 * Zwrocenie polozenia gornej krawedzi
	 * @return polozenie gornej krawedzi
	 */
	public int getUpEdge() {
		return a;
	}
	/**
	 * Zwrocenie polozenia dolnej krawedzi
	 * @return polozenie dolnej krawedzi
	 */
	public int getDownEdge() {
		return c;
	}
	/**
	 * Zwrocenie polozenia prawej krawedzi
	 * @return polozenie prawej krawedzi
	 */
	public int getRightEdge() {
		return b;
	}
	/**
	 * Zwrocenie polozenia lewej krawedzi
	 * @return polozenie lewej krawedzi
	 */
	public int getLeftEdge() {
		return d;
	}
	/**
	 * Zmiana pozycji w pionie jednostki.
	 * @param i Nowa pozycja w pionie.
	 */
	public void setY(int i) {
		y=i;
		setEdges();
	}
	/**
	 * Zmiana pozycji w poziomie jednostki.
	 * @param i Nowa pozycja w poziomie.
	 */
	public void setX(int i) {
		x=i;
		setEdges();
	}
	/**
	 * Ustawienie rogow jednostki
	 */
	public void setEdges() {
		a=y;
		b=x+width;
		c=y+height;
		d=x;
	}
	/**
	 * Zmiana obrazka jednostki.
	 * @param name Nazwa nowego obrazka.
	 */
	public void setImg(String name) {
		imageFileName="img/"+name;
		try {
			img = ImageIO.read(new File(imageFileName));
		} catch (IOException e) {
			System.out.println("Plik " + imageFileName + " nie istnieje.");
			e.printStackTrace();
		}
	}
	/**
	 * Wyswietlenie wspolrzednych rogow jednostki
	 */
	public void showUnit() {
		System.out.println("x = " + x + " y= " + y);
		System.out.println("a = " + a + " b= " + b);
		System.out.println("c = " + c + " d= " + d);
		System.out.println("height = " + height + " width= " + width);
	}
}
