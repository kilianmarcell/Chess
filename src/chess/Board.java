package chess;

import chess.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Board extends JFrame {
    public Square squares[][] = new Square[8][8];
    private PieceSet[] pieceSets = new PieceSet[2];
    private List<Square> possibleMoves = new ArrayList<>();
    private Piece movingPiece = null;
    private Image onePiecePicture[] = new Image[12]; //Stores the pictures if single pieces

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
                setPictureOfPiece(p);
            }
        }
        setVisible(true);
    }

    private void setPictureOfPiece(Piece piece) {
        if(piece.getClass() == King.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 0 : 6])));
        if(piece.getClass() == Queen.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 1 : 7])));
        if(piece.getClass() == Knight.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 2 : 8])));
        if(piece.getClass() == Bishop.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 3 : 9])));
        if(piece.getClass() == Rook.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 4 : 10])));
        if(piece.getClass() == Pawn.class)
            piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 5 : 11])));
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

            for (int i = 0; i < 8; i++) {
                piecesHelpList.get(i).setPosition(squares[helpRow == 7 ? 6 : 1][i]); //Pawns positions
                squares[helpRow == 7 ? 6 : 1][i].setPiece(piecesHelpList.get(i));
            }
            int i = 8;
            piecesHelpList.get(i).setPosition(squares[helpRow][4]); //King position
            squares[helpRow][4].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][3]); //Queen position
            squares[helpRow][3].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][2]); //Knight position (first)
            squares[helpRow][2].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][1]); //Bishop position (first)
            squares[helpRow][1].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][0]); //Rook position (first)
            squares[helpRow][0].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][5]); //Knight position (second)
            squares[helpRow][5].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][6]); //Bishop position (second)
            squares[helpRow][6].setPiece(piecesHelpList.get(i++));
            piecesHelpList.get(i).setPosition(squares[helpRow][7]); //Rook position (second)
            squares[helpRow][7].setPiece(piecesHelpList.get(i));
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
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(square != null) {
                            if(movingPiece == null) {
                                movingPiece = square.getPiece();
                                if(movingPiece != null) possibleMoves = movingPiece.possibleMoves(squares);
                                for(Square s: possibleMoves) {
                                    System.out.println(s.getRow() + " " + s.getColumn());
                                }
                            } else {
                                if(possibleMoves.contains(square)) {
                                    System.out.println("Igen");
                                    Square oldSquare = movingPiece.getPosition();
                                    square.setPiece(movingPiece);
                                    movingPiece.setPosition(square);
                                    setPictureOfPiece(movingPiece);
                                    oldSquare.removeAll();

                                    //To show the picture of piece
                                    square.revalidate();
                                    oldSquare.repaint();
                                }
                                possibleMoves.clear();
                                movingPiece = null;
                                System.out.println("vege");
                            }
                        }
                    }
                });
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
