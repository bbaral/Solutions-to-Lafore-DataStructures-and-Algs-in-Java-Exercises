//Chapter 4, Exercise 1: Write a method for the Queue class that displays
//the contents of the queue. You should show the queue contents from the
//first item inserted to the last, without indicating to the viewer
//whether the sequence is broken by wrapping around the end of the array.

//Chapter 4, Exercise 2: Create a Deque class based on the discussion of
//deques (double-ended queues) in this chapter. It should include insertLeft(),
//insertRight(), removeLeft(), removeRight(), isEmpty(), and isFull() methods.

//Chapter 4, Exercise 3: Write a program that implements a stack class that
//is based on the Deque class in Exercise 2.

//Chapter 4, Exercise 4: The priority queue shown in Listing 4.6 features fast
//removal of the highest-priority item but slow insertion of new items. Write
//a program with a revised PriorityQ class that has fast O(1) insertion time
//but slower removal of the high-priority item.

//Chapter 4, Exercise 5: Queues are often used to simulate the flow of people, 
//cars, airplanes, transactions, and so on. Write a program that models checkout 
//lines at a supermarket, using the Queue class from the queue.java program 
//(Listing 4.4). Several lines of customers should be displayed; you can use the 
//display() method of Programming Project 4.1. You can add a new customer by 
//pressing a key. You’ll need to determine how the customer will decide which line
//to join. The checkers will take random amounts of time to process each customer 
//(presumably depending on how many groceries the customer has). Once checked out, 
//the customer is removed from the line. For simplicity, you can simulate the passing
//of time by pressing a key. Perhaps every keypress indicates the passage of one
//minute. (Java, of course, has more sophisticated ways to handle time.)

import java.io.*;

class Deque					//exercise 4.2
{
	private int maxSize;
	private long[] arr;
	private int nItems;
	
	private int left;
	private int right;
	
	public Deque(int s)
	{
		maxSize = s;
		arr = new long[maxSize];
		nItems = 0;
		
		left = 1;
		right = 0;
	}
	
	public long peekLeft()
	{
		return arr[left];
	}
	
	public long peekRight()
	{
		return arr[right];
	}	

	public void insertLeft(long value)
	{
		if(!isFull())
		{
			left--;
			if(left < 0) left = maxSize-1;
			arr[left] = value;
			nItems++;
		}
		else System.out.println("The Queue is full!");	
	}

	public long removeLeft()
	{
		if(!isEmpty())
		{
			long temp = arr[left];
			left++;
			if(left >= maxSize) left = 0;
			nItems--;
			return temp;
		}
		else
		{
			System.out.println("The Queue is empty!");
			return -1;
		}
	}
	
	public void insertRight(long value)
	{
		if(!isFull())
		{
			right++;
			if(right >= maxSize) right = 0;
			arr[right] = value;
			nItems++;
		}
		else System.out.println("The Queue is full!");
	}
	
	public long removeRight()
	{
		if(!isEmpty())
		{
			long temp = arr[right];
			right--;
			if(right < 0) right = maxSize-1;
			nItems--;
			return temp;
		}
		else
		{
			System.out.println("The Queue is empty!");
			return -1;
		}
	}
	
	public Boolean isEmpty()
	{
		if(nItems == 0) return true;
		else return false;	
	}	
	
	public Boolean isFull()
	{
		if(nItems == maxSize) return true;
		else return false;
	}
	
	public void display()
	{
		System.out.println("Left: " + left + ", Right: " + right);
		int index = left;
		for(int i = 0; i < nItems; i++)
		{
			if(index >= maxSize) index = 0;
			System.out.print(arr[index] + " ");
			index++;
		}	
		System.out.println("");
	}
}

//basically StackQ is just a wrapper that limits Deque's functionality
class StackQ				//exercise 4.3
{
	private Deque stackQueue;
	
	public StackQ(int size) 
	{
		stackQueue = new Deque(size);
	}
	
	public void push(long j) 
	{
		stackQueue.insertRight(j);	
	}
	
	public long pop()
	{
		return stackQueue.removeRight();
	}
	
	public long peek()
	{
		return stackQueue.peekRight();
	}
	
	public Boolean isEmpty()
	{
		return stackQueue.isEmpty();
	}
	
	public Boolean isFull()
	{
		return stackQueue.isFull();
	}
	
}

//unsorted Priority Queue with fast insertion and slow removal
class PriorityQ					//exercise 4.4
{
	private int maxSize;
	private long[] queArray;
	private int nItems;
	
	public PriorityQ(int s)
	{
		maxSize = s;
		queArray = new long[maxSize];
		nItems = 0;
	}
	
	public void insert(long value)
	{
		if(isFull())
		{
			System.out.println("Queue is full!");
			return;
		}
		else
		{
			queArray[nItems] = value;
			nItems++;
		}	
	}
	
	public long remove()
	{
		if(isEmpty())
		{
			System.out.println("Queue is empty!");
			return -1;
		}
		//find lowest value and store its index
		int minIndex = 0;
		for(int i = 0; i < nItems; i++)
			if(queArray[i] < queArray[minIndex]) minIndex = i;
		
		//store the minimum value for return later
		long temp = queArray[minIndex];
		
		//shift all entries down and decrement nItems
		for(int j = minIndex; j < nItems-1; j++)
		{
			queArray[j] = queArray[j+1];
		}
		nItems--;
		
		return temp;
	}
	
	public long peekMin()	//look through and find lowest number
	{
		int minIndex = 0;
		for(int i = 0; i < nItems; i++)
			if(queArray[i] < queArray[minIndex]) minIndex = i;
		return queArray[minIndex];
	}
	
	public boolean isEmpty()
	{
		return (nItems==0);
	}
	
	public boolean isFull()
	{
		return (nItems==maxSize);
	}
	
	public void display()
	{
		for(int i = 0; i < nItems; i++)
		{
			System.out.print(queArray[i] + " ");
		}
		System.out.println("");
	}
}
	
class Queue
{
	private int maxSize;
	private long[] queArray;
	private int front;
	private int rear;
	private int nItems;
	
	public long runningTally; //in checkout program, use to track how far
							  //the checker is with the customer's order
	public boolean justAdded; //so that when runningTally is 0, new items
							  //aren't deleted right away.
	
	public Queue(int s)
	{
		maxSize = s;
		queArray = new long[maxSize];
		front = 0;
		rear = -1;
		nItems = 0;
		runningTally = 0;
	}
	
	public void insert(long j)
	{
		if(rear == maxSize -1)
			rear = -1;
		queArray[++rear] = j;
		nItems++;
	}
	
	public long remove()
	{
		long temp = queArray[front++];
		if(front == maxSize)
			front = 0;
		nItems--;
		return temp;
	}
	
	public long peekFront()
	{
		return queArray[front];
	}
	
	public long itemAt(int i)
	{
		return queArray[i];
	}

	public boolean isEmpty()
	{
		return(nItems==0);
	}
	
	public boolean isFull()
	{
		return(nItems==maxSize);
	}
	
	public int size()
	{
		return nItems;
	}
	
	public void display()	//exercise 4.1
	{
		int temp = front;
		for(int i = 0; i < nItems; i++)
		{
			System.out.print(queArray[temp] + " ");
			temp++;
			if(temp == maxSize) temp = 0;
		}
		System.out.println("");
	}
}

//exercise 5: Class to contain all the checkout lines of a store and their functions
class Checkout
{
	private int numQueues;
	private int maxQueSize;
	private Queue[] queues;
	private Queue fastLane; //An optional 10-items-or-less line?
	
	private long base; //Time to tender cash/credit expressed as a multiple
					   //of time to ring up one item;
	private double percent;	//chance of generating a new customer at each step, keep low!
	
	public Checkout(int nQueues, int nSize, boolean fast)
	{
		numQueues = nQueues;
		maxQueSize = nSize;
		queues = new Queue[numQueues];
		if(fast) fastLane = new Queue(maxQueSize);
		for(int i = 0; i < numQueues; i++)
			queues[i] = new Queue(maxQueSize);
		
		base = 15;
		percent = 10;
	}
	
	//use logic to place a customer with random # of items in a queue
	public void addCustomer()
	{
		long customer = (long) (Math.ceil(Math.random() * 30));	//max of 30 items
		int shortest = 0;	//tracks the current best option
		long shortestLength = 9999;	//tracks the minimum length during loops
		long temp = 0;
		//If there's a fast lane, consider using it
		if(fastLane != null && customer <= 10) 
		{
			temp = base * fastLane.size();
			for(int j = 0; j < fastLane.size(); j++)
				temp += fastLane.itemAt(j);
			if(shortestLength > temp) 
			{
				shortest = -1;
				shortestLength = temp;
			}	
		}
		//Check all the regular lanes too
		for(int i = 0; i < queues.length; i++)
		{
			temp = base * queues[i].size();
			for(int j = 0; j < queues[i].size(); j++)
				temp += queues[i].itemAt(j);
			if(shortestLength > temp) 
			{
				shortest = i;
				shortestLength = temp;
			}
		}
		
		if(shortest == -1) //if fast lane is best choice, add customer there and return
		{
			fastLane.insert(customer);
			System.out.println("Customer " + customer + " added to fast-lane.");
			//prevent process() from deleting new customer when queue is empty
			fastLane.justAdded = true;
		}
		else //otherwise add the customer to the shortest regular line
		{
			queues[shortest].insert(customer);
			System.out.println("Customer " + customer + " added to line " + (shortest+1));
			//prevent process() from deleting new customer when queue is empty
			if(queues[shortest].runningTally <= 0)
				queues[shortest].justAdded = true;
		}
		
	}
	
	public void process(int time)	//pass a given amount of time and update queues
	{
		for(int i = 0; i < time; i++) //do a bunch of operations for each time-tick
		{
			for(int j = 0; j < queues.length; j++) //go through each queue and update
			{
				if(queues[j].runningTally <= 0) //if cashier has finished customer
				{
					//remove front customer and get new one
					if(!queues[j].isEmpty() && !queues[j].justAdded) queues[j].remove();
					queues[j].runningTally = queues[j].peekFront();
				}
				else queues[j].runningTally--; //decrement runningTally for each tick
				queues[j].justAdded = false; //after 1 tick, any new items can be processed
			}
			
			if(fastLane != null)
			{
				if(fastLane.runningTally <= 0)
				{
					if(!fastLane.isEmpty() && !fastLane.justAdded) fastLane.remove();
					fastLane.runningTally = fastLane.peekFront();
				}
				else fastLane.runningTally--;
				fastLane.justAdded = false;
			}
			
			if((Math.random() * 100) <= percent) //every tick, possibly generate new customer
				addCustomer();
		}
	}
	
	public void display()
	{
		for(int i = 0; i < queues.length; i++)
		{
			System.out.print("Line " + (i+1) + ": ");
			queues[i].display();
			System.out.println("   -Running Tally: " + queues[i].runningTally);
		}
		if(fastLane != null) 
		{
			System.out.print("Fast-Lane: ");
			fastLane.display();
			System.out.println("   -Running Tally: " + fastLane.runningTally);
		}
	}
	
	public void initialize()	//start with a bunch of random customers
	{
		for(int i = 0; i < queues.length; i++)
		{
			for(int j = 0; j < (Math.floor(Math.random() * 4)); j++)
			{
				queues[i].insert((long)(Math.ceil(Math.random() * 30)));
			}
			queues[i].runningTally = queues[i].peekFront(); //get started on first customer
		}
		if(fastLane != null)
		{
			for(int j = 0; j < (Math.floor(Math.random() * 4)); j++)
			{
				fastLane.insert((long)(Math.ceil(Math.random() * 10)));
			}
			fastLane.runningTally = fastLane.peekFront(); //get started on first customer
		}	
	}
}
		
class DequeTest
{
	public static void main(String[] args) throws IOException
	{
		String input;
		Checkout grocery = new Checkout(3, 20, true); //numQueues, queSize, fastlane
		grocery.initialize();
		grocery.display();
		
		while(true)
		{
			System.out.print("Time to Pass: ");
			System.out.flush();
			input = getString();
			if (input.equals("") ) break;
			grocery.process(Integer.parseInt(input) );
			
			grocery.display();
		}			
	}
	
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
}