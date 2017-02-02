import javax.swing.*;
import java.util.ArrayList;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Color;

public class PlayerState implements State
{
    int id;
    ArrayList<CardVisual> a;

    boolean hit;
    boolean stay;
    boolean split;
    boolean doubledown;

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
        game.ahhhh = this;
        game.panel.setVisible(true);
      
        VisualizeHelper.centerHand(a, 0.3f);

        game.gameButtons[0].setVisible(true);
        game.gameButtons[1].setVisible(true);

        if (game.game.currentHand.cards.size() == 2)
        {
            game.gameButtons[2].setVisible(game.game.currentHand.cards.get(0).value == game.game.currentHand.cards.get(1).value);
        }

        if (game.game.currentHand.money >= game.game.currentHand.bet * 2)
        {
            game.gameButtons[3].setVisible(true);
        }
    }

    public void exit(BlackJackVisualize game)
    {
        game.panel.setVisible(false);

        // Remove the hand

        VisualizeHelper.benchHand(a, id, game.handsToCards.size(), 0.2f);

        for (JButton b : game.gameButtons)
        {
            b.setVisible(false);
        }
    }

    float stateTime = 0;
    float deadTime = 0;

    public void doStuff(BlackJackVisualize game, float dt)
    {
        stateTime += dt;
            
        if (game.game.currentHand.isOver())
        {
            deadTime += dt;
        }

        if (this.hit && !game.game.currentHand.isOver())
        {
            CardVisual cv;
            cv = new CardVisual(game.game.addCardToCurrent());
            a.add(cv);

            VisualizeHelper.centerHand(a, 0.5f);

            this.hit = false;

            game.gameButtons[2].setVisible(false);
            game.gameButtons[3].setVisible(false);

            return;
        }

        if (this.stay || game.game.currentHand.isTwentyOne() || (game.game.currentHand.isOver() && deadTime > 4 && CardVisual.moving.isEmpty()))
        {
            if (id + 1 < game.game.allHands.size())
            {
                System.out.println(id);
                game.state = new PlayerState(id + 1, game);
                this.exit(game);
                game.state.enter(game);
                return;
            }
            else
            {
                this.exit(game);
                game.state = new DealerState();
                game.state.enter(game);
                return;
            }
        }
        
        if (this.split)
        {
            Hand h = new Hand();
            h.money = 0;
            h.cards.add(game.game.currentHand.cards.remove(1));
            game.game.allHands.add(id+1, h);
            
            if (game.game.currentHand.parent == null)
            {
                h.parent = game.game.currentHand;
            }
            else
            {
                h.parent = game.game.currentHand.parent;
            }
            h.bet = h.parent.bet;

            ArrayList<CardVisual> handVisual = new ArrayList<CardVisual>();
            game.handsToCards.add(id+1, handVisual);
            handVisual.add(game.handsToCards.get(id).remove(1));
            
            this.split = false;
            game.gameButtons[2].setVisible(false);
            game.gameButtons[3].setVisible(false);
            
            for (int i = 0; i < game.handsToCards.size(); i++)
            {
                if (i == id) {continue;}
                
                VisualizeHelper.benchHand(game.handsToCards.get(i), i, game.handsToCards.size(), 0.2f);
            }
        }
        if (doubledown)
        {
            game.game.currentHand.bet *= 2;
            this.hit = true;
            this.stay = true;

            doubledown = false;
        }
    }

    public void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r)
    {
        if (deadTime != 0)
        {
            r.width = BlackJackVisualize.WIDTH;
            r.height = 60;

            r.x = 0;
            r.y = BlackJackVisualize.HEIGHT/2 - 30;

            g2.setColor(new Color(0.1f, 0.1f, 0.1f, 0.8f * Math.min(1, deadTime/2)));
            g2.fill(r);

            r.height = 50;
            r.y = BlackJackVisualize.HEIGHT/2 - 25;

            g2.fill(r);

            g2.setColor(new Color(1, 0f, 0f, 1 * Math.max(0,Math.min(1, deadTime/2 - 0.5f))));
            g2.setFont(new Font("Comic Sans MS", Font.PLAIN, 48)); 
            g2.drawString("YOU DIED",
                BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth("YOU DIED")/2,
                BlackJackVisualize.HEIGHT/2 + 20);
        }

        g2.setColor(Color.getHSBColor(2/3f, 0.5f, 0f));
        g2.setFont(new Font("Impact", Font.PLAIN, 30)); 

        String s = "" + game.game.currentHand.determineValue();

        g2.drawString(s,
            BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth(s)/2,
            BlackJackVisualize.HEIGHT/2 - 80);

        int val = Math.min(10, game.game.dealer.cards.get(0).value);
        s = "" + (val + 1) + " - " + (val + 11);

        g2.drawString(s,
            BlackJackVisualize.WIDTH/2 - g2.getFontMetrics().stringWidth(s)/2,
            BlackJackVisualize.HEIGHT/2 - 120);

        for (int i = 0; i < game.game.allHands.size(); i++)
        {
            if (i == id) {continue;}

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