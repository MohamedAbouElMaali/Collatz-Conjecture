package Main;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;


import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


//Main Class that runs the program
public class MathProblem {

    //Variables
    static boolean isDone = false; //False if the problem hasn't been solved
    static int start = 1; //First Value to be tried
    public static int maxPlace = 0; //Last Value to be tried
    public static ArrayList<Integer> triedNum = new ArrayList<>(); //The list of numbers already tried
    public static LinkedList<Integer> newTriedNum = new LinkedList<>(); //The list of new numbers that have been tried so they can be put into the database
    public static int tryIntervals = 0; //Amount of numbers to try before saving the data to the file and updating the array triedNum
    static MathProblem mathProblem = new MathProblem();

    public static void main(String[] args) {
        //Retreving data for "start" and "maxPlace" from file(StartingData.txt)
        System.out.println("Currently Retrieving Data...");
        try {
            start = Integer.parseInt(FileManager.readFile("StartingData.txt"));
            maxPlace = Integer.parseInt(FileManager.readFile("StartingData.txt")) + tryIntervals;
            System.out.println("Success.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Faliure");
        }

        //Coping Array From File
        CompileManager.writeWhileRun(true);


        //Main Loop
        while (!isDone) {
            System.out.println(start - 1 + " did not work.\n"); //System.out.println("Currently attempting " + i);
            calcEquation.setElmToTest(start);
            calcEquation.calcPattern(triedNum, start, mathProblem);
            isDone = calcEquation.isDone;

            //Compile more of the array while running
            if (start > maxPlace) {
                CompileManager.writeWhileRun(false);
                calcEquation.reveiwArray(true, false, mathProblem);
                FileManager.compileArrayToFile(triedNum, "AttemptedData.txt");
                System.exit(0);
            }
            start++;
        }
        //If loop was exited
        System.out.println(start - 1 + " worked!");
    }

}


//The Program that manages the automation of writing the file and the delegate for compiling the array
class CompileManager {
    static MathProblem mathProblem = new MathProblem();

    //Appends the new array values into "AttemptedData.txt"
    //qCompile if true compiles the array and if false updates "StartingData.txt" to the current value
    public static void writeWhileRun(boolean qCompile) {
        if (qCompile) {
            System.out.println("Currently Coping Array...");
            compileArray();
            System.out.println("Done.");
        }
        if (!qCompile) {
            FileManager.appendFile("AttemptedData.txt", MathProblem.newTriedNum);
            MathProblem.newTriedNum.clear();
            compileArray();
            FileManager.writeFile("StartingData.txt", Integer.toString(MathProblem.maxPlace));
            MathProblem.maxPlace += mathProblem.tryIntervals;
        }
    }

    //Writes the array as a delegate
    public static void compileArray() {
        System.out.println("Currently compling array...");
        FileManager.setFile("AttemptedData.txt"); //Setting the file in the File Manager Class
        MathProblem.triedNum = (ArrayList<Integer>) (FileManager.writeIntArray(MathProblem.triedNum));
        System.out.println("Done.");
    }
}


//The Program that carries out all the calculation for the conjunture
class calcEquation {
    static boolean isDone = false;
    static int elmToTest;

    //Sets static variable 'elmToTest' to given value
    public static void setElmToTest(int elmToTestTemp) {
        elmToTest = elmToTestTemp;
    }


    //Main method
    public static boolean calcPattern(ArrayList<Integer> triedNums, int testingNumber, MathProblem mathProblem) {
        int testingNumberTemp = testingNumber;

        //Checks to see if the number is already in the system
        for (int size = mathProblem.triedNum.size() - 1; size > 0; size--) {
            if (testingNumber == mathProblem.triedNum.get(size)) {
                if (!(mathProblem.triedNum.contains(elmToTest))) mathProblem.newTriedNum.add(elmToTest);
                return false;
            }
        }

        //Shorten the array list
        //reveiwArray(false, false, mathProblem);

        //Re-Checking
        if (testingNumber % 2 == 1) {
            testingNumberTemp = (3 * testingNumber) + 1;
            calcEquation.calcPattern(triedNums, testingNumberTemp, mathProblem); //Has not failed so trying it again
        } else {
            testingNumberTemp /= 2;
            calcEquation.calcPattern(triedNums, testingNumberTemp, mathProblem); //Has not failed so trying it again
        }

        //Returning result
        if (!isDone) {
            //Adding failed number to array list
            if (!(triedNums.contains(elmToTest))) MathProblem.newTriedNum.add(elmToTest);
        }
        return isDone;
    }

    //Shortens the array list by deleting unneccary numbers
    //Boolean one activates method 1; Boolean two activates method 2
    public static void reveiwArray(boolean one, boolean two, MathProblem mathProblem) {
        int begSize = mathProblem.triedNum.size(); //Temporary test of usefullness

        System.out.println("Currently Reveiwing Array...");

        //Method 1:Delete numbers that are even and can be divided by two(continuasly) to equal another number in the array
        if (one) {
            System.out.print("Attempting Method One");
            int temp = mathProblem.triedNum.size()-1;
            for (int f = mathProblem.triedNum.size() - 1; f > 0; f--) {
                int place = mathProblem.triedNum.get(f);
                checkDivTwo(place, mathProblem.triedNum, 1);
                if (f == temp - 10000) {
                    System.out.print('.');
                    temp = temp - 10000;
                }
            }
            System.out.println();
        }

        //Expieremental
        //Method 2: Delete numbers that are even and when subtracted by one and divided by three can be found in the array(triedNum)
        if (two) {
            System.out.println("Attempting Method Two");
            //Method 2: Delete
            for (int index = mathProblem.triedNum.size() - 1; index > 0; index--) {
                int place = mathProblem.triedNum.get(index);
                if (place%2 == 0) {
                    if (mathProblem.triedNum.contains((place-1)/3)) {
                        mathProblem.triedNum.remove(index);
                    }
                }
            }
        }

        System.out.println("Array Reveiw Complete.\n");

        int endSize = mathProblem.triedNum.size(); //Temporary test of usefullness

    }

    //Helper method for review array method 1
    //Checks to see if "place" is even
    //If it is it will see if place/2 is found within triedNum.
    //If it is found it will delete it and endn the method.
    //If it isn't found it will recall the method
    //If it isn't it will return and end the method.
    private static void checkDivTwo(int place, ArrayList<Integer> triedNum, int stackNum) {
        if (place % 2 == 0) {
            int placeDividedByProduct = place / (2*stackNum);
            if (triedNum.contains(placeDividedByProduct)) {
                triedNum.remove(triedNum.indexOf(place));
            } else {
                checkDivTwo(place, triedNum, stackNum + 1);
            }

        } else {
            return;
        }
    }
}


//The Program that deals with all things file related
class FileManager {
    static File triedNumsInfo = null;
    static FileWriter triedNumsWriter;
    static RandomAccessFile appendTool;

    //REQUIRED: SET FILE BEFORE CALLING ANY METHODS
    public static void setFile(String fileName) {
        triedNumsInfo = new File(fileName);
    }

    //Prepares a List of type Integer to be converted into a .txt file
    private static String arrayClean(List<Integer> triedNumTemp) {
        String arrayTemp = triedNumTemp.toString();
        for (char letter : arrayTemp.toCharArray()) {
            if (letter == ',') {
                int index = arrayTemp.indexOf(letter);
                arrayTemp = arrayTemp.substring(0, index) + "\n" + arrayTemp.substring(index + 2, arrayTemp.length());
            } else if (letter == '[') {
                int index = arrayTemp.indexOf(letter);
                arrayTemp = arrayTemp.substring(0, index) + arrayTemp.substring(index + 1, arrayTemp.length());
            } else if (letter == ']') {
                int index = arrayTemp.indexOf(letter);
                arrayTemp = arrayTemp.substring(0, index);
            }
        }
        return arrayTemp;
    }

    //Writes a file from a given string
    public static void writeFile(String fileName, String arrayTemp) {
        try {
            triedNumsInfo = new File(fileName);
            triedNumsWriter = new FileWriter(triedNumsInfo);
            triedNumsWriter.write(arrayTemp);
            triedNumsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Adds to the file a given LinkedList
    public static void appendFile(String fileName, LinkedList<Integer> triedNumTemp) {
        try {
            triedNumsInfo = new File(fileName);
            triedNumsWriter = new FileWriter(triedNumsInfo, true);
            String arrayTemp = arrayClean(triedNumTemp);
            triedNumsWriter.write("\n");
            triedNumsWriter.write(arrayTemp);
            triedNumsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Returns the contents of a file in the form of a string
    public static String readFile(String fileName) throws FileNotFoundException {
        File fileToRead = new File(fileName);
        Scanner s = new Scanner(fileToRead);
        String toReturn = "";
        if (s.hasNextLine()) {
            toReturn = s.nextLine();
        }
        s.close();
        return toReturn;
    }

    //Writes an Integer List from a File
    public static List<Integer> writeIntArray(List<Integer> triedNums) {
        try {
            triedNums.clear();
            ArrayList<String> temp = getArrayList(triedNumsInfo);
            for (String index : temp) {
                Integer adding = Integer.parseInt(index);
                triedNums.add(adding);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return triedNums;
    }

    //Returns a String ArrayList from a file
    private static ArrayList<String> getArrayList(File f) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        Scanner s = new Scanner(f);

        boolean testing = s.hasNextLine();
        while (testing) {
            //System.out.println(s.nextLine());
            String a = s.nextLine();
            if (a != "") {
                list.add(a);
            }
            testing = s.hasNextLine();
        }
        s.close();
        return list;
    }

    //Compiles an Array into a given file
    public static void compileArrayToFile(ArrayList<Integer> triedNum, String fileName) {
        String arrayTemp = arrayClean(triedNum);
        writeFile(fileName, arrayTemp);
    }
}






