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
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> player) {


		for (int i = 0; i < getSizeOfHand(); i++) {
			if (discardPile.top().getRank() == hand.get(i).getRank()) {
				discardPile.add(this.hand.remove(i));
				//checks if its the winner
				if (this.hand.size() == 0) {
					return true;
				}
				return false;
			} else if (discardPile.top().getSuit() == hand.get(i).getSuit()) {
				discardPile.add(this.hand.remove(i));
				//checks if its the winner
				if (this.hand.size() == 0) {
					return true;
				}
				return false;
			}
		}
		//will pick the card from the pile
		pickupCard(drawPile);
		if (discardPile.top().getRank() == pickupCard(drawPile).getRank()) {
			discardPile.add(this.hand.remove(this.hand.size() - 1));
			//checks if its the winner
			if (this.hand.size() == 0) {
				return true;
			}
			return false;
		} else if (discardPile.top().getSuit() == pickupCard(drawPile).getSuit()) {
			discardPile.add(this.hand.remove(this.hand.size() - 1));
			//checks if its the winner
			if (this.hand.size() == 0) {
				return true;
			}
			return false;
		}


		return false;
	}

	// will pick cards until it gets the power card
	public boolean ExtraCardss(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> player) {


		//iterates if the power cards are in hand
		for (int i = 0; i < hand.size(); i++) {
			//checks if power card number two exist is in hands
			if (hand.get(i).getRank() == 2) {
				//cheeks the suit of the card in hand and the discarded card on the top
				if (this.hand.get(i).getSuit() == discardPile.top().getSuit()) {
					discardPile.add(this.hand.remove(i));
					//return card 2
					return true;
				}
				continue;
			}
			//checks if power card number Jack exist is in hands
			else if (hand.get(i).getRank() == 4) {
				//cheeks the suit of the card in hand and the discarded card on the top
				if (hand.get(i).getSuit() == discardPile.top().getSuit()) {
					discardPile.add(this.hand.remove(i));

					//return card 4
					return true;
				}
				continue;
			}
			//checks if power card number Jack exist is in hands
			else if (hand.get(i).getRank() == 7) {
				//cheeks the suit of the card in hand and the discarded card on the top
				if (hand.get(i).getSuit() == discardPile.top().getSuit()) {
					discardPile.add(this.hand.remove(i));
					//return card 7
					if (this.hand.size() == 0) {
						return true;
					}
					return false;
				}
				continue;
			}
			//checks if power card number eight exist is in hands
			else if (hand.get(i).getRank() == 8) {
				discardPile.add(this.hand.remove(i));
				//return card 8
				return true;
			}
		}

		// if there in no power card in hand, pick up cards until we get one
		while (!drawPile.isEmpty()) {
			Card pickedCard = pickupCard(drawPile);
			// check if the pickedCard is a power card
			if (pickedCard.getRank() == 2 || pickedCard.getRank() == 4 || pickedCard.getRank() == 7 ||
					pickedCard.getRank() == 8) {
				// if it is a power card and it can be played, play it
				if (discardPile.isValidPlay(pickedCard)) {
					discardPile.add(this.hand.remove(hand.size() - 1));
					// if our hand is empty, return true
					return this.hand.size() == 0;
				}
			}
		}
		// if our hand is empty, return true
		return this.hand.size() == 0;
	}
}

