package chess.windows;

import chess.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectDifficultyWindow extends JFrame {
    public SelectDifficultyWindow() {
        setTitle("Sakk");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.WHITE);

        JTextArea textArea = new JTextArea("Játékmód kiválasztása");
        textArea.setFont(textArea.getFont().deriveFont(25f));
        textArea.setPreferredSize(new Dimension(400, 40));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        JButton twoPlayerButton = new JButton("Kétjátékos");
        JButton easyRobotButton = new JButton("Könnyű robot");
        JButton hardRobotButton = new JButton("Nehéz robot");

        twoPlayerButton.setPreferredSize(new Dimension(400, 40));
        easyRobotButton.setPreferredSize(new Dimension(400, 40));
        hardRobotButton.setPreferredSize(new Dimension(400, 40));

        twoPlayerButton.setFont(twoPlayerButton.getFont().deriveFont(14f));
        easyRobotButton.setFont(easyRobotButton.getFont().deriveFont(14f));
        hardRobotButton.setFont(hardRobotButton.getFont().deriveFont(14f));

        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Board board = new Board(0);
                board.create(false);
            }
        });

        easyRobotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        hardRobotButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(10, 10, 10, 10); // Paddings
        panel.add(textArea, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(twoPlayerButton, gbc);
        gbc.gridy = 2;
        panel.add(easyRobotButton, gbc);
        gbc.gridy = 3;
        panel.add(hardRobotButton, gbc);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack(); // Pack the components to set the size of the window
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }
}
