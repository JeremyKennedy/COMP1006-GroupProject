import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Stack;

public class Crazy8Game {
	static final Card[] deck = createDeck();

	static int BAD_PLAYERS;       // BadPlayer
	static int RANDOM_PLAYERS;    // RandomPlayer
	static int EIGHTS_PLAYERS;    // MindTheEights
	static int HAMPER_PLAYERS;    // HamperLeader
	static int HIGH_PLAYERS;      // DiscardHighPoints
	static int EXTRA_PLAYERS;     // ExtraCards
	static int POINTS_GOAL = 1;     // set to 0 for single round games, otherwise set point limit for multiple round games

	static int currentRound = 0;
	static boolean win;
	static DiscardPile discardPile;
	static Stack<Card> drawPile;
	static ArrayList<Player> players;
	static int playerCount;
	static int player;                      // start game with player 0
	static int direction;                   // 1 = forwards, -1 = backwards

	public static void main(String[] args) {
		if (args.length > 0) {
			if (Objects.equals(args[0], "eights")) {
				EIGHTS_PLAYERS = 1;    // MindTheEights
				RANDOM_PLAYERS = 2;    // RandomPlayer
			} else if (Objects.equals(args[0], "hamper")) {
				HAMPER_PLAYERS = 1;    // HamperLeader
				RANDOM_PLAYERS = 2;    // RandomPlayer
			} else if (Objects.equals(args[0], "high")) {
				HIGH_PLAYERS = 1;      // DiscardHighPoints
				RANDOM_PLAYERS = 2;    // RandomPlayer
			} else if (Objects.equals(args[0], "extra")) {
				EXTRA_PLAYERS = 1;     // ExtraCards
				RANDOM_PLAYERS = 2;    // RandomPlayer
			}
		} else {
			BAD_PLAYERS = 1;       // BadPlayer
			RANDOM_PLAYERS = 1;    // RandomPlayer
			EIGHTS_PLAYERS = 1;    // MindTheEights
			HAMPER_PLAYERS = 1;    // HamperLeader
			HIGH_PLAYERS = 1;      // DiscardHighPoints
			EXTRA_PLAYERS = 1;     // ExtraCards
			POINTS_GOAL = 2000;
		}
		createPlayers();

		resetDrawAndDiscard();

		if (args.length > 0) {
			players.get(0).hand = new ArrayList<Card>();
			if (Objects.equals(args[0], "eights")) {
				players.get(0).hand.add(new Card("Hearts", "8"));
				players.get(0).hand.add(new Card("Hearts", "2"));
				players.get(0).hand.add(new Card("Spades", "3"));
				players.get(0).hand.add(new Card("Clubs", "4"));
				players.get(0).hand.add(new Card("Diamonds", "5"));
			} else if (Objects.equals(args[0], "hamper")) {
				players.get(0).hand.add(new Card("Hearts", "8"));
				players.get(0).hand.add(new Card("Spades", "8"));
				players.get(0).hand.add(new Card("Hearts", "3"));
				players.get(0).hand.add(new Card("Spades", "3"));
				players.get(0).hand.add(new Card("Hearts", "7"));
			} else if (Objects.equals(args[0], "high")) {
				players.get(0).hand.add(new Card("Hearts", "8"));
				players.get(0).hand.add(new Card("Spades", "8"));
				players.get(0).hand.add(new Card("Hearts", "2"));
				players.get(0).hand.add(new Card("Spades", "3"));
				players.get(0).hand.add(new Card("Hearts", "4"));
			} else if (Objects.equals(args[0], "extra")) {
				players.get(0).hand.add(new Card("Hearts", "8"));
				players.get(0).hand.add(new Card("Spades", "8"));
				players.get(0).hand.add(new Card("Hearts", "3"));
				players.get(0).hand.add(new Card("Spades", "3"));
				players.get(0).hand.add(new Card("Clubs", "4"));
				players.get(0).hand.add(new Card("Diamonds", "5"));
				players.get(0).hand.add(new Card("Hearts", "6"));
			}
			System.out.println("[" + players.get(0).type + "] " + "Player " + 0 + "'s hand: " + players.get(0));
			dealCardsToPlayer(1);
			dealCardsToPlayer(2);
		} else {
			dealCardsToPlayers();
		}
		startGame();
	}

	private static void startGame() {
		win = false;
		boolean tie = false;
		player = -1;    // start game with player 0
		direction = 1;  // 1 = forwards, -1 = backwards
		int consecutivePasses = 0;
		currentRound++;
		System.out.println("\nDraw pile is: " + drawPile);
		System.out.println("Discard pile is: " + discardPile);
		System.out.println("\nBeginning round " + currentRound + "!");

		while (!win && !tie) {
			player = getNextPlayer();
			System.out.printf("\nIt is player %d's turn! [%s]%n", player, players.get(player).type);
			System.out.println("Their hand: " + players.get(player));

			Card topDiscard = discardPile.top();
			System.out.println("Top of discard pile: " + topDiscard);

			Card topDraw = drawPile.isEmpty() ? new Card("None", "None") : drawPile.peek();
			System.out.println("Top of draw pile: " + topDraw);

			win = players.get(player).play(discardPile, drawPile, players);

			System.out.println("Their new hand: " + players.get(player));

			// logic for power cards and tie-game detection
			Card newTopDiscard = discardPile.top();
			if (win) {
				break;
			} else if (topDiscard != newTopDiscard) {
				consecutivePasses = 0;
				if (newTopDiscard.getRank() == 2) {
					player = getNextPlayer();
					System.out.printf(
							"A two was played! Player %d is now picking up cards and skipping their turn...%n", player);
					System.out.println("Their old hand: " + players.get(player));
					for (int i = 0; i < 2 && !drawPile.isEmpty(); i++) {
						players.get(player).pickupCard(drawPile);
					}
					System.out.println("Their new hand: " + players.get(player));
				} else if (newTopDiscard.getRank() == 4) {
					player = getNextPlayer();
					System.out.printf("A four was played! Player %d is now skipping their turn...%n", player);
				} else if (newTopDiscard.getRank() == 7) {
					System.out.println("A seven was played! Switching play direction...");
					direction = direction * -1;
				}
			} else if (consecutivePasses >= playerCount) {
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
		for (Player aPlayer : players) {
			for (Card card : aPlayer.hand) {
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
			System.out.printf("[%s] Player %d's total points: %d%n", players.get(i).type, i, players.get(i).points);
		}
		System.out.println("\n--------------------\n");

		// continue playing if we haven't reached the point goal yet, otherwise end the game
		if (highestPoints < POINTS_GOAL) {
			resetDrawAndDiscard();
			dealCardsToPlayers();
			startGame();
		} else {
			System.out.printf("Game over! Player %d has won the game with %d points after %d rounds!%n", highestPlayer,
					highestPoints, currentRound);
		}
	}

	// using the player-count constants, create the appropriate number of players
	private static void createPlayers() {
		players = new ArrayList<>();
		for (int i = 0; i < EIGHTS_PLAYERS; i++) {
			players.add(new MindTheEights(new Card[]{}));
		}
		for (int i = 0; i < HAMPER_PLAYERS; i++) {
			players.add(new HamperLeader(new Card[]{}));
		}
		for (int i = 0; i < HIGH_PLAYERS; i++) {
			players.add(new DiscardHighPoints(new Card[]{}));
		}
		for (int i = 0; i < EXTRA_PLAYERS; i++) {
			players.add(new ExtraCards(new Card[]{}));
		}
		for (int i = 0; i < BAD_PLAYERS; i++) {
			players.add(new BadPlayer(new Card[]{}));
		}
		for (int i = 0; i < RANDOM_PLAYERS; i++) {
			players.add(new RandomPlayer(new Card[]{}));
		}
		playerCount = players.size();
	}

	// reset the draw and discard piles, shuffle the draw pile, then discard one card
	private static void resetDrawAndDiscard() {
		discardPile = new DiscardPile();
		drawPile = new Stack<>();
		for (Card card : deck) {
			drawPile.push(card);
		}
		Collections.shuffle(drawPile);
		discardPile.cards.push(drawPile.pop());
	}

	// deal out five cards to every player in the game (seven cards if two player game)
	private static void dealCardsToPlayers() {
		for (int i = 0; i < players.size(); i++) {
			dealCardsToPlayer(i);
		}
	}

	// deal out five cards to every player in the game (seven cards if two player game)
	private static void dealCardsToPlayer(int player) {
		ArrayList<Card> hand = new ArrayList<>();
		int cardCount = playerCount == 2 ? 7 : 5;
		for (int j = 0; j < cardCount; j++) {
			hand.add(drawPile.pop());
		}
		players.get(player).hand = hand;
		System.out.println(
				"[" + players.get(player).type + "] " + "Player " + player + "'s hand: " + players.get(player));
	}

	// reset every player's hand (used for tie games to make sure no points are awarded)
	private static void emptyPlayersHands() {
		for (Player player : players) {
			player.hand = new ArrayList<>();
		}
	}

	// returns the index of the next player in the players array
	static int getNextPlayer() {
		int nextPlayer = player + direction;
		if (nextPlayer == -1) {                 // prevents the current player from being negative,
			nextPlayer += playerCount;          // since floor division doesn't work as desired for negative numbers
		}
		return nextPlayer % playerCount;
	}

	// returns the index of the previous player in the players array
	static int getPrevPlayer() {
		int prevPlayer = player - direction;
		if (prevPlayer == -1) {                 // prevents the current player from being negative,
			prevPlayer += playerCount;          // since floor division doesn't work as desired for negative numbers
		}
		return prevPlayer % playerCount;
	}

	// creates a full 52 card deck, to be used when refilling the draw pile
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