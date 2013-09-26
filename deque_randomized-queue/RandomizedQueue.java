import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{

  private Item[] items;
  private int size;

  public RandomizedQueue() 
  {
    items = (Item[]) new Object[2];
    size = 0;
  } 

  public boolean isEmpty() 
  {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void enqueue(Item item) 
  {
    if (item == null) throw new NullPointerException();

    if (size == items.length) resize(size * 2);
    items[size++] = item;
  }

  public Item dequeue() 
  {
    if (isEmpty()) throw new NoSuchElementException();

    int index = StdRandom.uniform(size);
    Item item = items[index];
    
    items[index] = items[--size];
    items[size] = null;

    if (size > 0 && size == items.length / 4) resize(items.length / 2); // one quarter full;

    return item;

  }

  public Item sample() 
  {
    if (isEmpty()) throw new NoSuchElementException();
    return items[StdRandom.uniform(size)];
  }

  public Iterator<Item> iterator() { return new RandomizedQueueIterator(); }

  private class RandomizedQueueIterator implements Iterator<Item> 
  {
    private int n;
    private Item[] iitems;

    private RandomizedQueueIterator() 
    {
      n = size;
      iitems = (Item[]) new Object[n];
      for (int i = 0; i < n; i++)
        iitems[i] = items[i]; 
    }

    public boolean hasNext() { return n > 0; }

    public Item next() 
    {
      if (!hasNext()) throw new NoSuchElementException();

      int index = StdRandom.uniform(n);
      Item item = iitems[index];
      iitems[index] = iitems[--n];
      iitems[n] = null;
      return item;
    }

    public void remove() { throw new UnsupportedOperationException(); }

  }

  private void resize(int capacity)
  {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < size; i++) 
      copy[i] = items[i];
    items = copy;      
  }

}