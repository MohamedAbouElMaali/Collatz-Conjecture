package Main;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;


//Main Class that runs the program
public class MathProblem {

    Data data = new Data();

    public static void main(String[] args) {
        //Retreving data for "start" and "maxPlace" from file(StartingData.txt)
        System.out.println("Currently Retrieving Data...");
        try {
            Data.start = Integer.parseInt(Data.fileManager.readFile(Data.startingData));
            Data.maxPlace = Data.start + Data.tryIntervals;
            System.out.println("Success.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Faliure");
        }

        //Coping Array From File
        Data.compileManager.writeWhileRun(true);


        //Main Loop
        while (!isDone) {
            System.out.println(Data.start - 1 + " did not work.\n"); //System.out.println("Currently attempting " + i);
            calcEquation.setElmToTest(Data.start);
            calcEquation.calcPattern(Data.triedNum, Data.start);
            Data.isDone = calcEquation.isDone;

            //Compile more of the array while running
            if (Data.start > Data.maxPlace) {
                Data.compileManager.writeWhileRun(false);
                calcEquation.reveiwArray(false, false);
                Data.fileManager.compileArrayToFile(Data.triedNum, Data.attemptedData);
                System.exit(0);
            }
            Data.start++;
        }
        //If loop was exited
        System.out.println(Data.start - 1 + " worked!");
    }

}



//The Program that carries out all the calculation for the conjunture
class calcEquation {
    boolean isDone = false;
    int elmToTest;

    //Sets variable 'elmToTest' to given value
    public void setElmToTest(int elmToTestTemp) {
        elmToTest = elmToTestTemp;
    }


    //Main method
    public boolean calcPattern(ArrayList<Integer> triedNums, int testingNumber) {
        int testingNumberTemp = testingNumber;

        //Checks to see if the number is already in the system
        for (int size = Data.triedNum.size() - 1; size > 0; size--) {
            if (testingNumber == Data.triedNum.get(size)) {
                if (!(Data.triedNum.contains(elmToTest))) Data.newTriedNum.add(elmToTest);
                return false;
            }
        }

        //Shorten the array list
        //reveiwArray(false, false, mathProblem);

        //Re-Checking
        if (testingNumber % 2 == 1) {
            testingNumberTemp = (3 * testingNumber) + 1;
            calcEquation.calcPattern(triedNums, testingNumberTemp); //Has not failed so trying it again
        } else {
            testingNumberTemp /= 2;
            calcEquation.calcPattern(triedNums, testingNumberTemp); //Has not failed so trying it again
        }

        //Returning result
        if (!isDone) {
            //Adding failed number to array list
            if (!(triedNums.contains(elmToTest))) Data.newTriedNum.add(elmToTest);
        }
        return isDone;
    }

    //Shortens the array list by deleting unneccary numbers
    //Boolean one activates method 1; Boolean two activates method 2
    public void reveiwArray(boolean one, boolean two) {
        int begSize = Data.triedNum.size(); //Temporary test of usefullness

        System.out.println("Currently Reveiwing Array...");

        //Method 1:Delete numbers that are even and can be divided by two(continuasly) to equal another number in the array
        if (one) {
            System.out.print("Attempting Method One");
            int temp = Data.triedNum.size()-1;
            for (int f = Data.triedNum.size() - 1; f > 0; f--) {
                int place = Data.triedNum.get(f);
                checkDivTwo(place, Data.triedNum, 1);
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
            for (int index = Data.triedNum.size() - 1; index > 0; index--) {
                int place = Data.triedNum.get(index);
                if (place%2 == 0) {
                    if (Data.triedNum.contains((place-1)/3)) {
                        Data.triedNum.remove(index);
                    }
                }
            }
        }

        System.out.println("Array Reveiw Complete.\n");

        int endSize = Data.triedNum.size(); //Temporary test of usefullness

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