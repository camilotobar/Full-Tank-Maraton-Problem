package Collections;

public class Edge<V, W> {

	/**
	 * The weight of the edge
	 */
	private W weight;

	private Node<V> from;

	private Node<V> to;

	public Edge(W weight, Node<V> from, Node<V> to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

	/**
	 * @return the from
	 */
	public Node<V> getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(Node<V> from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Node<V> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(Node<V> to) {
		this.to = to;
	}

	/**
	 * @return the weight
	 */
	public W getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            the weight to set
	 */
	public void setWeight(W weight) {
		this.weight = weight;
	}

}
