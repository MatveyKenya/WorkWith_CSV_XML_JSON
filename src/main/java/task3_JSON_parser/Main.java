package task3_JSON_parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import task1_CSV_JSON_parser.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String fileName = "data.json";
        String json = readString(fileName);
        //System.out.println(json);
        List<Employee> list = jsonToList(json);
        list.forEach(System.out::println);
    }


    private static List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try{
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(json);
            for (Object obj : jsonArray){
                JSONObject jsonObject = (JSONObject) obj;
                Employee employee = gson.fromJson(jsonObject.toJSONString(), Employee.class);
                list.add(employee);
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        return list;
    }


    private static String readString(String fileName) {
        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String s;
            while ((s = br.readLine()) != null){
                jsonString.append(s);
            }

        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
        return jsonString.toString();
    }

}
