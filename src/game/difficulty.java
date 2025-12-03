import javax.swing.*;
import java.awt.*;

public class difficulty {

    // Constructor to launch the window
    public difficulty() {
        createDifficultyWindow();
    }

    private void createDifficultyWindow() {
        JFrame frame = new JFrame("Select Difficulty");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton easyBtn = new JButton("Easy (10 cards)");
        JButton mediumBtn = new JButton("Medium (14 cards)");
        JButton hardBtn = new JButton("Hard (18 cards)");

        panel.add(easyBtn);
        panel.add(mediumBtn);
        panel.add(hardBtn);

        frame.add(panel);
        frame.setVisible(true);

        // --- Action Listeners to Open the Game ---

        easyBtn.addActionListener(e -> {
            frame.dispose(); // Close difficulty window
            new Cards("EASY"); // Start Game
        });

        mediumBtn.addActionListener(e -> {
            frame.dispose();
            new Cards("MEDIUM");
        });

        hardBtn.addActionListener(e -> {
            frame.dispose();
            new Cards("HARD");
        });
    }
}
