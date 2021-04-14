package team18.hw5;

import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * This class creates a HangMan object and runs the main method.
 * 
 * @author Andria-Maria Papageorgiou & Athina Nicolaou
 * @since 14/4/2021
 *
 */
public class HangMan {

	private int guesses; 
	private int length; // number of length of the word
	private PossibleWords words; // object with all the possible list of words
	private char[] current; //array with the missing word
	private List<String> guessed; //list with the guessed letters of the word
	private String curGuess; //current guess-letter

	/**
	 * The constructor of {@link HangMan} creates the HangMan game.
	 * @param dictionary is a list with all the words.
	 * @param length is the length of the missing word.
	 * @param tries is the number of the total tries a player has.
	 */
	public HangMan(List<String> dictionary, int length, int tries) {
		this.length = length;
		this.guesses = tries;
		System.out.println("You choose to use a length word of: " + length);
		System.out.println("You choose to use a maximum of tries: " + tries);
		current = new char[length];
		this.fillCurrent(); //fill the table with -
		words = new PossibleWords(dictionary,length); //new object
		curGuess = "";
		guessed = new ArrayList<String>();
	}

	/**
	 * 	This method fills the array with the characters of the missing word with dashes.
	 */
	public void fillCurrent() {
		for (int i = 0; i < this.length; i++) {
			current[i] = '-';
		}
	}

	/**
	 * This method is the representation of the array with the characters of the missing word.
	 * @return a String with the missing word.
	 */
	public String printCurrent() {
		String s = "";
		for (int i = 0; i < current.length; i++) {
			s += current[i];
		}
		return s;
	}

	/**
	 * This method is the representation of the {@link HangMan}.
	 */
	public String toString() {
		String s = "\n";
		s += "guesses : " + guesses + "\n";
		s += "words : " + words.getLength();
		s += "\nguessed : " + guessed;
		s += "\ncurrent : " + printCurrent() + "\n";
		return s;
	}

	/**
	 * This method check whether the input in the Arguments is valid.
	 * @param one a String value
	 * @param two a String value
	 * @return an array with two integer values which represent the two String parameters.
	 */
	public static int[] checkArgs(String one, String two) {
		int[] array = new int[2];
		Scanner scan = new Scanner(System.in);
		
		//while the first String is not a number between 1 and 9 ask for a new input
		while (one.length() > 1 || !(one.charAt(0) >= '1' && one.charAt(0) <= '9')) {
			System.out.println("You must enter a valid number for the length of the word.");
			one = scan.next();
		}
		
		//while the second String is not a number between 1 and 9 ask for a new input
		while (two.length() > 1 || !(two.charAt(0) >= '1' && two.charAt(0) <= '9')) {
			System.out.println("You must enter a valid number for the tries.");
			two = scan.next();
		}
		array[0] = Integer.parseInt(one);
		array[1] = Integer.parseInt(two);
		return array;
	}

	/**
	 * This method checks if the player has won.
	 * @return false if the player used all his tries without winning, otherwise returns true.
	 */
	public boolean win() {
		if (this.guesses == 0) {
			return true;
		} else {
			//if all the characters of the array have a different value from '-' then it means that
			//the player has found all the letters of the missing word.
			for (int i = 0; i < current.length; i++) {
				if (current[i] == '-') {
					return false;
				}

			}
			return true;
		}

	}

	/**
	 * This method checks if the player has made the same guess as before.
	 * @param guess is the letter the player guessed.
	 * @return true if the player has guessed the same letter before, otherwise returns false.
	 */
	public boolean sameGuess(String guess) {
		Scanner scan = new Scanner(System.in);
		for (String letter : guessed) {
			if (letter.equals(guess)) {
				/*
				 * System.out.println("You already guessed that\n"); System.out.println(this);
				 * System.out.print("Your guess? "); curGuess=scan.next();
				 */
				return true;
			}
		}
		return false;
	}

	/**
	 * This method checks if the letter the player submitted is a letter of the alphabet.
	 * @param guess a String which represents the guess of the player.
	 * @return true if the String is a letter of the alphabet.
	 */
	public boolean validGuess(String guess) {
		Scanner scan = new Scanner(System.in);
		if (guess.length() > 1 || !(guess.charAt(0) >= 'a' && guess.charAt(0) <= 'z')) {
			/*
			 * System.out.print("Your guess should a lower case of the alphabet (a-z): ");
			 * guess=scan.next();
			 */
			return false;
		}
		return true;
	}

	/**
	 * This method is the play method of the {@link HangMan}, while there is not a winner
	 * the PC asks for another guess from the user. Checks if the input is {@link validGuess()} or {@link sameGuess()}
	 * and executes specific commands accordingly.
	 */
	public void play() {
		while (!win()) {
			Scanner scan = new Scanner(System.in);
			System.out.println(this.toString()); //print HangMan
			System.out.print("Your guess? ");
			this.curGuess = scan.next();
			if (!validGuess(this.curGuess)) { //if not valid guess
				System.out.print("Your guess should a lower case of the alphabet (a-z)");
			} else if (sameGuess(this.curGuess)) { //if same guess
				System.out.println("You already guessed that");
			}
			else {
				words.initializeArray();
				guessed.add(curGuess); //add the letter/char in the list of guesses
				guesses--; //decreases the tries/guesses
				words.moves(curGuess);
			}

		}
	}

	/**
	 * This if the main method of our programme which reads from a file and creates a {@link HangMan} game.
	 * @param args takes input for the txt file, the length of the word and the tries of the player.
	 */
	public static void main(String[] args) {

		List<String> dictionary = new ArrayList<String>(); //a list for all the words of the game
		System.out.println("Welcome to the hangman game.");
		
		//reads from a file and adds all the Strings in a list.
		try {
			File infile = new File(args[0]);
			Scanner scan = new Scanner(infile);
			while (scan.hasNextLine()) {
				dictionary.add(scan.nextLine());
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		int[] array = new int[2];
		array = checkArgs(args[1], args[2]); //check the arguments
		HangMan hangMan = new HangMan(dictionary, array[0], array[1]); //creates HangMan game
		hangMan.play(); //play HangMan
	}
}