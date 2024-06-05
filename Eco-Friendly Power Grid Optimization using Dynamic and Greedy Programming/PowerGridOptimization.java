import java.util.ArrayList;

/**
 * This class accomplishes Mission POWER GRID OPTIMIZATION
 */
public class PowerGridOptimization {
    private ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour;

    public PowerGridOptimization(ArrayList<Integer> amountOfEnergyDemandsArrivingPerHour){
        this.amountOfEnergyDemandsArrivingPerHour = amountOfEnergyDemandsArrivingPerHour;
    }

    public ArrayList<Integer> getAmountOfEnergyDemandsArrivingPerHour() {
        return amountOfEnergyDemandsArrivingPerHour;
    }
    /**
     *     Function to implement the given dynamic programming algorithm
     *     SOL(0) <- 0
     *     HOURS(0) <- [ ]
     *     For{j <- 1...N}
     *         SOL(j) <- max_{0<=i<j} [ (SOL(i) + min[ E(j), P(j − i) ] ]
     *         HOURS(j) <- [HOURS(i), j]
     *     EndFor
     *
     * @return OptimalPowerGridSolution
     */
    public OptimalPowerGridSolution getOptimalPowerGridSolutionDP(){
        // TODO: YOUR CODE HERE
        int N = amountOfEnergyDemandsArrivingPerHour.size();
        int[] SOL = new int[N + 1]; // a new array to store optimal solutions
        ArrayList<ArrayList<Integer>> HOURS = new ArrayList<>(); // a new arraylist of Integer arraylist to store hours for optimal solutions

        SOL[0] = 0;
        HOURS.add(new ArrayList<>()); // initializing with an empty list

        // Looping through each hour
        for (int j = 1; j <= N; j++) {
            int maxSatisfied = 0;
            int bestHour = -1;

            // Finding the maximum satisfied demand by trying all possible discharge hours
            for (int i = 0; i < j; i++) {
                int satisfied = SOL[i] + Math.min(amountOfEnergyDemandsArrivingPerHour.get(j - 1), getEffectiveEnergy(j - i));
                if (satisfied > maxSatisfied) {
                    maxSatisfied = satisfied;
                    bestHour = i;
                }
            }

            SOL[j] = maxSatisfied;

            // Constructing the list of hours for the optimal solution
            ArrayList<Integer> hourList = new ArrayList<>();
            if (bestHour != -1) {
                hourList.addAll(HOURS.get(bestHour));
            }
            hourList.add(j);
            HOURS.add(hourList);
        }

        return new OptimalPowerGridSolution(SOL[N], HOURS.get(N));
    }

    // a private function to calculate effective energy discharged after i hours of charging
    private int getEffectiveEnergy(int i) {
        return i * i;
    }

}
