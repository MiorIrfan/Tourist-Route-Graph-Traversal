//Assignment 2 CPT212 2022/2023
//Group members: 1. MIOR MUHAMMAD IRFAN BIN MIOR LATFEE
//               2. MOHAMAD NAZMI BIN HASHIM

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class ModifiedFinder {
    private int numNodes; //the number input edges input for the the nodes
    private List<Integer>[] adjacentList; //store the nodes and their neighbour
    private List<Edge>[] nodeEdgeList; //store the nodes, the vertex and weight value of the nodes
    private boolean foundedTrack;

    //to create and array list of the nodes
    public ModifiedFinder(int numNodes) {
        this.numNodes = numNodes;
        adjacentList = new ArrayList[numNodes];
        nodeEdgeList = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
            adjacentList[i] = new ArrayList<>();
            nodeEdgeList[i] = new ArrayList<>();
        }
        foundedTrack = false;
    }

    //Add data of each node, their vertex and edge value
    public void addEdge(int origin, int destination, int value) {
        adjacentList[origin].add(destination);
        nodeEdgeList[origin].add(new Edge(origin, destination, value));
    }

    //To initialise the depth first search algorithm
    public List<Integer> findPathDFS(int originNode, int destinationNode) {
        boolean[] visited = new boolean[numNodes];
        int[] parent = new int[numNodes];
        Arrays.fill(parent, -1);
        dfs(originNode, destinationNode, visited, parent);

        if (foundedTrack) {
            List<Integer> finalPath = new ArrayList<>();
            int current = destinationNode;
            while (current != -1) {
                finalPath.add(0, current);
                current = parent[current];
            }
            return finalPath;
        } else {
            return null;
        }
    }

    //to do the depth first search algorithm
    private void dfs(int current, int destination, boolean[] visited, int[] parent) {
        visited[current] = true;
        if (current == destination) {
            foundedTrack = true;
            return;
        }
        for (int i = 0; i < adjacentList[current].size(); i++) {
            int neighbor = adjacentList[current].get(i);
            if (!visited[neighbor]) {
                parent[neighbor] = current;
                dfs(neighbor, destination, visited, parent); //recursion for visiting other nodes in the path
                if (foundedTrack) {
                    return;
                }
            }
        }
        visited[current] = false;
    }

    //Class edge used to get value of edge for the nodes
    static class Edge {
        int origin;
        int destination;
        int value;

        public Edge(int origin, int destination, int value) {
            this.origin = origin;
            this.destination = destination;
            this.value = value;
        }
    }

    //return the value of edge for a node
    public int getEdgeValue(int origin, int destination) {
        for (Edge edge : nodeEdgeList[origin]) {
            if (edge.destination == destination) {
                return edge.value;
            }
        }
        return 0;  // If no edge found, return 0 or a suitable default value
    }

    //display the list of locations to be selected by the user
    public static int menu() {
        System.out.println("Please choose from the list of available location/n");
        System.out.println("BALIK PULAU     ---   MUZIUM CAFE WESTERN");
        System.out.println("MASJID NEGERI   ---   KEK LOK SI TEMPLE");
        System.out.println("KOMTAR          ---   AIRPORT PENANG");
        System.out.println("JETTY           ---   SUNGAI TIRAM");
        System.out.println("GELUGOR PENANG  ---   GENERAL HOSPITAL PENANG");
        System.out.println("BATU FERRINGHI  ---   TELUK BAHANG");
        System.out.println("BATU MAUNG      ---   SUNSHINE BAYAN BARU");
        System.out.println("SUNGAI PINANG   ---   ORIENTAL GARDEN");
        System.out.println("JELUTONG        ---   KOMPLEKS BUKIT JAMBUL");
        System.out.println("QUEENSBAY       ---   MUZIUM PERANG");
        return -1;
    }


    public static void main(String[] args) {
        // Create a road network graph
        int numNodes = 48; //initialize the number of array
        ModifiedFinder trackSearcher = new ModifiedFinder(numNodes); //create an instance of the ModifiedFInder class

        // Read data from a file
        try {
            File file = new File("edges.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("\t");
                int source = Integer.parseInt(parts[0]);
                int destination = Integer.parseInt(parts[1]);
                int value = Integer.parseInt(parts[2]);
                trackSearcher.addEdge(source, destination, value);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Display message to welcome user
        System.out.println("\n    ******Welcome to Tourist Navigation Plans Application******     ");
        System.out.println("----------------------------------------------------------------------");
        System.out.println(" You can see the available places to visit from your current location");
        System.out.println("    This application will suggest the top 1 route that you can take");
        System.out.println("----------------------------------------------------------------------");

        menu(); //display the menu

        String originNode, destinationNode;
        int origin, destination;
        char answer;
        Scanner scanner = new Scanner(System.in);

        do {
            do {
                // Prompt the user to enter the origin and destination cities
                System.out.print("\nEnter your current location: ");
                originNode = scanner.nextLine().trim().toUpperCase();
                System.out.print("Enter your destination: ");
                destinationNode = scanner.nextLine().trim().toUpperCase();

                // Convert to int node
                origin = locationToVertex(originNode);
                destination = locationToVertex(destinationNode);

            } while (origin == -1 || destination == -1);

            // Find the path using DFS
            List<Integer> track = trackSearcher.findPathDFS(origin, destination);

            // Output the result
            if (track != null) {
                System.out.println("\nPath from " + originNode + " to " + destinationNode + ":");
                for (int i = 0; i < track.size(); i++) {
                    int vertex = track.get(i);
                    String location = vertexToLocation(vertex);
                    System.out.print(location);
                    if (i != track.size() - 1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();

                // Calculate the total weight of the path
                int totalDistance = 0;
                for (int i = 0; i < track.size() - 1; i++) {
                    int originVertex = track.get(i);
                    int destinationVertex = track.get(i + 1);
                    int edgeDistance = trackSearcher.getEdgeValue(originVertex, destinationVertex);
                    totalDistance += edgeDistance;
                }
                System.out.println("Total distance of the path: " + totalDistance + " km\n");
            } else {
                System.out.println("No path from " + originNode + " to " + destinationNode + " found.");
            }

            System.out.print("Do you want to find another place you want to go? (y/n): ");
            answer = scanner.nextLine().toLowerCase().charAt(0);
        } while (answer == 'y');

        System.out.println("\nThank you for using this app! Have a nice day!");
    }

    // Helper method to convert location name to vertex number
    private static int locationToVertex(String location) {
        switch (location.toUpperCase()) {
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
                System.out.println("Invalid location name: " + location);
                return -1;
        }
    }

    // Helper method to convert vertex number to location name
    private static String vertexToLocation(int vertex) {
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
