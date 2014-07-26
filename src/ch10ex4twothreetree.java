//tree23.java

//Chapter 10, Exercises 4 and 5:

/* Modify the tree234.java program (Listing 10.1) so that it creates and works
with 2-3 trees instead. It should display the tree and allow searches. It should
also allow items to be inserted, but only if the parent of the leaf node (which is
being split) does not also need to be split. This implies that the split() routine
need not be recursive. In writing insert(), remember that no splits happen
until the appropriate leaf has been located. Then the leaf will be split if it’s full.
You’ll need to be able to split the root too, but only when it’s a leaf. With this
limited routine you can insert fewer than nine items before the program
crashes. */

/* Extend the program in Programming Project 10.4 so that the split() routine is
recursive and can handle situations with a full parent of a full child. This will
allow insertion of an unlimited number of items. Note that in the revised
split() routine you’ll need to split the parent before you can decide where the
items go and where to attach the children. */

import java.io.*;


class Node23
{
	private static final int ORDER = 3;
	private int numItems;
	private Node23 parent;
	private Node23 childArray[] = new Node23[ORDER];
	private DataItem itemArray[] = new DataItem[ORDER-1];
	
	public void connectChild(int childNum, Node23 child)
	{
		childArray[childNum] = child;
		if(child != null)
			child.parent = this;
	}
	
	public Node23 disconnectChild(int childNum)
	{
		Node23 tempNode = childArray[childNum];
		childArray[childNum] = null;
		return tempNode;
	}
	
	public Node23 getChild(int childNum)
	{ return childArray[childNum]; }
	
	public Node23 getParent()
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

class Tree23
{
	private Node23 root = new Node23();
	
	//find needs no modification from the 2-3-4 tree
	public int find(long key)
	{
		Node23 curNode = root;
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
	
	//dives down to leaf and inserts the new DataItem if it's not full.
	//otherwise, splits upwards through all full nodes and modifies the tree.
	public void insert(long dValue)
	{
		Node23 curNode = root;
		DataItem tempItem = new DataItem(dValue);
		
		//Find point of insertion
		while(true)
		{
			if (curNode.isLeaf() )	//if node is leaf,
				break;					//go insert
			else
				curNode = getNextChild(curNode, dValue);
		}//end while
		
		if(curNode.isFull())	//if leaf is full, we have to split it
		{						//and all full parents
			split(curNode, tempItem);
		}
		else					//we lucked out, just put it in the leaf
			curNode.insertItem(tempItem);	//insert new DataItem
	}
		
	//split a full node, insert the middle value into the parent
	public Node23 split(Node23 thisNode, DataItem newItem)
	{
		Node23 parent = thisNode.getParent();
		
		//sort the two values from the node as well as the new item
		//ugly sort, I know, but it's easy and shouldn't hinder performance
		DataItem[] sortedItems = new DataItem[3];
		if(newItem.dData <= thisNode.getItem(0).dData)
		{
			sortedItems[0] = newItem;
			sortedItems[1] = thisNode.getItem(0);
			sortedItems[2] = thisNode.getItem(1);
		}
		else if(newItem.dData <= thisNode.getItem(1).dData)
		{
			sortedItems[0] = thisNode.getItem(0);
			sortedItems[1] = newItem;
			sortedItems[2] = thisNode.getItem(1);
		}
		else
		{
			sortedItems[0] = thisNode.getItem(0);
			sortedItems[1] = thisNode.getItem(1);
			sortedItems[2] = newItem;
		}
		
		//empty out the current node and put the lowest value as only data item
		thisNode.removeItem();
		thisNode.removeItem();
		thisNode.insertItem(sortedItems[0]);
		
		//make a new right node for the largest value
		Node23 newRightNode = new Node23();
		newRightNode.insertItem(sortedItems[2]);
		
		//if this node is the root, make a new root
		if(thisNode == this.root)
		{
			Node23 newRoot = new Node23();
			newRoot.insertItem(sortedItems[1]);
			newRoot.connectChild(0, thisNode);
			newRoot.connectChild(1, newRightNode);
			this.root = newRoot;
			return newRightNode;
		}
		//if the parent is full, call split again
		//then readjust child relationships
		else if(parent.isFull())
		{
			Node23 sibling = split(parent, sortedItems[1]);
			
			//case 1: split node is leftmost child of parent
			if(thisNode.equals(parent.getChild(0)))
			{
				Node23 childB = parent.disconnectChild(1);
				Node23 childC = parent.disconnectChild(2);
				parent.connectChild(1, newRightNode);
				sibling.connectChild(0, childB);
				sibling.connectChild(1, childC);
			}
			else if(thisNode.equals(parent.getChild(1)))
			{
				Node23 childC = parent.disconnectChild(2);
				sibling.connectChild(0, newRightNode);
				sibling.connectChild(1, childC);
			}
			else
			{
				parent.disconnectChild(2);
				sibling.connectChild(0, thisNode);
				sibling.connectChild(1, newRightNode);
			}
				
			return newRightNode;
		}
		//otherwise, add the middle value to the parent
		else
		{
			parent.insertItem(sortedItems[1]);
		
			//--then connect the newRightNode to parent--//
			
			//if the new Right node is made from the leftmost child of parent...
			if(thisNode.equals(parent.getChild(0)))
			{
				//shift current middle child to the right
				parent.connectChild(2, parent.disconnectChild(1));
				//and put the newRightNode as middle child
				parent.connectChild(1, newRightNode);
			}
			//otherwise just attach new Right node as rightmost child of parent
			else
				parent.connectChild(2, newRightNode);
		}
		
		return newRightNode;
	}//end split()

	public Node23 getNextChild(Node23 theNode, long theValue)
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
	
	private void recDisplayTree(Node23 thisNode, int level, int childNumber)
	{
		System.out.print("level="+level+" child="+childNumber+" ");
		thisNode.displayNode();
		
		//call ourselves for each child of this node
		int numItems = thisNode.getNumItems();
		for(int j = 0; j < numItems+1; j++)
		{
			Node23 nextNode = thisNode.getChild(j);
			if(nextNode != null)
				recDisplayTree(nextNode, level+1, j);
			else
				return;
		}
	}
} //end class tree23

class Tree23App
{	
	public static void main(String[] args) throws IOException
	{
		long value;
		Tree23 theTree = new Tree23();
		
		theTree.insert(50);
		theTree.insert(40);
		theTree.insert(60);
		theTree.insert(30);
		theTree.insert(70);
		
		while(true)
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, or find: ");
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
} //end class Tree23App




















