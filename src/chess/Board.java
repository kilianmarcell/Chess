package chess;

import chess.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Board extends JFrame {
    public Square squares[][] = new Square[8][8];
    private PieceSet[] pieceSets = new PieceSet[2];

    public void create() {
        setBounds(8, 8, 640, 640);
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        createBoard(); //Create board
        addPiecesToBoard(); //Add peaces to board

        //Set pictures of pieces
        BufferedImage allPieces = null;
        try {
            //All pieces are on this picture
            allPieces = ImageIO.read(new File(System.getProperty("user.dir") + "/pieces/pieces.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image onePiecePicture[] = new Image[12]; //Stores the pictures if single pieces
        int idx = 0;
        for (int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                //Adding every piece image to onePiecePicture
                onePiecePicture[idx] = allPieces.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                idx++;
            }
        }

        //Adding piece pictures to board
        for (int i = 0; i < 2; i++) {
            for(Piece p : pieceSets[i].getPieces()) {
                if(p.getClass() == King.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[0 + (i * 6)])));
                if(p.getClass() == Queen.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[1 + (i * 6)])));
                if(p.getClass() == Knight.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[2 + (i * 6)])));
                if(p.getClass() == Bishop.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[3 + (i * 6)])));
                if(p.getClass() == Rook.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[4 + (i * 6)])));
                if(p.getClass() == Pawn.class) p.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[5 + (i * 6)])));
            }
        }
        setVisible(true);
    }

    //Adding peaces to chessboard
    private void addPiecesToBoard() {
        //Creating white and black PeaceSets
        PieceSet white = new PieceSet("white");
        PieceSet black = new PieceSet("black");

        //Adding PeaceSets to Board class
        pieceSets[0] = white;
        pieceSets[1] = black;

        //Placing white and black peaces on board
        for (int j = 0; j < 2; j++) {
            List<Piece> piecesHelpList = white.getPieces();
            int helpRow = 7;
            if(j == 1) piecesHelpList = black.getPieces();
            if(j == 1) helpRow = 0;

            for (int i = 0; i < 8; i++) piecesHelpList.get(i).setPosition(squares[helpRow == 7 ? 6 : 1][i]); //Pawns positions
            int i = 8;
            piecesHelpList.get(i++).setPosition(squares[helpRow][4]); //King position
            piecesHelpList.get(i++).setPosition(squares[helpRow][3]); //Queen position
            piecesHelpList.get(i++).setPosition(squares[helpRow][2]); //Knight position (first)
            piecesHelpList.get(i++).setPosition(squares[helpRow][1]); //Bishop position (first)
            piecesHelpList.get(i++).setPosition(squares[helpRow][0]); //Rook position (first)
            piecesHelpList.get(i++).setPosition(squares[helpRow][5]); //Knight position (second)
            piecesHelpList.get(i++).setPosition(squares[helpRow][6]); //Bishop position (second)
            piecesHelpList.get(i).setPosition(squares[helpRow][7]); //Rook position (second)
        }
    }

    //Creating chessboard
    private void createBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = new Square(i, j);
                squares[i][j] = square;

                //Setting black and white squares
                square.setSize(80, 80);
                if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) square.setBackground(Color.WHITE);
                else square.setBackground(Color.GRAY);
                square.setVisible(true);
                add(squares[i][j]);
            }
        }
    }

    public Square getSquare(int row, int column) {
        return squares[row][column];
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public PieceSet[] getPieceSets() {
        return pieceSets;
    }

    public void setPieceSets(PieceSet[] pieceSets) {
        this.pieceSets = pieceSets;
    }
}
