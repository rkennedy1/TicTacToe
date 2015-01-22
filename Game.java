package Game;

import java.util.Scanner;

public class Game {
	public char[][] gameArray;
	public int pos1;
	public int pos2;
	public int count;

	public static void main(String []args) {
		int player = 0;
		Game myGame = new Game();
		boolean continueGame = true;
		myGame.gameArray = initializeArray();
		Scanner input = new Scanner(System.in);
		do {
			String temp = "O";
			if (player % 2 == 0 ) {
				temp = "X";
			}
			if (player == 0) {
				printBoard(myGame);
			}
			System.out.print("\nEnter your move player " + temp + " with syntax Row,Column: ");
			String userInput = input.next();
			if (checkPosIsValid(userInput, myGame) == true) {
				if (checkPosIsOpen(myGame) == true) {
					placeMarker(userInput, player, myGame);
					printBoard(myGame);
					if(isWinner(myGame) == true){
						System.out.println("\nPlayer " + temp + " won!!");
						continueGame = false;
					}
					player++;
				} else {
					System.out.println("That space is occupied, try again"); 

				}
			} else {
				System.out.println("Incorrect syntax, try again");
			}
			if (player == 9 && isWinner(myGame) == false) {
				continueGame = false;
				System.out.println("\nIts a draw");
			}
		} while (continueGame);
	}
	public static char[][] initializeArray() {
		char[][] array = {
				{49, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32},
				{50, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32},
				{51, 32, 32, 32,  124, 32, 32, 32, 124, 32, 32, 32}
		};
		return array;
	}
	public static void printBoard(Game myGame) {
		char[][] array = myGame.gameArray;
		int q = 0;
		for(int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[0].length; j++) {
				if (q % array[0].length == 0) {
					if (!(q == 0))						
						System.out.print("\n------------");
					if (q == 0) {
						System.out.println("\n  1 | 2 | 3 ");
						System.out.print(array[i][j]);
					}
					else
						System.out.print("\n" + array[i][j]);
				}else
					System.out.print(array[i][j]);
				q++;
			}
		}
	}
	public static void placeMarker(String input, int player, Game myGame) {
		int[] pos2vals = {2,6,10};
		myGame.gameArray[myGame.pos1-1][pos2vals[myGame.pos2-1]] = (char) ((player % 2 == 0) ? 88 : 79);	
	}
	public static boolean checkPosIsOpen(Game myGame) {
		int pos2 = 0;
		switch (myGame.pos2){
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
		return myGame.gameArray[myGame.pos1-1][pos2] == 32;
	}
	public static boolean checkPosIsValid(String input, Game myGame) {
		if (input.length() == 3 && input.charAt(1) == ',') {
			changeToInteger1(input, myGame); 
			changeToInteger2(input, myGame);
			if ((myGame.pos1 <= 3 && myGame.pos1 >= 1) && (myGame.pos2 <= 3 && myGame.pos2 >= 1)) {
				return true;
			}
		}
		return false;
	}
	public static void changeToInteger1(String input, Game myGame) {
		myGame.pos1 = Integer.parseInt(input.substring(0,1));
	}
	public static void changeToInteger2(String input, Game myGame){
		myGame.pos2 = Integer.parseInt(input.substring(2,3));
	}
	public static boolean isWinner(Game myGame) {
		return checkDiagonals(myGame) || checkCols(myGame) || checkRows(myGame);
	}
	public static boolean checkRows(Game myGame) {
		char[][] array = myGame.gameArray;
		for (int i=0;i<array.length;i++){
			if (array[i][2] == array[i][6] && array[i][6] == array[i][10] && array[i][2] != 32) { //Values are the same and not blank
				return true;
			}
		}
		return false;
	}
	public static boolean checkDiagonals(Game myGame) {
		char[][] array = myGame.gameArray;
		if ((array[0][2] == 88 && array[1][6] == 88 && array[2][10] == 88) || (array[0][2] == 79 && array[1][6] == 79 && array[2][10] == 79)) {
			return true;
		}
		if ((array[0][10] == 88 && array[1][6] == 88 && array[2][2] == 88) || (array[0][10] == 79 && array[1][6] == 79 && array[2][2] == 79)) {
			return true;
		}
		return false;
	}
	public static boolean checkCols(Game myGame) {
		char[][] array = myGame.gameArray;
		int k = 0;
		for (int i=0;i<array.length;i++){
			if (i != 3) {
				switch (i) {
				case 0:
					k = 2; break;
				case 1:
					k = 6; break;
				case 2:
					k = 10; break;
				}
				if (array[0][k] == array[1][k] && array[1][k] == array[2][k] && array[1][k] != 32) { //Values are the same and not blank
					return true;
				} 
			} 
		}
		return false;
	}
}