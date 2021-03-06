
/**
 * Write a description of class Hand here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Hand
{
    Hand parent;
    
    ArrayList<Card> cards;
    int money;
    int bet;
    
    public Hand(Card c)
    {
        this();
        this.cards.add(c);
    }
    
    public Hand()
    {
        this.cards = new ArrayList<Card>();
        this.money = 100;
    }
    
    public int determineValue()
    {
        int sum = 0;
        int aces = 0;
        for(int i = 0; i < cards.size(); i++)
        {
            Card card = cards.get(i);
            if (card.value == 1) {
                sum += 11;
                aces++;
            } else {
                sum += Math.min(card.value, 10);
            }
        }
        
        // Backtrack aces that are 11
        for (int i = aces; i > 0 && sum > 21; i--)
        {
            sum -= 10;
        }
        
        return sum;
    }
    
    public boolean isOver()
    {
        return determineValue() > 21;
    }
    
    public boolean isTwentyOne()
    {
        return determineValue() == 21;
    }
    
    public boolean hit(Card c)
    {
        cards.add(c);
        return isOver();
    }
    
    public String dealerString()
    {
        return cards.get(0).name;
    }
    
    public String toString()
    {
        String out = "";
        for (Card c : cards)
        {
            out += c.name + "\n";
        }
        out += "-- totaling to " + determineValue();
        return out;
    }
    
    /**
     * Hand Testing
     */
    public static void main(String[] args)
    {
        System.out.println("-------------------");
        for (int i = 0; i < 10; i++)
        {
            Hand h = new Hand();
            h.hit(Card.getCards()[0]);
            h.hit(Card.getCards()[(int)(Math.random() * 52)]);
            h.hit(Card.getCards()[(int)(Math.random() * 52)]);
            
            System.out.println(h.cards.get(0).name + ", " + h.cards.get(1).name + ", " + h.cards.get(2).name);
            System.out.println(h.determineValue());
        }
    }
}
