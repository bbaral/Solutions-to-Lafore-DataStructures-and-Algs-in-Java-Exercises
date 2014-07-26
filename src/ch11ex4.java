//Chapter 11, Exercise 4

/* Write a rehash() method for the hash.java program. It should be called by
insert() to move the entire hash table to an array about twice as large whenever
the load factor exceeds 0.5. The new array size should be a prime number.
Refer to the section "Expanding the Array" in this chapter. Don’t forget you’ll
need to handle items that have been “deleted,” that is, written over with –1. */

import java.io.*;

class HashDataItem
{
	private int iData;
	public HashDataItem(int ii)
		{ iData = ii; }
	
	public int getKey()
		{ return iData; }
}

class HashTable
{
	private HashDataItem[] hashArray;
	private int arraySize;
	private HashDataItem nonItem;
	private double numItems;	//used to determine loadFactor
	
	public HashTable(int size)
	{
		numItems = 0;
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
	
	public double getLoadFactor()
	{ return (numItems / (double)arraySize); }
	
	public HashTable rehash()
	{
		//create a new table based on the original one
		int newSize = getPrime(arraySize*2);
		HashTable newTable = new HashTable(newSize);
		newTable.setNumItems(numItems); //transfer numItems from oldtable to newtable
		
		//for all the values in the old hash table, re-insert them into the new table.
		for(int j = 0; j < arraySize; j++)
		{
			if(hashArray[j] != null && hashArray[j].getKey() != -1)
				newTable.rehashInsert(hashArray[j], newSize);
		}
		
		
		//then return that table
		return newTable;
	}
	
	private int getPrime(int min)	//returns 1st prime > min
	{
		for(int j = min+1; true; j++)
		  if( isPrime(j) )
			  return j;
	}
	
	private boolean isPrime(int n)
	{
		for(int j = 2; (j*j <= n); j++)
			if(n%j == 0)
				return false;
		return true;
	}
	
	public int hashFunc(int key)
	{
		return key % arraySize;
	}
	
	//used by rehash() to calculate new locations for keys
	private void rehashInsert(HashDataItem item, int size)
	{
		//assumes table not full
		int key = item.getKey();
		int hashVal = key % size;
		while(hashArray[hashVal] != null && hashArray[hashVal].getKey() != -1)
		{
			++hashVal;
			hashVal %= arraySize;
		}
		hashArray[hashVal] = item;
	}
	
	private void setNumItems(double number)
	{ numItems = number; }
	
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
		numItems++;
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
				numItems--;
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

class HashTableApp
{
	public static void main(String[] args) throws IOException
	{
		HashDataItem aDataItem;
		int aKey, size, n, keysPerCell;
		double loadFactor;
		
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
			//If load factor is greater than 0.5, rehash
			loadFactor = theHashTable.getLoadFactor();
			if(loadFactor >= 0.5)
			{
				System.out.println("Table getting full... rehashing...");
				theHashTable = theHashTable.rehash();
			}
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















