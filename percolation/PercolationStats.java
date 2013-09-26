/**
 * Auto Generated Java Class.
 */
public class PercolationStats {
    
    private double[] fracs;        
    private int T;    
    
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) 
            throw new java.lang.IllegalArgumentException();

        this.T = T;        
        fracs = new double[T];        

        int x, y, opened;        
        Percolation p;
        for (int i = 0; i < T; i++) {            
            p = new Percolation(N);                            
            opened = 0;
            while (!p.percolates()) {
                x = StdRandom.uniform(1, N + 1);                
                y = StdRandom.uniform(1, N + 1);                                
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    opened++;
                }                                
            }
            // it percolates
            fracs[i] = (double) opened / (N * N);
        }                         
    }
    
    public double mean() {
        return StdStats.mean(fracs);
    }
    
    public double stddev() {   
        if (T == 1)
            return Double.NaN;    
        return StdStats.stddev(fracs);        
    }
    
    public double confidenceLo() {
        return mean() - ((1.96 * Math.sqrt(stddev())) / Math.sqrt(T));
    }
    
    public double confidenceHi() {
        return mean() + ((1.96 * Math.sqrt(stddev())) / Math.sqrt(T));
    }
    
    public static void main(String[] args) {        
        // int N = Integer.parseInt(args[0]);
        // int T = Integer.parseInt(args[1]);            
        
        // N = 1??

        Stopwatch sw = new Stopwatch();
        PercolationStats monteCarlo = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));                    
        System.out.println("time used               = " + sw.elapsedTime() + "s");
        System.out.println("mean                    = " + monteCarlo.mean());
        System.out.println("stddev                  = " + monteCarlo.stddev());
        System.out.println("95% confidence interval = " + monteCarlo.confidenceLo() + ", " + monteCarlo.confidenceHi());
    }
    
}
