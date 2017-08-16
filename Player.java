import java.util.*;

public abstract class Player {
	public int points;  // represents count of points from winning games
	public String type;
	protected ArrayList<Card> hand;

	public Player(Card[] cards) {
		this.hand = new ArrayList<>(Arrays.asList(cards));
	}

	public int getSizeOfHand() {
		return hand.size();
	}

	/* play a card  */
	// return true if player wins game by playing last card
	// returns false otherwise
	// side effects: plays a card to top of discard Pile, possibly taking zero
	//               or more cards from the top of the drawPile
	//               card played must be valid card
	public abstract boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players);

	public Card pickupCard(Stack<Card> drawPile) {
		if (drawPile.isEmpty()) {
			System.out.println("ERROR: TRIED TO PICK UP FROM EMPTY DRAW PILE");
			return new Card("None", "None");
		}
		Card card = drawPile.pop();
		hand.add(card);
		System.out.println("Picked up card: " + card);
		return card;
	}

	// discard a card, and if the card is an eight, play the most common suit in the player's hand after
	public boolean discardCard(ArrayList<Card> hand, int cardNumber) {
		Card eightNewSuit = null;

		// if we have an eight, determine the best suit to play after
		if (hand.get(cardNumber).getRank() == 8) {

			// create an array with all the suits in the hand and remove the card we are about to play
			ArrayList<String> handSuits = new ArrayList<>();
			for (Card card : hand) {
				handSuits.add(card.getSuit());
			}
			handSuits.remove(cardNumber);

			// if the eight was our last card, leave the suit as the eight's suit
			if (handSuits.size() == 0) {
				eightNewSuit = hand.get(0);
			// if we only have one card left, set the suit to that
			} else if (handSuits.size() == 1) {
				eightNewSuit = new Card(handSuits.get(0), "8");
			// otherwise we find our most frequent suit
			} else {
				Collections.sort(handSuits);
				String mostCommonSuit = hand.get(0).getSuit();
				int mostCommonCount = 0;
				int count = 1;
				for (int i = 1; i < handSuits.size(); i++) {
					if (Objects.equals(handSuits.get(i - 1), handSuits.get(i))) {
						count++;
						if (count > mostCommonCount) {
							mostCommonCount++;
							mostCommonSuit = handSuits.get(i);
						}
					} else {
						count = 0;
					}
				}
				eightNewSuit = new Card(mostCommonSuit, "8");
			}

		}
		return discardCard(hand, cardNumber, eightNewSuit);

	}

	// discard a card, and if the card is an eight, play eightNewSuit after
	public boolean discardCard(ArrayList<Card> hand, int cardNumber, Card eightNewSuit) {
		if (Crazy8Game.discardPile.isValidPlay(hand.get(cardNumber))) {
			Card discardedCard = this.hand.remove(cardNumber);
			Crazy8Game.discardPile.add(discardedCard);
			if (discardedCard.getRank() == 8) {
				if (eightNewSuit != null && eightNewSuit.getRank() == 8) {
					System.out.println(
							"An eight was played! Player " + Crazy8Game.player + " is now setting the suit...");
					Crazy8Game.discardPile.add(eightNewSuit);

				} else {
					System.out.println("ERROR: INVALID CARD PLAYED ON TOP OF EIGHT - RANK MUST BE EIGHT");
				}
			}
		}
		return getSizeOfHand() == 0;
	}

	@Override
	public String toString() {
		return String.valueOf(hand);
	}

	protected boolean playNormally(DiscardPile discardPile, Stack<Card> drawPile) {
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