package Collections;

import java.util.ArrayList;

public class Node<V> {

	/**
	 * The key of the node
	 */
	private Integer key;

	/**
	 * The value of the node
	 */
	private V value;

	public Node(Integer key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * @return the key
	 */
	public Integer getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(Integer key) {
		this.key = key;
	}

}
