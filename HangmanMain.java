import java.io.FileNotFoundException;
import javax.swing.SwingUtilities;

public class HangmanMain {
    public static void main(String[] args) 
    {
        // messages shown in the console once the game is launched
        System.out.println("Welcome to Hangman!");
        System.out.println("Choose your game mode:");
        System.out.println("1. Console");
        System.out.println("2. GUI");
        
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choice = scanner.nextInt();

        // what to do when the user has chosen the console version of the game
        if (choice == 1) 
            try
            {
                // Launch console version of game
                ConsoleHangman consoleGame = new ConsoleHangman();
                consoleGame.start();
            } 
            catch (FileNotFoundException e) 
            {
                System.out.println("Error: Word file not found.");
            } 

        // what to do when the user has chosen the GUI version of the game
        else if (choice == 2) 
        {
            // Launch GUI version of game
            SwingUtilities.invokeLater(HangmanGUI::new);
        } 

        // if user writes any number other than 1 or 2
        else 
        {
            System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }
}