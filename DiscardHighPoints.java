/*
Author: Jeremy

DiscardHighPoints will try to discard their highest point cards as soon as they can.
This strategy aims to prevent the winner of a game (if is a different player) from obtaining too many points.
This player will try to change suits whenever possible to a different suit if they have high point cards of that different suit.
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class DiscardHighPoints extends Player {
	public DiscardHighPoints(Card[] cards) {
		super(cards);
		this.type = "DiscardHighPoints";
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		System.out.println("Strategy: discarding highest value card...");
		// sort the hand by points, then go through all cards in the hand, and play the first valid one
		while (true) {
			Collections.sort(hand);
			for (int i = getSizeOfHand() - 1; i >= 0; i--) {
				if (discardPile.isValidPlay(hand.get(i))) {
					return discardCard(hand, i);
				}
			}

			// pick up a card from the draw pile, if it isn't empty
			if (!drawPile.isEmpty()) {
				pickupCard(drawPile);
			} else {
				// nothing left to do, so pass
				System.out.println("Passing!");
				return getSizeOfHand() == 0;
			}
		}
	}
}
