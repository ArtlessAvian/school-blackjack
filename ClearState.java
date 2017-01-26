import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class ClearState implements State
{
	public void enter(BlackJackVisualize game)
	{
		for (CardVisual cv : CardVisual.allCards)
		{
			cv.slideTo(BlackJackVisualize.WIDTH*2/3, -20, 1);
		}
	}

	public void exit(BlackJackVisualize game)
	{

	}

	float stateTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		if (CardVisual.moving.isEmpty())
		{
			this.exit(game);
			game.state = new DealState();
			game.state.enter(game);
		}
	}

	public void drawSelf(Graphics2D g2, Rectangle r)
	{

	}
}