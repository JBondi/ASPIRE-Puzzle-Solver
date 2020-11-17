package edu.jhuapl.aspire.puzzle;

import java.io.IOException;
import java.io.InputStream;
import opennlp.tools.tokenize.*;

public class OpenNlp {
	OpenNlp() throws IOException{getResourcesAsStream("models/en-token.bin");
	InputStream inputStream = null;
	TokenizerModel model = new TokenizerModel(inputStream);
	Tokenizer tokenizer = new TokenizerME(model);
	tokenizer.tokenize("Hanna lives in the red house.");
	}
	public void getResourcesAsStream(String stringstring) {
	}
}
