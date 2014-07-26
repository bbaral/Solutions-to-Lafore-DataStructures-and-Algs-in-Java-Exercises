//Chapter 6, Exercise 5: Implement a recursive approach to showing all the
//teams that can be created from a group (n things taken k at a time).

public class ch6exercise5
{
	public static void main(String[] args)
	{
		String sequence = "";
		int teamSize = 15;
		int groupSize = 7;
		showTeams(teamSize, groupSize, sequence, 'A', groupSize-1);
	}
	

	
	public static void showTeams(int teamSize, int groupSize, String sequence, char letter, int evalSize)
	{
		if(groupSize > teamSize || teamSize < 0 || groupSize < 0) return;
		
		sequence += Character.toString(letter);
		letter++;
		showTeams(teamSize-1, groupSize-1, sequence, letter, evalSize); //Left call
		sequence = sequence.substring(0, sequence.length()-1);
		
		if(sequence.length() == evalSize) System.out.println(sequence + (char)(letter-1));
		showTeams(teamSize-1, groupSize, sequence, letter, evalSize); //Right call
		letter--;		
	}
	

}
