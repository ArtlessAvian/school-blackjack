/**
 * BlackJack
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

import java.awt.Graphics2D;
import java.awt.Rectangle;

public interface State
{
	void enter(BlackJackVisualize game);
	void exit(BlackJackVisualize game);
	void doStuff(BlackJackVisualize game, float dt);
	void drawSelf(BlackJackVisualize game, Graphics2D g2, Rectangle r);
}