
/**
 * Write a description of class Hand here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class Hand
{
    ArrayList<Card> cards;
    
    public Hand()
    {
        this.cards = new ArrayList<Card>();
    }
    
    public int determineValue(boolean aceIsEleven)
    {
        int sum = 0;
        for(int i = 0; i < cards.size(); i++)
        {
            Card card = cards.get(i);
            if(card.value == 1 && aceIsEleven){
                sum += 11;
            }else{
                sum += Math.min(card.value, 10);
            }
        }
        return sum;
    }
    
    public boolean isOver(boolean aceIsEleven)
    {
        return determineValue(aceIsEleven) > 21;
    }
    
    public boolean hit(Deck d)
    {
        cards.add(d.deck.remove(0));
        return isOver(true) || isOver(false);
    }
}
