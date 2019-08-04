import java.io.*;
import java.util.*;

public class FullTankAdjacencyList {

	static BufferedReader in;
	static BufferedWriter out;
	static Graph map;
	static int[][] queries;

	public static void main(String[] args) throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new BufferedWriter(new OutputStreamWriter(System.out));

		StringTokenizer stk = new StringTokenizer(in.readLine());
		int cities = Integer.parseInt(stk.nextToken());
		int roads = Integer.parseInt(stk.nextToken());
		map = new Graph(cities, roads);

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

		int queriesNumber = Integer.parseInt(in.readLine());
		queries = new int[queriesNumber][3];
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

		for (int j = 0; j < queriesNumber; j++) {
			long minWay = map.cheapestTrip(queries[j][0], queries[j][1], queries[j][2]);
			out.write((minWay != Graph.INFINITE) ? minWay + "\n" : "impossible\n");
		}
		out.close();
		in.close();
	}

}

class Graph {

	static final int INFINITE = Integer.MAX_VALUE;

	HashMap<City, List<Road>> roadsList;
	HashMap<State, HashMap<State, StateEdge>> priceList;
	City[] nodes;
	State[] states;
	int maxCapacity;
	int cities;
	int roads;

	// Dikstra variables
	long[] distances;
	boolean[] visited;

	Graph(int cities, int roads) {
		this.cities = cities;
		this.roads = roads;
		nodes = new City[cities];
		roadsList = new HashMap<>(cities);
	}

	public void toStateSpaceGraph(int maxCapacity) {
		this.maxCapacity = maxCapacity;
		visited = new boolean[(maxCapacity + 1) * cities];
		distances = new long[(maxCapacity + 1) * cities];
		states = new State[(maxCapacity + 1) * cities];
		priceList = new HashMap<>();

		int statesIndex = 0;
		State currentState, nextState;
		for (City city : nodes) {
			currentState = new State(city, 0);

			for (int i = 0; i < maxCapacity; i++) {
				nextState = new State(city, i + 1);

				priceList.put(currentState, new HashMap<>());
				priceList.get(currentState).put(nextState, new StateEdge(currentState, nextState));
				states[statesIndex] = currentState;
				statesIndex++;
				currentState = nextState;
			}

			priceList.put(currentState, new HashMap<>());
			states[statesIndex] = currentState;
			statesIndex++;
		}

		for (City city : nodes) {
			Collection<Road> roads = roadsList.get(city);

			for (Road road : roads) {
				int indexFrom = (city.number * (maxCapacity + 1)) + road.distance;
				int indexTo = road.to * (maxCapacity + 1);
				int end = indexFrom + (maxCapacity + 1) - road.distance;

				for (int i = indexFrom, j = indexTo; i < end; i++, j++)
					priceList.get(states[i]).put(states[j], new StateEdge(states[i], states[j]));
			}
		}
	}

	void addCity(int number, int fuelPrice) {
		City newNode = new City(number, fuelPrice);
		nodes[number] = newNode;
		roadsList.put(newNode, new ArrayList<>());
	}

	void addRoad(int from, int to, int distance) {
		roadsList.get(nodes[from]).add(new Road(from, to, distance));
		roadsList.get(nodes[to]).add(new Road(to, from, distance));
	}

	long cheapestTrip(int carCapacity, int from, int to) {
		Arrays.fill(distances, INFINITE);
		Arrays.fill(visited, false);
		return dijkstrinha_OP(carCapacity, from, to);
	}

	long dijkstrinha_OP(int carCapacity, int from, int to) {

		PriorityQueue<DijkstraVertex> queue = new PriorityQueue<>();
		queue.add(new DijkstraVertex(states[from * (maxCapacity + 1)], 0));
		distances[from * (maxCapacity + 1)] = 0;
		int endIndex = to * (maxCapacity + 1);

		while (!visited[endIndex] && !queue.isEmpty()) {
			State current = queue.poll().node;

			if (!visited[getStateIndex(current)]) {
				visited[getStateIndex(current)] = true;
				Collection<StateEdge> adjacencies = priceList.get(current).values();

				for (StateEdge aux : adjacencies)
					if (!visited[getStateIndex(aux.to)]) {
						int edgeWeight = aux.weight;

						if (carCapacity >= aux.to.fuelState && (distances[getStateIndex(current)]
								+ edgeWeight < distances[getStateIndex(aux.to)])) {
							distances[getStateIndex(aux.to)] = distances[getStateIndex(current)] + edgeWeight;
							queue.add(new DijkstraVertex(aux.to, distances[getStateIndex(aux.to)]));
						}
					}
			}
		}

		return distances[endIndex];
	}

	int getStateIndex(State state) {
		return (state.city.number * (maxCapacity + 1)) + state.fuelState;
	}

	class DijkstraVertex implements Comparable<DijkstraVertex> {

		State node;
		long distance;

		DijkstraVertex(State node, long distance) {
			this.node = node;
			this.distance = distance;
		}

		@Override
		public int compareTo(DijkstraVertex anotherVertex) {
			return (int) (distance - anotherVertex.distance);
		}
	}

	class Road {

		int from;
		int to;
		int distance;

		Road(int from, int to, int distance) {
			this.from = from;
			this.to = to;
			this.distance = distance;
		}
	}

	class City {

		int number;
		int fuelPrice;

		City(int number, int fuelPrice) {
			this.number = number;
			this.fuelPrice = fuelPrice;
		}
	}

	class State {

		City city;
		int fuelState;

		State(City city, int fuelState) {
			this.city = city;
			this.fuelState = fuelState;
		}
	}

	class StateEdge {

		State from;
		State to;
		int weight;

		StateEdge(State from, State to) {
			this.from = from;
			this.to = to;
			weight = (from.city.equals(to.city)) ? from.city.fuelPrice : 0;
		}
	}
}
