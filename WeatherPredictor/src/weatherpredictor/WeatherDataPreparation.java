package weatherpredictor; 
import java.io.BufferedReader; 
import java.io.FileNotFoundException; 
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.StringTokenizer;


//a public class DataPreparation is created to import and prepare the weather data
public class WeatherDataPreparation { 	
	int wdbCount; //counts the number of weather data blocks
	WeatherDataBlock[] wdbList; //an array of weather data blocks is created

	
	public WeatherDataPreparation() {
		wdbCount = 0;
		wdbList = new WeatherDataBlock[WeatherDataBlock.MAX_WDB_COUNT]; //the array can hold up to 1000 elements
	
	}

	
	//the DataRequest method makes requests to DarkSky API 
	public static String DataRequest(String date) throws Exception {
		String apiKey = "a5f4c55e2a0ebcdbc143a5c59eec093a/"; //represents the API key for an account
		String lat_long = "42.7654,-71.4676,"; //represents the latitude and longitude for Nashua
		String completeDate = date + "T15:00:00"; //represents the date from which data is to be receieved
		String options = "?exclude=currently,hourly,flags"; //represents the excluded categories from the data request

		//the passedURL variable represents the link from which data requests are made
		String passedURL = "https://api.darksky.net/forecast/";
		passedURL = passedURL + apiKey + lat_long + completeDate + options;

		URL url = new URL(passedURL); //a URL object is created that is a pointer to a resource on the internet
		HttpURLConnection connection = (HttpURLConnection)url.openConnection(); //establishes a connection to the online resource

		//a BufferedReader object is created that reads text from a character input-stream
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

		String inputLine = "";
		String returnInputLine = "";

		while((inputLine = in.readLine()) != null) {
			returnInputLine = inputLine;
		
		}

		in.close(); //closes the stream and any system resources associated with it

		return returnInputLine;		
	
	}

	//weather data blocks are added to the array through this method
	public boolean AddToWDBList(WeatherDataBlock wdb) {
		wdbList[wdbCount] = wdb;
		wdbCount++;
		
		return true;
	
	}

	//computes the precipitation type for the next day by finding values in the array
	public boolean ComputeNextDayPrecipitationType() {
		for(int i=1;i<wdbCount;i++) {
			wdbList[i].nextDayPrecipitationType = wdbList[i-1].precipitationType;
			wdbList[i].setStreamValid();
		
		}
		
		return true;
	
	}

	
	//the public method JSONtoCSV converts the data in the request from JSON to CSV
	public WeatherDataBlock JSONtoWeatherDataBlock(String inputString, String dateString) {
		WeatherDataBlock wdb = new WeatherDataBlock ();
		wdb.date = dateString;
		wdb.precipitationType = "none";			

		//the commaTokenizer breaks a String based on the location of commas
		StringTokenizer commaTokenizer = new StringTokenizer(inputString, ",");

		//the data from DarkSky API is split based on the location of commas
		while(commaTokenizer.hasMoreTokens()) {
			String tokenCommaString = commaTokenizer.nextToken();
			
			//the data is split based on the location of colons after separated by commas
			StringTokenizer colonTokenizer = new StringTokenizer(tokenCommaString, ":");

			//the data is split based on the location of colons
			while(colonTokenizer.hasMoreTokens()) {

  				String tokenColonString = colonTokenizer.nextToken();
  				//data is only obtained if it contains the following key words

  				if(tokenCommaString.contains("temperatureHigh\"")) 
  					wdb.temperatureHigh = tokenColonString;  					
  			    else if(tokenCommaString.contains("temperatureLow\"")) 
  					wdb.temperatureLow = tokenColonString;
  			    else if(tokenCommaString.contains("dewPoint\"")) 
  					wdb.dewPoint = tokenColonString;
  			    else if(tokenCommaString.contains("humidity\"")) 
  					wdb.humidity = tokenColonString;
  				else if(tokenCommaString.contains("pressure\"")) 
  					wdb.pressure = tokenColonString;
  				else if(tokenCommaString.contains("precipIntensity\"")) 
  					wdb.precipitationIntensity = tokenColonString;
  				else if(tokenCommaString.contains("precipType\"")) { 	
  					String tmpString = tokenColonString.substring(1, tokenColonString.length() - 1);
  					wdb.precipitationType = tmpString;
			    
			    }  				
  			}
		}
		
		if(!wdb.setContentValid()) {
			System.out.println("Incomplete information was received from Dark Sky:"+ inputString);
		
		}
   	    
		return wdb;
	
	}

	
	//the public method createCSVFile creates a CSV file with a given name
	public boolean writeCSVFile(String date) throws FileNotFoundException, UnsupportedEncodingException {

		PrintWriter writer = new PrintWriter("WeatherHistorical_"+ date + ".csv","UTF-8");
		
		//the headerString variable is representative of the headers for the CSV file
		String headerString = WeatherDataBlock.returnHeaderString();
		writer.println(headerString);

		for(int i=0;i<wdbCount;i++) {
			if(this.wdbList[i].isStreamValid() && this.wdbList[i].isContentValid()) {
				String valueString = this.wdbList[i].returnValueString();
				writer.println(valueString);
			
			}
		}
	
		writer.close();	
		return true;		
	
	}

	//the public method getPreviousDays returns the day previous to the one parsed
	public String getPreviousDays(String date) throws ParseException {	
		String dt = date; //represents the start date

		//a SimpleDateFormat object is created that formats the date based on the locale
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance(); //a Calendar object is created
		c.setTime(sdf.parse(dt)); //parses text at the beginning of the String to produce a date
		c.add(Calendar.DATE, -1);  //represents the number of days to add

		dt = sdf.format(c.getTime()); //dt now represents the new date		

		return dt; //the new date is returned
	
	}
}