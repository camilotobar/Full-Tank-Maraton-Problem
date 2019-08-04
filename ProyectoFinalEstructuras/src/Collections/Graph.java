package Collections;

import java.util.*;

public class Graph<V, W> implements IGraph<V, W> {

	public static final int INFINITE = Integer.MAX_VALUE;
	public HashMap<Integer, Node<V>> nodes;
	public HashMap<Integer, HashMap<Integer, Edge<V, W>>> adjacencyList;
	public long[][] adjacencyMatrix;

	public Graph(int nNodes) {
		nodes = new HashMap<Integer, Node<V>>();
		adjacencyList = new HashMap<Integer, HashMap<Integer, Edge<V, W>>>();
		adjacencyMatrix = new long[nNodes][nNodes];

		for (int i = 0; i < adjacencyMatrix.length; i++)
			for (int j = 0; j < adjacencyMatrix.length; j++)
				adjacencyMatrix[i][j] = INFINITE;
	}

	@Override
	public void addNode(Integer key, V value) {
		nodes.put(key, new Node<V>(key, value));
		HashMap<Integer, Edge<V, W>> adjacents = new HashMap<Integer, Edge<V, W>>();
		adjacencyList.put(key, adjacents);
	}

	@Override
	public void addEdge(Integer current, Integer connected, W weight) {
		HashMap<Integer, Edge<V, W>> adjacents = adjacencyList.get(current);
		adjacents.put(connected, new Edge<V, W>(weight, nodes.get(current), nodes.get(connected)));
		adjacents = adjacencyList.get(connected);
		adjacents.put(current, new Edge<V, W>(weight, nodes.get(connected), nodes.get(current)));		
		if (weight != null) {
			adjacencyMatrix[current][connected] = Integer.parseInt(String.valueOf(weight));
			adjacencyMatrix[connected][current] = Integer.parseInt(String.valueOf(weight));
		} else {
			adjacencyMatrix[current][connected] = 0;
			adjacencyMatrix[connected][current] = 0;
		}
	}

	public ArrayList<Node<V>> DFS(Integer key) {
		boolean[] visited = new boolean[nodes.size()];
		ArrayList<Node<V>> dfs = new ArrayList();
		DFSRecursion(nodes.get(key), visited, dfs, 0);
		Iterator<Node<V>> nodes = this.nodes.values().iterator();
		while (nodes.hasNext()) {
			Node<V> n = nodes.next();
			if (!visited[(int) n.getKey()])
				DFSRecursion(n, visited, dfs, 0);
		}
		return dfs;
	}

	public void DFSRecursion(Node<V> node, boolean[] visited, ArrayList<Node<V>> dfs, int i) {
		visited[(int) node.getKey()] = true;
		dfs.add(node);
		i++;
		Iterator<Edge<V, W>> adjacents = adjacencyList.get(node.getKey()).values().iterator();
		while (adjacents.hasNext()) {
			Node<V> n = adjacents.next().getTo();
			if (!visited[(int) n.getKey()])
				DFSRecursion(n, visited, dfs, i);
		}
	}

	public ArrayList<Node<V>> BFS(Integer key) {
		boolean[] visited = new boolean[nodes.size()];
		ArrayList<Node<V>> bfs = new ArrayList<>();
		BFSUtil(nodes.get(key), visited, bfs);
		Iterator<Node<V>> nodes = this.nodes.values().iterator();
		while (nodes.hasNext()) {
			Node<V> n = nodes.next();
			if (!visited[(int) n.getKey()])
				BFSUtil(n, visited, bfs);
		}
		return bfs;
	}

	public void BFSUtil(Node<V> node, boolean[] visited, ArrayList<Node<V>> bfs) {
		Queue<Node<V>> queue = new LinkedList<>();
		visited[(int) node.getKey()] = true;
		queue.add(node);
		int i = 0;
		while (!queue.isEmpty()) {
			node = queue.poll();
			bfs.add(node);
			Iterator<Edge<V, W>> adjacents = adjacencyList.get(node.getKey()).values().iterator();
			while (adjacents.hasNext()) {
				Node<V> n = adjacents.next().getTo();
				if (!visited[(int) n.getKey()]) {
					visited[(int) n.getKey()] = true;
					queue.add(n);
				}
			}
			i++;
		}
	}

	public long dijkstra(int from, int to) {
		long[] distances = new long[nodes.size()];
		boolean[] visited = new boolean[nodes.size()];

		Arrays.fill(distances, INFINITE);
		Arrays.fill(visited, false);

		return dijkstrinha(distances, visited, from, to);
	}

	public long dijkstrinha(long[] distances, boolean[] visited, int from, int to) {

		PriorityQueue<DijkstraVertex> queue = new PriorityQueue<>();
		queue.add(new DijkstraVertex(nodes.get(from), 0));
		distances[from] = 0;
		StringBuilder[] way = new StringBuilder[nodes.size()];
		way[from] = new StringBuilder(from + "");

		while (!visited[to] && !queue.isEmpty()) {
			Node current = queue.poll().node;
			if (visited[current.getKey()])
				continue;
			visited[current.getKey()] = true;

			for (Node aux : nodes.values())
				if (!visited[aux.getKey()])
					if (distances[current.getKey()]
							+ adjacencyMatrix[current.getKey()][aux.getKey()] < distances[aux.getKey()]) {
						distances[aux.getKey()] = distances[current.getKey()]
								+ adjacencyMatrix[current.getKey()][aux.getKey()];
						queue.add(new DijkstraVertex(aux, distances[aux.getKey()]));
						way[aux.getKey()] = new StringBuilder();
						way[aux.getKey()].append(way[current.getKey()] + " ");
						way[aux.getKey()].append(aux.getValue());
					}
		}

		return distances[to];
	}

	// Hacia todos los nodos
	public long[] dijkstra(int from) {
		long[] distances = new long[nodes.size()];
		boolean[] visited = new boolean[nodes.size()];

		Arrays.fill(distances, INFINITE);
		Arrays.fill(visited, false);

		return dijkstrinha(distances, visited, from);
	}

	public long[] dijkstrinha(long[] distances, boolean[] visited, int from) {

		PriorityQueue<DijkstraVertex> queue = new PriorityQueue<>();
		queue.add(new DijkstraVertex(nodes.get(from), 0));
		distances[from] = 0;
		StringBuilder[] way = new StringBuilder[nodes.size()];

		while (!queue.isEmpty()) {
			Node current = queue.poll().node;
			if (visited[current.getKey()])
				continue;
			visited[current.getKey()] = true;

			for (Node aux : nodes.values())
				if (!visited[aux.getKey()])
					if (distances[current.getKey()]
							+ adjacencyMatrix[current.getKey()][aux.getKey()] < distances[aux.getKey()]) {
						distances[aux.getKey()] = distances[current.getKey()]
								+ adjacencyMatrix[current.getKey()][aux.getKey()];
						queue.add(new DijkstraVertex(aux, distances[aux.getKey()]));
						way[aux.getKey()] = new StringBuilder();
						way[aux.getKey()].append(way[current.getKey()] + " ");
						way[aux.getKey()].append(aux.getValue());
					}
		}

		return distances;
	}

	class DijkstraVertex implements Comparable<DijkstraVertex> {

		public Node node;
		public long distance;

		DijkstraVertex(Node node, long distance) {
			this.node = node;
			this.distance = distance;
		}

		public int compareTo(DijkstraVertex vertex) {
			return (int) (distance - vertex.distance);
		}
	}

	public long[][] floydWarshall() {
		long[][] distanceMat = adjacencyMatrix.clone();

		for (int k = 0; k < nodes.size(); ++k) {
			for (int i = 0; i < nodes.size(); ++i) {
				for (int j = 0; j < nodes.size(); ++j) {
					if (i == j) {
						distanceMat[i][j] = 0;
					} else if (distanceMat[i][k] != INFINITE && distanceMat[k][j] != INFINITE) {
						distanceMat[i][j] = Math.min(distanceMat[i][j], distanceMat[i][k] + distanceMat[k][j]);
						distanceMat[j][i] = distanceMat[i][j];
					}
				}
			}
		}
		return distanceMat;
	}

	@Override
	public boolean containsKey(Integer key) {
		return nodes.containsKey(key);
	}

	@Override
	public int size() {
		return nodes.size();
	}

}
