import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class PlayerState implements State
{
	int id;
	ArrayList<CardVisual> a;

	boolean hit;
	boolean stay;

	PlayerState(int id, BlackJackVisualize game)
	{
		this.id = id;
		this.a = game.handsToCards.get(id);
		game.game.switchToHand(id);
	}

	public void enter(BlackJackVisualize game)
	{
		// Make button visible
		// Expand Hand
		game.ahhhh = this;
		game.panel.setVisible(true);
		for (int i = 0; i < a.size(); i++)
		{
			CardVisual cv = a.get(i);
			cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * a.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, 0.3f);			
		}
	}

	public void exit(BlackJackVisualize game)
	{
		game.panel.setVisible(false);

		// Remove the hand
		int aaa = BlackJackVisualize.HEIGHT - Card.HEIGHT;
		int size = game.game.allHands.size();

		for (int i = 0; i < a.size(); i++)
		{
			CardVisual cv = a.get(i);
			cv.slideTo((int)(190 + (BlackJackVisualize.WIDTH - 400) * (id+1)/(size+1f)) + 20 * i, aaa - 20, 0.2f);
		}
	}

	float stateTime = 0;
	float deadTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		if (this.stay || (game.game.currentHand.isOver() && deadTime > 4 && CardVisual.moving.isEmpty()))
		{
			if (id + 1 < game.game.allHands.size())
			{
				System.out.println(id);
				game.state = new PlayerState(id + 1, game);
				this.exit(game);
				game.state.enter(game);
			}
			else
			{
				// goto new state
				this.exit(game);
				game.state = new DealerState();
				game.state.enter(game);
			}
		}

		stateTime += dt;
		if (this.hit && !game.game.currentHand.isOver())
		{
			CardVisual cv;
			cv = new CardVisual(game.game.addCardToCurrent());
			a.add(cv);

			for (int i = 0; i < a.size(); i++)
			{
				cv = a.get(i);
				cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * a.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, 0.3f);			
			}

			this.hit = false;

			return;
		}

		if (game.game.currentHand.isOver())
		{
			deadTime += dt;
		}
	}

	int MAGIC_NUMBER = 120;

	public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r)
	{
		if (deadTime != 0)
		{
			r.width = BlackJackVisualize.WIDTH;
			r.height = 60;

			r.x = 0;
			r.y = BlackJackVisualize.HEIGHT/2 - 30;

			g2.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f * Math.min(1, deadTime/2)));
			g2.fill(r);

			r.height = 50;
			r.y = BlackJackVisualize.HEIGHT/2 - 25;

			g2.fill(r);

			g2.setColor(new Color(1, 0f, 0f, 1 * Math.max(0,Math.min(1, deadTime/2 - 0.5f))));
			g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 48)); 
			g2.drawString("YOU DIED",
				BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth("YOU DIED")/2,
				BlackJackVisualize.HEIGHT/2 + 20);
		}

		g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0f));
		g2.setFont(new Font("Impact", Font.PLAIN, 30)); 

		String s = "" + game.game.currentHand.determineValue();

		g2.drawString(s,
			BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth(s)/2,
			BlackJackVisualize.HEIGHT/2 - 80);

		int val = Math.min(10, game.game.dealer.cards.get(0).value);
		s = "" + (val + 1) + " - " + (val + 11);

		g2.drawString(s,
			BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth(s)/2,
			BlackJackVisualize.HEIGHT/2 - 120);

		for (int i = 0; i < game.game.allHands.size(); i++)
		{
			if (i == id) {continue;}

			s = "" + game.game.allHands.get(i).determineValue();
			int size = game.game.allHands.size();

			g2.drawString(s,
				(int)(190 + (BlackJackVisualize.WIDTH - 400) * (i+1)/(size+1f)) + 20 * i
				 - g2.getFontMetrics().stringWidth(s)/2,
				BlackJackVisualize.HEIGHT/2 + 120);
		}
	}
}