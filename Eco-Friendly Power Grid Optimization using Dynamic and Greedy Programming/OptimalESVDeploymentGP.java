import java.util.ArrayList;
import java.util.Collections;

/**
 * This class accomplishes Mission Eco-Maintenance
 */
public class OptimalESVDeploymentGP {
    private ArrayList<Integer> maintenanceTaskEnergyDemands;

    /*
     * Should include tasks assigned to ESVs.
     * For the sample input:
     * 8 100
     * 20 50 40 70 10 30 80 100 10
     * 
     * The list should look like this:
     * [[100], [80, 20], [70, 30], [50, 40, 10], [10]]
     * 
     * It is expected to be filled after getMinNumESVsToDeploy() is called.
     */
    private ArrayList<ArrayList<Integer>> maintenanceTasksAssignedToESVs = new ArrayList<>();

    ArrayList<ArrayList<Integer>> getMaintenanceTasksAssignedToESVs() {
        return maintenanceTasksAssignedToESVs;
    }

    public OptimalESVDeploymentGP(ArrayList<Integer> maintenanceTaskEnergyDemands) {
        this.maintenanceTaskEnergyDemands = maintenanceTaskEnergyDemands;
    }

    public ArrayList<Integer> getMaintenanceTaskEnergyDemands() {
        return maintenanceTaskEnergyDemands;
    }

    /**
     *
     * @param maxNumberOfAvailableESVs the maximum number of available ESVs to be deployed
     * @param maxESVCapacity the maximum capacity of ESVs
     * @return the minimum number of ESVs required using first fit approach over reversely sorted items.
     * Must return -1 if all tasks can't be satisfied by the available ESVs
     */
    public int getMinNumESVsToDeploy(int maxNumberOfAvailableESVs, int maxESVCapacity) {
        // TODO: Your code goes here
        // sorting the maintenance tasks by energy requirements in decreasing / reverse order
        maintenanceTaskEnergyDemands.sort(Collections.reverseOrder());

        // Initialize ESVs with remaining energy capacities
        ArrayList<Integer> remainingESVCapacities = new ArrayList<>();
        int ii = 0;
        while (ii < maxNumberOfAvailableESVs) {
            remainingESVCapacities.add(maxESVCapacity);
            ii++;
        }
        int filledCount = 0;

        // Iterate over maintenance tasks
        for (int task : maintenanceTaskEnergyDemands) {

            // Find the first ESV that can accommodate the task
            for (int i = 0; i < remainingESVCapacities.size(); i++) {
                if (task <= remainingESVCapacities.get(i)) {
                    // Assign task to ESV
                    if (maintenanceTasksAssignedToESVs.size() <= i) {
                        maintenanceTasksAssignedToESVs.add(new ArrayList<>());
                    }
                    maintenanceTasksAssignedToESVs.get(i).add(task);

                    // Update remaining energy capacity of the ESV
                    remainingESVCapacities.set(i, remainingESVCapacities.get(i) - task);
                    filledCount++;
                    break;
                }
            }


        }

        if (filledCount != maintenanceTaskEnergyDemands.size()) {
            return -1;
        }
        // Return the number of ESVs used at  the end
        return maintenanceTasksAssignedToESVs.size();
    }

}
