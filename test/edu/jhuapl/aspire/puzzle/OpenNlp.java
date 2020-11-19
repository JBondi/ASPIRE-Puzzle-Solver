package edu.jhuapl.aspire.puzzle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
		Assertions.assertEquals(tokens.length, 7);
		System.out.println(Arrays.toString(tokens));
		StringTokenizer tokenizer2 = new StringTokenizer("Hanna lives in the red house.");
		System.out.println(tokenizer2.countTokens());
	}
}
