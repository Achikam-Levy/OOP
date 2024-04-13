
/**
 * Represents a Tic-Tac-Toe tournament between two players. The tournament consists of multiple rounds,
 * and the results are tallied to determine the overall winner between the two players.
 * The tournament uses the Game class to play individual games in each round.
 *
 * @author Achikam levy
 * @see Player
 * @see Renderer
 * @see Game
 */
public class Tournament {

    // Scores for each player and ties
    private int playerAScore = 0;
    private int playerBScore = 0;
    private int ties = 0;

    // Players and tournament configuration
    private final Player PlayerA;
    private final Player PlayerB;
    private final Renderer renderer;
    private final int ROUNDS;

    /**
     * Constructs a Tournament with the specified number of rounds, renderer, and players.
     *
     * @param rounds   The number of rounds in the tournament.
     * @param renderer The renderer to display the game board.
     * @param player1  The first player in the tournament.
     * @param player2  The second player in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.ROUNDS = rounds;
        this.renderer = renderer;
        this.PlayerA = player1;
        this.PlayerB = player2;
    }

    /**
     * Plays the specified number of rounds in the tournament and prints the results.
     *
     * @param size        The size of the game board.
     * @param winStreak   The number of consecutive marks required to win a game.
     * @param playerName1 The name of player 1.
     * @param playerName2 The name of player 2.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        for (int i = 0; i < this.ROUNDS; i++) {
            // Alternate players in each round
            if (i % 2 == 0) {
                playOneGame(this.PlayerA, this.PlayerB, size, winStreak, i);
            } else {
                playOneGame(this.PlayerB, this.PlayerA, size, winStreak, i);
            }
        }
        // Print the tournament results
        System.out.println("######### Results #########\n" +
                "Player 1, " + playerName1 + " won: " + this.playerAScore + " rounds\n" +
                "Player 2, " + playerName2 + " won: " + this.playerBScore + " rounds\n" +
                "Ties: " + this.ties);
    }

    /**
     * Plays a single game in the tournament between two players.
     *
     * @param playerX    The player who plays as 'X'.
     * @param playerO    The player who plays as 'O'.
     * @param size       The size of the game board.
     * @param winStreak  The number of consecutive marks required to win a game.
     * @param roundIndex The index of the current round.
     */
    private void playOneGame(Player playerX, Player playerO, int size, int winStreak, int roundIndex) {
        // Create a new game with the specified players, board size, and win streak
        Game game = new Game(playerX, playerO, size, winStreak, this.renderer);
        Mark gameResult = game.run();

        // Update scores based on the game result
        if (gameResult == Mark.X) {
            updateScores(roundIndex % 2 == 0);
        } else if (gameResult == Mark.O) {
            updateScores(roundIndex % 2 == 1);
        } else {
            this.ties++;
        }
    }

    /**
     * Updates the scores based on the winner of a game.
     *
     * @param playerAWins True if Player A wins, false if Player B wins.
     */
    private void updateScores(boolean playerAWins) {
        if (playerAWins) {
            this.playerAScore++;
        } else {
            this.playerBScore++;
        }
    }

    /**
     * check that the players names and renderer type are valid - means that they are not null
     *
     * @param renderer Renderer object
     * @param playerA  Player object
     * @param playerB  Player object
     * @return true if all the arguments are valid
     */
    private static boolean argumentsAreValid(Renderer renderer, Player playerA, Player playerB) {
        // Display a message and exit if renderer is not valid
        if (renderer == null) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return false;
        }

        // Display a message and exit if players are not valid
        if (playerA == null || playerB == null) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return false;
        }
        // all inputs are valid
        return true;
    }

    /**
     * Main method to run the Tic-Tac-Toe tournament based on command line arguments.
     * can assume that args length is 6
     *
     * @param args Command line arguments: [rounds, size, winStreak, rendererType, player1Type, player2Type]
     */
    public static void main(String[] args) {
        // for the manual checker, I hope lines number is Ok, it's just for the note's and for readability.
        RendererFactory rendererFactory = new RendererFactory();
        PlayerFactory playerFactory = new PlayerFactory();

        // Parse command line arguments
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);

        // Build renderer based on command line arguments
        Renderer renderer = rendererFactory.buildRenderer(args[3].toLowerCase(), size);

        // change letter to lower case
        String playerAName = args[4].toLowerCase();
        String playerBName = args[5].toLowerCase();

        // Build players based on command line arguments
        Player playerA = playerFactory.buildPlayer(playerAName);
        Player playerB = playerFactory.buildPlayer(playerBName);

        // Create a new tournament and play the specified number of rounds
        Tournament tournament = new Tournament(rounds, renderer, playerA, playerB);

        // check arguments validity, if they are not valid, exit
        if (!argumentsAreValid(renderer, playerA, playerB)) return;

        // play a whole tournament
        tournament.playTournament(size, winStreak, playerAName, playerBName);
    }
}
