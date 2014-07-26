import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Chapter 12, Exercise 3: PriorityQueue implemented with 
//ch12listing1Heap.java

class HeapPriorityQ
{
	Heap theHeap;
	
	public HeapPriorityQ(int size)
	{
		theHeap = new Heap(size);
	}
	
	public boolean insert(int value)
	{
		return theHeap.insert(value);
	}
	
	public int remove()
	{
		int temp = theHeap.remove().getKey();
		return temp;
	}
	
	public boolean isEmpty()
	{
		return theHeap.isEmpty();
	}
	
	public void display()
	{
		theHeap.displayHeap();
	}
}

class HeapQueueApp
{
	public static void main(String[] args) throws IOException
	{
		int value;
		HeapPriorityQ theQueue = new HeapPriorityQ(31);
		boolean success;
		
		theQueue.insert(70);
		theQueue.insert(40);
		theQueue.insert(50);
		theQueue.insert(20);
		theQueue.insert(60);
		theQueue.insert(100);
		theQueue.insert(80);
		theQueue.insert(30);
		theQueue.insert(10);
		theQueue.insert(90);
		
		while(true)
		{
			System.out.print("Enter first letter of ");
			System.out.print("show, insert, remove: ");
			int choice = getChar();
			switch(choice)
			{
			case 's':
				theQueue.display();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				success = theQueue.insert(value);
				if (!success)
					System.out.println("Can't insert; queue full");
				break;
			case 'r':
				if(!theQueue.isEmpty())
					theQueue.remove();
				else
					System.out.println("Can't remove; queue empty");
				break;
			default:
				System.out.println("Invalid entry!\n");
			} //end switch
		} //end while
	} //end main
	
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
} //end class HeapQueueApp