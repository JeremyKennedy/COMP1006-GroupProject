import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class Crazy8Game {

	public static void main(String[] args) {
		int BAD_PLAYERS = 3;     // BadPlayer
		int RANDOM_PLAYERS = 0;  // RandomPlayer
		int EIGHTS_PLAYERS = 0;  // MindTheEights
		int HAMPER_PLAYERS = 0;  // HamperLeader
		int HIGH_PLAYERS = 1;    // DiscardHighPoints
		int EXTRA_PLAYERS = 0;   // ExtraCards


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
		for (int i = 0; i < RANDOM_PLAYERS; i++) {
			players.add(new RandomPlayer(getNewHand(drawPile)));
		}
		//		for (int i = 0; i < EIGHTS_PLAYERS; i++) {
		//			players.add(new MindTheEights(getNewHand(drawPile)));
		//		}
		for (int i = 0; i < HAMPER_PLAYERS; i++) {
			players.add(new HamperLeader(getNewHand(drawPile)));
		}
		for (int i = 0; i < HIGH_PLAYERS; i++) {
			players.add(new DiscardHighPoints(getNewHand(drawPile)));
		}
		for (int i = 0; i < EXTRA_PLAYERS; i++) {
			players.add(new ExtraCards(getNewHand(drawPile)));
		}
		for (int i = 0; i < players.size(); i++) {
			System.out.println("[" + players.get(i).getClass() + "] " + "Player " + i + "'s hand: " + players.get(i));

		}

		boolean win = false;
		int playerCount = players.size();
		int player = -1;    // start game with player 0
		int direction = 1;  // 1 = forwards, -1 = backwards

		discardPile.add(drawPile.pop());

		System.out.println("Draw pile is: " + drawPile);
		System.out.println("Discard pile is: " + discardPile);

		while (!win) {
			player = getNextPlayer(player, direction, playerCount);
			System.out.println("\nIt is player " + player + "'s turn! [" + players.get(player).getClass() + "]");
			System.out.println("Their hand: " + players.get(player));

			Card topDiscard = discardPile.top();
			System.out.println("Top of discard pile: " + topDiscard);

			Card topDraw = drawPile.isEmpty() ? new Card("None", "None") : drawPile.peek();
			System.out.println("Top of draw pile: " + topDraw);

			win = players.get(player).play(discardPile, drawPile, players);

			System.out.println("Their new hand: " + players.get(player));

			Card newTopDiscard = discardPile.top();
			if (topDiscard != newTopDiscard) {
				if (newTopDiscard.getRank() == 2) {
					player = getNextPlayer(player, direction, playerCount);
					System.out.printf(
							"A two was played! Player %d is now picking up cards and skipping their turn...%n", player);
					System.out.println("Their old hand: " + players.get(player));
					players.get(player).pickupCard(drawPile);
					players.get(player).pickupCard(drawPile);
					System.out.println("Their new hand: " + players.get(player));
				} else if (newTopDiscard.getRank() == 4) {
					player = getNextPlayer(player, direction, playerCount);
					System.out.printf("A four was played! Player %d is now skipping their turn...%n", player);
				} else if (newTopDiscard.getRank() == 7) {
					System.out.println("A seven was played! Switching play direction...");
					direction = direction * -1;
				}
			} else {
				System.out.println("No card was played, player passed their turn.");
			}


			//			if (drawPile.isEmpty()) {
			//				refillDrawPile(drawPile, discardPile);
			//			}
		}
		System.out.println("\n--------------------\nWinner is player " + player + "!");
	}

	private static int getNextPlayer(int currentPlayer, int direction, int playerCount) {
		if (direction == -1 && currentPlayer == 0) {  // prevents the current player from being negative,
			currentPlayer += playerCount;             // since floor division doesn't work as desired for negative numbers
		}
		return (currentPlayer + direction) % playerCount;
	}

	private static Card[] getNewHand(Stack<Card> drawPile) {
		Card[] hand = new Card[5];
		for (int i = 0; i < 5; i++) {
			hand[i] = drawPile.pop();
		}
		return hand;
	}

	public static void refillDrawPile(Stack<Card> drawPile, DiscardPile discardPile) {
		System.out.println("\nDraw pile is empty, shuffling!");
		Collections.shuffle(discardPile.cards);
		Stack<Card> cards = discardPile.cards;
		for (int i = 0; i < cards.size(); i++) {
			drawPile.push(discardPile.cards.pop());
		}
	}
}