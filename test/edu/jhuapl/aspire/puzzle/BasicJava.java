package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BasicJava {
	
	private static final int SIZE = 4;
	private static String ExceptionMessage = "Exception Message";
	
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
		public void test2() throws IOException{
			test();
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
	}




