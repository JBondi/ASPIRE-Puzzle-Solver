package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


public class puzzleTests {

	@Test
	public void solvingTest() throws IOException{
		FileInputStream stream = new FileInputStream("src/resources/examples/PuzzleData.txt");
		Scanner scanner = new java.util.Scanner(stream);
		
		InputStream inputStream = new FileInputStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(inputStream);
		Tokenizer tokenizer = new TokenizerME(model);
		while(scanner.hasNextLine()){
			String[] tokens= tokenizer.tokenize(scanner.nextLine());
			System.out.println(Arrays.toString(tokens));
			for (String token: tokens) {
				if (token.equals(",")){
					
				}
				else{
					System.out.println(token);
				}
			}
		}
		
		Map<String, String[]> data = new HashMap<>();
		String[] dataArray = new String[]{"Hannah", "James", "Xaiver", "Wolfgang"};
		data.put("People", dataArray); 
		System.out.println(Arrays.toString(data.get("People")));
		
		Map<String, String[]> colorData = new HashMap<>();
		String[] houseColor = new String[]{"Red", "Green", "Black", "Blue"};
		colorData.put("House Color", houseColor); 
		System.out.println(Arrays.toString(colorData.get("House Color")));
		
		Map<String, String[]> petData = new HashMap<>();
		String[] pets = new String[]{"Cat", "Dog", "Marmot", "Fish"};
		petData.put("Pets", pets); 
		System.out.println(Arrays.toString(petData.get("Pets")));
	}
	
	//trim
	

	}