import java.util.ArrayList;
import java.util.Stack;

public class BadPlayer extends Player {

	public BadPlayer(Card[] cards) {
		super(cards);
		this.type = "BadPlayer";
	}

	/* play a card */
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {
		System.out.println("Strategy: normal play...");
		return playNormally(discardPile, drawPile);
	}
}