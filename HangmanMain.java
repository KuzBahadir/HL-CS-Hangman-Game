
import javax.swing.SwingUtilities;

public class HangmanMain {
    public static void main(String[] args) 
    {
        System.out.println("Welcome to Hangman!");
        System.out.println("Choose your game mode:");
        System.out.println("1. Console");
        System.out.println("2. GUI");
        
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choice = scanner.nextInt();

        if (choice == 1) 
        {
            // Launch console version of game
            ConsoleHangman consoleGame = new ConsoleHangman();
            consoleGame.start();
        } 
        else if (choice == 2) 
        {
            // Launch GUI version of game
            SwingUtilities.invokeLater(HangmanGUI::new);
        } 
        else 
        {
            System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }
}
