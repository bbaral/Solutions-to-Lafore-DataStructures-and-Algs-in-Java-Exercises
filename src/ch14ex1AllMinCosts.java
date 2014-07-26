//Chapter 14, Exercise 1:

//Modify the path.java program (Listing 14.2) to print
//a table of the minimum costs to get from any vertex
//to any other vertex.

//uses class DistPar from ch14listing2Path.java

//uses class Pathvertex from ch14listing2Path.java

class AllPathsGraph
{
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private PathVertex vertexList[];
	private int adjMat[][];
	private int nVerts;
	private int nTree;				//number of verts in tree
	private DistPar sPath[];		//array for shortest path data
	private int currentVert;		
	private int startToCurrent;		//distance to currentVert
	
	public AllPathsGraph()
	{
		vertexList = new PathVertex[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		nTree = 0;
		for(int j = 0; j<MAX_VERTS; j++)
			for(int k = 0; k<MAX_VERTS; k++)
				adjMat[j][k] = INFINITY;
		sPath = new DistPar[MAX_VERTS];
	}
	
	public int getNumVerts()
		{ return nVerts; }
	
	public void addVertex(char lab)
		{ vertexList[nVerts++] = new PathVertex(lab); }
	
	public void addEdge(int start, int end, int weight)
	{
		adjMat[start][end] = weight;	//directed edges
	}
	
	//find all shortest paths
	public void path(int start)
	{
		System.out.println("Shortest paths for " + vertexList[start].label + ":");
		int startTree = start;		//start at specified vertex
		vertexList[startTree].isInTree = true;
		nTree = 1;				//put it in tree
		
		// transfer row of distances from adjMat to sPath
		for(int j = 0; j < nVerts; j++)
		{
			int tempDist = adjMat[startTree][j];
			sPath[j] = new DistPar(startTree, tempDist);
		}
		
		//until all vertices are in the tree
		while(nTree < nVerts)
		{
			int indexMin = getMin();	//get minimum from sPath
			int minDist = sPath[indexMin].distance;
			
			if(minDist == INFINITY)
			{
				System.out.println("There are unreachable vertices.");
				break;
			}
			else
			{
				currentVert = indexMin;
				startToCurrent = sPath[indexMin].distance;
				//minimum distance from startTree is
				//to currentVert, and is startToCurrent
			}
			//put current vertex in tree
			vertexList[currentVert].isInTree = true;
			nTree++;
			adjust_sPath();			//update sPath[] array
		} //end while
		
		displayPaths();			//display sPath[] contents
		
		nTree = 0;				//clear tree
		for(int j = 0; j < nVerts; j++)
			vertexList[j].isInTree = false;
	} //end path()
	
	public int getMin()			//get entry from sPath
	{							//with minimum distance
		int minDist = INFINITY;
		int indexMin = 0;
		for(int j = 0; j<nVerts; j++)
		{
			if(!vertexList[j].isInTree && //smaller than old one
					sPath[j].distance < minDist)
			{
				minDist = sPath[j].distance;
				indexMin = j;
			}
		} //end for
		return indexMin;
	} //end getMin()
	
	public void adjust_sPath()
	{
		//adjust values in shortest-path array sPath
		int column = 0;		
		while(column < nVerts)
		{
			//if vertex already in tree, skip it
			if(vertexList[column].isInTree)
			{
				column++;
				continue;
			}
			//calculates distance for one sPath entry
				//get edge from currentVert to column
			int currentToFringe = adjMat[currentVert][column];
				//add distance from start
			int startToFringe = startToCurrent + currentToFringe;
				//get distance of sPath entry
			int sPathDist = sPath[column].distance;
			
			//compare distance from start with sPath entry
			if(startToFringe < sPathDist) //if shorter,
			{								//update sPath
				sPath[column].parentVert = currentVert;
				sPath[column].distance = startToFringe;
			}
			column++;
		}
	} //end adjust_sPath()
	
	public void displayPaths()
	{
		for(int j = 0; j<nVerts; j++)
		{
			System.out.print(vertexList[j].label + "=");
			if(sPath[j].distance == INFINITY)
				System.out.print("inf");
			else
				System.out.print(sPath[j].distance);
			char parent = vertexList[ sPath[j].parentVert ].label;
			System.out.print("(" + parent + ") ");
		}
		System.out.println();
	} //end displayPaths()
} //end class WeightedGraph


class AllPathsApp
{
	public static void main(String[] args)
	{
		AllPathsGraph theGraph = new AllPathsGraph();
		theGraph.addVertex('A');
		theGraph.addVertex('B');
		theGraph.addVertex('C');
		theGraph.addVertex('D');
		theGraph.addVertex('E');
		
		theGraph.addEdge(0, 1, 50);
		theGraph.addEdge(0, 3, 80);
		theGraph.addEdge(1, 2, 60);
		theGraph.addEdge(1, 3, 90);
		theGraph.addEdge(2, 4, 40);
		theGraph.addEdge(3, 2, 20);
		theGraph.addEdge(3, 4, 70);
		theGraph.addEdge(4, 1, 50);
		
		System.out.println("Shortest paths");
		int numVerts = theGraph.getNumVerts();
		for(int i = 0; i < numVerts; i++)
			theGraph.path(i);
		System.out.println();
	} //end main()
} //end class PathApp



























