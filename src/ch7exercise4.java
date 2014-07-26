//Chapter 7, Exercise 4

/* Selection means finding the kth largest or kth smallest element from an array.
For example, you might want to select the 7th largest element. Finding the
median (as in Programming Project 7.2) is a special case of selection. The same
partitioning process can be used, but you look for an element with a specified
index number rather than the middle element. Modify the program from
Programming Project 7.2 to allow the selection of an arbitrary element. */

class Selection extends ArrayIns
{
	protected int middleCell;
	
	public Selection(int max)
	{
		super(max);
		middleCell = 0;
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
	
	//finds kth largest number
	public long findIndex(int left, int right, int index)
	{
		if(right-left <= 0) return theArray[index];
		else
		{
			int partition = partitionIt3(left, right);
			if(partition == index) return theArray[index];
			else if(partition < index) return findIndex(partition+1, right, index);
			else return findIndex(left, partition-1, index);
		}
	}
	
	public static void main(String[] args)
	{
		Selection arr = new Selection(31);
		for(int i = 0; i < 20; i++) arr.insert((long)(Math.random()*100));
		
		arr.display();
		int k = 1;
		long selection = arr.findIndex(0, arr.size()-1, k);
		arr.display();
		System.out.println((k+1) + "th smallest number: " + selection);
	}
}