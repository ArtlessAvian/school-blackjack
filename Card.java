
/**
 * Write a description of class CartAlt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card
{
    static String[] facesName = {"Spades", "Clubs", "Diamonds", "Hearts"};
    static String[] valuesName = {"Ace", "Two", "Three", "Four", "Five", "Six",
       "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
   
    private static Card[] cards = new Card[52];
   
    public static Card[] getCards()
    {
       if (cards[0] == null)
       {
           for (int i = 0; i < 4; i++)
           {
                for (int j = 0; j < 13; j++)
                {
                   cards[i * 13 + j] = new Card(valuesName[j] + " of " + facesName[i], j + 1, i);
                }
            }
       }
       return cards;
    }
   
    final String name;
    final int value; // literal
    final int suit; // 0 Spades, 1 Clubs, 2 Diamonds, 3 Hearts
   
    public Card(String name, int value, int suit)
    {
        this.name = name;
        this.value = value;
        this.suit = suit;
    }
   
    public static void main(String[] args)
    {
        Card[] cards = getCards();
        //for (int i = 0; i < 52; i++)
        //{
        //    System.out.println(i + " " + cards[i].name);
        //}
    }
}
