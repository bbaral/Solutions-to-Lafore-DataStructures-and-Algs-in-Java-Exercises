//Chapter 7, Exercise 5: Implement a radix sort as described 
//in the last section of this chapter. It should handle variable 
//amounts of data and variable numbers of digits in the key.

class RadixSort
{
	private DequeLL[] theList;
	private int base;
	
	public RadixSort()
	{
		base = 10;
		theList = new DequeLL[base];
		for(int i = 0; i < base; i++)
			theList[i] = new DequeLL();
	}
	
	
	
	public void sortIt(long[] arr, int numDigits)
	{
		for(int i = 0; i < numDigits; i++)
		{
			//copy array into different linked lists
			for(int j = 0; j < arr.length; j++)
			{
				//sort the element by the current digit
				long temp = arr[j];
				for(int k = 0; k < i; k++) temp /= base;
				temp %= base;
				theList[(int)temp].insertLeft(arr[j]);
			}
			
			//copy linked lists back into array
			int arrayCounter = 0;
			for(int m = 0; m < base; m++)
			{
				
				while(!theList[m].isEmpty()) 
				{
					arr[arrayCounter] = theList[m].removeRight();
					arrayCounter++;
				}
			}
		}
	}
	
	public static void main(String[] args)
	{
		int size = 15;
		long[] arr = new long[size];
		for(int i = 0; i < size; i++)
			arr[i] = (long)(Math.random()*199)+100;
		for(int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println("");
		RadixSort sorter = new RadixSort();
		sorter.sortIt(arr, 3);
		for(int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println("");
	}
	
	
}