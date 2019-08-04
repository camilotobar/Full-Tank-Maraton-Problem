package Mundo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


public class Grafo {

	static final int INFINITE = Integer.MAX_VALUE;

	private HashMap<City, List<Road>> roadsList;
	private HashMap<State, HashMap<State, StateEdge>> priceList;
	private City[] nodes;
	private State[] states;
	private int maxCapacity;
	private int cities;
	private int roads;

	/**
	 * @return the roadsList
	 */
	public HashMap<City, List<Road>> getRoadsList() {
		return roadsList;
	}

	/**
	 * @param roadsList the roadsList to set
	 */
	public void setRoadsList(HashMap<City, List<Road>> roadsList) {
		this.roadsList = roadsList;
	}

	/**
	 * @return the priceList
	 */
	public HashMap<State, HashMap<State, StateEdge>> getPriceList() {
		return priceList;
	}

	/**
	 * @param priceList the priceList to set
	 */
	public void setPriceList(HashMap<State, HashMap<State, StateEdge>> priceList) {
		this.priceList = priceList;
	}

	/**
	 * @return the nodes
	 */
	public City[] getNodes() {
		return nodes;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(City[] nodes) {
		this.nodes = nodes;
	}

	/**
	 * @return the states
	 */
	public State[] getStates() {
		return states;
	}

	/**
	 * @param states the states to set
	 */
	public void setStates(State[] states) {
		this.states = states;
	}

	/**
	 * @return the maxCapacity
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	/**
	 * @return the cities
	 */
	public int getCities() {
		return cities;
	}

	/**
	 * @param cities the cities to set
	 */
	public void setCities(int cities) {
		this.cities = cities;
	}

	/**
	 * @return the roads
	 */
	public int getRoads() {
		return roads;
	}

	/**
	 * @param roads the roads to set
	 */
	public void setRoads(int roads) {
		this.roads = roads;
	}

	/**
	 * @return the distances
	 */
	public long[] getDistances() {
		return distances;
	}

	/**
	 * @param distances the distances to set
	 */
	public void setDistances(long[] distances) {
		this.distances = distances;
	}

	/**
	 * @return the visited
	 */
	public boolean[] getVisited() {
		return visited;
	}

	/**
	 * @param visited the visited to set
	 */
	public void setVisited(boolean[] visited) {
		this.visited = visited;
	}

	// Dikstra variables
	private long[] distances;
	private boolean[] visited;

	public Grafo(int cities, int roads) {
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

	public void addCity(int number, int fuelPrice) {
		City newNode = new City(number, fuelPrice);
		nodes[number] = newNode;
		roadsList.put(newNode, new ArrayList<>());
	}

	public void addRoad(int from, int to, int distance) {
		roadsList.get(nodes[from]).add(new Road(from, to, distance));
		roadsList.get(nodes[to]).add(new Road(to, from, distance));
	}

	public long cheapestTrip(int carCapacity, int from, int to) {
		Arrays.fill(distances, INFINITE);
		Arrays.fill(visited, false);
		return dijkstrinha_OP(carCapacity, from, to);
	}

	public long dijkstrinha_OP(int carCapacity, int from, int to) {

		PriorityQueue<DijkstraVertex> queue = new PriorityQueue<>();
		queue.add(new DijkstraVertex(states[from * (maxCapacity + 1)], 0));
		distances[from * (maxCapacity + 1)] = 0;
		int endIndex = to * (maxCapacity + 1);
		StringBuilder[] way = new StringBuilder[states.length];
		

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
							way[getStateIndex(aux.from)] = new StringBuilder();
							way[getStateIndex(aux.from)].append(way[getStateIndex(current)] + " ");
							way[getStateIndex(aux.from)].append(aux.weight);
						}
					}
			}
		}
		return distances[endIndex];
	}

	
	public int getStateIndex(State state) {
		return (state.city.number * (maxCapacity + 1)) + state.fuelState;
	}

	public class DijkstraVertex implements Comparable<DijkstraVertex> {

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

	public class Road {

		int from;
		int to;
		int distance;

		Road(int from, int to, int distance) {
			this.from = from;
			this.to = to;
			this.distance = distance;
		}
	}

	public class City {

		int number;
		int fuelPrice;

		City(int number, int fuelPrice) {
			this.number = number;
			this.fuelPrice = fuelPrice;
		}
	}

	public class State {

		City city;
		int fuelState;

		State(City city, int fuelState) {
			this.city = city;
			this.fuelState = fuelState;
		}
	}
	
	public class StateEdge {

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
