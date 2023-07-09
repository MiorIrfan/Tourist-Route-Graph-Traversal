import java.util.*;

public class PathFinderDFS {
    private int numNodes;
    private List<Integer>[] adjacencyList;
    private List<Edge>[] edgeList;
    private List<Integer> path;
    private boolean pathFound;

    public PathFinderDFS(int numNodes) {
        this.numNodes = numNodes;
        adjacencyList = new ArrayList[numNodes];
        edgeList = new ArrayList[numNodes];
        for (int i = 0; i < numNodes; i++) {
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
        boolean[] visited = new boolean[numNodes];
        int[] parent = new int[numNodes];
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
        int origin;
        int destination;
        int value;

        public Edge(int origin, int destination, int value) {
            this.origin = origin;
            this.destination = destination;
            this.value = value;
        }
    }

    public int getEdgeValue(int origin, int destination) {
        for (Edge edge : edgeList[origin]) {
            if (edge.destination == destination) {
                return edge.value;
            }
        }
        return 0;  // If no edge found, return 0 or a suitable default value
    }

    public static void main(String[] args) {
        // Create a transportation system graph
<<<<<<< HEAD
        int numNodes = 7;
        PathFinderDFS trackSearch = new PathFinderDFS(numNodes);
        trackSearch.addEdge(0, 1, 5);  // Vertex 0 (origin) to Vertex 1 with value 5
        trackSearch.addEdge(0, 2, 7);  // Vertex 0 (origin) to Vertex 2 with value 7
        trackSearch.addEdge(1, 3, 10); // Vertex 1 to Vertex 3 with value 10
        trackSearch.addEdge(2, 3, 3);  // Vertex 2 to Vertex 3 with value 3
        trackSearch.addEdge(3, 4, 8);  // Vertex 3 to Vertex 4 with value 8
        trackSearch.addEdge(3, 0, 2);  // Vertex 3 to Vertex 5 with value 2
        trackSearch.addEdge(4, 5, 6);  // Vertex 4 to Vertex 6 with value 6
        trackSearch.addEdge(5, 1, 4);  // Vertex 5 to Vertex 6 with value 4
=======
        int numVertices = 7;
        PathFinderDFS pathFinder = new PathFinderDFS(numVertices);
        pathFinder.addEdge(0, 1, 5);  // Vertex 0 (source) to Vertex 1 with value 5
        pathFinder.addEdge(0, 2, 7);  // Vertex 0 (source) to Vertex 2 with value 7
        pathFinder.addEdge(1, 3, 10); // Vertex 1 to Vertex 3 with value 10
        pathFinder.addEdge(2, 3, 3);  // Vertex 2 to Vertex 3 with value 3
        pathFinder.addEdge(3, 0, 8);  // Vertex 3 to Vertex 4 with value 8
        pathFinder.addEdge(3, 4, 2);  // Vertex 3 to Vertex 5 with value 2
        pathFinder.addEdge(4, 5, 6);  // Vertex 4 to Vertex 6 with value 6
        pathFinder.addEdge(5, 1, 4);  // Vertex 5 to Vertex 6 with value 4
>>>>>>> origin/main

        // Prompt the user to enter the origin and destination vertices
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter origin vertex: ");
        int origin = scanner.nextInt();
        System.out.print("Enter destination vertex: ");
        int destination = scanner.nextInt();

        // Find the path using DFS
        List<Integer> path = trackSearch.findPathDFS(origin, destination);

        // Output the result
        if (path != null) {
            System.out.println("Path from " + origin + " to " + destination + ":");
            for (int i = 0; i < path.size(); i++) {
                int vertex = path.get(i);
                System.out.print(vertex);
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
                int edgeWeight = trackSearch.getEdgeValue(sourceVertex, destinationVertex);
                totalWeight += edgeWeight;
            }

            System.out.println("Total length of the path: " + totalWeight);


        } else {
            System.out.println("No path from " + origin + " to " + destination + " found.");
        }
    }
}
