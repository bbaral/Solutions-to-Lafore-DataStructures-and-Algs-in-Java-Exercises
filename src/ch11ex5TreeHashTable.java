//Chapter 11, Exercise 5: Instead of a separate-chaining hash table made
//with linked lists, use an array of binary search trees.
//Don't allow duplicate keys, and deletion can be left out.

//uses the binary search tree from ch8listing1.java

import java.io.*;




class TreeHashTable
{
	private Tree[] hashArray;
	private int arraySize;
	
	public TreeHashTable(int size)
	{
		arraySize = size;
		hashArray = new Tree[arraySize];
		for(int j = 0; j < arraySize; j++)
			hashArray[j] = new Tree();
	}
	
	public void displayTable()
	{
		for(int j = 0; j < arraySize; j++)
		{
			System.out.print(j + ". ");
			hashArray[j].traverse(2);
		}
	}
	
	public int hashFunc(int key)
	{ return key % arraySize; }
	
	public void insert(Node theNode)
	{
		int key = theNode.iData;
		int hashVal = hashFunc(key);
		if(find(key) != null)
		{
			System.out.println("Value already exists in table.");
			return;
		}
		else
			hashArray[hashVal].insert(theNode.iData, theNode.dData);
	}
	
	public void delete(int key)
	{
		int hashVal = hashFunc(key);
		if(!hashArray[hashVal].isEmpty()) hashArray[hashVal].delete(key);
	}
	
	public Node find(int key)
	{
		int hashVal = hashFunc(key);
		if(!hashArray[hashVal].isEmpty())
		{	
			Node theNode = hashArray[hashVal].find(key);
			return theNode;
		}
		else
			return null;
	}
}

class HashTreeApp
{
	public static void main(String[] args) throws IOException
	{
		int aKey;
		Node aDataItem;
		int size, n, keysPerCell = 100;
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		
		TreeHashTable theHashTable = new TreeHashTable(size);
		
		for(int j = 0; j<n; j++)
		{
			aKey = (int)(java.lang.Math.random() * keysPerCell * size);
			aDataItem = new Node();
			aDataItem.iData = aKey;
			theHashTable.insert(aDataItem);
		}
		
		while(true)
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, delete, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getInt();
				aDataItem = new Node();
				aDataItem.iData = aKey;
				theHashTable.insert(aDataItem);
				break;
			case 'd':
				System.out.print("Enter key value to delete: ");
				aKey = getInt();
				theHashTable.delete(aKey);
				break;
			case 'f':
				System.out.print("Enter key value to find: ");
				aKey = getInt();
				aDataItem = theHashTable.find(aKey);
				if(aDataItem != null)
					System.out.println("Found " + aKey);
				else
					System.out.println("Could not find " + aKey);
				break;
			default:
				System.out.println("Invalid Entry!");
			}
		}
	}
	
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
}