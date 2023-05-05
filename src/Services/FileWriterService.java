package Services;

import Utils.Writers.ILineWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterService <T>{
    private static FileWriterService instance;
    private FileWriterService() {
    }
    public static <T> FileWriterService<T> getInstance() {
        if (instance == null) {
            instance = new FileWriterService<>();
        }
        return instance;
    }

    public void writeLine(T object, ILineWriter<T> writer, String path) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))) {
            String line = writer.writeLine(object);
            if (line != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        }
    }
}
