//Chapter 7, Exercise 2: Modify the quickSort2.java program (Listing 7.4)
//to count the number of copies and comparisons it makes during a sort
//and then display the totals.

class QuickSortCount
{
	protected long[] theArray;
	protected int nElems;
	protected static long copies;
	protected static long comparisons;
	
	public QuickSortCount(int maxSize)
	{
		theArray = new long[maxSize];
		nElems = 0;
		copies = 0;
		comparisons = 0;
	}
	
	public void insert(long value)
	{
		theArray[nElems++] = value;
	}
	
	public void display()
	{
		for(int i = 0; i < nElems; i++)
		{
			if(i % 100 == 0) System.out.println("");
			System.out.print(theArray[i] + " ");
		}
			
		System.out.println("");
	}
	
	public void quickSort2()
	{
		copies = comparisons = 0;
		recQuickSort2(0, nElems - 1);
		System.out.println("Copies: " + copies);
		System.out.println("Comparisons: " + comparisons);
	}
	
	//modified to count the number of copies and comparisons
	public void recQuickSort2(int left, int right)
	{
		int size = right-left+1;
		if(size <=3)
		{
			comparisons++;
			manualSort(left, right);
		}
		else
		{
			comparisons++;
			long median = medianOf3(left, right);
			int partition = partitionIt2(left, right, median);
			recQuickSort2(left, partition-1);
			recQuickSort2(partition+1, right);
		}
		
	}
	
	public int partitionIt2(int left, int right, long pivot)
	{
		int leftPtr = left;
		int rightPtr = right-1;
		
		while(true)
		{
			while(theArray[++leftPtr] < pivot) comparisons++; //nop
			while(theArray[--rightPtr] > pivot) comparisons++; //nop
			
			if(leftPtr >= rightPtr)
			{
				comparisons++;
				break;
			}
			else
			{
				comparisons++;
				swap(leftPtr, rightPtr);
			}
		}
		swap(leftPtr, right-1);
		return leftPtr;
	}
	
	public void swap(int index1, int index2)
	{
		long temp = theArray[index1];
		theArray[index1] = theArray[index2];
		theArray[index2] = temp;
		copies += 3;
	}
	
	public long medianOf3(int left, int right)
	{
		int center = (left+right)/2;
		
		if(theArray[left] > theArray[center]) swap(left, center);
		if(theArray[left] > theArray[right]) swap(left, right);
		if(theArray[center] > theArray[right]) swap(center, right);
		comparisons += 3;
		
		swap(center, right-1); //put pivot on right
		return theArray[right-1]; //return median value
	}
	
	public void manualSort(int left, int right)
	{
		int size = right-left+1;
		if(size <= 1)
		{
			comparisons++;
			return;
		}
		comparisons++;
		if(size == 2)
		{
			comparisons++;
			if(theArray[left] > theArray[right]) swap(left, right);
			comparisons++;
			return;
		}
		else
		{
			comparisons++;
			if(theArray[left] > theArray[right-1]) swap(left, right-1);
			if(theArray[left] > theArray[right]) swap(left, right);
			if(theArray[right-1] > theArray[right]) swap(right-1, right);
			comparisons += 3;
		}
		
	}
	
	public static void main(String[] args)
	{
		QuickSortCount arr = new QuickSortCount(100);
		for(int i = 0; i < 100; i++) arr.insert(100-i);
		arr.quickSort2();
	}
}