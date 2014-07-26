//Chapter 8, Exercise 4

/* Write a program that transforms a postfix expression into a tree such as that
shown in Figure 8.11 in this chapter. You’ll need to modify the Tree class from
the tree.java program (Listing 8.1) and the ParsePost class from the
postfix.java program (Listing 4.8) in Chapter 4. There are more details in the
discussion of Figure 8.11.

After the tree is generated, traversals of the tree will yield the prefix, infix, and
postfix equivalents of the algebraic expression. The infix version will need
parentheses to avoid generating ambiguous expressions. In the inOrder()
method, display an opening parenthesis before the first recursive call and a
closing parenthesis after the second recursive call. */

import java.io.*;
import java.util.*;

class PostfixTree
{
	private AbcNode root;			//first node of tree
	private Stack<AbcNode> charStack;
	
	public PostfixTree(String initString)
		{ 
			//read initString and generate a tree to 
			//represent the algebraic expression
			charStack = new Stack<AbcNode>();
			
			for(int i = 0; i < initString.length(); i++)
			{
				if(initString.charAt(i) == '+'
						|| initString.charAt(i) == '-'
						|| initString.charAt(i) == '*'
						|| initString.charAt(i) == '/')
				{
					AbcNode operatorTemp = new AbcNode();
					operatorTemp.sData = Character.toString(initString.charAt(i));
					operatorTemp.rightChild = charStack.pop();
					operatorTemp.leftChild = charStack.pop();
					charStack.push(operatorTemp);
				}
				else
				{
					AbcNode operandTemp = new AbcNode();
					operandTemp.sData = Character.toString(initString.charAt(i));
					charStack.push(operandTemp);
				}
			}
			
			//pop the root of the completed tree into Postfixtree.root
			root = charStack.pop();
			
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
			System.out.print("(");
			inOrder(localRoot.leftChild);
			System.out.print(localRoot.sData + " ");
			inOrder(localRoot.rightChild);
			System.out.print(")");
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

class PostfixTreeApp
{
	public static void main(String[] args) throws IOException
	{
		int value;
		String initString = "ABCDEF+/-*+";
		PostfixTree theTree = new PostfixTree(initString);
		
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