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
		if (stateTime > 1f && game.game.dealer.determineValue() < 16)
		{
			CardVisual cv;
			cv = new CardVisual(game.game.addCardToDealer());
			game.dealer.add(cv);

			for (int i = 0; i < game.dealer.size(); i++)
			{
				cv = game.dealer.get(i);
				cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * game.dealer.size() + 50 + 100 * i, 100, 0.3f);			
			}

			stateTime -= 1f;

			return;
		}

		if (game.game.dealer.isOver())
		{
			deadTime += dt;
		}

		if ((game.game.dealer.determineValue() >= 16 && stateTime > 2) || (game.game.dealer.isOver() && deadTime > 4 && CardVisual.moving.isEmpty()))
		{
			this.exit(game);
			game.state = new ClearState();
			game.state.enter(game);
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
			g2.drawString("EASY MONEYYYY",
				BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth("EASY MONEYYYY")/2,
				BlackJackVisualize.HEIGHT/2 + 20);
		}
		
		g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0f));
		g2.setFont(new Font("Impact", Font.PLAIN, 30)); 

		String s = "" + game.game.dealer.determineValue();

		g2.drawString(s,
			BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth(s)/2,
			BlackJackVisualize.HEIGHT/2 - 120);

        for (int i = 0; i < game.game.allHands.size(); i++)
        {
            s = "" + game.game.allHands.get(i).determineValue();

            g2.drawString(s,
                (VisualizeHelper.distribute(i, game.handsToCards.size())
                 - g2.getFontMetrics().stringWidth(s)/2),
                BlackJackVisualize.HEIGHT/2 + 90);
        }
	}
}