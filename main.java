import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    String name;
    int arrivalTime;
    int priority;
    int executionTime;
    int memoryRequirement;
    int printerCount;
    int scannerCount;
    int modemCount;
    int cdDriveCount;

    public Process(String name, int arrivalTime, int priority, int executionTime,
                   int memoryRequirement, int printerCount, int scannerCount,
                   int modemCount, int cdDriveCount) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.priority = priority;
        this.executionTime = executionTime;
        this.memoryRequirement = memoryRequirement;
        this.printerCount = printerCount;
        this.scannerCount = scannerCount;
        this.modemCount = modemCount;
        this.cdDriveCount = cdDriveCount;
    }
}

class Scheduler {
    Queue<Process> processQueue;

    public Scheduler() {
        this.processQueue = new LinkedList<>();
    }

    public void addProcess(Process process) {
        processQueue.add(process);
    }

    public void runFCFS() {
        while (!processQueue.isEmpty()) {
            Process process = processQueue.poll();
            System.out.println("Running process " + process.name + " using FCFS");
            // Simulate process execution
            try {
                Thread.sleep(process.executionTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void runRoundRobin(int timeQuantum) {
        while (!processQueue.isEmpty()) {
            Process process = processQueue.poll();
            System.out.println("Running process " + process.name + " using Round Robin");
            // Simulate process execution with time quantum
            try {
                Thread.sleep(Math.min(timeQuantum, process.executionTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // If the process is not completed, add it back to the queue
            if (process.executionTime > timeQuantum) {
                process.executionTime -= timeQuantum;
                processQueue.add(process);
            }
        }
    }
}

public class main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        // Read input from the file
        try (BufferedReader br = new BufferedReader(new FileReader("giris.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the input line
                String[] tokens = line.split(",");
                String name = tokens[0].trim();
                int arrivalTime = Integer.parseInt(tokens[1].trim());
                int priority = Integer.parseInt(tokens[2].trim());
                int executionTime = Integer.parseInt(tokens[3].trim());
                int memoryRequirement = Integer.parseInt(tokens[4].trim());
                int printerCount = Integer.parseInt(tokens[5].trim());
                int scannerCount = Integer.parseInt(tokens[6].trim());
                int modemCount = Integer.parseInt(tokens[7].trim());
                int cdDriveCount = Integer.parseInt(tokens[8].trim());

                // Create a process and add it to the scheduler
                Process process = new Process(name, arrivalTime, priority, executionTime,
                        memoryRequirement, printerCount, scannerCount, modemCount, cdDriveCount);
                scheduler.addProcess(process);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Running processes using FCFS
        System.out.println("Running processes using FCFS:");
        scheduler.runFCFS();

        // Resetting the scheduler and adding processes again
        scheduler = new Scheduler();

        // Read input from the file again
        try (BufferedReader br = new BufferedReader(new FileReader("giris.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Parse the input line
                String[] tokens = line.split(",");
                String name = tokens[0].trim();
                int arrivalTime = Integer.parseInt(tokens[1].trim());
                int priority = Integer.parseInt(tokens[2].trim());
                int executionTime = Integer.parseInt(tokens[3].trim());
                int memoryRequirement = Integer.parseInt(tokens[4].trim());
                int printerCount = Integer.parseInt(tokens[5].trim());
                int scannerCount = Integer.parseInt(tokens[6].trim());
                int modemCount = Integer.parseInt(tokens[7].trim());
                int cdDriveCount = Integer.parseInt(tokens[8].trim());

                // Create a process and add it to the scheduler
                Process process = new Process(name, arrivalTime, priority, executionTime,
                        memoryRequirement, printerCount, scannerCount, modemCount, cdDriveCount);
                scheduler.addProcess(process);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Running processes using Round Robin with time quantum of 20
        System.out.println("\nRunning processes using Round Robin:");
        scheduler.runRoundRobin(20);
    }
}
