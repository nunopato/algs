/**
 * Auto Generated Java Class.
 */
public class Percolation 
{    
    private boolean[][] grid;
    private int N;
    private WeightedQuickUnionUF connections = null;
    private WeightedQuickUnionUF fullness = null;
    private final int virtualTop;
    private final int virtualBottom;
    
    public Percolation(int N) 
    {
        this.N = N;
        grid = new boolean[N][N]; // default false        
        connections = new WeightedQuickUnionUF(N * N + 2); // + 2 is for virtual top and bottom sites                                
        fullness = new WeightedQuickUnionUF(N * N + 2);

        virtualTop = 0 ; 
        virtualBottom = N * N + 1;

        for (int i = 1; i <= N; i++) {
            connections.union(virtualTop, i);
            fullness.union(virtualTop, i);
        } 
            
        for (int i = N * N - N + 1; i <= N * N; i++) 
            connections.union(i, virtualBottom);
    }
    
    // By convention, the indices i and j are integers between 1 and N
    // Open site (i,j) if not already open
    public void open(int i, int j) {
        if (!validIndices(i, j))                 
            throw new java.lang.IndexOutOfBoundsException();        

        grid[i - 1][j - 1] = true; // Open it
        
        //Checking all 4 possibilities for adjacent cells
        if (i > 1 && isOpen(i - 1, j)) { // top
            connections.union(N * (i - 1) + (j), N * (i - 2) + (j));
            fullness.union(N * (i - 1) + (j), N * (i - 2) + (j));
        }

        if (i < N && isOpen(i + 1, j)) { // bottom
            connections.union(N * (i - 1) + (j), N * i + (j));
            fullness.union(N * (i - 1) + (j), N * i + (j));
        }        

        if (j > 1 && isOpen(i, j - 1)) { // left
            connections.union(N * (i - 1) + (j), N * (i - 1) + (j - 1)); 
            fullness.union(N * (i - 1) + (j), N * (i - 1) + (j - 1)); 
        }

        if (j < N && isOpen(i, j + 1)) { // right
            connections.union(N * (i - 1) + (j), N * (i - 1) + j+1);
            fullness.union(N * (i - 1) + (j), N * (i - 1) + j+1);
        }

    }
    
    public boolean isOpen(int i, int j) {
        if (!validIndices(i, j))                 
            throw new java.lang.IndexOutOfBoundsException();

        return grid[i - 1][j - 1];
    }
    
    public boolean isFull(int i, int j) {
        if (!validIndices(i, j))                 
            throw new java.lang.IndexOutOfBoundsException();
        
        if (!isOpen(i, j))
            return false;
        
        int cur = N * (i-1) + j;
        return fullness.connected(virtualTop, cur);

    }
    
    public boolean percolates() {
        return connections.connected(virtualTop, virtualBottom);
        // for (int i = 1; i <= N; i++) {
        //     if(isFull(N, i)) {
        //         return true;
        //     }
        // }
        // return false;
    }    

    private int xyTo1D(int x, int y) {        
        return N * (x - 1) + (y - 1);
    }

    private boolean validIndices(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) 
            return false;
        return true;    
    }

    public static void main(String[] args) {        
        Percolation p = new Percolation(5);
        p.open(1, 1);
        p.open(1, 2);
        System.out.println(p.connections.connected(p.xyTo1D(1,1), p.xyTo1D(1, 5)));        
    }
}
