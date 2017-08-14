import java.util.Objects;
import java.util.Stack;

public class DiscardPile {
	/* the actual discard pile */
	protected Stack<Card> cards = new Stack<Card>();

	/* the top card on the discard pile */
	public Card top() {
		if (cards.isEmpty()) {
			return new Card("None", "None");
		}
		return cards.peek();
	}

	/* add a card to the discard pile */
	public void add(Card card) {
		if (Objects.equals(card.rank, top().rank) || Objects.equals(card.suit, top().suit) ||
				Objects.equals(card.rank, Card.RANKS[8]) || cards.isEmpty()) {
			System.out.println("Added card to discard pile: " + card);
			cards.push(card);
		} else {
			System.out.println("ERROR: INVALID CARD ADDED TO DISCARD PILE");
			System.out.println("ERROR: Discarded card: " + card);
			System.out.println("ERROR: Card on top of deck: " + top());
		}
	}

	@Override
	public String toString() {
		return String.valueOf(cards);
	}
}
