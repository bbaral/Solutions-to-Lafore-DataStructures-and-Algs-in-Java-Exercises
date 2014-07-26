//Chapter 5, Exercise 6

/* Let’s try something a little different: a two-dimensional linked list, which we’ll
call a matrix. This is the list analogue of a two-dimensional array. It might be
useful in applications such as spreadsheet programs. If a spreadsheet is based
on an array, and you insert a new row near the top, you must move every cell
in the lower rows N*M cells, which is potentially a slow process. If the spreadsheet
is implemented by a matrix, you need only change N pointers.

For simplicity, we’ll assume a singly linked approach (although a double-linked
approach would probably be more appropriate for a spreadsheet). Each link
(except those on the top row and left side) is pointed to by the link directly
above it and by the link on its left. You can start at the upper-left link and
navigate to, say, the link on the third row and fifth column by following the
pointers down two rows and right four columns. Assume your matrix is created
with specified dimensions (7 by 10, for example). You should be able to insert
values in specified links and display the contents of the matrix. */

class MLink
{
	public double dData;
	public MLink nextRow;
	public MLink nextCol;
	
	public MLink(double value)
	{ dData = value; }
	
	public void displayLink()
	{ System.out.print(dData + " "); }
}

//2D Matrix implemented with Linked List
class Matrix
{
	private int nRows;
	private int nCols;
	
	private MLink first;
	private MLink current;
	
	public Matrix(int rows, int cols)
	{
		nRows = rows;
		nCols = cols;
		
		first = new MLink(0);
		current = first;
		
		//initialize all cells to 0
		MLink temp;
		for(int i = 0; i < nRows; i++)
		{
			temp = current;
			for(int j = 1; j < nCols; j++)
			{
				temp.nextCol = new MLink(0);
				temp = temp.nextCol;
			}
			if(i < nRows-1) //don't make a new row if we're on the last one
			{
				current.nextRow = new MLink(0);
				current = current.nextRow;
			}
		}
	}
	
	public boolean insert(double value, int row, int col)
	{
		if(row > nRows || col > nCols || row < 0 || col < 0)
		{
			System.out.println("Must be in range (0,0) to (" + nRows + "," + nCols + ")");
			return false;
		}
		else
		{
			current = first;
			for(int i = 1; i < row; i++)
			{ current = current.nextRow; }
			for(int j = 1; j < col; j++)
			{ current = current.nextCol; }
			current.dData = value;
			
			System.out.println("Inserted " + value + " at (" + row + "," + col + ")");
			return true;
		}
	}
	
	public void display()
	{
		current = first;
		MLink temp;
		while(current != null)
		{
			temp = current;
			while(temp != null)
			{
				temp.displayLink();
				temp = temp.nextCol;
			}
			System.out.println("");
			current = current.nextRow;
		}
	}
}

class MatrixApp
{
	public static void main(String[] args)
	{
		Matrix theMatrix = new Matrix(4,6);
		theMatrix.display();
		
		theMatrix.insert(14.99, 3, 2);
		theMatrix.insert(1, 4, 6);
		theMatrix.insert(2050, 0, 0);
		theMatrix.insert(7, 10, 10);
		theMatrix.insert(39, -1, -1);
		theMatrix.display();
	}
}