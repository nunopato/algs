public class Board {

  private int[][] board;  

  public Board(int[][] blocks) {
    int[][] cp = new int[blocks.length][];    

    for (int i = 0; i < blocks.length; i++)
      cp[i] = blocks[i].clone();

    board = cp;    
  }

  public int dimension() {
    return board.length;
  }

  public int hamming() {
    int total = 0;        
    for (int i = 0; i < dimension(); i++)
      for (int j = 0; j < dimension(); j++)
        if (board[i][j] != 0 && !inPlace(i, j)) 
          total += 1;        
    return total;    
  }

  public int manhattan() {    
    int total = 0;    
    for (int i = 0; i < dimension(); i++) 
      for (int j = 0; j < dimension(); j++){        
        if (board[i][j] != 0 && !inPlace(i, j))           
          total += getCorrectPlaceSteps(i, j);        
      }
                  
    return total;
  }

  public boolean isGoal() {
    for (int i = 0; i < dimension(); i++)
      for (int j = 0; j < dimension(); j++)
        if (!inPlace(i, j))
          return false;
    return true;
  }

  public Board twin() {
    int[][] cp = new int[dimension()][];    

    for (int i = 0; i < dimension(); i++)
      cp[i] = this.board[i].clone();

    Board b = new Board(cp);    

    if (this.board[0][0] != 0 && this.board[0][1] != 0) {      
      b.board[0][0] = this.board[0][1];
      b.board[0][1] = this.board[0][0];
    } else {      
      b.board[1][0] = this.board[1][1];
      b.board[1][1] = this.board[1][0];
    }
    return b;
  }

  public boolean equals(Object y) {
    if (y == this)  return true;
    if (y == null) return false; 
    if (y.getClass() != this.getClass()) return false;

    Board that = (Board) y;
    if (this.dimension() != that.dimension()) return false;

    int[][] newBoard = that.board;      
    for (int i = 0; i < dimension(); i++)
      for (int j = 0; j < dimension(); j++)
        if (board[i][j] != newBoard[i][j]) 
          return false;
    return true;
  }

  public Iterable<Board> neighbors() {    
    Stack<Board> neighbors = new Stack<Board>();

    int i, j = 0;
    
    outerloop: // Labels
    for (i = 0; i < dimension(); i++) 
      for (j = 0; j < dimension(); j++)         
        if (this.board[i][j] == 0)          
          break outerloop;        
        
    if (i >= 0 && i < dimension()) {
      if (i >= 1) {
        int[][] cp = cloneBoard();      
        cp[i - 1][j] = 0;
        cp[i][j] = this.board[i - 1][j];  
        Board b = new Board(cp);
        neighbors.push(b);
      }
      if (i < dimension() - 1) {
        int[][] cp = cloneBoard();
        cp[i + 1][j] = 0;
        cp[i][j] = this.board[i + 1][j];
        Board b = new Board(cp);
        neighbors.push(b);  
      }      
    }

    if (j >= 0 && j < dimension()) {
      if (j >= 1) {
        int[][] cp = cloneBoard();
        cp[i][j - 1] = 0;
        cp[i][j] = this.board[i][j - 1];
        Board b = new Board(cp);
        neighbors.push(b);
      }
      if (j < dimension() - 1) {
        int[][] cp = cloneBoard();
        cp[i][j + 1] = 0;
        cp[i][j] = this.board[i][j + 1];
        Board b = new Board(cp);
        neighbors.push(b);
      }            
    }    
    return neighbors;
  }

  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(dimension() + "\n");
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++)         
        s.append(Integer.toString(board[i][j]) + " ");    
      s.append("\n");
    }
    return s.toString();
  }

  /*
    helper methods
  */

  private int[][] cloneBoard() {        
    int[][] cp = new int[dimension()][];
    for (int i = 0; i < dimension(); i++)
      cp[i] = this.board[i].clone();
    return cp;
  }

  private int getCorrectPlaceSteps(int i, int j) {        
    // Sum the number of horizontal and vertical
    return Math.abs(i - getCorrectI(i, j)) + Math.abs(j - getCorrectJ(i, j));
  }

  private int getCorrectI(int i, int j) {
    int current = board[i][j];    
    return (current - 1) / dimension();
  }

  private int getCorrectJ(int i, int j) {
    int current = board[i][j];    
    return (current - 1) % dimension();
  }
  
  private boolean inPlace(int i, int j) {
    int current = board[i][j];
    if (current == 0) return i == (dimension() - 1) && j == i;
    else              return (dimension() * i + j + 1) == current;
  } 

}