package Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

//The Program that deals with all things file related
public class FileManager {
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
	
	//Adds to the file a given LinkedList
	public static void appendFile(String fileName, String arrayTemp) {
		try {
			triedNumsInfo = new File(fileName);
			triedNumsWriter = new FileWriter(triedNumsInfo, true);
			triedNumsWriter.write("\n");
			triedNumsWriter.write(arrayTemp);
			triedNumsWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	//Returns the contents of a file in the form of a string
	public static String readFile(String fileName) throws FileNotFoundException{
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
			for (String index: temp) {
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






