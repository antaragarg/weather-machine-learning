package weatherpredictor;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.Evaluation;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader ;
import java.io.IOException;

public class WDBModelGenerator {
	public static void main(String[] args) throws Exception {
  	     int percentTrain = 80; //represents the percentage of the data set that will be used for training 
  	     String csvFileName  = "WeatherHistorical_2019-01-15.csv"; 
  	     String arffFileName = "WeatherHistorical_2019-01-15.arff";
  	     String wekaModelName = "wdbDecisionTable.model";
  	     
  	     convertCsvToArff (csvFileName, arffFileName);
		   
		  //training
		  BufferedReader reader = null;
		  reader=new BufferedReader(new FileReader(arffFileName));
		  Instances train = new Instances (reader);
		  train.setClassIndex(8);     
		  reader.close();

		  DecisionTable dt = new DecisionTable();
		  dt.buildClassifier(train);
		      		      		      
		  int trainSetSize = Math.round((train.numInstances() * percentTrain)/100);
		  int testSetSize = train.numInstances() - trainSetSize;
			 
		  train = randomizeSet(train);
		  Instances trainingSet = new Instances(train, 0, trainSetSize);
		  Instances testingSet = new Instances(train, 0, testSetSize);

		  Evaluation eval = new Evaluation(trainingSet);
		  eval.evaluateModel (dt ,testingSet);

		  System.out.println(eval.toSummaryString("\nResults \n=======\n",true));
		  System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+eval.recall(1)+" ");    
		      
		  weka.core.SerializationHelper.write(wekaModelName, dt);
		
	}   
	
	//randomly chooses the training set and the test set 
	private static Instances randomizeSet(Instances trainingSet){
		trainingSet.randomize(new Random(1));
		return trainingSet;
		
	}
	
	//converts the CSV data file into an ARFF file format
	public static void convertCsvToArff(String csvName, String arffName) throws IOException {
		//loads the CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvName));
		Instances data = loader.getDataSet();
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffName));
		saver.writeBatch();
	
	}
}