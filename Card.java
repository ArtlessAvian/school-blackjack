
/**
 * Write a description of class CartAlt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Card
{
   String[] facesName = {"Spades", "Clubs", "Diamonds", "Hearts"};
   String[] valuesName = {"Ace", "Two", "Three", "Four", "Five", "Six",
       "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
   
   public static Card[] cards = new Card[52];
   
   {
       for (int i = 0; i < 4; i++)
       {
           for (int j = 0; j < 13; j++)
           {
               cards[i * 13 + j] = new Card(valuesName[j] + " of " + facesName[i], j + 1, i);
           }
       }
   }
   
   String name;
   int value; // literal
   int suit; // 0 Spades, 1 Clubs, 2 Diamonds, 3 Hearts
   
   public Card(String name, int value, int suit)
   {
       this.name = name;
       this.value = value;
       this.suit = suit;
   }
}
