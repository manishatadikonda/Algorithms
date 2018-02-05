import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


public class LCS_Manisha_Tadikonda_manishat{
	private static String sequence;

	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
		PrintWriter writer = new PrintWriter("output.txt");	
		String input1 = reader.readLine();
		String input2 = reader.readLine();
		writer.println(lcs(input1, input2));
		writer.println(sequence);
		writer.close();      
		reader.close();
	}

	private static int lcs(String input1, String input2) {
		int length1 = input1.length();
		int length2 = input2.length();

		char[] string1 = input1.toCharArray();
		char[] string2 = input2.toCharArray();

		int[][] LCS = new int[length1+1][length2+1];
		for(int i = 0; i <= length1; i++){
			LCS[i][0] = 0;
		}
		for(int i = 1; i <= length1; i++){
			LCS[0][i] = 0;
			for(int j = 1; j <= length2; j++){
				if(string1[i-1] == string2[j-1]){
					LCS[i][j] = LCS[i-1][j-1]+1;
				}
				else{
					LCS[i][j] = Math.max(LCS[i-1][j], LCS[i][j-1]);
				}
			}
		}
		StringBuilder builder = new StringBuilder();
		while(length1 > 0 && length2 > 0){		
			if(LCS[length1][length2] == LCS[length1-1][length2]){
				length1--;
			}
			else if(LCS[length1][length2] == LCS[length1][length2-1]){
				length2--;
			}
			else{
				builder.append(string1[length1-1]);
				length1--;
				length2--;
			}
		}
		sequence = builder.reverse().toString();
		return LCS[input1.length()][input2.length()];
	}
}
