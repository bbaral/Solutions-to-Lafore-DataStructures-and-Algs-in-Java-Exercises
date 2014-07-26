//Chapter 13, Exercise 5: Solve the Knight's Tour puzzle
//using depth-first searching with variable board sizes

import java.io.*;

class KnightStackX
{
	private int[] st;
	private int top;

	public KnightStackX() // constructor
	{
		st = new int[KnightGraph.AREA]; // make array
		top = -1;
	}

	public void push(int j) // put item on stack
		{ st[++top] = j; }

	public int pop() // take item off stack
		{ return st[top--]; }
	
	public int peek() // peek at top of stack
		{ return st[top]; }

	public boolean isEmpty() // true if nothing on stack-
		{ return (top == -1); }
	
	public boolean isFull()
		{ return (top == st.length-1); }
	
	public boolean oneItem() //is there only one item left in the stack?
		{ return (top == 0); }

} // end class KnightStackX

//placed in Vertex to remember which vertices
//were visited from that Vertex
class VisitedStackX
{
	private int[] st;
	private int top;

	public VisitedStackX() // constructor
	{
		st = new int[8]; // make array
		top = -1;
	}

	public void push(int j) // put item on stack
		{ st[++top] = j; }

	public int pop() // take item off stack
		{ return st[top--]; }
	
	public int peek() // peek at top of stack
		{ return st[top]; }

	public boolean isEmpty() // true if nothing on stack-
		{ return (top == -1); }
	
	public boolean isFull()
		{ return (top == st.length-1); }

} // end class KnightStackX

class KnightVertex
{
	public char label; // label (e.g. ‘A’)
	public boolean wasVisited;
	public VisitedStackX justVisited;
	public int lastVisited;

	public KnightVertex() // constructor
	{ 
		label = '-';
		wasVisited = false; 
		justVisited = new VisitedStackX();
		lastVisited = -1;
	}

} // end class KnightVertex

class KnightGraph
{
	public static final int SIZE = 6;
	public static final int AREA = SIZE*SIZE;
	private KnightVertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private KnightStackX theStack;

	public KnightGraph()
	{
		vertexList = new KnightVertex[AREA];
		// adjacency matrix
		adjMat = new int[AREA][AREA];
		nVerts = AREA;
		for(int j=0; j<SIZE; j++) // set adjacency
			for(int k=0; k<SIZE; k++) // matrix to 0
				adjMat[j][k] = 0;
		theStack = new KnightStackX();
		
		//GENERATE THE BOARD
		for(int i = 0; i < AREA; i++)
			vertexList[i] = new KnightVertex();
		
		//ADD POSSIBLE MOVES TO EACH SPACE
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				addPossibleMoves(i, j);
	}

	public void addPossibleMoves(int i, int j)
	{
		int currentRow = i*SIZE;
		int currentCol = j;
		int currentVertex = currentRow+currentCol;
		
		//if the move is in the board
		if(i-1>=0)
		{
			if(j-2>=0)
				addEdge(currentVertex, currentVertex-SIZE-2);
			if(j+2<SIZE)
				addEdge(currentVertex, currentVertex-SIZE+2);
		}
		if(i+1<SIZE)
		{
			if(j-2>=0)
				addEdge(currentVertex, currentVertex+SIZE-2);
			if(j+2<SIZE)
				addEdge(currentVertex, currentVertex+SIZE+2);
		}
		if(i-2>=0)
		{
			if(j-1>=0)
				addEdge(currentVertex, currentVertex-(SIZE*2)-1);
			if(j+1<SIZE)
				addEdge(currentVertex, currentVertex-(SIZE*2)+1);
		}
		if(i+2<SIZE)
		{
			if(j-1>=0)
				addEdge(currentVertex, currentVertex+(SIZE*2)-1);
			if(j+1<SIZE)
				addEdge(currentVertex, currentVertex+(SIZE*2)+1);
		}
	}
	
	//DIRECTED EDGES
	public void addEdge(int start, int end)
	{
		adjMat[start][end] = 1;
	}

	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	
	public void displayBoard()
	{
		System.out.println("..................................");
		for(int i = 0; i < SIZE; i++)
		{
			for(int j = 0; j < SIZE; j++)
				System.out.print(vertexList[i*SIZE+j].label);
			System.out.println();
		}
		System.out.println("..................................");
	}

	//modified to allow specification of starting vertex
	public boolean dfs(int start) throws IOException // depth-first search
	{ 
		vertexList[start].label = 'S'; //denote start with S
		vertexList[start].wasVisited = true; // mark it
		theStack.push(start); // push it
		while( !theStack.isEmpty() ) // until stack empty,
		{
			int m = theStack.peek();
			vertexList[m].label = 'M';
			//displayBoard();
			//getChar();
			
			if(theStack.isFull())	//we won
			{
				for(int j=0; j<nVerts; j++) // reset flags
				{
					vertexList[j].wasVisited = false;
					vertexList[j].label = '-';
					vertexList[j].lastVisited = -1;
					while(!vertexList[j].justVisited.isEmpty())
						vertexList[j].justVisited.pop();
					KnightStackX winningMoves = new KnightStackX();
					while(!theStack.isEmpty())
						winningMoves.push(theStack.pop());
					while(!winningMoves.isEmpty())
						System.out.print(winningMoves.pop() + " ");
				}
				vertexList[0] = new KnightVertex();
				System.out.println();
				return true;
			}
			// get an unvisited vertex adjacent to stack top
			int v = getNextVertex( theStack.peek() );
			
			if(v == -1) // if no such vertex,
			{
				int unmark = theStack.pop();
				vertexList[unmark].label = '-';
				vertexList[unmark].wasVisited = false;
				vertexList[unmark].lastVisited = -1;
				//unmarkVisited(unmark);
			}
			else // if it exists,
			{
				vertexList[v].wasVisited = true; // mark it
				int curVertex = theStack.peek();
				vertexList[curVertex].label = 'K';
				vertexList[curVertex].lastVisited = v;
				//vertexList[curVertex].justVisited.push(v);
				theStack.push(v); // push it
			}
		} // end while
		
		// stack is empty, so we’re done
		for(int j=0; j<nVerts; j++) // reset flags
		{	
			vertexList[j].wasVisited = false;
			vertexList[j].label = '-';
			vertexList[j].lastVisited = -1;
			while(!vertexList[j].justVisited.isEmpty())
				vertexList[j].justVisited.pop();
		}
		
		return false;
	} // end dfs

	public int getNextVertex(int v)
	{
		for(int j = vertexList[v].lastVisited+1; j < nVerts; j++)
			if(adjMat[v][j]==1 && vertexList[j].wasVisited == false) return j;
		return -1;
	}
	
	public void getChar() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		br.readLine();
	}
	
	// returns an unvisited vertex adj to v
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j=0; j<nVerts; j++)
			if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
				return j;
		return -1;
	}
	
	// marks all adjacent vertices of FAILURE NODE as unvisited
		public void unmarkVisited(int v)
		{
			while(!vertexList[v].justVisited.isEmpty())
			{
				int j = vertexList[v].justVisited.pop();
				vertexList[j].wasVisited = false;
				vertexList[j].label = '-';
			}
					
		}
	
	public int[][] getAdjMat()
	{ return adjMat; }
	
	public void displayMatrix(int[][] matrix)
	{
		for(int i=0; i<nVerts; i++)
		{
			for(int j=0; j<nVerts; j++)
				System.out.print(matrix[i][j]);
			System.out.println();
		}
	}
	
} // end class KnightGraph

class KnightApp
{
	public static void main(String[] args) throws IOException
	{
		KnightGraph theGraph = new KnightGraph();
		System.out.println("CREATED GRAPH");
		boolean solutionFound = false;
		theGraph.displayMatrix(theGraph.getAdjMat());
		for(int i = 0; i < KnightGraph.AREA; i++)
		{
		
			solutionFound = theGraph.dfs(i);
			if(solutionFound) 
				System.out.println("FOUND A SOLUTION STARTING AT ("
						+ (i/KnightGraph.SIZE) + ", " + (i%KnightGraph.SIZE) + ")");
			else
				System.out.println("NO SOLUTION FROM ("
						+ (i/KnightGraph.SIZE) + ", " + (i%KnightGraph.SIZE) + ")");
		}
	} // end main()
} // end class KnightApp
////////////////////////////////////////////////////////////////