//Stores all variables and data

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class Data {
    //Variables
    static boolean isDone = false; //False if the problem hasn't been solved
    static int start = 1; //First Value to be tried
    public static int maxPlace = 0; //Last Value to be tried
    public static ArrayList<Integer> triedNum = new ArrayList<>(); //The list of numbers already tried
    public static LinkedList<Integer> newTriedNum = new LinkedList<>(); //The list of new numbers that have been tried so they can be put into the database
    public static int tryIntervals = 100; //Amount of numbers to try before saving the data to the file and updating the array triedNum
    static MathProblem mathProblem = new MathProblem();
    static FileManager fileManager = new FileManager();
    static FileManager.CompileManager compileManager = fileManager.new CompileManager();

    static String attemptedData = "/files/AttemptedDataTest.txt";
    static String startingData = "/files/StartingDataTest.txt";
    String fileName3 = "";
}