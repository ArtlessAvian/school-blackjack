
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

	long lastTime;

	PlayerState ahhhh;
	State state;
	float[] timePerFrame = new float[60];
	int thingy = 0;

	JPanel panel;

	static int MS_PER_FRAME = 1000/60;

	ArrayList<ArrayList<CardVisual>> handsToCards = new ArrayList<ArrayList<CardVisual>>();
	ArrayList<CardVisual> dealer = new ArrayList<CardVisual>();

	public BlackJackVisualize()
	{
		game = new BlackJack();

		for (int i = 0; i < game.allHands.size(); i++)
		{
			handsToCards.add(new ArrayList<CardVisual>());
		}

		r = new Rectangle();

		state = new DealState();
		state.enter(this);
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

		timePerFrame[thingy] = deltaTime;
		thingy = (thingy + 1) % 60;

		gameLogic(deltaTime);

		Graphics2D g2 = (Graphics2D)g;

		// Draw Background  
		r.setSize(WIDTH, HEIGHT);
		r.x = 0;
		r.y = 0;
		g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0.5f));
		g2.fill(r);

		// Draw each card
		for (int i = 0; i < CardVisual.allCards.size(); i++)
		{
			CardVisual c = CardVisual.allCards.get(i);
			c.timer += deltaTime;
			c.drawSelf(g2, r);
		}

		r.setSize(2, 2);
		r.x = (int)(Math.random() * 2);
		r.y = (int)(Math.random() * 2);
		g2.setColor(Color.getHSBColor(1, 1, 1));
		g2.fill(r);

		state.drawSelf(this, g2, r);

		float averageFPS = 0;
		for (int i = 0; i < 60; i++)
		{
			averageFPS += timePerFrame[i];
		}
		averageFPS /= 60f;
		averageFPS = 1/averageFPS;

		g2.setColor(Color.RED);

		g2.drawString(averageFPS + "", 50, HEIGHT - 70);
		g2.drawString(state.getClass().toString() + "", 50, HEIGHT - 120);
		g2.drawString(CardVisual.moving.size() + "", 50, HEIGHT - 180);
	}

	final static int WIDTH = 1200;
	final static int HEIGHT = 675;

	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		
		class PlayerStateMagic implements ActionListener
		{
			BlackJackVisualize game;
			int thingy;
			PlayerStateMagic(BlackJackVisualize gam, int thing)
			{
				game = gam;
				thingy = thing;
			}

			public void actionPerformed(ActionEvent e)
		    {
		    	switch (thingy)
		    	{
		    		case 0: {game.ahhhh.hit = true; break;}
		    		case 1: {game.ahhhh.stay = true; break;}
		    	}
		    }
		}

		BlackJackVisualize game = new BlackJackVisualize();

		game.panel = new JPanel();
		game.panel.setPreferredSize(new Dimension(50,50));

		JButton hit = new JButton("HIT");
		hit.addActionListener(new PlayerStateMagic(game, 0));
		game.panel.add(hit);

		JButton stay = new JButton("STAY");
		stay.addActionListener(new PlayerStateMagic(game, 1));
		game.panel.add(stay);

		game.panel.setVisible(false);
		
		frame.add(game, BorderLayout.CENTER);
		frame.add(game.panel, BorderLayout.SOUTH);
		
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
