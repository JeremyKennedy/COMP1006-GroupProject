// Author: Rajpal

import java.util.ArrayList;
import java.util.Stack;

public class HamperLeader extends Player {


	public HamperLeader(Card[] cards) {
		super(cards);
	}

	/* play a card */
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		// find the size of the next and previous leader's hand
		int nextPlayerSize = players.get(Crazy8Game.getNextPlayer()).getSizeOfHand();
		int prevPlayerSize = players.get(Crazy8Game.getPrevPlayer()).getSizeOfHand();
		int leaderSize = players.get(0).getSizeOfHand();
		for (Player player : players) {
			if (player.getSizeOfHand() < leaderSize) {
				leaderSize = player.getSizeOfHand();
			}
		}
		// if the next player is the leader, play a power card, if we have one.
		if (nextPlayerSize == leaderSize) {
			System.out.println("Strategy: hampering leader...");
			// iterate through our hand until we find a power card, then play it.
			for (int i = 0; i < getSizeOfHand(); i++) {
				// if it is a power card and it can be played, play it
				if (Card.isPowerCard(hand.get(i)) && discardPile.isValidPlay(hand.get(i))) {
					return discardCard(hand, i);
				}
			}
		}
		// if the previous player is the leader, we try to keep power cards.
		else if (prevPlayerSize == leaderSize) {
			System.out.println("Strategy: holding for leader...");
			// iterate through our hand until either NOT a power card, or a seven, and play it.
			for (int i = 0; i < getSizeOfHand(); i++) {
				// if it is a valid play...
				if (discardPile.isValidPlay(hand.get(i))) {
					// and either NOT a power card, or IS a seven, play it
					if (!Card.isPowerCard(hand.get(i)) || hand.get(i).getRank() == 7) {
						return discardCard(hand, i);
					}
				}
			}
		}
		// if we reach this point it either means we couldn't find a power card, or
		// the next and previous player is not the leader, and we should play normally

		System.out.println("Strategy: normal play...");
		// iterate through our hand until we find a card that can be played, then play it
		for (int i = 0; i < getSizeOfHand(); i++) {
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
	
