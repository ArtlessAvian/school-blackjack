import java.util.ArrayList;

public class PlayerState implements State
{
	int id;
	ArrayList<CardVisual> a;

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
		for (int i = 0; i < a.size(); i++)
		{
			CardVisual cv = a.get(i);
			cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * a.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, 0.3f);			
		}
	}

	public void exit(BlackJackVisualize game)
	{
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

	public void doStuff(BlackJackVisualize game, float dt)
	{
		stateTime += dt;
		if (stateTime > 1)
		{
			if (game.game.currentHand.determineValue() < 18)
			{
				CardVisual cv;
				cv = new CardVisual(game.game.addCardToCurrent());
				a.add(cv);

				for (int i = 0; i < a.size(); i++)
				{
					cv = a.get(i);
					cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * a.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, 0.3f);			
				}

				stateTime -= 1;
				return;
			}

			if (id + 1 < game.game.allHands.size())
			{
				System.out.println(id);
				game.state = new PlayerState(id + 1, game);
				game.state.enter(game);
				this.exit(game);
			}
			else
			{
				// goto new state
			}
		}
	}
}