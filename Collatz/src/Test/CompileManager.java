package Test;
import Main.MathProblem;

//The Program that manages the automation of writing the file and the delegate for compiling the array
public class CompileManager {
	static MathProblem mathProblem = new MathProblem();

	//Appends the new array values into "Test.txt"
	//qCompile if true compiles the array and if false updates "MaxPlaceSize.txt" to the current value
	public static void writeWhileRun(boolean qCompile) {
		System.out.println("Currently Coping Array...");
		FileManager.appendFile("Test.txt", mathProblem.triedNumTemp);
		System.out.println("Done.");
		mathProblem.triedNumTemp.clear();
		if (qCompile) compileArray();
		if (!qCompile) {FileManager.writeFile("MaxPlaceSize.txt", Integer.toString(mathProblem.maxPlace)); mathProblem.maxPlace += 50000; }
	}

	//Writes the array as a delegate
	public static void compileArray() {
		System.out.println("Currently compling array...");
		FileManager.setFile("Test.txt");
		mathProblem.triedNum = FileManager.writeIntArray(mathProblem.triedNum);
		System.out.println("Done.");
	}
}






