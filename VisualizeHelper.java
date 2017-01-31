
import java.util.ArrayList;

public class VisualizeHelper
{
    static int aaa = BlackJackVisualize.HEIGHT - Card.HEIGHT;

	public static int distribute(int i, int size)
	{
		return (int)(200 + (BlackJackVisualize.WIDTH - 400) * (i+1)/(size+1f));
	}

	public static void benchHand(ArrayList<CardVisual> butts, int i, int size, float time)
	{
        for (int id = 0; id < butts.size(); id++)
        {
            CardVisual cv = butts.get(id);
            cv.slideTo((int)(distribute(i, size) + 20 * id - 10 * (butts.size()/2f)), aaa - 20, time);
        }
	}

	public static void centerHand(ArrayList<CardVisual> butts, float time)
	{
		for (int i = 0; i < butts.size(); i++)
        {
            CardVisual cv = butts.get(i);
            cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * butts.size() + 50 + 100 * i, BlackJackVisualize.HEIGHT/2, time);           
        }
	}

	public static void dealerHand(ArrayList<CardVisual> dealer)
	{
		for (int i = 0; i < dealer.size(); i++)
		{
			CardVisual cv = dealer.get(i);
			cv.slideTo(BlackJackVisualize.WIDTH/2 - 50 * dealer.size() + 50 + 100 * i, 100, 0.3f);			
		}
	}
}