package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.tokenize.*;

public class OpenNlp {
	@Test
	public void sentenceTester() throws IOException {
		InputStream inputStream = new FileInputStream("en-token.bin");
		TokenizerModel model = new TokenizerModel(inputStream);
		Tokenizer tokenizer = new TokenizerME(model);
		tokenizer.tokenize("Hanna lives in the red house.");
		String[] tokens;
		tokens = tokenizer.tokenize("Hanna lives in the red house.");
		ArrayList<String> list = new ArrayList<>();
		Arrays.asList(tokens);
		Assertions.assertEquals(tokens.length, 7);
		System.out.println(Arrays.toString(tokens));
		StringTokenizer tokenizer2 = new StringTokenizer("Hanna lives in the red house.");
		System.out.println(tokenizer2.countTokens());
	}
	
	@Test
	public void parser2() throws Exception {
		FileInputStream stream = new FileInputStream("src/resources/examples/PuzzleClues.txt");
		Scanner scanner = new java.util.Scanner(stream);
		Map<String, String[]> allData = puzzleData();
		ParserModel model = new ParserModel(new File("src/resources/models/en-parser-chunking.bin"));
		while(scanner.hasNextLine()) {
			String line= scanner.nextLine();
			parser(line, model, allData);
		}
	}
	
	private List<String> parser(String sentence, ParserModel model, Map<String, String[]> allData) throws Exception{
		List<String> puzzleNouns= new ArrayList<>();
		Parser parser = ParserFactory.create(model);
		Parse[] parsedSentence = ParserTool.parseLine(sentence, parser, 1);
		Parse parse = parsedSentence[0];
		List<String> nounInSentence = parseChild(parse);
		System.out.println(nounInSentence + " This was the list of nouns");
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
		System.out.println(puzzleNouns);
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
	
	private Map<String, String[]> puzzleData() throws IOException {
		FileInputStream stream = new FileInputStream("src/resources/examples/PuzzleData.txt");
		Scanner scanner = new java.util.Scanner(stream);
		Map<String, String[]> allData = new HashMap<>();
		while(scanner.hasNextLine()) {
			String line= scanner.nextLine();
			String[] splitLine = line.split(":");
			System.out.println("Name of data: " + splitLine[0]);
			String[] dataPoints1 = splitLine[1].split(",");
			String[] dataPoints2= new String[dataPoints1.length];
			for (int i= 0; i< dataPoints2.length; i++) {
				dataPoints2[i]= dataPoints1[i].trim();
			}
			System.out.println(Arrays.toString(dataPoints2));
			allData.put(splitLine[0], dataPoints2);
		}//end of while loop
		System.out.println(allData);
		System.out.println(allData.get("Pet")[1]);
		return allData;
	}
	
	@Test
	public void association() throws Exception{
		DoccatModel model =new DoccatModel(new File("src/resources/models/AssociationModel.bin"));
		DocumentCategorizer doccat = new DocumentCategorizerME(model);
		Scanner scanner = new Scanner(new File ("src/resources/examples/PuzzleClues.txt"));
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] docWords = line.replaceAll("[^A-Za-z]", " ").split(" ");
			double[] aProbs = doccat.categorize(docWords);
			System.out.println(line);
			String category = doccat.getBestCategory(aProbs);
			System.out.println(category);
		}
	}
}