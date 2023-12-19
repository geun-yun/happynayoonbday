package LovelyWordSearchGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/******************
 * WordSearchGame.WordsearchGenerator class
 * 
 * Uses the WordSearchGame.WordGenerator class to generate random words
 * Arrange the words randomly in a wordsearch board which is represented by a 2D String array
 * 
 * @author Tech-Ranch
 *
 ******************/

public class LovelyWordSearchGenerator {

	private int numOfWords;	//Number of words in the wordsearch
	private String board[][]; //[row] [col]
	private LovelyWordGenerator generator;
	private ArrayList<String> listOfWords = new ArrayList<String>();
	private int length;
	
	public LovelyWordSearchGenerator(int numOfWords, int length){
		generator = new LovelyWordGenerator();
		this.numOfWords = numOfWords;
		this.length = length;
	}

	public void setUpGuaranteed() {
		do {
			setUp();
		} while (!isUnique());
	}

	public boolean isUnique() {
		boolean[][] used = new boolean[length][length];

		for (String word : listOfWords) {
			// Find the start position and direction of each word
			int[] startPos = findWordStartPosition(word);
			if (startPos == null) {
				return false; // Word not found, should not happen
			}
			boolean isVertical = isWordVertical(word, startPos);

			for (int i = 0; i < word.length(); i++) {
				int row = startPos[0] + (isVertical ? i : 0);
				int col = startPos[1] + (isVertical ? 0 : i);

				if (used[row][col]) {
					return false; // Letter is used more than once
				}
				used[row][col] = true;
			}
		}
		return true;
	}
	private int[] findWordStartPosition(String word) {
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < length; col++) {
				// Check if the first letter matches
				if (board[row][col] != null && board[row][col].equals(word.substring(0, 1))) {
					if ((col + word.length() <= length && checkHorizontal(word, row, col)) ||
							(row + word.length() <= length && checkVertical(word, row, col))) {
						return new int[]{row, col};
					}
				}
			}
		}
		return null; // Word not found
	}

	private boolean checkHorizontal(String word, int row, int col) {
		for (int i = 0; i < word.length(); i++) {
			if (board[row][col + i] == null || !board[row][col + i].equals(word.substring(i, i + 1))) {
				return false;
			}
		}
		return true;
	}

	private boolean checkVertical(String word, int row, int col) {
		for (int i = 0; i < word.length(); i++) {
			if (board[row + i][col] == null || !board[row + i][col].equals(word.substring(i, i + 1))) {
				return false;
			}
		}
		return true;
	}

	private boolean isWordVertical(String word, int[] startPos) {
		int row = startPos[0];
		int col = startPos[1];

		// Check if the word extends vertically
		if (row + word.length() <= length) {
			for (int i = 1; i < word.length(); i++) {
				if (board[row + i][col] == null || !board[row + i][col].equals(word.substring(i, i + 1))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}


	/**
	 * Sets up the wordsearch grid by generating random words and inserting them into the 2D board array
	 */
	public void setUp(){
		int numGenerated = 0;
		
		//Create 2D array that represent the board
		board = new String[length][length];
		
		Random rand = new Random();
		
		while(numGenerated < numOfWords){
			
			ArrayList<Integer> randRow = new ArrayList<Integer>();
			ArrayList<Integer> randCol = new ArrayList<Integer>();
			
			String word =generator.generateWord();
			listOfWords.add(word);

			//determine if the word will be vertical or horizontal
			boolean vertical;
			int n = (rand.nextInt())%2;
			if(n == 0)
				vertical = true;
			else
				vertical = false;
			
			
			//generate a row 0 to the length - 1
			if(vertical){
				
				for(int i = 0; i<length; i++){
					randCol.add(i);
				}
				
				for(int j = 0; j<(length-word.length()); j++){
					randRow.add(j);
				}
				Collections.shuffle(randCol, new Random());
				Collections.shuffle(randRow, new Random());
				
				for(int j = 0; j < randCol.size(); j++){
					boolean success = false;
					for(int i = 0; i < randRow.size(); i++){
						int r = randRow.get(i);
						
						//check if the spots are empty in the 2d array
						boolean haveRoom = true;
						for(int k = 0; k < word.length(); k++){
							if(board[k+r][randCol.get(j)]!=null){
								//check for overlapping letters
								if(!board[k+r][randCol.get(j)].equals(word.substring(k, k+1))){
									haveRoom = false;
									break;
								}
							}
						}
						//if there's room, insert the word
						if(haveRoom){
							//insert each letter in the word into the board
							for(int k = 0; k < word.length(); k++){
								board[k+r][randCol.get(j)] = word.substring(k, k+1);
							}
							success = true;
							break;
						}
					}
					if(success)
						break;
				}
			}else{
				for(int i = 0; i<length; i++){
					randRow.add(i);
				}
				
				for(int j = 0; j<length-word.length(); j++){
					randCol.add(j);
				}
				
				Collections.shuffle(randCol, new Random());
				Collections.shuffle(randRow, new Random());
				
				for(int j = 0; j < randRow.size(); j++){
					boolean success = false;
					for(int i = 0; i < randCol.size(); i++){
						int c = randCol.get(i);
						
						//check if the spots are empty in the 2d array
						boolean haveRoom = true;
						for(int k = 0; k < word.length(); k++){
							if(board[randRow.get(j)][k+c]!=null){
								//check for overlapping letters
								if(!board[randRow.get(j)][k+c].equals(word.substring(k, k+1))){
									haveRoom = false;
									break;
								}
							}
						}
						//if there's room, insert the word
						if(haveRoom){
							//insert each letter in the word into the board
							for(int k = 0; k < word.length(); k++){
								board[randRow.get(j)][k+c] = word.substring(k, k+1);
							}
							success = true;
							break;
						}
					}
					if(success)
						break;
				}
			}
			numGenerated++;
		}
	}
	

	/**
	 * Accessor for the number of words
	 * @return numOfWords
	 */
	public int getNumOfWords() {
		return numOfWords;
	}

	/**
	 * Set the variable numOfWords to the given param
	 * @param numOfWords - the number of words
	 */
	public void setNumOfWords(int numOfWords) {
		this.numOfWords = numOfWords;
	}

	/**
	 * Accessor for the wordsearch board variable
	 * @return board - the 2D String array that represent the wordsearch board
	 */
	public String[][] getBoard() {
		return board;
	}

	/**
	 * Accessor the the list of words that were generated
	 * @return listOfWords - the list of words
	 */
	public ArrayList<String> getListOfWords() {
		return listOfWords;
	}

}
