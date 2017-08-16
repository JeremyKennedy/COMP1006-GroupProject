/*
Author: Jeremy

MindTheEights should always be aware of any eights they are holding.
This player will their eights until late in the game, but won't hold on to them too long (as they are worth a lot of points).
Once any opponent goes down to one card, it's time to play your eight.
If you have two eights, start playing them once an opponent goes down to two cards. (Expand this for 3 or 4 or more eights.)
*/

import java.util.ArrayList;
import java.util.Stack;

public class MindTheEights extends Player {


	public MindTheEights(Card[] cards) {
		super(cards);
		this.type = "MindTheEights";
	}

	/* play a card */
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {

		// find the size of the leader's hand
		int leaderSize = players.get(0).getSizeOfHand();
		for (Player player : players) {
			if (player.getSizeOfHand() < leaderSize) {
				leaderSize = player.getSizeOfHand();
			}
		}

		// count the number of eights in our hand
		int countOfEights = 0;
		for (Card card : hand) {
			if (card.getRank() == 8) {
				countOfEights++;
			}
		}

		// if the leader has fewer cards than we have eights, play an eight
		if (leaderSize <= countOfEights) {
			System.out.println("Strategy: playing an eight...");
			// iterate through our hand until we find a power card, then play it.
			for (int i = 0; i < getSizeOfHand(); i++) {
				// if it is a power card and it can be played, play it
				if (hand.get(i).getRank() == 8) {
					return discardCard(hand, i);
				}
			}
			System.out.println("Strategy: no eights in hand, playing normally...");
		}

		// otherwise we keep our eights for later
		else {
			System.out.println("Strategy: playing any card except an eight...");
			// iterate through our hand until we find a valid move other than an eight, and play it.
			for (int i = 0; i < getSizeOfHand(); i++) {
				// if it is a valid play and not an eight, play it
				if (discardPile.isValidPlay(hand.get(i)) && hand.get(i).getRank() != 8) {
					return discardCard(hand, i);
				}
			}
			System.out.println("Strategy: no valid moves in hand, playing normally...");
		}

		// if we reach this point it either means we couldn't satisfy our strategy, and we should play normally
		return playNormally(discardPile, drawPile);
	}
}

