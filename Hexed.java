import java.util.*;
import java.lang.*;

public class Hexed{
  //made by Noriel Navarro
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

  //made by Noriel Navarro
  // this method prints the initialized board
  public static void printBoard(char[][] board){
      for (int i = board.length - 1; i >= 0; i--){
          for (int j = 0; j < board[i].length; j++){
              System.out.print("[" + board[i][j] + "]");
          }
          System.out.println();
      }
  }

  //made by Noriel Navarro
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

  //made by Patrick Collado
  //sets the index in the board into the player's color
  public static void setMove(int row, int col, char player, char[][] board){
      board[row][col] = player;
  }


  public static ArrayList<Coordinates> checkPossibleMoves(int row, int col, char friend, char opponent, char[][] board){
    ArrayList<Coordinates> enemies = checkEnemyNeighbors(row, col, friend, opponent, board);
    ArrayList<Coordinates> moves = new ArrayList<>();
    int enemyRow = 0;
    int enemyCol = 0;
    int nextRow = 0;
    int nextCol = 0;

    for(int i = 0; i < enemies.size(); i++){
      String direction = enemies.get(i).getDirection();
      enemyRow = enemies.get(i).getRow();
      enemyCol = enemies.get(i).getCol();

      switch(direction){
        case "above":
          int x = 0;
          while(checkAbove(enemyRow + x, enemyCol, opponent, board)){
            x++;
          }
          if(enemyRow + x < 7){
            Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol, "none");
            moves.add(possiblemove);
          }
          break;
        case "below":
          int x = 0;
          while(checkBelow(enemyRow - x, enemyCol, opponent, board)){
            x++;
          }
          if(enemyRow - x >= 0){
            Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol, "none");
            moves.add(possiblemove);
          }
          break;
        case "upper left":
          int x = 0;
          int y = 0;
          do{
            if((enemyCol - y) % 2 == 0){
              y++;
            }else{
              y++;
              x++;
            }
          }while(checkUpperLeft(enemyRow + x, enemyCol - y, opponent, board));
          if(enemyRow + x < 7 && enemyCol - y >= 0){
            Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol - y, "none");
            moves.add(possiblemove);
          }
          break;
        case "upper right":
          int x = 0;
          int y = 0;
          do{
            if((enemyCol - y) % 2 == 0){
              y++;
            }else{
              y++;
              x++;
            }
          }while(checkUpperRight(enemyRow + x, enemyCol + y, opponent, board));
          if(enemyRow + x < 7 && enemyCol + y < 7){
            Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol + y, "none");
            moves.add(possiblemove);
          }
          break;
        case "lower left":
          int x = 0;
          int y = 0;
          do{
            if((enemyCol - y) % 2 == 0){
              x++;
              y++;
            }else{
              x++;
            }
          }while(checkLowerLeft(enemyRow - x, enemyCol - y, opponent, board));
          if(enemyRow - x >= 0 && enemyCol - y >= 0){
            Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol - y, "none");
            moves.add(possiblemove);
          }
          break;
        case "lower right":
        int x = 0;
        int y = 0;
        do{
          if((enemyCol - y) % 2 == 0){
            x++;
            y++;
          }else{
            x++;
          }
        }while(checkLowerRight(enemyRow + x, enemyCol - y, opponent, board));
        if(enemyRow - x < 0){
          Coordinates possiblemove = new Coordinates(enemyRow + x, enemyCol - y, "none");
          moves.add(possiblemove);
        }
        break;
      }
    }
    return moves;

  }

  //checks if the tile above is an enemy
  public static boolean checkAbove(int row, int col, char opponent, char[][] board){
      if(board[row + 1][col] == opponent){
        return true;
      }else{
        return false;
      }
  }

  public static boolean checkBelow(int row, int col, char opponent, char[][] board){
      if(board[row - 1][col] == opponent){
        return true;
      }else{
        return false;
      }
  }

  public static boolean checkUpperLeft(int row, int col, char opponent, char[][] board){
      if(col % 2 == 0){
        if(board[row][col - 1] == opponent){
          return true
        }else{
          return false
        }
      }else{
        if(board[row + 1][col - 1] == opponent){
          return true
        }else{
          return false
        }
      }
  }

  public static boolean checkUpperRight(int row, int col, char opponent, char[][] board){
      if(col % 2 == 0){
        if(board[row][col + 1] == opponent){
          return true
        }else{
          return false
        }
      }else{
        if(board[row + 1][col + 1] == opponent){
          return true
        }else{
          return false
        }
      }
  }

  public static boolean checkLowerLeft(int row, int col, char opponent, char[][] board){
      if(col % 2 == 0){
        if(board[row - 1][col - 1] == opponent){
          return true
        }else{
          return false
        }
      }else{
        if(board[row][col - 1] == opponent){
          return true
        }else{
          return false
        }
      }
  }

  public static boolean checkLowerRight(int row, int col, char opponent, char[][] board){
      if(col % 2 == 0){
        if(board[row - 1][col + 1] == opponent){
          return true
        }else{
          return false
        }
      }else{
        if(board[row][col + 1] == opponent){
          return true
        }else{
          return false
        }
      }
  }


  // //made by Patrick Collado
  // //returns arraylist of coordinates of possible moves
  // public static ArrayList<Coordinates> checkPossibleMoves(int row, int col, char friend, char opponent, char[][] board){
  //     ArrayList<Coordinates> enemies = checkEnemyNeighbors(row, col, friend, opponent, board);
  //     ArrayList<Coordinates> moves = new ArrayList<>();
  //
  //     for(int i = 0; i < enemies.size(); i++){
  //         if("above".equals(enemies.get(i).getDirection())) {
  //             int x = 1;
  //             if(x + enemies.get(i).getRow() <= 6){
  //                 while(x + enemies.get(i).getRow() <= 6 && board[enemies.get(i).getRow() + x][enemies.get(i).getCol()] == opponent){
  //                     x++;
  //                 }
  //                 Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol(), "above");
  //                 moves.add(possiblemove);
  //             }
  //         }
  //
  //         if("lower left".equals(enemies.get(i).getDirection())){
  //             int x = 1;
  //             if(enemies.get(i).getCol() % 2 == 0){
  //                 if(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() - x >= 0){
  //                     while(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() - x >= 0 && board[enemies.get(i).getRow() - x][enemies.get(i).getCol() - x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() - x, "lower left");
  //                     moves.add(possiblemove);
  //                 }
  //
  //             }else{
  //                 if(enemies.get(i).getRow() - x >= 0){
  //                     while(enemies.get(i).getRow() - x >= 0 && board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "lower left");
  //                     moves.add(possiblemove);
  //                 }
  //             }
  //         }
  //
  //         if("lower right".equals(enemies.get(i).getDirection())){
  //             int x = 1;
  //             if(enemies.get(i).getCol() % 2 == 0){
  //                 if(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() + x < 9){
  //                     while(enemies.get(i).getRow() - x >= 0 && enemies.get(i).getCol() + x < 9 && board[enemies.get(i).getRow() - x][enemies.get(i).getCol() + x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol() + x, "lower right");
  //                     moves.add(possiblemove);
  //                 }
  //             }else{
  //                 if(enemies.get(i).getCol() + x < 9){
  //                     while(enemies.get(i).getCol() + x < 9 && board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "lower right");
  //                     moves.add(possiblemove);
  //                 }
  //
  //             }
  //         }
  //
  //         if("below".equals(enemies.get(i).getDirection())){
  //             int x = 1;
  //             if(enemies.get(i).getRow() - x >= 0){
  //                 while(enemies.get(i).getRow() - x >= 0 && board[enemies.get(i).getRow() - x][enemies.get(i).getCol()] == opponent ){
  //                     x++;
  //                 }
  //                 Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() - x, enemies.get(i).getCol(), "below");
  //                 moves.add(possiblemove);
  //             }
  //         }
  //
  //
  //         if("upper left".equals(enemies.get(i).getDirection())) {
  //             int x = 1;
  //             if(enemies.get(i).getCol() % 2 == 0){
  //                 if(enemies.get(i).getCol() - x >= 0){
  //                     while(enemies.get(i).getCol() - x >= 0 && board[enemies.get(i).getRow()][enemies.get(i).getCol() - x] == opponent ){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() - x, "upper left");
  //                     moves.add(possiblemove);
  //                 }
  //             }else{
  //                 if(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() - x >= 0){
  //                     while(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() - x >= 0 && board[enemies.get(i).getRow() + x][enemies.get(i).getCol() - x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() - x, "upper left");
  //                     moves.add(possiblemove);
  //                 }
  //             }
  //         }
  //         if("upper right".equals(enemies.get(i).getDirection())) {
  //             int x = 1;
  //             if(enemies.get(i).getCol() % 2 == 0){
  //                 if(enemies.get(i).getCol() + x < 9){
  //                     while(enemies.get(i).getCol() + x < 9 && board[enemies.get(i).getRow()][enemies.get(i).getCol() + x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow(), enemies.get(i).getCol() + x, "upper left");
  //                     moves.add(possiblemove);
  //                 }
  //             }else{
  //                 if(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() < 9){
  //                     while(enemies.get(i).getRow() + x < 7 && enemies.get(i).getCol() < 9 && board[enemies.get(i).getRow() + x][enemies.get(i).getCol() + x] == opponent){
  //                         x++;
  //                     }
  //                     Coordinates possiblemove = new Coordinates(enemies.get(i).getRow() + x, enemies.get(i).getCol() + x, "upper left");
  //                     moves.add(possiblemove);
  //                 }
  //
  //             }
  //         }
  //     }
  //
  //     return moves;
  // }

  //made by Patrick Collado
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
                  Coordinates enemy = new Coordinates(row, col - 1, "lower left");
                  coor.add(enemy);
              }
          }

          if(col + 1 <= 6){
              if(board[row][col + 1] == opponent){
                  Coordinates enemy = new Coordinates(row, col + 1, "lower right");
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
                  Coordinates enemy = new Coordinates(row + 1, col - 1, "upper left");
                  coor.add(enemy);
              }
          }


          if(row + 1 <= 6 && col + 1 <= 8){
              if(board[row + 1][col + 1] == opponent){
                  Coordinates enemy = new Coordinates(row + 1, col + 1, "upper right");
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
  //made by Patrick Collado
  //Checks if there is a winner and if there is, who.
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

  //made by Joshua Bantayan
  //transforms sandwiched tiles
  public static void transformSandwiched(int row, int col, char friend, char opponent, char[][] board){
      ArrayList<Coordinates> pendingTiles =  new ArrayList<>();

      if (col % 2 == 0){
          if(row + 1 <= 6){
              if (board[row + 1][col] == opponent){
                  int x = 1;
                  while (row + x <= 6 && board[row + x][col] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row + x, col, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if(row - 1 >= 0){
              if (board[row - 1][col] == opponent) {
                  int x = 1;
                  while (row - x >= 0 && board[row - x][col] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if (row - 1 >= 0 && col - 1 >= 0){
              if (row - 1 >= 0 && col - 1 >= 0 && board[row - 1][col - 1] == opponent) {
                  int x = 1;
                  while (row - x >= 0 && col - x >= 0 && board[row - x][col - x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row - x, col - x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if(row - 1 >= 0 && col + 1 <= 8){
              if (row - 1 >= 0 && col + 1 <= 8 && board[row - 1][col + 1] == opponent){
                  int x = 1;
                  while (row - x >= 0 && col + x <= 8 && board[row - x][col + x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row - x, col + x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if(col - 1 >= 0){
              if (col - 1 >= 0 && board[row][col - 1] == opponent){
                  int x = 1;
                  while (col - x >= 0 && board[row][col - x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if(col + 1 <= 8){
              if (board[row][col + 1] == opponent){
                  int x = 1;
                  while (col + x <= 8 && board[row][col + x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }
      } else {

          if (row + 1 <= 6){
              if (board[row + 1][col] == opponent){
                  int x = 1;
                  if(row + x <= 6){
                      while (row + x <= 6 && board[row + x][col] == opponent){
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
                  if(row - x >= 0){
                      while (row - x >= 0 && board[row - x][col] == opponent){
                          Coordinates pendingCoordinate = new Coordinates(row - x, col, "none");
                          pendingTiles.add(pendingCoordinate);
                          x++;
                      }
                  }
              }
          }

          if(col - 1 >= 0){
              if (board[row][col - 1] == opponent){
                  int x = 1;
                  while (col - x >= 0 && board[row][col - x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row, col - x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

          if(col + 1 <= 8){
              if (board[row][col + 1] == opponent){
                  int x = 1;
                  while (col + x <= 8 && board[row][col + x] == opponent){
                      if(col + x % 2 == 0) {
                          Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
                          pendingTiles.add(pendingCoordinate);
                          x++;
                      } else {
                          Coordinates pendingCoordinate = new Coordinates(row, col + x, "none");
                          pendingTiles.add(pendingCoordinate);
                          x++;
                      }
                  }
              }
          }


          if(row + 1 <= 6 && col - 1 >= 0){
              if(board[row + 1][col - 1] == opponent){
                  int x = 1;
                  while (row + x <= 6 && col - x >= 0 && board[row + x][col - x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row + x, col - x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }


          if(row + 1 <= 6 && col + 1 <= 8){
              if(board[row + 1][col + 1] == opponent){
                  int x = 1;
                  while (row + x <= 6 && col + x <= 8 && board[row + x][col + x] == opponent){
                      Coordinates pendingCoordinate = new Coordinates(row + x, col + x, "none");
                      pendingTiles.add(pendingCoordinate);
                      x++;
                  }
              }
          }

      }

      for (int i = 0; i < pendingTiles.size(); i++){
          board[pendingTiles.get(i).getRow()][pendingTiles.get(i).getCol()] = friend;
          System.out.println("Transformed Tiles: " + pendingTiles.get(i).getRow() + " " + pendingTiles.get(i).getCol());
      }


  }

  //made by Joshua Bantayan
  //prints the list of all possible moves
  public static void printPossibleMoves(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
      ArrayList<Coordinates> possibleMoves = new ArrayList<>();
      int moveCount = 0;
      for(int i = 0; i < friendlies.size(); i++){
          possibleMoves = checkPossibleMoves(friendlies.get(i).getRow(), friendlies.get(i).getCol(), friend, opponent, board);
          for(int j = 0; j < possibleMoves.size(); j++){
              System.out.println("Row:" + possibleMoves.get(j).getRow() + " Col: " + possibleMoves.get(j).getCol());
              moveCount++;
          }
      }
      if(moveCount == 0){
          System.out.println("Hexed");
      }
  }
  //made by Patrick Collado
  //returns an arraylist of all possible moves
  public static ArrayList<Coordinates> getPossibleMoves(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
      ArrayList<Coordinates> possibleMoves = new ArrayList<>();
      ArrayList<Coordinates> buffer = new ArrayList<>();
      for(int i = 0; i < friendlies.size(); i++){
          buffer = checkPossibleMoves(friendlies.get(i).getRow(), friendlies.get(i).getCol(), friend, opponent, board);
          for(int j = 0; j < buffer.size(); j++){
              Coordinates move = new Coordinates(buffer.get(j).getRow(), buffer.get(j).getCol(), "none");
              possibleMoves.add(move);
          }
      }
      return possibleMoves;
  }
  //made by Joshua Bantayan
  //this method will check all possible moves and returns the coordinates of the move that will transform
  //the least amount of tiles
  public static Coordinates recommendedMove(ArrayList<Coordinates> friendlies, char friend, char opponent, char[][] board){
      ArrayList<Coordinates> shuffledList = new ArrayList<>();
      Coordinates recomended;
      shuffledList = getPossibleMoves(friendlies, friend, opponent, board);
      Collections.shuffle(shuffledList);
      recomended = shuffledList.get(0);
      return recomended;
  }

  //Collaboration by the whole team
  public static void main(String[] args){
      ArrayList<Coordinates> possibleMoves = new ArrayList<>();
      ArrayList<Coordinates> friendlies = new ArrayList<>();
      //ArrayList<Coordinates> bufferPM = new ArrayList<>();
      boolean hexChecker;
      char[][] board = new char[7][9];
      char first;
      char you;
      char opp = ' ';
      Coordinates suggestedMove;
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
          suggestedMove = recommendedMove(friendlies, you, opp, board);
          System.out.println("Row: " + suggestedMove.getRow() + " Col: " + suggestedMove.getCol());
          System.out.println("Please enter your move:");
          yourMoveRow = sc.nextInt();
          yourMoveCol = sc.nextInt();
          setMove(yourMoveRow, yourMoveCol, you, board);
          transformSandwiched(yourMoveRow, yourMoveCol, you, opp, board);
          printBoard(board);

          System.out.println("What is their move?");
          enemyMoveRow = sc.nextInt();
          enemyMoveCol = sc.nextInt();
          setMove(enemyMoveRow, enemyMoveCol, opp, board);
          transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
      }else {
          System.out.println("What is their move?");
          enemyMoveRow = sc.nextInt();
          enemyMoveCol = sc.nextInt();
          setMove(enemyMoveRow, enemyMoveCol, opp, board);
          transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
      }

      while(winner.equals("none")){
          friendlies = scanBoard(you, board);
          printBoard(board);
          System.out.println("Here are your possible moves:");
          printPossibleMoves(friendlies, you, opp, board);
          System.out.println("Suggested move: ");
          suggestedMove = recommendedMove(friendlies, you, opp, board);
          System.out.println("Row: " + suggestedMove.getRow() + " Col: " + suggestedMove.getCol());
          System.out.println("Please enter your move:");
          yourMoveRow = sc.nextInt();
          yourMoveCol = sc.nextInt();
          setMove(yourMoveRow, yourMoveCol, opp, board);
          transformSandwiched(yourMoveRow, yourMoveCol, you, opp, board);
          printBoard(board);

          winner = checkWinner(you, opp, board);
          if(!winner.equals("none")){
              break;
          }

          System.out.println("What is their move?");
          enemyMoveRow = sc.nextInt();
          enemyMoveCol = sc.nextInt();
          setMove(enemyMoveRow, enemyMoveCol, opp, board);
          transformSandwiched(enemyMoveRow, enemyMoveCol, opp, you, board);
          printBoard(board);

          winner = checkWinner(you, opp, board);
          if(!winner.equals("none")){
              break;
          }

      }

      System.out.println(winner + "won!");
  }
}
