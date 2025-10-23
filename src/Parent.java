import jdk.jshell.execution.Util;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Parent
{
    final static String dir = "./datos/";
    public List<File> files = new ArrayList<>();
    Process[] childProcess;

    final List <String> resultSummary = new ArrayList<>();

    public static void main(String[] args) {
        Parent p = new Parent();
        p.loadFiles();
        p.processFiles();
        int totalVowels = 0;
        for (File file : p.files) {
            p.resultSummary.add(Utils.countWordsInFile(file));
        }
        p.waitProcesses();
        p.readResults();
    }

    void loadFiles()
    {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("No files found in directory: " + dir);
            return;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file);
            }
        }
    }

    void processFiles()
    {
        childProcess = new Process[files.size()];
        String cp = System.getProperty("java.class.path");
        int i = 0;
        for (File file : files) {
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
//        CompletableFuture<?>[] futures = Arrays.stream(childProcess)
//                .map(Process::onExit)
//                .toArray(CompletableFuture[]::new);
//        CompletableFuture.allOf(futures).join();
    }

    void readResults()
    {
        File[] resultFolders = getResultFolders();
        if (resultFolders.length == 0) {
            System.err.println("No result folders found.");
            return;
        }
        Thread[] threads = new Thread[resultFolders.length];
        int i = 0;
        for (File folder : resultFolders) {
            File[] files = getFilesInFolder(folder);
            threads[i] = new Thread(()-> {
                for (File file : files) {
                    String[] fileLines = Utils.loadFile(file);

                    synchronized (resultSummary) {
                        assert fileLines != null;
                        resultSummary.addAll(Arrays.stream(fileLines).toList());
                    }
                }
            });
            i++;
        }

        Utils.saveFile("parent", resultSummary);
    }

    File[] getResultFolders() {
        File resFolder = new File(Utils.savePath);
        File[] resultFiles = resFolder.listFiles();
        if (resultFiles == null) {
            System.err.println("No result folders found in directory: " + Utils.savePath);
            return new File[0];
        }
        return resultFiles;
    }

    File[] getFilesInFolder(File folder) {
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("No files found in directory: " + folder.getPath());
            return new File[0];
        }
        else return listOfFiles;
    }

}