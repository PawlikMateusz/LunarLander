package LunarLander;


import LunarL.*;

import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Game extends JFrame {
	private Action menu;
	private static Stage stage = new Stage();
	/**
	 * Utworzenie ramki z gr¹.
	 */
	public Game() {
		super("Lunar Lander");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Stage.WIDTH,Stage.HEIGHT);
		setMinimumSize(new Dimension(400,300));
		this.setResizable(true);
		
		//menu = new Action();
		add(stage);
		pack();
		//setPanel("stage");
		setVisible(true);	
	}
	/**
	 * Zmiana wyswitlanego panelu w ramce.
	 * @param panel Nazwa na jaki panel zmienic.
	 * "stage" - panel, wyswietlajacy gre.
	 * "menu" - panel wyswietlajacy menu.
	 */
	public void setPanel(String panel) {
		if (panel.equals("stage")) {
			remove(menu);
			add(stage);
			pack();
		} else if (panel.equals("menu")) {
			remove(stage);
			add(menu);
			pack();
		}
	}
	
	/**
	 * Uruchomienie gry. Uruchomienie watku.
	 * @param args nieu¿ywane
	 * @throws FileNotFoundException 
	 */
//	public static void main(String[] args) throws FileNotFoundException {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new Game();
//			}
//		});
	public static void start(){
		Thread a = new Thread(stage);
		a.start();
		while (a.isAlive()) {}
		try {
			Scores scores = new Scores();
			scores.changeScores(stage.getPlayer().getName(),stage.getPlayer().getPoints());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}