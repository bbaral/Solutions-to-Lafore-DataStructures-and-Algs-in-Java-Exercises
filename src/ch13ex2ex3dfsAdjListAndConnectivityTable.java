//Chapter 13, Exercise 2: Modify the dfs.java program (listing 13.1)
//to use adjacency lists rather an adjacency matrix.

//Chapter 13, Exercise 3: Modify the dfs.java program to
//display a connectivity table for a graph, as described in the section
//"Connectivity in Directed Graphs."

class StackX
{
	private final int SIZE = 20;
	private int[] st;
	private int top;
	
	public StackX()
	{
		st = new int[SIZE];
		top = -1;
	}
	
	public void push(int j)
	{ st[++top] = j; }
	
	public int pop()
	{ return st[top--]; }
	
	public int peek()
	{ return st[top]; }
	
	public boolean isEmpty()
	{ return top == -1; }
}

//uses Vertex class from ch13ex1BreadthFirstMinSpanTree.java

class DFSLink
{
	public int vertex;
	public DFSLink next;
	
	
}

class DFSList
{
	public DFSLink head;
	
	public DFSList()
	{ 
		head = new DFSLink();
		//head is given a dummy value so searches
		//don't necessarily include vertex A (0)
		head.vertex = -1;
	}
	
	public void insert(int v)
	{
		DFSLink cur = head;
		while(cur.next != null)
			cur = cur.next;
		cur.next = new DFSLink();
		cur.next.vertex = v;
	}
	
	public DFSLink deleteFromEnd()
	{
		if(head == null) return null;
		DFSLink cur = head;
		while(cur.next.next != null)
			cur = cur.next;
		DFSLink temp = cur.next;
		cur.next = null;
		return temp;
	}
	
	public boolean contains(int j)
	{
		DFSLink cur = head;
		while(cur != null)
		{
			if(cur.vertex == j) return true;
			cur = cur.next;
		}
		return false;
	}
}

class DFSGraph
{
	private final int MAX_VERTS = 20;
	private Vertex[] vertexList;
	private DFSList[] adjMat;
	private int nVerts;
	private StackX theStack;
	
	public DFSGraph()
	{
		vertexList = new Vertex[MAX_VERTS];
		adjMat = new DFSList[MAX_VERTS];
		nVerts = 0;
		for(int j = 0; j < MAX_VERTS; j++)
			adjMat[j] = new DFSList();
		theStack = new StackX();
	}
	
	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	
	//creates an edge from one vertex to another.
	//DIRECTED GRAPH, not undirected
	public void addEdge(int start, int end)
	{
		adjMat[start].insert(end);
	}
	
	public void displayVertex(int j)
	{
		System.out.print(vertexList[j].label);
	}
	
	//modified to allow specification of a starting vertex
	public void dfs(int start)
	{
		vertexList[start].wasVisited = true;
		displayVertex(start);
		theStack.push(start);
		
		while(!theStack.isEmpty())
		{
			int v = getAdjUnvisitedVertex(theStack.peek());
			if(v == -1)
				theStack.pop();
			else
			{
				vertexList[v].wasVisited = true;
				displayVertex(v);
				theStack.push(v);
			}
		}
		System.out.println();
		for(int j = 0; j < nVerts; j++)
			vertexList[j].wasVisited = false;
	}
	
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j = 0; j < nVerts; j++)
			if(adjMat[v].contains(j) && vertexList[j].wasVisited == false)
				return j;
		return -1;
	}
	
	public void displayConnectivityTable()
	{
		for(int j = 0; j < nVerts; j++)
			dfs(j);
	}
} // end class DFSGraph

class DFSApp
{
	public static void main(String[] args)
	{
		DFSGraph theGraph = new DFSGraph();
		theGraph.addVertex('A');
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		
		theGraph.addEdge(0, 1);
		theGraph.addEdge(2, 1);
		theGraph.addEdge(2, 3);
		theGraph.addEdge(3, 1);
		theGraph.addEdge(4, 2);
		
		System.out.println("Connectivity Table of the Directed Graph:");
		theGraph.displayConnectivityTable();
		System.out.println();
	}
}

































