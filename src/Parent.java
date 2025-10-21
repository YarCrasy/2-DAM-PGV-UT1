import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Parent
{
    final String dir = "./datos/";
    public List<File> files = new ArrayList<>();
    Process[] childProcess;

    public static void main(String[] args) {
        Parent p = new Parent();
        p.loadFiles();
        p.processFiles();
        p.waitProcesses();
        p.readResults();
    }

    void loadFiles()
    {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
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
    }

    void readResults()
    {
        File[] resultFiles = new File[files.size()*2];
    }

}