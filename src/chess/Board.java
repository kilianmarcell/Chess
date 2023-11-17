package chess;

import chess.moves.Move;
import chess.pieces.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private List<Move> movesList = new ArrayList<>();
    private Piece movingPiece = null;
    private Image onePiecePicture[] = new Image[12]; //Stores the pictures if single pieces
    private JLabel[] lastFiveMoves = new JLabel[5];
    private JButton backButton = new JButton("Vissza!");
    private boolean whiteMove = true;

    public void create() {
        setBounds(8, 8, 800, 880);
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 8));

        createBoard(); //Create board
        addPiecesToBoard(); //Add peaces to board

        add(new JLabel("Utolsó 5 lépés:", SwingConstants.CENTER));

        for (int i = 0; i < 5; i++) {
            lastFiveMoves[i] = new JLabel("-", SwingConstants.CENTER);
            add(lastFiveMoves[i]);
        }

        add(backButton);
        backButtonClickEvent(backButton);

        //Set pictures of pieces
        BufferedImage allPieces = null;
        try {
            //All pieces are on this picture
            allPieces = ImageIO.read(new File(System.getProperty("user.dir") + "/pictures/pieces.png"));
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
        pack();
        setVisible(true);
    }

    private void setPictureOfPiece(Piece piece) {
        if(piece.getClass() == King.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 0 : 6])));
        if(piece.getClass() == Queen.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 1 : 7])));
        if(piece.getClass() == Knight.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 2 : 8])));
        if(piece.getClass() == Bishop.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 3 : 9])));
        if(piece.getClass() == Rook.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 4 : 10])));
        if(piece.getClass() == Pawn.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 5 : 11])));
    }

    private void setLastFiveMovesText(List<Move> moves) {
        if(!moves.isEmpty()) {
            int j = 0;
            int i = moves.size() - 1;
            while(i >= 0 && j < 5) {
                lastFiveMoves[j++].setText(moves.get(i).getFromSquare().toString() + " -> " + moves.get(i--).getToSquare().toString());
            }
            while(j < 5) lastFiveMoves[j++].setText("-");
        } else {
            for (int i = 0; i < 5; i++) lastFiveMoves[i].setText("-");
        }
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
                mouseClick(square);
                add(squares[i][j]);
            }
        }
    }

    private void mouseClick(Square square) {
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pieceMove(square);
            }
        });
    }

    private void backButtonClickEvent(JButton backButton) {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!movesList.isEmpty()) {
                    Square fromSquare = movesList.get(movesList.size() - 1).getToSquare();
                    Square toSquare = movesList.get(movesList.size() - 1).getFromSquare();

                    if(movesList.get(movesList.size() - 1).getKickedPiece() == null) {
                        Piece fromPiece = fromSquare.getPiece();

                        toSquare.setPiece(fromPiece);
                        fromPiece.setPosition(toSquare);

                        fromSquare.setPiece(null);
                        fromSquare.removeAll();
                        fromSquare.repaint();
                        setPictureOfPiece(toSquare.getPiece());
                        toSquare.revalidate();
                    } else {
                        Piece fromPiece = fromSquare.getPiece();

                        toSquare.setPiece(fromPiece);
                        fromPiece.setPosition(toSquare);

                        fromSquare.setPiece(null);
                        fromSquare.removeAll();
                        fromSquare.setPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        movesList.get(movesList.size() - 1).getKickedPiece().setPosition(fromSquare);
                        setPictureOfPiece(fromSquare.getPiece());

                        fromSquare.repaint();
                        setPictureOfPiece(toSquare.getPiece());
                        toSquare.revalidate();
                    }

                    movesList.remove(movesList.size() - 1);
                    setLastFiveMovesText(movesList);
                    if(whiteMove) whiteMove = false;
                    else whiteMove = true;
                }
            }
        });
    }

    private void pieceMove(Square square) {
        if(square != null) {
            if(movingPiece == null) {
                movingPiece = square.getPiece();
                if(movingPiece != null && (whiteMove && square.getPiece().getColor() == "white" || !whiteMove && square.getPiece().getColor() == "black")) {
                    possibleMoves = movingPiece.possibleMoves(squares);
                } else {
                    movingPiece = null;
                }
                for(Square s: possibleMoves) {
                    if(s.getPiece() == null) s.setBackground(Color.GREEN);
                    else s.setBackground(Color.RED);
                    s.revalidate();
                }
            } else {
                if(!possibleMoves.isEmpty()) {
                    Square oldSquare = movingPiece.getPosition();
                    if(oldSquare != square) {
                        if(possibleMoves.contains(square)) {
                            if(square.getPiece() != null) {
                                movesList.add(new Move(movingPiece.getPosition(), square, square.getPiece()));
                                square.removeAll();
                                square.setPiece(null);
                            } else {
                                movesList.add(new Move(movingPiece.getPosition(), square));
                            }

                            oldSquare.setPiece(null); //Removing the piece
                            oldSquare.removeAll(); //Removing the piece picture

                            square.setPiece(movingPiece); //Set square that the piece is on it
                            movingPiece.setPosition(square);
                            setPictureOfPiece(movingPiece); //Set the picture of the piece

                            square.revalidate(); //To show the picture of piece, refresh label
                            oldSquare.repaint();
                            if(whiteMove) whiteMove = false;
                            else whiteMove = true;
                        }
                    }
                }
                for(Square s: possibleMoves) {
                    if(s.getRow() % 2 == 0 && s.getColumn() % 2 == 0 || s.getRow() % 2 == 1 && s.getColumn() % 2 == 1) s.setBackground(Color.WHITE);
                    else s.setBackground(Color.GRAY);
                }
                setLastFiveMovesText(movesList);
                possibleMoves.clear();
                movingPiece = null;
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
