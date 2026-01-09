package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void clearScreen() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Linux/macOS
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (final Exception e) {
            // Handle exceptions (e.g., if running inside an IDE that doesn't support this)
            System.out.println("Could not clear console: " + e.getMessage());
        }
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine().toLowerCase();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int i = 0; i < pieces.length; i++) {
            IO.print(ANSI_RED + (8 - i) + " " + ANSI_RESET);
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], false);
            }
            IO.println();
        }
        IO.println(ANSI_RED + "  a b c d e f g h" + ANSI_RESET);
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        IO.println();
        printCapturedPieces(captured);
        IO.println();
        IO.println("Turn : " + chessMatch.getTurn());
        if (!chessMatch.isCheckMate()) {
            IO.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.isCheck()) {
                IO.println(ANSI_RED + "CHECK!" + ANSI_RESET);
            }
        }
        else {
            IO.println(ANSI_RED + "CHECKMATE!" + ANSI_RESET);
            IO.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int i = 0; i < pieces.length; i++) {
            IO.print(ANSI_RED + (8 - i) + " " + ANSI_RESET);
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            IO.println();
        }
        IO.println(ANSI_RED + "  a b c d e f g h" + ANSI_RESET);
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if(background) {
            IO.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            IO.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                IO.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                IO.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        IO.print(" ");
    }

    public static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).toList();
        List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.BLACK).toList();

        IO.println("Captured pieces");
        IO.print("WHITE: ");
        IO.print(ANSI_WHITE);
        IO.print(Arrays.toString(white.toArray()));
        IO.print(ANSI_RESET);
        IO.println();
        IO.print("BLACK: ");
        IO.print(ANSI_YELLOW);
        IO.println(Arrays.toString(black.toArray()));
        IO.print(ANSI_RESET);
    }
}
