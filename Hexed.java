import java.util.*;

public class Hexed{

class Board{
  char[][] board = new char[7][9];
  int initialRow = 0;
  int initialCol = 0;

  public Board(){
    this.initialRow = initialRow;
    this.initialCol = initialCol;
    this.board = board;
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

  public void printBoard(){
    for (int i = 0; i < board.length; i++){
      for (int j = 0; j < board[i].length; j++){
        System.out.print("[" + board[i][j] + "]");
      }
      System.out.println();
    }
  }
  //returns list of friendly tiles
  public ArrayList<Coordinates> scanBoard(char you){
    ArrayList<Coordinates> friendlies = new ArrayList<>();
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] == you){
          Coordinates friendly = new Coordinate(i, j, "none");
          friendlies.add(friendly);
        }
      }
    }
  }

  public void setMove(int row, int col, char opp){
    board[row][col] == opp;
  }

}


  public static void main(String[] args){
      ArrayList<Coordinates> possibleMoves = new ArrayList<>();
      ArrayList<Coordinates> friendlies = new ArrayList<>();
      //ArrayList<Coordinates> bufferPM = new ArrayList<>();
      Board board;
      char first;
      char you;
      char opp;
      int initialRow;
      int initialCol;
      int enemyMoveRow;
      int enemyMoveCol;
      int yourMoveRow;
      int yourMoveCol;
      String winner = "none";
      String firstPlayer;
      Scanner sc = new Scanner(System.in);

      System.out.print("What's your color?");
      you = sc.next().charAt(0);
      setPlayers(you, opp);

      System.out.print("Who goes first?");
      first = sc.next().charAt(0);



      System.out.print("Enter starting center: ");
      initialRow = sc.nextInt();
      initialCol = sc.nextInt();

      board.initializeBoard(initialRow, initialCol, first);
      board.printBoard();

      friendlies = scanBoard(you);
      if(first == you){
        System.out.println("Here are your possible moves:")
        getPossibleMoves(friendlies, you, opp, board);
        System.out.println("Please enter your move:");
        yourMoveRow = sc.nextInt();
        yourMoveCol = sc.nextInt();
        board.setMove(yourMoveRow, yourMoveCol);
        //check for sandwich

        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        board.setMove(enemyMoveRow, enemyMoveCol);
        //check for sandwich
      }else {
        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        board.setMove(enemyMoveRow, enemyMoveCol);
        //check for sandwich
        //check if someone won

      }

      while(winner.equals("none")){
        System.out.println("Here are your possible moves:")
        getPossibleMoves(friendlies, you, opp, board);
        System.out.println("Please enter your move:");
        yourMoveRow = sc.nextInt();
        yourMoveCol = sc.nextInt();
        board.setMove(yourMoveRow, yourMoveCol);
        //check for sandwich
        winner = checkWinner(you, opp, board); //check if someone won
        if(!winner.equals("none")){
            break;
        }
        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        board.setMove(enemyMoveRow, enemyMoveCol);
        //check for sandwich
        winner = checkWinner(you, opp, board);//check if someone won
      }

      // todo:
      // -terminating conditions; winner and loser conditions/
      // -hexed condition
      // -actual gameplay
      //   -enemy move input /
      //   -looping code that loops every after move has been made/
      //   -sandwiching / transforming method
      // -output; the possible moves and the recommended moves /
      // -board printing /
  }


  //returns arraylist of coordinates of possible moves
  public ArrayList<Coordinates> checkPossibleMoves(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(row, col, you, board);
    ArrayList<Coordinates> moves = new ArrayList<>();

    for(int i = 0; i < enemies.size(); i++){

      if(enemies.get(i).getDirection().equals("above")){
        int x = 1;
        while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol()] != you){
          x++;
        }
        Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol(), "above");
        moves.add(possiblemove);
      }

      if(enemies.get(i).getDirection().equals("lower left")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() - x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("lower right")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() + x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("below")){
        int x = 1;
        while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol()] != you){
          x++;
        }
        Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol(), "below");
        moves.add(possiblemove);
      }

      if(enemies.get(i).getDirection().equals("upper left")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }
      }

      if(enemies.get(i).getDirection().equals("upper right")){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] != you){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }
      }
    }
  }

  //returns arraylist of enemy coordinates
  public ArrayList<Coordinates> checkEnemyNeighbors(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> coor = new ArrayList<>();

    if(col % 2 == 0){
      if(board[row + 1][col] != you){
        Coordinates enemy = new Coordinates(row + 1, col, "above");
        coor.add(enemy);
      }

      if (board[row - 1][col - 1] != you){
        Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
        coor.add(enemy);
      }

      if(board[row - 1][col + 1] != you){
        Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
        coor.add(enemy);
      }

      if(board[row - 1][col] != you){
        Coordinates enemy = new Coordinates(row - 1, col, "below");
        coor.add(enemy);
      }

      if(board[row][col - 1] != you){
        Coordinates enemy = new Coordinates(row, col - 1, "upper left");
        coor.add(enemy);
      }

      if(board[row][col + 1] != you){
        Coordinates enemy = new Coordinates(row, col + 1, "upper right");
        coor.add(enemy);
      }
    }else {
      if(board[row + 1][col] != you){
        Coordinates enemy = new Coordinates(row + 1, col, "above");
        coor.add(enemy);
      }

      if (board[row][col - 1] != you){
        Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
        coor.add(enemy);
      }

      if(board[row][col + 1] != you){
        Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
        coor.add(enemy);
      }

      if(board[row - 1][col] != you){
        Coordinates enemy = new Coordinates(row - 1, col, "below");
        coor.add(enemy);
      }

      if(board[row + 1][col - 1] != you){
        Coordinates enemy = new Coordinates(row, col - 1, "upper left");
        coor.add(enemy);
      }

      if(board[row + 1][col + 1] != you){
        Coordinates enemy = new Coordinates(row, col + 1, "upper right");
        coor.add(enemy);
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

  public String checkWinner(char you, char opp, Board board){
    ArrayList<Coordinates> enemyMoves = new ArrayList<>();
    ArrayList<Coordinates> friendlyMoves = new ArrayList<>();
    int enemyMoveCount;
    int friendlyMoveCount;
    int enemyTiles = 0;
    int friendlyTiles = 0;
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] == you){
          friendlyMoves = checkPossibleMoves(i, j, you, board);
          friendlyMoveCount += friendlyMoves.size();
          friendlyTiles++;
        }else if (board[i][j] == opp){
          enemyMoves = checkPossibleMoves(i, j, opp, board);
          enemyMoveCount += enemyMoves.size();
          enemyTiles++;
        }

        if(enemyMoveCount + friendlyMoveCount != 0){
          return "none";
        }else{
          if(enemyTiles > friendlyTiles){
            return you.charAt(0);
          }else{
            return opp.charAt(0);
          }
        }
      }
    }
  }

//transforms sandwiched tiles
  public void transformSandwiched(int row, int col, char player){
    ArrayList<Coordinates> pendingTiles = ArrayList<>();
    int x = 1;
  if (col % 2 == 0){
    if (board[row + x][col] != player){                //above
      while (board[row + x][col] != player){
        Coordinates pendingCoordinate = new Coordinates(row, col, "none");
        pendingTiles.add(pendingCoordinate);
        x++
      }
    }

    if (board[row - x][col] != player) {             //below
      while (board[row - x][col] != player){
        Coordinates pendingCoordinate = new Coordinates(row, col, "none");
        pendingTiles.add(pendingCoordinate);
        x++
      }
    }

    if (board[row - x][col - x] != player) {
      while (board[row - x][col - x])
    }
  } else {

  }


}

  public void setPlayers(char you, char opp){
    if (you == 'g'){
      opp = 'r';
    } else {
      opp = 'g';
    }
  }
  public void getPossibleMoves(ArrayList<Coordinates> friendlies, char you, char opp, Board board){
    ArrayList<Coordinates> possibleMoves = new ArrayList<>();
    for(int i = 0; i < friendlies.size(); i++){
            possibleMoves = checkPossibleMoves(friendlies.get(i).getRow, friendlies.get(i).getCol, you, opp, board);
            System.out.println("Row:" + possibleMoves.get(i).getRow + " Col: " + possibleMoves.get(i).getCol);
          }
  }
}
