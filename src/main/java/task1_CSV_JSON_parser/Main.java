package task1_CSV_JSON_parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = parseCSV(columnMapping, fileName);
        list.forEach(System.out::println);

        String json = listToJson(list);
        System.out.println(json);

        writeString(json);

    }

    private static void writeString(String json) {
        try (FileWriter fileWriter = new FileWriter("dataFromCSV.json")){
            fileWriter.write(json);
            fileWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    static List<Employee> parseCSV(String[] columnMapping, String fileName){
        List<Employee> list = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))){

            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setColumnMapping(columnMapping);
            strategy.setType(Employee.class);

            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            list = csv.parse();

        }catch (IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
