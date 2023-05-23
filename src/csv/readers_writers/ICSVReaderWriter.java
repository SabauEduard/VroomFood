package csv.readers_writers;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface ICSVReaderWriter<T extends Serializable> {
    String WRITING_FILE_NAME = "src\\Csv\\output.csv";
    String SEPARATOR = ",";
    String getReadingFileName();
    default String getWritingFileName(){
        return WRITING_FILE_NAME;
    }
    T processCSVLine(String line);
    String convertObjectToCSVLine(T object);default List<T> read() {
        File file = new File(getReadingFileName());
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            List<T> resultLines = new ArrayList<>();
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {
                resultLines.add(processCSVLine(currentLine));
                currentLine = bufferedReader.readLine();
            }
            return resultLines;
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!\n");
            return Collections.emptyList();
        }
        catch (IOException e) {
            System.out.println("Error reading file!\n");
            return Collections.emptyList();
        }
    }


    default void write(T object){
        File file = new File(getWritingFileName());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(convertObjectToCSVLine(object));
            bufferedWriter.newLine();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!\n");
        }
        catch (IOException e) {
            System.out.println("Error writing file!\n");
        }
    }

}
