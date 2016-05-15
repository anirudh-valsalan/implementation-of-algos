/**
 * Class Vertex
 * @author : Team G12
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */

/**
 * Class to represent a vertex of a graph
 * 
 *
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vertex implements Index, Comparator<Vertex>{
	public int name; // name of the vertex
	public boolean seen; // flag to check if the vertex has already been visited
	public Vertex parent; // parent of the vertex
	public int distance; // distance to the vertex from the source vertex
	public List<Edge> Adj, revAdj; // adjacency list; use LinkedList or ArrayList
	public List<Edge> untraversedEdges;//untraversed edges list; use LinkedList or ArrayList
	public Integer adjPosition;//position of the vertex in the adjaceny list.
	public int inDegreeCount;//number of edges associated with the vertex.
	public boolean isInfinity;
	public int index;
	public boolean isConnectedToSource;
	public int shortestPathCount;
	public boolean isPresentInStack;
	public boolean isProcessed;
	public boolean isCycle;
	public boolean isNegCycle;
	public Integer order;
	Color color;
	
	public Integer count;
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
		isInfinity=false;
		color =Color.white;
	
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
	 public int compare(Vertex u, Vertex v){
	    	if(u.isInfinity){
	    		return 1;
	    	}
	    	if(v.isInfinity){
	    		return -1;
	    	}
	    	else{
	    		return u.distance-v.distance;
	    		
	    	}
	    	
	    }

	 @Override
		public void putIndex(int index) {
			this.index=index;
			
		}

		@Override
		public int getIndex() {
			return index;
		}
}