public class Solver {
  
  private SearchNode result;
  
  private class SearchNode implements Comparable<SearchNode> {

    private final Board board;
    private int moves;
    private SearchNode prev;    
    private int priority;    

    public SearchNode(Board board, SearchNode prev) {
      this.board = board;
      this.prev = prev;
      
      if (prev == null) moves = 0;
      else              moves = prev.moves + 1;
      
      priority = board.manhattan() + moves;      
    }

    public int compareTo(SearchNode that) {
      return this.priority - that.priority;
    }
    
  }

  public Solver(Board initial) {        
    if (initial.isGoal()) result = new SearchNode(initial, null);
    else                  result = solve(initial, initial.twin());
  }  

  private SearchNode solve(Board initial, Board twin) {    
    SearchNode current;
    SearchNode currentTwin;
    
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    MinPQ<SearchNode> pqTwin = new MinPQ<SearchNode>();
    pq.insert(new SearchNode(initial, null));
    pqTwin.insert(new SearchNode(twin, null));

    while (true) {
      current = step(pq);            
      currentTwin = step(pqTwin);

      if (currentTwin == null && current == null) return null; 
      
      if (current != null && current.board.isGoal()) return current;      
      if (currentTwin != null && currentTwin.board.isGoal()) return null;            
    }

  }

  private SearchNode step(MinPQ<SearchNode> pq) {
    if (pq.isEmpty()) return null;
    SearchNode current = pq.delMin();
    for (Board neighborBoard: current.board.neighbors()) 
      if (current.prev == null || !neighborBoard.equals(current.prev.board)) 
        pq.insert(new SearchNode(neighborBoard, current));
    
    return current;
  }

  public boolean isSolvable() {
    return result != null;
  }

  public int moves() {
    if (!isSolvable()) return -1;
    else               return result.moves;        
  }

  public Iterable<Board> solution() {
    if (!isSolvable()) return null;    
    
    Stack<Board> solution = new Stack<Board>();

    SearchNode current = result;
    while (current != null) {
      solution.push(current.board);    
      current = current.prev;
    }
          
    return solution;    
  }

  public static void main(String[] args) {    
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    
    Board initial = new Board(blocks);           

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}