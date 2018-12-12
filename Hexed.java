import java.util.*;
import java.lang.*;

public class Hexed{



  //initializes the board; puts the starting circle
  public static void initializeBoard(int row, int col, char first, char[][] board){
    if(col % 2 == 0){
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
        board[row + 1][col] = 'r';
        board[row][col - 1] = 'r';
        board[row][col + 1] = 'r';
        board[row - 1][col] = 'g';
        board[row + 1][col - 1] = 'g';
        board[row + 1][col + 1] = 'g';
      }else if (first == 'g'){
        board[row + 1][col] = 'g'; //above
        board[row][col - 1] = 'g'; //lower left
        board[row][col + 1] = 'g'; //lower right
        board[row - 1][col] = 'r'; //below
        board[row + 1][col - 1] = 'r'; //upper left
        board[row + 1][col + 1] = 'r'; //upper right
      }
    }
  }
  // this method prints the initialized board
  public static void printBoard(char[][] board){
    for (int i = board.length - 1; i >= 0; i--){
      for (int j = 0; j < board[i].length; j++){
        System.out.print("[" + board[i][j] + "]");
      }
      System.out.println();
    }
  }
  //returns list of friendly tiles
  public static ArrayList<Coordinates> scanBoard(char you, char[][] board){
    ArrayList<Coordinates> friendlies = new ArrayList<>();
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] == you){
          Coordinates friendly = new Coordinates(i, j, "none");
          friendlies.add(friendly);
        }
      }
    }
    return friendlies;
  }

  public static void setMove(int row, int col, char opp, char[][] board){
    board[row][col] = opp;
  }


  public static void main(String[] args){
      ArrayList<Coordinates> possibleMoves = new ArrayList<>();
      ArrayList<Coordinates> friendlies = new ArrayList<>();
      //ArrayList<Coordinates> bufferPM = new ArrayList<>();
      boolean hexChecker;
      char[][] board = new char[7][9];
      char first;
      char you;
      char opp = ' ';
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
      //setPlayers(you, opp);
      if (you == 'g'){
        opp = 'r';
      } else if (you == 'r'){
        opp = 'g';
      }

      System.out.println(String.valueOf(opp));

      System.out.print("Who goes first?");
      first = sc.next().charAt(0);



      System.out.print("Enter starting center: ");
      initialRow = sc.nextInt();
      initialCol = sc.nextInt();

      initializeBoard(initialRow, initialCol, first, board);
      printBoard(board);

      friendlies = scanBoard(you, board);
      if(first == you){
        System.out.println("Here are your possible moves:");
        getPossibleMoves(friendlies, you, board);
        System.out.println("Please enter your move:");
        yourMoveRow = sc.nextInt();
        yourMoveCol = sc.nextInt();
        setMove(yourMoveRow, yourMoveCol, you, board);
        transformSandwiched(yourMoveRow, yourMoveCol, you, board);
        //check for sandwich

        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        setMove(enemyMoveRow, enemyMoveCol, opp, board);
        transformSandwiched(enemyMoveRow, enemyMoveCol, opp, board);
        //check for sandwich
      }else {
        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        setMove(enemyMoveRow, enemyMoveCol, opp, board);
        transformSandwiched(enemyMoveRow, enemyMoveCol, opp, board);
        //check for sandwich
        //check if someone won

      }

      while(winner.equals("none")){
        printBoard(board);
        System.out.println("Here are your possible moves:");
        // if(ifHexed(you, board) == true){
        //   System.out.println("You are hexed");
        // }else{
        //
        // }
        getPossibleMoves(friendlies, you, board);
        System.out.println("Please enter your move:");
        yourMoveRow = sc.nextInt();
        yourMoveCol = sc.nextInt();
        setMove(yourMoveRow, yourMoveCol, opp, board);
        transformSandwiched(yourMoveRow, yourMoveCol, you, board);
        //check for sandwich
        winner = checkWinner(you, opp, board); //check if someone won
        if(!winner.equals("none")){
            break;
        }
        // if(ifHexed(opp, board) == true){
        //   System.out.println("They are hexed");
        // }else{
        //
        // }
        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        setMove(enemyMoveRow, enemyMoveCol, opp, board);
        transformSandwiched(enemyMoveRow, enemyMoveCol, opp, board);
        //check for sandwich
        winner = checkWinner(you, opp, board);//check if someone won
        if(!winner.equals("none")){
            break;
        }
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
  public static ArrayList<Coordinates> checkPossibleMoves(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(row, col, you, board);
    ArrayList<Coordinates> moves = new ArrayList<>();

    for(int i = 0; i < enemies.size(); i++){

      if("above".equals(enemies.get(i).getDirection())) {
        int x = 1;
        while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol()] != you && x + enemies.get(i).getRow() < 7){
          x++;
        }
        Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol(), "above");
        moves.add(possiblemove);
      }

      if("lower left".equals(enemies.get(i).getDirection())){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() - x] != you && enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() - x >= 0){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] != you  && enemies.get(i).getRow() - x >= 0){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "lower left");
          moves.add(possiblemove);
        }
      }

      if("lower right".equals(enemies.get(i).getDirection())){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() + x] != you && enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() + x < 9){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] != you && enemies.get(i).getCol() + x < 9){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "lower right");
          moves.add(possiblemove);
        }
      }

      if("below".equals(enemies.get(i).getDirection())){
        int x = 1;
        while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol()] != you && enemies.get(i).getRow() - x >= 0){
          x++;
        }
        Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol(), "below");
        moves.add(possiblemove);
      }

      if("upper left".equals(enemies.get(i).getDirection())) {
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] != you && enemies.get(i).getCol() - x >= 0){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] != you && enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() - x >= 0){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() - x, "upper left");
          moves.add(possiblemove);
        }
      }

      if("upper right".equals(enemies.get(i).getDirection())) {
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] != you && enemies.get(i).getCol() + x < 9){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }else{
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() + x] != you && enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() < 9){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() + x, "upper left");
          moves.add(possiblemove);
        }
      }
    }

    return moves;
  }

  //returns arraylist of enemy coordinates
  public static ArrayList<Coordinates> checkEnemyNeighbors(int row, int col, char you, char[][] board){
    ArrayList<Coordinates> coor = new ArrayList<>();

    if(col % 2 == 0){
      if(row + 1 <= 6){
        if(board[row + 1][col] != you){
          Coordinates enemy = new Coordinates(row + 1, col, "above");
          coor.add(enemy);
        }
      }


      if (row - 1 >= 0 && col - 1 >= 0){
        if(board[row - 1][col - 1] != you){
          Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
          coor.add(enemy);
        }

      }
      if(row - 1 >= 0 && col + 1 <= 8 ){
        if(board[row - 1][col + 1] != you){
          Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
          coor.add(enemy);
        }
      }

      if(row - 1 >= 0){
        if(board[row - 1][col] != you){
          Coordinates enemy = new Coordinates(row - 1, col, "below");
          coor.add(enemy);
        }
      }

      if(col - 1 >= 0){
        if(board[row][col - 1] != you){
          Coordinates enemy = new Coordinates(row, col - 1, "upper left");
          coor.add(enemy);
        }
    }
      if(col + 1 <= 8){
        if(board[row][col + 1] != you){
          Coordinates enemy = new Coordinates(row, col + 1, "upper right");
          coor.add(enemy);
        }
      }
    }else {
      if(row + 1 <= 6){
        if(board[row + 1][col] != you){
          Coordinates enemy = new Coordinates(row + 1, col, "above");
          coor.add(enemy);
        }
      }

      if(col - 1 >= 0){
        if (board[row][col - 1] != you){
          Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
          coor.add(enemy);
        }
      }

      if(col + 1 <= 6){
        if(board[row][col + 1] != you){
          Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
          coor.add(enemy);
        }
      }

      if(row - 1 >= 0){
        if(board[row - 1][col] != you){
          Coordinates enemy = new Coordinates(row - 1, col, "below");
          coor.add(enemy);
        }
      }


      if(row + 1 <= 6 && col - 1 >= 0){
        if(board[row + 1][col - 1] != you){
          Coordinates enemy = new Coordinates(row, col - 1, "upper left");
          coor.add(enemy);
        }
      }


      if(row + 1 <= 6 && col + 1 <= 8){
        if(board[row + 1][col + 1] != you){
          Coordinates enemy = new Coordinates(row, col + 1, "upper right");
          coor.add(enemy);
        }
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

  public static String checkWinner(char you, char opp, char[][] board){
    ArrayList<Coordinates> enemyMoves = new ArrayList<>();
    ArrayList<Coordinates> friendlyMoves = new ArrayList<>();
    int enemyMoveCount = 0;
    int friendlyMoveCount = 0;
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


      }
    }
    if(enemyMoveCount + friendlyMoveCount != 0){
      return "none";
    }else{
      if(enemyTiles > friendlyTiles){
        return String.valueOf(you);
      }else{
        return String.valueOf(opp);
      }
    }
  }

//transforms sandwiched tiles
  public static void transformSandwiched(int row, int col, char player, char[][] board){
    ArrayList<Coordinates> pendingTiles =  new ArrayList<>();

  if (col % 2 == 0){
    if(row + 1 <= 6){
      if (board[row + 1][col] != player){
        int x = 1;             //above
        while (row + x <= 6 && board[row + x][col] != player){
          Coordinates pendingCoordinate = new Coordinates(row + x, col, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if(row - 1 >= 0){
      if (board[row - 1][col] != player) {
        int x = 1;         //below
        while (row - x >= 0 && board[row - x][col] != player){
          Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if (row - 1 >= 0 && col - 1 >= 0){
      if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] != player) {
        int x = 1;     // lowerleft
        while (board[row - x][col - x] != player){
          Coordinates pendingCoordinate = new Coordinates(row - x, col - x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if(row - 1 >= 0 && col + 1 <= 8){
      if (row - 1 >= 0 && col + 1 <= 8 && board[row - 1][col + 1] != player){
        int x = 1;     // lowerright
        while (board[row - x][col + x] != player){
          Coordinates pendingCoordinate = new Coordinates(row - x, col + x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if(col - 1 >= 0){
      if (col - 1 >= 0 && board[row][col - 1] != player){
        int x = 1;     // upperleft
        while (board[row][col - x] != player){
          Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if(col + 1 <= 8){
      if (board[row][col + 1] != player){
        int x = 1;     // upperleft
        while (col + x <= 8 && board[row][col + x] != player){
          Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }
  } else {

    if (row + 1 <= 6){
      if (board[row + 1][col] != player){
        int x = 1;             //above
        if(row + x <= 6){
          while (row + x <= 6 && board[row + x][col] != player){
            Coordinates pendingCoordinate = new Coordinates(row + x, col, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }

      }
    }

    if(row - 1 >= 0){
      if (board[row - 1][col] != player){
        int x = 1;
        if(row - x >= 0){         //below
        while (row - x >= 0 && board[row - x][col] != player){
          Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
        }
      }
  }

    if(col - 1 >= 0){
      if (board[row][col - 1] != player){
        int x = 1;         //lowerleft
        while (col - x >= 0 && board[row][col - x] != player){
          Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

    if(col + 1 <= 8){
      if (board[row][col + 1] != player){
        int x = 1;         //lowerright
        while (col + x <= 8 && board[row][col + x] != player){
          Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }


    if(row + 1 <= 6 && col - 1 >= 0){
      if(board[row + 1][col - 1] != player){
        int x = 1;         //upperleft
        while (row + x <= 6 && col - x >= 0 && board[row + x][col - x] != player){
          Coordinates pendingCoordinate = new Coordinates(row + x, col - x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }


    if(row + 1 <= 6 && col + 1 <= 8){
      if(board[row + 1][col + 1] != player){
        int x = 1;         //upperright
        while (row + x <= 6 && col + x <= 8 && board[row + x][col + x] != player){
          Coordinates pendingCoordinate = new Coordinates(row + x, col + x, "none");
          pendingTiles.add(pendingCoordinate);
          x++;
        }
      }
    }

  }

  for (int i = 0; i < pendingTiles.size(); i++){
    board[pendingTiles.get(i).getRow()][pendingTiles.get(i).getCol()] = player;
  }


}

public static boolean ifHexed(char player, char[][] board){
  ArrayList<Coordinates> moves = new ArrayList<>();
  int numberOfMoves = 0;

  for(int i = 0;i < 7; i++){
    for(int j = 0; j < 7; i++){
      if(i <= 6 && j <= 6 && i >= 0 && j >= 0){
        if(board[i][j] == player){
          moves = checkPossibleMoves(i, j, player, board);
          numberOfMoves += moves.size();
        }
      }

    }
  }

  if(numberOfMoves == 0){
    return true;
  }else{
    return false;
  }
}

  public static void setPlayers(char you, char opp){
    if (you == 'g'){
      opp = 'r';
    } else if (you == 'r'){
      opp = 'g';
    }
  }
  public static void getPossibleMoves(ArrayList<Coordinates> friendlies, char player, char[][] board){
    ArrayList<Coordinates> possibleMoves = new ArrayList<>();
    for(int i = 0; i < friendlies.size(); i++){
            possibleMoves = checkPossibleMoves(friendlies.get(i).getRow(), friendlies.get(i).getCol(), player, board);
            System.out.println(possibleMoves.size());
            if(!possibleMoves.isEmpty()){
              System.out.println("Row:" + possibleMoves.get(i).getRow() + " Col: " + possibleMoves.get(i).getCol());
            }

          }
  }
  //this method will check all possible moves and returns the coordinates of the move that will transform
  //the least amount of tiles
  // public static ArrayList<Coordinates> recommendedMove(){
  //   ArrayList<Coordinates> recomended =
  // }
}
