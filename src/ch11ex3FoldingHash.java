//Chapter 11, Exercise 3: Write a hash function to implement a 
//digit-folding approach in the hash function (as described in the 
//"Hash Functions" section of this chapter). Your program should 
//work for any array size and any key length. Use linear probing.

import java.io.*;

class FoldingHashTable
{
	private HashDataItem[] hashArray;
	private int arraySize;
	private HashDataItem nonItem;
	
	public FoldingHashTable(int size)
	{
		arraySize = size;
		hashArray = new HashDataItem[arraySize];
		nonItem = new HashDataItem(-1); //deleted item key is -1
	}
	
	public void displayTable()
	{
		System.out.print("Table: ");
		for(int j = 0; j < arraySize; j++)
		{
			if(hashArray[j] != null)
				System.out.print(hashArray[j].getKey() + " ");
			else
				System.out.print("** ");
		}
		System.out.println("");
	}
	
	//folds the key into groups of digits and adds the groups
	public int hashFunc(int key)
	{
		int groupSize = 1;
		int temp = arraySize;
		int hashVal = 0;
		
		while(temp > 0)
		{
			temp /= 10;
			groupSize *= 10;
		}
		
		while(key > 0)
		{
			hashVal += key % groupSize;
			key /= groupSize;
		}
		
		return hashVal;
	}
	
	public void insert(HashDataItem item)
	{
		//assumes table not full
		int key = item.getKey();
		int hashVal = hashFunc(key);
		while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1)
		{
			++hashVal;
			hashVal %= arraySize;
		}
		hashArray[hashVal] = item;
	}
	
	public HashDataItem delete(int key)
	{
		int hashVal = hashFunc(key);
		
		while(hashArray[hashVal] != null)
		{
			if(hashArray[hashVal].getKey() == key)
			{
				HashDataItem temp = hashArray[hashVal];
				hashArray[hashVal] = nonItem;
				return temp;
			}
			++hashVal;
			hashVal %= arraySize;
		}
		return null;
	}
	
	public HashDataItem find(int key)
	{
		int hashVal = hashFunc(key);
		while(hashArray[hashVal] != null)
		{
			if(hashArray[hashVal].getKey() == key)
				return hashArray[hashVal];
			++hashVal;
			hashVal %= arraySize;
		}
		return null;
	}
} //end class HashTable

class FoldingHashTableApp
{
	public static void main(String[] args) throws IOException
	{
		HashDataItem aDataItem;
		int aKey, size, n, keysPerCell;
		
		System.out.print("Enter size of hash table: ");
		size = getInt();
		System.out.print("Enter initial number of items: ");
		n = getInt();
		keysPerCell = 10;
		
		HashTable theHashTable = new HashTable(size);
		
		for(int j=0; j<n; j++)
		{
			aKey = (int)(java.lang.Math.random() * keysPerCell * size);
			aDataItem = new HashDataItem(aKey);
			theHashTable.insert(aDataItem);
		}
		
		while(true)
		{
			System.out.print("Enter first letter of show, insert, delete, or find: ");
			char choice = getChar();
			switch(choice)
			{
			case 's':
				theHashTable.displayTable();
				break;
			case 'i':
				System.out.print("Enter key value to insert: ");
				aKey = getInt();
				aDataItem = new HashDataItem(aKey);
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
				System.out.println("Invalid entry!");
			}
		}
	}//end main
	
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
} //end HashTableApp















