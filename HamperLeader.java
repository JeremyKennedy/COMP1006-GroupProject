import java.util.ArrayList;
import java.util.Stack;

public class HamperLeader extends Player {
	public HamperLeader(Card[] cards) {
		super(cards);
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		return false;
	}
}
