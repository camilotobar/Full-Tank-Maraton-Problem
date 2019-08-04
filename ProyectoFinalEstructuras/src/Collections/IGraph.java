package Collections;

import java.util.ArrayList;

public interface IGraph<V, W> {

	public void addNode(Integer key, V value);

	public void addEdge(Integer elem, Integer connected, W weight);
	
	public ArrayList<Node<V>> BFS(Integer key);

	public ArrayList<Node<V>> DFS(Integer key);

	public long dijkstra(int from, int to);
	
	public long[] dijkstra(int from);
	
	public long[][] floydWarshall();
	
	public boolean containsKey(Integer key);
	
	public int size();
	
}
