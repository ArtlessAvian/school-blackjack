/**
 * BlackJack
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class DealState implements State
{
	public void enter(BlackJackVisualize game)
	{
		int aaa = BlackJackVisualize.HEIGHT - Card.HEIGHT;

		CardVisual cv;

		int size = game.game.allHands.size();
		for (int i = 0; i < size; i++)
		{
			game.game.switchToHand(i);
			cv = new CardVisual(game.game.addCardToCurrent());
			game.handsToCards.get(i).add(cv);

			cv = new CardVisual(game.game.addCardToCurrent());
			game.handsToCards.get(i).add(cv);

			VisualizeHelper.benchHand(game.handsToCards.get(i), i, size, 1f);
		}

		cv = new CardVisual(game.game.addCardToDealer());
		cv.slideTo(BlackJackVisualize.WIDTH/2 - 100 + 50, 100, 1f);
		game.dealer.add(cv);

		cv = new CardVisual(game.game.addCardToDealer());
		cv.slideTo(BlackJackVisualize.WIDTH/2 - 100 + 50 + 100, 100, 1f);
		game.dealer.add(cv);
		cv.isHidden = true;
	}

	public void exit(BlackJackVisualize game)
	{

	}

	float stateTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		stateTime += dt;
		if (CardVisual.moving.isEmpty() && stateTime > 0.2f)
		{
			// Goto new state
			game.state = new PlayerState(0, game);
			game.state.enter(game);
			this.exit(game);
		}
	}

	public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r) {}
}