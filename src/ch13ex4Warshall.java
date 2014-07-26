//Chapter 13, Exercise 4: Implement Warshall's algorithm
//to find the transitive closure of a graph.

class WStackX
{
	private final int SIZE = 20;
	private int[] st;
	private int top;

	public WStackX() // constructor
	{
		st = new int[SIZE]; // make array
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

} // end class WStackX

class WVertex
{
	public char label; // label (e.g. ‘A’)
	public boolean wasVisited;

	public WVertex(char lab) // constructor
	{
	label = lab;
	wasVisited = false;
	}

} // end class WVertex

class WarshallGraph
{
	private final int MAX_VERTS = 20;
	private WVertex vertexList[]; // list of vertices
	private int adjMat[][]; // adjacency matrix
	private int nVerts; // current number of vertices
	private WStackX theStack;

	public WarshallGraph()
	{
		vertexList = new WVertex[MAX_VERTS];
		// adjacency matrix
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j=0; j<MAX_VERTS; j++) // set adjacency
			for(int k=0; k<MAX_VERTS; k++) // matrix to 0
				adjMat[j][k] = 0;
		theStack = new WStackX();
	}

	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new WVertex(lab);
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

	//modified to allow specification of starting vertex
	public void dfs(int start) // depth-first search
	{ // begin at vertex 0
		vertexList[start].wasVisited = true; // mark it
		displayVertex(start); // display it
		theStack.push(start); // push it
		while( !theStack.isEmpty() ) // until stack empty,
		{
			// get an unvisited vertex adjacent to stack top
			int v = getAdjUnvisitedVertex( theStack.peek() );
			if(v == -1) // if no such vertex,
				theStack.pop();
			else // if it exists,
			{
				vertexList[v].wasVisited = true; // mark it
				displayVertex(v); // display it
				theStack.push(v); // push it
			}
		} // end while
		
		// stack is empty, so we’re done
		for(int j=0; j<nVerts; j++) // reset flags
		vertexList[j].wasVisited = false;
	} // end dfs

	// returns an unvisited vertex adj to v
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j=0; j<nVerts; j++)
		if(adjMat[v][j]==1 && vertexList[j].wasVisited==false)
		return j;
		return -1;
	}
	
	public void displayConnectivityTable()
	{
		for(int j = 0; j < nVerts; j++)
		{
			dfs(j);
			System.out.println();
		}
	}
	
	//creates the transitive closure of a graph
	public int[][] warshall()
	{
		int[][] newAdjMat = new int[MAX_VERTS][MAX_VERTS];
		
		//copy old adjmat to new one
		for(int i = 0; i < nVerts; i++)
			for(int j = 0; j < nVerts; j++)
				newAdjMat[i][j] = adjMat[i][j];
		
		//each row
		for(int y = 0; y < nVerts; y++)
		{
			//each cell in row
			for(int x = 0; x < nVerts; x++)
			{
				if(adjMat[y][x] == 1)
				{
					for(int z = 0; z < nVerts; z++)
					{
						if(adjMat[z][y] == 1)
							newAdjMat[z][x] = 1;
					}
				}
			}
		}
		
		return newAdjMat;
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
	
} // end class WarshallGraph

class WarshallApp
{
	public static void main(String[] args)
	{
		WarshallGraph theGraph = new WarshallGraph();
		theGraph.addVertex('A'); // 0 (start for dfs)
		theGraph.addVertex('B'); // 1
		theGraph.addVertex('C'); // 2
		theGraph.addVertex('D'); // 3
		theGraph.addVertex('E'); // 4
		theGraph.addEdge(0, 2); 
		theGraph.addEdge(1, 0);
		theGraph.addEdge(1, 4);
		theGraph.addEdge(3, 4);
		theGraph.addEdge(4, 2);
		
		theGraph.displayConnectivityTable();
		int[][] newAdjMat = theGraph.warshall();
		theGraph.displayMatrix(theGraph.getAdjMat());
		System.out.println();
		theGraph.displayMatrix(newAdjMat);
	} // end main()
} // end class WarshallApp
////////////////////////////////////////////////////////////////