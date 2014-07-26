//Chapter 8, Exercise 1

/* Start with the tree.java program (Listing 8.1) and modify it to create a binary
tree from a string of letters (like A, B, and so on) entered by the user. Each
letter will be displayed in its own node. Construct the tree so that all the nodes
that contain letters are leaves. Parent nodes can contain some non-letter
symbol like +. Make sure that every parent node has exactly two children.
Don’t worry if the tree is unbalanced. Note that this will not be a search tree;
there’s no quick way to find a given node. */

import java.io.*;
import java.util.*;

class AbcNode
{
	public String sData;			//data item (key)
	public AbcNode leftChild;
	public AbcNode rightChild;
	
	public void displayNode()
	{
		System.out.print("{" + sData + "}");
	}
}

class AbcTree
{
	private AbcNode root;			//first node of tree
	
	public AbcTree(String initString)
		{ 
			root = null;
			
			//Parse the input string into an array of single-letter nodes
			AbcNode[] nodeArray = new AbcNode[initString.length()];
			for(int i = 0; i < initString.length(); i++)
			{
				nodeArray[i] = new AbcNode();
				nodeArray[i].sData = String.valueOf(initString.charAt(i));
			}
			
			//Generate "+" nodes and add children iteratively
			AbcNode plusNode = new AbcNode();
			plusNode.sData = "+";
			plusNode.leftChild = nodeArray[0];
			plusNode.rightChild = nodeArray[1];
			
			for(int j = 2; j < nodeArray.length; j++)
			{
				AbcNode newNode = new AbcNode();
				newNode.sData = "+";
				newNode.leftChild = plusNode;
				newNode.rightChild = nodeArray[j];
				plusNode = newNode;
			}
			root = plusNode;
		}
	
	public void traverse(int traverseType)
	{
		switch(traverseType)
		{
		case 1: System.out.print("\nPreorder traversal: ");
				preOrder(root);
				break;
		case 2: System.out.print("\nInorder traversal: ");
				inOrder(root);
				break;
		case 3: System.out.print("\nPostorder traversal: ");
				postOrder(root);
				break;
		}
		System.out.println("");
	}
	
	private void preOrder(AbcNode localRoot)
	{
		if(localRoot != null)
		{
			System.out.print(localRoot.sData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
	
	private void inOrder(AbcNode localRoot)
	{
		if(localRoot != null)
		{
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.sData + " ");
			inOrder(localRoot.rightChild);
		}
	}
	
	private void postOrder(AbcNode localRoot)
	{
		if(localRoot != null)
		{
			postOrder(localRoot.leftChild);
			postOrder(localRoot.rightChild);
			System.out.print(localRoot.sData + " ");
		}
	}
	
	public void displayTree()
	{
		Stack<AbcNode> globalStack = new Stack<AbcNode>();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println(
		".......................................................");
		while(isRowEmpty==false)
		{
			Stack<AbcNode> localStack = new Stack<AbcNode>();
			isRowEmpty = true;
			
			for(int j = 0; j < nBlanks; j++)
				System.out.print(" ");
			
			while(globalStack.isEmpty() == false)
			{
				AbcNode temp = globalStack.pop();
				if(temp != null)
				{
					System.out.print(temp.sData);
					localStack.push(temp.leftChild);
					localStack.push(temp.rightChild);
					
					if(temp.leftChild != null ||
							temp.rightChild != null)
						isRowEmpty = false;
				}
				else
				{
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j = 0; j < nBlanks*2 - 2; j++)
					System.out.print(" ");
			} //end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty() == false)
				globalStack.push( localStack.pop() );
		} //end while isRowEmpty is false
		System.out.println(
		".......................................................");
	} //end displayTree()
} //end class AbcTree

class AbcTreeApp
{
	public static void main(String[] args) throws IOException
	{
		int value;
		String initString = "ABCDEFG";
		AbcTree theTree = new AbcTree(initString);
		
		while(true)
		{
			System.out.print("Enter first letter of show"
					+ " or traverse: ");
			int choice = getChar();
			switch(choice)
			{
			case 's':
				theTree.displayTree();
				break;
			case 't':
				System.out.print("Enter type 1, 2, or 3: ");
				value = getInt();
				theTree.traverse(value);
				break;
			default:
				System.out.print("Invalid entry!\n");
			} // end switch
		} // end while
	} // end main()
	
	public static String getString() throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}
	
	public static char getChar() throws IOException
	{
		String s = getString();
		return s.charAt(0);
	}
	
	public static int getInt() throws IOException
	{
		String s = getString();
		return Integer.parseInt(s);
	}
}