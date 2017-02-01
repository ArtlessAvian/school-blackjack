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

    BetsState(int id, BlackJackVisualize game)
    {
        this.id = id;
        this.h = game.game.allHands.get(id);
        game.game.switchToHand(id);
    }

    public void enter(BlackJackVisualize game)
    {
        // Make button visible
        // Expand Hand
        game.ahhhh = this;
        game.panel.setVisible(true);
      
        VisualizeHelper.centerHand(a, 0.3f);
        
        if (game.game.currentHand.cards.size() == 2)
        {
            game.split.setVisible(game.game.currentHand.cards.get(0).value == game.game.currentHand.cards.get(1).value);
        }
    }

    public void exit(BlackJackVisualize game)
    {
        game.panel.setVisible(false);

        // Remove the hand

        VisualizeHelper.benchHand(a, id, game.handsToCards.size(), 0.2f);
    }

    float stateTime = 0;
    float deadTime = 0;

    public void doStuff(BlackJackVisualize game, float dt)
    {
        stateTime += dt;
        
        if (allIn)
        {
            h.bets = h.money;
        }
        
        if (h.bets >= h.money)
        {
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
                game.state = new PlayerState(0);
                game.state.enter(game);
                return;
            }
        }
        
        if (this.hit && !game.game.currentHand.isOver())
        {
            CardVisual cv;
            cv = new CardVisual(game.game.addCardToCurrent());
            a.add(cv);

            VisualizeHelper.centerHand(a, 0.5f);

            this.hit = false;
            
            game.split.setVisible(false);

            return;
        }
        
        if (this.split)
        {
            
        }
    }

    public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r)
    {

    }
}