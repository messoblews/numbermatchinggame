import javax.swing.*;

public class StartScreen {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Matching Card Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(null);

        JLabel titleLabel = new JLabel("Matching Card Game", SwingConstants.CENTER);
        titleLabel.setBounds(50, 20, 300, 30);
        frame.add(titleLabel);

        JButton singleButton = new JButton("Singleplayer");
        singleButton.setBounds(120, 80, 150, 30);
        frame.add(singleButton);

        JButton multiButton = new JButton("Multiplayer");
        multiButton.setBounds(120, 120, 150, 30);
        frame.add(multiButton);

        // SINGLEPLAYER BUTTON
        singleButton.addActionListener(e -> {
            frame.dispose(); // Close Start Screen
            new difficulty(); // Open Difficulty Screen
        });

        // MULTIPLAYER BUTTON
        multiButton.addActionListener(e -> {
            // For now, this also goes to Difficulty, 
            // but you can add specific multiplayer logic here later.
            frame.dispose(); // Close Start Screen
            new difficulty(); // Open Difficulty Screen
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}