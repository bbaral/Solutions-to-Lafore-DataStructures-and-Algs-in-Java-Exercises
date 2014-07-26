//Chapter 6, Exercise 1: Write a recursive method mult() that performs
//multiplication of x and y by adding x to itself y times.

//Chapter 6, Exercise 3: Implement the recursive approach to raising
//a number to a power, as described in the "Raising a Number to a Power"
//section near the end of this chapter.

public class ch6ex1ex3
{
	public static void main(String[] args)
	{
		double x = 3;
		double y = 3;
		
		double answer = power(x, y);
		System.out.println(x + " ^ " + y + " = " + answer);
		
		double a = 5;
		double b = 9;
		double answer2 = mult(a, b);
		System.out.println(a + " * " + b + " = " + answer2);
	}
	
	//Computes x^y recursively
	public static double power(double x, double y)
	{
		if(y == 1) return x;	//at end, return back up
		else if (y%2 == 0) return power(x*x, y/2);	//exponent is even
		else return x * power(x, y-1);	//exponent is odd, multiply once and offset exponent to be even again
	}
	
	//Computes x*y recursively
	public static double mult(double x, double y)
	{
		if(y==1) return x;
		else return x + mult(x, y-1);
	}
}
