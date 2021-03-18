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

public class PuzzleSolver {
	
	private Map<String, String[]> puzzleData;
	private List<String> puzzleClues;
	private Parser parser;
	private DocumentCategorizer doccat;
	private Tokenizer token;
	
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
	
	public boolean association(String Ella) throws Exception{
			String[] docWords = token.tokenize(Ella);
			double[] aProbs = doccat.categorize(docWords);
			String category = doccat.getBestCategory(aProbs);
			int categoryNum = Integer.parseInt(category);
			return categoryNum == 1;
	}

	public static void main(String[] args) throws Exception {
		PuzzleSolver solver = new PuzzleSolver();
		solver.readPuzzleDataFromFile("src/resources/examples/PuzzleData.txt");
		solver.readPuzzleCluesFromFile("src/resources/examples/PuzzleClues.txt");

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
	
	public void addClue(String sentence) throws Exception{
		puzzleClues.add(sentence);
		List<String> puzzleNouns = parser(sentence, puzzleData);
		boolean associationResults= association(sentence);
		if (!associationResults) {
			System.out.print("Not ");
		}
		System.out.println(puzzleNouns);
	}
	
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
