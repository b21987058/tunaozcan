import java.io.Serializable;
import java.util.*;

public class Project implements Serializable {
    static final long serialVersionUID = 33L;
    private final String name;
    private final List<Task> tasks;

    public Project(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    /**
     * @return the total duration of the project in days
     */
    public int getProjectDuration() {
        int projectDuration = 0;
        int[] schedule = getEarliestSchedule();

        // TODO: YOUR CODE HERE
        for (int i = 0; i < tasks.size(); i++) {
            projectDuration = Math.max(projectDuration, schedule[i] + tasks.get(i).getDuration());
        }


        return projectDuration;
    }

    /**
     * Schedule all tasks within this project such that they will be completed as early as possible.
     *
     * @return An integer array consisting of the earliest start days for each task.
     */
    public int[] getEarliestSchedule() {

        // TODO: YOUR CODE HERE
        int[] earliestStart = new int[tasks.size()];
        int[] earliestEnd = new int[tasks.size()];

        // Create the dependency map
        Map<Integer, List<Integer>> dependencyMap = new HashMap<>();
        int i = 0;

        // Using while loop to populate the dependency map
        while (i < tasks.size()) {
            Task task = tasks.get(i);
            dependencyMap.put(task.getTaskID(), task.getDependencies());
            i++;
        }

        // Reset index for next loop
        i = 0;

        // Using while loop to calculate earliest start times
        while (i < tasks.size()) {
            Task task = tasks.get(i);
            int taskId = task.getTaskID();
            switch (earliestStart[taskId] == 0 ? 1 : 0) {
                case 1:
                    calculateEarliestStart(taskId, earliestStart, earliestEnd, dependencyMap);
                    break;
                default:
                    break;
            }
            i++;
        }

        // Return the earliest start times
        return earliestStart;


    }

    private void calculateEarliestStart(int taskId, int[] earliestStart, int[] earliestEnd, Map<Integer, List<Integer>> dependencyMap) {
        boolean dependenciesChecked = earliestStart[taskId] != 0 || dependencyMap.get(taskId).isEmpty();
        switch (dependenciesChecked ? 1 : 0) {
            case 1:
                earliestEnd[taskId] = earliestStart[taskId] + tasks.get(taskId).getDuration();
                return;
            default:
                break;
        }

        int maxEndTime = 0;
        List<Integer> dependencies = dependencyMap.get(taskId);
        int i = 0;

        while (i < dependencies.size()) {
            int dependency = dependencies.get(i);
            switch (earliestStart[dependency] == 0 ? 1 : 0) {
                case 1:
                    calculateEarliestStart(dependency, earliestStart, earliestEnd, dependencyMap);
                    break;
                default:
                    break;
            }
            maxEndTime = Math.max(maxEndTime, earliestEnd[dependency]);
            i++;
        }

        earliestStart[taskId] = maxEndTime;
        earliestEnd[taskId] = earliestStart[taskId] + tasks.get(taskId).getDuration();
    }

    public static void printlnDash(int limit, char symbol) {
        for (int i = 0; i < limit; i++) System.out.print(symbol);
        System.out.println();
    }

    /**
     * Some free code here. YAAAY! 
     */
    public void printSchedule(int[] schedule) {
        int limit = 65;
        char symbol = '-';
        printlnDash(limit, symbol);
        System.out.println(String.format("Project name: %s", name));
        printlnDash(limit, symbol);

        // Print header
        System.out.println(String.format("%-10s%-45s%-7s%-5s","Task ID","Description","Start","End"));
        printlnDash(limit, symbol);
        for (int i = 0; i < schedule.length; i++) {
            Task t = tasks.get(i);
            System.out.println(String.format("%-10d%-45s%-7d%-5d", i, t.getDescription(), schedule[i], schedule[i]+t.getDuration()));
        }
        printlnDash(limit, symbol);
        System.out.println(String.format("Project will be completed in %d days.", tasks.get(schedule.length-1).getDuration() + schedule[schedule.length-1]));
        printlnDash(limit, symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;

        int equal = 0;

        for (Task otherTask : ((Project) o).tasks) {
            if (tasks.stream().anyMatch(t -> t.equals(otherTask))) {
                equal++;
            }
        }

        return name.equals(project.name) && equal == tasks.size();
    }

}
