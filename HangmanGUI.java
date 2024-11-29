import java.awt.*; // gives classes necessary to create a GUI/applet
import java.awt.event.ActionEvent; // stores information about an action event (pressing the guess button)
import java.awt.event.ActionListener; // to be able to perform an action once a button is pressed
import java.io.File; // lets the program use files in my computer
import java.io.FileNotFoundException; // lets the program handle the error that happens when a file is missing
import java.util.ArrayList; // allows program to use array lists (no. of guesses, and words from .txt file)
import java.util.List; // allows the program to use the list interface
import java.util.Random; // allows the program to randomize given data
import java.util.Scanner; // lets program read input from different source (files, keyboard, mouse-click, etc.)
import javax.swing.*; // creates the GUI

public class HangmanGUI 
{
    private String word; // The word that the user is guessing
    private List<Character> playerGuesses; // To keep track of guesses from the player
    private int wrongCount; // To keep track of number of wrong letters
    private JLabel wordLabel; // The word display
    private JTextArea hangmanLabel; // The hangman drawing
    private JTextField guessField; // The area the player writes in
    private JButton guessButton; // The button to press when guessing a letter

    public HangmanGUI() 
    {
        // Initializes game variables
        playerGuesses = new ArrayList<>();
        wrongCount = 0;

        // Where to choose a random word from
        try 
        {
            word = getRandomWordFromFile("words.txt"); // path for the .txt file with 854 words
        } 
        catch (FileNotFoundException e) 
        {
            JOptionPane.showMessageDialog(null, "Word file not found!"); // message to display when the .txt file is not found
            System.exit(1); // what to do when the file is not found
        }

        // Setup GUI settings
        JFrame frame = new JFrame("Hangman"); // name of app displayed at the top
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // decides what to do when the app is closed
        frame.setSize(600, 450); // sets window size of the game
        frame.setLayout(new BorderLayout());

        // Hangman drawing area
        hangmanLabel = new JTextArea(getHangmanState(wrongCount));
        hangmanLabel.setFont(new Font("Monospaced", Font.PLAIN, 20)); // the settings for the hangman drawing
        hangmanLabel.setEditable(false); // Prevent user editing
        hangmanLabel.setFocusable(false); // Make it display-only
        hangmanLabel.setBackground(frame.getBackground()); // Match frame background
        frame.add(hangmanLabel, BorderLayout.NORTH);

        // Word display
        wordLabel = new JLabel(getWordDisplay());
        wordLabel.setFont(new Font("Monospaced", Font.BOLD, 24)); // settings for the word display
        wordLabel.setHorizontalAlignment(SwingConstants.CENTER); // position of the word display
        frame.add(wordLabel, BorderLayout.CENTER);

        // Guess input area
        JPanel inputPanel = new JPanel();
        guessField = new JTextField(5);
        guessButton = new JButton("Guess"); // writing on the button to press when the user has entered a letter to guess
        inputPanel.add(new JLabel("Enter a letter:")); // writing infront of the box where the user enters a letter
        inputPanel.add(guessField); // the box where the user inputs a letter to guess
        inputPanel.add(guessButton); // button to press when the user has entered a letter to guess
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Add button action to the Guess button
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
    
    // the code for the guessing
    private void processGuess() 
    {
        String guess = guessField.getText(); // the letter the user inputs to guess
        if (guess.isEmpty() || guess.length() > 1) // error to show when the user doesn't input a letter and presses guess
        {
            JOptionPane.showMessageDialog(null, "Please enter a single letter.");
            return;
        }

        // code for the area that the correct guessed letters are displayed
        char guessedChar = guess.charAt(0);
        guessField.setText("");

        if (playerGuesses.contains(guessedChar)) // code for the message to display when the user guesses the same letter more than once
        {
            JOptionPane.showMessageDialog(null, "You already guessed that letter!");
            return;
        }

        playerGuesses.add(guessedChar); // stores the letters that the player has already guessed

        if (word.contains(String.valueOf(guessedChar))) // the code for when the player has either ran our of guesses or when the player guesses the word correct
        {
            // when the guess is correct
            wordLabel.setText(getWordDisplay());
            if (isWordComplete()) 
            {
                JOptionPane.showMessageDialog(null, "You win! The word was: " + word); // message displayed when user guesses the word correct
                System.exit(0); // what the code should do when the user guesses the word correct
            }
        } 
        else 
        {
            // incorrect guesses
            wrongCount++; // when the guess is incorrect
            hangmanLabel.setText(getHangmanState(wrongCount)); // Update hangman state
            if (wrongCount >= 6) // max number of wrong guesses is 6, therefore it's wrongcount >= 6
            {
                JOptionPane.showMessageDialog(null, "You lose! The word was: " + word); // message displayed when user runs out of guesses
                System.exit(0); // what the code should do when the user runs out of guesses
            }
        }
    }

    // display of the letters ex: player guesses "a", display shows _ _ a _ _
    private String getWordDisplay() 
    {
        StringBuilder display = new StringBuilder();
        for (char c : word.toCharArray()) 
        {
            if (playerGuesses.contains(c)) // if the guessed letter is in the randomly chosen word
            {
                display.append(c).append(" "); // updates the word display to show the guessed letter in the correct position in the word
            } 
            else 
            {
                display.append("_ "); // puts _ when the letter that the user guessed is wrong (doesn't add the letter)
            }
        }
        return display.toString();
    }

    private boolean isWordComplete() // checks wether the user has guessed all the letters correctly
    {
        for (char c : word.toCharArray()) 
        {
            if (!playerGuesses.contains(c)) // what to do when the player guess does not include correct letter
            {
                return false; // returns false if the player guess is not in the word
            }
        }
        return true; // returns true if the player guesses are in the word
    }

    // the drawings for the hangman depending on how many letters the player gets wrong
    private String getHangmanState(int wrongCount) 
    {
        String[] states = 
        {
            " -------\n |     |\n\n\n\n\n", // no wrongs
            " -------\n |     |\n O\n\n\n\n", // one wrong
            " -------\n |     |\n O\n \\ \n\n\n", // two wrongs
            " -------\n |     |\n O\n \\ / \n\n\n", // three wrongs
            " -------\n |     |\n O\n \\ / \n  | \n\n", // four wrongs
            " -------\n |     |\n O\n \\ / \n  | \n / \n", // five wrongs
            " -------\n |     |\n O\n \\ / \n  | \n / \\" // six wrongs
        };
        return states[wrongCount];
    }

    // code for choosing random word from the given .txt file
    private String getRandomWordFromFile(String filePath) throws FileNotFoundException 
    {
        Scanner scanner = new Scanner(new File(filePath));
        List<String> words = new ArrayList<>(); // puts the words in the file in an ArrayList
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
        new HangmanGUI(); // starts HangmanGUI when the code is run
    }
}