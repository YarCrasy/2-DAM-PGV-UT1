import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class Parent
{
    final static String dir = "./datos/";
    public List<File> files = new ArrayList<>();
    Process[] childProcess;
    final List <String> resultSummary = new ArrayList<>();

    int vowelTotal = 0;
    int wordsTotal = 0;


    static void main(String[] args) {
        Parent p = new Parent();
        p.loadFiles();
        p.processFiles();
        p.waitProcesses();
        p.processResults();
    }

    void loadFiles()
    {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("No files loaded from directory: " + dir);
            return;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) files.add(file);
        }
    }

    void processFiles()
    {
        childProcess = new Process[files.size()];
        String cp = System.getProperty("java.class.path");
        int i = 0;
        for (File file : files) {
            resultSummary.add(Utils.countWordsInFile(file));
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", cp, "Child", file.getPath());
            pb.directory(new File("."));
            pb.inheritIO();

            try {
                childProcess[i++] = pb.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    void waitProcesses()
    {
        for (Process process : childProcess) {
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void processResults()
    {
        Stack<Thread> threads = new Stack<>();
        startCountingThreads(threads);
        waitCountingThreads(threads);

        float aux = (wordsTotal == 0) ? 0 : (float)vowelTotal / wordsTotal;
        resultSummary.add("Promedio de vocales por palabra: " + aux);

        Utils.saveFile("parent", resultSummary);
    }

    void startCountingThreads(Stack<Thread> threads) {
        File[] resultFolders = getResultFolders();
        if (resultFolders.length == 0) {
            System.err.println("No result folders found.");
            return;
        }
        for (File folder : resultFolders) {
            String path = folder.getPath();
            if (path.equals(".\\resultados\\minusculas")) {
                threads.push(new Thread(countTotalWords(folder))).start();
            }
            else if (path.equals(".\\resultados\\vocales")) {
                threads.push(new Thread(countTotalVowels(folder))).start();
            }
        }
    }

    void waitCountingThreads(Stack<Thread> threads) {
        while (!threads.isEmpty()) {
            try {
                threads.pop().join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    File[] getResultFolders() {
        File resFolder = new File(Utils.savePath);
        File[] resultFiles = resFolder.listFiles();
        if (resultFiles == null) {
            System.err.println("No result folders found in directory getResultFolders(): " + Utils.savePath);
            return new File[0];
        }
        return resultFiles;
    }

    Runnable countTotalWords(File folder) {
        return () -> {
            File[] resultFiles = getFilesInFolder(folder);
            for (File f : resultFiles) {
                String[] lines = Utils.loadFile(f);
                for (String line : lines) {
                    wordsTotal += line.split("\\s+").length;
                }
            }
            resultSummary.add("Total words: " + wordsTotal);
        };
    }

    Runnable countTotalVowels(File folder) {
        return () -> {
            File[] resultFiles = getFilesInFolder(folder);
            for (File f : resultFiles) {
                String[] lines = Utils.loadFile(f);
                for (String line : lines) {
                    vowelTotal += Integer.parseInt(line);
                }
            }
            resultSummary.add("Total vowels: " + vowelTotal);
        };
    }

    File[] getFilesInFolder(File folder) {
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("No files found in directory getFilesInFolder(): " + folder.getPath());
            return new File[0];
        }
        else return listOfFiles;
    }

}