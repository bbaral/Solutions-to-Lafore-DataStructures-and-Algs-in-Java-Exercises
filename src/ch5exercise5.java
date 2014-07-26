//Chapter 5, Exercise 5

/* The Josephus Problem is a famous mathematical puzzle that goes back to
ancient times. There are many stories to go with the puzzle. One is that
Josephus was one of a group of Jews who were about to be captured by the
Romans. Rather than be enslaved, they chose to commit suicide. They arranged
themselves in a circle and, starting at a certain person, started counting off
around the circle. Every nth person had to leave the circle and commit suicide.
Josephus decided he didn’t want to die, so he arranged the rules so he would
be the last person left. If there were (say) 20 people, and he was the seventh
person from the start of the circle, what number should he tell them to use for
counting off? The problem is made much more complicated because the circle
shrinks as the counting continues.

Create an application that uses a circular linked list (like that in Programming
Project 5.3) to model this problem. Inputs are the number of people in the
circle, the number used for counting off, and the number of the person where
counting starts (usually 1). The output is the list of persons being eliminated.
When a person drops out of the circle, counting starts again from the person
who was on his left (assuming you go around clockwise). Here’s an example.
There are seven people numbered 1 through 7, and you start at 1 and count off
by threes. People will be eliminated in the order 4, 1, 6, 5, 7, 3. Number 2 will
be left. */

class Josephus
{
	private CLinkedList theList;
	private int count;
	private int people;
	
	public Josephus(int nPeople, int nCount, int startPoint)
	{
		theList = new CLinkedList();
		count = nCount;
		people = nPeople;
		
		for(int i = 1; i <= nPeople; i++)
		{
			theList.insert(i);
			theList.step();
		}
		
		theList.step();		//starting point at 1
		
		for(int i = 1; i < startPoint; i++)
		{
			theList.step();	//move step by step to starting person
		}
	}
	
	private void display()
	{
		theList.display();
	}
	
	//steps through list by count and removes an item
	private CLink kill()
	{
		for(int i = 0; i < count-1; i++) //count-1 because CList deletes item AFTER list.current
		{
			theList.step();
		}
		CLink temp = theList.delete();
		System.out.println("Killed #" + temp.lData);
		theList.step();	//move to person left of killed person
		return temp;
	}
	
	public CLink execute()	//runs through entire sequence
	{
		CLink temp = new CLink(0);
		for(int i = 0; i < people-1; i++)
		{
			display();
			temp = kill();
		}	
		System.out.println("Last person left is #" + temp.next.lData);
		return temp;
	}
}

class JosephusApp
{
	public static void main(String[] args)
	{
		Josephus trial = new Josephus(10, 2, 1);
		trial.execute();
	}
}