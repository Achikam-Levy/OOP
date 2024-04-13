//import java.util.Arrays;
//
//import java.util.Random;
//
//public class Tests {
//
//    private boolean TestPercentages(Tournament tournament, int size , int winStreak, String[] playersNames, int rounds){
//        tournament.playTournament(size,winStreak, playersNames[0], playersNames[1]);
//        double percentage = (double) tournament.playerAScore / rounds;
//        double expectedSuccessRate = 0.55 ;
//        return percentage >= expectedSuccessRate;
//    }
//
//    public static void main(String[] args) {
//
//        Tests mainTest = new Tests();
//        PlayerFactory playerFactory = new PlayerFactory();
//        String whateverName = "whatever";
//        String cleverName = "clever";
//        String geniusName = "genius";
//        Player whatever  = playerFactory.buildPlayer("whatever");
//        Player clever = playerFactory.buildPlayer("clever");
//        Player genius = playerFactory.buildPlayer("genius");
//
//
//        RendererFactory rendererFactory = new RendererFactory();
//        Random random = new Random();
//
//
//
//        for (int i = 0; i < 100; i++) {
//            int size = random.nextInt(4,10);
//            int winStreak = random.nextInt(3,12);
//            Renderer renderer = rendererFactory.buildRenderer("none", size);
//            int rounds = random.nextInt(100, 50000);
//            Tournament tournament1 = new Tournament(rounds, renderer, clever, whatever);
//            Tournament tournament2 = new Tournament(rounds, renderer, genius, clever);
//            Tournament tournament3 = new Tournament(rounds, renderer, genius, whatever);
//            if (!(
//                    mainTest.TestPercentages( tournament1, size, winStreak, new String[]{cleverName, whateverName}, rounds) &&
//                            mainTest.TestPercentages( tournament2, size, winStreak, new String[]{geniusName, cleverName}, rounds) &&
//                            mainTest.TestPercentages( tournament3, size, winStreak, new String[]{geniusName, whateverName}, rounds)
//            )){
//                System.err.println("rounds: " + rounds + "\nsize: " + size + "\nwinStreak: " + winStreak + "\nfail!");
//                return;
//            }
//        }
//    }
//}
//
//
//
//
//
//
//
//
