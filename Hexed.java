import java.util.*;

public class Hexed{
  public static void main(String[] args){
      char[][] board = new char[7][9];
      Scanner sc = new Scanner(System.in);
      int initialRow = 0;
      int initialCol = 0;
      char first;
      char you;
      char opp;


      System.out.print("What's your color?");
      you = sc.nextChar();

      System.out.print("Enter starting center: ");
      initialRow = sc.nextInt();
      initialCol = sc.nextInt();

      board[initialRow][initialCol] = 'C';

      if(first == 'r'){
        board[initialRow + 1][initialCol] = 'r';
        board[initialRow - 1][initialCol - 1] = 'r';
        board[initialRow - 1][initialCol + 1] = 'r';
        board[initialRow - 1][initialCol] = 'g';
        board[initialRow][initialCol - 1] = 'g';
        board[initialRow][initialCol + 1] = 'g';
      }else if (first == 'g'){
        board[initialRow + 1][initialCol] = 'g';
        board[initialRow - 1][initialCol - 1] = 'g';
        board[initialRow - 1][initialCol + 1] = 'g';
        board[initialRow - 1][initialCol] = 'r';
        board[initialRow][initialCol - 1] = 'r';
        board[initialRow][initialCol + 1] = 'r';
      }

  }

  public void setPlayers(){
    if (you == 'g'){
      opp = 'r';
    } else {
      opp = 'g';
    }
  }

  public boolean checkNorth(int row, int col){
    char[][] tempBoard = new char[7][9];
    int tempRow;
    int tempCol;
    if(row != 6){
      for(int i = row + 1; i <= 6; i++){
          if(board[i][col] == opp){
            tempBoard[i+1][col] == you;
          }
      }
    }
  }




}
