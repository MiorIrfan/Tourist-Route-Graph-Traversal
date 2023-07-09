import java.util.*;

public class ModifiedFinderMultiplePath {
    private int numVertices;
    private List<Integer>[] adjacencyList;
    private List<Edge>[] edgeList;
    private List<List<Integer>> paths;
    private boolean pathFound;

    public ModifiedFinderMultiplePath(int numVertices) {
        this.numVertices = numVertices;
        adjacencyList = new ArrayList[numVertices];
        edgeList = new ArrayList[numVertices];
        for (int i = 0; i < numVertices; i++) {
            adjacencyList[i] = new ArrayList<>();
            edgeList[i] = new ArrayList<>();
        }
        paths = new ArrayList<>();
        pathFound = false;
    }

    public void addEdge(int source, int destination, int value) {
        adjacencyList[source].add(destination);
        edgeList[source].add(new Edge(source, destination, value));
    }

    public List<List<Integer>> findAllPathsDFS(int source, int destination) {
        boolean[] visited = new boolean[numVertices];
        int[] parent = new int[numVertices];
        Arrays.fill(parent, -1);
        dfs(source, destination, visited, parent, new ArrayList<>());

        return paths;
    }

    private void dfs(int current, int destination, boolean[] visited, int[] parent, List<Integer> currentPath) {
        visited[current] = true;
        currentPath.add(current);

        if (current == destination) {
            paths.add(new ArrayList<>(currentPath));
        } else {
            for (int neighbor : adjacencyList[current]) {
                if (!visited[neighbor]) {
                    parent[neighbor] = current;
                    dfs(neighbor, destination, visited, parent, currentPath);
                }
            }
        }

        visited[current] = false;
        currentPath.remove(currentPath.size() - 1);
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
        ModifiedFinderMultiplePath pathFinder = new ModifiedFinderMultiplePath(numCities);

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
        System.out.print("This application will suggest top 5 routes that you can take\n");

        String sourceCity, destinationCity;
        int source, destination;

        do {
            // Prompt the user to enter the source and destination cities
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter source city: ");
            sourceCity = scanner.nextLine();
            System.out.print("Enter destination city: ");
            destinationCity = scanner.nextLine();

            // convert to int node
            source = cityToVertex(sourceCity);
            destination = cityToVertex(destinationCity);

        }while(source == -1 || destination == -1);
        // Find all possible paths using DFS
        List<List<Integer>> allPaths = pathFinder.findAllPathsDFS(source, destination);

        int counter = 5;
        // Output the result
        if (allPaths.isEmpty()) {
            System.out.println("\nNo path from " + sourceCity + " to " + destinationCity + " found.");
        } else {
            System.out.println("\nPaths from " + sourceCity + " to " + destinationCity + ":");


            for (List<Integer> path : allPaths) {
//                if(counter > 0) {
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
                    System.out.println();
//                    System.out.println(counter);
//                    counter--;
//                }


            }

        }

    }

    // Helper method to convert city name to vertex numberff
    private static int cityToVertex(String city) {
        switch (city) {
            case "Balik Pulau":
                return 1;
            case "Muzium Cafe Western":
                return 2;
            case "Kek Lok Si Temple":
                return 3;
            case "Masjid Negeri":
                return 4;
            case "Komtar":
                return 5;
            case "Jetty":
                return 6;
            case "Airport Penang":
                return 7;
            case "Sungai Tiram":
                return 8;
            case "Gelugor Penang":
                return 9;
            case "General Hospital Penang":
                return 10;
            case "Batu Ferringhi":
                return 11;
            case "Teluk Bahang":
                return 12;
            case "Batu Maung":
                return 13;
            case "Sunshine Bayan Baru":
                return 14;
            case "Kompleks Bukit Jambul":
                return 15;
            case "Oriental Garden":
                return 16;
            case "Jelutong":
                return 17;
            case "Sungai Pinang":
                return 18;
            case "Queensbay":
                return 19;
            case "Muzium Perang":
                return 20;
            default:
                System.out.println("Invalid city name: " + city);
                return -1;
        }
    }

    // Helper method to convert vertex number to city name
    private static String vertexToCity(int vertex) {
        switch (vertex) {
            case 1:
                return "Balik Pulau";
            case 2:
                return "Muzium Cafe Western";
            case 3:
                return "Kek Lok Si Temple";
            case 4:
                return "Masjid Negeri";
            case 5:
                return "Komtar";
            case 6:
                return "Jetty";
            case 7:
                return "Airport Penang";
            case 8:
                return "Sungai Tiram";
            case 9:
                return "Gelugor Penang";
            case 10:
                return "General Hospital Penang";
            case 11:
                return "Batu Ferringhi";
            case 12:
                return "Teluk Bahang";
            case 13:
                return "Batu Maung";
            case 14:
                return "Sunshine Bayan Baru";
            case 15:
                return "Kompleks Bukit Jambul";
            case 16:
                return "Oriental Garden";
            case 17:
                return "Jelutong";
            case 18:
                return "Sungai Pinang";
            case 19:
                return "Queensbay";
            case 20:
                return "Muzium Perang";
            default:
                throw new IllegalArgumentException("Invalid vertex number: " + vertex);
        }
    }
}
