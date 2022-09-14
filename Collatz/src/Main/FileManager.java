
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//The Program that deals with all things file related
class FileManager {
    File triedNumsInfo = null; //The File
    FileWriter triedNumsWriter;
    //static RandomAccessFile appendTool;

    //Prepares a List of type Integer to be converted into a .txt file
    private String arrayClean(List<Integer> triedNumTemp) {
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
    public void writeFile(String fileName, String cleanArray) {
        try {
            triedNumsInfo = new File(fileName);
            triedNumsWriter = new FileWriter(triedNumsInfo);
            triedNumsWriter.write(cleanArray);
            triedNumsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Adds to the file a given LinkedList
    public void appendFile(String fileName, LinkedList<Integer> triedNumTemp) {
        try {
            triedNumsInfo = new File(fileName);
            triedNumsWriter = new FileWriter(triedNumsInfo, true);
            String cleanArray = arrayClean(triedNumTemp);
            triedNumsWriter.write("\n");
            triedNumsWriter.write(cleanArray);
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

    //Returns an Integer List from a File
    public List<Integer> writeIntArray(String fileName, List<Integer> triedNums) {
        try {
            triedNums.clear();
            triedNumsInfo = new File(fileName);
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

    //Returns an ArrayList from a file
    private ArrayList<String> getArrayList(File f) throws FileNotFoundException {
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
    public void compileArrayToFile(ArrayList<Integer> triedNum, String fileName) {
        String arrayTemp = arrayClean(triedNum);
        writeFile(fileName, arrayTemp);
    }

    //The Program that manages the automation of writing the file and the delegate for compiling the array
    class CompileManager {
        //Appends the new array values into "AttemptedData.txt"
        //qCompile if true compiles the array and if false updates "StartingData.txt" to the current value
        public void writeWhileRun(boolean qCompile) {
            if (qCompile) {
                System.out.println("Currently Coping Array...");
                compileArray();
                System.out.println("Done.");
            }
            if (!qCompile) {
                Data.fileManager.appendFile(Data.attemptedData, Data.newTriedNum);
                Data.newTriedNum.clear();
                compileArray();
                Data.fileManager.writeFile(Data.startingData, Integer.toString(Data.maxPlace));
                Data.maxPlace += Data.tryIntervals;
            }
        }

        //Writes the array as a delegate
        public void compileArray() {
            System.out.println("Currently compling array...");
            Data.triedNum = (ArrayList<Integer>) (Data.fileManager.writeIntArray(Data.attemptedData, Data.triedNum));
            System.out.println("Done.");
        }
    }
}