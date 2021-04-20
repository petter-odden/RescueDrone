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
        ObjectMapper objectMapper = new ObjectMapper();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        Person[] personArray = new Person[0];

       try {
            personArray = objectMapper.readValue(filePath, Person[].class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        HashMap<String, Person> personHashMap = new HashMap<>();

        for (Person planetSystem : personArray) {
            personHashMap.put(planetSystem.getName(), planetSystem);
        }
        return personHashMap;
    }
    static public HashMap<String, Person> readJSONFromFile(String path){
        File pathFile = new File(path);
        return readJSONFromFile(pathFile);
    }
}
