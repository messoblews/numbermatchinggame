import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class Cards {

    class Card {
        String cardName;
        ImageIcon cardImage;

        Card(String cardName, ImageIcon cardImage) {
            this.cardName = cardName;
            this.cardImage = cardImage;
        }
    }

    JFrame frame = new JFrame("Number Matching Card Game");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel restartGamePanel = new JPanel();
    JButton restartButton = new JButton("Restart Game");

    ArrayList<Card> cardSet;
    ArrayList<JButton> board;

    ImageIcon cardBackImageIcon;

    JButton selectedCard1;
    JButton selectedCard2;

    Timer hideCardTimer;
    Timer gameTimeTimer;

    int errorCount = 0;
    int pairsToFind;
    int pairsFound = 0;
    int secondsPlayed = 0;

    int cardWidth = 90;
    int cardHeight = 120;

    boolean gameReady = false;

    public Cards(String difficulty) {

        int rows, columns, numberOfPairs;

        if (difficulty.equalsIgnoreCase("HARD")) {
            numberOfPairs = 9;
            rows = 3;
            columns = 6;
        } else if (difficulty.equalsIgnoreCase("MEDIUM")) {
            numberOfPairs = 7;
            rows = 2;
            columns = 7;
        } else {
            numberOfPairs = 5;
            rows = 2;
            columns = 5;
        }

        pairsToFind = numberOfPairs;

        int boardWidth = columns * cardWidth;
        int boardHeight = rows * cardHeight;

        frame.setSize(boardWidth, boardHeight + 100);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        loadImages(numberOfPairs);
        shuffleCards();

        setupTextPanel(boardWidth);
        setupBoardPanel(rows, columns);
        setupRestartPanel(boardWidth);
        setupTimer();

        startGameTimer();

        frame.setVisible(true);
        gameReady = true;
    }

    void loadImages(int numberOfPairs) {
        cardSet = new ArrayList<>();

        // Load back image (uses /img/cardback.jpeg)
        Image backImg = new ImageIcon(getClass().getResource("/img/cardback.jpeg")).getImage();
        cardBackImageIcon = new ImageIcon(backImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));

        for (int i = 0; i < numberOfPairs; i++) {
            String cardName = "card" + i;
            Image cardImg = new ImageIcon(getClass().getResource("/img/" + cardName + ".jpeg")).getImage();
            ImageIcon cardIcon = new ImageIcon(cardImg.getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH));

            Card card = new Card(cardName, cardIcon);
            cardSet.add(card);
            cardSet.add(card);
        }
    }

    void shuffleCards() {
        Collections.shuffle(cardSet);
    }

    void setupTextPanel(int width) {
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Errors: 0 | Time: 0s");

        textPanel.setPreferredSize(new Dimension(width, 30));
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);
    }

    void setupBoardPanel(int rows, int cols) {
        board = new ArrayList<>();
        boardPanel.setLayout(new GridLayout(rows, cols));

        for (int i = 0; i < cardSet.size(); i++) {

            JButton tile = new JButton();
            tile.setPreferredSize(new Dimension(cardWidth, cardHeight));
            tile.setIcon(cardBackImageIcon);
            tile.setFocusable(false);

            tile.putClientProperty("flipped", false);

            tile.addActionListener(e -> handleCardClick(tile));

            board.add(tile);
            boardPanel.add(tile);
        }

        frame.add(boardPanel, BorderLayout.CENTER);
    }

    void setupRestartPanel(int width) {
        restartButton.setFont(new Font("Arial", Font.BOLD, 16));
        restartButton.setPreferredSize(new Dimension(width, 30));
        restartButton.addActionListener(e -> resetGame());

        restartGamePanel.add(restartButton);
        frame.add(restartGamePanel, BorderLayout.SOUTH);
    }

    void setupTimer() {
        hideCardTimer = new Timer(1000, e -> hideCards());
        hideCardTimer.setRepeats(false);
    }

    void startGameTimer() {
        gameTimeTimer = new Timer(1000, e -> {
            secondsPlayed++;
            textLabel.setText("Errors: " + errorCount + " | Time: " + secondsPlayed + "s");
        });
        gameTimeTimer.start();
    }

    void handleCardClick(JButton tile) {
        if (!gameReady) return;
        if ((boolean) tile.getClientProperty("flipped")) return;

        int index = board.indexOf(tile);
        tile.setIcon(cardSet.get(index).cardImage);
        tile.putClientProperty("flipped", true);

        if (selectedCard1 == null) {
            selectedCard1 = tile;
        } else if (selectedCard2 == null) {
            selectedCard2 = tile;
            gameReady = false;
            checkMatch();
        }
    }

    void checkMatch() {
        int index1 = board.indexOf(selectedCard1);
        int index2 = board.indexOf(selectedCard2);

        if (cardSet.get(index1).cardName.equals(cardSet.get(index2).cardName)) {

            selectedCard1.setEnabled(false);
            selectedCard2.setEnabled(false);

            selectedCard1 = null;
            selectedCard2 = null;
            gameReady = true;

            pairsFound++;

            if (pairsFound == pairsToFind) {
                gameTimeTimer.stop();

                int finalScore = 1000 - (errorCount * 10) - (secondsPlayed * 2);
                if (finalScore < 0) finalScore = 0;

                frame.dispose();
                Scoreboard.showScoreboard(finalScore, secondsPlayed);
            }

        } else {
            errorCount++;
            textLabel.setText("Errors: " + errorCount + " | Time: " + secondsPlayed + "s");
            hideCardTimer.start();
        }
    }

    void hideCards() {
        selectedCard1.setIcon(cardBackImageIcon);
        selectedCard2.setIcon(cardBackImageIcon);

        selectedCard1.putClientProperty("flipped", false);
        selectedCard2.putClientProperty("flipped", false);

        selectedCard1 = null;
        selectedCard2 = null;

        gameReady = true;
    }

    void resetGame() {

        gameReady = false;
        selectedCard1 = null;
        selectedCard2 = null;

        pairsFound = 0;
        errorCount = 0;
        secondsPlayed = 0;

        textLabel.setText("Errors: 0 | Time: 0s");

        shuffleCards();

        for (JButton tile : board) {
            tile.setIcon(cardBackImageIcon);
            tile.putClientProperty("flipped", false);
            tile.setEnabled(true);
        }

        gameReady = true;
        gameTimeTimer.restart();
    }
}
