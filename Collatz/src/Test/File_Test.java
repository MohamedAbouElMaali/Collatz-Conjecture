package Test;
import java.util.ArrayList;

import Main.MathProblem;

public class File_Test {

	public static void main(String[] args) {
		ArrayList<Integer> testing = new ArrayList<>();
		FileManager.setFile("Test.txt");
		System.out.println("1");
		FileManager.writeIntArray(testing);
		System.out.println("2");
		CalcEquation.reveiwArray(true, false, testing);
		System.out.println("3");
//		String arrayTemp = Integer.toString(testing.size());
//		System.out.println("4");
//		FileManager.writeFile("Test.txt", arrayTemp);
//		System.out.println("5");
//		FileManager.appendFile("Test.txt", Integer.toString(testing.size()));
//		System.out.println("Done.");
	}

}






