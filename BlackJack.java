
/**
 * BlackJack
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import java.util.ArrayList;

// for command line stuff
import java.util.Scanner;

public class BlackJack
{
    public Deck deck;
    public Hand currentHand;
    public Hand dealer;
    public ArrayList<Hand> allHands;
    
    public BlackJack()
    {
        deck = new Deck();
        currentHand = new Hand();
        dealer = new Hand();
        //allHands = new ArrayList<Hand>();
        //allHands.add(new Hand());
    }
    
    public Hand switchTo(int player)
    {
        // TODO
        return null;
    }
    
    
    /**
     * The actual game
     */
    public static void main(String[] args)
    {
        BlackJack bj = new BlackJack();
        Scanner scanner = new Scanner(System.in);
        
        bj.currentHand.hit(bj.deck.drawCard());
        bj.currentHand.hit(bj.deck.drawCard());
        
        bj.dealer.hit(bj.deck.drawCard());
        bj.dealer.hit(bj.deck.drawCard());
        
        
        
        
        System.out.println("");
        String str = scanner.nextLine();
        System.out.println(str);
        
        
        
    }
}
