import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class BadPlayer extends Player {

	public BadPlayer(Card[] cards) {
		super(cards);
	}

	/* play a card */
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		// go through all cards in the hand, and play the first valid one
		while (true) {
			for (int i = 0; i < hand.size(); i++) {
				if (Objects.equals(hand.get(i).rank, discardPile.top().rank) ||
						Objects.equals(hand.get(i).suit, discardPile.top().suit) ||
						Objects.equals(hand.get(i).rank, Card.RANKS[8])) {
					discardPile.add(this.hand.remove(i));
					return this.hand.size() == 0;
				}
			}

			// pick up a card from the draw pile, if it isn't empty
			if (!drawPile.isEmpty()) {
				pickupCard(drawPile);
			} else {
				// nothing left to do, so pass
				System.out.println("Passing!");
				return this.hand.size() == 0;
			}
		}
	}
}