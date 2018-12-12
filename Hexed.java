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
  public static ArrayList<Coordinates> scanBoard(char friend, char[][] board){
    ArrayList<Coordinates> friendlies = new ArrayList<>();
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] == friend){
          Coordinates friendly = new Coordinates(i, j, "none");
          friendlies.add(friendly);
        }
      }
    }
    return friendlies;
  }

  public static void setMove(int row, int col, char player, char[][] board){
    board[row][col] = player;
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
      if (you == 'g'){
        opp = 'r';
      } else if (you == 'r'){
        opp = 'g';
      }

      //System.out.println(String.valueOf(opp));

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
        printPossibleMoves(friendlies, you, opp, board);
        System.out.println("Suggested move: ");
        recommendedMove(friendlies, you, opp, board);
        System.out.println("Please enter your move:");
        yourMoveRow = sc.nextInt();
        yourMoveCol = sc.nextInt();
        setMove(yourMoveRow, yourMoveCol, you, board);
        transformSandwiched(yourMoveRow, yourMoveCol, you, opp, board);
        //check for sandwich

        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        setMove(enemyMoveRow, enemyMoveCol, opp, board);
        transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
        //check for sandwich
      }else {
        System.out.println("What is their move?");
        enemyMoveRow = sc.nextInt();
        enemyMoveCol = sc.nextInt();
        setMove(enemyMoveRow, enemyMoveCol, opp, board);
        transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
        //check for sandwich
        //check if someone won

      }

      while(winner.equals("none")){
        printBoard(board);
        System.out.println("Here are your possible moves:");
        if(ifHexed(you, opp, board)){
          System.out.println("You are hexed");
        }else{
          printPossibleMoves(friendlies, you, opp, board);
          System.out.println("Please enter your move:");
          yourMoveRow = sc.nextInt();
          yourMoveCol = sc.nextInt();
          setMove(yourMoveRow, yourMoveCol, opp, board);
          transformSandwiched(yourMoveRow, yourMoveCol, you, opp, board);
        }
        winner = checkWinner(you, opp, board); //check if someone won
        if(!winner.equals("none")){
            break;
        }
        System.out.println("What is their move?");
        if(ifHexed(opp, you, board)){
          System.out.println("They are hexed");
        }else{
          enemyMoveRow = sc.nextInt();
          enemyMoveCol = sc.nextInt();
          setMove(enemyMoveRow, enemyMoveCol, opp, board);
          transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
        }
        winner = checkWinner(you, opp, board);//check if someone won
        if(!winner.equals("none")){
            break;
        }
        System.out.println(winner + "won!");
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
  public static ArrayList<Coordinates> checkPossibleMoves(int row, int col, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(row, col, friend, opponent, board);
    ArrayList<Coordinates> moves = new ArrayList<>();

    for(int i = 0; i < enemies.size(); i++){
      if("above".equals(enemies.get(i).getDirection())) {
        int x = 1;
        if(x + enemies.get(i).getRow() <= 6){
          while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol()] == opponent){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol(), "above");
          moves.add(possiblemove);
        }
      }

      if("lower left".equals(enemies.get(i).getDirection())){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          if(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() - x >= 0){
            while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() - x] == opponent ){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() - x, "lower left");
            moves.add(possiblemove);
          }

        }else{
          if(enemies.get(i).getRow() - x >= 0){
            while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "lower left");
            moves.add(possiblemove);
          }
        }
      }

      if("lower right".equals(enemies.get(i).getDirection())){
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          if(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() + x < 9){
            while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol() + x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() + x, "lower right");
            moves.add(possiblemove);
          }
        }else{
          if(enemies.get(i).getCol() + x < 9){
            while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "lower right");
            moves.add(possiblemove);
          }

        }
      }

        if("below".equals(enemies.get(i).getDirection())){
          int x = 1;
          if(enemies.get(i).getRow() - x >= 0){
          while(board[enemies.get(i).getRow() - x][enemies.get(i).getCol()] == opponent ){
            x++;
          }
          Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol(), "below");
          moves.add(possiblemove);
        }
      }


      if("upper left".equals(enemies.get(i).getDirection())) {
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          if(enemies.get(i).getCol() - x >= 0){
            while(board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opponent ){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "upper left");
            moves.add(possiblemove);
          }
        }else{
          if(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() - x >= 0){
            while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() - x, "upper left");
            moves.add(possiblemove);
          }
        }
      }
      if("upper right".equals(enemies.get(i).getDirection())) {
        int x = 1;
        if(enemies.get(i).getCol() % 2 == 0){
          if(enemies.get(i).getCol() + x < 9){
            while(board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "upper left");
            moves.add(possiblemove);
          }
        }else{
          if(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() < 9){
            while(board[enemies.get(i).getRow() + x][enemies.get(i).getCol() + x] == opponent){
              x++;
            }
            Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() + x, "upper left");
            moves.add(possiblemove);
          }

        }
      }
    }

    return moves;
  }

  //returns arraylist of enemy coordinates
  public static ArrayList<Coordinates> checkEnemyNeighbors(int row, int col, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> coor = new ArrayList<>();

    if(col % 2 == 0){
      if(row + 1 <= 6){
        if(board[row + 1][col] == opponent){
          Coordinates enemy = new Coordinates(row + 1, col, "above");
          coor.add(enemy);
        }
      }


      if (row - 1 >= 0 && col - 1 >= 0){
        if(board[row - 1][col - 1] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
          coor.add(enemy);
        }

      }
      if(row - 1 >= 0 && col + 1 <= 8 ){
        if(board[row - 1][col + 1] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
          coor.add(enemy);
        }
      }

      if(row - 1 >= 0){
        if(board[row - 1][col] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col, "below");
          coor.add(enemy);
        }
      }

      if(col - 1 >= 0){
        if(board[row][col - 1] == opponent){
          Coordinates enemy = new Coordinates(row, col - 1, "upper left");
          coor.add(enemy);
        }
    }
      if(col + 1 <= 8){
        if(board[row][col + 1] == opponent){
          Coordinates enemy = new Coordinates(row, col + 1, "upper right");
          coor.add(enemy);
        }
      }
    }else {
      if(row + 1 <= 6){
        if(board[row + 1][col] == opponent){
          Coordinates enemy = new Coordinates(row + 1, col, "above");
          coor.add(enemy);
        }
      }

      if(col - 1 >= 0){
        if (board[row][col - 1] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col - 1, "lower left");
          coor.add(enemy);
        }
      }

      if(col + 1 <= 6){
        if(board[row][col + 1] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col + 1, "lower right");
          coor.add(enemy);
        }
      }

      if(row - 1 >= 0){
        if(board[row - 1][col] == opponent){
          Coordinates enemy = new Coordinates(row - 1, col, "below");
          coor.add(enemy);
        }
      }


      if(row + 1 <= 6 && col - 1 >= 0){
        if(board[row + 1][col - 1] == opponent){
          Coordinates enemy = new Coordinates(row, col - 1, "upper left");
          coor.add(enemy);
        }
      }


      if(row + 1 <= 6 && col + 1 <= 8){
        if(board[row + 1][col + 1] == opponent){
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

  public static String checkWinner(char friend, char opponent, char[][] board){
    ArrayList<Coordinates> enemyMoves = new ArrayList<>();
    ArrayList<Coordinates> friendlyMoves = new ArrayList<>();
    int enemyMoveCount = 0;
    int friendlyMoveCount = 0;
    int enemyTiles = 0;
    int friendlyTiles = 0;
    for(int i = 0; i < board.length; i++){
      for(int j = 0; j < board[i].length; j++){
        if(board[i][j] == friend){
          friendlyMoves = checkPossibleMoves(i, j, friend, opponent, board);
          friendlyMoveCount += friendlyMoves.size();
          friendlyTiles++;
        }else if (board[i][j] == opponent){
          enemyMoves = checkPossibleMoves(i, j, friend, opponent, board);
          enemyMoveCount += enemyMoves.size();
          enemyTiles++;
        }


      }
    }
    if(enemyMoveCount + friendlyMoveCount != 0){
      return "none";
    }else{
      if(enemyTiles > friendlyTiles){
        return String.valueOf(friend);
      }else{
        return String.valueOf(opponent);
      }
    }
  }


  //transforms sandwiched tiles
    public static void transformSandwiched(int row, int col, char friend, char opponent, char[][] board){
      ArrayList<Coordinates> pendingTiles =  new ArrayList<>();

    if (col % 2 == 0){
      if(row + 1 <= 6){
        if (board[row + 1][col] == opponent){
          int x = 1;             //above
          while (row + x <= 6 && board[row + x][col] == opponent){
            System.out.println("above detected");
            Coordinates pendingCoordinate = new Coordinates(row + x, col, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if(row - 1 >= 0){
        if (board[row - 1][col] == opponent) {
          int x = 1;         //below
          while (row - x >= 0 && board[row - x][col] == opponent){
            System.out.println("below detected");
            Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if (row - 1 >= 0 && col - 1 >= 0){
        if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] == opponent) {
          int x = 1;     // lowerleft
          while (row - x >= 0 && col - x >= 0 && board[row - x][col - x] == opponent){
            System.out.println("lowerleft detected");
            Coordinates pendingCoordinate = new Coordinates(row - x, col - x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if(row - 1 >= 0 && col + 1 <= 8){
        if (row - 1 >= 0 && col + 1 <= 8 && board[row - 1][col + 1] == opponent){
          int x = 1;     // lowerright
          while (row - x >= 0 && col + x <= 8 && board[row - x][col + x] == opponent){
            System.out.println("lowerright detected");
            Coordinates pendingCoordinate = new Coordinates(row - x, col + x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if(col - 1 >= 0){
        if (col - 1 >= 0 && board[row][col - 1] == opponent){
          int x = 1;     // upperleft
          while (col - x >= 0 && board[row][col - x] == opponent){
            System.out.println("upperleft detected");
            Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if(col + 1 <= 8){
        if (board[row][col + 1] == opponent){
          int x = 1;     // upperright
          while (col + x <= 8 && board[row][col + x] == opponent){
            System.out.println("Upper right detected");
            Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }
    } else {

      if (row + 1 <= 6){
        if (board[row + 1][col] == opponent){
          int x = 1;             //above
          if(row + x <= 6){
            while (row + x <= 6 && board[row + x][col] == opponent){
              System.out.println("above detected");
              Coordinates pendingCoordinate = new Coordinates(row + x, col, "none");
              pendingTiles.add(pendingCoordinate);
              x++;
            }
          }

        }
      }

      if(row - 1 >= 0){
        if (board[row - 1][col] == opponent){
          int x = 1;
          if(row - x >= 0){         //below
          while (row - x >= 0 && board[row - x][col] == opponent){
            System.out.println("below detected");
            Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
          }
        }
    }

      if(col - 1 >= 0){
        if (board[row][col - 1] == opponent){
          int x = 1;         //lowerleft
          while (col - x >= 0 && board[row][col - x] == opponent){
            System.out.println("lowerleft detected");
            Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

      if(col + 1 <= 8){
        if (board[row][col + 1] == opponent){
          int x = 1;         //lowerright
          while (col + x <= 8 && board[row][col + x] == opponent){
            System.out.println("lowerright detected");
            Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }


      if(row + 1 <= 6 && col - 1 >= 0){
        if(board[row + 1][col - 1] == opponent){
          int x = 1;         //upperleft
          while (row + x <= 6 && col - x >= 0 && board[row + x][col - x] == opponent){
            System.out.println("upperleft detected");
            Coordinates pendingCoordinate = new Coordinates(row + x, col - x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }


      if(row + 1 <= 6 && col + 1 <= 8){
        if(board[row + 1][col + 1] == opponent){
          int x = 1;         //upperright
          while (row + x <= 6 && col + x <= 8 && board[row + x][col + x] == opponent){
            System.out.println("upperright detected");
            Coordinates pendingCoordinate = new Coordinates(row + x, col + x, "none");
            pendingTiles.add(pendingCoordinate);
            x++;
          }
        }
      }

    }

    for (int i = 0; i < pendingTiles.size(); i++){
      board[pendingTiles.get(i).getRow()][pendingTiles.get(i).getCol()] = friend;
      System.out.println(pendingTiles.get(i).getRow() + " " + pendingTiles.get(i).getCol());
    }


  }

public static boolean ifHexed(char friend, char opponent, char[][] board){
  ArrayList<Coordinates> moves = new ArrayList<>();
  int numberOfMoves = 0;

  for(int i = 0;i < 7; i++){
    for(int j = 0; j < 7; i++){
      if(i <= 6 && j <= 6 && i >= 0 && j >= 0){
        if(board[i][j] == friend){
          moves = getPossibleMoves(i, j, friend, opponent, board);
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

  public static void printPossibleMoves(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> possibleMoves = getPossibleMoves(friendlies, friend, opponent, board);
    for(int i = 0; i < possibleMoves.size(); i++){
    System.out.println("Row:" + possibleMoves.get(i).getRow() + " Col: " + possibleMoves.get(i).getCol());
    }
  }

  public static ArrayList<Coordinates> getPossibleMoves(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> possibleMoves = new ArrayList<>();
    ArrayList<Coordinates> buffer = new ArrayList<>();
    for(int i = 0; i < friendlies.size(); i++){
      buffer = checkPossibleMoves(friendlies.get(i).getRow(), friendlies.get(i).getCol(), friend, opponent, board);
      for(int j = 0; j < buffer.size(); j++){
        Coordinate move = new Coordinate(buffer.get(j).getRow(), buffer.get(j).getCol(), "none");
        possibleMoves.add(move);
      }
    }
    return possibleMoves;
  }
  //this method will check all possible moves and returns the coordinates of the move that will transform
  //the least amount of tiles
  public static Coordinates recommendedMove(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> shuffledList = new ArrayList<>();
    Coordinates recomended;
    shuffledList = getPossibleMoves(friendlies, friend, opponent, board);
    Collections.shuffle(shuffledList);
    recomended = shuffledList.get(0);
    System.out.println("Row: " + recomended.getRow() + "   Column: " + recomended.getCol());
  }
}
