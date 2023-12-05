package chess.windows;

import chess.Piece;
import chess.pieces.Bishop;
import chess.pieces.Knight;
import chess.pieces.Queen;
import chess.pieces.Rook;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PawnChangeWindow extends JFrame {
    private Piece piece;
    public PawnChangeWindow(Piece getPiece) {
        piece = getPiece;
        setTitle("Paraszt átalakulás");
        setLayout(new BorderLayout());

        Image onePiecePicture[] = new Image[4];
        BufferedImage allPieces = null;
        try {
            allPieces = ImageIO.read(new File(System.getProperty("user.dir") + "/pictures/pieces.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int idx = 0;
        int colorPixelStart = piece.getColor().equals("white") ? 0 : 200;
        int colorPixelStop = piece.getColor().equals("white") ? 200 : 400;
        for (int y = colorPixelStart; y < colorPixelStop; y += 200) {
            for (int x = 200; x < 1000; x += 200) {
                onePiecePicture[idx] = allPieces.getSubimage(x, y, 200, 200).getScaledInstance(64, 64, BufferedImage.SCALE_SMOOTH);
                idx++;
            }
        }

        JLabel cimLabel = new JLabel("Mivé változzon a bábu?");
        cimLabel.setFont(cimLabel.getFont().deriveFont(25f));
        cimLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cimLabel.setBorder(new EmptyBorder(0,0,15,0));
        add(cimLabel, BorderLayout.NORTH);

        JPanel piecesPictures = new JPanel(new GridLayout(1, 4));

        for (int i = 1; i <= 4; i++) {
            JPanel picture = new JPanel();
            picture.add(new JLabel(new ImageIcon(onePiecePicture[i - 1])));
            int helpI = i;
            picture.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(helpI == 1) piece = new Queen(piece.getColor());
                    if(helpI == 2) piece = new Bishop(piece.getColor());
                    if(helpI == 3) piece = new Knight(piece.getColor());
                    if(helpI == 4) piece = new Rook(piece.getColor());
                    super.mouseClicked(e);
                    dispose();
                }
            });
            piecesPictures.add(picture);
        }

        add(piecesPictures, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(800, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}