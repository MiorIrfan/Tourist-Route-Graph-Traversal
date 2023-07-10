import java.util.*;

public class PathFinderDFS {
    private int numVertices;
    private List<Integer>[] adjacencyList;
    private List<Edge>[] edgeList;
    private List<Integer> path;
    private boolean pathFound;

    public PathFinderDFS(int numVertices) {
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
        // Create a transportation system graph
        int numVertices = 7;
        PathFinderDFS pathFinder = new PathFinderDFS(numVertices);
        pathFinder.addEdge(0, 1, 5);  // Vertex 0 (source) to Vertex 1 with value 5
        pathFinder.addEdge(0, 2, 7);  // Vertex 0 (source) to Vertex 2 with value 7
        pathFinder.addEdge(1, 4, 10); // Vertex 1 to Vertex 3 with value 10
        pathFinder.addEdge(2, 3, 3);  // Vertex 2 to Vertex 3 with value 3
        pathFinder.addEdge(3, 4, 8);  // Vertex 3 to Vertex 4 with value 8
        pathFinder.addEdge(3, 0, 2);  // Vertex 3 to Vertex 5 with value 2
        pathFinder.addEdge(4, 5, 6);  // Vertex 4 to Vertex 6 with value 6
        pathFinder.addEdge(5, 1, 4);  // Vertex 5 to Vertex 6 with value 4

        // Prompt the user to enter the source and destination vertices
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter source vertex: ");
        int source = scanner.nextInt();
        System.out.print("Enter destination vertex: ");
        int destination = scanner.nextInt();

        // Find the path using DFS
        List<Integer> path = pathFinder.findPathDFS(source, destination);

        // Output the result
        if (path != null) {
            System.out.println("Path from " + source + " to " + destination + ":");
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
                int edgeWeight = pathFinder.getEdgeValue(sourceVertex, destinationVertex);
                totalWeight += edgeWeight;
            }

            System.out.println("Total length of the path: " + totalWeight);


        } else {
            System.out.println("No path from " + source + " to " + destination + " found.");
        }
    }
}
