/*
 * circuits4you.com
 * 2016 November 25
 * Load Cell HX711 Module Interface with Arduino to measure weight in Kgs
 Arduino 
 pin 
 2 -> HX711 CLK
 3 -> DOUT
 5V -> VCC
 GND -> GND
 
 Most any pin on the Arduino Uno will be compatible with DOUT/CLK.
 The HX711 board can be powered from 2.7V to 5V so the Arduino 5V power should be fine.
*/
 
#include "HX711.h"  //You must have this library in your arduino library folder
#include "ArduinoJson.h"
#include "Math.h"
 
#define DOUT  3
#define CLK  2
int led = 13;
 
HX711 scale(DOUT, CLK);



 
//Change this calibration factor as per your load cell once it is found you many need to vary it in thousands
float calibration_factor = -396650; //-106600 worked for my 40Kg max scale setup 

char* cPayload;
String currentWeightString;
String previousWeightString;
StaticJsonBuffer<400> currentJsonBuffer;
JsonObject& currentWeightJSON = currentJsonBuffer.createObject();
StaticJsonBuffer<400> previousJsonBuffer;
JsonObject& previousWeightJSON = previousJsonBuffer.createObject();
int errorPercentThreshold = 20;



 
//=============================================================================================
//                         SETUP
//=============================================================================================
void setup() {
  pinMode(led, OUTPUT); // Declare the LED as an output
  Serial.begin(9600);
 /* Serial.println("HX711 Calibration");
  Serial.println("Remove all weight from scale");
  Serial.println("After readings begin, place known weight on scale");
  Serial.println("Press a,s,d,f to increase calibration factor by 10,100,1000,10000 respectively");
  Serial.println("Press z,x,c,v to decrease calibration factor by 10,100,1000,10000 respectively");
  Serial.println("Press t for tare");*/
  scale.set_scale();
  scale.tare(); //Reset the scale to 0
 
  long zero_factor = scale.read_average(); //Get a baseline reading
 /* Serial.print("Zero factor: "); //This can be used to remove the need to tare the scale. Useful in permanent scale projects.
  Serial.println(zero_factor);*/
previousWeightJSON["shelfId"] = "s007";
  previousWeightJSON["unitWeight"] = 150;
  previousWeightJSON["quantity"] = 0;
  previousWeightJSON["totalWeight"] = 0;
  previousWeightJSON["errorWeight"] = 0;
  previousWeightJSON["invalidItemPresent"] = false;
  

}
 
//=============================================================================================
//                         LOOP
//=============================================================================================


void loop() {

  //if(!previousWeightJSON.isNull()){
    scale.set_scale(calibration_factor); //Adjust to this calibration factor
     //previousWeightJSON.printTo(previousWeightString);
    //Serial.println("Previous JSON Before all- "+previousWeightString);
    float previousError = previousWeightJSON.get<float>("errorWeight");
    int previousQuantity = previousWeightJSON.get<int>("quantity");
    float unitWeight = previousWeightJSON.get<float>("unitWeight");
    float previousWeight = previousWeightJSON.get<float>("totalWeight");
    boolean errorItem = previousWeightJSON.get<boolean>("invalidItemPresent");
    
    //Below value will come from the sensor
    float currentWeightWithPreviousError = scale.get_units()*1000;
    //int unitWeight = 155;

    //substract the previous error from total weight to get the proper current total weight 
    float currentWeightWithoutPreviousError = currentWeightWithPreviousError - previousError;

    int calculatedCurrentQuantity;

    float currentError = previousError;

    //weight of item(s) loaded to shelf right now
    float currentInputWeight = previousWeight - currentWeightWithPreviousError;
                
    // Calculated quantity of current item(s) loaded to shelf
    int calculatedCurrentInputQuantity = round(currentInputWeight / unitWeight);

    //Ideal weight as per the calculated weight. 
    float idealInputWeight = calculatedCurrentInputQuantity * unitWeight;

    //The difference between ideal weight and actual input weight is the error for current entry.
    float currentInputErrorPrecent = ((currentInputWeight - idealInputWeight) * 100) / idealInputWeight;

    //If the current entry's error rate is greater than 10%(configured) then mark item(s) as error.
    if (abs(currentInputErrorPrecent) >= errorPercentThreshold) {
        currentError = previousError + currentInputWeight;
        errorItem = true;
        calculatedCurrentQuantity = previousQuantity;
    }
    else {
        calculatedCurrentQuantity = (int) round((currentWeightWithoutPreviousError / unitWeight));
        currentError = previousError + (currentInputWeight - idealInputWeight);
        errorItem = false;
    }
    
      /*float floatQuantity = weight/unitWeight;
      int quantity = round(floatQuantity);
      if(quantity<=2){
        digitalWrite(led, HIGH); // Turn the LED on
      }else{
        digitalWrite(led, LOW); // Turn the LED on
      }*/


      
      currentWeightJSON["shelfId"] = previousWeightJSON.get<String>("shelfId");
      currentWeightJSON["unitWeight"] = previousWeightJSON.get<float>("unitWeight");
      currentWeightJSON["quantity"] = calculatedCurrentQuantity;
      currentWeightJSON["totalWeight"] = currentWeightWithPreviousError;
      currentWeightJSON["errorWeight"] = currentError;
      currentWeightJSON["invalidItemPresent"] = errorItem;


      
      currentWeightString = "";
      previousWeightString = "";
      //Serial.println("Previous JSON - ");
      //previousWeightJSON.printTo(Serial);
      //Serial.println();

      //Serial.println("Current JSON - ");
      if(!(previousWeightJSON.get<int>("quantity") == currentWeightJSON.get<int>("quantity") && previousWeightJSON.get<boolean>("invalidItemPresent") == currentWeightJSON.get<boolean>("invalidItemPresent"))){
        Serial.println("Data has changed \n");
        currentWeightJSON.printTo(Serial);
        Serial.println();
        Serial.println();
      
      }else{
         Serial.println("Unchanged Data \n");
        currentWeightJSON.printTo(Serial);
        Serial.println();
        Serial.println();
      }
      
      //Serial.println();
      //Serial.println("----------------------------------------------------------------------------------------------------------------------");
      
      
      //Serial.println("Previous Weight - "+previousWeightString);
      //Serial.println();
      //Serial.println("Current Weight - "+currentWeightString);
      //Serial.println();
     previousWeightJSON.set("shelfId" , currentWeightJSON.get<String>("shelfId"));
     previousWeightJSON.set("unitWeight" , currentWeightJSON.get<float>("unitWeight"));
     previousWeightJSON.set("quantity" , currentWeightJSON.get<int>("quantity"));
     previousWeightJSON.set("totalWeight" , currentWeightJSON.get<float>("totalWeight"));
     previousWeightJSON.set("errorWeight" , currentWeightJSON.get<float>("errorWeight"));
     previousWeightJSON.set("invalidItemPresent" , currentWeightJSON.get<boolean>("invalidItemPresent"));
     delay(5000);

  
  //}
  
  if(Serial.available())
  {
    char temp = Serial.read();
   /* if(temp == '+' || temp == 'a')
      calibration_factor += 10;
    else if(temp == '-' || temp == 'z')
      calibration_factor -= 10;
    else if(temp == 's')
      calibration_factor += 100;  
    else if(temp == 'x')
      calibration_factor -= 100;  
    else if(temp == 'd')
      calibration_factor += 1000;  
    else if(temp == 'c')
      calibration_factor -= 1000;
    else if(temp == 'f')
      calibration_factor += 10000;  
    else if(temp == 'v')
      calibration_factor -= 10000;  
    else if(temp == 't')
      scale.tare();  //Reset the scale to zero*/
    //input += temp;
   // printf(input);
      
  }
}
//=============================================================================================
