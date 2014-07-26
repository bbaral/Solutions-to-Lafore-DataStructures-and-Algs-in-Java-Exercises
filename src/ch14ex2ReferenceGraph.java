//Chapter 14, Exercise 2

//Write a program that implements a graph where Java references
//represent edges, so that a Vertex object contains a list of 
//vertices that it's connected to.

//The main() method should be similar to main() in listing 14.2
//so that it uses the same addVertex() and addEdge() calls.

//Display a connectivity table of the graph to prove
//that it is constructed properly.

class RefStackX
{
	private final int SIZE = 20;
	private RefVertex[] st;
	private int top;
	
	public RefStackX()
	{
		st = new RefVertex[SIZE];
		top = -1;
	}
	
	public void push(RefVertex j)
	{ st[++top] = j; }
	
	public RefVertex pop()
	{ return st[top--]; }
	
	public RefVertex peek()
	{ return st[top]; }
	
	public boolean isEmpty()
	{ return top == -1; }
}

class RefVertex
{
	public char label;		//label(e.g. 'A')
	public boolean wasVisited;
	public EdgeList edges;
	
	public RefVertex(char lab)
	{
		label = lab;
		wasVisited = false;
		edges = new EdgeList();
	}
} //end class RefVertex

class RefEdge
{
	public int distance;	//distance to destination vertex
	public RefVertex destination;	//holds a reference to the destination vertex
	
	public RefEdge(RefVertex dest, int dist)
	{
		distance = dist;
		destination = dest;
	}
} //end class RefEdge

class EdgeLink
{
	public RefEdge edge;
	public EdgeLink next;
	
	public EdgeLink(RefVertex dest, int dist)
	{
		edge = new RefEdge(dest, dist);
		next = null;
	}
}

class EdgeList
{
	public EdgeLink head;
	
	public EdgeList()
	{
		head = null;
	}
	
	public void insert(RefVertex dest, int dist)
	{
		if(head == null)
		{
			head = new EdgeLink(dest, dist);
			head.next = null;
		}
		else
		{
			EdgeLink current = head;
			while(current.next != null)
				current = current.next;
			current.next = new EdgeLink(dest, dist);
		}
	}
	
	public RefEdge findUnvisitedEdge()
	{
		EdgeLink current = head;
		while(current != null)
		{
			if(current.edge.destination.wasVisited == false)
				return current.edge;
			current = current.next;
		}
		return null;
	}
	
}

class RefGraph
{
	private final int MAX_VERTS = 20;
	private RefVertex vertexList[];
	private int nVerts;
	private RefStackX theStack;
	
	public RefGraph()
	{
		vertexList = new RefVertex[MAX_VERTS];	
		nVerts = 0;
		theStack = new RefStackX();
	}
	
	public void addVertex(char lab)
		{ vertexList[nVerts++] = new RefVertex(lab); }
	
	public void addEdge(int start, int end, int weight)
		{ vertexList[start].edges.insert(vertexList[end], weight); }
	
	//modified to allow specification of a starting vertex
		public void dfs(int start)
		{		
			vertexList[start].wasVisited = true;
			System.out.print(vertexList[start].label);
			theStack.push(vertexList[start]);
			
			while(!theStack.isEmpty())
			{
				RefEdge v = getAdjUnvisitedEdge(theStack.peek());
				if(v == null)
					theStack.pop();
				else
				{
					v.destination.wasVisited = true;
					System.out.print(v.destination.label);
					theStack.push(v.destination);
				}
			}
			System.out.println();
			for(int j = 0; j < nVerts; j++)
				vertexList[j].wasVisited = false;
		}
		
		public RefEdge getAdjUnvisitedEdge(RefVertex v)
		{ return v.edges.findUnvisitedEdge(); }
		
		public void displayConnectivityTable()
		{
			for(int j = 0; j < nVerts; j++)
				dfs(j);
		}
		
} //end class RefGraph


class RefGraphApp
{
	public static void main(String[] args)
	{
		RefGraph theGraph = new RefGraph();
		theGraph.addVertex('A');
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		theGraph.addVertex('F');
		theGraph.addVertex('G');
		theGraph.addVertex('H');
		theGraph.addVertex('I');
		theGraph.addVertex('J');
		
		theGraph.addEdge(0, 1, 50);
		theGraph.addEdge(0, 7, 15);
		theGraph.addEdge(1, 2, 20);
		theGraph.addEdge(1, 4, 500);
		theGraph.addEdge(2, 3, 80);
		theGraph.addEdge(3, 6, 28);
		theGraph.addEdge(4, 5, 22);
		theGraph.addEdge(5, 3, 10000);
		theGraph.addEdge(6, 2, 589);
		theGraph.addEdge(7, 8, 39);
		theGraph.addEdge(7, 9, 99);
		theGraph.addEdge(9, 2, 188);
		
		theGraph.displayConnectivityTable();
		System.out.println();
	} //end main()
} //end class RefGraphApp