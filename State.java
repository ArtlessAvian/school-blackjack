public interface State
{
	void enter(BlackJackVisualize game);
	void exit(BlackJackVisualize game);
	void doStuff(BlackJackVisualize game, float dt);
}