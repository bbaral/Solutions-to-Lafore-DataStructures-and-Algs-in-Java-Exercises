//tree234.java

//Chapter 10, Exercise 1: Write a method that returns the minimum value
//in a 2-3-4 tree.

//Chapter 10, Exercise 2: Write a method that does an in-order traverse
//of a 2-3-4 tree. It should display all the items in order.

//Chapter 10, Exercise 3: A 2-3-4 tree can be used a sorting machine. Write
//a sort() method that's passed an array of key values from main() and writes
//them back to the array in sorted order.

import java.io.*;

class DataItem
{
	public long dData; //one data item
	
	public DataItem(long dd)
	{ dData = dd; }
	
	public void displayItem()
	{ System.out.print("/"+dData);}
}

class Node234
{
	private static final int ORDER = 4;
	private int numItems;
	private Node234 parent;
	private Node234 childArray[] = new Node234[ORDER];
	private DataItem itemArray[] = new DataItem[ORDER-1];
	
	public void connectChild(int childNum, Node234 child)
	{
		childArray[childNum] = child;
		if(child != null)
			child.parent = this;
	}
	
	public Node234 disconnectChild(int childNum)
	{
		Node234 tempNode = childArray[childNum];
		childArray[childNum] = null;
		return tempNode;
	}
	
	public Node234 getChild(int childNum)
	{ return childArray[childNum]; }
	
	public Node234 getParent()
	{ return parent; }
	
	public boolean isLeaf()
	{ return (childArray[0]==null) ? true : false; }
	
	public int getNumItems()
	{ return numItems; }
	
	public DataItem getItem(int index)
	{ return itemArray[index]; }
	
	public boolean isFull()
	{ return (numItems==ORDER-1) ? true : false; }
	
	public int findItem(long key)
	{
		for(int j=0; j<ORDER-1; j++)
		{
			if(itemArray[j] == null)
				break;
			else if(itemArray[j].dData == key)
				return j;
		}
		return -1;
	}
	
	public int insertItem(DataItem newItem)
	{
		//assumes node is not full
		numItems++;
		long newKey = newItem.dData;
		
		for(int j=ORDER-2; j>=0; j--)
		{
			if(itemArray[j] == null)
				continue;
			else
			{
				long itsKey = itemArray[j].dData;
				if(newKey < itsKey)
					itemArray[j+1] = itemArray[j];
				else
				{
					itemArray[j+1] = newItem;
					return j+1;
				}
			}//end else (not null)
		}//end for
		itemArray[0] = newItem;
		return 0;
	}
	
	public DataItem removeItem()
	{
		//assumes node not empty
		DataItem temp = itemArray[numItems-1];
		itemArray[numItems-1] = null;
		numItems--;
		return temp;
	}
	
	public void displayNode()	//format "/24/56/74/"
	{
		for(int j = 0; j<numItems; j++)
			itemArray[j].displayItem();
		System.out.println("/");
	}
} //end class Node

class Tree234
{
	private Node234 root = new Node234();
	
	public int find(long key)
	{
		Node234 curNode = root;
		int childNumber;
		while(true)
		{
			if( (childNumber=curNode.findItem(key)) != -1)
				return childNumber;			//found it
			else if( curNode.isLeaf() )
				return -1;					//couldn't find it
			else
				curNode = getNextChild(curNode, key);
		} //end while
	}
	
	//Exercise 1
	public void findMinimum()
	{
		Node234 curNode = root;
		Node234 answer = new Node234();
		while((curNode = curNode.getChild(0)) != null)
			answer = curNode;
		System.out.println("Minimum value is "+answer.getItem(0).dData);
	}
	
	//Exercise 2
	public void traverseInOrder()
	{
		recTraverse(root);
	}
	
	private void recTraverse(Node234 curNode)
	{
		//if it's a leaf, spew it all out
		if(curNode.isLeaf())
		{
			for(int j = 0; j<curNode.getNumItems(); j++)
				curNode.getItem(j).displayItem();
			return;
		}
		//otherwise get child 0, print item 0, get child 1, print item 1...
		else
		{
			for(int j = 0; j < curNode.getNumItems()+1; j++)
			{
				recTraverse(curNode.getChild(j));
				if(j < curNode.getNumItems())
					curNode.getItem(j).displayItem();
			}
		}
	}
	
	//Exercise 3b
	//write over the original array (passed by reference)
	//by iterating through the tree and increasing index of array (i)
	//after each insertion
		public void sortTraverse(long[] theArray)
		{
			int i = 0;
			recSortTraverse(root, theArray, i);
		}
		
		private int recSortTraverse(Node234 curNode, long[] theArray, int i)
		{
			//if it's a leaf, spew it all out
			if(curNode.isLeaf())
			{
				for(int j = 0; j<curNode.getNumItems(); j++)
				{
					theArray[i] = curNode.getItem(j).dData;
					i++;
				}
				return i;
			}
			//otherwise get child 0, print item 0, get child 1, print item 1...
			else
			{
				for(int j = 0; j < curNode.getNumItems()+1; j++)
				{
					i = recSortTraverse(curNode.getChild(j), theArray, i);
					if(j < curNode.getNumItems())
					{
						theArray[i] = curNode.getItem(j).dData;
						i++;
					}
				}
				return i;
			}
		}
	
	public void insert(long dValue)
	{
		Node234 curNode = root;
		DataItem tempItem = new DataItem(dValue);
		
		while(true)
		{
			if (curNode.isFull() )		//if node full,
			{
				split(curNode);			//split it
				curNode = curNode.getParent(); //back up
										//search once
				curNode = getNextChild(curNode, dValue);
			}//end if(node is full)
			
			else if (curNode.isLeaf() )	//if node is leaf,
				break;					//go insert
			
			else
				curNode = getNextChild(curNode, dValue);
		}//end while
		
		curNode.insertItem(tempItem);	//insert new DataItem
	}
		
	public void split(Node234 thisNode)
	{
		//assumes node is full
		DataItem itemB, itemC;
		Node234 parent, child2, child3;
		int itemIndex;
		
		itemC = thisNode.removeItem(); //remove rightmost item
		itemB = thisNode.removeItem(); //remove next item
		child2 = thisNode.disconnectChild(2); //remove children
		child3 = thisNode.disconnectChild(3);
		
		Node234 newRight = new Node234();
		
		if(thisNode==root)					//if this is the root
		{
			root = new Node234();			//make a new root
			parent = root;					//and connect thisNode to it
			root.connectChild(0, thisNode);
		}
		else
			parent = thisNode.getParent();
		
		//deal with parent
		itemIndex = parent.insertItem(itemB); //insert old middle item to parent
		int n = parent.getNumItems();			//total items?
		
		for(int j = n-1; j>itemIndex; j--)	//move parent's connections
		{
			Node234 temp = parent.disconnectChild(j);
			parent.connectChild(j+1, temp);	//one child to the right
		}
		
		//connect newRight to parent
		parent.connectChild(itemIndex+1, newRight);
		
		//deal with newRight
		newRight.insertItem(itemC);			//item C to newRight
		newRight.connectChild(0, child2);	//connect to 0 and 1
		newRight.connectChild(1, child3);	//on newRight
	}//end split()

	public Node234 getNextChild(Node234 theNode, long theValue)
	{
		int j;
		//assumes node is not empty, not full, not a leaf
		int numItems = theNode.getNumItems();
		for(j=0; j<numItems; j++)		//for each item in node
		{								//are we less?
			if( theValue < theNode.getItem(j).dData)
				return theNode.getChild(j); //return left child
		} //end for						//we're greater, so
		return theNode.getChild(j);		//return right child
	}
	
	public void displayTree()
	{
		recDisplayTree(root, 0, 0);
	}
	
	private void recDisplayTree(Node234 thisNode, int level, int childNumber)
	{
		System.out.print("level="+level+" child="+childNumber+" ");
		thisNode.displayNode();
		
		//call ourselves for each child of this node
		int numItems = thisNode.getNumItems();
		for(int j = 0; j < numItems+1; j++)
		{
			Node234 nextNode = thisNode.getChild(j);
			if(nextNode != null)
				recDisplayTree(nextNode, level+1, j);
			else
				return;
		}
	}
} //end class tree234

class Tree234App
{
	//Exercise 3: Sort array with tree
	public static void sort(long[] theArray, int counter)
	{
		Tree234 sortingTree = new Tree234();
		//insert all values into the tree
		for(int j = 0; j < counter; j++)
			sortingTree.insert(theArray[j]);
		sortingTree.sortTraverse(theArray);
	}
	
	public static void main(String[] args) throws IOException
	{
		long value;
		Tree234 theTree = new Tree234();
		
		theTree.insert(50);
		theTree.insert(40);
		theTree.insert(60);
		theTree.insert(30);
		theTree.insert(70);
		
		while(true)
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, minimum, traverse, order, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theTree.displayTree();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theTree.insert(value);
				break;
			case 'm':
				theTree.findMinimum();
				break;
			case 'o':
				value = 0;
				long[] arrayToSort = new long[20];
				int counter = 0;
				while(value != -1 && counter < 20)
				{
					System.out.print("Enter value (enter '-1' to stop): ");
					value = getInt();
					if(value != -1)
					{
						arrayToSort[counter] = value;
						counter++;
					}
				}
				System.out.println("Before sorting...");
				for(int j = 0; j < counter; j++)
					System.out.print(arrayToSort[j] + " ");
				sort(arrayToSort, counter);
				System.out.println("After sorting...");
				for(int j = 0; j < counter; j++)
					System.out.print(arrayToSort[j] + " ");
				System.out.println("");
				break;
			case 't':
				theTree.traverseInOrder();
				break;
			case 'f':
				System.out.print("Enter value to find: ");
				value = getInt();
				int found = theTree.find(value);
				if(found != -1)
					System.out.println("Found " + value);
				else
					System.out.println("Could not find " + value);
				break;
			default:
				System.out.println("Invalid entry!");
			}//end switch
		}//end while
	}//end main()
	
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
} //end class Tree234App




















