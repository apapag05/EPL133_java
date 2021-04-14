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
	private ArrayList<String>[] allLists; //an array with arraylists - each array list represents a specific pattern
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
	public int binaryValue(String s, String c) {
		int value=0;
		for (int i=s.length()-1;i>=0;i--) {
			if (s.charAt(i)==c.charAt(0)) {
				value+=(int)(Math.pow(2, i));
			}
		}
		return value;
	}
	
	public void moves(String c) {
		splitList(c);
		biggestList();
	}

	public void splitList(String c) {
		int value=0;
		for (String word : currentList) {
			value=binaryValue(word,c);
			allLists[value].add(word);
		}
	}
	
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
		printList(currentList);
	}
	
	public List<String> copyList(List<String> list) {
		List<String> newList=new ArrayList<String>();
		for (String word : list) {
			newList.add(word);
		}
		return newList;
	}
	
	public void printList(List<String> list) {
		for (String word : list) {
			System.out.print(word + " ");
		}
	}
	
}
