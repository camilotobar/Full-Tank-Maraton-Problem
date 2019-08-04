package Collections;

import java.util.ArrayList;
import java.util.Iterator;

public class Console {

	public static void main(String[] args) {
		Graph<Character, Long> graph = new Graph<>(6);
		char value = 'M';
		for (int i = 0; i < 6; i++) {
			graph.addNode(i, new Character((char) (value + i)));
		}
		graph.addEdge(0, 1, 9l);
		graph.addEdge(0, 3, 6l);
		graph.addEdge(0, 4, 5l);
		graph.addEdge(0, 5, 2l);
		graph.addEdge(1, 2, 1l);
		graph.addEdge(1, 3, 2l);
		graph.addEdge(2, 5, 7l);
		graph.addEdge(2, 3, 4l);
		graph.addEdge(2, 3, 4l);
		graph.addEdge(3, 4, 12l);
		graph.addEdge(3, 5, 3l);
		
		
		System.out.println("BFS:");
		ArrayList<Node<Character>> bfs = graph.BFS(3);
		for (int i = 0; i < bfs.size(); i++) {
			System.out.print(bfs.get(i).getValue() + " ");
		}
		System.out.println();
		System.out.println("DFS:");
		ArrayList<Node<Character>> dfs = graph.DFS(3);
		for (int i = 0; i < dfs.size(); i++) {
			System.out.print(dfs.get(i).getValue() + " ");
		}
		System.out.println();
		long[][] floyd = new long[6][6];
		floyd = graph.floydWarshall();
		System.out.println("Floyd Warshall:");
		for (int i = 0; i < floyd.length; i++) {
			for (int j = 0; j < floyd.length; j++) {
				System.out.print(floyd[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("Dijkstra:");
		long[] dijkstra = new long[6];
		dijkstra = graph.dijkstra(0);
		for (int i = 0; i < dijkstra.length; i++) {
			System.out.print(dijkstra[i]+" ");
		}
		System.out.println();
	}
}
