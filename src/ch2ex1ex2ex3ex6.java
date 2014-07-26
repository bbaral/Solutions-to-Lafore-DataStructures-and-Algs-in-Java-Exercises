//Chapter 2, Exercise 1: To the HighArray class in the highArray.java
//program (Listing 2.3), add a method called getMax() that returns the
//value of the highest key in the array, or -1 if the array is empty.
//You can assume all the keys are positive numbers.

//Chapter 2, Exercise 2: Modify the method getMax() so that the item with
//the highest key is not only returned by the method, but also removed
//from the array. Call the method removeMax().

//Chapter 2, Exercise 3: The removeMax() method suggests a way to sort
//the contents of the array by key value. Implement a sorting scheme that
//does not require modifying the HighArray class, but only the code in main().

//Chapter 2, Exercise 6: Write a noDups() method. This method should remove
//all duplicates from the array. That is, if three items with the key 17 appear
//in the array, noDups() should remove two of them. Don't worry about maintaining
//the order of the items.

import java.lang.Math;

class HighArray
{
	private long[] a;
	private int nElems;
	
	public HighArray(int max)
	{
		a = new long[max];
		nElems = 0;
	}
	
	public int getNumElems()
	{
		return nElems;
	}
	
	public boolean find(long searchKey)
	{
		int j;
		for(j=0; j<nElems; j++)
		{
			if(a[j]==searchKey) break;
		}
		if(j==nElems) return false;
		else return true;
	}
	
	public void insert(long value)
	{
		a[nElems] = value;
		nElems++;
	}
	
	public long delete(long value)
	{
		int j;
		for(j=0; j<nElems; j++)
		{
			if(a[j]==value) break;
		}
		if(j == nElems) return 0;
		else
		{
			int k;
			for(k=j; k < (nElems-1); k++)
			{
				a[k] = a[k+1];
			}
			nElems--;
			return value;
		}
	}
	
	public long getMax()
	{
		int j;
		long biggest = -1;
		if(nElems == 0) return biggest;
		else
		{
			for(j=0; j<nElems; j++)
			{
				if(a[j] > biggest) biggest = a[j];
			}
			return biggest;
		}
	}
	
	public long removeMax()	//Finds the biggest entry and deletes it
	{
		int j;
		long biggest = -1;
		if(nElems == 0) return biggest;
		else
		{
			for(j=0; j<nElems; j++)
			{
				if(a[j] > biggest) biggest = a[j];
			}
			delete(biggest);
			return biggest;
		}
	}
	
	public void noDups()
	{
		//This version shrinks the array as it goes along
		int size = nElems - 1;
		long value = 0;
		for(int i = 0; i <= size; i++)
		{
			value = a[i];
			for(int j = i+1; j <= size; j++)
			{
				if(a[j] == value)
				{
					for(int k = j; k < size; k++)
					{
						a[k] = a[k+1];
					}
					nElems--;
					j--;
					size--;
					System.out.println("Deleted " + value);
				}
			}
		}
	}
	
	public void display()
	{
		for(int j=0; j<nElems; j++)
		{
			System.out.print(a[j] + " ");
		}
		System.out.println("");
	}
}


class HighArrayApp
{
	public static void main(String[] args)
	{
		int maxSize = 20;
		HighArray a = new HighArray(maxSize);
		for(int i=0; i < (maxSize-1); i++)
		{
			long random = (long) (Math.random()*200);
			a.insert(random);
		}
		
		a.display();
		System.out.println("Biggest value is " + a.removeMax());
		a.display();
		
		System.out.println("Going to sort the array backwards...");
		int length = a.getNumElems();
		HighArray b = new HighArray(maxSize);
		for(int j = 0; j < length; j++)
		{
			long item = a.removeMax();
			b.insert(item);
		}
		b.display();
		
		HighArray c = new HighArray(maxSize*2);
		for(int i=0; i < ((maxSize*2)-3); i++)
		{
			long random = (long) (Math.random()*200);
			c.insert(random);
		}
		c.insert(70);
		c.insert(70);
		c.insert(70);
		System.out.println("Demonstrating noDups function:");
		c.display();
		c.noDups();
		c.display();
		
	}
}