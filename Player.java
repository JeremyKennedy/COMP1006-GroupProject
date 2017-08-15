import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public abstract class Player {
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

	@Override
	public String toString() {
		return String.valueOf(hand);
	}
}