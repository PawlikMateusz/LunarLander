package LunarLander;

import static java.lang.Math.abs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Stage extends JPanel implements Runnable {
	/**
	 * Statek, którym kieruje gracz.
	 */
	private Ship ship;
	/**
	 * Dodatkowe elementy z bonusami
	 */
	private Vector<Bonus> extras = new Vector<Bonus>();
	/**
	 * Podloze, powierzchnia.
	 */
	private Ground ground;
	/**
	 * Nazwa obrazka t³a.
	 */
	private String background;
	/**
	 * Obrazek tla.
	 */
	private BufferedImage img;
	/**
	 * Wysokosc okna.
	 */
	public static int HEIGHT=600;
	/**
	 * Szerokosc okna.
	 */
	public static int WIDTH=800;
	/**
	 * Prêdkosc rakiety w pionie i poziomie.
	 */
	double speedH=0,speedV=0;
	/**
	 * Zmienne mowiace, w jakim kierunku zwiekszana jest predkosc rakiety.
	 */
	boolean up,left,down,right;
	/**
	 * Zmienna mowiaca czy jest dopuszczalna predkosc
	 */
	boolean highSpeed=false;
	/**
	 * Obiekt obs³uguj¹cy kolizje.
	 */
	private Collision collision = new Collision();
	/**
	 * Obiekt zawierajacy dane poziomu.
	 */
	private Config config;
	/**
	 * Obrazek zawierajacy obrazi bonusow.
	 */
	BufferedImage bonusImg = null;
	/**
	 * Gracz, przechowuje nazwe, ilosc paliwa, liczbe zyc, punkty.
	 */
	private Player player;
	/**
	 * Obiekt s³u¿¹cy do losowania licz z danego zakresu.
	 */
	private Random random = new Random();
	/**
	 * Zmienna mowiaca kiedy rysowac odliczanie od 3 do 1.
	 */
	private boolean start;
	/**
	 * Zmienna mowiaca czy koniec gry.
	 */
	private boolean gameOver=false;
	/**
	 * Zmienna sluzaca do rysowania do odliczania od 3 do 1, gdy poczatek gry lub oczekiwanie na nastepny poziom.
	 */
	int k;
	/**
	 * Czas w sekundach od rozpoczecia poziomu.
	 */
	private long timeInSeconds;
	/**
	 * Czas w milisekundach od rozpoczecia poziomu.
	 */
	private long timeInMiliSeconds;
	/**
	 * Zmienna mowiaca, ktory jest aktualnie poziom.
	 * Liczona od 0 do 9, gdy 10 poziomow.
	 */
	private int currentLevel=0;
	/**
	 * Maksymalna liczba poziomow.
	 * Po przejsciu ostaniego poziomu gra sie konczy.
	 */
	private static int MAX_LEVEL=10;
	/**
	 * Liczba klatek na sekudne
	 */
	private static final int FPS = 25;
	/**
	 * Czas miedzy klatkami.
	 */
	private static final int SKIP_TICKS = 1000 / FPS;
	
	/**
	 * Utworzenie panelu.
	 */
	public Stage() {
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		//System.out.println("konstruktor Stage");
		background="img/tlo/tlo"+currentLevel+".png";
		if (! new File(background).exists()) {
			throw new IllegalArgumentException("Plik " + background + " nie istnieje.");
		}
		player = new Player("Mateusz","1");
		
		config = new Config(currentLevel);
		MAX_LEVEL=config.getFileNumber();

		BufferedImage shipImg = null;
		try {
			shipImg = ImageIO.read(new File("img/rakieta.png"));
			bonusImg = ImageIO.read(new File("img/bonus.png"));
			shipImg=shipImg.getSubimage(0, player.getShipNumber()*250, 1166, 250);
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
		}
		ship = new Ship(100,100,shipImg);
		ground = new Ground(config,0);
		
		
//		addMouseListener(new MouseAdapter() {
//			public void mousePressed(MouseEvent e) {
//				int mouseX = e.getX();
//				int mouseY = e.getY();
//				//System.out.println("mouselistener");
//
//				ship.showShip();
//				ship.setX(mouseX);
//				ship.setY(mouseY);
//				speedV=0;
//				speedH=0;
//			}
//		});
		/*
		 * Keybindings
		 */
		Action pressedRight = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("PRAWO");
				//if (collision.getCollision()==true)
				if (player.getFuel()>0) right=true;
			}
		};
		Action releasedRight = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
//				//System.out.println("PRAWO2");
				right=false;
			}
		};
		Action pressedLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("LEWO");
				//if (collision.getCollision()==true)
				if (player.getFuel()>0) left=true;
			}
		};
		Action releasedLeft = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("LEWO2");
				left=false;
			}
		};
		Action pressedUp = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("GORA");
				//if (collision.getCollision()==true)
				if (player.getFuel()>0) up=true;
			}
		};
		Action releasedUp = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("GORA2");
				up=false;
			}
		};
		Action pressedDown = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("DOL");
				//if (collision.getCollision()==true)
				if (player.getFuel()>0) down=true;
			}
		};
		Action releasedDown = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				////System.out.println("DOL2");
				down=false;
			}
		};
		Action info = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				ship.showShip();
			}
		};
		getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"pressedRight");
		getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"),"releasedRight");
		getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"pressedLeft");
		getInputMap().put(KeyStroke.getKeyStroke("released LEFT"),"releasedLeft");
		getInputMap().put(KeyStroke.getKeyStroke("UP"),"pressedUp");
		getInputMap().put(KeyStroke.getKeyStroke("released UP"),"releasedUp");
		getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"pressedDown");
		getInputMap().put(KeyStroke.getKeyStroke("released DOWN"),"releasedDown");
		getInputMap().put(KeyStroke.getKeyStroke('i'),"info");
		getActionMap().put("pressedRight", pressedRight);
		getActionMap().put("releasedRight", releasedRight);
		getActionMap().put("pressedLeft", pressedLeft);
		getActionMap().put("releasedLeft", releasedLeft);
		getActionMap().put("pressedUp", pressedUp);
		getActionMap().put("releasedUp", releasedUp);
		getActionMap().put("pressedDown", pressedDown);
		getActionMap().put("releasedDown", releasedDown);
		getActionMap().put("info", info);
		
					
	}
	/**
	 * Metoda zwracacjaca nowy rozmiar komponentu, gdy ten sie zmieni.
	 * @return Nowe wymiary.
	 */
	public Dimension getPreferedSize() {
		return new Dimension(WIDTH,HEIGHT);
	}
	/**
	 * Pobiera obrazek tla po dodaniu panelu do kontenera.
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		////System.out.println("addNotify");
		try {
			img = ImageIO.read(new File(background));
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
		}
	}
	/**
	 * Maluje wszystkie elementy gry tj.:
	 * tlo, statek, powierzchnie zlozona z kafelkow, elementy dodatkowe (paliwo, zycia).
	 * @param g Konektst graficzny.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		////System.out.println("paintComponent");
		Graphics2D g2d = (Graphics2D) g;
		Dimension size = getSize();
		float rateHeight = (float)size.height/HEIGHT;
		float rateWidth = (float)size.width/WIDTH;
		g2d.drawImage(img, 0, 0, size.width, size.height,this);	
		paintGround(rateWidth,rateHeight,size, g2d);
		paintExtras(rateWidth,rateHeight,size, g2d);
		paintShip(rateWidth,rateHeight,size, g2d);
		paintStart(rateWidth,rateHeight,start,k,g2d);
		paintStop(rateWidth,rateHeight,g2d);
		paintStats(rateWidth,rateHeight,player.getName(),Long.toString(timeInSeconds),Integer.toString(player.getLifes())
				,Integer.toString(player.getPoints()),player.getFuel(),g2d);
	}
	/**
	 * Rysowanie podloza, powierzchni.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param size Rozmiar okna.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintGround(float rateWidth, float rateHeight, Dimension size, Graphics2D g2d) {
		//int blockWidth=size.width/Ground.WIDTH; //wysokosc kafelka
		//int blockHeight=size.height/(2*Ground.HEIGHT); //szerokosc kafelka
		for (int v=0; v<Ground.HEIGHT; v++) {
			int y=(int)(v*rateHeight*Block.BLOCK_SIZE+size.height/2);//blockHeight+size.height/2; //pozycja y kafelka
			int yPrim=(int)((v+1)*rateHeight*Block.BLOCK_SIZE+size.height/2); //liczy pozycje nastepnego kafelka
			if (v==Ground.HEIGHT-1) yPrim=yPrim+1; //jesli ostatni wiersz to dodaje +1 pixel do pozycji
			int sizeHPrim=yPrim-y; //wysokosc kafelka - bierze pozycje nastepnego kafelka i liczy ró¿nice
			for (int u=0; u<Ground.WIDTH; u++) {
				int x=(int)(u*rateWidth*Block.BLOCK_SIZE);//blockWidth; //pozycja x kafelka
				int xPrim=(int)((u+1)*rateWidth*Block.BLOCK_SIZE); //liczy pozycje nastepnego kafelka
				int sizeWPrim=xPrim-x; //szerokosc kafelka - bierze pozycje nastepnego kafelka i liczy ró¿nice
				g2d.drawImage(ground.getBlock(v,u).getImg(), x, y, sizeWPrim, sizeHPrim, this);
			}
		}
	}
	/**
	 * Rysowanie statku.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param size Rozmiar okna.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintShip(float rateWidth, float rateHeight, Dimension size, Graphics2D g2d) {
		int x=(int)(rateWidth*ship.getX()); //pozycja x statka
		int y=(int)(rateHeight*ship.getY()); //pozycja y statka
		int shipWidth=(int)(rateWidth*Ship.WIDTH); //wysokosc statka
		int shipHeight=(int)(rateHeight*Ship.HEIGHT); //szerokosc statka
		g2d.drawImage(ship.getImg(), x, y, shipWidth, shipHeight, this);	
		//g2d.drawImage(ship.getImg(), x, y, (int)(shipWidth+abs(speedH)), (int)(shipHeight+abs(speedV)), this);	
		//g2d.fillRect(x, y, (int)(shipWidth+abs(speedH)), (int)(shipHeight+abs(speedV)));	
		g2d.setColor(Color.cyan);
		if(ship.getY()<0) {
			g2d.fillOval(x+shipWidth/2-5, 2, 10, 10);
			g2d.drawString(Integer.toString(ship.getY()), x+shipWidth/2+2, 10);
		}
		//g2d.drawString(Double.toString(ship.getVUpDown()), x, y);
	}
	/**
	 * Rysowanie elementow dodatkowych, bonusow.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param size Rozmiar okna.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintExtras(float rateWidth, float rateHeight, Dimension size, Graphics2D g2d) {
		for(Bonus bonus : extras) {
			int x=(int)(rateWidth*bonus.getX()); //pozycja x statka
			int y=(int)(rateHeight*bonus.getY()); //pozycja y statka
			int bonusWidth=(int)(rateWidth*Bonus.SIZE); //wysokosc statka
			int bonusHeight=(int)(rateHeight*Bonus.SIZE);
			g2d.drawImage(bonus.getImg(), x, y, bonusWidth, bonusHeight, this);	
		}
	}
	/**
	 * Rysuje odliczanie od 3, gdy poczatek gry lub po przejsciu poziomu i oczekiwaniu na nastepny poziom.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param start Zmienna mowiaca czy rysowac odliczanie.
	 * @param k Przechowuje liczbe rysowana (od 3 do 1). Zmienia sie gdy nastepuje odliczanie.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintStart(float rateWidth, float rateHeight, boolean start, int k, Graphics2D g2d) {
		if (start==true) {
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int)(100*(rateHeight/2+rateWidth/2)))); 
			g2d.setColor(Color.yellow);
			if (ship.landed())
				g2d.drawString(Integer.toString(k), 400*rateWidth, 500*rateHeight);
			else g2d.drawString(Integer.toString(k), 400*rateWidth, 300*rateHeight);
		}
	}
	/**
	 * Rysowanie napisu, gdy gracz przegral.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintStop(float rateWidth, float rateHeight, Graphics2D g2d) {
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int)(100*(rateHeight/2+rateWidth/2))));
		if (gameOver & !ship.landed()) {
			//System.out.println(currentLevel);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int)(100*(rateHeight/2+rateWidth/2)))); 
			g2d.setColor(Color.red);
			g2d.drawString("Przegrales", 200*rateWidth, 300*rateHeight);
		} else if (ship.landed()) {
			if (currentLevel<MAX_LEVEL-1) {
				g2d.setColor(Color.green);
				g2d.drawString("Wyladowales", 125 * rateWidth, 300 * rateHeight);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN,
						(int) (50 * (rateHeight/2+rateWidth/2))));
				g2d.drawString("Nastepna runda za", 200 * rateWidth,
						375 * rateHeight);
			} else {

				//System.out.println(currentLevel);
				g2d.setColor(Color.green);
				g2d.drawString("Wygrales!", 190 * rateWidth, 300 * rateHeight);
				g2d.setFont(new Font("TimesRoman", Font.PLAIN,
						(int) (50 *(rateHeight/2+rateWidth/2))));
				g2d.drawString("Udalo ci sie ukonczyc", 160 * rateWidth,
						375 * rateHeight);
				g2d.drawString("wszystkie poziomy.", 200 * rateWidth,
						450 * rateHeight);
			}
		}
	}
	/**
	 * Rysowanie statystyk gracza (imie, punkty, zycia, czas, paliwo) oracz czasu.
	 * @param rateWidth Stosunek szerokosci rozciagnietego okna do wartosci 800.
	 * @param rateHeight Stosunek dlugosci rozciagnietego okna do wartosci 600.
	 * @param name Imie gracza.
	 * @param time Czas rundy.
	 * @param lifes Liczba zyc.
	 * @param points Punkty zdobyte przez gracza
	 * @param fuel Paliwo, ktore posiada gracz.
	 * @param g2d Kontekst graficzny.
	 */
	private void paintStats(float rateWidth, float rateHeight, String name, String time, String lifes, String points, int fuel, Graphics g2d) {
		g2d.setColor(Color.lightGray);
		g2d.fillRect((int)(680*rateWidth),0, (int)(120*rateWidth), (int)(85*rateHeight));
		g2d.setColor(Color.black);
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, (int)(15*(rateHeight/2+rateWidth/2)))); 
		g2d.drawString("Imie: " + name, (int)(680*rateWidth), (int)(15*rateHeight));
		g2d.drawString("Punkty: " + points, (int)(680*rateWidth), (int)(30*rateHeight));
		g2d.drawString("Zycia: " + lifes, (int)(680*rateWidth), (int)(45*rateHeight));
		g2d.drawString("Czas: " + time, (int)(680*rateWidth), (int)(60*rateHeight));
		
//		int time2=(int)(110*rateWidth)-(int)((timeInSeconds*110*rateWidth)/10);
//		if (time2<0) time2=0;
		
		g2d.drawString("Paliwo: ", (int)(680*rateWidth), (int)(75*rateHeight));
		
		int fuel2=(int)((fuel*110*rateWidth)/Player.MAX_FUEL);
		g2d.setColor(Color.red);
		g2d.fillRect((int)(685*rateWidth),(int)(78*rateHeight), (int)(110*rateWidth), (int)(5*rateHeight));
		g2d.setColor(Color.black);
		g2d.fillRect((int)(685*rateWidth),(int)(78*rateHeight), fuel2, (int)(5*rateHeight));
	}
	/**
	 * Na poczatku rysuje odliczanie.
	 * Pozniej dopoki nie jest koniec gry, czyli gracz nie stracil wszystkich zyc lub nie przeszedl wszystkich poziomow trwa gra.
	 * Dopoki gracz nie wyladowal lub nie stracil wszystkich zyc trwa poziom. Gdy wyladuje przyznanie punktow i zmiana poziomu.
	 */
	@Override
	public void run() {
		this.k = 3;
		for (int k = 0; k < 3; k++) {
			this.start = true;
			repaint();
			sleep(1400);
			this.k--;
			this.start = false;
		}
		while (!gameOver) {
			repaint();
			sleep(1000);
			int i = 0;
			this.k = 3;
			long startTime = System.currentTimeMillis();
			int sleepTime = 16;
			int nextGameTick = 0;
			int[] bonusTime = new int[5];
			for (int w=0;w<5;w++) {
				bonusTime[w]=random.nextInt(40);
			}
			while (!ship.isCrashed() & !ship.landed()) {
				timeInMiliSeconds = (System.currentTimeMillis() - startTime);
				timeInSeconds = timeInMiliSeconds / 1000;
				for (int k = 0; k < 2; k++) {
					i++;
					updateStage(i,bonusTime);
				}

				repaint();

				if (ship.isCrashed()) {
					for (int q = 1; q < 7; q++) {
						ship.setCurrentSprite(4+q);
						repaint();
						sleep(200);
					}
					gameOver=true;
				}

				nextGameTick += SKIP_TICKS;
				sleepTime = (int) (nextGameTick - timeInMiliSeconds);
				if (sleepTime<0) sleepTime=2;
				sleep(sleepTime);
				if (i == 32)
					i = 0;
			}
			if(!ship.isCrashed()) player.addPoints((int)(100*(currentLevel+1)-timeInSeconds+player.getFuel()));
			extras.clear();
			repaint();
			if (ship.landed() & currentLevel<MAX_LEVEL-1) {
				for (int k = 0; k < 3; k++) {
					this.start = true;
					repaint();
					sleep(1400);
					this.k--;
					this.start = false;
				}
			}
			if (currentLevel<MAX_LEVEL-1) {
				currentLevel++;
				setLevel(currentLevel);
			} else {
				gameOver=true;
			}
		}
		
		
	}
	/**
	 * Uœpienie w¹tku.
	 * @param speed Czas, na jaki zostaje uspiony.
	 */
	private void sleep(int speed) {
        try {
        	if (speed<0) Thread.sleep(5); else
        		Thread.sleep(speed);
        } catch (InterruptedException ie) {
        }
    }
	/**
	 * Uaktualnienie stanu gry. 
	 * Tworzenie elementow dodatkowych.
	 * Uaktualnienie stanu statku - polozenia.
	 * Sprawdzenie kolizji statku z podlozem i elementami dodatkowymi
	 */
	private void updateStage(int i, int[] bonusTime) {
		i=updateI(i);
		createBonus(bonusTime);
		
		/*
		 * Po wyjœciu poza mape, statek pojawia sie
		 * po drugiej stronie.
		 */
		if (ship.getX()<0) ship.setX(800);
		if (ship.getX()>800) ship.setX(0);
		/*
		 * Sprawdzenie kolizji z powierzchnia.
		 * Jesli zaszla i jest odpowiednia predkosc statku oraz statek znajduje sie w odpowiednim miejscu -
		 * przedzialy pobierane z pliku config to statek laduje, jesli nie to sie rozbija (utrata zycia lub koniec gry).
		 * Jesli kolizja bedzie w nastepnym kroku to dostosowanie predkosci, aby statek nie wnikal w podloze.
		 * Gdy brak kolizji mozliwosc sterowania pojazdem i dzialanie grawitacji.
		 */
		double[] v= collision.checkCollisionInNextStep(ship, ground);
		if (collision.isCollision()) {
			if (
					(ship.getX()>config.getXLandingArea(0,0) & 
					ship.getX()<config.getXLandingArea(0,1)-Ship.WIDTH_C) |
					(ship.getX()>config.getXLandingArea(1,0) & 
					ship.getX()<config.getXLandingArea(1,1)-Ship.WIDTH_C) |
					(ship.getX()>config.getXLandingArea(2,0) & 
					ship.getX()<config.getXLandingArea(2,1)-Ship.WIDTH_C)
					) {
					if (highSpeed) {
						lossLife();
					} else {
						landing();
					}
				} else {
					lossLife();
				}
				i=0;
		} else if (collision.isCollisionInNextStep()) {
			if (abs(ship.getVUpDown())>config.getMinimumV() | abs(ship.getVLeftRight())>config.getMinimumV() ) {
				highSpeed=true;
			} else {
				highSpeed=false;
			}
			speedV=v[0];
			speedH=v[1];
		} else {
			/*
			 * Sprawdzanie kolizji z innymi elementami
			 */
			Iterator<Bonus> itr = extras.iterator();
			while(itr.hasNext()) {
			    Bonus element = itr.next();
			    collision.checkCollisionWithUnit(ship, element);
			    if (collision.isCollisionWithUnit()){
			    	player.receiveBonus(element.getName(), element.getBonus());
			    	itr.remove();
			    }
			 }
						
			/*
			 * Wplyw sterowania.
			*/
			if (up==true) {
				speedV+=config.getEnginePower();
				player.reduceFuel();
			}
			if (right==true) {
					speedH+=config.getEnginePower();
					player.reduceFuel();
			}
			if (left==true) {
				speedH-=config.getEnginePower();
				player.reduceFuel();
			}
			if (down==true) {
				speedV-=config.getEnginePower();
				player.reduceFuel();
			}
		
			/*
			 * Dzialanie sil oporu powietrza, hamowanie.
			 */
			if (speedH<0) {
				if (speedH<-config.getEnginePower()/4) speedH+=config.getEnginePower()/4;
				else speedH=0;
			}
			else if (speedH>0) {
				if (speedH>config.getEnginePower()/4) speedH-=config.getEnginePower()/4;
				else speedH=0;
			}
		
			/*
			 * Dzialanie grawtiacji.
			 */
			speedV-=config.getGravity();
		}
		
		if (collision.isCollision()) {
			if (up==true) {
				speedV+=0.01;
			}
		}
		/*
		 * Odswiezanie parametrow statku:
		 * predkosci horyzontalnej, predkosci pionowej, obrazka.
		 */
		updateShip(i);
		/*
		 * Statek wykonuje ruch.
		 */
		ship.move();
	}
	/**
	 * Odswiezanie parametrow statku:
	 * predkosci horyzontalnej, predkosci pionowej, obrazka.
	 * @param currentShipSprite Aktualny obrazek statku.
	 */
	private void updateShip(int currentShipSprite) {
		ship.setVLeftRight(speedH);
		ship.setVUpDown(speedV);
		ship.setCurrentSprite(currentShipSprite);
	}
	/**
	 * Odswiezanie zmiennej, na podstawie ktorej tworzona jest animacja - 
	 * zmiana obrazka statku.
	 * @param i Odswiezana zmienna.
	 * @return Odswiezona zmienna.
	 */
	private int updateI(int i) {
		if ((i <= 16) & (i > 8)) i=1;
		else
		if ((i <= 24) & (i > 16)) i=2;
		else
		if ((i <= 8) & (i > 0)) i=3;
		else
		if (i >= 24) i=4;
		return i;
	}
	/**
	 * Utrata zycia gdy kolizja z podlozem.
	 * Gdy gracz straci wszystkie zycia koniec gry.
	 */
	private void lossLife() {
		speedV=0;
		speedH=0;

		player.removeLife();
		player.reducePoints(100);
		if (player.getLifes()<=0)
			ship.crash();
			gameOver=true;
		if (!ship.isCrashed()) {
			ship.setX(random.nextInt(750));
			ship.setY(random.nextInt(200));
			player.setFuel(Player.MAX_FUEL);
			gameOver=false;
		}
		
	}
	/**
	 * Ladowanie statku. Zmiana predkosci na 0.
	 */
	private void landing() {
		speedV=0;
		speedH=0;
		ship.land();

	}
	/**
	 * Zmiana poziomu gry.
	 * @param level Nastepny poziom.
	 */
	private void setLevel(int level) {
		background="img/tlo/tlo"+level+".png";
		try {
			img = ImageIO.read(new File(background));
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
		}

		config = new Config(level);
		player.setFuel(Player.MAX_FUEL);
		BufferedImage shipImg = null;
		try {
			shipImg = ImageIO.read(new File("img/rakieta.png"));
			shipImg=shipImg.getSubimage(0, 1*250, 1166, 250);
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
            e.printStackTrace();
		}
		ship = new Ship(100,100,shipImg);
		ground = new Ground(config,level);
	}
	/**
	 * Stworzenie elementow dodatkowych w zaleznosci od wylosowanych wczesniej czasow, w ktorych sa tworzone.
	 * @param bonusTime Tablica przechowujaca czasy, w ktorych elementy sa tworzone.
	 */
	public void createBonus(int[] bonusTime) {
		for (int x=0;x<5;x++) {
			String name = null;
			int bonusValue = 0;
			int h=random.nextInt(2);
			if (bonusTime[x]==timeInSeconds) {
				bonusTime[x]=-1;
				switch(h) {
				case 0: {
					name="fuel";
					bonusValue=random.nextInt(350-50)+50;
					break;
				}
				case 1: {
					name="life";
					bonusValue=random.nextInt(2-1)+1;
					break;
				}
				default: break;
				}
				extras.add(new Bonus(random.nextInt(750),random.nextInt(350),bonusImg.getSubimage(h*50, 0, 50, 50),bonusValue,name));
			}
		}
	}
	/**
	 * Zwrocenie gracza.
	 * @return Gracz.
	 */
	public Player getPlayer() {
		return player;
	}
}
