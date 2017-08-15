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

		// if there in no power card in hand
		//pick card from pile until it gets the power cards
		while (true) {
			//

			Card pickedcard = pickupCard(drawPile);
			//check the drawPile is a power card
			if (drawPile.empty()) {
				break;
			}
			if (pickedcard.getRank() == 2) {
				//it will check if the top discarded card suit is the same as the new draw card suit
				if (discardPile.top().getSuit() == pickedcard.getSuit()) {
					discardPile.add(this.hand.remove(hand.size() - 1));
					break;
				}
				if (drawPile.empty()) {
					return false;
				}
				continue;
			} else if (pickedcard.getRank() == 4) {
				//it will check if the top discarded card suit is the same as the new draw card suit
				if (discardPile.top().getSuit() == pickedcard.getSuit()) {
					discardPile.add(this.hand.remove(hand.size() - 1));
					break;
				}
				if (drawPile.empty()) {
					return false;
				}
				continue;
			} else if (pickedcard.getRank() == 7) {
				//it will check if the top discarded card suit is the same as the new draw card suit
				if (discardPile.top().getSuit() == pickedcard.getSuit()) {
					discardPile.add(this.hand.remove(hand.size() - 1));
					break;
				}
				if (drawPile.empty()) {
					return false;
				}
				continue;
			} else if (pickedcard.getRank() == 8) {
				discardPile.add(this.hand.remove(hand.size() - 1));
				break;
			}
			if (drawPile.empty()) {

				return false;
			}
			// continue the loop until it gets the power cards
			continue;
		}

		// return false if player doesn't left with one card
		return false;
	}
}

