//Chapter 5, Exercise 1: Priority Queue implemented with sorted Linked List.
//The remove operation should remove the item with the smallest key.

class QLink
{
	public long dData;	// data item
	public QLink next;   // next link in list
	
	public QLink(long dd)
	{ dData = dd; }
	
	public void displayLink()
	{ System.out.print(dData + " "); }
}

class SortedList
{
	private QLink first;
	
	public SortedList()
	{
		first = null;
	}
	
	public boolean isEmpty() { return (first==null); }
	
	public void insert(long key)
	{
		QLink newLink = new QLink(key);
		QLink previous = null;
		QLink current = first;
		
		while(current != null && key > current.dData)
		{
			previous = current;
			current = current.next;
		}
		if(previous == null)
			first = newLink;
		else
			previous.next = newLink;
		newLink.next = current;
	}
		
	public QLink remove()
	{
		QLink temp = first;
		first = first.next;
		return temp;
	}
	
	public void displayList()
	{
		System.out.print("List (first-->last): ");
		QLink current = first;
		while(current != null)
		{
			 current.displayLink();
			 current = current.next;
		}
		System.out.println("");
	}
	
	public long getFirst()
	{
		return first.dData;
	}
}

class LaForePriorityQueue
{
	SortedList queList;
	
	public LaForePriorityQueue()
	{
		queList = new SortedList();
	}
	
	public void insert(long value)
	{
		queList.insert(value);
	}
	
	public QLink remove()	//removes link with the smallest key
	{
		if(queList.isEmpty())
		{
			System.out.println("Queue is empty.");
			return null;
		}
		return queList.remove();
	}
	
	public long peekMin()
	{
		return queList.getFirst();
	}
	
	public boolean isEmpty()
	{
		return queList.isEmpty();
	}
	
	public void display()
	{
		queList.displayList();
	}
}

class ChapterFive
{
	public static void main(String[] args)
	{
		LaForePriorityQueue myQ = new LaForePriorityQueue();
		myQ.insert(5);
		myQ.insert(10);
		myQ.insert(1);
		long temp = myQ.peekMin();
		System.out.println("MIN IS " + temp);
		
		myQ.remove();
		myQ.remove();
		myQ.display();
		
		myQ.insert(600);
		myQ.insert(2250);
		myQ.insert(1);
		myQ.insert(15);
		myQ.display();
		
		QLink answer = myQ.remove();
		System.out.println(answer.dData);
		
		answer = myQ.remove();
		System.out.println(answer.dData);
		
		answer = myQ.remove();
		System.out.println(answer.dData);
		
		answer = myQ.remove();
		System.out.println(answer.dData);
		
		answer = myQ.remove();
		System.out.println(answer.dData);
		
		answer = myQ.remove();
	}
}