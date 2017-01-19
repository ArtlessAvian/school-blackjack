
/**
 * Visualizes Blackjack.
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import javax.swing.*;
import java.awt.*;

public class BlackJackVisualize extends JComponent
{
    BlackJack game;
    
    public BlackJackVisualize()
    {
        game = new BlackJack();
    }
    
    Rectangle r = new Rectangle();
    
    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;
        r.setSize(WIDTH, HEIGHT);
        g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0.5f));
        g2.fill(r);
    }

    final static int WIDTH = 1200;
    final static int HEIGHT = 675;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        
        frame.add(new BlackJackVisualize());
    }
}
