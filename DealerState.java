import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class DealerState implements State
{
	public void enter(BlackJackVisualize game)
	{
		game.dealer.get(1).isHidden = false;
	}

	public void exit(BlackJackVisualize game)
	{

	}

	float stateTime = 0;
	float deadTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		stateTime += dt;
		if (stateTime > 0.2f && game.game.dealer.determineValue() < 16)
		{
			CardVisual cv;
			cv = new CardVisual(game.game.addCardToDealer());
			game.dealer.add(cv);

			for (int i = 0; i < game.dealer.size(); i++)
			{
				cv = game.dealer.get(i);
				cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * game.dealer.size() + 50 + 100 * i, 100, 0.3f);			
			}

			stateTime -= 0.2f;

			return;
		}

		if (game.game.dealer.isOver())
		{
			deadTime += dt;
		}

		if (game.game.dealer.determineValue() >= 16 || (game.game.dealer.isOver() && deadTime > 4 && CardVisual.moving.isEmpty()))
		{

		}
	}

	int MAGIC_NUMBER = 120;

	public void drawSelf(Graphics2D g2, Rectangle r)
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
			g2.drawString("YOU DIED", BlackJackVisualize.WIDTH/2 - MAGIC_NUMBER, BlackJackVisualize.HEIGHT/2 + 20);
		}
	}
}