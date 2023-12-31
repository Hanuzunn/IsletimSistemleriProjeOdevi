import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    int arrivalTime;
    int priority;
    int executionTime;
    int memoryRequirement;
    int printerCount;
    int scannerCount;
    int modemCount;
    int cdDriveCount;

    public Process(int arrivalTime, int priority, int executionTime,
                   int memoryRequirement, int printerCount, int scannerCount,
                   int modemCount, int cdDriveCount) {
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.executionTime = executionTime;
        this.memoryRequirement = memoryRequirement;
        this.printerCount = printerCount;
        this.scannerCount = scannerCount;
        this.modemCount = modemCount;
        this.cdDriveCount = cdDriveCount;
    }

    @Override
    public String toString() {
        return "Process{" +
                "arrivalTime=" + arrivalTime +
                ", priority=" + priority +
                ", executionTime=" + executionTime +
                ", memoryRequirement=" + memoryRequirement +
                ", printerCount=" + printerCount +
                ", scannerCount=" + scannerCount +
                ", modemCount=" + modemCount +
                ", cdDriveCount=" + cdDriveCount +
                '}';
    }
}

class Dispatcher {
    Queue<Process> realTimeQueue;
    Queue<Process> userQueue;
    int availablePrinters;
    int availableScanners;
    int availableModems;
    int availableCdDrives;
    int availableMemory;

    public Dispatcher() {
        this.realTimeQueue = new LinkedList<>();
        this.userQueue = new LinkedList<>();
        this.availablePrinters = 2;
        this.availableScanners = 1;
        this.availableModems = 1;
        this.availableCdDrives = 2;
        this.availableMemory = 1024;
    }

    public void addProcess(Process process) {
        if (process.priority == 0) {
            realTimeQueue.add(process);
        } else {
            userQueue.add(process);
        }
    }

    public void runDispatcher() {
        while (!realTimeQueue.isEmpty() || !userQueue.isEmpty()) {
            if (!realTimeQueue.isEmpty()) {
                runRealTimeProcess();
            } else if (!userQueue.isEmpty()) {
                runUserProcess();
            }
        }
    }

    private void runRealTimeProcess() {
        Process process = realTimeQueue.poll();
        System.out.println("Running real-time process: " + process);
        simulateProcessExecution(process);
    }

    private void runUserProcess() {
        Process process = userQueue.poll();
        System.out.println("Running user process: " + process);
        simulateProcessExecution(process);
    }

    private void simulateProcessExecution(Process process) {
        // Simulate process execution
        try {
            Thread.sleep(process.executionTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Process completed: " + process);

        // Release resources
        availableMemory += process.memoryRequirement;
        availablePrinters += process.printerCount;
        availableScanners += process.scannerCount;
        availableModems += process.modemCount;
        availableCdDrives += process.cdDriveCount;
    }
}

public class Main {
    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        // Read processes from the input file
        try (BufferedReader br = new BufferedReader(new FileReader("giriş.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                int arrivalTime = Integer.parseInt(tokens[0].trim());
                int priority = Integer.parseInt(tokens[1].trim());
                int executionTime = Integer.parseInt(tokens[2].trim());
                int memoryRequirement = Integer.parseInt(tokens[3].trim());
                int printerCount = Integer.parseInt(tokens[4].trim());
                int scannerCount = Integer.parseInt(tokens[5].trim());
                int modemCount = Integer.parseInt(tokens[6].trim());
                int cdDriveCount = Integer.parseInt(tokens[7].trim());

                Process process = new Process(arrivalTime, priority, executionTime,
                        memoryRequirement, printerCount, scannerCount, modemCount, cdDriveCount);

                dispatcher.addProcess(process);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Run the dispatcher
        dispatcher.runDispatcher();
    }
}
