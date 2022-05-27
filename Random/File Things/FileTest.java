import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import java.util.Scanner;


import java.util.ArrayList;


public class FileTest {

	public static void main(String[] args) {

		ArrayList<Integer> triedNums = new ArrayList<Integer>();

		for (int i = 0; i < 7; i++) {
			int val = (int) (Math.random()*7);
			triedNums.add(val);
		}

		/*
		//FileStuff.compileArrayToFile(triedNums, "TestFile.txt");
		String arrayTemp = FileStuff.arrayClean(triedNums);
		FileStuff.writeFile("TestFile.txt", arrayTemp);
		FileStuff.writeIntArray(triedNums);
		System.out.println(triedNums);

		 */

		FileStuff.appendFile("InfoPageTest.txt", triedNums, 7);

		//FileStuff.writeFile("InfoPage.txt", "Hello");

	}


}

class FileStuff {
	static File triedNumsInfo = null;
	static FileWriter triedNumsWriter;
	static RandomAccessFile appendTool;
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

	public static void appendFile(String fileName, ArrayList<Integer> input, int off) throws IOException {
		try {
			triedNumsInfo = new File(fileName);
			triedNumsWriter = new FileWriter(triedNumsInfo, true);
			String arrayTemp = arrayClean(input);
			triedNumsWriter.write("\n");
			triedNumsWriter.write(arrayTemp);
			triedNumsWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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

	public static ArrayList<String> getArrayList(File f) throws FileNotFoundException {
		Scanner s;
		ArrayList<String> list = new ArrayList<String>();
		s = new Scanner(f);

		boolean testing = s.hasNextLine();
		while (testing) {
			//System.out.println(s.nextLine());
			String a = s.nextLine();
			list.add(a);
			testing = s.hasNextLine();
		}
		s.close();
		return list;
	}

	public static ArrayList<Integer> complieFileToArray(String fileName, String arrayTemp, ArrayList<Integer> triedNums) throws FileNotFoundException {
		return FileStuff.writeIntArray(triedNums);
	}

	public static void compileArrayToFile(ArrayList<Integer> triedNum, String fileName) {
		String arrayTemp = FileStuff.arrayClean(triedNum);

		 FileStuff.writeFile(fileName, arrayTemp);
	}
}







