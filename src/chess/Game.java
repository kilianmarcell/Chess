package chess;

import chess.windows.MenuWindow;
import chess.windows.PawnChangeWindow;

import javax.swing.*;

public class Game {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuWindow();
            }
        });
    }
}