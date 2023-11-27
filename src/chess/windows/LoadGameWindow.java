package chess.windows;

import chess.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LoadGameWindow extends JFrame {
    public LoadGameWindow() {
        setTitle("Játszma betöltése");
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Mi a játszma neve?");
        titleLabel.setFont(titleLabel.getFont().deriveFont(25f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JTextField textField = new JTextField();
        add(textField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Vissza a menübe");
        JButton saveButton = new JButton("Betöltés");

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
                File f = new File(textField.getText() + ".txt");
                if(f.exists() && !f.isDirectory()) {
                    dispose();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new SelectDifficultyWindow(textField.getText() + ".txt");
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Nem létezik ilyen nevű játszma!");
                }
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