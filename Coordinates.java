public class Coordinates{
  int row;
  int col;
  String direction;
  public Coordinates(int row, int col, String direction){
    this.row = row;
    this.col = col;
    this.direction = direction;
  }

  public int getRow(){
    return row;
  }

  public int getCol(){
    return col;
  }

  public String getDirection(){
    return direction;
  }
}
