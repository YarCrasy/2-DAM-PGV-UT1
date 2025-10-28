import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * Utility helper methods used by Parent and Child classes.
 *
 * <p>Provides file loading, simple filename parsing, and result file saving. Methods
 * are package-private / static to be used across the small project.
 */
public class Utils {
    final static String savePath = "./resultados/";
    static int wordCount, voidLines;

    /**
     * Reads all lines from the provided file and returns them as a String array.
     * On error the method prints an error to stderr and returns null.
     *
     * @param file file to read
     * @return array of lines read from the file or null on error
     */
    static String[] loadFile(File file) {
        try {
            return Files.readAllLines(file.toPath()).toArray(new String[0]);
        } catch (Exception e) {
            System.err.println("Error reading file: " + file.getName());
            return null;
        }
    }

    /**
     * Extracts the first continuous sequence of digits from the beginning of the
     * filename (before any non-digit). If no digits are found, returns 0.
     *
     * <p>Examples: "datos1.txt" -> 1, "file12.extra" -> 12, "abc" -> 0.
     *
     * @param f file whose name will be parsed
     * @return parsed leading number or 0 if none
     */
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

    /**
     * Saves the given lines to a file under the configured `savePath` directory
     * with the name `<fileName>.res`. Parent directories are created if needed.
     *
     * @param fileName base name (without extension) to use for the saved file
     * @param lines lines to write into the file
     */
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

    /**
     * Counts words and empty lines in the provided file returning a brief summary
     * string. This is used by the parent to collect a per-file human-readable
     * summary.
     *
     * @param file input file to analyze
     * @return summary string with filename, word count and void line count
     */
    static String countWordsInFile(File file) {
        String[] fileLines = loadFile(file);
        wordCount = voidLines = 0;
        assert fileLines != null;
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
