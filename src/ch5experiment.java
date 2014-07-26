/* Modify main() in the linkList.java program (Listing 5.1) so that it continuously
inserts links into the list until memory is exhausted. After each 1,000
items, have it display the number of items inserted so far. This way, you can
learn approximately how many links a list can hold in your particular
machine. (Of course, the number will vary depending on what other programs
are in memory and many other factors.) Don’t try this experiment if it will
crash your institution’s network. */

// linkList.java
// demonstrates linked list
// to run this program: C>java LinkListApp
////////////////////////////////////////////////////////////////
class Link
{
	public int iData; // data item (key)
	public double dData; // data item
	public Link next; // next link in list
	// -------------------------------------------------------------
	public Link(int id, double dd) // constructor
	{
		iData = id; // initialize data
		dData = dd; // (‘next’ is automatically
	} // set to null)
	// -------------------------------------------------------------
	public void displayLink() // display ourself
	{
		System.out.print("{" + iData + ", " + dData + "} ");
	}
} // end class Link
////////////////////////////////////////////////////////////////
class LinkList
{
	private Link first; // ref to first link on list
	// -------------------------------------------------------------
	public LinkList() // constructor
	{
		first = null; // no items on list yet
	}
	// -------------------------------------------------------------
	public boolean isEmpty() // true if list is empty
	{
		return (first==null);
	}
	// -------------------------------------------------------------
	// insert at start of list
	public void insertFirst(int id, double dd)
	{ // make new link
		Link newLink = new Link(id, dd);
		newLink.next = first; // newLink --> old first
		first = newLink; // first --> newLink
	}
	// -------------------------------------------------------------
	public Link deleteFirst() // delete first item
	{ // (assumes list not empty)
		Link temp = first; // save reference to link
		first = first.next; // delete it: first-->old next
		return temp; // return deleted link
	}
	// -------------------------------------------------------------
	public void displayList()
	{
		System.out.print("List (first-->last): ");
		Link current = first; // start at beginning of list
		while(current != null) // until end of list,
		{
			current.displayLink(); // print data
			current = current.next; // move to next link
		}
		System.out.println("");
	}
	// -------------------------------------------------------------
} // end class LinkList
////////////////////////////////////////////////////////////////
class LinkListApp
{
	public static void main(String[] args)
	{
		LinkList theList = new LinkList(); // make new list
		double counter = 0;
		while(true)
		{
			for(int i = 0; i < 1000; i++)
			{
				theList.insertFirst(5,5.55);
				counter++;
			}
			System.out.println("Counter: " + counter);
		}
	} // end main()
} // end class LinkListApp