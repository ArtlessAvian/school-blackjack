import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;

public class DealerState implements State
{
    public void enter(BlackJackVisualize game)
    {
        game.dealer.get(1).isHidden = false;
        winners = new boolean[game.handsToCards.size()];
    }

    public void exit(BlackJackVisualize game)
    {

    }

    float stateTime = 0;
    float payoutTime = 0;

    boolean[] winners;

    ArrayList<Float> xs = new ArrayList<Float>();
    ArrayList<Float> ys = new ArrayList<Float>();


    public void doStuff(BlackJackVisualize game, float dt)
    {
        stateTime += dt;
        if (stateTime > 1f && game.game.dealer.determineValue() <= 16)
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

        int dealerVal = game.game.dealer.determineValue();

        if (dealerVal > 16)
        {
            if (payoutTime == 0)
            {
                for (int i = 0; i < winners.length; i++)
                {
                    Hand h = game.game.allHands.get(i);
                    if (game.game.dealer.isOver())
                    {
                        h.money += h.bet;
                        winners[i] = true;
                        continue;                        
                    }
                    if (h.isOver())
                    {
                        h.money -= h.bet;
                        continue;
                    }
                    if (dealerVal > h.determineValue())
                    {
                        h.money -= h.bet;
                    }
                    if (dealerVal < h.determineValue())
                    {
                        h.money += h.bet;
                        winners[i] = true;
                    }
                }
            }
            payoutTime += dt;
        }

        if ((dealerVal > 16 && payoutTime > 4) && CardVisual.moving.isEmpty() && stateTime > 0.2f)
        {
            this.exit(game);
            game.state = new ClearState();
            game.state.enter(game);
        }
    }

    int MAGIC_NUMBER = 120;
    float lastCheck = 0;

    public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r)
    {
        if (payoutTime != 0)
        {
            boolean containsWinner = false;
            for (int i = 0; i < winners.length; i++)
            {
                if (winners[i])
                {
                    containsWinner = true;
                    break;
                }
            }

            for (int i = 0; i < xs.size(); i++)
            {
                g2.setColor(Color.GREEN);
                g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
                g2.drawString("$",
                    xs.get(i) - g2.getFontMetrics().stringWidth("$") / 2,
                    ys.get(i));

                // lol framerate dependent
                xs.set(i, (float)(xs.get(i) + 10 * Math.random() - 5));
                ys.set(i, ys.get(i) + 4);
            }

            // eww
            while (lastCheck < payoutTime)
            {
                for (int i = 0; i < winners.length; i++)
                {
                    if (winners[i])
                    {
                        xs.add((float)VisualizeHelper.distribute(i, winners.length));
                        ys.add(0f);
                    }
                }
                lastCheck += 0.1;
            }

            if (payoutTime > 0.3f)
            {
                if (containsWinner)
                {
                    g2.setColor(Color.getHSBColor(payoutTime * 3, 0.8f, 1));
                    g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
                    g2.drawString("holla holla get dolla",
                        BlackJackVisualize.WIDTH / 2 - g2.getFontMetrics().stringWidth("holla holla get dolla") / 2,
                        BlackJackVisualize.HEIGHT / 2 + 20);
                }
                else
                {
                    g2.setColor(Color.getHSBColor(1 / 2f, 0.8f, 1));
                    g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 48));
                    g2.drawString("o rip :C",
                        BlackJackVisualize.WIDTH / 2 - g2.getFontMetrics().stringWidth("o rip :C") / 2,
                        BlackJackVisualize.HEIGHT / 2 + 20);
                }
            }
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

            g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0f));
            g2.drawString(s,
                (VisualizeHelper.distribute(i, game.handsToCards.size())
                    - g2.getFontMetrics().stringWidth(s)/2),
                BlackJackVisualize.HEIGHT/2 + 90);

            if (game.game.allHands.get(i).isOver())
            {
                g2.setColor(Color.getHSBColor(0.1f, 1, 1));
                g2.drawString("DEAD",
                    (VisualizeHelper.distribute(i, game.handsToCards.size())
                        - g2.getFontMetrics().stringWidth("DEAD")/2),
                    BlackJackVisualize.HEIGHT/2 + 285);
            }

            if (game.game.allHands.get(i).isTwentyOne())
            {
                g2.setColor(Color.getHSBColor(System.nanoTime()/759179858f, 0.5f, 0.5f));
                g2.drawString("EZ $$$",
                    (VisualizeHelper.distribute(i, game.handsToCards.size())
                        - g2.getFontMetrics().stringWidth("EZ $$$")/2),
                    BlackJackVisualize.HEIGHT/2 + 285);
            }
        }
    }
}