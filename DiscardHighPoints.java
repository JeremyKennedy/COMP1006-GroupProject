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

		// sort the hand by points...
		Collections.sort(hand);

		// iterate through our hand until we find a card that can be played, then play it
		for (int i = getSizeOfHand() - 1; i >= 0; i--) {
			// if it can be played, play it
			if (discardPile.isValidPlay(hand.get(i))) {
				return discardCard(hand, i);
			}
		}
		// if we can't find a playable card, pick up cards until we find one
		while (!drawPile.isEmpty()) {
			Card pickedCard = pickupCard(drawPile);
			// if it can be played, play it
			if (discardPile.isValidPlay(pickedCard)) {
				return discardCard(hand, getSizeOfHand() - 1);
			}

		}

		// if we couldn't pick up a playable card, we must pass
		System.out.println("Passing!");
		return getSizeOfHand() == 0;
	}
}
