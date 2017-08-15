import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Crazy8Game {

	public static void main(String[] args) {
		int BAD_PLAYERS = 3;     // BadPlayer
		int RANDOM_PLAYERS = 1;  // RandomPlayer
		int EIGHTS_PLAYERS = 1;  // MindTheEights
		int HAMPER_PLAYERS = 1;  // HamperLeader
		int HIGH_PLAYERS = 1;    // DiscardHighPoints
		int EXTRA_PLAYERS = 1;   // ExtraCards


		/* create the deck */
		Card[] deck = new Card[52];
		int index = 0;
		for (int r = 2; r <= 14; r += 1) {
			for (int s = 0; s < 4; s += 1) {
				deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
		
		/* shuffle the deck */
		Random rnd = new Random();
		Card swap;
		for (int i = deck.length - 1; i >= 0; i = i - 1) {
			int pos = rnd.nextInt(i + 1);
			swap = deck[pos];
			deck[pos] = deck[i];
			deck[i] = swap;
		}		

		/* discard and draw piles */
		DiscardPile discardPile = new DiscardPile();
		Stack<Card> drawPile = new Stack<>();
		for (int i = 0; i < deck.length; i++) {
			drawPile.push(deck[i]);
		}
		deck = null;

		/* players in the game */
		ArrayList<Player> players = new ArrayList<>();
		for (int i = 0; i < BAD_PLAYERS; i++) {
			players.add(new BadPlayer(getNewHand(drawPile)));
		}
		//		for (int i = 0; i < RANDOM_PLAYERS; i++) {
		//			players.add(new RandomPlayer(getNewHand(drawPile)));
		//		}
		//		for (int i = 0; i < EIGHTS_PLAYERS; i++) {
		//			players.add(new MindTheEights(getNewHand(drawPile)));
		//		}
		//		for (int i = 0; i < HAMPER_PLAYERS; i++) {
		//			players.add(new HamperLeader(getNewHand(drawPile)));
		//		}
		//		for (int i = 0; i < HIGH_PLAYERS; i++) {
		//			players.add(new DiscardHighPoints(getNewHand(drawPile)));
		//		}
		for (int i = 0; i < EXTRA_PLAYERS; i++) {
			players.add(new ExtraCards(getNewHand(drawPile)));
		}
		for (int i = 0; i < players.size(); i++) {
			System.out.println("[" + players.get(i).getClass() + "] " + "Player " + i + "'s hand: " + players.get(i));

		}

		boolean win = false;
		int player = -1;    // start game with player 0

		discardPile.add(drawPile.pop());

		System.out.println("Draw pile is: " + drawPile);
		System.out.println("Discard pile is: " + discardPile);

		while (!win) {
			player = (player + 1) % players.size();
			System.out.println("\nIt is player " + player + "'s turn! [" + players.get(player).getClass() + "]");
			System.out.println("Their hand: " + players.get(player));
			Card topDraw = drawPile.isEmpty() ? new Card("None", "None") : drawPile.peek();
			System.out.println("Top of draw pile: " + topDraw);
			System.out.println("Top of discard pile: " + discardPile.top());

			win = players.get(player).play(discardPile, drawPile, players);

			// topDraw = drawPile.isEmpty() ? new Card("None", "None") : drawPile.peek();
			// System.out.println("Top of draw pile: " + topDraw);
			// System.out.println("Top of discard pile: " + discardPile.top());
		}
		System.out.println("\n--------------------\nWinner is player " + player + "!");
	}

	private static Card[] getNewHand(Stack<Card> drawPile) {
		Card[] hand = new Card[5];
		for (int i = 0; i < 5; i++) {
			hand[i] = drawPile.pop();
		}
		return hand;
	}
}