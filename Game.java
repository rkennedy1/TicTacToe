package Game;

import java.util.Scanner;

public class Game {
	//Defining class vars
	public char[][] gameArray;
	public int pos1;
	public int pos2;
	public int count;

	public static void main(String []args) {
		//Setting vars
		int player = 0;
		Game myGame = new Game();
		boolean continueGame = true;
		myGame.gameArray = initializeArray();
		Scanner input = new Scanner(System.in);
		do { //do while loop
			//Setting which player is playing at the time
			String temp = "O";
			if (player % 2 == 0 ) {
				temp = "X";
			}
			if (player == 0) {
				printBoard(myGame);
			}
			System.out.print("\nEnter your move player " + temp + " with syntax Row,Column: "); //prompting the player
			String userInput = input.next();
			if (checkPosIsValid(userInput, myGame) == true) { //checking if the move is a valid input with the formatting
				if (checkPosIsOpen(myGame) == true) { //Checks if the move is open or occupied (if open will return true)
					placeMarker(userInput, player, myGame); //places the marker (X/O) where the move is on the board
					printBoard(myGame); //Prints the current game board
					if(isWinner(myGame) == true){ //Checks if the current player is a winner or not
						System.out.println("\nPlayer " + temp + " won!!"); 
						continueGame = false; //stops the game
					}
					player++; 
				} else {
					System.out.println("That space is occupied, try again"); //error message

				}
			} else {
				System.out.println("Incorrect syntax, try again"); //error message
			}
			if (player == 9 && isWinner(myGame) == false) { //if there has been 9 moves and no one has won 
				continueGame = false; //The game ends and prints "Its a draw"
				System.out.println("\nIts a draw");
			}
		} while (continueGame); 
	}
	public static char[][] initializeArray() { //The default board array
		char[][] array = {
				{49, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32},
				{50, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32},
				{51, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32}
		};
		return array;
	}
	public static void printBoard(Game myGame) {
		char[][] array = myGame.gameArray; 
		int count = 0;
		for(int i = 0; i < array.length; i++) { 
			for (int j = 0; j < array[0].length; j++) { 
				if (count % array[0].length == 0) { //if count var remainder of the arrays length is 0
					if (!(count == 0))						//If count does not equal 0 it will print a line
						System.out.print("\n------------"); 
					if (count == 0) { //If count equals 0 it will print the numbers on top
						System.out.println("\n  1 | 2 | 3 ");
						System.out.print(array[i][j]);
					}
					else
						System.out.print("\n" + array[i][j]); //prints a new line
				}else
					System.out.print(array[i][j]);
				count++;
			}
		}
	}
	public static void placeMarker(String input, int player, Game myGame) {
		int[] pos2vals = {2,6,10}; //values for pos2
		myGame.gameArray[myGame.pos1-1][pos2vals[myGame.pos2-1]] = (char) ((player % 2 == 0) ? 88 : 79); //if the remainder of player and 2 is 0 then use X if not use O
	}
	public static boolean checkPosIsOpen(Game myGame) {
		int pos2 = 0;
		switch (myGame.pos2){  //uses this switch to set pos2 to the exact spots on the array since the first column on the board isnt the 1st space in the array
		case 1:
			pos2 = 2;
			break;
		case 2:
			pos2 = 6;
			break;
		case 3:
			pos2 = 10;
			break;
		}
		return myGame.gameArray[myGame.pos1-1][pos2] == 32; //If the space is open it will return false else it will return true
	}
	public static boolean checkPosIsValid(String input, Game myGame) {
		if (input.length() == 3 && input.charAt(1) == ',') { //checks if the input is 3 long and the 2nd char is a comma
			changeToInteger1(input, myGame); //changes the 1st char to an int
			changeToInteger2(input, myGame); //changes the 1st char to an int
			if ((myGame.pos1 <= 3 && myGame.pos1 >= 1) && (myGame.pos2 <= 3 && myGame.pos2 >= 1)) { //checks if the points are between 1 and 3
				return true;
			}
		}
		return false;
	}
	public static void changeToInteger1(String input, Game myGame) {
		myGame.pos1 = Integer.parseInt(input.substring(0,1)); //first substrings out the 1st char then parses it to an int
	}
	public static void changeToInteger2(String input, Game myGame){
		myGame.pos2 = Integer.parseInt(input.substring(2,3)); //first substrings out the 2nd char then parses it to an int
	}
	public static boolean isWinner(Game myGame) {
		return checkDiagonals(myGame) || checkCols(myGame) || checkRows(myGame); //checks all the methods for the winning combination 
	}
	public static boolean checkRows(Game myGame) {
		char[][] array = myGame.gameArray;
		for (int i=0;i<array.length;i++){ 
			if (array[i][2] == array[i][6] && array[i][6] == array[i][10] && array[i][2] != 32) { //checks if values are the same and not blank
				return true;
			}
		}
		return false;
	}
	public static boolean checkDiagonals(Game myGame) {
		char[][] array = myGame.gameArray;
		if ((array[0][2] == 88 && array[1][6] == 88 && array[2][10] == 88) || (array[0][2] == 79 && array[1][6] == 79 && array[2][10] == 79)) { //checks if values are the same and not blank
			return true;
		}
		if ((array[0][10] == 88 && array[1][6] == 88 && array[2][2] == 88) || (array[0][10] == 79 && array[1][6] == 79 && array[2][2] == 79)) { //checks if values are the same and not blank
			return true;
		}
		return false;
	}
	public static boolean checkCols(Game myGame) {
		char[][] array = myGame.gameArray;
		int k = 0;
		for (int i=0;i<array.length;i++){
			if (i != 3) {
				switch (i) { //uses this switch to set k to the exact spots on the array since the first column on the board isnt the 1st space in the array
				case 0:
					k = 2; 
					break;
				case 1:
					k = 6; 
					break;
				case 2:
					k = 10; 
					break;
				}
				if (array[0][k] == array[1][k] && array[1][k] == array[2][k] && array[1][k] != 32) { //Values are the same and not blank
					return true;
				} 
			} 
		}
		return false;
	}
}