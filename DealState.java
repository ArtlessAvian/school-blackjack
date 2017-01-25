/**
 * BlackJack
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

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
			cv.slideTo((int)(190 + (BlackJackVisualize.WIDTH - 400) * (i+1)/(size+1f)), aaa - 20, 0.9f);
			game.handsToCards.get(i).add(cv);

			cv = new CardVisual(game.game.addCardToCurrent());
			cv.slideTo((int)(210 + (BlackJackVisualize.WIDTH - 400) * (i+1)/(size+1f)) + 20, aaa - 20, 1f);
			game.handsToCards.get(i).add(cv);
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
		if (CardVisual.moving.isEmpty())
		{
			// Goto new state
			game.state = new PlayerState(0, game);
			game.state.enter(game);
			this.exit(game);
		}
	}
}