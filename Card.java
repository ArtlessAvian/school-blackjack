
/**
 * Write a description of class CartAlt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Card
{
	static String[] facesName = {"Spade", "Club", "Diamond", "Heart"};
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
					String numberPart;

					switch (j)
					{
						case 0 : {numberPart = "A"; break;}
						case 10 : {numberPart = "J"; break;}
						case 11 : {numberPart = "Q"; break;}
						case 12 : {numberPart = "K"; break;}
						default : {numberPart = (j + 1) + ""; break;}
					}

					BufferedImage img = null;

					try
					{
						File f = new File("Cards/" +
							numberPart + "_" + facesName[i].toUpperCase() + ".jpg");

						//System.out.println(f.getAbsolutePath());

						img = ImageIO.read(f);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				  
					cards[i * 13 + j] = new Card(valuesName[j] + " of " + facesName[i] + "s",
					j + 1, i,
					img);
				}
			}
		}
		return cards;
	}
	
	final String name;
	final int value; // literal
	final int suit; // 0 Spades, 1 Clubs, 2 Diamonds, 3 Hearts

	// Draw Stuff;
	final BufferedImage img;
	static BufferedImage backSide;

	int posX = 0;
	int posY = 0;
	
	public Card(String name, int value, int suit, BufferedImage img)
	{
		this.name = name;
		this.value = value;
		this.suit = suit;
		this.img = img;

		if (backSide == null)
		{
			try
			{
				File f = new File("Cards/BACK.jpg");

				//System.out.println(f.getAbsolutePath());

				Card.backSide = ImageIO.read(f);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void reset()
	{
		posX = 0;
		posY = 0;
	}

	public void drawSelf(Graphics2D g2, Rectangle r)
	{
        g2.drawImage(img, posX, posY, 165, 240, null);
        r.x = posX;
        r.y = posY;
        r.width = 165;
        r.height = 240;
        g2.setColor(Color.BLACK);
        g2.draw(r);
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
