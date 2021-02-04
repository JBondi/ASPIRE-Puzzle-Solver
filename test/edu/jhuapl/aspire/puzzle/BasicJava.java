package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BasicJava {
	
	private static final int SIZE = 4;
	private static String ExceptionMessage = "Exception Message";
	private int min;
	
	private void test() throws IOException{
	    try {
	        System.out.println("Entered try statement");
	        PrintWriter out = new PrintWriter(new FileWriter("OutFile.txt"));
	        for (int i = 0; i < SIZE; i++) {
	            Object list= null;
	            Object get;
				out.println("Value at: " + i + " = " + ((String) list));
	        }
	    }
	        catch (IOException e) {
	            System.out.println("Caught IOException: " + e.getMessage());
	        }
	  }
	    @Test
	    public void tests() throws Exception{
	    	try{
	               Object s = null;
	               s.toString();
	               System.out.println("This will never print");
	    	}
	    	catch(Exception e){
	               System.out.println(e);
	    	}
	    }
	    
	    @Test
	    public void test1() throws IOException{
			File fp;
	    	fp = new File ("file_name", "r");
	    	System.out.println(fp.canRead());
	    }
		

		@Test
		public void arrays() {
			
			//Create an array of 10 numbers
			int[] arrayOfNumbers = new int[10];
			
			//fill it using a for-loop
			for(int i = 0; i < arrayOfNumbers.length; i++) {
				arrayOfNumbers[i] = i * 2;
			}
			
			//Now, arrayOfNumbers is [0,2,4,6,8,10,12,14,16,18]
			
			Assertions.assertArrayEquals(arrayOfNumbers, new int[] {0,2,4,6,8,10,12,14,16,18});
			
			//Let's change the array so that each number is one less 
			for(int i = 0; i < arrayOfNumbers.length; i++) {
				arrayOfNumbers[i] = arrayOfNumbers[i] - 1;
			}
			//Now, arrayOfNumbers is [-1,1,3,5,7,9,11,13,15,17]
			
			Assertions.assertArrayEquals(arrayOfNumbers, new int[] {-1,1,3,5,7,9,11,13,15,17});
		}
		
		@Test
		public void stringStuff() {
			
			//These two variables are the same, except that one has a lot of extra
			//spaces so we use the method "trim()" to get rid of them.
			String wordWithExtraSpaces = "    Word  ";
			String wordWithoutExtraSpaces = "Word";
			String changedWord = wordWithExtraSpaces.trim();
			Assertions.assertEquals(wordWithoutExtraSpaces, changedWord);
			
		}
		
		@Test
		public void arrayTrim(){
			int[] size = new int [] {10,4,15,213,5};

			min = size[0];

			String result = "             "; 

			for(int i = 0; i < size.length; i++){
			    result += size[i] +  " "; 
			}
			System.out.println(result.trim());
			System.out.println(result);
		}
		
		@Test
		public void arrayTrim2() {
			String[] ravens = new String[] {" Hannah likes ice cream ", " I like to ride my bike ", " The ravens went to the playoffs "};
			for(int i = 0; i< ravens.length; i++) {
				System.out.println(ravens[i].trim());
			}
		}
		
		@Test
		public void Lists() {
			ArrayList<Integer> arlist = new ArrayList();
			for (int i= 1 ; i<= 10; i++) {
				arlist.add(i);
			}
			arlist.add(11);
			System.out.println(arlist.get(5));
		}
		
		private int factorial(int number) {
			if (number <= 1) {
				return 1;
			}
			return number *(factorial (number-1));
		}
		
		@Test
		public void factorialTest() {
			int f = factorial(6);
			System.out.println(f);
		}
		
		public void Recursion(int times) {
			if (times<5) {
				System.out.println(times);
			}
			else {
				System.out.println(times);
				Recursion(times-1);
			}
		}
		
		@Test
		public void  recursionTests(){
			Recursion(10);
}

		}