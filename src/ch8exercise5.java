//Chapter 8, Exercise 5

/* Write a program to implement Huffman coding and decoding. It should do the
following:

Accept a text message, possibly of more than one line.
Create a Huffman tree for this message.
Create a code table.
Encode the message into binary.
Decode the message from binary back to text.

If the message is short, the program should be able to display the Huffman tree
after creating it. The ideas in Programming Projects 8.1, 8.2, and 8.3 might
prove helpful. You can use String variables to store binary numbers as arrangements
of the characters 1 and 0. Don’t worry about doing actual bit manipulation
unless you really want to. */

import java.io.*;
import java.util.*;

class HuffmanNode implements Comparable<Object>
{
	public String sData;			//data item (key)
	public Integer iData;			//frequency of character
	public HuffmanNode leftChild;
	public HuffmanNode rightChild;
	
	public void displayNode()
	{
		System.out.print("{" + sData + ", " + iData + "}");
	}
	
	public int compareTo(Object o)
	{
		HuffmanNode node = (HuffmanNode)o;
		return (this.iData - node.iData);
	}
}

class HuffmanTree
{
	private HuffmanNode root;			//first node of tree
	private String[] codeTable;
	private String huffmanCode;			//used to generate table with "0"s and "1"s
	
	public HuffmanTree(String initString)
		{
			huffmanCode = new String();
			codeTable = new String[128]; //going to keep it ASCII for simplicity
			System.out.print("Initializing Huffman tree...");
			
			//Find frequencies of each character in input string
			HashMap<Character, Integer> map = new HashMap<Character, Integer>();
			String s = initString;
			for(int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				Integer val = map.get(new Character(c));
				if(val != null)
					map.put(c, new Integer(val + 1));
				else
					map.put(c, 1);
			}
			
			//Insert each character into a node and fill a priority queue with nodes
			PriorityQueue<HuffmanNode> nodeQueue = new PriorityQueue<HuffmanNode>();
			for(Map.Entry<Character, Integer> entry : map.entrySet())
			{
				String key = Character.toString(entry.getKey());
				Integer value = entry.getValue();
				HuffmanNode tempNode = new HuffmanNode();
				tempNode.sData = key;	//node contains character
				tempNode.iData = value; //and frequency of character
				nodeQueue.add(tempNode);
			}
			
			//keep combining nodes until there is only one left (root of Huffman Tree)
			while(nodeQueue.size() > 1)
			{
				HuffmanNode temp1 = nodeQueue.remove();
				HuffmanNode temp2 = nodeQueue.remove();
				HuffmanNode newTree = new HuffmanNode();
				newTree.iData = temp1.iData + temp2.iData;
				newTree.leftChild = temp1;
				newTree.rightChild = temp2;
				nodeQueue.add(newTree);
			}
			
			//get 1 remaining node as root of entire tree
			root = nodeQueue.remove();
			
			System.out.println(" done");
			
			System.out.print("Creating code table...");
			createTable(root);
			System.out.println(" done");
			
		}
	
	public void createTable(HuffmanNode localRoot)
	{
		if(localRoot.sData != null)
		{
			char temp = localRoot.sData.charAt(0);
			int index = (int)temp;
			codeTable[index] = huffmanCode;
			System.out.println("Generated (" + codeTable[index] + ") at index " + index);
			return;
		}
		else
		{
			huffmanCode += "0";
			createTable(localRoot.leftChild);
			huffmanCode = huffmanCode.substring(0, huffmanCode.length()-1);
			
			huffmanCode += "1";
			createTable(localRoot.rightChild);
			huffmanCode = huffmanCode.substring(0, huffmanCode.length()-1);
		}
	}
	
	public String encode(String message)
	{
		String result = new String();
		//fill result with codes for each letter of message
		for(int i = 0; i < message.length(); i++)
		{
			char temp = message.charAt(i);
			int index = (int)temp;
			result += codeTable[index];
		}
		return result;
	}
	
	public String decode(String codedMessage)
	{
		String result = new String();
		HuffmanNode temp = root;
		int i = 0;
		while(i < codedMessage.length())
		{
			if(temp.sData == null) 
			{
				if(codedMessage.charAt(i) == '0') {temp = temp.leftChild;}
				else if(codedMessage.charAt(i) == '1') {temp = temp.rightChild;}
				i++;
			}
			else
			{
				result += temp.sData;
				temp = root;
			}
		}
		result += temp.sData;	//parse the last character
		
		
		return result;
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
	
	private void preOrder(HuffmanNode localRoot)
	{
		if(localRoot != null)
		{
			System.out.print(localRoot.sData + " ");
			preOrder(localRoot.leftChild);
			preOrder(localRoot.rightChild);
		}
	}
	
	private void inOrder(HuffmanNode localRoot)
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
	
	private void postOrder(HuffmanNode localRoot)
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
		Stack<HuffmanNode> globalStack = new Stack<HuffmanNode>();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println(
		".......................................................");
		while(isRowEmpty==false)
		{
			Stack<HuffmanNode> localStack = new Stack<HuffmanNode>();
			isRowEmpty = true;
			
			for(int j = 0; j < nBlanks; j++)
				System.out.print(" ");
			
			while(globalStack.isEmpty() == false)
			{
				HuffmanNode temp = globalStack.pop();
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
} //end class HuffmanTree

class HuffmanTreeApp
{
	public static void main(String[] args) throws IOException
	{
		int value;
		String initString = "Alleluia, praise the LORD!!!";
		String encoded = new String();
		String decoded = new String();
		HuffmanTree theTree = new HuffmanTree(initString);
		
		while(true)
		{
			System.out.print("Enter first letter of decode, encode, show,"
					+ " or traverse: ");
			int choice = getChar();
			switch(choice)
			{
			case 'e':
				encoded = theTree.encode(initString);
				System.out.println(encoded);
				break;
			case 'd':
				decoded = theTree.decode(encoded);
				System.out.println(decoded);
				break;
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