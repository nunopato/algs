public class Brute {
  public static void main(String[] args) {
    // rescale coordinates and turn on animation mode    
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);                         
    
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    
    for (int i = 0; i < N; i++) {      
      int x = in.readInt();
      int y = in.readInt();
      Point p = new Point(x, y);
      points[i] = p;
      p.draw();                      
    }   
    
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          for (int z = 0; z < N; z++) {            
            if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[j]) == points[i].slopeTo(points[z])) {              
                // To print only the combination of points that go in ascending order
              if (points[i].compareTo(points[j]) < 0 && points[j].compareTo(points[k]) < 0 && points[k].compareTo(points[z]) < 0) {
                points[i].drawTo(points[z]);              
                StdOut.println(points[i].toString() + "->" + points[j].toString() + "->" + points[k].toString() + "->" + points[z].toString());                        
              }              
            }
          }
        }
      }
    }
    // display to screen all at once
    StdDraw.show(0);
  }
}