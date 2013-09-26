public class Subset
{
  
  public static void main(String[] args) 
  {
    int k = Integer.parseInt(args[0]);
    RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();

    while (!StdIn.isEmpty())
    {
      String item = StdIn.readString();      
      randomQueue.enqueue(item);
    }

    for (int i = 0; i < k && !randomQueue.isEmpty(); i++) 
      StdOut.printf("%s\n", randomQueue.dequeue());

  }

}