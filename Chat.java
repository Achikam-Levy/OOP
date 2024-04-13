import java.util.Scanner;

/**
 * The Chat class represents a simple interactive chat application using ChatterBot instances.
 * @author Achikam Levy
 */
public class Chat {

    // Define replies to legal requests for ChatterBot A
    static String[] repliesToLegalRequestA = {
            "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                    "? okay: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
            "okay, here goes: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE};

    // Define replies to illegal requests for ChatterBot A
    static String[] repliesToIllegalRequestA = {
            "what is " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
            "say I should say "};

    // Define replies to legal requests for ChatterBot B
    static String[] repliesToLegalRequestB = {
            "You want me to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                    ", do you? alright: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
            "sey " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                    " ? ,you got it : " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE};

    // Define replies to illegal requests for ChatterBot B
    static String[] repliesToIllegalRequestB = {
            "whaaat you mean " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
            , "say say "};

    /**
     * The main method to initiate and run the interactive chat application.
     *
     * @param args Command-line arguments (we not use them in this application).
     */
    public static void main(String[] args) {

        // Create an array of ChatterBots
        ChatterBot[] chatterBots = {
                new ChatterBot("Kermit", repliesToIllegalRequestA, repliesToLegalRequestA),
                new ChatterBot("Frog", repliesToIllegalRequestB, repliesToLegalRequestB),
        };

        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Initial statement for the chat
        String statement = "hello";

        // Main chat loop
        while (true) {
            // Iterate through each ChatterBot in the array
            for (ChatterBot bot : chatterBots) {
                // Get the reply from the current ChatterBot based on the statement
                statement = bot.replyTo(statement);

                // Display ChatterBot name and its response
                System.out.print(bot.getName() + ": " + statement);

                // Wait for user input (press Enter) before moving to the next ChatterBot
                scanner.nextLine();
            }
        }
    }
}
