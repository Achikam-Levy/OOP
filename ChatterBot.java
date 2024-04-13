import java.util.*;


/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * @author Dan Nirel
 */
class ChatterBot {

    // Prefix used to identify legal requests
    static private final String REQUEST_PREFIX = "say ";

    // Placeholder for the requested phrase in legal requests
    static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";

    // Placeholder for illegal requests
    static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";

    // Array to store replies to illegal requests
    private final String[] repliesToIllegalRequest;

    // Array to store replies to legal requests
    private final String[] legalRequestsReplies;

    // Name of the ChatterBot
    private final String name;

    // Random number generator for selecting random replies
    private final Random rand = new Random();

    /**
     * Constructor for ChatterBot class.
     *
     * @param name                    The name of the ChatterBot.
     * @param repliesToIllegalRequest Array of replies to illegal requests.
     * @param repliesToLegalRequest   Array of replies to legal requests.
     */
    ChatterBot(String name, String[] repliesToIllegalRequest, String[] repliesToLegalRequest) {
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        this.legalRequestsReplies = new String[repliesToLegalRequest.length];
        this.name = name;

        // Copy replies to illegal requests array
        for (int i = 0; i < repliesToIllegalRequest.length; i++) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }

        // Copy replies to legal requests array
        for (int i = 0; i < repliesToLegalRequest.length; i++) {
            this.legalRequestsReplies[i] = repliesToLegalRequest[i];
        }
    }

    /**
     * Responds to a legal request, who should start with the word "say",
     * by replacing the placeholder with the requested phrase.
     *
     * @param statement The legal request statement.
     * @return The response to the legal request.
     */
    public String replyToLegalRequest(String statement) {
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(
                this.legalRequestsReplies,
                PLACEHOLDER_FOR_REQUESTED_PHRASE,
                phrase);
    }

    /**
     * Responds to an illegal request by replacing the placeholder with the provided phrase.
     *
     * @param statement The illegal request statement.
     * @return The response to the illegal request.
     */
    public String replyToIllegalRequest(String statement) {
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(
                this.repliesToIllegalRequest,
                PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                phrase);
    }

    /**
     * Replaces a placeholder in a random pattern from the given array of replies.
     *
     * @param optionalReplies    Array of optional replies.
     * @param possiblePlaceHolder Placeholder to be replaced.
     * @param PlaceHolder        Replacement for the placeholder.
     * @return A random reply with the placeholder replaced.
     */
    public String replacePlaceholderInARandomPattern(
            String[] optionalReplies,
            String possiblePlaceHolder,
            String PlaceHolder) {
        int randomIndex = rand.nextInt(optionalReplies.length);
        String reply = optionalReplies[randomIndex];
        return reply.replaceAll(possiblePlaceHolder, PlaceHolder);
    }

    /**
     * Responds to a request, either legal or illegal, by calling the appropriate method.
     *
     * @param statement The request statement.
     * @return The response to the request.
     */
    public String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            return replyToLegalRequest(statement);
            // we don’t repeat the request prefix, so delete it from the reply
        }
        return replyToIllegalRequest(statement);
    }

    /**
     * Retrieves the name of the ChatterBot.
     *
     * @return The name of the ChatterBot.
     */
    public String getName() {
        return this.name;
    }
}
