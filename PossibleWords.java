package team18.hw5;

import java.util.*;

/**
 * This class represents the operation which chooses in each round the list with the remaining words of the {@link HangMan} game.
 * @author Andria-Maria Papageorgiou & Athina Nicolaou
 * @since 14/4/2021
 *
 */
public class PossibleWords {
	private List<String> currentList; //list with the remaining words for each round
	private ArrayList<String>[] allLists; //an array with arraylists - each array list represents a specific pattern based on the decimal value of the world
	private int lengthOfArray;

	/**
	 * The constructor creates the lists with all the words of the game.
	 * @param dictionary is a list with all given words.
	 * @param length is the length of the missing word of (@link HangMan}.
	 */
	public PossibleWords(List<String> dictionary, int length) {
		currentList = new ArrayList<String>();
		//add all words in the current list
		for (String word : dictionary) {
			if (word.length() == length)
				currentList.add(word);
		}
		if (currentList.size()==0) {
			System.out.println("There are no words with this length in the txt file.");
			System.exit(0);
		}
		
		//length of array equals all the possible combinations depending on the length of the word
		lengthOfArray=(int)(Math.pow(2, length)) - 1; 
		this.allLists=new ArrayList[this.lengthOfArray];
		initializeArray();
	}

	/**
	 * This method initializes the lists of the array {@link allLists}.
	 */
	public void initializeArray() {
		for (int i = 0; i < allLists.length; i++) {
            allLists[i] = new ArrayList<String>();
        }
	}
	
	/**
	 * Getter method of the length of the current list from which the PC can choose.
	 * @return the length of the current list from which the PC can choose.
	 */
	public int getLength() {
		return currentList.size();
	}
	
	/**
	 * This method converts a String to a binary number depending on the indeces which have the given character and then gets its decimal value.
	 * @param s is the word.
	 * @param c is the letter player guessed
	 * @return the decimal value of a String.
	 */
	public int decimalValue(String s, String c) {
		int value=0;
		for (int i=s.length()-1;i>=0;i--) {
			//if the character of the word is the same as the given character then it takes the value 1. if not it takes 0.
			//eg word: cool, character: 0 -> binary value : 0110 --> decimal value=2^2+2^1=5
			if (s.charAt(i)==c.charAt(0)) {
				value+=(int)(Math.pow(2, i));
			}
		}
		return value;
	}
	
	/**
	 * This method splits your currentList into smaller lists and then pick the next list to be current.
	 * @param character is the character the player guessed.
	 */
	public void moves(String character) {
		splitList(character);
		biggestList();
	}

	/**
	 * This method splits the current list into smaller lists by finding the {@link decimalValue} of each word and adding it
	 * to the appropriate list of an ArrayList based on this value (eg word with decimal value = 10 will be added in the
	 * ArrayList[10].
	 * @param character is a given letter and it represents the criteria of splitting the list.
	 */
	public void splitList(String character) {
		int value=0;
		for (String word : currentList) {
			value=decimalValue(word,character);
			allLists[value].add(word);
		}
	}
	
	/**
	 * This method finds the biggest list in the ArrayList and define it as the next current list.
	 * First index of ArrayList represents the list of words which do not have the player's guess, so if the next current list
	 * which was chosen before is the same size as this list, then the next current list will be this list.
	 */
	public void biggestList() {
		int max=0;
		for (int i=0;i<allLists.length;i++) {
			if (allLists[i].size()>=max) {
				max=allLists[i].size();
				currentList=copyList(allLists[i]);
			}
		}
		
		//if first list which do not have the letter is the same size as the list which was chosen before
		//then this is our currentList - pc is not playing fair
		if (allLists[0].size()==currentList.size()) {
			currentList=copyList(allLists[0]);
		}
		//printList(currentList);
	}
	
	/**
	 * This method checks if the word contains the given character/String and add the indeces of the character in the word
	 * in a list.
	 * @param character is the player's guess.
	 * @return a list with the indeces which have the specific character.
	 */
	public List<Integer> hasLetter(String character) {
		List<Integer> indeces=new ArrayList<Integer>();
		for (int i=0;i<currentList.get(0).length();i++) {
			if (currentList.get(0).charAt(i)==character.charAt(0)) {
				indeces.add(i);
			}
		}
		return indeces;
		
	}
	
	/**
	 * This method returns the first word of the currentList.
	 * @return the first word of the currentList.
	 */
	public String getWord() {
		return currentList.get(0);
	}
	
	/**
	 * This method operates like a copy constructor of a list.
	 * @param list
	 * @return a new list same as the given one.
	 */
	public List<String> copyList(List<String> list) {
		List<String> newList=new ArrayList<String>();
		for (String word : list) {
			newList.add(word);
		}
		return newList;
	}
	
}
