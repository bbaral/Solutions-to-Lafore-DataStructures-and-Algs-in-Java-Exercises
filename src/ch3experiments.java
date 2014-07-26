// bubbleSort.java
// demonstrates bubble sort
// to run this program: C>java BubbleSortApp
////////////////////////////////////////////////////////////////
class ArrayBub
{
	private long[] a; // ref to array a
	private int nElems; // number of data items
	//--------------------------------------------------------------
	public ArrayBub(int max) // constructor
	{
		a = new long[max]; // create the array
		nElems = 0; // no items yet
	}
	//--------------------------------------------------------------
	public long valueAt(int index)
	{
		return a[index];
	}
	//--------------------------------------------------------------
	public void insert(long value) // put element into array
	{
		a[nElems] = value; // insert it
		nElems++; // increment size
	}
	//--------------------------------------------------------------
	public void display() // displays array contents
	{
		for(int j=0; j<nElems; j++) // for each element,
			System.out.print(a[j] + " "); // display it
		System.out.println("");
	}
	//--------------------------------------------------------------
	public void bubbleSort()
	{
		int out, in;
		for(out=nElems-1; out>1; out--) // outer loop (backward)
			for(in=0; in<out; in++) // inner loop (forward)
				if( a[in] > a[in+1] ) // out of order?
					swap(in, in+1); // swap them
	} // end bubbleSort()
	//--------------------------------------------------------------
	public void biDirBubbleSort()	//bubble sort with two "out" counters
	{
		int outBottom, outTop, in;
		outBottom = 0;
		outTop = nElems-1;
		while(outBottom < outTop)
		{
			for(in = outBottom; in < outTop; in++)
				if(a[in] > a[in+1]) swap(in, in+1);
			outTop--;
			for(in = outTop; in > outBottom; in--)
				if(a[in-1] > a[in]) swap(in, in-1);
			outBottom++;
		}
	} // end biDirBubbleSort()
	//--------------------------------------------------------------
	public void selectionSort()
	{
		int out, in, min;
		for(out=0; out<nElems-1; out++) // outer loop
		{
			min = out; // minimum
			for(in=out+1; in<nElems; in++) // inner loop
				if(a[in] < a[min] ) // if min greater,
					min = in; // we have a new min
			swap(out, min); // swap them
		} // end for(out)
	} // end selectionSort()
	private void swap(int one, int two)
	{
		long temp = a[one];
		a[one] = a[two];
		a[two] = temp;
	}
	//--------------------------------------------------------------
	public void insertionSort()
	{
		int in, out;
		int copies = 0, comparisons = 0;
		for(out = 1; out < nElems; out++)
		{
			long temp = a[out];
			copies++;
			in = out;
			while(in > 0 && a[in-1] >= temp)
			{
				a[in] = a[in-1];
				copies++;
				if(in == 1) comparisons++;
				comparisons++;
				in--;
			}
			a[in] = temp;
			copies++;
		}
		System.out.println("Copies: " + copies);
		System.out.println("Comparisons: " + comparisons);
	}
	//--------------------------------------------------------------
	public void insertionSortNoDups()	//Kind of the same as NoDups()?
	{
		int in, out;
		int numDups = 0;
		for(out = 1; out < nElems; out++)
		{
			long temp = a[out];
			in = out;
			while(in > 0 && a[in-1] >= temp)
			{
				if(a[in-1] == temp && temp > -1) 
				{
					temp = -1;
					numDups++;
				}	
				a[in] = a[in-1];
				in--;
			}
			a[in] = temp;
		}
		System.out.println(numDups);
		display();
		int totalElems = nElems - numDups;
		for(int i = 0; i < totalElems; i++)
		{
			a[i] = a[i+numDups];
		}
		nElems -= numDups;
		display();
		
	}
	//--------------------------------------------------------------
	public long median()
	{
		//Copy array to a temporary array
		ArrayBub b = new ArrayBub(nElems);
		for(int i = 0; i < nElems; i++)
			b.insert(a[i]);
		b.insertionSort();
		if(b.nElems % 2 == 0) //If there are 2 middle numbers, return average of them
		{
			int temp = b.nElems / 2;
			long median = (b.valueAt(temp) + b.valueAt(temp-1) ) / 2;
			return median;
		}
		else return b.valueAt(b.nElems/2);
	}
	//--------------------------------------------------------------
	public void noDups()
	{
		//If the list is sorted, we know all duplicates are adjacent 
		insertionSort();
		
		int total = nElems; //Store nElems because we will be reducing it during the for loop
		int shiftAmount = 0;  //This makes it so we can shift everything in one pass
		long curNum = 0; //This stores the number we are looking for
		for(int index = 0; index < total; index++)
		{
			if(a[index] == curNum)
			{
				shiftAmount++;
				nElems--;
			}
			else
			{
				curNum = a[index];
				a[index-shiftAmount] = a[index];
			}
		}
	}		
	//--------------------------------------------------------------	
} // end class ArrayBub
////////////////////////////////////////////////////////////////
class BubbleSortApp
{
	public static void main(String[] args)
	{
		int maxSize = 30;
		ArrayBub arr = new ArrayBub(maxSize);
		for(int i = 0; i < maxSize; i++)
		{
			if(i%6==0) arr.insert(12);
			else arr.insert(i);
		}
		arr.display();
		arr.insertionSortNoDups();
	} // end main()
} // end class BubbleSortApp
////////////////////////////////////////////////////////////////