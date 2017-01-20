
/**
 * Visualizes Blackjack.
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

public class BlackJackVisualize extends JComponent
{
    BlackJack game;
    Rectangle r;
    ArrayList<Card> cardsToDraw;

    public BlackJackVisualize()
    {
        game = new BlackJack();
        game.newRound();

        r = new Rectangle();

        cardsToDraw = new ArrayList<Card>();
    }

    public void paint(Graphics g)
    {
        Graphics2D g2 = (Graphics2D)g;

        // Draw Background
        r.setSize(WIDTH, HEIGHT);
        r.x = 0;
        r.y = 0;
        g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0.5f));
        g2.fill(r);

        // g2.setColor(Color.getHSBColor(0, 1, 1));
        // r.setSize(100,100);
        // r.x = (int)(Math.random() * 100);
        // r.y = (int)(Math.random() * 100);
        // g2.fill(r);

        //g2.drawImage(cardsToDraw.get(0).img, 0, 0, 100, 200, null);

        // Draw each card
        for (int i = 0; i < game.currentHand.cards.size(); i++)
        {
            Card c = game.currentHand.cards.get(i);
            if (!cardsToDraw.contains(c))
            {
                c.reset();
                c.posX = i * 100;
            }
            c.drawSelf(g2, r);
        }
    }

    final static int WIDTH = 1200;
    final static int HEIGHT = 675;
    
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);

        frame.add(new BlackJackVisualize());

        frame.setVisible(true);        

        class TimerListener implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                frame.repaint();
            }
        }

        ActionListener listener = new TimerListener();
        Timer t = new Timer(1000/60, listener);
        t.start();
    }
}
