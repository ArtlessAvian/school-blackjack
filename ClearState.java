import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.util.Iterator;

public class ClearState implements State
{
	public void enter(BlackJackVisualize game)
	{
		System.out.println("here we go");
		for (CardVisual cv : CardVisual.allCards)
		{
			cv.slideTo(BlackJackVisualize.WIDTH*2/3, -50, 1);
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
			CardVisual.allCards.clear();
			for (Hand h : game.game.allHands)
			{
				h.cards.clear();
			}
			game.game.dealer.cards.clear();
			for (ArrayList<CardVisual> aaa : game.handsToCards)
			{
				aaa.clear();
			}
			game.dealer.clear();

			this.exit(game);
			game.state = new DealState();
			game.state.enter(game);
		}
	}

	public void drawSelf(Graphics2D g2, Rectangle r)
	{

	}
}