import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Crazy8Game {
	static final int BAD_PLAYERS = 1;       // BadPlayer
	static final int RANDOM_PLAYERS = 0;    // RandomPlayer
	static final int EIGHTS_PLAYERS = 0;    // MindTheEights
	static final int HAMPER_PLAYERS = 0;    // HamperLeader
	static final int HIGH_PLAYERS = 1;      // DiscardHighPoints
	static final int EXTRA_PLAYERS = 1;     // ExtraCards

	static final int POINTS_GOAL = 10000;     // set to 0 for single round games, otherwise set point limit for multiple round games

	static final Card[] deck = createDeck();

	static boolean win;
	static DiscardPile discardPile;
	static Stack<Card> drawPile;
	static ArrayList<Player> players;
	static int playerCount;
	static int player;                      // start game with player 0
	static int direction;                   // 1 = forwards, -1 = backwards

	public static void main(String[] args) {
		createPlayers();

		resetDrawAndDiscard();
		dealCardsToPlayers();
		startGame();
	}

	private static void startGame() {
		win = false;
		boolean tie = false;
		player = -1;    // start game with player 0
		direction = 1;  // 1 = forwards, -1 = backwards
		int consecutivePasses = 0;

		System.out.println("Draw pile is: " + drawPile);
		System.out.println("Discard pile is: " + discardPile);

		while (!win && !tie) {
			player = getNextPlayer();
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
				consecutivePasses = 0;
				if (newTopDiscard.getRank() == 2) {
					player = getNextPlayer();
					System.out.printf(
							"A two was played! Player %d is now picking up cards and skipping their turn...%n", player);
					System.out.println("Their old hand: " + players.get(player));
					players.get(player).pickupCard(drawPile);
					players.get(player).pickupCard(drawPile);
					System.out.println("Their new hand: " + players.get(player));
				} else if (newTopDiscard.getRank() == 4) {
					player = getNextPlayer();
					System.out.printf("A four was played! Player %d is now skipping their turn...%n", player);
				} else if (newTopDiscard.getRank() == 7) {
					System.out.println("A seven was played! Switching play direction...");
					direction = direction * -1;
				}
			} else if (consecutivePasses >= playerCount - 1) {
				System.out.println("--------------------\n");
				System.out.println("No one can play, and the draw pile is empty. Tie game! Resetting...");
				emptyPlayersHands();
				tie = true;
			} else {
				System.out.println("No card was played, player passed their turn.");
				consecutivePasses++;
			}
		}

		// calculates and displays points
		int points = 0;
		int highestPoints = 0;
		int highestPlayer = -1;
		for (Player player_ : players) {      // named "player_" because "player" was taken
			for (Card card : player_.hand) {
				points += card.getPoints();
			}
		}
		players.get(player).points += points;
		if (!tie) {
			System.out.println("--------------------\n");
			System.out.printf("Winner is player %d, with %d points this round!%n", player, points);
		}
		System.out.println("\nLEADERBOARD:");
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).points > highestPoints) {
				highestPoints = players.get(i).points;
				highestPlayer = i;
			}
			System.out.printf("Player %d's total points: %d%n", i, players.get(i).points);
		}
		System.out.println("\n--------------------\n");
		if (highestPoints < POINTS_GOAL) {
			resetDrawAndDiscard();
			dealCardsToPlayers();
			startGame();
		} else {
			System.out.printf("Game over! Player %d has won the game with %d points!%n", highestPlayer, highestPoints);
		}
	}

	private static void createPlayers() {
		players = new ArrayList<>();
		for (int i = 0; i < BAD_PLAYERS; i++) {
			players.add(new BadPlayer(new Card[]{}));
		}
		for (int i = 0; i < RANDOM_PLAYERS; i++) {
			players.add(new RandomPlayer(new Card[]{}));
		}
		//		for (int i = 0; i < EIGHTS_PLAYERS; i++) {
		//			players.add(new MindTheEights(new Card[]{})));
		//		}
		for (int i = 0; i < HAMPER_PLAYERS; i++) {
			players.add(new HamperLeader(new Card[]{}));
		}
		for (int i = 0; i < HIGH_PLAYERS; i++) {
			players.add(new DiscardHighPoints(new Card[]{}));
		}
		for (int i = 0; i < EXTRA_PLAYERS; i++) {
			players.add(new ExtraCards(new Card[]{}));
		}
		playerCount = players.size();
	}

	private static void resetDrawAndDiscard() {
		discardPile = new DiscardPile();
		drawPile = new Stack<>();
		for (Card card : deck) {
			drawPile.push(card);
		}
		Collections.shuffle(drawPile);
		discardPile.add(drawPile.pop());
	}

	private static void dealCardsToPlayers() {
		for (int i = 0; i < players.size(); i++) {
			players.get(i).hand = getNewHand();
			System.out.println("[" + players.get(i).getClass() + "] " + "Player " + i + "'s hand: " + players.get(i));
		}
	}

	private static void emptyPlayersHands() {
		for (Player player : players) {
			player.hand = new ArrayList<>();
		}
	}

	private static ArrayList<Card> getNewHand() {
		ArrayList<Card> hand = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			hand.add(drawPile.pop());
		}
		return hand;
	}


	private static int getNextPlayer() {
		if (direction == -1 && player == 0) {  // prevents the current player from being negative,
			player += playerCount;             // since floor division doesn't work as desired for negative numbers
		}
		return (player + direction) % playerCount;
	}


	private static Card[] createDeck() {
		/* create the deck */
		Card[] deck = new Card[52];
		int index = 0;
		for (int r = 2; r <= 14; r += 1) {
			for (int s = 0; s < 4; s += 1) {
				deck[index++] = new Card(Card.SUITS[s], Card.RANKS[r]);
			}
		}
		return deck;
	}
}