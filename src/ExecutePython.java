import java.io.IOException;

public class ExecutePython {
    private final String pathFile;

    public ExecutePython(String pathFile) {
        this.pathFile = pathFile;
    }


    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder("python", pathFile);
        processBuilder.redirectErrorStream(true);
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int exitCode = 0;
        try {
            exitCode = process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}