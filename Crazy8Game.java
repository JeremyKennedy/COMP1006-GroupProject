import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Crazy8Game {

	public static void main(String[] args) {
		
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
		
		/* players in the game */
		ArrayList<Player> players = new ArrayList<>();
		players.add(new BadPlayer(Arrays.copyOfRange(deck, 0, 5)));
		System.out.println("Player 0's hand: " + players.get(0));
		players.add(new BadPlayer(Arrays.copyOfRange(deck, 5, 10)));
		System.out.println("Player 1's hand: " + players.get(1));
		players.add(new BadPlayer(Arrays.copyOfRange(deck, 10, 15)));
		System.out.println("Player 2's hand: " + players.get(2));
		
		
		/* discard and draw piles */
		DiscardPile discardPile = new DiscardPile();
		Stack<Card> drawPile = new Stack<>();
		for (int i = 15; i < deck.length; i++) {
			drawPile.push(deck[i]);
		}

		boolean win = false;
		int player = -1;    // start game with player 0

		discardPile.add(drawPile.pop());

		System.out.println("Draw pile is: " + drawPile);
		System.out.println("Discard pile is: " + discardPile);

		while (!win) {
			player = (player + 1) % players.size();
			System.out.println("\nIt is player " + player + "'s turn!");
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
}