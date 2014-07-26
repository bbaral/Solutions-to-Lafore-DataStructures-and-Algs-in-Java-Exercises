import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//Chapter 12, Exercise 4: Implements a priority-queue with a binary search tree
//instead of a heap. Uses ch8listing1.java + added function removeMax()

class TreePriorityQ
{
	Tree theTree;
	
	public TreePriorityQ()
	{
		theTree = new Tree();
	}
	
	public void insert(int value)
	{
		theTree.insert(value, 0); //no use for dData in this exercise
	}
	
	public Node remove()
	{
		return theTree.removeMax();
	}
	
	public void change(int oldValue, int newValue)
	{	
		if(theTree.delete(oldValue))
			theTree.insert(newValue, 0);
		else
			System.out.println("Value does not exist!");
	}
	
	public boolean isEmpty()
	{
		return theTree.isEmpty();
	}
	
	public void display()
	{
		theTree.traverse(2);
	}
} //end class TreePriorityQ

class TreeQueueApp
{
	public static void main(String[] args) throws IOException
	{
		int value, value2;
		TreePriorityQ theQueue = new TreePriorityQ();
		
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
			System.out.print("show, insert, remove, change: ");
			int choice = getChar();
			switch(choice)
			{
			case 's':
				theQueue.display();
				break;
			case 'i':
				System.out.print("Enter value to insert: ");
				value = getInt();
				theQueue.insert(value);
				break;
			case 'r':
				if(!theQueue.isEmpty())
					theQueue.remove();
				else
					System.out.println("Can't remove; queue empty");
				break;
			case 'c':
				System.out.print("Enter value to change: ");
				value = getInt();
				System.out.print("Enter new value: ");
				value2 = getInt();
				theQueue.change(value, value2);
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
} //end class TreeQueueApp