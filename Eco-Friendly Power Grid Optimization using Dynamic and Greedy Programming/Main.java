import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

/**
 * Main class
 */
// FREE CODE HERE
public class Main {
    public static void main(String[] args) throws IOException {

       /** MISSION POWER GRID OPTIMIZATION BELOW **/

        System.out.println("##MISSION POWER GRID OPTIMIZATION##");
        // You are expected to read the file given as the first command-line argument to read
        // the energy demands arriving per hour. Then, use this data to instantiate a
        // PowerGridOptimization object. You need to call getOptimalPowerGridSolutionDP() method
        // of your PowerGridOptimization object to get the solution, and finally print it to STDOUT.
        // TODO: Your code goes here

        // file reading section into an arraylist of integers
        String filename = args[0];
        ArrayList<Integer> energyDemands = readEnergyDemands(filename);

        // to create a new PowerGridOptimization object
        PowerGridOptimization powerGrid = new PowerGridOptimization(energyDemands);

        // where everything goes on basically
        OptimalPowerGridSolution solution = powerGrid.getOptimalPowerGridSolutionDP();

        // printing solution for Mission Zero to STDOUT
        System.out.println("The total number of demanded gigawatts: " + energyDemands.stream().mapToInt(Integer::intValue).sum());
        System.out.println("Maximum number of satisfied gigawatts: " + solution.getmaxNumberOfSatisfiedDemands());
        System.out.print("Hours at which the battery bank should be discharged: ");
        ArrayList<Integer> hours = solution.getHoursToDischargeBatteriesForMaxEfficiency();
        for (int i = 0; i < hours.size(); i++) {
            System.out.print(hours.get(i));
            if (i < hours.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
        System.out.println("The number of unsatisfied gigawatts: " + (energyDemands.stream().mapToInt(Integer::intValue).sum() - solution.getmaxNumberOfSatisfiedDemands()));
        System.out.println("##MISSION POWER GRID OPTIMIZATION COMPLETED##");

        /** MISSION ECO-MAINTENANCE BELOW **/

        // Read the number of available ESVs and ESV capacity from the file
        String esvMaintenanceFilename = args[1];
        int maxNumberOfAvailableESVs = 0;
        int maxESVCapacity = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(esvMaintenanceFilename))) {
            String[] esvInfo = reader.readLine().trim().split("\\s+");
            maxNumberOfAvailableESVs = Integer.parseInt(esvInfo[0]);
            maxESVCapacity = Integer.parseInt(esvInfo[1]);
        } catch (IOException e) {
            System.out.println("Error reading ESV maintenance file: " + e.getMessage());
            return;
        }

        // a try catch block to read the energy requirements of maintenance tasks from the file ESVMaintenance.dat
        ArrayList<Integer> maintenanceTaskEnergyDemands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(esvMaintenanceFilename))) {
            reader.readLine(); // Skip the first line (ESV info)
            String[] energyDemands2 = reader.readLine().trim().split("\\s+");
            for (String demand : energyDemands2) {
                maintenanceTaskEnergyDemands.add(Integer.parseInt(demand));
            }
        } catch (IOException e) {
            System.out.println("Error reading ESV maintenance file: " + e.getMessage());
            return;
        }

        // to create a new OptimalESVDeploymentGP object
        OptimalESVDeploymentGP esvDeploymentGP = new OptimalESVDeploymentGP(maintenanceTaskEnergyDemands);

        // to get the solution
        int minNumESVsToDeploy = esvDeploymentGP.getMinNumESVsToDeploy(maxNumberOfAvailableESVs, maxESVCapacity);

        System.out.println("##MISSION ECO-MAINTENANCE##");
        // TODO: Your code goes here
        // You are expected to read the file given as the second command-line argument to read
        // the number of available ESVs, the capacity of each available ESV, and the energy requirements 
        // of the maintenance tasks. Then, use this data to instantiate an OptimalESVDeploymentGP object.
        // You need to call getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) method
        // of your OptimalESVDeploymentGP object to get the solution, and finally print it to STDOUT.

        // printing solution to STDOUT for mission1: ECO-MAINTENANCE
        if (minNumESVsToDeploy != -1) {
            System.out.println("The minimum number of ESVs to deploy: " + minNumESVsToDeploy);
            ArrayList<ArrayList<Integer>> tasksAssignedToESVs = esvDeploymentGP.getMaintenanceTasksAssignedToESVs();
            for (int i = 0; i < tasksAssignedToESVs.size(); i++) {
                System.out.println("ESV " + (i + 1) + " tasks: " + tasksAssignedToESVs.get(i));
            }
        } else {
            System.out.println("Warning: Mission Eco-Maintenance Failed.");
        }
        System.out.println("##MISSION ECO-MAINTENANCE COMPLETED##");
    }


    // a method to read energy demands from file for missin zero
    private static ArrayList<Integer> readEnergyDemands(String filename) throws IOException {
        ArrayList<Integer> energyDemands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                for (String token : tokens) {
                    energyDemands.add(Integer.parseInt(token));
                }
            }
        }
        return energyDemands;
    }
}
