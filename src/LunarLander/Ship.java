package LunarLander;

import static java.lang.Math.abs;

import java.awt.image.BufferedImage;

public class Ship extends Unit {
	/**
	 * Wysokość statku.
	 */
	public static int HEIGHT=70;
	/**
	 * Wysokość statku bez ognia do kolizji.
	 */
	public static int HEIGHT_C=50;
	/**
	 * Szerokość statku.
	 */
	public static int WIDTH=30;
	/**
	 * Szeokość statku do kolizji.
	 */
	public static int WIDTH_C=29;
	/** 
	 * Szybkość pionowa statku
	 */
	private double vUpDown;
	/** 
	 * Szybkość pozioma statku
	 */
	private double vLeftRight;
	/**
	 * Stan statku (rozbity lub nie)
	 */
	private boolean isCrashed;
	/**
	 * Stan statku (wyladowal lub w powietrzu)
	 */
	private boolean landed;
	
	private BufferedImage[] sprites = new BufferedImage[11];
	private int currentSprite;
	
	
	/**
	 * Utworzenie statku.
	 * @param x Położenie od lewej krawędzi okna.
	 * @param y Położenie od górnej krawędzi okna.
	 * @param img Obrazek statku.
	 */
	public Ship(int x, int y, BufferedImage img) {
		super(img, WIDTH_C, HEIGHT_C, x, y);
		for (int i = 0; i<11; i++) {
			sprites[i]=super.getImg().getSubimage(i*106,0,106,250);
		}
		currentSprite=1;
	}
	/**
	 * Zmiana położenia statku
	 * Gdy vUpDown > 0 to do gory.
	 * Gdy vLeftRight > 0 do w prawo.
	 */
	public void move() {
		super.setX((int)(super.getX()+vLeftRight));
		super.setY((int)(super.getY()-vUpDown));
		super.setEdges();

	}
	/**
	 * Sprawdzenie czy statek może sie poruszać.
	 */
	public boolean canMove(String whichWay) {
		return true;
	}
	/**
	 * Zwrócenie pozycji w poziomie statku.
	 * @return Pozycja w poziomie.
	 */
	public int getX() {
		return super.getX();
	}
	/**
	 * Zwrócenie pozycji w pionie statku.
	 * @return Pozycja w pionie.
	 */
	public int getY() {
		return super.getY();
	}
	/**
	 * Zwrócenie predkosci w poziomie
	 * @return Predkosc w poziomie
	 */
	public double getVLeftRight() {
		return vLeftRight;
	}
	/**
	 * Zwrócenie predkosci w pionie statku.
	 * @return Predkosci w pionie.
	 */
	public double getVUpDown() {
		return vUpDown;
	}
	/**
	 * Zwrocenie polozenia gornej krawedzi
	 * @return polozenie gornej krawedzi
	 */
	public int getUpEdge() {
		return super.getUpEdge();
	}
	/**
	 * Zwrocenie polozenia dolnej krawedzi
	 * @return polozenie dolnej krawedzi
	 */
	public int getDownEdge() {
		return super.getDownEdge();
	}
	/**
	 * Zwrocenie polozenia prawej krawedzi
	 * @return polozenie prawej krawedzi
	 */
	public int getRightEdge() {
		return super.getRightEdge();
	}
	/**
	 * Zwrocenie polozenia lewej krawedzi
	 * @return polozenie lewej krawedzi
	 */
	public int getLeftEdge() {
		return super.getLeftEdge();
	}
	/**
	 * Zwrocenie obrazka statku.
	 * @return Obrazek.
	 */
	public BufferedImage getImg() {
		return sprites[currentSprite];
	}
	public void setCurrentSprite(int currentSprite) {
		this.currentSprite=currentSprite;
	}
	/**
	 * Zmiana pozycji w pionie statku.
	 * @param i Nowa pozycja w pionie.
	 */
	public void setY(int i) {
		super.setY(i);
	}
	/**
	 * Zmiana pozycji w poziomie statku.
	 * @param i Nowa pozycja w poziomie.
	 */
	public void setX(int i) {
		super.setX(i);
	}
	/**
	 * Zmiana obrazka.
	 * @param name Nazwa nowego obrazka.
	 */
//	public void setImg(String name) {
//		name = "rakieta/"+name+".png";
//		super.setImg(name);
//	}
	/**
	 * Zmiana predkosci poziomej statku.
	 * @param speed Nowa predkosc statku.
	 */
	public void setVLeftRight(double speed) {
		if (abs(speed)>=Block.BLOCK_SIZE) {
			if (speed<0) speed=-Block.BLOCK_SIZE+1;
			else speed=Block.BLOCK_SIZE-1;
		}
		this.vLeftRight=speed;
	}
	/**
	 * Zmiana predkosci pionowej statku.
	 * @param speed Nowa predkosc statku.
	 */
	public void setVUpDown(double speed) {
		if (abs(speed)>=Block.BLOCK_SIZE) {
			if (speed<0) speed=-Block.BLOCK_SIZE+1;
			else speed=Block.BLOCK_SIZE-1;
		}
		this.vUpDown=speed;
	}
	/**
	 * Wyswietlenie informacji o statku
	 */
	public void showShip() {
		System.out.println("####SHIP####");
		super.showUnit();
		System.out.println("Vupdown = " + vUpDown + " Vleftright= " + vLeftRight);
		System.out.println("############");
	}
	/**
	 *  zwrocenie stanu statku (rozbity lub nie)
	 * @return stan statku (rozbity lub nie)
	 */
	public boolean isCrashed() {
		return isCrashed;
	}
	/** 
	 * Rozbicie statku
	 */
	public void crash() {
		isCrashed=true;
	}
	/**
	 * Zwrocenie informacji o wyladowaniu statku
	 * @return informacja o wyladowaniu statku
	 */
	public boolean landed() {
		return landed;
	}
	/**
	 * Wyladowanie statku
	 */
	public void land() {
		landed=true;
	}

} 