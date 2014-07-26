//Implement Floyd's algorithm. You should be able to enter
//graphs of arbitrary complexity into main().

//uses class PathVertex from ch14listing2Path.java

class FloydGraph
{
	private final int MAX_VERTS = 20;
	private final int INFINITY = 1000000;
	private PathVertex vertexList[];
	private int adjMat[][];
	private int nVerts;
	
	public FloydGraph()
	{
		vertexList = new PathVertex[MAX_VERTS];
		adjMat = new int[MAX_VERTS][MAX_VERTS];
		nVerts = 0;
		for(int j = 0; j<MAX_VERTS; j++)
			for(int k = 0; k<MAX_VERTS; k++)
				adjMat[j][k] = INFINITY;
	}
	
	public void addVertex(char lab)
		{ vertexList[nVerts++] = new PathVertex(lab); }
	
	public void addEdge(int start, int end, int weight)
	{
		adjMat[start][end] = weight;	//directed edges
	}
	
	//creates the transitive closure of a graph
	public int[][] floyd()
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
				if(newAdjMat[y][x] < INFINITY)
				{
					for(int z = 0; z < nVerts; z++)
					{
						if(newAdjMat[z][y] < INFINITY)
						{
							int newValue = newAdjMat[y][x] + newAdjMat[z][y];
							int oldValue = newAdjMat[z][x];
							if(newValue < oldValue)
								newAdjMat[z][x] = newValue;
						}
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
			{
				if(matrix[i][j] == INFINITY)
					System.out.print("-- ");
				else
					System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
} //end class FloydGraph


class FloydApp
{
	public static void main(String[] args)
	{
		FloydGraph theGraph = new FloydGraph();
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
		
		theGraph.displayMatrix(theGraph.getAdjMat());
		System.out.println();
		int[][] newMatrix = theGraph.floyd();
		theGraph.displayMatrix(newMatrix);
	} //end main()
} //end class FloydApp