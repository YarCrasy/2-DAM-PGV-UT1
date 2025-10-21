import java.io.File;
import java.nio.*;
import java.nio.file.Files;
import java.util.List;

public class Child {

    private File f;
    String[] fileLines = new String[0];

    public void main(String[] args) {
        if (args.length == 0) {
            System.err.println(this.getClass().getSimpleName() + ": No arguments provided");
            return;
        }
        f = new File(args[0]);
        loadFile();
        toLowerCase();
        countVowels();
    }

    void loadFile() {
        try {
            fileLines = Files.readAllLines(f.toPath()).toArray(new String[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void toLowerCase() {
        String[] aux = new String[fileLines.length];
        for (int i = 0; i < fileLines.length; i++) {
            aux[i] = fileLines[i].toLowerCase();
        }
        saveFile("minusculas", getFileNum(), aux);
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
        saveFile("vocales", getFileNum(), aux);
    }

    void saveFile(String fileName, int num, String[] lines) {
        File file = new File("./resultados/" + fileName + "/" + fileName + "-" + num + ".res");
        try {
            if (!file.exists()) {
                Files.createDirectories(file.toPath().getParent());
                Files.createFile(file.toPath());
            }
            Files.write(file.toPath(), List.of(lines));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    int getFileNum() {
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

}