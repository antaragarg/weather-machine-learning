package weatherpredictor;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.classifiers.Classifier;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader ;
import java.io.IOException;

public class WDBModelPredictor {
	public static void main(String[] args) throws Exception {
 	     String csvFileName  = "WPData_Inputv1.csv"; //represents the name of the CSV file
 	     String arffFileName = "WPData_Inputv1.arff"; //represents the name of the ARFF file
 	     String wekaModelName = "wdbDecisionTable.model"; //represents the name of the Weka model
 	     
  	     convertCsvToArff(csvFileName, arffFileName); //the CSV file is converted to an ARFF file format
 	     
  	     //predicts the class instance values
 	     Classifier cls = (Classifier) weka.core.SerializationHelper.read(wekaModelName);
 	     BufferedReader reader = null;
 	     reader = new BufferedReader(new FileReader(arffFileName));
 	     Instances originalTrain = new Instances(reader);
 	     originalTrain.setClassIndex(7);     
 	     reader.close();
 	     
 	     //predicts the class value for each instance
 	     for(int i=0;i<originalTrain.numInstances();i++) {
 	    	 //performs the prediction
 	    	 double value = ((Classifier) cls).classifyInstance(originalTrain.instance(i));
 	    	 
 	    	 //gets the name of the class value
 	    	 String prediction = originalTrain.classAttribute().value((int)value); 
 	    	 System.out.println("The predicted value of instance "+ Integer.toString(i)+ ": " + prediction); 			
 	      
 	     }
	  }
	
	//converts the CSV data file into an ARFF file format
	public static void convertCsvToArff (String csvName, String arffName) throws IOException {
		//loads the CSV
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(csvName));
		Instances data = loader.getDataSet();
		
		//saves the ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(arffName));
		saver.writeBatch();
	
	}
}