# weather-machine-learning

Program Instructions

The program consists of three main parts, each of which have specific instructions for use.

Part 1: Data Collection, Preparation, & Cleaning
In order to run the program, you will need to download the Weka Java Machine Learning Library (Link: https://www.cs.waikato.ac.nz/ml/weka/) and add it to the WeatherPredictor project folder in either NetBeans or Eclipse. 
The WeatherDataPreparation and WeatherDataPreparationDriver classes were designed specifically for my Dark Sky account. In order to run the classes on your computer, please create a free Dark Sky account (Link: https://darksky.net/dev) and replace the apiKey variable in my code with the secret key they provide specific to your account. You can only make up to 1000 free data requests per day, so the program can only be run once per day. I have attached a file called “WeatherHistorical_2019-01-15.csv” to the Final Project Assignment which is the output of the program, in case you cannot create the file on your own. This file must be placed in the folder of the WeatherPredictor project to work.  

Part 2: Training
To use the WDBModelGenerator class, you must make sure that the name of the file generated from Part 1 is the same as the one stored in the csvFileName variable. 
The CSV file is converted to an ARFF file format to generate the model. The new ARFF file must be placed in the WeatherPredictor project folder to work.
The program will create a model file which must be placed in the WeatherPredictor project folder. I have attached this file as “wdbDecisionTable.model.”

Part 3: Prediction
I have attached an input file as “WPData_Inputv1.csv” which will need to be used to test the model generated. Place this file in the WeatherPredictor project folder. 

