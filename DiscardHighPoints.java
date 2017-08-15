// Author: Jeremy

import java.util.ArrayList;
import java.util.Stack;

public class DiscardHighPoints extends Player {
	public DiscardHighPoints(Card[] cards) {
		super(cards);
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		return false;
	}
}
