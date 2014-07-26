class arraySh
{
	private long[] theArray;
	private int nElems;
	
	public arraySh(int max)
	{
		theArray = new long[max];
		nElems = 0;
	}
	
	public void insert(long value)
	{
		theArray[nElems++] = value;
	}
	
	public void display()
	{
		for(int i = 0; i < nElems; i++)
			System.out.print(theArray[i] + " ");
		System.out.println("");
	}
	
	public void shellSort()
	{
		int inner, outer;
		long temp;
		
		int h = 1;
		while(h <= nElems/3)
			h = h*3 + 1;
		
		while(h>0)
		{
			for(outer = h; outer < nElems; outer++)
			{
				temp = theArray[outer];
				inner = outer;
				while(inner > h-1 && theArray[inner-h] >= temp)
				{
					theArray[inner] = theArray[inner-h];
					inner -= h;
				}
				theArray[inner] = temp;
			}
			h = (h-1)/3;
		}
	}
}

class shellApp
{
	public static void main(String[] args)
	{
		int maxSize = 100;
		arraySh arr = new arraySh(maxSize);
		
		for(int i = 0; i < maxSize; i++)
			arr.insert((long)(Math.random()*199));
		
		arr.display();
		arr.shellSort();
		arr.display();
	}
}
