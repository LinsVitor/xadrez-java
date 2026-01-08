package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Program {
    static void main() {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        while (true) {
            UI.printBoard(chessMatch.getPieces());
            IO.println();
            IO.print("Source: ");
            ChessPosition source = UI.readChessPosition(sc);

            IO.println();
            IO.print("Target: ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }

    }
}
