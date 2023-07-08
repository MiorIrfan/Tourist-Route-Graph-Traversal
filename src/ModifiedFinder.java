import java.util.*;

public class ModifiedFinder {
    private int numVertices;
    private List<Integer>[] adjacencyList;
    private List<Edge>[] edgeList;
    private List<Integer> path;
    private boolean pathFound;

    public ModifiedFinder(int numVertices) {
        this.numVertices = numVertices;
        adjacencyList = new ArrayList[numVertices];
        edgeList = new ArrayList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
            edgeList[i] = new ArrayList<>();
        }
        path = new ArrayList<>();
        pathFound = false;
    }

    public void addEdge(int source, int destination, int value) {
        adjacencyList[source].add(destination);
        edgeList[source].add(new Edge(source, destination, value));
    }

    public List<Integer> findPathDFS(int source, int destination) {
        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];
        Arrays.fill(parent, -1);
        dfs(source, destination, visited, parent);

        if (pathFound) {
            List<Integer> finalPath = new ArrayList<>();
            int current = destination;
            while (current != -1) {
                finalPath.add(0, current);
                current = parent[current];
            }
            return finalPath;
        } else {
            return null;
        }
    }

    private void dfs(int current, int destination, boolean[] visited, int[] parent) {
        visited[current] = true;
        if (current == destination) {
            pathFound = true;
            return;
        }
        for (int i = 0; i < adjacencyList[current].size(); i++) {
            int neighbor = adjacencyList[current].get(i);
            if (!visited[neighbor]) {
                parent[neighbor] = current;
                dfs(neighbor, destination, visited, parent);
                if (pathFound) {
                    return;
                }
            }
        }
    }

    static class Edge {
        int source;
        int destination;
        int value;

        public Edge(int source, int destination, int value) {
            this.source = source;
            this.destination = destination;
            this.value = value;
        }
    }

    public int getEdgeValue(int source, int destination) {
        for (Edge edge : edgeList[source]) {
            if (edge.destination == destination) {
                return edge.value;
            }
        }
        return 0;  // If no edge found, return 0 or a suitable default value
    }

    public static void main(String[] args) {
        // Create a road network graph
        int numCities = 7;
        ModifiedFinder pathFinder = new ModifiedFinder(numCities);
        pathFinder.addEdge(0, 1, 5);  // Road from City 0 (source) to City 1 with distance 5
        pathFinder.addEdge(0, 2, 7);  // Road from City 0 (source) to City 2 with distance 7
        pathFinder.addEdge(1, 3, 10); // Road from City 1 to City 3 with distance 10
        pathFinder.addEdge(2, 3, 3);  // Road from City 2 to City 3 with distance 3
        pathFinder.addEdge(3, 4, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(3, 5, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(4, 5, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(5, 1, 4);  // Road from City 5 to City 6 with distance 4

        // Prompt the user to enter the source and destination cities
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter source city: ");
        String sourceCity = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String destinationCity = scanner.nextLine();

        // Find the path using DFS
        int source = cityToVertex(sourceCity);
        int destination = cityToVertex(destinationCity);
        List<Integer> path = pathFinder.findPathDFS(source, destination);

        // Output the result
        if (path != null) {
            System.out.println("Path from " + sourceCity + " to " + destinationCity + ":");
            for (int i = 0; i < path.size(); i++) {
                int vertex = path.get(i);
                String city = vertexToCity(vertex);
                System.out.print(city);
                if (i != path.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();

            // Calculate the total weight of the path
            int totalWeight = 0;
            for (int i = 0; i < path.size() - 1; i++) {
                int sourceVertex = path.get(i);
                int destinationVertex = path.get(i + 1);
                int edgeWeight = pathFinder.getEdgeValue(sourceVertex, destinationVertex);
                totalWeight += edgeWeight;
            }
            System.out.println("Total distance of the path: " + totalWeight + " km");
        } else {
            System.out.println("No path from " + sourceCity + " to " + destinationCity + " found.");
        }
    }

    // Helper method to convert city name to vertex number
    private static int cityToVertex(String city) {
        switch (city) {
            case "Butterworth":
                return 0;
            case "Jelutong":
                return 1;
            case "Air Itam":
                return 2;
            case "Gelugor":
                return 3;
            case "George Town ":
                return 4;
            case "Balik Pulau":
                return 5;
            case "Bayan Lepas":
                return 6;
            default:
                throw new IllegalArgumentException("Invalid city name: " + city);
        }
    }

    // Helper method to convert vertex number to city name
    private static String vertexToCity(int vertex) {
        switch (vertex) {
            case 0:
                return "Butterworth";
            case 1:
                return "Jelutong";
            case 2:
                return "Air Itam";
            case 3:
                return "Gelugor";
            case 4:
                return "George Town";
            case 5:
                return "Balik Pulau";
            case 6:
                return "Bayan Lepas";
            default:
                throw new IllegalArgumentException("Invalid vertex number: " + vertex);
        }
    }
}
