import javax.swing.*; 
import java.awt.*;

public class Scoreboard {

    public static void showScoreboard(int finalScore, int finalTime) {

        JFrame frame = new JFrame("SCOREBOARD");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Scoreboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1));

        JLabel scoreLabel = new JLabel("Points Earned: " + finalScore, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(scoreLabel);

        JLabel timeLabel = new JLabel("Your Time: " + finalTime + " seconds", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        centerPanel.add(timeLabel);

        frame.add(centerPanel, BorderLayout.CENTER);

        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> {
            frame.dispose(); // Close Scoreboard
            StartScreen.main(null); // Go back to Main Menu
        });

        frame.add(playAgainButton, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}