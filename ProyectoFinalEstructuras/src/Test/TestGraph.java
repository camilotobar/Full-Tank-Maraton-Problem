package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import Collections.Graph;
import Collections.Node;

public class TestGraph {

	Graph<Character, Long> graph;
	
	public void setupEscenario1(){
		graph = new Graph<>(6);
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
	}

	@Test
	public void testAgregarNodo(){
		graph = new Graph<>(5);
		Random rand = new Random();
		char value = 'A';
		for (int i = 0; i < 5; i++) {
			graph.addNode(i, new Character((char) (value + i)));
		}
		int key = rand.nextInt(5);
		assertTrue(graph.containsKey(key));
		assertEquals(5, graph.size());
	}
	
	@Test
	public void testAgregarEdge(){
		graph = new Graph<>(6);
		char value = 'M';
		for (int i = 0; i < 6; i++) {
			graph.addNode(i, new Character((char) (value + i)));
		}
		graph.addEdge(0, 1, 9l);
		assertNotEquals(0, graph.adjacencyList.size());
	}
	
	@Test
	public void testBFS(){
		ArrayList<Character> expected = new ArrayList<>();
		expected.add('P');
		expected.add('M');
		expected.add('N');
		expected.add('O');
		expected.add('Q');
		expected.add('R');
		setupEscenario1();
		ArrayList<Node<Character>> bfs = graph.BFS(3);
		for (int i = 0; i < bfs.size(); i++) {
			assertEquals(expected.get(i), bfs.get(i).getValue());
		}
	}
	
	@Test
	public void testDFS(){
		ArrayList<Character> expected = new ArrayList<>();
		expected.add('P');
		expected.add('M');
		expected.add('N');
		expected.add('O');
		expected.add('R');
		expected.add('Q');
		setupEscenario1();
		ArrayList<Node<Character>> dfs = graph.DFS(3);
		for (int i = 0; i < dfs.size(); i++) {
			assertEquals(expected.get(i), dfs.get(i).getValue());
		}
	}
	
	@Test
	public void tesDijskstra(){
		long[] expected = {0, 7, 8, 5, 5, 2};
		long[] dijkstra = new long[6];
		setupEscenario1();
		dijkstra = graph.dijkstra(0);
		assertArrayEquals(expected, dijkstra);
	}
	
	@Test
	public void tesFloydWarshall(){
		setupEscenario1();
		long[][] fw = new long[6][6];
		fw = graph.floydWarshall();
		for (int i = 0; i < fw.length; i++) {
			assertArrayEquals(graph.dijkstra(i), fw[i]);
		}
	}
	
}
