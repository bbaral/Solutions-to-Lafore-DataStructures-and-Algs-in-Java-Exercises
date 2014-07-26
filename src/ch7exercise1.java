//Chapter 7, exercise 1: Modify the partition.java program (Listing 7.2)
//so that the partitionIt() method always uses the highest-index (right)
//element as the pivot, rather than an arbitrary number. Make sure your
//routine will work for arrays of three or fewer elements.

class ArrayPar extends ArrayIns
{
	public ArrayPar(int max)
	{
		super(max);
	}
	
	public int size() {return nElems;}
	
	public int partitionIt3(int left, int right)
	{
		int leftPtr = left - 1;
		int rightPtr = right;
		if(rightPtr - leftPtr <= 0)
		{
			System.out.println("Sub-array too small to sort");
			return -1;
		}
		long pivot = theArray[right];
		System.out.println("Pivot = " + pivot);
		
		
		while(true)
		{
			while(leftPtr < right && theArray[++leftPtr] < pivot)
				; //nop
			while(rightPtr > left && theArray[--rightPtr] > pivot)
				; //nop
			if(leftPtr >= rightPtr) break;
			else swap(leftPtr, rightPtr);
		}
		swap(leftPtr, right); //move pivot to partition
		return leftPtr;
	}
	
	public static void main(String[] args)
	{
		int maxSize = 51;
		ArrayPar arr = new ArrayPar(maxSize);
		for(int i = 0; i < maxSize; i++) arr.insert((long)(Math.random()*199));
		arr.display();
		arr.partitionIt3(0, maxSize-1);
		arr.display();
	}
}