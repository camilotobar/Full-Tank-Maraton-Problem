package Mundo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import Collections.Node;
import Mundo.Grafo.Road;


public class Generador {

	public BufferedReader in;
	public BufferedWriter out;
	public Grafo map;
	public Graph g;
	
	public int[][] queries;
	public int queriesNumber;
	public long [] minWays;
	
	public Generador() {
	}
	
	
	public void Cargar(File archivo) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(archivo));
		StringTokenizer stk = new StringTokenizer(in.readLine());
		int cities = Integer.parseInt(stk.nextToken());
		int roads = Integer.parseInt(stk.nextToken());
		map = new Grafo(cities, roads);

		stk = new StringTokenizer(in.readLine());

		int i = 0;
		while (stk.hasMoreTokens()) {
			map.addCity(i, Integer.parseInt(stk.nextToken()));
			i++;
		}

		while (roads > 0) {
			stk = new StringTokenizer(in.readLine());
			int from = Integer.parseInt(stk.nextToken());
			int to = Integer.parseInt(stk.nextToken());
			int distance = Integer.parseInt(stk.nextToken());

			map.addRoad(from, to, distance);
			roads--;
		}

		queriesNumber = Integer.parseInt(in.readLine());
		queries = new int[queriesNumber][3];
		minWays = new long[queriesNumber];
		int maxCapacity = Integer.MIN_VALUE;

		for (int j = 0; j < queriesNumber; j++) {
			stk = new StringTokenizer(in.readLine());
			int carCapacity = Integer.parseInt(stk.nextToken());
			maxCapacity = Math.max(maxCapacity, carCapacity);

			queries[j][0] = carCapacity;
			queries[j][1] = Integer.parseInt(stk.nextToken());
			queries[j][2] = Integer.parseInt(stk.nextToken());
		}

		map.toStateSpaceGraph(maxCapacity);

		in.close();
		resolver();
	}
	
	
	public void generarCasos(File f) throws IOException{
		PrintWriter out = new PrintWriter(new FileWriter(f));
		Random rand = new Random();
		int n = 1000000;
		int m = 10000000;
		out.write(n + " " + m + "\n");
		String s = "";
		for (int i = 0; i < n; i++) {
			if (i < n-1)
				out.write((rand.nextInt(100)+1)+" ");
			else
				out.write((rand.nextInt(100)+1)+"\n");
		}
		for (int i = 0; i < m; i++) {
			out.write(rand.nextInt(n)+" ");
			out.write(rand.nextInt(n)+" ");
			out.write((rand.nextInt(100)+1)+"\n");
		}
		int q = rand.nextInt(100);
		out.write(q+"\n");
		for (int i = 0; i < q; i++) {
			out.write((rand.nextInt(100)+1)+" ");
			out.write(rand.nextInt(n)+" ");
			out.write(rand.nextInt(n)+"\n");
		}
		out.close();
	}
	
	public void resolver(){
		for (int j = 0; j < queriesNumber; j++) {
			long minWay = map.cheapestTrip(queries[j][0], queries[j][1], queries[j][2]);
			minWays[j] = minWay;
		}
	}


	/**
	 * @return the in
	 */
	public BufferedReader getIn() {
		return in;
	}


	/**
	 * @param in the in to set
	 */
	public void setIn(BufferedReader in) {
		this.in = in;
	}


	/**
	 * @return the out
	 */
	public BufferedWriter getOut() {
		return out;
	}


	/**
	 * @param out the out to set
	 */
	public void setOut(BufferedWriter out) {
		this.out = out;
	}


	/**
	 * @return the map
	 */
	public Grafo getMap() {
		return map;
	}


	/**
	 * @param map the map to set
	 */
	public void setMap(Grafo map) {
		this.map = map;
	}


	/**
	 * @return the queries
	 */
	public int[][] getQueries() {
		return queries;
	}


	/**
	 * @param queries the queries to set
	 */
	public void setQueries(int[][] queries) {
		this.queries = queries;
	}


	/**
	 * @return the queriesNumber
	 */
	public int getQueriesNumber() {
		return queriesNumber;
	}


	/**
	 * @param queriesNumber the queriesNumber to set
	 */
	public void setQueriesNumber(int queriesNumber) {
		this.queriesNumber = queriesNumber;
	}


	/**
	 * @return the minWays
	 */
	public long[] getMinWays() {
		return minWays;
	}


	/**
	 * @param minWays the minWays to set
	 */
	public void setMinWays(long[] minWays) {
		this.minWays = minWays;
	}
	
	public void pintarGrafo(){
		g = new SingleGraph("Map");
		for (int i = 0; i < map.getCities(); i++) {
			g.addNode(map.getNodes()[i].number+"").addAttribute("label", map.getNodes()[i].number+", "+map.getNodes()[i].fuelPrice);
		}
		Iterator<List<Road>> i = map.getRoadsList().values().iterator();
		int c = 0;
		while (i.hasNext()){
			List<Road> roads = i.next();
			
			for (int j = 0; j < roads.size(); j++) {
				String from = roads.get(j).from+"";
				String to = roads.get(j).to+"";
				if (!(g.getEdge(from+to)!=null||g.getEdge(to+from)!=null)){
					g.addEdge(from+to, roads.get(j).from+"", roads.get(j).to+"").addAttribute("label", roads.get(j).distance);									
				}
			}
			c++;
		}
		g.display();
	}
	
}
