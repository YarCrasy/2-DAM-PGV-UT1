import java.io.File;
import java.nio.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Child {

    private File f;
    String[] fileLines;

    public static void main(String[] args) {
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

    void toLowerCase() {
        List<String> aux = new ArrayList<>();
        for (String fileLine : fileLines) {
            aux.add(fileLine.toLowerCase());
        }
        Utils.saveFile("minusculas/minusculas-"+Utils.getFileNum(f), aux);
    }

    void countVowels() {
        List<String> aux = new ArrayList<>();
        for (int i = 0; i < fileLines.length; i++) {
            int count = 0;
            for (char c : fileLines[i].toCharArray()) {
                if ("aeiouAEIOUáéíóúÜü".indexOf(c) != -1) {
                    count++;
                }
            }
            aux.add(count + "");
        }
        Utils.saveFile("vocales/vocales-"+Utils.getFileNum(f), aux);
    }


}