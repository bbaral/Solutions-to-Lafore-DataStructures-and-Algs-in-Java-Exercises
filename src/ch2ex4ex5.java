//Chapter 2, Exercise 4: Modify the orderedArray.java program (Listing 2.4)
//so that the insert() and delete() routines, as well as find(), use a binary
//search, as suggested in the text.

//Chapter 2, Exercise 5: Add a merge() method so that you can merge two ordered
//source arrays into an ordered destination array. The source arrays may hold
//different numbers of data items. You'll need to handle the situation when one
//source array exhausts its contents before the other.

class OrdArray
{
	private long[] a; // ref to array a
	private int nElems; // number of data items
	//-----------------------------------------------------------
	public OrdArray(int max) // constructor
	{
		a = new long[max]; // create array
		nElems = 0;
	}
	//-----------------------------------------------------------
	public int size()
	{ return nElems; }
	//-----------------------------------------------------------
	public long getValueAt(int index)
	{
		return a[index];
	}
	//-----------------------------------------------------------
	public void setValueAt(int index, long value)
	{
		a[index] = value;
	}
	//-----------------------------------------------------------
	public int find(long searchKey)
	{
		int lowerBound = 0;
		int upperBound = nElems-1;
		int curIn;
		while(true)
		{
			curIn = (lowerBound + upperBound ) / 2;
			if(a[curIn]==searchKey)
				return curIn; // found it
			else if(lowerBound > upperBound)
				return nElems; // can’t find it
			else // divide range
			{
				if(a[curIn] < searchKey)
					lowerBound = curIn + 1; // it’s in upper half
				else
					upperBound = curIn - 1; // it’s in lower half
			} // end else divide range
		} // end while
	} // end find()
	//-----------------------------------------------------------
	public void insert(long value) // put element into array
	{
		int lowerBound = 0;
		int upperBound = nElems - 1;
		int j = 0;
		while(true)
		{
			if(lowerBound > upperBound) break;
			j = (lowerBound + upperBound) / 2;
			if(value > a[j]) 
			{
				lowerBound = j + 1;
				j++;
			}
			else upperBound = j - 1;
		}
		for(int k=nElems; k>j; k--) // move bigger ones up
			a[k] = a[k-1];
		a[j] = value; // insert it
		nElems++; // increment size
	} // end insert()
	//-----------------------------------------------------------
	public boolean delete(long value)
	{
		int j = find(value);
		if(j==nElems) // can’t find it
			return false;
		else // found it
		{
			for(int k=j; k<nElems; k++) // move bigger ones down
			a[k] = a[k+1];
			nElems--; // decrement size
			return true;
		}
	} // end delete()
	//-----------------------------------------------------------
	public static OrdArray merge(OrdArray a, OrdArray b)
	{
		int length = a.size() + b.size();
		OrdArray c = new OrdArray(length*2); //Times 2 in case you want to insert later?
		int i = 0, j = 0, k = 0;
		while(j < a.size() && k < b.size())
		{
			if(a.getValueAt(j) <= b.getValueAt(k))
			{
				c.setValueAt(i, a.getValueAt(j));
				j++;
			}
			else
			{
				c.setValueAt(i, b.getValueAt(k));
				k++;
			}
			c.nElems++;
			i++;
		}

		while(j < a.size())
		{
			c.setValueAt(i, a.getValueAt(j));
			c.nElems++;
			i++;
			j++;
		}
		
		while(k < b.size())
		{
			c.setValueAt(i, b.getValueAt(k));
			c.nElems++;
			i++;
			k++;
		}
		return c;
		
	}
	//-----------------------------------------------------------
	public void display() // displays array contents
	{
		for(int j=0; j<nElems; j++) // for each element,
		System.out.print(a[j] + " "); // display it
		System.out.println("");
	}
	//-----------------------------------------------------------
} // end class OrdArray
////////////////////////////////////////////////////////////////
class OrderedApp
{
	public static void main(String[] args)
	{
		int maxSize = 100; // array size
		long random = 0;
		OrdArray arr; // reference to array
		arr = new OrdArray(maxSize); // create the array
		for(int i = 0; i < 45; i++)
		{
			random = (long) (Math.random()*200);
			arr.insert(random);
		}
		System.out.print("Array1: ");
		arr.display(); // display items again
		
		OrdArray arr2 = new OrdArray(maxSize);
		for(int i = 0; i < 15; i++)
		{
			random = (long) (Math.random()*200);
			arr2.insert(random);
		}
		
		System.out.print("Array2: ");
		arr2.display();
		
		OrdArray arr3 = OrdArray.merge(arr, arr2);
		System.out.print("Array3: ");
		arr3.display();
	} // end main()
} // end class OrderedApp