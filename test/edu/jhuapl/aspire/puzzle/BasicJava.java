package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

	}




