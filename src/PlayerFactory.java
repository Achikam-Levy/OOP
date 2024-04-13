/**
 * A factory class responsible for creating instances of different player types based on the provided type.
 * The supported player types include the strings: WHATEVER, CLEVER, GENIUS, and HUMAN.
 *
 * @author Achikam Levy
 * @see WhateverPlayer
 * @see CleverPlayer
 * @see GeniusPlayer
 * @see HumanPlayer
 */
public class PlayerFactory {

    // players names constants.
    private final String WHATEVER = "whatever";
    private final String CLEVER = "clever";
    private final String GENIUS = "genius";
    private final String HUMAN = "human";

    /**
     * Constructs a new PlayerFactory instance.
     * nothing is need to be done in this constructor.
     */
    public PlayerFactory() {
    }

    /**
     * Builds and returns a player object based on the specified player type.
     *
     * @param type The type of player to be created (whatever, clever, genius, human).
     * @return player object corresponding to the specified type, or null if an unsupported type is provided.
     */
    public Player buildPlayer(String type) {
        Player newPlayer;
        // suppose to get them already in lower case, but remain just in case...
        String playerType = type.toLowerCase();
        switch (playerType) {
            case WHATEVER -> newPlayer = new WhateverPlayer();
            case CLEVER -> newPlayer = new CleverPlayer();
            case GENIUS -> newPlayer = new GeniusPlayer();
            case HUMAN -> newPlayer = new HumanPlayer();
            default -> {
                // in case of invalid playerType
                return null;
            }
        }
        return newPlayer;
    }
}
