//Chapter 15, Exercise 4: Implement the Traveling Salesman Problem 
//with a non-directed graph. Use the brute-force approach of testing
//every possible sequence of cities. Don't worry about eliminating symmetrical routes.

//Chapter 15, Exercise 5: Write a program that displays all the Hamiltonian
//cycles of a weighted, non-directed graph. Basically the TSP
//without the total distance displayed.

import java.util.Vector;

class TSPVertex
{
	public char label;		//label(e.g. 'A')
	public boolean wasVisited;
	public int lastVisited;
	
	public TSPVertex(char lab)
	{
		label = lab;
		wasVisited = false;
		lastVisited = -1;
	}
} //end class Vertex

class TSPGraph
{
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private TSPVertex vertexList[];
	private int adjMat[][];
	private int nVerts;
	private StackX theStack;
	
	public TSPGraph()
	{
		vertexList = new TSPVertex[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j = 0; j<MAX_VERTS; j++)
			for(int k = 0; k<MAX_VERTS; k++)
				adjMat[j][k] = INFINITY;
		theStack = new StackX();
	}
	
	public int get_nVerts()
		{ return nVerts; }
	
	public void addVertex(char lab)
		{ vertexList[nVerts++] = new TSPVertex(lab); }
	
	public void addEdge(int start, int end, int weight)
	{
		adjMat[start][end] = weight;	//non-directed edges
		adjMat[end][start] = weight;
	}
	
	//print all paths that visit each node
	//exactly once, as well as their total weight
	public void travel(int start)
	{
		int v = start;
		int numVerts = 1;
		vertexList[v].wasVisited = true;
		theStack.push(v);
		Vector<Integer> totals = new Vector<Integer>(); 
		
		while(!theStack.isEmpty())
		{
			v = getAdjUnvisitedEdge(theStack.peek());
			if(v == -1)
			{
				//if the last node in sequence reconnects with
				//the starting node, print the full circuit
				if(adjMat[theStack.peek()][start] < INFINITY
						&& numVerts == nVerts)
				{
					int total = 0;
					int A, B;
					StackX displayStack = new StackX();
					while(!theStack.isEmpty())
						displayStack.push(theStack.pop());
					while(!displayStack.isEmpty())
					{
						System.out.print(vertexList[displayStack.peek()].label);
						A = displayStack.peek();
						theStack.push(displayStack.pop());
						if(!displayStack.isEmpty())
						{
							B = displayStack.peek();
							total += adjMat[A][B];
						}
					}
					total += adjMat[theStack.peek()][start];
					totals.add(total);
					System.out.println(vertexList[start].label + ": " + total);
				}
				int unmark = theStack.pop();
				vertexList[unmark].wasVisited = false;
				vertexList[unmark].lastVisited = -1;
				numVerts--;
			}
			else
			{
				vertexList[theStack.peek()].lastVisited = v;
				vertexList[v].wasVisited = true;
				theStack.push(v);
				numVerts++;
			}
		}
		
		int smallest = INFINITY;
		for(int j = 0; j<totals.size(); j++)
			if(totals.get(j) < smallest)
				smallest = totals.get(j);
		System.out.println("Shortest path is " + smallest);
	}
	
	
	public void hamiltonianCycles(int start)
	{
		int v = start;
		int numVerts = 1;
		vertexList[v].wasVisited = true;
		theStack.push(v);
		
		while(!theStack.isEmpty())
		{
			v = getAdjUnvisitedEdge(theStack.peek());
			if(v == -1)
			{
				//if the last node in sequence reconnects with
				//the starting node, print the full circuit
				if(adjMat[theStack.peek()][start] < INFINITY
						&& numVerts == nVerts)
				{
					StackX displayStack = new StackX();
					while(!theStack.isEmpty())
						displayStack.push(theStack.pop());
					while(!displayStack.isEmpty())
					{
						System.out.print(vertexList[displayStack.peek()].label);
						theStack.push(displayStack.pop());
					}
					System.out.println(vertexList[start].label);
					
				}
				int unmark = theStack.pop();
				vertexList[unmark].wasVisited = false;
				vertexList[unmark].lastVisited = -1;
				numVerts--;
			}
			else
			{
				vertexList[theStack.peek()].lastVisited = v;
				vertexList[v].wasVisited = true;
				theStack.push(v);
				numVerts++;
			}
		}
	}
	
	private int getAdjUnvisitedEdge(int v)
	{
		for(int j = vertexList[v].lastVisited+1; j < nVerts; j++)
			if(vertexList[j].wasVisited == false &&
					adjMat[v][j] < INFINITY)
				return j;	
		return -1;
	}
	
} //end class TSPGraph


class TSPApp
{
	public static void main(String[] args)
	{
		TSPGraph theGraph = new TSPGraph();
		theGraph.addVertex('A');
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		
		theGraph.addEdge(0, 1, 50);
		theGraph.addEdge(1, 2, 40);
		theGraph.addEdge(2, 3, 60);
		theGraph.addEdge(3, 0, 80);
		theGraph.addEdge(4, 0, 40);
		theGraph.addEdge(4, 1, 10);
		theGraph.addEdge(4, 2, 90);
		theGraph.addEdge(4, 3, 30);
		
		for(int j = 0; j < theGraph.get_nVerts(); j++)
		{
			theGraph.travel(j);
			System.out.println();
		}
		
		System.out.println("Hamiltonian Cycles starting from C");
		theGraph.hamiltonianCycles(2);
	} //end main()
} //end class TSPApp



























