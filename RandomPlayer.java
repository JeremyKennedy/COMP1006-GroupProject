//Author: Jeremy

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class RandomPlayer extends Player {
	public RandomPlayer(Card[] cards) {
		super(cards);
		this.type = "RandomPlayer";
	}

	@Override
	public boolean play(DiscardPile discardPile, Stack<Card> drawPile, ArrayList<Player> players) {

		System.out.println("Strategy: playing randomly...");
		// shuffle the hand, then play normally
		Collections.shuffle(hand);
		return playNormally(discardPile, drawPile);
	}
}
