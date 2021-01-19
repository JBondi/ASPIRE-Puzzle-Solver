package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

public class Review {

	@Test
	public void Review2() throws FileNotFoundException {
		FileInputStream stream = new FileInputStream("src/resources/ReviewExample.txt");
		Scanner scanner = new java.util.Scanner(stream);
		
		Map<String, String> firstLine = new HashMap<>();
		String clues = new String("My favorite color is orange.");
		firstLine.put("First Line", clues); 
		System.out.println(firstLine.get("First Line"));
		
		String clues1 = new String("My favorite candy is butterfinger.");
		firstLine.put("Second Line", clues1); 
		System.out.println(firstLine.get("Second Line"));
		
		String clues2 = new String("I like playing softball.");
		firstLine.put("Third Line", clues2); 
		System.out.println(firstLine.get("Third Line"));
	}
	
	@Test
	public void Review3() throws FileNotFoundException {
		FileInputStream stream = new FileInputStream("src/resources/ReviewExample.txt");
		Scanner scanner = new java.util.Scanner(stream);
		
	      while (scanner.hasNextLine()) {
	    	  String Ella = scanner.nextLine();
	          System.out.println("Token: "+Ella);
	          StringTokenizer tokenizer= new StringTokenizer(Ella);
	          while(tokenizer.hasMoreTokens()) {
	        	  String candy = tokenizer.nextToken();
	        	  System.out.println(candy);
	          }
	      }
	}
}
