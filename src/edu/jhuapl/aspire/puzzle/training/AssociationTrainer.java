package edu.jhuapl.aspire.puzzle.training;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizer;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.ml.AbstractTrainer;
import opennlp.tools.ml.perceptron.PerceptronTrainer;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class AssociationTrainer {

	public static void main(String[] args) throws Exception {
		InputStreamFactory dataIn = new MarkableFileInputStreamFactory(new File("src/resources/AssociationTrainingData.txt"));
		ObjectStream lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
		ObjectStream sampleStream = new DocumentSampleStream(lineStream);
		TrainingParameters params = new TrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, "100");
		params.put(TrainingParameters.CUTOFF_PARAM, "2");
		params.put(AbstractTrainer.ALGORITHM_PARAM, PerceptronTrainer.PERCEPTRON_VALUE);
		DoccatModel model =  DocumentCategorizerME.train("en", sampleStream,params, new DoccatFactory());
        DocumentCategorizer doccat = new DocumentCategorizerME(model);
        String[] docWords = "Hannah lives next to the red house".replaceAll("[^A-Za-z]", " ").split(" ");
        double[] aProbs = doccat.categorize(docWords);
        System.out.println(Arrays.toString(aProbs));
        String category = doccat.getBestCategory(aProbs);
        System.out.println(category);
        BufferedOutputStream modelOut = new BufferedOutputStream(new FileOutputStream("src/resources/models/AssociationModel.bin"));
        model.serialize(modelOut);
        System.out.println("\nTrained Model is saved locally at : "+"model"+File.separator+"en-movie-classifier-naive-bayes.bin");

	}

}
