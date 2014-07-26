//Chapter 13, Exercise 1: Modify the bfs.java program (listing 13.2)
//to find the minimum spanning tree using a breadth-first search
//instead of a depth-first search as in listing 13.3

//this queue seems not to check if it
//will overwrite itself when full...
class GraphQueue
{
	private final int SIZE = 20;
	private int[] queArray;
	private int front;
	private int rear;
	
	public GraphQueue()
	{
		queArray = new int[SIZE];
		front = 0;
		rear = -1;
	}
	
	public void insert(int j) //put item at rear of queue
	{
		if(rear == SIZE - 1)
			rear = -1;
		queArray[++rear] = j;
	}
	
	public int remove()	//take item from front of queue
	{
		int temp = queArray[front++];
		if(front == SIZE)
			front = 0;
		return temp;
	}
	
	public boolean isEmpty()
	{
		return (rear + 1 == front || (front+SIZE-1 == rear) );
	}
} //end class GraphQueue

class Vertex
{
	public char label;	//label (e.g. 'A')
	public boolean wasVisited;
	
	public Vertex(char lab)
	{
		label = lab;
		wasVisited = false;
	}
	
} //end class Vertex

class BFSGraph
{
	private final int MAX_VERTS = 20;
	private Vertex vertexList[]; //list of vertices
	private int adjMat[][];	//adjacency matrix
	private int nVerts;	//current number of vertices
	private GraphQueue theQueue;
	
	public BFSGraph()
	{
		vertexList = new Vertex[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j = 0; j<MAX_VERTS; j++)			//set adjacency
			for(int k = 0; k<MAX_VERTS; k++)		//matrix to 0
				adjMat[j][k] = 0;
		theQueue = new GraphQueue();
	}
	
	public void addVertex(char lab)
	{
		vertexList[nVerts++] = new Vertex(lab);
	}
	
	public void addEdge(int start, int end)
	{
		adjMat[start][end] = 1;
		adjMat[end][start] = 1;
	}
	
	public void displayVertex(int v)
	{
		System.out.print(vertexList[v].label);
	}
	
	public void bfs()
	{
		vertexList[0].wasVisited = true;
		displayVertex(0);
		theQueue.insert(0);
		int v2;
		
		while(!theQueue.isEmpty())
		{
			int v1 = theQueue.remove();
			while( (v2=getAdjUnvisitedVertex(v1)) != -1)
			{
				vertexList[v2].wasVisited = true;
				displayVertex(v2);
				theQueue.insert(v2);
			}
		}
		
		for(int j = 0; j < nVerts; j++)
			vertexList[j].wasVisited = false;
	} //end bfs()
	
	//displays the minimum spanning tree made from a BFS
	public void bfMinSpanTree()
	{
		vertexList[0].wasVisited = true;
		theQueue.insert(0);
		int v2;
		
		while(!theQueue.isEmpty())
		{
			int v1 = theQueue.remove();
			while( (v2=getAdjUnvisitedVertex(v1)) != -1)
			{
				vertexList[v2].wasVisited = true;
				displayVertex(v1);
				displayVertex(v2);
				System.out.print("|");
				theQueue.insert(v2);
			}
		}
		
		for(int j = 0; j < nVerts; j++)
			vertexList[j].wasVisited = false;
	} //end bfMinSpanTree()
	
	//returns an unvisited vertex adjacent to v
	public int getAdjUnvisitedVertex(int v)
	{
		for(int j = 0; j < nVerts; j++)
			if(adjMat[v][j] == 1 && vertexList[j].wasVisited == false)
				return j;
		return -1;
	}
	
} //end class Graph

class BFSApp
{
	public static void main(String[] args)
	{
		BFSGraph theGraph = new BFSGraph();
		theGraph.addVertex('A');	// 0 (start for dfs)
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		theGraph.addVertex('F');
		theGraph.addVertex('G');
		theGraph.addVertex('H');
		theGraph.addVertex('I');
		
		theGraph.addEdge(0, 1);
		theGraph.addEdge(1, 7);
		theGraph.addEdge(7, 8);
		theGraph.addEdge(1, 8);
		theGraph.addEdge(1, 2);
		theGraph.addEdge(0, 2);
		theGraph.addEdge(2, 3);
		theGraph.addEdge(3, 4);
		theGraph.addEdge(3, 5);
		theGraph.addEdge(3, 6);
		theGraph.addEdge(4, 5);
		theGraph.addEdge(5, 6);
		
		System.out.print("Visits: ");
		theGraph.bfs();
		System.out.println();
		
		System.out.println("Minimum Spanning Tree of Graph");
		theGraph.bfMinSpanTree();
		System.out.println();
		
	}
}




























