package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * This is a tool for solving logic puzzles
 * @author Ella Matz
 *
 */
public class PuzzleSolver {
	
	private Map<String, String[]> puzzleData;
	private List<String> puzzleClues;
	private Parser parser;
	private DocumentCategorizer doccat;
	private Tokenizer token;
	
	/**
	 * Default constructor 
	 * @throws Exception when there is an error reading the model files
	 */
	public PuzzleSolver() throws Exception{
		this.puzzleClues= new ArrayList<>();
		ParserModel model= new ParserModel(new File("src/resources/models/en-parser-chunking.bin"));
		this.parser = ParserFactory.create(model);
		DoccatModel fun =new DoccatModel(new File("src/resources/models/AssociationModel.bin"));
		this.doccat = new DocumentCategorizerME(fun);
		InputStream inputStream = new FileInputStream("en-token.bin");
		TokenizerModel model1 = new TokenizerModel(inputStream);
		this.token = new TokenizerME(model1);
	}
	
	/**
	 * Determines if a sentence implies a relationship between two nouns/adjectives 
	 * ex. I own a pillow vs. I don't have blonde hair
	 * @param Ella -Sentence Test
	 * @return True if a positive association
	 * @throws Exception with the document categorizer 
	 */
	public boolean association(String Ella) throws Exception{
			String[] docWords = token.tokenize(Ella);
			double[] aProbs = doccat.categorize(docWords);
			String category = doccat.getBestCategory(aProbs);
			int categoryNum = Integer.parseInt(category);
			return categoryNum == 1;
	}
	
	/**
	 * Main Method
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		PuzzleSolver solver = new PuzzleSolver();
		solver.readPuzzleDataFromFile(args[0]);
		solver.readPuzzleCluesFromFile(args[1]);

	}
	
	public List<String> readPuzzleCluesFromFile(String fileName) throws Exception{
		FileInputStream stream = new FileInputStream(fileName);
		Scanner scanner = new java.util.Scanner(stream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			addClue(line);
		}
		return puzzleClues;
	}
	
	//Splits the lines 
	public Map<String, String[]> readPuzzleDataFromFile(String fileName) throws IOException {
		FileInputStream stream = new FileInputStream(fileName);
		Scanner scanner = new java.util.Scanner(stream);
		Map<String, String[]> allData = new HashMap<>();
		while(scanner.hasNextLine()) {
			String line= scanner.nextLine();
			String[] splitLine = line.split(":");
			String[] dataPoints1 = splitLine[1].split(",");
			String[] dataPoints2= new String[dataPoints1.length];
			for (int i= 0; i< dataPoints2.length; i++) {
				dataPoints2[i]= dataPoints1[i].trim();
			}
			allData.put(splitLine[0], dataPoints2);
		}//end of while loop
		this.puzzleData = allData;
		return allData;
	}
	
	public void setData(Map<String, String[]> puzzleData) throws Exception {
		this.puzzleData= puzzleData;
	}
	
	//Figures out positive and negative connotation 
	public void addClue(String sentence) throws Exception{
		puzzleClues.add(sentence);
		List<String> puzzleNouns = parser(sentence, puzzleData);
		boolean associationResults= association(sentence);
		if (!associationResults) {
			System.out.print("Not ");
		}
		System.out.println(puzzleNouns);
	}
	
	//method that parses each of the clues
	private List<String> parser(String sentence, Map<String, String[]> allData) throws Exception{
		List<String> puzzleNouns= new ArrayList<>();
		Parse[] parsedSentence = ParserTool.parseLine(sentence, parser, 1);
		Parse parse = parsedSentence[0];
		List<String> nounInSentence = parseChild(parse);
		for(String key : allData.keySet()) {
			String[] values = allData.get(key);
			for(String value : values) {
				for(String noun: nounInSentence){
					if(noun.equalsIgnoreCase(value)) {
						puzzleNouns.add(noun);
					}
				}
				}
		}
		return puzzleNouns;
	}

	//gives each the clue words a part of speech
	private List<String> parseChild(Parse child) {
		List<String> list = new ArrayList<>();
		if(child.getChildCount() > 0 
				&&
				child.getChildren()[0].getType().equals("TK")) {
			if(child.getChildCount() > 0 && (child.getType().equals("NNP"))||
					child.getType().equals("NN")|| 
					child.getType().equals("JJ"))
				list.add(child.getCoveredText());
		}
		for(Parse newChild : child.getChildren())
			list.addAll(
					parseChild(newChild));
		return list;
	}

}
