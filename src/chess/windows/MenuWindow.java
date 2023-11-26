package chess.windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuWindow extends JFrame {
    public MenuWindow() {
        setTitle("Sakk");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(Color.WHITE);

        JTextArea textArea = new JTextArea("Sakk");
        textArea.setFont(textArea.getFont().deriveFont(25f));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        JButton newGameButton = new JButton("Új játék");
        JButton loadGameButton = new JButton("Játék betöltése");
        JButton exitButton = new JButton("Kilépés");

        newGameButton.setPreferredSize(new Dimension(300, 40));
        loadGameButton.setPreferredSize(new Dimension(300, 40));
        exitButton.setPreferredSize(new Dimension(300, 40));

        newGameButton.setFont(newGameButton.getFont().deriveFont(14f));
        loadGameButton.setFont(loadGameButton.getFont().deriveFont(14f));
        exitButton.setFont(exitButton.getFont().deriveFont(14f));

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new SelectDifficultyWindow();
                    }
                });
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        exitButton.addActionListener(new ActionListener() {
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
        panel.add(newGameButton, gbc);
        gbc.gridy = 2;
        panel.add(loadGameButton, gbc);
        gbc.gridy = 3;
        panel.add(exitButton, gbc);

        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack(); // Pack the components to set the size of the window
        setLocationRelativeTo(null); // Center the window on the screen
        setVisible(true);
    }
}
