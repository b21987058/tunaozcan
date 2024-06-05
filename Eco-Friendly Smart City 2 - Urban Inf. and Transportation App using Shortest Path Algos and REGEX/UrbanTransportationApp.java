import java.io.Serializable;
import java.util.*;

class UrbanTransportationApp implements Serializable {
    static final long serialVersionUID = 99L;

    public HyperloopTrainNetwork readHyperloopTrainNetwork(String filename) {
        HyperloopTrainNetwork hyperloopTrainNetwork = new HyperloopTrainNetwork();
        hyperloopTrainNetwork.readInput(filename);
        return hyperloopTrainNetwork;
    }

    /**
     * Function to calculate the fastest route from the user's desired starting point to
     * the desired destination point, taking into consideration the hyperloop train
     * network.
     * @return List of RouteDirection instances
     */
    public List<RouteDirection> getFastestRouteDirections(HyperloopTrainNetwork network) {
        List<RouteDirection> routeDirections = new ArrayList<>();

        // Create a priority queue to store the minimum distance to each station
        PriorityQueue<StationDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(sd -> sd.distance));
        Map<Station, Double> distanceMap = new HashMap<>();
        Map<Station, RouteDirection> previousMap = new HashMap<>();

        // Initialize distances
        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                distanceMap.put(station, Double.MAX_VALUE);
            }
        }
        distanceMap.put(network.startPoint, 0.0);

        // Add the starting point to the priority queue
        pq.add(new StationDistance(network.startPoint, 0.0));

        while (!pq.isEmpty()) {
            StationDistance current = pq.poll();
            Station currentStation = current.station;
            double currentDistance = current.distance;

            if (currentDistance > distanceMap.get(currentStation)) continue;

            // Explore neighboring stations
            for (TrainLine line : network.lines) {
                if (line.trainLineStations.contains(currentStation)) {
                    int index = line.trainLineStations.indexOf(currentStation);

                    // Check previous station
                    if (index > 0) {
                        Station neighbor = line.trainLineStations.get(index - 1);
                        double newDist = currentDistance + currentStation.coordinates.distanceTo(neighbor.coordinates) / network.averageTrainSpeed * 60;
                        if (newDist < distanceMap.get(neighbor)) {
                            distanceMap.put(neighbor, newDist);
                            pq.add(new StationDistance(neighbor, newDist));
                            previousMap.put(neighbor, new RouteDirection(currentStation.description, neighbor.description, newDist - currentDistance, true));
                        }
                    }

                    // Check next station
                    if (index < line.trainLineStations.size() - 1) {
                        Station neighbor = line.trainLineStations.get(index + 1);
                        double newDist = currentDistance + currentStation.coordinates.distanceTo(neighbor.coordinates) / network.averageTrainSpeed * 60;
                        if (newDist < distanceMap.get(neighbor)) {
                            distanceMap.put(neighbor, newDist);
                            pq.add(new StationDistance(neighbor, newDist));
                            previousMap.put(neighbor, new RouteDirection(currentStation.description, neighbor.description, newDist - currentDistance, true));
                        }
                    }
                }
            }

            // Explore walking to other stations
            for (TrainLine line : network.lines) {
                for (Station station : line.trainLineStations) {
                    if (!station.equals(currentStation)) {
                        double newDist = currentDistance + currentStation.coordinates.distanceTo(station.coordinates) / network.averageWalkingSpeed * 60;
                        if (newDist < distanceMap.get(station)) {
                            distanceMap.put(station, newDist);
                            pq.add(new StationDistance(station, newDist));
                            previousMap.put(station, new RouteDirection(currentStation.description, station.description, newDist - currentDistance, false));
                        }
                    }
                }
            }
        }

        // Reconstruct the path
        Station current = network.destinationPoint;
        while (previousMap.containsKey(current)) {
            routeDirections.add(previousMap.get(current));
            current = getPreviousStation(current, previousMap, network);
        }
        Collections.reverse(routeDirections);
        return routeDirections;
    }

    private Station getPreviousStation(Station current, Map<Station, RouteDirection> previousMap, HyperloopTrainNetwork network) {
        RouteDirection direction = previousMap.get(current);
        for (TrainLine line : network.lines) {
            for (Station station : line.trainLineStations) {
                if (station.description.equals(direction.startStationName)) {
                    return station;
                }
            }
        }
        return null;
    }

    /**
     * Function to print the route directions to STDOUT
     */
    public void printRouteDirections(List<RouteDirection> directions) {
        double totalTime = directions.stream().mapToDouble(d -> d.duration).sum();
        System.out.printf("The fastest route takes %.0f minute(s).%n", totalTime);
        System.out.println("Directions");
        System.out.println("----------");

        int step = 1;
        for (RouteDirection direction : directions) {
            String mode = direction.trainRide ? "Get on the train" : "Walk";
            System.out.printf("%d. %s from \"%s\" to \"%s\" for %.2f minutes.%n",
                    step++, mode, direction.startStationName, direction.endStationName, direction.duration);
        }
    }

    private static class StationDistance {
        Station station;
        double distance;

        StationDistance(Station station, double distance) {
            this.station = station;
            this.distance = distance;
        }
    }
}
