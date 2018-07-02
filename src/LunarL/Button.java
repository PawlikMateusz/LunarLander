package LunarL;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 





import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import LunarLander.Game;

@SuppressWarnings("serial")
public class Button extends JPanel implements ActionListener{
	
    public static final int xxx = 100;
    public static final int yyy = 300;
    
    private JButton nowaGra;
    private JButton najlepszeWyniki;
    private JButton wyborStatku;
    private JButton wyjscie;
    
    private Game game;
    private Action action;
    
    private static boolean gowano;
   
 
    public Button(Action action) {
        nowaGra = new JButton("NowaGra");
        najlepszeWyniki = new JButton("Najlepsze Wyniki");
        wyborStatku = new JButton("Wybór statku");
        wyjscie = new JButton("Wyjœcie");
        this.action=action;
        nowaGra.addActionListener(this);
        najlepszeWyniki.addActionListener(this);
        wyborStatku.addActionListener(this);
        wyjscie.addActionListener(this);
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(xxx, yyy));
        add(nowaGra);
        add(najlepszeWyniki);
        add(wyborStatku);
        add(wyjscie);
        setVisible(true);             
    }
 
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
 
        if(source == nowaGra){
     	  SwingUtilities.invokeLater(new Runnable() {
     		  public void run() {
     			 game=new Game();
     		  }
     	  });
     	  action.dispose();
     	  gowano=true;
     	  
        } else if (source == najlepszeWyniki) {
        } else if (source == wyborStatku) {
        } else if (source == wyjscie) {
        }
        
    }
    public Game getGame() {
    	return game;
    }
    public static boolean getGowano() {
    	return gowano;
    }
}