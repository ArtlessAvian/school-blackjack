
/**
 * Write a description of class CardVisual here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

public class CardVisual
{
	Card c;

	int initPosX = 0;
	int initPosY = 0;
	int posX = 0;
	int posY = 0;
	float timer = 0; // seconds
	float lerpTime = 0;
	boolean isMoving = false;
	boolean isHidden = false;

	static ArrayList<CardVisual> moving = new ArrayList<CardVisual>();
	
	public CardVisual(Card c)
	{
		this.c = c;

		initPosX = 0;
		initPosY = 0;
		posX = 0;
		posY = 0;
		timer = 0;
		lerpTime = 0;
		isMoving = false;
		isHidden = true;
	}

	public void slideTo(int x, int y, float time)
	{
		initPosX = posX;
		initPosY = posY;
		posX = x;
		posY = y;
		timer = 0;
		lerpTime = time;
		isMoving = true;
		CardVisual.moving.add(this);
	}

	public void drawSelf(Graphics2D g2, Rectangle r)
	{
		r.width = Card.WIDTH;
		r.height = Card.HEIGHT;

		if (timer > lerpTime)
		{
			isMoving = false;
			CardVisual.moving.remove(this);
		}

		if (isMoving)
		{
			// Uses Smooth Step
			float t = Math.min(timer/(float)lerpTime,1);
			float smoothStep = t*t*(3 - 2*t);

			r.x = (int)(initPosX + (posX - initPosX) * smoothStep);
			r.y = (int)(initPosY + (posY - initPosY) * smoothStep);
		}
		else
		{
			r.x = posX;
			r.y = posY;
		}

		if (isHidden)
		{
			g2.drawImage(Card.backSide, r.x, r.y, r.width, r.height, null);
		}
		else
		{
			g2.drawImage(c.img, r.x, r.y, r.width, r.height, null);
		}

		g2.setColor(Color.BLACK);
		g2.draw(r);
	}
}