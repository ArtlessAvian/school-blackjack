
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
    
    public void newRound()
    {
        currentHand.cards.clear();
        dealer.cards.clear();
        
        currentHand.hit(deck.drawCard());
        currentHand.hit(deck.drawCard());
        
        dealer.hit(deck.drawCard());
        dealer.hit(deck.drawCard());
    }
    
    /**
     * The actual game
     */
    public static void main(String[] args)
    {
        System.out.print("\u000C");
        
        BlackJack bj = new BlackJack();
        Scanner scanner = new Scanner(System.in);

        bj.newRound();
        
        System.out.println("The dealer has " + bj.dealer.dealerString());
        
        boolean stay = false;
        while (!stay && !bj.currentHand.isOver() && !bj.currentHand.isTwentyOne())
        {
            System.out.println("You have ");
            System.out.println(bj.currentHand);
            
            System.out.println("anything other than s hits");
            String str = scanner.nextLine();
            stay = (str.equals("s"));
            if (!stay)
            {
                Card c = bj.deck.drawCard();
                System.out.println("Drew a " + c.name);
                bj.currentHand.hit(c);
            }
        }
        
        System.out.println("------------------");
        
        if (bj.currentHand.isOver())
        {
            System.out.println("lol you went over biiiiiiiiiiiiiiiiiiiiitch");
        }
        else
        {             
            System.out.println("The dealer also has " + bj.dealer.cards.get(1).name);
            System.out.println("totalling to " + bj.dealer.determineValue());
            
            while (bj.dealer.determineValue() < 16)
            {
                Card c = bj.deck.drawCard();
                bj.dealer.hit(c);
                System.out.println("Drew a " + c.name);
                System.out.println("totalling to " + bj.dealer.determineValue());
            }
            
            System.out.println("------------------");
            
            if (bj.currentHand.isOver())
            {
                System.out.println("lol you r winnr");
            }
            else
            {
                if (bj.currentHand.determineValue() > bj.dealer.determineValue())
                {
                    System.out.println("gg ez no re");
                }
                else
                {
                    System.out.println("wtf this game is rigged");
                }
            }
        }
    }
}
