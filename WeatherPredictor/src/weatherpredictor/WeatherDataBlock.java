package weatherpredictor;

public class WeatherDataBlock {	
	public static int MAX_WDB_COUNT = 1000; //represents the maximum number of requests that can be made to Dark Sky 

	boolean contentValid; //boolean representing whether or not the content from Dark Sky is valid
	boolean streamValid; //boolean representing whether or not the stream is valid

	//once initialized, the following variables make up a weather data block for a single day 
	
	String date;
	String temperatureHigh;
	String temperatureLow;
	String dewPoint;
	String humidity;
	String pressure;
	String precipitationIntensity;
	String precipitationType;
	
	String nextDayPrecipitationType; //represents the variable that will be predicted

	public WeatherDataBlock() {
		contentValid = false;
		streamValid  = false;
	
		//each variable is originally uninitialized
		
		date = "UNINIT";
		temperatureHigh = "UNINIT";
		temperatureLow = "UNINIT";
		dewPoint = "UNINIT";
		humidity = "UNINIT";
		pressure = "UNINIT";
		precipitationIntensity = "UNINIT";
		precipitationType = "UNINIT";
		
		nextDayPrecipitationType = "UNINIT";
	
	}

	//returns a boolean value representing whether or not the stream is valid
	public boolean isStreamValid() {	
		return streamValid;
	
	}
	
	//returns a boolean value representing whether or not the stream is valid
	public boolean setStreamValid() {	
		streamValid = false;
		
		if (nextDayPrecipitationType != "UNINIT") {
			streamValid = true;			
		
		}
		
		return streamValid;
	
	}
	
	//returns a boolean value representing whether or not the Dark Sky content is valid
	public boolean isContentValid() {	
		return contentValid;
	
	}
	
	//returns a boolean value representing whether or not the variables are uninitialized
	public boolean setContentValid() {	
		contentValid = false;

		if(date != "UNINIT" &&
			temperatureHigh != "UNINIT" &&
			temperatureLow != "UNINIT" &&
			dewPoint != "UNINIT" &&
			humidity != "UNINIT" &&
			pressure != "UNINIT" &&
			precipitationIntensity != "UNINIT" &&
			precipitationType != "UNINIT") {
			contentValid = true;			
		
		}
				
		return contentValid;
	
	}

	//returns the headers for the columns in the CSV file
	public static String returnHeaderString() {	
        String headString = "Date";
        headString += ", TemperatureHigh";
        headString += ", TemperatureLow";
        headString += ", DewPoint";
        headString += ", Humidity";
        headString += ", Pressure";
        headString += ", PrecipitationIntensity";
        headString += ", PrecipitationType";
        headString += ", NextDayPrecipitationType";

        return headString;
	
	}

	//returns the values for the columns in the CSV file
	public String returnValueString() {
		if(!streamValid)
			return null;

		String valueString = date;
		valueString += ", " + temperatureHigh;
		valueString += ", " + temperatureLow;
		valueString += ", " + dewPoint;
		valueString += ", " + humidity;
		valueString += ", " + pressure;
		valueString += ", " + precipitationIntensity;
		valueString += ", " + precipitationType;
		valueString += ", " + nextDayPrecipitationType;		
		
		return valueString;
	
	}
}