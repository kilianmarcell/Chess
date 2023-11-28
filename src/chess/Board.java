package chess;

import chess.moves.Move;
import chess.pieces.*;
import chess.windows.SaveGameWindow;
import chess.windows.SelectDifficultyWindow;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static chess.CheckChecks.checkCheck;

public class Board extends JFrame {
    private int gameMode = 0;
    private String gameName = "";
    public Square squares[][] = new Square[8][8];
    private PieceSet[] pieceSets = new PieceSet[2];
    private List<Square> possibleMoves = new ArrayList<>();
    private List<Move> movesList = new ArrayList<>();
    private Piece movingPiece = null;
    private Image onePiecePicture[] = new Image[12]; //Stores the pictures if single pieces
    private JLabel[] lastFiveMoves = new JLabel[5];
    private JButton backButton = new JButton("Vissza");
    private JButton menuButton = new JButton("Menü");
    private boolean whiteMove = true;
    private boolean isCastling = false;
    private boolean isEnPassant = false;

    public Board(int gameMode) {
        this.gameMode = gameMode;
    }

    public Board(String gameName) {
        this.gameName = gameName;
    }

    public Board(String gameName, int gameMode) {
        this.gameMode = gameMode;
        this.gameName = gameName;
    }

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
        add(menuButton);
        backButtonClickEvent(backButton);
        menuButtonClickEvent(menuButton);

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
        setLocationRelativeTo(null);

        if(!gameName.equals("")) {
            String loadMoves = "";
            try {
                loadMoves = Files.readString(Path.of(System.getProperty("user.dir") + "/" + gameName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            int helpRow = -1;
            for(char c : loadMoves.toCharArray()) {
                if(c != '\n' && c != ' ' && helpRow == -1) {
                    helpRow = Character.getNumericValue(c);
                } else if(helpRow != -1) {
                    int helpColumn = Character.getNumericValue(c);
                    pieceMove(squares[helpRow - 1][helpColumn - 1]);
                    helpRow = -1;
                }
            }
        }
    }

    private void menuButtonClickEvent(JButton menuButton) {
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SaveGameWindow(movesList);
                        dispose();
                    }
                });
            }
        });
    }

    private void setPictureOfPiece(Piece piece) {
        if(piece.getClass() == King.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 0 : 6])));
        else if(piece.getClass() == Queen.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 1 : 7])));
        else if(piece.getClass() == Knight.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 2 : 8])));
        else if(piece.getClass() == Bishop.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 3 : 9])));
        else if(piece.getClass() == Rook.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 4 : 10])));
        else if(piece.getClass() == Pawn.class) piece.getPosition().add(new JLabel(new ImageIcon(onePiecePicture[piece.getColor() == "white" ? 5 : 11])));
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

    private void mouseClick(Square square) { //Implements a piece selection and that piece's move
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pieceMove(square);
                if(gameMode > 0 && !whiteMove) {
                    List<Piece> blackPieces = pieceSets[1].getPieces();
                    List<Piece> removedPiecesHelp = new ArrayList<>();
                    randomMove(blackPieces, removedPiecesHelp);
                }
            }
        });
    }

    private void randomMove(List<Piece> blackPieces, List<Piece> removedPiecesHelp) { //Implements a random move to the robot
        Random random = new Random();
        int randomPiece = random.nextInt(blackPieces.size());
        Piece helpPiece = blackPieces.get(randomPiece);

        if(helpPiece == squares[helpPiece.getPosition().getRow()][helpPiece.getPosition().getColumn()].getPiece()) pieceMove(helpPiece.getPosition());
        if(possibleMoves.isEmpty()) {
            removedPiecesHelp.add(helpPiece);
            blackPieces.remove(helpPiece);
            pieceMove(squares[0][0]);
        } else {
            pieceMove(possibleMoves.get(random.nextInt(possibleMoves.size())));
        }

        if(!whiteMove) randomMove(blackPieces, removedPiecesHelp);
        else if(whiteMove && !removedPiecesHelp.isEmpty()) {
            for(Piece p : removedPiecesHelp) blackPieces.add(p);
            removedPiecesHelp.clear();
        }
    }

    private void oneMoveBack() {
        if(!movesList.isEmpty()) {
            for(Square s: possibleMoves) {
                if(s.getRow() % 2 == 0 && s.getColumn() % 2 == 0 || s.getRow() % 2 == 1 && s.getColumn() % 2 == 1) s.setBackground(Color.WHITE);
                else s.setBackground(Color.GRAY);
            }
            Square fromSquare = movesList.get(movesList.size() - 1).getToSquare();
            Square toSquare = movesList.get(movesList.size() - 1).getFromSquare();
            String fromSquareColor = "";
            if(toSquare != null) fromSquareColor = fromSquare.getPiece().getColor();

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
                if(movesList.get(movesList.size() - 1).getKickedPiece().getClass() == Pawn.class) { //En passant
                    if(movesList.get(movesList.size() - 1).getToSquare().getRow() == 2 && (movesList.get(movesList.size() - 2).getFromSquare().getRow() + 2) == movesList.get(movesList.size() - 2).getToSquare().getRow()) {
                        squares[fromSquare.getRow() + 1][fromSquare.getColumn()].setPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        movesList.get(movesList.size() - 1).getKickedPiece().setPosition(squares[fromSquare.getRow() + 1][fromSquare.getColumn()]);
                        setPictureOfPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        fromSquare.setPiece(null);
                        fromSquare.removeAll();
                        setPictureOfPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        fromSquare.repaint();
                        squares[fromSquare.getRow() + 1][fromSquare.getColumn()].revalidate();
                    } else if(movesList.get(movesList.size() - 1).getToSquare().getRow() == 5 && (movesList.get(movesList.size() - 2).getFromSquare().getRow() - 2) == movesList.get(movesList.size() - 2).getToSquare().getRow()) {
                        squares[fromSquare.getRow() - 1][fromSquare.getColumn()].setPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        movesList.get(movesList.size() - 1).getKickedPiece().setPosition(squares[fromSquare.getRow() - 1][fromSquare.getColumn()]);
                        setPictureOfPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        fromSquare.setPiece(null);
                        fromSquare.removeAll();
                        setPictureOfPiece(movesList.get(movesList.size() - 1).getKickedPiece());
                        fromSquare.repaint();
                        squares[fromSquare.getRow() - 1][fromSquare.getColumn()].revalidate();
                    }
                }
            }

            movesList.remove(movesList.size() - 1);
            setLastFiveMovesText(movesList);
            if(!movesList.isEmpty() && movesList.get(movesList.size() - 1).getToSquare().getPiece() != null && movesList.get(movesList.size() - 1).getToSquare().getPiece().getColor().equals(fromSquareColor)) {
                oneMoveBack(); //For castling we need to hit the back button once
            } else {
                if(whiteMove) whiteMove = false;
                else whiteMove = true;
            }
        }
    }

    private void backButtonClickEvent(JButton backButton) {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oneMoveBack();
            }
        });
    }

    private void pieceMove(Square square) { //Implements one move
        if(square != null) {
            if(movingPiece == null) {
                movingPiece = square.getPiece();
                if(movingPiece != null && (whiteMove && square.getPiece().getColor() == "white" || !whiteMove && square.getPiece().getColor() == "black")) {
                    possibleMoves = movingPiece.possibleMoves(squares);
                    checkCastling();
                    checkEnPassant();
                    for(int i = 0; i < possibleMoves.size(); i++) if(whatIfPieceWasThere(movingPiece, possibleMoves.get(i))) possibleMoves.remove(i--);
                } else {
                    movingPiece = null;
                }
                for(Square s: possibleMoves) {
                    if(s.getPiece() == null) s.setBackground(Color.GREEN);
                    else s.setBackground(Color.RED);
                    s.revalidate();
                }
                if(!possibleMoves.isEmpty() && isEnPassant) {
                    if((whiteMove && movingPiece.getPosition().getRow() == 3) || (!whiteMove && movingPiece.getPosition().getRow() == 4)) possibleMoves.get(possibleMoves.size() - 1).setBackground(Color.RED);
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
                                doCastling(square);
                                movesList.add(new Move(movingPiece.getPosition(), square));
                                doEnPassant(square);
                                isCastling = false;
                                isEnPassant = false;
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
                if(checkCheck(squares, whiteMove ? pieceSets[0].getPiece(8) : pieceSets[1].getPiece(8))) {
                    if(checkCheckMate()) {
                        System.out.println("Sakk matt!");
                        JOptionPane.showMessageDialog(null, "Sakk matt!\nGratulálok, " + (whiteMove ? "fekete" : "fehér") + " nyert!");
                    }
                    else System.out.println("Sakk!");
                }
                possibleMoves.clear();
                movingPiece = null;
            }
        }
    }

    private void checkEnPassant() { //Checks if en passant is possible
        if(movingPiece.getClass() == Pawn.class) {
            int row = whiteMove ? 3 : 4;
            if(movingPiece.getPosition().getRow() == row) {
                Move lastMove = movesList.get(movesList.size() - 1);
                if(lastMove != null && lastMove.getToSquare().getPiece().getClass() == Pawn.class && (lastMove.getToSquare().getColumn() == (movingPiece.getPosition().getColumn() + 1) || lastMove.getToSquare().getColumn() == (movingPiece.getPosition().getColumn() - 1))) {
                    if(whiteMove && lastMove.getToSquare().getRow() == lastMove.getFromSquare().getRow() + 2) {
                        possibleMoves.add(squares[lastMove.getToSquare().getRow() - 1][lastMove.getToSquare().getColumn()]);
                        isEnPassant = true;
                    }
                    if(!whiteMove && lastMove.getToSquare().getRow() == lastMove.getFromSquare().getRow() - 2) {
                        possibleMoves.add(squares[lastMove.getToSquare().getRow() + 1][lastMove.getToSquare().getColumn()]);
                        isEnPassant = true;
                    }
                }
            }
        }
    }

    private void doEnPassant(Square square) { //If player does en passant it implements it
        if(isEnPassant) {
            int row = whiteMove ? 2 : 5;
            int hitRow = whiteMove ? 3 : 4;
            if(square.getRow() == row) {
                movesList.remove(movesList.size() - 1);
                movesList.add(new Move(movingPiece.getPosition(), square, squares[hitRow][square.getColumn()].getPiece()));
                squares[hitRow][square.getColumn()].setPiece(null);
                squares[hitRow][square.getColumn()].removeAll();
                squares[hitRow][square.getColumn()].repaint();
            }
        }
    }

    private void checkCastling() {
        if(movingPiece.getClass() == King.class) { //Check castling
            if(movingPiece.getColor().equals("white") && movingPiece.getPosition() == squares[7][4] && !checkCheck(squares, pieceSets[0].getPiece(8))) {
                boolean canCastleRight = true;
                boolean canCastleLeft = true;
                for(Move m : movesList) {
                    if(m.getFromSquare() == squares[7][4] || m.getFromSquare() == squares[7][7]) canCastleRight = false;
                    if(m.getFromSquare() == squares[7][4] || m.getFromSquare() == squares[7][0]) canCastleLeft = false;
                }
                if(squares[7][7].getPiece().getClass() == Rook.class && squares[7][7].getPiece().getColor().equals("white") && canCastleRight && squares[7][5].getPiece() == null && squares[7][6].getPiece() == null) {
                    possibleMoves.add(squares[7][6]);
                    isCastling = true;
                }
                if(squares[7][0].getPiece().getClass() == Rook.class && squares[7][0].getPiece().getColor().equals("white") && canCastleLeft && squares[7][1].getPiece() == null && squares[7][2].getPiece() == null && squares[7][3].getPiece() == null) {
                    possibleMoves.add(squares[7][2]);
                    isCastling = true;
                }
            } else if(movingPiece.getColor().equals("black") && movingPiece.getPosition() == squares[0][4] && !checkCheck(squares, pieceSets[1].getPiece(8))) {
                boolean canCastleRight = true;
                boolean canCastleLeft = true;
                for(Move m : movesList) {
                    if(m.getFromSquare() == squares[0][4] || m.getFromSquare() == squares[0][7]) canCastleRight = false;
                    if(m.getFromSquare() == squares[0][4] || m.getFromSquare() == squares[0][0]) canCastleLeft = false;
                }
                if(squares[0][7].getPiece().getClass() == Rook.class && squares[0][7].getPiece().getColor().equals("black") && canCastleRight && squares[0][5].getPiece() == null && squares[0][6].getPiece() == null) {
                    possibleMoves.add(squares[0][6]);
                    isCastling = true;
                }
                if(squares[0][0].getPiece().getClass() == Rook.class && squares[0][0].getPiece().getColor().equals("black") && canCastleLeft && squares[0][1].getPiece() == null && squares[0][2].getPiece() == null && squares[0][3].getPiece() == null) {
                    possibleMoves.add(squares[0][2]);
                    isCastling = true;
                }
            }
        }
    }

    private void doCastling(Square square) { //If player castling this method implements it
        if(isCastling) {
            if(whiteMove && square.getRow() == 7) {
                if(square.getColumn() == 2) {
                    isCastling = false;
                    Piece leftWhiteRook = squares[7][0].getPiece();
                    movesList.add(new Move(leftWhiteRook.getPosition(), squares[7][3]));
                    squares[7][0].setPiece(null);
                    squares[7][0].removeAll();
                    squares[7][3].setPiece(leftWhiteRook);
                    leftWhiteRook.setPosition(squares[7][3]);
                    setPictureOfPiece(leftWhiteRook);
                    squares[7][0].repaint();
                    squares[7][3].revalidate();
                } else if(square.getColumn() == 6) {
                    isCastling = false;
                    Piece rightWhiteRook = squares[7][7].getPiece();
                    movesList.add(new Move(rightWhiteRook.getPosition(), squares[7][5]));
                    squares[7][7].setPiece(null);
                    squares[7][7].removeAll();
                    squares[7][5].setPiece(rightWhiteRook);
                    rightWhiteRook.setPosition(squares[7][5]);
                    setPictureOfPiece(rightWhiteRook);
                    squares[7][7].repaint();
                    squares[7][5].revalidate();
                }
            }
            if(!whiteMove && square.getRow() == 0) {
                if(square.getColumn() == 2) {
                    isCastling = false;
                    Piece leftWhiteRook = squares[0][0].getPiece();
                    movesList.add(new Move(leftWhiteRook.getPosition(), squares[0][3]));
                    squares[0][0].setPiece(null);
                    squares[0][0].removeAll();
                    squares[0][3].setPiece(leftWhiteRook);
                    leftWhiteRook.setPosition(squares[0][3]);
                    setPictureOfPiece(leftWhiteRook);
                    squares[0][0].repaint();
                    squares[0][3].revalidate();
                } else if(square.getColumn() == 6) {
                    isCastling = false;
                    Piece rightWhiteRook = squares[0][7].getPiece();
                    movesList.add(new Move(rightWhiteRook.getPosition(), squares[0][5]));
                    squares[0][7].setPiece(null);
                    squares[0][7].removeAll();
                    squares[0][5].setPiece(rightWhiteRook);
                    rightWhiteRook.setPosition(squares[0][5]);
                    setPictureOfPiece(rightWhiteRook);
                    squares[0][7].repaint();
                    squares[0][5].revalidate();
                }
            }
        }
    }

    private boolean checkCheckMate() { //Checks check mate
        int helpWhite = whiteMove ? 0 : 1;
        List<Piece> pieceListHelp = pieceSets[helpWhite].getPieces();
        for(Piece p : pieceListHelp) {
            if(squares[p.getPosition().getRow()][p.getPosition().getColumn()].getPiece() == p) {
                List<Square> possibleMovesHelp = p.possibleMoves(squares);
                for(int i = 0; i < possibleMovesHelp.size(); i++) if(whatIfPieceWasThere(p, possibleMovesHelp.get(i))) possibleMovesHelp.remove(i--);
                if(possibleMovesHelp.size() != 0) return false;
            }
        }
        return true;
    }

    private boolean whatIfPieceWasThere(Piece piece, Square moveToS) {
        boolean wouldBeCheck = false;
        Piece moveToP = moveToS.getPiece(); //Stores the piece where the moving piece goes
        Square moveFromS = piece.getPosition();

        moveToS.setPiece(piece);
        piece.setPosition(moveToS);
        moveFromS.setPiece(null);
        if(checkCheck(squares, whiteMove ? pieceSets[0].getPiece(8) : pieceSets[1].getPiece(8))) wouldBeCheck = true;

        moveToS.setPiece(moveToP);
        piece.setPosition(moveFromS);
        moveFromS.setPiece(piece);

        return wouldBeCheck;
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
