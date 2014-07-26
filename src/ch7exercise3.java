//Chapter 7, Exercise 3

/* In Exercise 3.2 in Chapter 3, we suggested that you could find the median of a
set of data by sorting the data and picking the middle element. You might
think using quicksort and picking the middle element would be the fastest way
to find the median, but there’s an even faster way. It uses the partition algorithm
to find the median without completely sorting the data. 

To see how this works, imagine that you partition the data, and, by chance, the
pivot happens to end up at the middle element. You’re done! All the items to
the right of the pivot are larger (or equal), and all the items to the left are
smaller (or equal), so if the pivot falls in the exact center of the array, then it’s
the median. The pivot won’t end up in the center very often, but we can fix
that by repartitioning the partition that contains the middle element.

Suppose your array has seven elements numbered from 0 to 6. The middle is
element 3. If you partition this array and the pivot ends up at 4, then you need
to partition again from 0 to 4 (the partition that contains 3), not 5 to 6. If the
pivot ends up at 2, you need to partition from 2 to 6, not 0 to 1. You continue
partitioning the appropriate partitions recursively, always checking if the pivot
falls on the middle element. Eventually, it will, and you’re done. Because you
need fewer partitions than in quicksort, this algorithm is faster.

Extend Programming Project 7.1 to find the median of an array. You’ll make
recursive calls somewhat like those in quicksort, but they will only partition
each subarray, not completely sort it. The process stops when the median is
found, not when the array is sorted. */

class Median extends ArrayIns
{
	protected int middleCell;
	
	public Median(int max)
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
	
	public long findMedian(int left, int right)
	{
		if(right-left <= 0) return theArray[middleCell];
		else
		{
			int partition = partitionIt3(left, right);
			if(partition == middleCell) return theArray[middleCell];
			else if(partition < middleCell) return findMedian(partition+1, right);
			else return findMedian(left, partition-1);
		}
	}
	
	public static void main(String[] args)
	{
		Median arr = new Median(31);
		for(int i = 0; i < 31; i++) arr.insert((long)(Math.random()*100));
		
		arr.display();
		arr.middleCell = arr.size()/2;
		long median = arr.findMedian(0, arr.size()-1);
		arr.display();
		System.out.println("Median: " + median);
	}
}