
/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.*;

public class Vertex {
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
	public List<Edge> untraversedEdges;//untraversed edges list; use LinkedList or ArrayList
	public Node<Edge> node;//node associated with the vertex.
	public Integer adjPosition;//position of the vertex in the adjaceny list.
	public int degree;//number of edges associated with the vertex.

	

	/**
	 * Constructor for the vertex
	 * 
	 * @param n
	 *            : int - name of the vertex
	 */
	Vertex(int n) {
		name = n;
		seen = false;
		parent = null;
		Adj = new ArrayList<Edge>();
		revAdj = new ArrayList<Edge>(); /* only for directed graphs */
		untraversedEdges = new ArrayList<>();
		adjPosition = 0;
	}

	/**
	 * Method to represent a vertex by its name
	 */
	public String toString() {
		return Integer.toString(name);
	}

	/**
	 * @return the adjPosition
	 */
	public Integer getAdjPosition() {
		return adjPosition;
	}

	/**
	 * @param adjPosition the adjPosition to set
	 */
	public void setAdjPosition(Integer adjPosition) {
		this.adjPosition = adjPosition;
	}
	
}