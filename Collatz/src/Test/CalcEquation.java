package Test;
import Main.MathProblem;

import java.util.ArrayList;

//The Program that carries out all the calculation for the conjecture
public class CalcEquation {
	static boolean isDone = false; static int b;

	//Sets static variable 'b' to given value
	public static void setB(int ba) { b = ba; }


	//Main method
	public static boolean calcPattern(ArrayList<Integer> triedNums, int i, MathProblem mathProblem) {
		int j = i;

		//Checks to see if the number is already in the system
      for (int a = MathProblem.triedNum.size() - 1; a >= 0; a--) {
      	if (j == MathProblem.triedNum.get(a)) {
      		isDone = false;
     			if (MathProblem.triedNum.contains(b)) {} else { MathProblem.triedNum.add(b); MathProblem.triedNumTemp.add(b); }
      		return false;
      	}
      }

		//Shorten the array list
		//reveiwArray(true, false, mathProblem);

		//Re-Checking
		if (i%2 == 1) {
          j = 3*i + 1; CalcEquation.calcPattern(triedNums, j, mathProblem); //Has not failed so trying it again
      } else {
			j /= 2; CalcEquation.calcPattern(triedNums, j, mathProblem); //Has not failed so trying it again
      }

		//Returning result
		if (!isDone) {
			//Adding failed number to array list
			if (triedNums.contains(b)) {} else { triedNums.add(b); MathProblem.triedNumTemp.add(b);}
		}
		return isDone;
	}

	//Shortens the array list by deleting unnecessary numbers
	//Boolean one activates method 1; Boolean two activates method 2
	public static void reveiwArray(boolean one, boolean two, ArrayList<Integer> triedNum) {
		//int begSize = mathProblem.triedNum.size(); //Temporary test of usefullness

		if (one) {
			//Method 1:Delete numbers that are even and can be divided by two(continuasly) to equal another number in the array
			//System.out.println(triedNum);
			for (int f = 0; f < triedNum.size(); f++) {
				int place = triedNum.get(f);
				System.out.println(f);
				checkDivTwo(place, triedNum);
			}
			System.out.println();
		}
		
		//Expieremental
		if (two) {
			//Method 2: Delete
			for (int f = triedNum.size() - 1; f > 0; f--) {
				int place = triedNum.get(f);
				if (triedNum.contains(place * 3 + 1)) {
					triedNum.set(triedNum.indexOf(place), -1);
				}
			}
		}

		//int endSize = mathProblem.triedNum.size(); //Temporary test of usefullness

	}
	
	public static void deleteFromArrayList(int index, ArrayList<Integer> triedNum) {
		triedNum.remove(index);
	}


	private static void checkDivTwo(int place, ArrayList<Integer> triedNum) {
		if (place % 2 == 0) {
			int placeDivideByTwo = place / 2;
			if (triedNum.contains(placeDivideByTwo)) {
				triedNum.set(triedNum.indexOf(place), -1);
			} else {
				checkDivTwo(placeDivideByTwo, triedNum);
			}
				
		} else {
			return;
		}
	}
}

