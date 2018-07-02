package LunarLander;

import static java.lang.Math.*;

public class Collision {
	/**
	 * Edge left up, Edge left down, Edge right up, Edge right down
	 * Tablica dwuwymiarowa zawierajaca kolizje wszystkich czterech wierzcholkow.
	 */
	private boolean[][] Edge= new boolean[2][2];
//	private boolean[][] Edge2= new boolean[2][2];
	/**
	 * Zmienne mowiace czy jest kolizja.
	 * collision - w tym momencie.
	 * collisionInNextStep - w nastepnych kroku.
	 * collisionWithUnit - miedzy dwoma obiektami typu unit, np statkiem i elementem dodatkowym (paliwem).
	 */
	private boolean collision,collisionInNextStep,collisionWithUnit;
	/**
	 * Tablica zawierajaca kafelki, ktorymi koliduje kazdy wierzcholek statek.
	 * block[0][0] - kafelek kolidujacy z wierzcholkiem lewym gornym. 
	 * block[1][0] - kafelek kolidujacy z wierzcholkiem lewym dolnym.
	 * block[0][1] - kafelek kolidujacy z wierzcholkiem prawym gornym.
	 * block[1][1] - kafelek kolidujacy z wierzcholkiem prawym dolnym.
	 */
	private Block[][] blocks = new Block[2][2];
	/**
	 * Konstruktor. Ustawienie wszystkich zmiennych, ze brak kolizji.
	 */
	public Collision() {
		Edge[0][0]=false;
		Edge[0][1]=false;
		Edge[1][0]=false;
		Edge[1][1]=false;
		collision=false;
//		Edge2[0][0]=false;
//		Edge2[0][1]=false;
//		Edge2[1][0]=false;
//		Edge2[1][1]=false;
		collisionInNextStep=false;
		collisionWithUnit=false;
	}
	/**
	 * Sprawdza dla kazdego wierzcholka w odleglosci wartosci predkoœci statku (czyli w nastepnym kroku)
	 * czy nie zaszla kolizja, aby nie wnikac w klocki.
	 * @param ship Statek sterowany przez gracza.
	 * @param ground Ziemia, powierzchnia zlozona z kafelek.
	 * @return Tablica zawieraj¹ca nowe predkosci statku, aby zatrzymal sie na powierzchni.
	 */
	public double[] checkCollisionInNextStep(Ship ship, Ground ground) {
		/*
		 * Jaki bedzie nastepny krok. 
		 * xx, yy - nastepna pozycja statku
		 */
		double speedRate=0;
		double newSpeedV=ship.getVUpDown();
		double newSpeedH=ship.getVLeftRight();
		if (newSpeedH!=0) speedRate=newSpeedV/newSpeedH;
		//int newX=ship.getX();
		//int newY=ship.getY();
		int xx=(int)(ship.getX()+newSpeedH);
		int yy=(int)(ship.getY()-newSpeedV);
		int rectHeight=Ship.HEIGHT_C;
		int rectWidth=Ship.WIDTH_C;
		/*
		 * Sprawdzenie kolizji dla kazdego wierzcholka
		 */
		if (yy>(Stage.HEIGHT/2-rectHeight) & yy<(Stage.HEIGHT) & 
		xx>(-rectWidth) & xx<(Stage.WIDTH)) {
			for (int i=0; i<2; i++) {
				for (int j=0; j<2; j++) {
					int x=(xx)+i*(rectWidth);
					int y=(yy)+j*(rectHeight);
					int u=(x-x%Block.BLOCK_SIZE)/Block.BLOCK_SIZE;
					int v=(y-y%(Block.BLOCK_SIZE))/(Block.BLOCK_SIZE)-12;
					if (v>=0 & v<Ground.HEIGHT & u>=0 & u<Ground.WIDTH) {
						//System.out.println(v+" "+u);
						int block = ground.getBlockType(v, u);
						blocks[j][i] = ground.getBlock(v, u);
						//System.out.println(block);
						switch (block) {
						case 0: {
							Edge[j][i] = false;
							break;
						}
						case 1: {
							Edge[j][i] = true;
							break;
						}
						case 2: {
							v=300+v*Block.BLOCK_SIZE;
							u=u*Block.BLOCK_SIZE;
							if ((y-v)>(Block.BLOCK_SIZE-(x-u))) {
								Edge[j][i] = true;
							} else {
								Edge[j][i] = false;
							}
							break;

						}
						case 3: {
							v=300+v*Block.BLOCK_SIZE;
							u=u*Block.BLOCK_SIZE;
							if ((x-u)<=(y-v)) {
								Edge[j][i] = true;
							} else {
								Edge[j][i] = false;
							}
							break;

						}
						case 4: {
							Edge[j][i] = true;
							break;
						}
						default:
							break;
						}
					} else
						Edge[j][i]=false;
				}
			}
		} else {
			Edge[0][0]=false;
			Edge[0][1]=false;
			Edge[1][0]=false;
			Edge[1][1]=false;
			collisionInNextStep=false;
		}
		/*
		 * Kolizja w tym momencie, przed wykonaniem kroku.
		 */
		collision=collisionInNextStep;
		/*
		 * Kolizja w nastepnym kroku jesli zasla conajmniej jedna kolizja z wierzcholkiem.
		 */
		collisionInNextStep=Edge[0][0]|Edge[0][1]|Edge[1][0]|Edge[1][1];
		/*
		 * Zmiana predkosci aby wyladowac na kafelku a nie w niego wejsc
		 */
		if (collisionInNextStep) {
			//ship.showShip();
			if ( ( newSpeedV>0 ) & ( Edge[0][0] | Edge[0][1] ) ) {
				int c = blocks[0][0].getDownEdge();
				int a = ship.getUpEdge();
				newSpeedV=abs(c-a)+1;
				System.out.println("newSpeedV="+newSpeedV);
			} else if ( ( newSpeedV<0 ) & ( Edge[1][0] | Edge[1][1] ) ) {
				int a = blocks[1][0].getUpEdge();
				int c = ship.getDownEdge();
				newSpeedV=-abs(a-c)-1;
				//System.out.println("Dol//////////////////////");
				//System.out.println("newSpeedV="+newSpeedV);
			}
			if ( ( newSpeedH<0 ) & ( Edge[0][0] | Edge[1][0] )) {
				int b = blocks[1][0].getRightEdge();
				int d = ship.getLeftEdge();
				newSpeedH=-abs(d-b)-1;
				//System.out.println("Lewo///////////////////////");
				//System.out.println("newSpeedH="+newSpeedH);

			} else if ( ( newSpeedH>0 ) & ( Edge[0][1] | Edge[1][1] ) ) {
				int d = blocks[1][1].getLeftEdge();
				int b = ship.getRightEdge();
				newSpeedH=abs(b-d)+1;
				System.out.println("newSpeedH="+newSpeedH);
			}
		}
		/*
		 * Dostosowanie predkosci, aby ich stosunek byl taki sam jak przed sprawdzeniem kolizji
		 */
		if (abs(ship.getVUpDown())>abs(ship.getVLeftRight())) 
			if (speedRate==0) newSpeedH=0;
			else newSpeedH=newSpeedV/speedRate;
		else
			newSpeedV=newSpeedH*speedRate;
		double[] v= {newSpeedV, newSpeedH};
		return v;
	}
	/**
	 * Sprawdzenie kolizji miedzy dwoma obiektami klasy unit, np statkiem i elementem dodatkowym.
	 * @param u1 Obiekt klasy unit pierwszy, np statek.
	 * @param u2 Obiekt klasy unit drugi, np element dodatkowy - paliwo.
	 */
	public void checkCollisionWithUnit(Unit u1, Unit u2) {
		if (((u2.getLeftEdge() <  u1.getLeftEdge() & u1.getLeftEdge() < u2.getRightEdge()) |
			(u2.getLeftEdge() < u1.getRightEdge() & u1.getRightEdge() < u2.getRightEdge())) &
			((u2.getUpEdge() < u1.getUpEdge() & u1.getUpEdge() < u2.getDownEdge()) |
			(u2.getUpEdge() < u1.getDownEdge() & u1.getDownEdge() < u2.getDownEdge()))) {
			collisionWithUnit=true;
		} else {
			collisionWithUnit=false;
		}
	}
	/**
	 * Zwrocenie czy jest kolizja w tym momencie.
	 * @return true jesli zaszla kolizja, false jesli nie zaszla.
	 */
	public boolean isCollision() {
		return collision;
	}
	/**
	 * Zwrocenie czy jest kolizja miedzy dwoma obiektami, np statkiem i elementem dodatkowym - paliwem.
	 * @return true jesli zaszla kolizja, false jesli nie zaszla.
	 */
	public boolean isCollisionWithUnit() {
		return collisionWithUnit;
	}
	/**
	 * Zwrocenie czy jest kolizja w nastepnym kroku.
	 * @return true jesli zaszla kolizja, false jesli nie zaszla.
	 */
	public boolean isCollisionInNextStep() {
		return collisionInNextStep;
	}
	/**
	 * Zwrocenie tablicy zawierajacej iformacje, czy z danym wierzcholkiem zaszla kolizja.
	 * @return Tablica czteroelementowa dla kazdego wierzcholka informujaca czy zaszla z nim kolizja.
	 */
	public boolean[][] getEdges() {
		return Edge;
	}
}
