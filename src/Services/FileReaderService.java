package Services;

import Utils.Readers.ILineReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderService<T>{
    private static FileReaderService instance;
    private FileReaderService() {
    }
    public static <T> FileReaderService<T> getInstance() {
        if (instance == null) {
            instance = new FileReaderService<>();
        }
        return instance;
    }
    public T readLine(ILineReader<T> reader, String path) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                T result = reader.readLine(line);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
