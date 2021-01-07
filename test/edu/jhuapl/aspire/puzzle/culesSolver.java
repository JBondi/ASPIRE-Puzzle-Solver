package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class culesSolver {
	
	@Test
	public void puzzleClues() throws IOException{
		FileInputStream stream = new FileInputStream("src/resources/examples/PuzzleClues.txt");
		Scanner scanner = new java.util.Scanner(stream);
		
		InputStream inputStream = new FileInputStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(inputStream);
		Tokenizer tokenizer = new TokenizerME(model);
		
		
		Map<String, String[]> clue1 = new HashMap<>();
		String[] clues = new String[]{"Hannah lives in the red house"};
		clue1.put("Clue 1", clues); 
		System.out.println(Arrays.toString(clue1.get("Clue 1")));
		
		Map<String, String[]> clue2 = new HashMap<>();
		String[] clues1 = new String[]{"The dog owner does not live in the black house"};
		clue2.put("Clue 2", clues1); 
		System.out.println(Arrays.toString(clue2.get("Clue 2")));
		
		Map<String, String[]> clue3 = new HashMap<>();
		String[] clues2 = new String[]{"Wolfgang does not own a cat"};
		clue3.put("Clue 3", clues2); 
		System.out.println(Arrays.toString(clue3.get("Clue 3")));
		
		Map<String, String[]> clue4 = new HashMap<>();
		String[] clues3 = new String[]{"Xavier owns the marmot"};
		clue4.put("Clue 4", clues3); 
		System.out.println(Arrays.toString(clue4.get("Clue 4")));
		
		Map<String, String[]> clue5 = new HashMap<>();
		String[] clues4 = new String[]{"A dog lives in the green house"};
		clue5.put("Clue 5", clues4); 
		System.out.println(Arrays.toString(clue5.get("Clue 5")));
	}
	
	@Test
	public void puzzleData() throws IOException {
		FileInputStream stream = new FileInputStream("src/resources/examples/PuzzleData.txt");
		Scanner scanner = new java.util.Scanner(stream);
		Map<String, String[]> allData = new HashMap<>();
		while(scanner.hasNextLine()) {
			String line= scanner.nextLine();
			String[] splitLine = line.split(":");
			System.out.println("Name of data: " + splitLine[0]);
			String[] dataPoints1 = splitLine[1].split(",");
			System.out.println(Arrays.toString(dataPoints1));
			allData.put(splitLine[0], dataPoints1);
		}//end of while loop
		System.out.println(allData);
		System.out.println(allData.get("Pet")[1]);
		
	}
}
