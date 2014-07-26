//Chapter 5, Exercise 3

/*A circular list is a linked list in which the last link points back to the first link.
There are many ways to design a circular list. Sometimes there is a pointer to
the “start” of the list. However, this makes the list less like a real circle and
more like an ordinary list that has its end attached to its beginning. Make a
class for a singly linked circular list that has no end and no beginning. The
only access to the list is a single reference, current, that can point to any link
on the list. This reference can move around the list as needed. (See
Programming Project 5.5 for a situation in which such a circular list is ideally
suited.) Your list should handle insertion, searching, and deletion. You may
find it convenient if these operations take place one link downstream of the
link pointed to by current. (Because the upstream link is singly linked, you
can’t get at it without going all the way around the circle.) You should also be
able to display the list (although you’ll need to break the circle at some arbitrary
point to print it on the screen). A step() method that moves current
along to the next link might come in handy too.*/

class CLink
{
	public long lData;
	public CLink next;
	
	public CLink(long value)
	{ lData = value; }
	
	public void displayLink()
	{ System.out.print(lData + " "); }
}

class CLinkedList
{
	private CLink current;
	private int nItems; //keeps track of length of the list
	
	public CLinkedList()
	{
		current = null;
		nItems = 0;
	}
	
	public boolean isEmpty() 
	{ return current==null; }
	
	public long getItems()
	{ return nItems; }
	
	public void step()
	{
		current = current.next;
	}
	
	public void insert(long value)	//inserts new link after current link
	{
		if(isEmpty())
		{
			current = new CLink(value);
			current.next = current;
		}
		else
		{
			CLink newLink = new CLink(value);
			newLink.next = current.next;
			current.next = newLink;
		}
		nItems++;
	}
	
	public CLink find(long value)
	{
		for(int i = 0; i < nItems; i++)
		{
			if(current.lData == value) 
			{
				System.out.println("Found " + value);
				return current;
			}
			else step();
		}
		System.out.println("Couldn't find " + value + ".");
		return null;
	}
	
	public CLink delete()
	{
		if(isEmpty())
		{
			System.out.println("List is empty.");
			return null;
		}
		else if(nItems == 1)
		{
			current = null;
			nItems = 0;
			return null;
		}
		else
		{
			CLink temp = current.next;
			current.next = current.next.next;
			nItems--;
			return temp;
		}
	}
	
	public CLink peek()
	{
		return current;
	}
	
	public void display()
	{
		System.out.print("Circular List (from current): ");
		CLink index = current;
		for(int i = 0; i < nItems; i++)
		{
			System.out.print(index.lData + " ");
			index = index.next;
		}
		System.out.println("");
	}
}

class CircularApp
{
	public static void main(String[] args)
	{
		CLinkedList theList = new CLinkedList();
		
		theList.insert(1);
		theList.insert(2);
		theList.insert(3);
		theList.insert(4);
		theList.display();
		
		theList.delete();
		theList.display();
		theList.delete();
		theList.display();
		
	}
}