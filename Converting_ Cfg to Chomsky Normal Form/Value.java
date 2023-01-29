package toc_hw;

import java.util.ArrayList;

public class Value {
	private String value;
	private ArrayList elemans = new ArrayList();
	private int size;
	private boolean epsilon = false;
	
	public Value() {
		this.value = value;
		this.elemans = elemans;
		this.size = size;
		this.epsilon = epsilon;
	}
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}
	public ArrayList getElemans() {return elemans;}
	public void setElemans(ArrayList elemans) {this.elemans = elemans;}
	public int getSize() {return size;}
	public void setSize(int size) {this.size = size;}
	public boolean getEpsilon() {return epsilon;}
	public void setEpsilon(boolean epsilon) {this.epsilon = epsilon;}

	
	public void display() {
		for(int i = 0; i < elemans.size();i++) {
			System.out.println(elemans.get(i));
		}
	}
	
	
	public boolean epsilonCheck() {
		boolean flag = false;
		
		for(int i = 0; i < elemans.size();i++) {
			if(elemans.get(i).equals("€")) {
				flag = true;
			}
		}
		return flag;
	}
		
	
	public void epsilonDelete() {
		for(int i = 0; i < elemans.size();i++) {
			if(elemans.get(i).equals("€")) {
				elemans.remove(i);
			}
		}
	}
	
	
	public boolean lengthCheck() {
		
		boolean flag  = true;
		String str;
		
		for(int i = 0; i < elemans.size();i++) {
			str = (String) elemans.get(i);
			if(str.length() > 2) {
				flag = false;
				break;
			}
		}
		return flag;
		
	}
}
