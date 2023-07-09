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
        int numCities = 48;
        ModifiedFinder pathFinder = new ModifiedFinder(numCities);
        //502
        pathFinder.addEdge(1, 2, 5);  // Road from City 0 (source) to City 1 with distance 5
        pathFinder.addEdge(2, 3, 7);  // Road from City 0 (source) to City 2 with distance 7
        pathFinder.addEdge(3, 4, 10); // Road from City 1 to City 3 with distance 10
        pathFinder.addEdge(4, 5, 3);  // Road from City 2 to City 3 with distance 3
        pathFinder.addEdge(5, 6, 8);  // Road from City 3 to City 4 with distance 8

        //502 reverse
        pathFinder.addEdge(6, 5, 5);  // Road from City 0 (source) to City 1 with distance 5
        pathFinder.addEdge(5, 4, 7);  // Road from City 0 (source) to City 2 with distance 7
        pathFinder.addEdge(4, 3, 10); // Road from City 1 to City 3 with distance 10
        pathFinder.addEdge(3, 2, 3);  // Road from City 2 to City 3 with distance 3
        pathFinder.addEdge(2, 1, 8);  // Road from City 3 to City 4 with distance 8

        //102
        pathFinder.addEdge(7, 8, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(8, 9, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(9, 4, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(4, 5, 5);  // Road from City 0 (source) to City 1 with distance 5
        pathFinder.addEdge(5, 10, 7);  // Road from City 0 (source) to City 2 with distance 7
        pathFinder.addEdge(10, 11, 10); // Road from City 1 to City 3 with distance 10
        pathFinder.addEdge(11, 12, 3);  // Road from City 2 to City 3 with distance 3

        //102 reverse
        pathFinder.addEdge(12, 11, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(11, 10, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(10, 5, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(5, 4, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(4, 9, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(9, 8, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(8, 7, 2);  // Road from City 3 to City 5 with distance 2

        //306
        pathFinder.addEdge(7, 14, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(14, 19, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(19, 15, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(15, 16, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(16, 4, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(4, 10, 2);  // Road from City 3 to City 5 with distance 2

        //306 reverse
        pathFinder.addEdge(10, 4, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(4, 16, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(16, 15, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(15, 19, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(19, 14, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(14, 7, 4);  // Road from City 5 to City 6 with distance 4

        //302
        pathFinder.addEdge(13, 20, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(20, 14, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(14, 15, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(15, 17, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(17, 18, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(18, 6, 2);  // Road from City 3 to City 5 with distance 2

        //302 reverse
        pathFinder.addEdge(6, 18, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(18, 17, 2);  // Road from City 3 to City 5 with distance 2
        pathFinder.addEdge(17, 15, 6);  // Road from City 4 to City 6 with distance 6
        pathFinder.addEdge(15, 14, 4);  // Road from City 5 to City 6 with distance 4
        pathFinder.addEdge(14, 20, 8);  // Road from City 3 to City 4 with distance 8
        pathFinder.addEdge(20, 13, 2);  // Road from City 3 to City 5 with distance 2

        //Display message to welcome user
        System.out.print("******Welcome to Tourist Navigation Plans Application******\n");
        System.out.print("You can see the available places to visit from your current location\n");
        System.out.print("This application will suggest top 1 routes that you can take\n\n");

        // Prompt the user to enter the source and destination cities
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your current place: ");
        String sourceCity = scanner.nextLine().trim().toUpperCase();
        System.out.print("Enter your destination place: ");
        String destinationCity = scanner.nextLine().trim().toUpperCase();

        System.out.println();

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
        switch (city.toUpperCase()) {
            case "BALIK PULAU":
                return 1;
            case "MUZIUM CAFE WESTERN":
                return 2;
            case "KEK LOK SI TEMPLE":
                return 3;
            case "MASJID NEGERI":
                return 4;
            case "KOMTAR":
                return 5;
            case "JETTY":
                return 6;
            case "AIRPORT PENANG":
                return 7;
            case "SUNGAI TIRAM":
                return 8;
            case "GELUGOR PENANG":
                return 9;
            case "GENERAL HOSPITAL PENANG":
                return 10;
            case "BATU FERRINGHI":
                return 11;
            case "TELUK BAHANG":
                return 12;
            case "BATU MAUNG":
                return 13;
            case "SUNSHINE BAYAN BARU":
                return 14;
            case "KOMPLEKS BUKIT JAMBUL":
                return 15;
            case "ORIENTAL GARDEN":
                return 16;
            case "JELUTONG":
                return 17;
            case "SUNGAI PINANG":
                return 18;
            case "QUEENSBAY":
                return 19;
            case "MUZIUM PERANG":
                return 20;
            default:
                throw new IllegalArgumentException("Invalid city name: " + city);
        }
    }

    // Helper method to convert vertex number to city name
    private static String vertexToCity(int vertex) {
        switch (vertex) {
            case 1:
                return "BALIK PULAU";
            case 2:
                return "MUZIUM CAFE WESTERN";
            case 3:
                return "KEK LOK SI TEMPLE";
            case 4:
                return "MASJID NEGERI";
            case 5:
                return "KOMTAR";
            case 6:
                return "JETTY";
            case 7:
                return "AIRPORT PENANG";
            case 8:
                return "SUNGAI TIRAM";
            case 9:
                return "GELUGOR PENANG";
            case 10:
                return "GENERAL HOSPITAL PENANG";
            case 11:
                return "BATU FERRINGHI";
            case 12:
                return "TELUK BAHANG";
            case 13:
                return "BATU MAUNG";
            case 14:
                return "SUNSHINE BAYAN BARU";
            case 15:
                return "KOMPLEKS BUKIT JAMBUL";
            case 16:
                return "ORIENTAL GARDEN";
            case 17:
                return "JELUTONG";
            case 18:
                return "SUNGAI PINANG";
            case 19:
                return "QUEENSBAY";
            case 20:
                return "MUZIUM PERANG";
            default:
                throw new IllegalArgumentException("Invalid vertex number: " + vertex);
        }
    }
}
