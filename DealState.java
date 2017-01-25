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

	}

	public void exit(BlackJackVisualize game)
	{

	}

	float stateTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		stateTime += dt;
		while (stateTime >= 0.2f)
		{
			System.out.println("hi");
			CardVisual testCard = new CardVisual(game.game.deck.drawCard());
			testCard.slideTo((int)(200 * Math.random()),(int)(200 * Math.random()),1);
			testCard.isHidden = false;
			stateTime -= 0.2f;
			game.cardsToDraw.add(testCard);
		}
	}
}