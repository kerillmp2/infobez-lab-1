import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileController {

    public static String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void writeToFile(String path, String content) {
        try {
            Files.writeString(Path.of(path), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
