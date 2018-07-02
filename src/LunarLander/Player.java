package LunarLander;

public class Player {
	/**
	 * Liczba punktow gracza
	 */
	private int points;
	/**
	 * Liczba zyc gracza
	 */
	private int lifes;
	/**
	 * Nazwa gracza
	 */
	private String name;

	/**
	 * Sciezka do obrazu statku
	 */
	private String imageFileName;
	/**
	 * Ilosc paliwa
	 */
	private int fuel;
	/**
	 * Maksymalna ilosc paliwa
	 */
	public static final int MAX_FUEL=500;
	/**
	 * Maksymalna liczba zyc
	 */
	private int maxLifes;
	/**
	 * Numer okreslajacy model statku
	 */
	private int shipNumber;
	
	/**
	 * Stworzenie profilu gracza
	 * @param name Nazwa gracza
	 * @param imageFileName 
	 */
	
	public Player(String name, String imageFileName) {
		this.name=name;
		this.imageFileName=imageFileName;
		points=0;
		maxLifes=3;
		lifes=maxLifes;
		shipNumber=0;
		fuel=MAX_FUEL;
	}
	
	/**
	 * Ustalenie liczby punktow
	 */
	public void setPoints(int points) {
		this.points=points;
	}
	/**
	 * Dodanie punktow
	 */
	public void addPoints(int points) {
		this.points+=points;
	}
	/**
	 * Odjecie punktow
	 */
	public void reducePoints(int points) {
		this.points-=points;
	}
	/**
	 * Zwrocenie liczby punktow
	 * @return liczba punktow
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * Odjecie jednego zycia po kolizji
	 */
	public void removeLife() {
		if (lifes>0) 
			lifes--;
	}
	/**
	 * Zwrocenie liczby zyc
	 * @return Liczba zyc
	 */
	public int getLifes() {
		return lifes;
	}
	/**
	 * Zwrocenie nazwy gracza
	 * @return Nazwa gracza
	 */
	public String getName() {
		return name;
	}
	/**
	 * Zwrocenie nazwy obrazu statku
	 * @return sciezka obrazu statku
	 */
	public String getImageFileName() {
		return imageFileName;
	}
	/**
	 * Ustalenie poziomu paliwa
	 * @param fuel aktualny poziom paliwa
	 */
	public void setFuel(int fuel) {
		this.fuel=fuel;
	}
	/**
	 * Zredukowanie poziomu paliwa
	 */
	public void reduceFuel() {
		if (fuel>0) 
		this.fuel--;
	}
	/**
	 * Zwrocenie poziomu paliwa
	 * @return poziom paliwa
	 */
	public int getFuel() {
		return fuel;
	}
	/**
	 * Zwrocenie numeru statku
	 * @return numer statku
	 */
	public int getShipNumber() {
		return shipNumber;
	}/**
	 * Ustalenie numeru statku
	 * @param Numer statku
	 */
	public void setShipNumber(int number) {
		shipNumber=number;
	}
	/**
	 * Otrzymanie "Bonus'a"
	 * @param name Nazwa bonusa
	 * @param bonus Numer ilosc zdobytych bonusow
	 */
	public void receiveBonus(String name, int bonus) {
		switch(name){
		case "fuel": {
			fuel+=bonus;
			if (fuel>MAX_FUEL) fuel=MAX_FUEL;
			break;
		}
		case "life": {
			lifes+=bonus;
			break;
		}
		default: break;
		}
	}
}
