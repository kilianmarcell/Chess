package chess.windows;

import chess.moves.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SaveGameWindow extends JFrame {
    public SaveGameWindow(List<Move> moves) {
        List<Move> movesList = moves;
        setTitle("Játszma mentése");
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Mi legyen a játszma neve?");
        titleLabel.setFont(titleLabel.getFont().deriveFont(25f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Ne mentse");
        JButton saveButton = new JButton("Mentés");

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MenuWindow();
                    }
                });
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = textField.getText() + ".txt";

                String chessGame = "";

                for(Move m : movesList) {
                    chessGame += m.getFromSquare().toString() + " " + m.getToSquare().toString();
                    //if(m.getKickedPiece() != null) chessGame += " " + m.getKickedPiece().getClass().toString().split(" ")[1];
                    chessGame += '\n';
                }

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
                    writer.write(chessGame);
                    writer.close();

                    System.out.println("Sikeres mentés");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new MenuWindow();
                    }
                });
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        setVisible(true);
        setLocationRelativeTo(null);
    }
}
