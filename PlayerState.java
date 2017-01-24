import java.util.ArrayList;

public class PlayerState implements State
{
	int id;

	PlayerState(int id)
	{
		this.id = id;
	}

	public void enter(BlackJackVisualize game)
	{
		// Make button visible
		// Expand Hand
		ArrayList<CardVisual> a = game.handsToCards.get(id);

		for (int i = 0; i < a.size(); i++)
		{
			CardVisual cv = a.get(i);
			cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * a.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, 1f);			
		}
	}

	public void exit(BlackJackVisualize game)
	{
		// Remove the hand
	}

	float stateTime = 0;

	public void doStuff(BlackJackVisualize game, float dt)
	{
		stateTime += dt;
		if (dt > 1)
		{
			// CardVisual cv = new CardVisual(game.game.addCardToCurrent());
			// cv.slideTo((int)(190 + (BlackJackVisualize.WIDTH - 400) * (i+1)/(size+1f)), aaa - 20, 1f);
			// game.state = new PlayerState(1);
			// game.state.enter(game);
			// this.exit(game);
		}
	}
}