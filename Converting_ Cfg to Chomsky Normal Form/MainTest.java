package toc_hw;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;



public class MainTest {
	
	public static void main(String[] args) throws IOException {
				
		ArrayList readValues = new ArrayList();//first list created while reading the file
		ArrayList mainList = new ArrayList();//Each row is stored as an object in this list.
						
		System.out.print("CFG Form\n--------");
		fileReading("CFG.txt", readValues); 
		

		for(int i = 0; i < readValues.size(); i++) {//each row is converted to an object and appended
			mainList.add(stringOperation((String) readValues.get(i)));
		}		
		
		System.out.print("\n\nEliminate €");
		while(epsilonCheck(mainList)) {
			epsilonCheck(mainList);	
			mainList = eliminateEpsilon(mainList);
		}		
		display(mainList);
		
		
		System.out.print("\n\nEliminate unit production");
		unitProduction(mainList);
		display(mainList);
		
		System.out.print("\n\nEliminate terminals");
		eliminateTerminals(mainList);
		display(mainList);
		
		System.out.print("\n\nBreak variable strings longer than 2");
		longerThanTwo(mainList);		
		display(mainList);
		
		System.out.print("\n\nCNF Form\n--------");
		display(mainList);
	}
	
	
	public static void display(ArrayList array) {
		
		ArrayList elemans = new ArrayList();
		Value tempValue;
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			elemans = tempValue.getElemans();
			if(!(tempValue.getValue().equals("E"))) {
				System.out.print(tempValue.getValue() + "-" );
				for(int j = 0; j < elemans.size(); j++) {									
					if(j == elemans.size() - 1)
						System.out.print(elemans.get(j));					
					else 
						System.out.print(elemans.get(j) + "|");
				}
			}
			System.out.println();
		}
		
	}
		

	public static Value stringOperation(String line) {
		
		String[] expected, expected2;
		Value v = new Value();
		ArrayList array = new ArrayList();		
		String firstChar = String.valueOf(line.charAt(0));
		v.setValue(firstChar);
			
		if(firstChar.equals("E")) {
			expected = line.split("=");
			expected2 = expected[1].split(",");
			
			for(int i = 0; i < expected2.length; i++) {
				array.add(expected2[i]);
			}
			v.setElemans(array);
			v.setSize(expected2.length);
		}
		else {
			expected = line.split("-");
			expected2 = expected[1].split("\\|");
			
			for(int i = 0; i < expected2.length; i++) {
				array.add(expected2[i]);
			}
			v.setElemans(array);
			v.setSize(expected2.length);
		}		
		return v;
	}
		
	
	public static void longerThanTwo(ArrayList array) {
	
		ArrayList elemans = new ArrayList();
		ArrayList tempList;
		Value tempValue;
		Value added;
		String[] characters = {"X","Y","Z","K","L","M","N","U","V","W"};//We wrote random letters instead of writing, this may be the max
		int cCount = 0;
		String str, newStr;
		
		
		while(longCheck(array) == false) {
			
			for(int i = 0; i < array.size(); i++) {
				tempValue = (Value) array.get(i);
				elemans = tempValue.getElemans();
				for(int j = 0; j < elemans.size(); j++) {
					str = (String) elemans.get(j);
					if(str.length() > 2) {
						tempList = new ArrayList();
						added = new Value();
						newStr = str.substring(1,3);//means take the 2nd and 3rd character
						//create new variable add to array
						tempList.add(newStr);
						added.setValue(characters[cCount]);
						added.setElemans(tempList);
						added.setSize(2);
						array.add(added);
						cCount++;
						//After creating a new one, a function is called that will traverse the entire array and convert the equal ones to this one.
						arrayUpdate(added, array);
					}
				}				
			}
		}
		
	}
	
	
	public static void arrayUpdate(Value v, ArrayList array) {
		ArrayList elemans = new ArrayList();
		ArrayList tempList = new ArrayList();
		Value tempValue;
		String str;
		String vStr = v.getValue();
		tempList = v.getElemans();
		String el = (String) tempList.get(0);
		
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			elemans = tempValue.getElemans();
			for(int j = 0; j < elemans.size(); j++) {
				str = (String) elemans.get(j);
				if(str.length() > 2) {
					if(str.contains(el)) {
						str = str.replace(el, vStr);
						elemans.set(j, str);
					}
				}
			}
		}
		
	}
		
	
	public static Boolean longCheck(ArrayList array) {//Check if there is a string greater than 2 lengths
		Boolean flag = true;
		Value tempValue;
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			flag = tempValue.lengthCheck();
			if(flag == false)
				break;
		}
		
		return flag;
	}
	

	public static void eliminateTerminals(ArrayList array) {
		
		ArrayList alphabet = new ArrayList();		
		ArrayList elemans = new ArrayList();
		ArrayList tempList = new ArrayList();
		ArrayList tempList2 = new ArrayList();
		Value added = new Value();
		Value added2 = new Value();
		Value tempValue;
		String str, temp, charr;
		int count = 0;
		String[] expected;
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(tempValue.getValue().equals("E")){
				elemans = tempValue.getElemans();
				for(int j = 0; j < elemans.size(); j++) {
					alphabet.add(elemans.get(j));
				}
				break;
			}
		}
		
		
		if(alphabet.size() == 2) {
			tempList.add(alphabet.get(0));
			added.setElemans(tempList);
			added.setValue("Q");
			added.setSize(1);
			array.add(added);
			
			tempList2.add(alphabet.get(1));
			added2.setElemans(tempList2);
			added2.setValue("R");
			added2.setSize(1);
			array.add(added2);
		}
		else if(alphabet.size() == 1) {
			tempList.add(alphabet.get(0));
			added.setElemans(tempList);
			added.setValue("Q");
			added.setSize(1);
			array.add(added);
		}
		
		
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(!(tempValue.getValue().equals("E"))){
				elemans = tempValue.getElemans();
				while(count < elemans.size()) {
					str = (String) elemans.get(count);
					if(str.length() > 1){
						
						for(int k = 0; k < alphabet.size(); k++) {
							temp = (String) alphabet.get(k);
							if(str.contains(temp)) {
								charr = newTerminal(temp, array);
								str = str.replace(temp, charr);
								elemans.set(count, str);
							}
						}					
					}					
					count++;
				}
				count = 0;
			}
		}
		
	}
	
		
	public static String newTerminal(String str, ArrayList array) {
		
		String temp = "";
		Value tempValue;
		ArrayList elemans = new ArrayList();
		
		for (int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(tempValue.getSize() == 1) {
				elemans = tempValue.getElemans();
				if(elemans.get(0).equals(str)) {
					temp = tempValue.getValue();
					break;
				}
			}						
		}
		return temp;
	}
	
	
	public static ArrayList eliminateEpsilon(ArrayList array) {
		
		ArrayList includedE = new ArrayList();
		ArrayList valuesList = new ArrayList();
		ArrayList tempList = new ArrayList();
		Value tempValue;
		String[] character;
		int count = 0;
		
		for(int i = 0; i < array.size(); i++) {//Strings containing epsilon are collected in an array
			tempValue = (Value) array.get(i);
			if(!(tempValue.getValue().equals("E"))) {
				if(tempValue.getEpsilon() == true) {
					includedE.add(tempValue.getValue());					
				}
			}
		}
		
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(!(tempValue.getValue().equals("E"))) {			
				valuesList = tempValue.getElemans();
				count = valuesList.size();
				for(int j = 0; j < count; j++) {
					character = ((String) valuesList.get(j)).split("");
					tempList = eliminateEpsilonContinuous(includedE, character, array);
					for(int k = 0; k < tempList.size(); k++) {
						valuesList.add(tempList.get(k));
					}
				}
			}
		}
				
		return array;
	}
	
		
	public static ArrayList eliminateEpsilonContinuous(ArrayList includedE, String[] character, ArrayList array) {
		
		ArrayList valuesList = new ArrayList();
		Value tempValue;
		String temp, newStr = "";
		int count = 0, tempCount = 0;
		

		while(count < includedE.size()) {
			temp = (String) includedE.get(count);//character to be replaced by epsilon
			for(int k = 0; k < character.length; k++) {
				if(character[k].equals(includedE.get(count))) {
					tempCount++;
				}				
			}
			if(tempCount != 0) {
				if(character.length == 1) {
					newStr = newStr + "€";
					valuesList.add(newStr);
					newStr = "";
				}
				else if(character.length == 2) {
					if(character[0].equals(includedE.get(count))) {
						newStr = newStr + character[1];
						valuesList.add(newStr);
						newStr = "";
					}
					if(character[1].equals(includedE.get(count))) {
						newStr =  character[0] + newStr;
						valuesList.add(newStr);
						newStr = "";
					}
					if(character[0].equals(includedE.get(count)) && character[0].equals(character[1])) {
						newStr = newStr + "€";
						valuesList.add(newStr);
						newStr = "";
					}
				}
				else if(character.length == 3) {
					if(character[0].equals(includedE.get(count))) {
						newStr = newStr + character[1] + character[2];
						valuesList.add(newStr);
						newStr = "";
					}
					if(character[1].equals(includedE.get(count))) {
						newStr =  character[0] + newStr + character[2];
						valuesList.add(newStr);
						newStr = "";
					}
					if(character[2].equals(includedE.get(count))) {
						newStr =  character[0] + character[1] + newStr;
						valuesList.add(newStr);
						newStr = "";
					}
					
					if(tempCount == 2) {
						if(character[0].equals(includedE.get(count)) && character[0].equals(character[1])) {//1. and 2. same
							newStr = character[2];
							valuesList.add(newStr);
							newStr = "";
						}
						else if(character[0].equals(includedE.get(count)) && character[0].equals(character[2])) {//1. and 3. same
							newStr = character[1];
							valuesList.add(newStr);
							newStr = "";
						}
						else if(character[1].equals(includedE.get(count)) && character[1].equals(character[2])) {//2. and 3. same
							newStr = character[0];
							valuesList.add(newStr);
							newStr = "";
						}						
					}
				}
				
			}
			epsilonCheckReversing(array, temp);
			count++;
		}
		return valuesList;
	}
		
		
	public static Boolean epsilonCheck(ArrayList array) {
		Boolean flag = false;
		Value v;
		ArrayList tempList;
		String temp;
		
		for(int i = 0; i < array.size(); i++) {
			v = (Value) array.get(i);
			tempList = v.getElemans();
			
			for(int j = 0; j < tempList.size(); j++) {
				temp = (String) tempList.get(j);
				if(temp.equals("€")) {
					v.setEpsilon(true);	
					v.epsilonDelete();
					flag = true;
				}				
			}
		}
		return flag;
	}
		
	
	public static void epsilonCheckReversing(ArrayList array, String temp) {
		
		Value tempV;
		String str;
		
		for(int i = 0; i < array.size(); i++) {
			tempV = (Value) array.get(i);
			str = tempV.getValue();
			if(str.equals(temp)) {
				tempV.setEpsilon(false);
			}
		}
	}
	
	
	public static void unitProduction(ArrayList array) {
		
		Value tempValue;
		Value tempT;
		ArrayList t = new ArrayList();	
		ArrayList ValueElemans;
		int elemanLength;
		String eleman;
		int count = 0;
		//It will go through the value elements and see if there is only variable going in it.
		//set variables before it is thrown into an array
		
		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(!(tempValue.getValue().equals("E"))) {
				t.add(tempValue);
			}
		}
		

		for(int i = 0; i < array.size(); i++) {
			tempValue = (Value) array.get(i);
			if(!(tempValue.getValue().equals("E"))) {
				ValueElemans = tempValue.getElemans();//will traverse strings one by one
				for(int j = 0; j < ValueElemans.size(); j++) {
					eleman = (String) ValueElemans.get(j);
					elemanLength = eleman.length();	
					if(elemanLength == 1) {//there's no need to look at something whose length isn't 1 anyway because it can't be unit production
						while(count < t.size()) {
							tempT = (Value) t.get(count);
							if(tempT.getValue().equals(eleman)) {
								mergeToArraylist(ValueElemans, tempT.getElemans(), eleman);
							}							
							count++;
						}
						count = 0;
					}					
				}				
			}
		}	
	}
	
	
	public static void mergeToArraylist(ArrayList list1, ArrayList list2, String eleman) {//Adding list 2 to list 1
		for(int k = 0; k < list2.size(); k++) {//loop to add arraylists together
			list1.add(list2.get(k));
		}
		for(int k = 0; k < list1.size(); k++) {
			if(list1.get(k).equals(eleman)) {
				list1.remove(k);
			}
		}
	}
	
	
	//File reading procedure 
	public static void fileReading(String str, ArrayList array) throws IOException {
			
		File file = new File(str);
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		String line = null;		
		line = reader.readLine();
		
		System.out.println();
		while(line != null) {	
			System.out.println(line);
			array.add(line);
			line = reader.readLine();	
			
		}
	}
	
	

}
