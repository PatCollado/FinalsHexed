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
      setPlayers();

      System.out.print("Who goes first?");
      first = sc.nextChar();

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
        board[initialRow + 1][initialCol] = 'g'; //above
        board[initialRow - 1][initialCol - 1] = 'g'; //lower left
        board[initialRow - 1][initialCol + 1] = 'g'; //lower right
        board[initialRow - 1][initialCol] = 'r'; //below
        board[initialRow][initialCol - 1] = 'r'; //upper left
        board[initialRow][initialCol + 1] = 'r'; //upper right
      }

  }

  public ArrayList<Coordinates> checkPossibleMoves(int row, int col, char you){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(int row, int col, char you);


  }

  public ArrayList<Coordinates> checkEnemyNeighbors(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> coor = new ArrayList<Coordinates>;
    if(board[row + 1][col] != you){
      Coordinate enemy = new Coordinate(row + 1, col);
      coor.add<enemy>;
    }

    if (board[row - 1][col - 1] != you){
      Coordinate enemy = new Coordinate(row - 1, col - 1);
      coor.add<enemy>;
    }

    if(board[row - 1][col + 1] != you){
      Coordinate enemy = new Coordinate(row - 1, col + 1);
      coor.add<enemy>;
    }

    if(board[row - 1][col] != you){
      Coordinate enemy = new Coordinate(row - 1, col);
      coor.add<enemy>;
    }

    if(board[row][col - 1] != you){
      Coordinate enemy = new Coordinate(row, col - 1);
      coor.add<enemy>;
    }

    if(board[row][col + 1] != you){
      Coordinate enemy = new Coordinate(row, col + 1);
      coor.add<enemy>;
    }

    return coor;
    // board[row + 1][col] above
    // board[row - 1][col - 1] lower left
    // board[row - 1][col + 1] lower right
    // board[row - 1][col] below
    // board[row][col - 1] upper left
    // board[row][col + 1] upper right
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
