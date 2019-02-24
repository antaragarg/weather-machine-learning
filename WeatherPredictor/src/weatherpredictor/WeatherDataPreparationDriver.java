package weatherpredictor;

public class WeatherDataPreparationDriver {
	public static void main(String[] args) throws Exception { 
		String startDate = "2019-01-15"; //represents an arbitrary start date for the data requests
		int iteratorCount = 960; //represents the number of requests made to DarkSky API

		//a DataPreparation object is created to test the DataPreparation methods 
		WeatherDataPreparation test = new WeatherDataPreparation();

		String iteratorDate = startDate; //represents the start date for the data requests
		//the for-loop makes data requests to the API by going back one day at a time 
		for (int iterator = 0;iterator < iteratorCount;iterator++) {			
			System.out.println("Iterator: " + (iterator+1) + " of " + iteratorCount);

			//the DataRequest method is invoked which requests data from the API
			String iteratorString = WeatherDataPreparation.DataRequest(iteratorDate);

			//creates a WeatherDataBlock object
			WeatherDataBlock wdb = test.JSONtoWeatherDataBlock(iteratorString, iteratorDate);

			//prints an error message if a data block cannot be added to the list
			if(!test.AddToWDBList(wdb)) {
				System.out.println("Unable to add to the Weather Data Block list.");
			
			}

			//the getPreviousDays method is invoked to go back one day from the starting date and repeat the process
			iteratorDate = test.getPreviousDays(iteratorDate);		
		
		}	

		System.out.println("The data from Dark Sky was successfully imported.");
		
		if(test.ComputeNextDayPrecipitationType())
			System.out.println("The data preparation computation was completed successfully.");
		
		if(test.writeCSVFile(startDate))
			System.out.println("The CSV file was successfully written.");

	}
}