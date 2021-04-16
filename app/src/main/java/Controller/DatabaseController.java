package Controller;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;

import Model.Person;

public class DatabaseController {
    static public HashMap<String, Person> readJSONFromFile()  {
        ObjectMapper objectMapper = new ObjectMapper();

        //datafile = code to fetch file
        Person[] personArray = new Person[0];

        try {
            personArray = objectMapper.readValue(dataFile, Person[].class);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        HashMap<String, Person> personHashMap = new HashMap<>();

        for (Person planetSystem : personArray) {
            personHashMap.put(planetSystem.getName(), planetSystem);
        }
        return personHashMap;
    }
}
