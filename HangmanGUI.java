import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.*;

public class HangmanGUI 
{
    private String word;
    private List<Character> playerGuesses;
    private int wrongCount;
    private JLabel wordLabel;
    private JTextArea hangmanLabel; // Corrected to class-level JTextArea
    private JTextField guessField;
    private JButton guessButton;

    public HangmanGUI() 
    {
        // Initialize game variables
        playerGuesses = new ArrayList<>();
        wrongCount = 0;

        // Choose a word
        try 
        {
            word = getRandomWordFromFile("/Users/kazi/School_Coding_Stuff/hangman_HSCS/words.txt");
        } 
        catch (FileNotFoundException e) 
        {
            JOptionPane.showMessageDialog(null, "Word file not found!");
            System.exit(1);
        }

        // Setup GUI
        JFrame frame = new JFrame("Hangman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);
        frame.setLayout(new BorderLayout());

        // Hangman drawing area
        hangmanLabel = new JTextArea(getHangmanState(wrongCount));
        hangmanLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        hangmanLabel.setEditable(false); // Prevent user editing
        hangmanLabel.setFocusable(false); // Make it display-only
        hangmanLabel.setBackground(frame.getBackground()); // Match frame background
        frame.add(hangmanLabel, BorderLayout.NORTH);

        // Word display
        wordLabel = new JLabel(getWordDisplay());
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(wordLabel, BorderLayout.CENTER);

        // Guess input area
        JPanel inputPanel = new JPanel();
        guessField = new JTextField(5);
        guessButton = new JButton("Guess");
        inputPanel.add(new JLabel("Enter a letter:"));
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Add button action
        guessButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                processGuess();
            }
        });

        frame.setVisible(true);
    }

    private void processGuess() 
    {
        String guess = guessField.getText();
        if (guess.isEmpty() || guess.length() > 1) 
        {
            JOptionPane.showMessageDialog(null, "Please enter a single letter.");
            return;
        }

        char guessedChar = guess.charAt(0);
        guessField.setText("");

        if (playerGuesses.contains(guessedChar)) 
        {
            JOptionPane.showMessageDialog(null, "You already guessed that letter!");
            return;
        }

        playerGuesses.add(guessedChar);

        if (word.contains(String.valueOf(guessedChar))) 
        {
            // Correct guess
            wordLabel.setText(getWordDisplay());
            if (isWordComplete()) 
            {
                JOptionPane.showMessageDialog(null, "You win! The word was: " + word);
                System.exit(0);
            }
        } 
        else 
        {
            // Incorrect guess
            wrongCount++;
            hangmanLabel.setText(getHangmanState(wrongCount)); // Update hangman state
            if (wrongCount >= 6) 
            {
                JOptionPane.showMessageDialog(null, "You lose! The word was: " + word);
                System.exit(0);
            }
        }
    }

    private String getWordDisplay() 
    {
        StringBuilder display = new StringBuilder();
        for (char c : word.toCharArray()) 
        {
            if (playerGuesses.contains(c)) 
            {
                display.append(c).append(" ");
            } 
            else 
            {
                display.append("_ ");
            }
        }
        return display.toString();
    }

    private boolean isWordComplete() 
    {
        for (char c : word.toCharArray()) 
        {
            if (!playerGuesses.contains(c)) 
            {
                return false;
            }
        }
        return true;
    }

    private String getHangmanState(int wrongCount) 
    {
        String[] states = 
        {
            " -------\n |     |\n\n\n\n\n",
            " -------\n |     |\n O\n\n\n\n",
            " -------\n |     |\n O\n \\ \n\n\n",
            " -------\n |     |\n O\n \\ / \n\n\n",
            " -------\n |     |\n O\n \\ / \n  | \n\n",
            " -------\n |     |\n O\n \\ / \n  | \n / \n",
            " -------\n |     |\n O\n \\ / \n  | \n / \\"
        };
        return states[wrongCount];
    }

    private String getRandomWordFromFile(String filePath) throws FileNotFoundException 
    {
        Scanner scanner = new Scanner(new File(filePath));
        List<String> words = new ArrayList<>();
        while (scanner.hasNext()) 
        {
            words.add(scanner.nextLine());
        }
        scanner.close();
        Random rand = new Random();
        return words.get(rand.nextInt(words.size()));
    }

    public static void main(String[] args) 
    {
        new HangmanGUI();
    }
}
