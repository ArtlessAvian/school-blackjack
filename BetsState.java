import javax.swing.*;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class BetsState implements State
{
    int id;
    Hand h;
    
    boolean allin;
    boolean done;

    BetsState(int id, BlackJackVisualize game)
    {
        this.id = id;
    }

    public void enter(BlackJackVisualize game)
    {
        this.h = game.game.allHands.get(id);
        h.bet = 0;
        game.game.switchToHand(id);

        game.bets = this;
        game.panel.setVisible(true);

        // Make button visible
        for (JButton b : game.betButtons)
        {
            b.setVisible(true);
        }
    }

    public void exit(BlackJackVisualize game)
    {
        game.panel.setVisible(false);

        for (JButton b : game.betButtons)
        {
            b.setVisible(false);
        }

        // Remove the hand
    }

    float stateTime = 0;
    float deadTime = 0;

    public void doStuff(BlackJackVisualize game, float dt)
    {
        stateTime += dt;
        
        if (allin || h.bet > h.money)
        {
            h.bet = h.money;
            System.out.println("hi");
            allin = false;
        }

        if (h.bet < 0)
        {
            h.bet = 0;
            System.out.println("hi");
        }

        if (done)
        {
            System.out.println("gargr");
            if (id + 1 < game.game.allHands.size())
            {
                System.out.println(id);
                game.state = new BetsState(id + 1, game);
                this.exit(game);
                game.state.enter(game);
                return;
            }
            else
            {
                // goto new state
                this.exit(game);
                game.state = new DealState();
                game.state.enter(game);
                return;
            }
        }
    }

    public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r)
    {
        int x = VisualizeHelper.distribute(id, game.handsToCards.size());

        g2.setColor(Color.getHSBColor(2/3f, 0.5f, 1f));
        
        String s = "\\/";

        g2.drawString(s,
            (x - g2.getFontMetrics().stringWidth(s)/2),
            BlackJackVisualize.HEIGHT/2 + 100);
    }
}