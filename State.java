/**
 * BlackJack
 * 
 * @author (Ryan Chang, Ryan P. Tran, Ryan L. Tran) 
 * @version (1/17/17)
 */

public interface State
{
	void enter(BlackJackVisualize game);
	void exit(BlackJackVisualize game);
	void doStuff(BlackJackVisualize game, float dt);
}