
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
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.border.*;
import javax.accessibility.*;
import javax.swing.*;

public class BlackJackVisualize extends JPanel
{
	BlackJack game;
	Rectangle r;
	ArrayList<CardVisual> cardsToDraw;

	long lastTime;

	State state = new DealState();

	static int MS_PER_FRAME = 1000/60;

	public BlackJackVisualize()
	{
		game = new BlackJack();

		r = new Rectangle();

		cardsToDraw = new ArrayList<CardVisual>();
	}

	// Too lazy for state based machine

	public void gameLogic(float dt)
	{
		state.doStuff(this,dt);

		// State Based Machine
		// Player phase
		// Dealer phase
		// Clear board phase
	}

	public void paint(Graphics g)
	{
		if (lastTime == 0) {lastTime = System.nanoTime();}
		long currentTime = System.nanoTime();
		float deltaTime = (float)((currentTime - lastTime) / 1000000000.0);
		lastTime = currentTime;

		gameLogic(deltaTime);

		Graphics2D g2 = (Graphics2D)g;

		// Draw Background
		r.setSize(WIDTH, HEIGHT);
		r.x = 0;
		r.y = 0;
		g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0.5f));
		g2.fill(r);

		// Draw each card
		for (int i = 0; i < cardsToDraw.size(); i++)
		{
			CardVisual c = cardsToDraw.get(i);
			c.timer += deltaTime;
			c.drawSelf(g2, r);
		}

		r.setSize(2, 2);
		r.x = (int)(Math.random() * 2);
		r.y = (int)(Math.random() * 2);
		g2.setColor(Color.getHSBColor(1, 1, 1));
		g2.fill(r);
	}

	final static int WIDTH = 1200;
	final static int HEIGHT = 675;
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(50,50));
		
		JButton hit = new JButton("HIT");
		hit.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        
		    }
		});
		panel.add(hit);
		JButton stay = new JButton("STAY");
		stay.addActionListener(new ActionListener()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        
		    }
		});
		panel.add(stay);
		
		frame.add(new BlackJackVisualize(), BorderLayout.CENTER);
		frame.add(panel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		
		class TimerListener implements ActionListener
		{
			public void actionPerformed(ActionEvent event)
			{
				frame.repaint();
			}
		}

		ActionListener listener = new TimerListener();
		Timer t = new Timer(1, listener);
		t.start();
	}
}
