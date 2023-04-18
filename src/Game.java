import java.util.Scanner;

public class Game {
    public static Scanner scn = new Scanner(System.in);

    final static int MAX = 10;
    private static char[][] user;
    private static char[][] copy;
    private static char[][] op;
    private static int UserShips, CompShips;
    public static int stat;

    public Game(){
        user = new char[MAX][MAX];
        op = new char[MAX][MAX];
        copy = new char[MAX][MAX];
        stat = 0;
        UserShips = 24;
        CompShips = 24;
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                user[i][j] = 'o';
                op[i][j] = 'o';
                copy[i][j] = 'o';
            }
        }
    }

    //Start and Set up the game
    public static void startTheGame() {
        inputUser();
        setCompShips();
        while (UserShips > 0 && CompShips > 0) {
            wait(3);
            printAll();
            System.out.println("SCORE OF THE GAME\n" +
                    "THE OPP : " + (24 - UserShips) + " YOUR : " + (24 - CompShips));
            while (inputHit()) {
                wait(3);
                printAll();
                System.out.println("SCORE OF THE GAME\n" +
                        "THE OPP : " + (24 - UserShips) + " YOUR : " + (24 - CompShips));
            }
            while (hitOfOp()) {
                wait(3);
                printAll();
                System.out.println("SCORE OF THE GAME\n" +
                        "THE OPP : " + (24 - UserShips) + " YOUR : " + (24 - CompShips));
            }
        }
        if (UserShips > CompShips ){
            System.out.println("~~~~~~~~~~~~~!!!YOU WON!!!~~~~~~~~~~~~~~~~");
            stat++;
        }else
        {
            System.out.println("~~~~~~~~~~~YOU LOSE~~~~~~~~~~");

        }
        System.out.println("~~~~~~~~~~~~~~~THE END OF THE GAME~~~~~~~~~~~~~~~~");
    }

  /*
    Check whether it is a goal or not
     */
    private static boolean isShot(int x, int y, char[][] board) {
        return board[x][y] == 'A';
    }
    /*
check whether the ship on the right place on the board
 */
    private static boolean checkerShip(int x, int y, char dir, int n, char[][] board) {
        int sign = 0;
        int os = -1;
        int ship = 0;
        switch (n) {
            case 1:
            case 2:
                ship = 6;
                break;
            case 3:
            case 4:
                ship = 4;
                break;
            case 5:
            case 6:
                ship = 2;
                break;
        }
        int stop = ship;
        try {
            switch (dir) {
                case 'R':
                    sign = 1;
                    os = y;
                    break;
                case 'L':
                    sign = -1;
                    os = y;
                    break;
                case 'B':
                    sign = 1;
                    os = x;
                    break;
                case 'T':
                    sign = -1;
                    os = x;
                    break;
            }
            if (os + (sign * ship) < -1 || os + (sign * ship) > 10) {
                return false;
            }
            for (int i = os; sign == 1 ? i < os + (sign * ship) : i > os + (sign * ship); i += sign) {
                if (dir == 'T' || dir == 'B') {
                    if (board[i][y] == 'o') {
                        board[i][y] = 'A';
                        stop--;
                    }
                } else {
                    if (board[x][i] == 'o') {
                        board[x][i] = 'A';
                        stop--;
                    }
                }
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return stop == 0;
    }


        /*
    the Set of ships of Computer's board
     */

    public static void setCompShips() {
        int n = 1;
        char[] t = {'R', 'L', 'T', 'B'};
        while (n < 7) {
            int x = (int) (Math.random() * (MAX - 1));
            int y = (int) (Math.random() * (MAX - 1));
            char dir = t[(int) (Math.random() * 3)];
            if (checkerShip(x, y, dir, n, op)) {
                n++;
            }
        }
    }

    /*
    the Hit of the Computer
     */
    public static boolean hitOfOp() {

        int x = (int) (Math.random() * (MAX - 1));
        int y = (int) (Math.random() * (MAX - 1));
        if (isShot(x, y, user)) {
            UserShips--;
            user[x][y] = 'X';

            System.out.println("Opponent got!");
            return true;
        } else if (user[x][y] == 'o') {
            user[x][y] = 'M';

            System.out.println("Opponent missed");
            return false;
        } else
            return true;
    }
    /*
    Input the coordinates of the hit by User
     */
    public static boolean inputHit() {
        System.out.print("\nPlease, write down the coordinates of the hit ('0 A'): ");
        String coorN = scn.next();
        String coorL = scn.next();
        int x = coorN.charAt(0) - 48;
        int y = coorL.charAt(0) - 65;
        try {
            if (isShot(x, y, op)) {
                op[x][y] = 'X';
                copy[x][y] = 'X';
                CompShips--;
                System.out.println("You got it! Hit again");
                return true;

            } else if (op[x][y] == 'o') {
                System.out.println("You missed :(");
                copy[x][y] = 'M';
                return false;
            } else return true;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("The out of board! Try again!");
            return true;
        }
    }


    //input the ships of user's board

    public static void inputUser() {

        int n = 1;
        print(user);
        while (n < 7) {

            System.out.println("Please, write down the coordinates if the start point of your ship('0 A'): ");
            String coorN = scn.next();
            String coorL = scn.next();
            int x = coorN.charAt(0) - 48;
            int y = coorL.charAt(0) - 65;
            System.out.println(x + " " + y);
            System.out.print("\nPlease, write down the direction of your ship\n" +
                    "R - right\n" +
                    "L - left\n" +
                    "T - top\n" +
                    "B - bottom\n");
            char dir = scn.next().charAt(0);
            if (checkerShip(x, y, dir, n, user)) {
                n++;
            } else
                System.out.println("Try again, it seems error coordinates :(");
            print(user);
        }
        System.out.println("LET'S START");
    }

    /*
    Printing the board
     */
    private static void print(char[][] board) {
        for (int i = 0; i < MAX; i++) {
            System.out.printf(" %d", i);
            for (int j = 0; j < MAX; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
        }
        System.out.print("-- A B C D E F G H I J\n");
    }
    public static void printAll() {
        System.out.println("~~~~~~~~~~~~~~~THE BATTLESHIPS~~~~~~~~~~~~~~~~");
        print(user);
        wait(2);
        System.out.println("~~~~~~~~~~~~~~~~THE COPY BOARD~~~~~~~~~~~~~~~~");
        print(copy);
    }

    /*
    The wait method
     */

    public static void wait(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}