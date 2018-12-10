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
      initializeBoard(initialRow, initialCol, first);

      todo:
      -terminating conditions; winner and loser conditions
      -hexed condition
      -actual gameplay
        -enemy move input
        -looping code that loops every after move has been made
      -output; the possible moves and the recommended moves
      -board printing



  }

  //initializes the board; puts the starting circle
  public void initializeBoard(int row, int col, char first){
    if(initialCol % 2 == 0){
      if(first == 'r'){
        board[row + 1][col] = 'r';
        board[row - 1][col - 1] = 'r';
        board[row - 1][col + 1] = 'r';
        board[row - 1][col] = 'g';
        board[row][col - 1] = 'g';
        board[row][col + 1] = 'g';
      }else if (first == 'g'){
        board[row + 1][col] = 'g'; //above
        board[row - 1][col - 1] = 'g'; //lower left
        board[row - 1][col + 1] = 'g'; //lower right
        board[row - 1][col] = 'r'; //below
        board[row][col - 1] = 'r'; //upper left
        board[row][col + 1] = 'r'; //upper right
      }
    }else {
      if(first == 'r'){
        board[initialRow + 1][initialCol] = 'r';
        board[initialRow][initialCol - 1] = 'r';
        board[initialRow][initialCol + 1] = 'r';
        board[initialRow - 1][initialCol] = 'g';
        board[initialRow + 1][initialCol - 1] = 'g';
        board[initialRow + 1][initialCol + 1] = 'g';
      }else if (first == 'g'){
        board[initialRow + 1][initialCol] = 'g'; //above
        board[initialRow][initialCol - 1] = 'g'; //lower left
        board[initialRow][initialCol + 1] = 'g'; //lower right
        board[initialRow - 1][initialCol] = 'r'; //below
        board[initialRow + 1][initialCol - 1] = 'r'; //upper left
        board[initialRow + 1][initialCol + 1] = 'r'; //upper right
      }
    }
  }

  //returns arraylist of coordinates of possible moves
  public ArrayList<Coordinates> checkPossibleMoves(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(int row, int col, char you, char[][] board);
    ArrayList<Coordinates> moves = new ArrayList<Coordinates>;

    for(int i = 0; i < enemies.size(); i++){

      if(enemies.get(i).getDirection().equals("above")){
        int x = 1;
        while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol()] == opp){
          x++;
        }
        Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() + x, enemies.get(i).getCol(), "above");
        moves.add(possiblemove);
      }

      if(enemies.get(i).getDirection().equals("lower left")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() - x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() - x, enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("lower right")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() + x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() - x, enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("below")){
        int x = 1;
        while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol()] == opp){
          x++;
        }
        Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() - x, enemies.get(i).getCol(), "below");
        moves.add(possiblemove);
      }

      if(enemies.get(i).getDirection().equals("upper left")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() + x, enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("upper right")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] == opp){
            x++;
          }
          Coordinates possiblemove = new Coordinate(enemies.get(i).getRow() + x, enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }
      }
    }
  }

  //returns arraylist of enemy coordinates
  public ArrayList<Coordinates> checkEnemyNeighbors(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> coor = new ArrayList<Coordinates>;

    if(col % 2 == 0){
      if(board[row + 1][col] != you){
        Coordinate enemy = new Coordinate(row + 1, col, "above");
        coor.add<enemy>;
      }

      if (board[row - 1][col - 1] != you){
        Coordinate enemy = new Coordinate(row - 1, col - 1, "lower left");
        coor.add<enemy>;
      }

      if(board[row - 1][col + 1] != you){
        Coordinate enemy = new Coordinate(row - 1, col + 1, "lower right");
        coor.add<enemy>;
      }

      if(board[row - 1][col] != you){
        Coordinate enemy = new Coordinate(row - 1, col, "below");
        coor.add<enemy>;
      }

      if(board[row][col - 1] != you){
        Coordinate enemy = new Coordinate(row, col - 1, "upper left");
        coor.add<enemy>;
      }

      if(board[row][col + 1] != you){
        Coordinate enemy = new Coordinate(row, col + 1, "upper right");
        coor.add<enemy>;
      }
    }else {
      if(board[row + 1][col] != you){
        Coordinate enemy = new Coordinate(row + 1, col, "above");
        coor.add<enemy>;
      }

      if (board[row][col - 1] != you){
        Coordinate enemy = new Coordinate(row - 1, col - 1, "lower left");
        coor.add<enemy>;
      }

      if(board[row][col + 1] != you){
        Coordinate enemy = new Coordinate(row - 1, col + 1, "lower right");
        coor.add<enemy>;
      }

      if(board[row - 1][col] != you){
        Coordinate enemy = new Coordinate(row - 1, col, "below");
        coor.add<enemy>;
      }

      if(board[row + 1][col - 1] != you){
        Coordinate enemy = new Coordinate(row, col - 1, "upper left");
        coor.add<enemy>;
      }

      if(board[row + 1][col + 1] != you){
        Coordinate enemy = new Coordinate(row, col + 1, "upper right");
        coor.add<enemy>;
      }
    }


    return coor;
    // if col % 2 == 0
    // board[row + 1][col] above
    // board[row - 1][col - 1] lower left
    // board[row - 1][col + 1] lower right
    // board[row - 1][col] below
    // board[row][col - 1] upper left
    // board[row][col + 1] upper right
    //else
    // board[row + 1][col] above
    // board[row][col - 1] lower left
    // board[row][col + 1] lower right
    // board[row - 1][col] below
    // board[row + 1][col - 1] upper left
    // board[row + 1][col + 1] upper right
  }

  public void setPlayers(){
    if (you == 'g'){
      opp = 'r';
    } else {
      opp = 'g';
    }
  }

  // public boolean checkNorth(int row, int col){
  //   char[][] tempBoard = new char[7][9];
  //   int tempRow;
  //   int tempCol;
  //   if(row != 6){
  //     for(int i = row + 1; i <= 6; i++){
  //         if(board[i][col] == opp){
  //           tempBoard[i+1][col] == you;
  //         }
  //     }
  //   }
  // }




}
