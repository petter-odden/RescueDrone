
package Controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import Model.Person;

public class DatabaseController {
    static public HashMap<String, Person> readJSONFromFile(File filePath)  {
        ObjectMapper objectMapper = new ObjectMapper(); //This makes objects out of the json


        Person[] personArray = new Person[0];   //Array to keep the objects

        try {
            personArray = objectMapper.readValue(filePath, Person[].class); //filePath can be any file or value as long as it is in JSON format
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        HashMap<String, Person> personHashMap = new HashMap<>();

        for (Person person : personArray) {   //Puts the values in a hashmap with their UID as key
            personHashMap.put(person.getUID(), person);
        }
        return personHashMap;
    }

    static public HashMap<String, Person> readJSONFromFile(String path){    //Version with just takes a string filepath as the input
        File pathFile = new File(path);
        return readJSONFromFile(pathFile);
    }
}