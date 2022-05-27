package Main;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;


import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;


//Main Class that runs the program
public class MathProblem {

    //Variables
    static boolean isDone = false; //False if the problem hasn't been solved
    static int start = 1; //First Value to be tried
    public static int maxPlace = 0; //Last Value to be tried
    public static ArrayList<Integer> triedNum = new ArrayList<>();
    public static LinkedList<Integer> triedNumTemp = new LinkedList<>();
    static MathProblem mathProblem = new MathProblem();

    public static void main(String[] args) {
        //Retreving data for "start" and "maxPlace" from file
        System.out.println("Currently Retrieving Data...");
        try {
            start = Integer.parseInt(FileManager.readFile("MaxPlaceSize.txt"));
            maxPlace = Integer.parseInt(FileManager.readFile("MaxPlaceSize.txt")) + 50000;
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
            calcEquation.setB(start);
            calcEquation.calcPattern(triedNum, start, mathProblem);
            isDone = calcEquation.isDone;

            //Compile more of the array while running
            if (start > maxPlace) {
                CompileManager.writeWhileRun(false);
            }
            start++;
        }
        //If loop was exited
        System.out.println(start - 1 + "worked!");
    }

}


//The Program that manages the automation of writing the file and the delegate for compiling the array
class CompileManager {
    static MathProblem mathProblem = new MathProblem();

    //Appends the new array values into "TestFile.txt"
    //qCompile if true compiles the array and if false updates "MaxPlaceSize.txt" to the current value
    public static void writeWhileRun(boolean qCompile) {
        System.out.println("Currently Coping Array...");
        FileManager.appendFile("TestFile.txt", mathProblem.triedNumTemp);
        System.out.println("Done.");
        mathProblem.triedNumTemp.clear();
        if (qCompile) compileArray();
        if (!qCompile) {
            FileManager.writeFile("MaxPlaceSize.txt", Integer.toString(mathProblem.maxPlace));
            mathProblem.maxPlace += 50000;
        }
    }

    //Writes the array as a delegate
    public static void compileArray() {
        System.out.println("Currently compling array...");
        FileManager.setFile("TestFile.txt");
        mathProblem.triedNum = FileManager.writeIntArray(mathProblem.triedNum);
        System.out.println("Done.");
    }
}


//The Program that carries out all the calculation for the conjunture
class calcEquation {
    static boolean isDone = false;
    static int b;

    //Sets static variable 'b' to given value
    public static void setB(int ba) {
        b = ba;
    }


    //Main method
    public static boolean calcPattern(ArrayList<Integer> triedNums, int i, MathProblem mathProblem) {
        int j = i;

        //Checks to see if the number is already in the system
        for (int a = mathProblem.triedNum.size() - 1; a >= 0; a--) {
            if (j == mathProblem.triedNum.get(a)) {
                isDone = false;
                if (mathProblem.triedNum.contains(b)) {
                } else {
                    mathProblem.triedNum.add(b);
                    mathProblem.triedNumTemp.add(b);
                }
                return false;
            }
        }


        //Shorten the array list
        reveiwArray(false, false, mathProblem);

        //Re-Checking
        if (i % 2 == 1) {
            j = 3 * i + 1;
            calcEquation.calcPattern(triedNums, j, mathProblem); //Has not failed so trying it again
        } else {
            j /= 2;
            calcEquation.calcPattern(triedNums, j, mathProblem); //Has not failed so trying it again
        }

        //Returning result
        if (!isDone) {
            //Adding failed number to array list
            if (triedNums.contains(b)) {
            } else {
                triedNums.add(b);
                MathProblem.triedNumTemp.add(b);
            }
        }
        return isDone;
    }

    //Shortens the array list by deleting unneccary numbers
    //Boolean one activates method 1; Boolean two activates method 2
    private static void reveiwArray(boolean one, boolean two, MathProblem mathProblem) {
        int begSize = mathProblem.triedNum.size(); //Temporary test of usefullness

        if (one) {
            //Method 1:Delete numbers that are even and can be divided by two(continuasly) to equal another number in the array
            for (int f = mathProblem.triedNum.size() - 1; f >= 0; f--) {
                int place = mathProblem.triedNum.get(f);
                checkDivTwo(place, mathProblem.triedNum);
            }
        }

        //Expieremental
        if (two) {
            //Method 2: Delete
            for (int f = mathProblem.triedNum.size() - 1; f >= 0; f--) {
                int place = mathProblem.triedNum.get(f);
                if (mathProblem.triedNum.contains(place * 3 + 1)) {
                    mathProblem.triedNum.remove(mathProblem.triedNum.indexOf(place));
                }
            }
        }

        int endSize = mathProblem.triedNum.size(); //Temporary test of usefullness

    }


    private static void checkDivTwo(int place, ArrayList<Integer> triedNum) {
        if (place % 2 == 0) {
            int placeDivideByTwo = place / 2;
            if (triedNum.contains(placeDivideByTwo)) {
                triedNum.remove(triedNum.indexOf(place));
            } else {
                checkDivTwo(place, triedNum);
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

    //Prepares a LinkedList to be converted into a .txt file
    public static String arrayClean(LinkedList<Integer> triedNumTemp) {
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

    //Prepares an ArrayList to be converted into a .txt file
    public static String arrayClean(ArrayList<Integer> triedNum) {
        String arrayTemp = triedNum.toString();
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

    //Writes an Integer Array List from a File
    public static ArrayList<Integer> writeIntArray(ArrayList<Integer> triedNums) {
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
    public static ArrayList<String> getArrayList(File f) throws FileNotFoundException {
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
        String arrayTemp = FileManager.arrayClean(triedNum);
        FileManager.writeFile(fileName, arrayTemp);
    }
}






