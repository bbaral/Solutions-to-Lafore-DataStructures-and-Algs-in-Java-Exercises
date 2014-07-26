class ArrayIns
{
	protected long[] theArray;
	protected int nElems;
	
	public ArrayIns(int maxSize)
	{
		theArray = new long[maxSize];
		nElems = 0;
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
	
	public void quickSort()
	{
		recQuickSort(0, nElems - 1);
	}
	
	public void quickSort2()
	{
		recQuickSort2(0, nElems - 1);
	}
	
	public void recQuickSort(int left, int right)
	{
		if(right-left <= 0)
			return;
		else
		{
			long pivot = theArray[right];
			int partition = partitionIt(left, right, pivot);
			recQuickSort(left, partition-1);
			recQuickSort(partition+1, right);
		}
	}
	
	public void recQuickSort2(int left, int right)
	{
		int size = right-left+1;
		if(size <=3)
			manualSort(left, right);
		else
		{
			long median = medianOf3(left, right);
			int partition = partitionIt2(left, right, median);
			recQuickSort2(left, partition-1);
			recQuickSort2(partition+1, right);
		}
	}
	
	public int partitionIt(int left, int right, long pivot)
	{
		int leftPtr = left-1;
		int rightPtr = right;
		while(true)
		{
			while(theArray[++leftPtr] < pivot) ; //nop
			while(rightPtr > 0 && theArray[--rightPtr] > pivot) ; //nop
			
			if(leftPtr >= rightPtr) break;
			else swap(leftPtr, rightPtr);
		}
		swap(leftPtr, right);
		return leftPtr;
	}
	
	public int partitionIt2(int left, int right, long pivot)
	{
		int leftPtr = left;
		int rightPtr = right-1;
		
		while(true)
		{
			while(theArray[++leftPtr] < pivot) ; //nop
			while(theArray[--rightPtr] > pivot) ; //nop
			
			if(leftPtr >= rightPtr) break;
			else swap(leftPtr, rightPtr);
		}
		swap(leftPtr, right-1);
		return leftPtr;
	}
	
	public void swap(int index1, int index2)
	{
		long temp = theArray[index1];
		theArray[index1] = theArray[index2];
		theArray[index2] = temp;
	}
	
	public long medianOf3(int left, int right)
	{
		int center = (left+right)/2;
		
		if(theArray[left] > theArray[center]) swap(left, center);
		if(theArray[left] > theArray[right]) swap(left, right);
		if(theArray[center] > theArray[right]) swap(center, right);
		
		swap(center, right-1); //put pivot on right
		return theArray[right-1]; //return median value
	}
	
	public void manualSort(int left, int right)
	{
		int size = right-left+1;
		if(size <= 1) return;
		if(size == 2)
		{
			if(theArray[left] > theArray[right]) swap(left, right);
			return;
		}
		else
		{
			if(theArray[left] > theArray[right-1]) swap(left, right-1);
			if(theArray[left] > theArray[right]) swap(left, right);
			if(theArray[right-1] > theArray[right]) swap(right-1, right);
		}
	}
}

class QuickSort1App
{
	public static void main(String[] args)
	{
		int maxSize = 1650;
		ArrayIns arr = new ArrayIns(maxSize);
		
		for(int i = 0; i < maxSize; i++)
			arr.insert((long)(Math.random()*199));
		
		arr.display();
		arr.quickSort();
		arr.display();
	}
}

class QuickSort2App
{
	public static void main(String[] args)
	{
		int maxSize = 165;
		ArrayIns arr = new ArrayIns(maxSize);
		
		for(int i = 0; i < maxSize; i++)
			arr.insert((long)(Math.random()*199));
		
		arr.display();
		arr.quickSort2();
		arr.display();
	}
}