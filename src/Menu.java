import java.util.Scanner;

public class Menu {
    public static Game game;

    static {
        game = new Game();
    }

    public static Scanner scn = new Scanner(System.in);
    public static void start(){

        System.out.println("~~~~~~~~~~~~~~~THE BATTLESHIPS~~~~~~~~~~~~~~~~");
        int c;
        do{
            System.out.println("~~~~~~~~~~~~~~~     MENU    ~~~~~~~~~~~~~~~~~");
            System.out.println("                1) START ");
            System.out.println("                2) STATS");
            System.out.println("                3) END");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            c = scn.nextInt();
            returnFunc(c);
        }while(c > 0 && c < 3);
        System.out.println("GOOD BYE!!!");
    }

    private static void returnFunc(int c) {
        switch (c){
            case 1:
                Game.startTheGame();
                break;
            case 2:
                statsOfTheGame();
                break;
        }
    }

    private static void statsOfTheGame() {
        System.out.println("THE STATS OF THE GAME");
        System.out.println("YOUR VICTORIES: ");
        System.out.println(Game.stat);
    }
}
