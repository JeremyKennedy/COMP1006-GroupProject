import java.util.ArrayList;
import java.util.Stack;

public class RandomPlayer extends Player {
	public RandomPlayer(Card[] cards) {
		super(cards);
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		return false;
	}
}
