import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
  // inner class
  private class Node 
  {
    Item item;
    Node next;
    Node prev;    
  }  

  private Node front;
  private Node back;
  private int size;
  
  public Deque()
  {    
    front = null;
    back = null;
    size = 0;
  }

  public boolean isEmpty() 
  {
    return size == 0;
  }

  public int size() 
  {
    return size;
  }

  public void addFirst(Item item) 
  {
    if (item == null) throw new NullPointerException();    

    if (size == 0)
    {
      Node first = new Node();
      first.item = item;
      front = first;
      back = first;
    } 
    else 
    {      
      Node newFront = new Node();
      newFront.prev = front;
      newFront.item = item;
      Node oldFront = front;
      oldFront.next = newFront;
      front = newFront;
    }
    size++;
  }

  public void addLast(Item item) 
  {
    if (item == null) throw new java.lang.NullPointerException();

    if (size == 0)
    {
      Node first = new Node();
      first.item = item;
      front = first;
      back = first;
    } 
    else
    {
      Node oldLast = back;
      Node newLast = new Node();
      newLast.item = item;
      newLast.next = oldLast;
      oldLast.prev = newLast;
      back = newLast;
    }    
    size++;
  }

  public Item removeFirst() 
  {
    if (size == 0) throw new NoSuchElementException();
    Item firstItem = front.item;
    
    if (size > 1) 
    {
      front = front.prev;
      front.next = null;
      // Node newFirst = front.prev;
      // newFirst.next = null;
    } else
    {
      front = null;
      back = null;
    }
        
    size--;
    return firstItem;
  }

  public Item removeLast() 
  {
    if (size == 0) throw new NoSuchElementException();
    Item lastItem = back.item;
    Node oldLast = back;
    
    if (size > 1)
    {
      back = oldLast.next;
      back.prev = null;
    } else 
    {
      front = null;
      back = null;
    }
        
    oldLast = null;
    size--;
    return lastItem;
  }

  public Iterator<Item> iterator() { return new DequeIterator(); }

  // inner class
  private class DequeIterator implements Iterator<Item>
  {      
    private Node current = front;
    
    public boolean hasNext() { return current != null; }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }

    public void remove() { throw new UnsupportedOperationException(); }

  }

  // public static void main(String[] args) 
  // {
  //   Deque d = new Deque();
  //   d.addFirst(1);
  // }

}