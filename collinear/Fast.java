import java.util.*;

public class Fast {
  public static void main(String[] args) {
    // rescale coordinates and turn on animation mode    
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);                         
    
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    Point[] bySlope = new Point[N]; // Sorting by slope        
    for (int i = 0; i < N; i++) {      
      int x = in.readInt();
      int y = in.readInt();
      Point p = new Point(x, y);
      points[i] = p;         
      p.draw();             
    }

    Arrays.sort(points); // Sorted by natural order
    for (int i = 0; i < N; i++) bySlope[i] = points[i]; // Copy    

    for (int i = 0; i < N; i++) {  
      Arrays.sort(bySlope);                            
      Arrays.sort(bySlope, points[i].SLOPE_ORDER);             

      int low = 1;      
      for (int j = 2; j < N; j++) {                              
        if (bySlope[0].slopeTo(bySlope[low]) == bySlope[0].slopeTo(bySlope[j])) {                              
          if (j == (N - 1) && (j - low) >= 2 && bySlope[0].compareTo(bySlope[low]) < 0) { // is the last one, so print it now                        
            bySlope[0].drawTo(bySlope[j]);                    
            // Display collinear points.
            StdOut.print(bySlope[0].toString());
            for (int z = low; z <= j; z++) {
                StdOut.print("->");
                StdOut.print(bySlope[z]);                
            }      
            StdOut.println();
          }                     
        } 
        else if ((j - low) > 2 && bySlope[0].compareTo(bySlope[low]) < 0) {                            
          bySlope[0].drawTo(bySlope[j - 1]);                    
          // Display collinear points.
          StdOut.print(bySlope[0].toString());
          for (int z = low; z < j; z++) {
              StdOut.print("->");
              StdOut.print(bySlope[z]);
          }                    
          StdOut.println();
          low = j;           
        }
        else {
          low = j;
        }                     
      }      
    }  
  }  
}

