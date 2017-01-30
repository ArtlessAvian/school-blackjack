
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
	int id;
	public Hand currentHand;
	public Hand dealer;
	public ArrayList<Hand> allHands;

	public BlackJack()
	{
		deck = new Deck();
		currentHand = new Hand();
		dealer = new Hand();
		allHands = new ArrayList<Hand>();
		allHands.add(currentHand);
		// allHands.add(new Hand());
		// allHands.add(new Hand());
	}
	
	public void switchToHand(int newID)
	{
		id = newID;
		currentHand = allHands.get(id);
	}

	public Card addCardToCurrent()
	{
		Card c = deck.drawCard();
		currentHand.cards.add(c);
		return c;
	}

	public Card addCardToDealer()
	{
		Card c = deck.drawCard();
		dealer.cards.add(c);
		return c;
	}

	/**
	 * The actual game
	 */
	public static void main(String[] args)
	{
		System.out.print("\u000C");
		
		BlackJack bj = new BlackJack();
		Scanner scanner = new Scanner(System.in);

		bj.currentHand.hit(bj.deck.drawCard());
		bj.currentHand.hit(bj.deck.drawCard());
		
		bj.dealer.hit(bj.deck.drawCard());
		bj.dealer.hit(bj.deck.drawCard());
		
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
			System.out.println("You have overdrawn!");
		}
		else
		{             
			System.out.println("The dealer has " + bj.dealer.cards.get(1).name);
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
				System.out.println("Dealer has overdrawn!");
			}
			else
			{
				if (bj.currentHand.determineValue() > bj.dealer.determineValue())
				{
					System.out.println("You Win!");
				}
				else
				{
					System.out.println("Push.");
				}
			}
		}
	}
}
