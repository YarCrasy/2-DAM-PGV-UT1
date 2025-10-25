import java.io.File;
import java.nio.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Child {

    private File f;
    String[] fileLines;

    public void main(String[] args) {
        if (args.length == 0) {
            System.err.println(this.getClass().getSimpleName() + ": No arguments provided");
            return;
        }
        f = new File(args[0]);
        fileLines = Utils.loadFile(f);
        toLowerCase();
        countVowels();
    }

    void toLowerCase() {
        List<String> aux = new ArrayList<>();
        for (String fileLine : fileLines) {
            aux.add(fileLine.toLowerCase());
        }
        Utils.saveFile("minusculas", Utils.getFileNum(f), aux);
    }

    void countVowels() {
        String[] aux = new String[fileLines.length];
        for (int i = 0; i < fileLines.length; i++) {
            int count = 0;
            for (char c : fileLines[i].toCharArray()) {
                if ("aeiouAEIOUáéíóúÜü".indexOf(c) != -1) {
                    count++;
                }
            }
            aux[i] = count + "";
        }
        Utils.saveFile("vocales", Utils.getFileNum(f), aux);
    }


}