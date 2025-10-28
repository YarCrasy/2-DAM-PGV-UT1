import java.io.File;
import java.nio.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Child worker process.
 *
 * <p>This class is executed as a separate Java process for each input file. The child
 * reads the provided file, writes a lowercase copy into the `minusculas` results
 * folder and writes a per-line vowel count into the `vocales` results folder. It
 * uses helper utilities provided by the `Utils` class for I/O and filename parsing.
 */
public class Child {

    private File f;
    String[] fileLines;

    /**
     * Entry point for the child process.
     *
     * <p>Expects a single argument: the path to the file that must be processed.
     * If no arguments are provided an error message is printed to stderr.
     *
     * @param args command-line arguments, args[0] should be the path to the input file
     */
    static void main(String[] args) {
        Child c = new Child();
        if (args.length == 0) {
            System.err.println(c.getClass().getSimpleName() + ": No arguments provided");
            return;
        }
        c.f = new File(args[0]);
        c.fileLines = Utils.loadFile(c.f);
        c.toLowerCase();
        c.countVowels();
    }

    /**
     * Converts all lines of the loaded file to lowercase and saves them
     * into a results file under `minusculas/minusculas-<n>.res` where <n>
     * is derived from the input filename using `Utils.getFileNum`.
     */
    void toLowerCase() {
        List<String> aux = new ArrayList<>();
        for (String fileLine : fileLines) {
            aux.add(fileLine.toLowerCase());
        }
        Utils.saveFile("minusculas/minusculas-"+Utils.getFileNum(f), aux);
    }

    /**
     * Counts vowels in each line, producing one integer-per-line. The results
     * are saved into `vocales/vocales-<n>.res` where <n> is obtained via
     * `Utils.getFileNum`.
     */
    void countVowels() {
        List<String> aux = new ArrayList<>();
        for (String fileLine : fileLines) {
            int count = 0;
            for (char c : fileLine.toCharArray()) {
                if ("aeiouAEIOUáéíóúÜü".indexOf(c) != -1) count++;
            }
            aux.add(count + "");
        }
        Utils.saveFile("vocales/vocales-"+Utils.getFileNum(f), aux);
    }


}