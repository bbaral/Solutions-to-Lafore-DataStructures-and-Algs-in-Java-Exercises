//Chapter 6, Exercise 4: Write a program that solves the knapsack problem
//for an arbitrary knapsack capacity and series of weights. Assume the weights
//are stored in an array.

public class ch6exercise4
{
	public static double[] sack;
	
	public static void main(String[] args)
	{
		sack = new double[]{11, 8, 7, 6, 5};
		if(!knapsack(37, 0)) System.out.println("No valid combinations");
		
	}
	
	public static Boolean knapsack(double targetWeight, int index)
	{
		Boolean complete = false;
		
		if(index == sack.length) return false;
		if(sack[index] == targetWeight)
		{
			System.out.print("Answer: " + sack[index] + " ");
			complete = true;
		}; //DONE
		if(sack[index] < targetWeight) 
		{
			complete = knapsack(targetWeight-sack[index], index+1); //keep going
			if(complete) System.out.print(sack[index] + " ");
			for(int i = index+1; i < sack.length; i++) 
			{
				if(!complete) complete = knapsack(targetWeight, i);
			}
		}
		if(sack[index] > targetWeight) complete = knapsack(targetWeight, index+1);
		
		
		return complete;
	}
}
