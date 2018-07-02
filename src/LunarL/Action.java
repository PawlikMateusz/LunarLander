package LunarL;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;

import javax.swing.*;

import LunarLander.Game;
import LunarLander.Scores;

@SuppressWarnings("serial")
public class Action extends JFrame {
	public static final int xxx = 800;
    public static final int yyy = 600;
    private Button buttonPanel;
	
    public Action() {
    	setLayout(new FlowLayout());
        setPreferredSize(new Dimension(xxx, yyy));
        setSize(300,200);
        buttonPanel = new Button(this);
        add(buttonPanel);
 
        setVisible(true);
    }
	public static void main(String[] args) throws FileNotFoundException {
	SwingUtilities.invokeLater(new Runnable() {
		public void run() {
			new Action();
		}
	});
	while(!Button.getGowano());
	
		Game.start();
	}

}

