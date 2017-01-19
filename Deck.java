
/**
 * Models a deck
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
    ArrayList<Card> deck;
    
    public Deck()
    {
        this.deck = new ArrayList<Card>();
    }
    
    public void shuffle()
    {
        Collections.shuffle(this.deck);
    }
    
    public Card drawCard()
    {
        if (deck.isEmpty())
        {
            Card[] cards = Card.getCards();
            for (Card c : cards)
            {
                this.deck.add(c);
            }
            this.shuffle();
        }
        
        return this.deck.remove(this.deck.size()-1);
    }
}
