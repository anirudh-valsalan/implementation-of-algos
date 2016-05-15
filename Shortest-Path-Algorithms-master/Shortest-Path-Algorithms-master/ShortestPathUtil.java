/**
 * All the algorithm implementations
 * @author : Team G12
 * Anirudh K V
 * Malini K Bhaskaran
 * Neha Nirmala Srinivas
 * Saumya Ann George
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ShortestPathUtil {
	/***
	 * runLevel1 :Method to run level1.
	 * 
	 * @param Graph
	 *            g: Graph instance g.
	 */
	Vertex source = null;

	public void runLevel1(Graph g) {
		long distance = 0;
		boolean status = false;
		// check whether the edge weights are same.
		boolean isEdgeWeightSame = checkForSameEdgeWeights(g);
		if (isEdgeWeightSame) {
			System.out.print("BFS ");
			distance = runBFS(g);
		} else {
			// check whether the input is a DAG.
			List<Vertex> topOrderList = getTopologicalOrderList(g);
			if (topOrderList.size() > 0) {
				System.out.print("DAG ");
				Vertex source = g.verts.get(1);
				distance = findShortestDistance(topOrderList, source, g);
			} else {
				// checkIfAllEdgesArePositive
				boolean isPoistive = checkIfAllEdgesArePositive(g);
				// run dijkstra if edges are positive.
				if (isPoistive) {
					System.out.print("DIJ ");
					distance = dijkstra(g);

				} else {
					// run bellmanFord.
					System.out.print("B-F ");
					status = bellmanFordTake3(g);
					if (status) {
						// calculate distance
						distance = calcualteDistance(g);
					}

				}
			}
		}
		System.out.println(distance);
		// print the elements if the number of nodes is less than or equal to 100.
		if (g.numNodes <= 100) {
			printElements(g);
		}

	}

	/**
	 * Print Elements
	 * 
	 * @param Graph
	 *            g
	 */
	private void printElements(Graph g) {
		//print the element it's shortest distance and parent.
		for (Vertex vertex2 : g.verts) {
			if (vertex2 != null) {
				System.out.print(vertex2.name + " ");
				if (vertex2.isInfinity) {
					System.out.print("INF" + " ");
				} else {
					System.out.print(vertex2.distance + " ");

				}

				if (vertex2.parent != null) {
					System.out.print(vertex2.parent + " ");
				} else
					System.out.print("-" + " ");
			}
			System.out.print("\n");
		}

	}

	/**
	 * calcualteDistance
	 * 
	 * @param Graph
	 *            g
	 * @return
	 */
	private long calcualteDistance(Graph g) {
		//calculate the total distance.
		long dist = 0;
		for (int i = 1; i <= g.numNodes; i++) {
			Vertex vertex = g.verts.get(i);
			if (!vertex.isInfinity) {
				dist = dist + vertex.distance;
			}
		}
		return dist;
	}

	/***
	 * runLevel2 :Method to run level2.
	 * 
	 * @param Graph
	 *            g
	 */
	public void runLevel2(Graph g) {
		// run bellmanFordTake3 algorithm
		boolean status = bellmanFordTake3(g);
		if (status) {
			// if bellmanFordTake3 works fine find the shortest paths.
			long distance = countShortestPaths(g);
			if (distance != 0) {
				System.out.println(distance);

				if (g.numNodes <= 100) {
					printElementsLevel2(g);
				}
			}
		}
	}

	/**
	 * printElementsLevel2
	 * 
	 * @param Graph
	 *            g
	 */

	private void printElementsLevel2(Graph g) {

		for (Vertex vertex : g.verts) {
			if (vertex != null) {
				if (vertex.isInfinity) {
					System.out.println(vertex.name + " " + "INF " + " " + vertex.count);
				} else {
					System.out.println(vertex.name + " " + vertex.distance + " " + vertex.count);
				}
			}
		}

	}

	/**
	 * countShortestPaths
	 * 
	 * @param Graph
	 *            g
	 */
	public long countShortestPaths(Graph g) {
		long totWeight = 0;// total weight
		Graph shortestPathGraph = new Graph(g.numNodes);
		// Array to store the shortest weights.
		Integer shortestWeightArray[] = new Integer[g.numNodes + 1];
		// Array to store the distances.
		boolean distanceStatus[] = new boolean[g.numNodes + 1];
		// initialize the array with distance status as infinity.
		for (Vertex vertex2 : g.verts) {
			if (vertex2 != null) {
				shortestWeightArray[vertex2.name] = vertex2.distance;
				distanceStatus[vertex2.name] = vertex2.isInfinity;
			}
		}
		// Iterate through edges and check if there is a shortest path between
		// two vertex.
		for (Edge e : g.edgeList) {
			Vertex fromNode = e.From;
			Vertex toNode = e.To;
			if (fromNode.isConnectedToSource && toNode.isConnectedToSource) {
				if (e.Weight + fromNode.distance == toNode.distance) {

					shortestPathGraph.addDirectedEdge(fromNode.name, toNode.name, e.Weight);

				}
			}
		}

		List<Vertex> sourceConnectedVertexList = findSourceConnectedVertexList(shortestPathGraph);
		Stack<Vertex> stackList = isCyclic(shortestPathGraph);
		if (stackList.size() != sourceConnectedVertexList.size()) {
			// cycle present
		} else {
			// create a array for maintaining the count of occurrence.
			Integer countOccuracnce[] = new Integer[g.numNodes + 1];
			initializeCount(countOccuracnce);
			while (!stackList.isEmpty()) {
				{
					Vertex source = stackList.pop();
					if (source.isConnectedToSource) {
						List<Edge> edgList = source.Adj;
						for (Edge edge : edgList) {
							Vertex otherEnd = edge.otherEnd(source);
							countOccuracnce[otherEnd.name] = countOccuracnce[otherEnd.name]
									+ countOccuracnce[source.name];
						}
					}

				}
			}
			// calculate total weights.
			for (int i = 1; i < countOccuracnce.length; i++) {
				totWeight = totWeight + countOccuracnce[i];

			}
			// set the newly found weight to the vertex.
			for (Vertex vertex : g.verts) {
				if (vertex != null && vertex.isConnectedToSource) {
					Integer count = countOccuracnce[vertex.name];
					Integer distance = shortestWeightArray[vertex.name];
					boolean isInfinity = distanceStatus[vertex.name];
					vertex.count = count;
					vertex.isInfinity = isInfinity;
					vertex.distance = distance;
				}
			}

		}
		return totWeight;
	}

	/**
	 * initializeCount
	 * 
	 * @param Integer[]
	 *            countOccuracnce
	 */
	private static void initializeCount(Integer[] countOccuracnce) {
		// initialize vertex.
		for (int i = 1; i < countOccuracnce.length; i++) {

			countOccuracnce[i] = 0;

		}
		int source = 1;
		countOccuracnce[source] = 1;

	}

	/**
	 * bellmanFordTake3
	 * 
	 * @param Graph
	 *            g return boolean status
	 */
	public boolean bellmanFordTake3(Graph g) {
		findSourceConnectedVertexList(g);
		// initialize vertex
		Vertex[] vertex = new Vertex[g.numNodes + 1];
		int count = 1;
		for (Vertex v : g.verts) {
			if (v != null) {
				v.isInfinity = true;
				v.seen = false;
				vertex[count] = v;
				vertex[count].index = count;
				v.parent = null;
				v.count = 0;
				++count;
			}
		}
		// make 1 as source vertex.
		vertex[1].distance = 0;
		vertex[1].isInfinity = false;
		vertex[1].seen = true;
		Queue<Vertex> vertexQueue = new ArrayDeque<>();
		vertexQueue.add(vertex[1]);
		// if vertex queue is not empty.
		while (!vertexQueue.isEmpty()) {
			Vertex u = vertexQueue.remove();
			u.seen = false;
			u.count = u.count + 1;
			if (u.count >= g.numNodes) {
				System.out.println("Non-positive cycle in graph. DAC is not applicable");
				findNegCycle(u,g);
				return false;

			}

			for (Edge edge : u.Adj) {
				Vertex v = edge.otherEnd(u);
				if (v.isConnectedToSource) {
					// check if v is distance is greater than or equal to
					// u.distance + edge.Weight
					if (!u.isInfinity) {
						if (((v.isInfinity) || (v.distance > (u.distance + edge.Weight)))) {
							if (v.isInfinity) {
								v.isInfinity = false;
							}

							v.distance = edge.Weight + u.distance;
							v.parent = u;
							if (!v.seen) {
								vertexQueue.add(v);
								v.seen = true;
							}
						}
					}

				} else {
					// source is at infinity.
					v.distance = 0;
					v.isInfinity = true;
					v.parent = u;
				}
			}
		}
		return true;
	}

	/**
	 * checkForSameEdgeWeights
	 * 
	 * @param Graph
	 *            g
	 * @return boolean status
	 */
	private boolean checkForSameEdgeWeights(Graph g) {
		boolean status = true;//default status
		List<Edge> edgeList = g.edgeList;
		long weight = edgeList.get(0).Weight;
       //for all the edges check whether the edge weights are same.
		for (Edge e : edgeList) {

			if (e.Weight == weight) {

				continue;
			} else {
				status = false;//change the status
				break;
			}
		}
		return status;

	}

	/**
	 * runBFS
	 * 
	 * @param Graph
	 *            g
	 * @return long distance
	 */
	private long runBFS(Graph g) {
      //Add vertex to the queue.
		Vertex source = g.verts.get(1);
		Queue<Vertex> vertexQueue = new ArrayDeque<>();
		for (Vertex vertex : g.verts) {
			if (vertex != null) {
				vertex.isInfinity = true;
			}
		}
		source.seen = true;
		source.isInfinity = false;
		vertexQueue.add(source);

		while (!vertexQueue.isEmpty()) {

			Vertex vertex = vertexQueue.remove();
            //scan the adjacency list and set weight.
			List<Edge> adjList = vertex.Adj;
			for (Edge edge : adjList) {
				Vertex otherEndVertex = edge.otherEnd(vertex);
				if (!otherEndVertex.seen) {
					otherEndVertex.seen = true;
					otherEndVertex.distance = vertex.distance + 1;
					otherEndVertex.parent = vertex;
					otherEndVertex.isInfinity = false;
					vertexQueue.add(otherEndVertex);
				}

			}

		}
		// calculate the distance.
		Integer dist = 0;
		for (Vertex vertex2 : g.verts) {
			if (vertex2 != null && !vertex2.isInfinity) {

				dist = dist + vertex2.distance;
			}
		}
		return dist;

	}

	/**
	 * getTopologicalOrderList
	 * 
	 * @param Graph
	 *            g
	 * @return List<Vertex> vertexList
	 */
	public List<Vertex> getTopologicalOrderList(Graph g) {
		List<Vertex> topOrderVertexList = new ArrayList<>();
        
		Queue<Vertex> topologicalQueue = new ArrayDeque<>();
		//find the vertex with in degree count zero.
		for (Vertex vertex : g.verts) {
			if (null != vertex) {
				vertex.inDegreeCount = vertex.revAdj.size();
				if (vertex.inDegreeCount == 0) {
					topologicalQueue.add(vertex);
					topOrderVertexList.add(vertex);
				}

			}

		}
		if (!topologicalQueue.isEmpty()) {
			while (!topologicalQueue.isEmpty()) {
				Vertex vertex = topologicalQueue.remove();

				List<Edge> adjList = vertex.Adj;
				for (Edge edge : adjList) {
					Vertex otherEndVertex = edge.otherEnd(vertex);
					//if we find a vertex with indegree count as zero just add it into the queue.
					otherEndVertex.inDegreeCount = otherEndVertex.inDegreeCount - 1;
					if (otherEndVertex.inDegreeCount == 0) {
						topologicalQueue.add(otherEndVertex);
						topOrderVertexList.add(otherEndVertex);
					}

				}

			}
			if (topOrderVertexList.size() != g.numNodes) {
				topOrderVertexList = new ArrayList<>();
			}

		}

		return topOrderVertexList;
	}

	/**
	 * findShortestDistance
	 * 
	 * @param topOrderList
	 * @param Vertex
	 *            source
	 * @param Graph
	 *            g
	 * @return long distance
	 */
	private long findShortestDistance(List<Vertex> topOrderList, Vertex source, Graph g) {
		long wmst = 0;
		for (Vertex vertex : g.verts) {
			if (vertex != null) {
				vertex.isInfinity = true;
			}
		}

		source.distance = 0;
		source.isInfinity = false;
       //find those vertex whose v.distance > (u.distance + e.Weight
		for (Vertex u : topOrderList) {
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!u.isInfinity) {
					if (((v.isInfinity) || (v.distance > (u.distance + e.Weight)))) {
						if (v.isInfinity) {
							v.isInfinity = false;
						}

						v.distance = e.Weight + u.distance;
						v.parent = u;

					}
				}
			}
		}

		wmst = 0;
		//add those vertex whose position is not at infinity.
		for (int i = 1; i < g.verts.size(); i++) {

			if (!g.verts.get(i).isInfinity) {

				wmst = wmst + g.verts.get(i).distance;

			}

		}
		return wmst;
	}

	/***
	 * dijkstra
	 * 
	 * @param Graph
	 *            g
	 * @return long distance
	 */
	public long dijkstra(Graph g) {
		long wmst = 0;
		Vertex[] vertex = new Vertex[g.numNodes + 1];
		int count = 1;
		// initialize the vertices.
		for (Vertex v : g.verts) {
			if (v != null) {
				v.isInfinity = true;
				v.seen = false;
				vertex[count] = v;
				vertex[count].index = count;
				++count;
			}
		}
		vertex[1].distance = 0;
		vertex[1].isInfinity = false;
		// create a indexed heap of vertices.
		IndexedHeap<Vertex> indexedVertexHeap = new IndexedHeap<Vertex>(vertex, new Vertex(0));
		while (!indexedVertexHeap.isSizeEmpty()) {
			Vertex u = indexedVertexHeap.remove();

			u.seen = true;
			for (Edge e : u.Adj) {
				Vertex v = e.otherEnd(u);
				if (!u.isInfinity) {
					// find those vertex whose v.distance > (u.distance +
					// e.Weight
					if (!v.seen && ((v.isInfinity) || (v.distance > (u.distance + e.Weight)))) {
						if (v.isInfinity) {
							v.isInfinity = false;
						}
						v.distance = e.Weight + u.distance;
						v.parent = u;
						indexedVertexHeap.decreaseKey(v);

					}
				}
			}
		}
		// add those vertex whose position is not at infinity.
		for (Vertex vertex2 : g.verts) {
			if (vertex2 != null) {
				if (!vertex2.isInfinity) {

					wmst = wmst + vertex2.distance;
				}

			}

		}
		return wmst;
	}

	/**
	 * checkIfAllEdgesArePositive
	 * 
	 * @param Graph
	 *            g
	 * @return boolean status
	 */
	private boolean checkIfAllEdgesArePositive(Graph g) {
		boolean status = true;//default status
		List<Edge> edgeList = g.edgeList;

		for (Edge edge : edgeList) {

			if (edge.Weight > 0) {
				continue;
			} else {
				status = false;//status changed to false.
				break;
			}
		}
		return status;
	}

	/**
	 * findDFS
	 * 
	 * @param Graph
	 *            g
	 * @return List<Vertex> vertexList
	 */
	public List<Vertex> findSourceConnectedVertexList(Graph g) {
		List<Vertex> sourceConnectedVertexList = new ArrayList<>();
		//initialize the vertexes as not seen .
		for (Vertex vertex : g.verts) {
			if (vertex != null) {
				vertex.seen = false;
				vertex.isConnectedToSource=false;
			}

		}
		Stack<Vertex> stack = new Stack<>();
		Vertex source = g.verts.get(1);
		stack.push(source);
		source.isConnectedToSource = true;
		source.seen = true;
		sourceConnectedVertexList.add(source);
		while (!stack.isEmpty()) {
			Vertex vertex = stack.pop();
			for (Edge edge : vertex.Adj) {
				Vertex otherEndVertex = edge.otherEnd(vertex);
				if (!otherEndVertex.seen) {

					otherEndVertex.seen = true;
					otherEndVertex.isConnectedToSource = true;
					stack.push(otherEndVertex);
					sourceConnectedVertexList.add(otherEndVertex);
				}
			}
		}
		return sourceConnectedVertexList;
	}
/**
 * isCyclic
 * @param  Graph g
 * @return
 *     Stack<Vertex> stack of vertices.
 */
	public Stack<Vertex> isCyclic(Graph g) {
		List<Edge> edgeList = new ArrayList<>();
		Stack<Vertex> verStack = new Stack<>();
		for (Vertex vertex : g) {
			vertex.color = Color.white;
		}

		Vertex source = g.verts.get(1);
		if (source.color == Color.white) {
			boolean isCyclic = checkCycle(g.verts.get(1), source.color, verStack, edgeList);
			//if there is a cycle present then print the cycle.
			if (isCyclic) {
				System.out.println("Non-positive cycle in graph. DAC is not applicable");
				if (!edgeList.isEmpty()) {
					Vertex origin = edgeList.get(0).To;
					for (Edge edge : edgeList) {
						if (edge.From.equals(origin)) {
							System.out.println(edge.From +" "+edge.To + " " + edge.Weight);
							break;
						} else {
							System.out.println(edge.From +" "+edge.To + " " + edge.Weight);
						}
					}
				}

			}
		}

		return verStack;

	}
	/**
	 * checkCycle
	 * 
	 * @param u:initial vertex
	 * @param color
	 * @param stack
	 * @param edgeList
	 * @return
	 *     boolean status whether cycle present or not.
	 */

	private boolean checkCycle(Vertex u, Color color, Stack<Vertex> stack, List<Edge> edgeList) {
		//make the  vertex color as gray means it is in processing stage.
		u.color = Color.gray;
        for (Edge e : u.Adj) {
			Vertex v = e.otherEnd(u);
			if (v.color == Color.gray) {
				//back edge detection.
				source = v;
				edgeList.add(e);
				return true;

			}
			if (v.color == Color.white && checkCycle(v, color, stack, edgeList)) {
               //add edges to edge list.
				edgeList.add(e);
				if (e.otherEnd(v) == source) {

					break;

				}
				return true;
			}

		}
        //make the color as processed
		u.color = Color.black;
		stack.push(u);
		if (edgeList.isEmpty()) {
			return false;
		} else
			return true;
	}
	
	/*
	 * Function to get the negative Cycle printed
	 * @param u -> Negative Cycle vertex from Bellman-Ford
	 * @param g -> Graph
	 * @param startVert -> 
	 */
	private void findNegCycle(Vertex u, Graph g) {
			
	    u.isNegCycle = true;
	    Vertex cycleStart=null;
	    Vertex startVert = null;
	    Stack<Vertex> cycleStack = new Stack<Vertex>();
	    //Identify start of negative cycle
		while(!u.parent.isNegCycle)
		{					
			u.parent.isNegCycle = true;
			u= u.parent;
			cycleStart = u.parent;
			startVert = cycleStart;
		}	
		//Push Cycle nodes to stack
		cycleStack.push(cycleStart);		
		while(cycleStart.parent!=startVert)
		{			
			cycleStart = cycleStart.parent;
			cycleStack.push(cycleStart);			
		}
		//Display edges from Negative Cycle vertices
		if(!cycleStack.isEmpty()){
			Vertex source=cycleStack.pop();
			Vertex Begin = source;
		while(!cycleStack.isEmpty()){
			Vertex dest =cycleStack.pop();
			for(Edge ed:source.Adj){
				if(ed.otherEnd(source).equals(dest)){
					System.out.println(ed.From+" "+ed.To+" "+ed.Weight);
					break;
				}
			}
			source=dest;
			
		}
		//Prints the final edge from begin and end vertices of cycle
		for(Edge ed:source.Adj){
			if(ed.otherEnd(source).equals(Begin)){
				System.out.println(ed.From+" "+ed.To+" "+ed.Weight);
				break;
			}
		}
		}
	}

}
