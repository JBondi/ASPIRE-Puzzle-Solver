package edu.jhuapl.aspire.puzzle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import opennlp.tools.cmdline.parser.ParserTool;
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
	public void parser() throws Exception{
		ParserModel model = new ParserModel(new File("src/resources/models/en-parser-chunking.bin"));
		Parser parser = ParserFactory.create(model);
		Parse[] parsedSentence = ParserTool.parseLine("The dog owner is living next to the black house", parser, 1);
		Parse parse = parsedSentence[0];
		List<String> list = parseChild(parse);
		System.out.println(list);
	}
	
	private List<String> parseChild(Parse child) {
		List<String> list = new ArrayList<>();
		if(child.getChildCount() > 0 
				&&
				child.getChildren()[0].getType().equals("TK")) {
			if(child.getChildCount() > 0 && (child.getChildren()[0].getType().equals("NNP"))||
					child.getChildren()[0].getType().equals("NN")|| 
					child.getChildren()[0].getType().equals("JJ"))
				list.add(child.getCoveredText());
		}
		child.show();
		for(Parse newChild : child.getChildren())
			list.addAll(parseChild(newChild));
		return list;
	}
}
