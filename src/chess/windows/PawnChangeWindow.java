package chess.windows;

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
    private int selectedPieceIndex = 0;
    public PawnChangeWindow(String color) {
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
        int colorPixelStart = color.equals("white") ? 0 : 200;
        int colorPixelStop = color.equals("white") ? 200 : 400;
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
                    super.mouseClicked(e);
                    selectedPieceIndex = helpI;
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

    public int getSelectedPieceIndex() {
        return selectedPieceIndex;
    }
}