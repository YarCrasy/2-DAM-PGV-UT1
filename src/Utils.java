import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
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

    static int[] countVowels(String[] fileLines) {
        int[] aux = new int[fileLines.length];
        int i = 0;
        for (String fileLine : fileLines) {
            int count = 0;
            for (char c : fileLine.toCharArray()) {
                //solo se contemplan vocales del alfabeto español
                if ("aeiouáéíóúü".indexOf(c) != -1) {
                    count++;
                }
            }
            aux[i++] = count;
        }
        return aux;
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
        return("Found: " + wordCount + " words/" + voidLines + " void lines in file: " + file.getName());
    }

}
