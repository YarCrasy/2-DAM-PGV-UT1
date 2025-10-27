import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class Utils {
    final static String savePath = "./resultados/";
    static int wordCount, voidLines;

    static String[] loadFile(File file) {
        try {
            return Files.readAllLines(file.toPath()).toArray(new String[0]);
        } catch (Exception e) {
            System.err.println("Error reading file: " + file.getName());
            return null;
        }
    }

    static int getFileNum(File f) {
        String name = f.getName();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (!sb.isEmpty()) {
                break;
            }
        }
        if (sb.isEmpty()) return 0;
        try {
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    static void saveFile(String fileName, List<String> lines) {
        File file = new File(savePath + fileName + ".res");

        try {
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                Files.createFile(file.toPath());
            }
            Files.write(file.toPath(), lines);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static String countWordsInFile(File file) {
        String[] fileLines = loadFile(file);
        wordCount = voidLines = 0;
        for (String line : fileLines) {
            if (line.trim().isEmpty()) {
                voidLines++;
                continue;
            }
            String[] words = line.trim().split("\\s+");
            wordCount += words.length;
        }
        return("Processing file: " + file.getName() + "\n" + wordCount + " words\n" + voidLines + " void lines\n");
    }

}


