package battleship;

import java.util.Scanner;

public class Main {
    public interface Table {
        void createTable();
        void createFowTable();
    }

    public interface Ship {
        boolean placeShip(String input, int s, String ship);
        boolean isValidLengthWidth(int ln1, int c1, int ln2, int c2, int s);
        boolean isValidPlacement(int ln1, int c1, int ln2, int c2);
        void updateBoard(int ln1, int c1, int ln2, int c2);
    }

    public interface Shooting {
        void shoot(String input);
        boolean isValidCoordinate(int ln, int c);
        boolean checkBoard();
    }

    public static class Board implements Table, Ship, Shooting {
        String[][] board;
        String[][] fowBoard;
        char[] lines = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        int totalShips;

        public Board() {
            board = new String[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    board[i][j] = "~";
                }
            }
            totalShips = 5;
        }

        public void FowBoard() {
            fowBoard = new String[10][10];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    fowBoard[i][j] = "~";
                }
            }
        }

        @Override
        public void createTable() {
            System.out.println("  1 2 3 4 5 6 7 8 9 10");

            for (int i = 0; i < 10; i++) {
                System.out.print(lines[i] + " ");
                for (int j = 0; j < 10; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        }

        @Override
        public void createFowTable() {
            System.out.println("  1 2 3 4 5 6 7 8 9 10");

            for (int i = 0; i < 10; i++) {
                System.out.print(lines[i] + " ");
                for (int j = 0; j < 10; j++) {
                    System.out.print(fowBoard[i][j] + " ");
                }
                System.out.println();
            }
        }

        @Override
        public boolean placeShip(String input, int s, String ship) {
            int ln1 = -1;
            int c1 = -1;
            int ln2 = -1;
            int c2 = -1;

            String[] coordinates = input.split(" ");
            ln1 = Character.toUpperCase(coordinates[0].charAt(0)) - 'A';
            c1 = Integer.parseInt(coordinates[0].substring(1)) - 1;
            ln2 = Character.toUpperCase(coordinates[1].charAt(0)) - 'A';
            c2 = Integer.parseInt(coordinates[1].substring(1)) - 1;

            if (!isValidLengthWidth(ln1, c1, ln2, c2, s)) {
                System.out.println("Error! Wrong length of the " + ship + "! Try again:");
                return false;
            }

            if (!isValidPlacement(ln1, c1, ln2, c2)) {
                if (ln1 != ln2 && c1 != c2) {
                    System.out.println("Error! Wrong ship location! Try again:");
                } else {
                    System.out.println("Error! You placed it too close to another one. Try again:");
                }
                return false;
            }

            updateBoard(ln1, c1, ln2, c2);
            createTable();

            return true;
        }

        @Override
        public boolean isValidLengthWidth(int ln1, int c1, int ln2, int c2, int s) {
            int length = Math.abs(ln2 - ln1) + 1;
            int width = Math.abs(c2 - c1) + 1;
            return (length == s && width == 1) || (length == 1 && width == s);
        }

        @Override
        public boolean isValidPlacement(int ln1, int c1, int ln2, int c2) {
            if (ln1 != ln2 && c1 != c2) {
                return false; // Diagonal placement not allowed
            }

            int minLn = Math.min(ln1, ln2);
            int maxLn = Math.max(ln1, ln2);
            int minC = Math.min(c1, c2);
            int maxC = Math.max(c1, c2);

            for (int ln = minLn; ln <= maxLn; ln++) {
                for (int c = minC; c <= maxC; c++) {
                    if (board[ln][c] != null && board[ln][c].equals("O")) {
                        return false;
                    }
                }
            }

            for (int ln = minLn - 1; ln <= maxLn + 1; ln++) {
                for (int c = minC - 1; c <= maxC + 1; c++) {
                    if (ln >= 0 && ln < 10 && c >= 0 && c < 10 && board[ln][c] != null && board[ln][c].equals("O")) {
                        return false;
                    }
                }
            }

            return true;
        }

        @Override
        public void updateBoard(int ln1, int c1, int ln2, int c2) {
            int minLn = Math.min(ln1, ln2);
            int maxLn = Math.max(ln1, ln2);
            int minC = Math.min(c1, c2);
            int maxC = Math.max(c1, c2);

            if (ln1 == ln2) {
                for (int c = minC; c <= maxC; c++) {
                    board[ln1][c] = "O";
                }
            } else if (c1 == c2) {
                for (int ln = minLn; ln <= maxLn; ln++) {
                    board[ln][c1] = "O";
                }
            }
        }

        @Override
        public void shoot(String input) {
            int ln = -1;
            int c = -1;

            System.out.println();

            String[] coordinates = input.split(" ");
            ln = Character.toUpperCase(coordinates[0].charAt(0)) - 'A';
            c = Integer.parseInt(coordinates[0].substring(1)) - 1;

            if (!isValidCoordinate(ln, c)) {
                System.out.println("Error! Invalid coordinate. Try again:");
                return;
            }

            if (board[ln][c].equals("O")) {
                board[ln][c] = "X";
                fowBoard[ln][c] = "X";
                System.out.println("You hit a ship!");

                boolean shipSunk = true;
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        if (board[i][j].equals("O")) {
                            shipSunk = false;
                            break;
                        }
                    }
                    if (!shipSunk) {
                        break;
                    }
                }
                if (shipSunk) {
                    System.out.println("You sank a ship!");
                    totalShips--;
                }
            } else if (board[ln][c].equals("~")) {
                board[ln][c] = "M";
                fowBoard[ln][c] = "M";
                System.out.println("You missed!");
            } else if (board[ln][c].equals("X")) {
                System.out.println("You already hit that coordinate!");
            }

            createFowTable();

            System.out.println();
            if (totalShips > 0) {
                System.out.println("Take a shot!");
            }
        }

        @Override
        public boolean isValidCoordinate(int ln, int c) {
            return ln >= 0 && ln < 10 && c >= 0 && c < 10;
        }

        @Override
        public boolean checkBoard() {
            int shipCount = 0;

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (board[i][j].equals("O")) {
                        shipCount++;
                        break;
                    }
                }
            }

            return shipCount != 0;
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.createTable();
        board.FowBoard();
        String[] ships = {"Aircraft Carrier", "Battleship", "Submarine", "Cruiser", "Destroyer"};
        int[] lengths = {5, 4, 3, 3, 2};

        String shot;
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < ships.length; i++) {
            boolean validPlacement = false;

            while (!validPlacement) {
                System.out.print("Enter the coordinates of the " + ships[i] + " (" + lengths[i] + " cells): ");
                String input = scanner.nextLine();
                validPlacement = board.placeShip(input, lengths[i], ships[i]);
            }

        }
        System.out.println();
        System.out.println("The game starts!");
        System.out.println();
        board.createFowTable();
        System.out.println("Take a shot!");
        while (board.checkBoard()) {
            shot = scanner.nextLine();
            board.shoot(shot);
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
        scanner.close();
    }
}
