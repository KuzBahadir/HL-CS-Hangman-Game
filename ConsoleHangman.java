import java.io.File; // lets the program use files in my computer
import java.io.FileNotFoundException; // lets the program handle the error that happens when a file is missing
import java.util.ArrayList; // allows program to use array lists (no. of guesses, and words from .txt file)
import java.util.List; // allows the program to use the list interface
import java.util.Random; // allows the program to randomize given data
import java.util.Scanner; // lets program read input from different source (files, keyboard, mouse-click, etc.)

public class ConsoleHangman 
{
    // starts the code once it has been chosen in the HangmanMain class
    public void start() throws FileNotFoundException 
    {
    main(new String[0]); // Reuse the main method logic
    }
    public static void main(String[] args) throws FileNotFoundException 
    {  
      Scanner keyboard = new Scanner(System.in); // sets keyboard as an input source
    
      System.out.println("1 or 2 players? (type 1 or 2)"); // asks player wether they would like to play single player mode or 2-player mode
      String players = keyboard.nextLine(); // scans next line in the console for the response 
      
      String word;
    
      if (players.equals("1")) // if player chooses single player
      {
        Scanner scanner = new Scanner(new File("words.txt")); // scans the .txt file
        List<String> words = new ArrayList<>(); // puts the words from the .txt file into an ArrayList
      
        while (scanner.hasNext()) 
        {
          words.add(scanner.nextLine()); // adds each word that the scanner scans into ArrayList
        }
        
        Random rand = new Random(); // randomize words
        word = words.get(rand.nextInt(words.size())); // gets random word from .txt file
      }
      else // if player chooses 2 player by typing in 2
      {
        System.out.println("Player, please enter your word:");
        word = keyboard.nextLine(); // the word becomes the word that the player types into the next line
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); // prints empty line
        System.out.println("Ready for the guesser! Good luck!");
      }
      
      //System.out.println(word);
    
      List<Character> playerGuesses = new ArrayList<>(); // keeps the number of guesses by the player in an ArrayList
    
      Integer wrongCount = 0; // number of wrong guesses is set to 0
    
      while(true) 
      {
        printHangedMan(wrongCount); // how many stages of the hangman should be drawn depending on how many wrong guesses the player has
      
        if (wrongCount >= 6) // limits number of wrong guesses to 6
        {
          System.out.println("You lose!"); // tells user they lost
          System.out.println("The word was: " + word); // reveals the word to the player
          break;
        }
      
        printWordState(word, playerGuesses); // state display function of the hangman
        if (!getPlayerGuess(keyboard, word, playerGuesses)) // if the player's guess is not in the word, increase the wrong guess count
        {
          wrongCount++;
        }
      
        if(printWordState(word, playerGuesses)) // if the word is completely revealed, print a winning message and end the game
        {
          System.out.println("You win!");
          break;
        }
      
        System.out.println("Please enter your guess for the word:"); // asks the player for a guess of what the word could be
        if(keyboard.nextLine().equals(word)) // if the entered word by the player is the correct word, display winning message and end the game
        {
          System.out.println("You win!");
          break;
        }
        else // if not, display a message that tells player to try again
        {
          System.out.println("Nope! Try again.");
        }
      }
    }

    private static void printHangedMan(Integer wrongCount) // how the hangman should be drawn depending on the amount of wrong guesses by the player
    {
      System.out.println(" -------");
      System.out.println(" |     |");
      if (wrongCount >= 1) 
      {
        System.out.println(" O"); // the head of the hangman
      }
      
      if (wrongCount >= 2) 
      {
        System.out.print("\\ ");
        if (wrongCount >= 3) 
        {
          System.out.println("/"); // the left arm of the hangman
        }
        else 
        {
          System.out.println("");
        }
      }
    
      if (wrongCount >= 4) 
      {
        System.out.println(" |"); // the body of the hangman
      }
    
      if (wrongCount >= 5) 
      {
        System.out.print("/ "); // the right arm of the hangman
        if (wrongCount >= 6) 
        {
          System.out.println("\\");
        }
        else 
        {
          System.out.println("");
        }
      }
      System.out.println("");
      System.out.println("");
    }

    private static boolean getPlayerGuess(Scanner keyboard, String word, List<Character> playerGuesses) // asks the player to guess a letter, records their guess, and checks whether the letter is part of the word
    {
      System.out.println("Please enter a letter:");
      String letterGuess = keyboard.nextLine();
      playerGuesses.add(letterGuess.charAt(0));
    
      return word.contains(letterGuess);
    }

    private static boolean printWordState(String word, List<Character> playerGuesses) // displays the current progress of the word, revealing letters guessed correctly while hiding the rest, and checks if the player has guessed the entire word
    {
      int correctCount = 0;
      for (int i = 0; i < word.length(); i++) 
      {
        if (playerGuesses.contains(word.charAt(i))) 
        {
          System.out.print(word.charAt(i));
          correctCount++;
        }
        else 
        {
          System.out.print("-");
        }
      }
      System.out.println();
    
      return (word.length() == correctCount);
    }
  }