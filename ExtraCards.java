/*
Author Melaku 

ExtraCards will risk taking cards from the draw pile in an effort to get power cards.
They will be clever with this though. If the next player only has 1 card left,
they will keep picking a card until they get a power card (if they do not already have one) 
so that they can try to prevent the next player from winning. 
They will not take more than one extra card in the early rounds of the game.
They will not take extra cards if they already have power cards in their hand.
*/

import java.util.ArrayList;
import java.util.Stack;


public class ExtraCards extends Player {

	public ExtraCards(Card[] cards) {
		super(cards);
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {

		// get the player that's going after us
		Player nextPlayer = players.get(Crazy8Game.getNextPlayer());

		// if the next player has only one card left, we focus on power cards.
		if (nextPlayer.getSizeOfHand() == 1) {
			// iterate through our hand until we find a power card, then play it.
			for (int i = 0; i < getSizeOfHand(); i++) {
				// if it is a power card and it can be played, play it
				if (Card.isPowerCard(hand.get(i)) && discardPile.isValidPlay(hand.get(i))) {
					discardPile.add(this.hand.remove(i));
					// if our hand is empty, return true
					return getSizeOfHand() == 0;
				}
			}

			// if there in no power card in hand, pick up cards until we get one, then play it
			while (!drawPile.isEmpty()) {
				Card pickedCard = pickupCard(drawPile);
				// if it is a power card and it can be played, play it
				if (Card.isPowerCard(pickedCard) && discardPile.isValidPlay(pickedCard)) {
					discardPile.add(this.hand.remove(getSizeOfHand() - 1));
					// if our hand is empty, return true
					return getSizeOfHand() == 0;
				}
			}
		}

		// if we reach this point it either means we couldn't find a power card, or
		// the next player has more than one card left, and we should play normally

		// iterate through our hand until we find a card that can be played, then play it
		for (int i = 0; i < getSizeOfHand(); i++) {
			// if it can be played, play it
			if (discardPile.isValidPlay(hand.get(i))) {
				discardPile.add(this.hand.remove(i));
				// if our hand is empty, return true
				return getSizeOfHand() == 0;
			}
		}
		// if we can't find a playable card, pick up cards until we find one
		while (!drawPile.isEmpty()) {
			Card pickedCard = pickupCard(drawPile);
			// if it can be played, play it
			if (discardPile.isValidPlay(pickedCard)) {
				discardPile.add(this.hand.remove(getSizeOfHand() - 1));
				// if our hand is empty, return true
				return getSizeOfHand() == 0;
			}

		}

		// if we couldn't pick up a playable card, we must pass
		System.out.println("Passing!");
		return getSizeOfHand() == 0;
	}
}

