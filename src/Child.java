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

    void countVowels(){
        List<String> aux = new ArrayList<>();
        for (int num : Utils.countVowels(fileLines)) {
            aux.add(Integer.toString(num));
        }

        String path = "vocales/vocales-" + Utils.getFileNum(f);
        Utils.saveFile(path, aux);
    }

    void toLowerCase() {
        List<String> aux = new ArrayList<>();
        for (String fileLine : fileLines) {
            aux.add(fileLine.toLowerCase());
        }
        String path = "minusculas/minusculas-" + Utils.getFileNum(f);
        Utils.saveFile(path, aux);
    }




}