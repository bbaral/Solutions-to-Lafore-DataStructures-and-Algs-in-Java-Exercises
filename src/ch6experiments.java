/* Rewrite the main() part of mergeSort.java (Listing 6.6) so you can fill the array
with hundreds of thousands of random numbers. Run the program to sort
these numbers and compare its speed with the sorts in Chapter 3, "Simple
Sorting." */

import java.io.*;
import java.lang.Math;

class TriangleApp
{
	static int theNumber;
	
	public static void main(String[] args) throws IOException
	{
		System.out.print("Enter a number: ");
		theNumber = getInt();
		int theAnswer = triangle(theNumber);
		System.out.println("Triangle="+theAnswer);
	}
	
	public static int triangle(int n)
	{
		if(n==1)
			return 1;
		else
			return (n + triangle(n-1));
	}
	
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
}

class DArray
{
	private long[] theArray;
	private int nElems;
	public DArray(int max)
	{
		theArray = new long[max];
		nElems = 0;
	}
	
	public void insert(long value)
	{
		theArray[nElems] = value;
		nElems++;
	}
	
	public void display()
	{
		for(int j = 0; j < nElems; j++)
			System.out.print(theArray[j] + " ");
		System.out.println("");
	}
	
	public void mergeSort()
	{
		long[] workspace = new long[nElems];
		recMergeSort(workspace, 0, nElems-1);
	}
	
	private void recMergeSort(long[] workspace, int lowerBound, int upperBound)
	{
		if(lowerBound == upperBound)
			return;
		else
		{
			int mid = (lowerBound+upperBound) / 2;
			recMergeSort(workspace, lowerBound, mid);
			recMergeSort(workspace, mid+1, upperBound);
			merge(workspace, lowerBound, mid+1, upperBound);
		}
	}
	
	private void merge(long[] workspace, int lowPtr, int highPtr, int upperBound)
	{
		int j = 0;
		int lowerBound = lowPtr;
		int mid = highPtr - 1;
		int n = upperBound-lowerBound+1;
		
		while(lowPtr <= mid && highPtr <= upperBound)
			if(theArray[lowPtr] < theArray[highPtr])
				workspace[j++] = theArray[lowPtr++];
			else
				workspace[j++] = theArray[highPtr++];
				
		while(lowPtr <= mid)
			workspace[j++] = workspace[lowPtr++];
		
		while(highPtr <= upperBound)
			workspace[j++] = workspace[highPtr++];
		
		for(j=0; j<n; j++)
			theArray[lowerBound+j] = workspace[j];
	}
}

class MergeSortApp
{
	public static void main(String[] args)
	{
		int maxSize = 10000;
		DArray arr = new DArray(maxSize);
		for(int i = 0; i < maxSize; i++)
		{
			arr.insert((long) Math.random()*maxSize);
		}
		arr.display();
		long startTime = System.nanoTime();
		arr.mergeSort();
		long endTime = System.nanoTime();
		long elapsed = endTime - startTime;
		arr.display();
		System.out.println("Time elapsed: " + elapsed);
	}
}