
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * This class will implement all methods for finding EulerPath and EulerCircut.
 */
/**
 * @author  Team G12.
 * Course : CS6301.002- Implementation of Advanced algorithm and data structures
 * 
 */
public class EulerGraph {
	private static int phase = 0;
	private static long startTime, endTime, elapsedTime;

	/**
	 * Method to check whether the graph is an Eulerian or not.
	 * @param a
	 *        :Graph g -input graph.
	 * @return
	 *        :int oddDegreeCount - odd degree vertex count in the graph.
	 */
	public static int isEulerCircuit(Graph g) {

		int oddDegreeCount = 0;//count the number of odd degree vertices in the graph.

		List<Vertex> verList = g.verts;
		for (Vertex vertex : verList) {
			if (null != vertex) {

				int size = vertex.Adj.size();//size of the adjaceny vertex list.
				if (size % 2 != 0) {
					oddDegreeCount++;//increment the odd degree count.
				}

			}
		}

		return oddDegreeCount;

	}

	/**
	 * Method to check whether the graph have an Euler path or not.
	 * @param a
	 *       :Graph g :Graph input.
	 * @return
	 *       :List<Vertex> vertexList.
	 */
	public static List<Vertex> isEulerPath(Graph g) {

		List<Vertex> oddDegreeVertexList = new LinkedList<>();
		List<Vertex> verList = g.verts;
		for (Vertex vertex : verList) {
			if (null != vertex) {

				int size = vertex.Adj.size();
				// check whether the graph have odd number of vertices.
				if (size % 2 != 0) {

					// if vertex have odd count then add it to the odd vertex
					// list.

					oddDegreeVertexList.add(vertex);

				}

			}
			if (oddDegreeVertexList.size() >=3) {
				break;
			}

		}
		// if odd degree count is 2 then the path will be between vertices of
		// odd degree.

		return oddDegreeVertexList;

	}

	
	/**
	 * This method will check whether the graph is connected or not.
	 * @param a
	 *       :Vertex vertex- vertex input
	 * @param b:
	 *       :int numbNodes - number of nodes in the graph
	 * @return
	 *       :boolean status - return true if graph is connected.
	 */
	public static boolean isConnected(Vertex vertex, int numNodes) {
		boolean isConnected = false;
		Deque<Vertex> deQueueStack = new ArrayDeque<>();
		depthFirstSearch(vertex, deQueueStack);
		// if the size of stack and number of nodes are same then the graph is
		// connected.
		if (!deQueueStack.isEmpty()) {
			if (deQueueStack.size() == numNodes) {
				isConnected = true;

			}
		}
		return isConnected;
	}

	/**
	 * This method will do the depth first search.
	 * @param a
	 *        :Vertex v -vertex input.
	 * @param b
	 *        :Dequeue<Vertex> stackDeque -stack dequeue.      
	 * 
	 */
	public static void depthFirstSearch(Vertex v, Deque<Vertex> stackDeque) {

		v.seen = true;
		List<Edge> adjList = v.Adj;
		// do depth first search.
		for (Edge e : adjList) {
			Vertex endVertex = e.otherEnd(v);
			if (!endVertex.seen) {

				depthFirstSearch(endVertex, stackDeque);

			}

		}
		// push the vertex after DFS to stack.
		stackDeque.push(v);

	}

	/**
	 * Method to determine whether the graph is Eulerian or not.
	 * 
	 * @param a
	 *        :Graph g -Graph input.
	 * @return
	 *        :Node<Edge> eulerTourOutput -Euler tour output.
	 */
	public static Node<Edge>  findEulerTour(Graph g) {
		Node<Edge> eulerTourOutput = null;//will contain the Euler tour output.
		boolean isConnected = isConnected(g.verts.get(1), g.numNodes);
		if (isConnected) {
			int oddDegreeCount = isEulerCircuit(g);
			// if the odd degree count is zero then the graph is Eulerian.
			Node<Edge> headNode = new Node<>(null);
		
			if (oddDegreeCount == 0) {
				g.setEulerCiruit(true);
				System.out.println("Graph is Eulerian");
				eulerTourOutput = getTourOutput(g.verts.get(1), headNode);
			} else {
				List<Vertex> oddVertexList = EulerGraph.isEulerPath(g);
				// if the oddVertexList size is 2 then then Eulerian Path will
				// be between vertices of odd degree.
				if (oddVertexList.size() == 2) {
					System.out.println("Graph has an Eulerian Path between vertices " + oddVertexList.get(0) + " and "
							+ " " + oddVertexList.get(1) + ".");

					if (oddVertexList.get(0).Adj.size() > oddVertexList.get(1).Adj.size()) {
						eulerTourOutput = getTourOutput(oddVertexList.get(1), headNode);
					} else {
						eulerTourOutput = getTourOutput(oddVertexList.get(0), headNode);
					}

				} else {
					System.out
							.println("Graph is not Eulerian.  It has " + oddDegreeCount + " vertices of odd degree. ");
				}

			}

		} else {
			System.out.println("Graph is not connected.");
		}
       return eulerTourOutput;
	}


	/**
	 * Method to get the Euler tour output.
	 * 
	 * @param a
	 *            :Vertex v - the start vertex to start the tour.
	 * @param b
	 *            :Node<Edge> doublyLinkedList - the empty doublyLinkedList.
	 * @return
	 *            :Node<Edge>  doublyLinkedList -doubly linked list which contains all edges in the graph.       
	 * 
	 */
	public static Node<Edge> getTourOutput(Vertex v, Node<Edge> doublyLinkedList) {
		//unvisited vertex list  will contain all vertices which still 
		//have unvisited edges after the first Euler tour.
		List<Vertex> unvisitedVertexList = new LinkedList<>();
		Node<Edge> temp = doublyLinkedList;  //Current edge from doublyLinkedList
		Edge e = getUnvisitedEdge(v);
		Node<Edge> node = new Node<Edge>(temp, e, null);//create new edge.
		temp.nextNode = node;
		temp = temp.nextNode;
		//start of the first Euler tour.This tour will complete a cycle.
		while (null != e) {
			Vertex u = e.otherEnd(v);
			Edge edge = getUnvisitedEdge(u);//get the unvisited edge of the vertex u.
			Node<Edge> nodeVertex = new Node<Edge>(temp, edge);//create new Edge
			temp.nextNode = nodeVertex;//assign nodeVertex as the nextNode for current node
			e = edge;
			v = u;
			temp = temp.nextNode;//increment the temp pointer.
			//check if the vertices have unvisited edge.
			if (u.degree > 0) {
				unvisitedVertexList.add(u);//add vertices to unvisitedVertexList.
				u.node = temp;

			}

		}
		Node<Edge> cycleNode = doublyLinkedList.nextNode;//LinkedList cycle generated after the first tour.
		//method to find subTour based on vertex entry in the unvisitedVertexList.
		findSubTour(unvisitedVertexList);
		return cycleNode;
	}

	/**
	 * Method to find the sub tour in the graph.
	 * 
	 * @param a
	 * 
	 *        :List<Vertex>unvisitedVertexList - unvisited vertex list.
	 */

	private static void findSubTour(List<Vertex> unvisitedVertexList) {
		
		while (unvisitedVertexList.size() > 0) {
			Vertex unvisitedVertex = unvisitedVertexList.get(0);
			//if vertex still have unvisited edges.
			if (unvisitedVertex.degree > 0) {
				Node<Edge> currentNode = unvisitedVertex.node;//currentNode
				Node<Edge> previousNode = currentNode.previousNode;//previous node.
				Node<Edge> tempPrevPointer = previousNode;//previous node pointer.
				Node<Edge> lastNode = currentNode;//the currentNode will be the last node after one sub tour.

				Edge edge = getUnvisitedEdge(unvisitedVertex);
				if (null != edge) {
					Node<Edge> linkeNode = new Node<Edge>(tempPrevPointer, edge);//link previous edge and current Edge
                    
					tempPrevPointer.nextNode = linkeNode;//point previous node next to linkNode.
					tempPrevPointer = tempPrevPointer.nextNode;//increment previous pointer.
				}
				//Iterate through the unvisited edges of vertex and add those edges to the linked list cycle.
				while (null != edge) {

					Vertex u = edge.otherEnd(unvisitedVertex);
					Edge edgeNext = getUnvisitedEdge(u);
					if (null != edgeNext) {
						Node<Edge> nodeVertex = new Node<Edge>(tempPrevPointer, edgeNext);
						tempPrevPointer.nextNode = nodeVertex;
						tempPrevPointer = tempPrevPointer.nextNode;
						//if vertex still have unvisited edges add those to the list.
						if (u.degree > 0) {
							unvisitedVertexList.add(u);
							u.node = tempPrevPointer;

						}
					}
					edge = edgeNext;
					unvisitedVertex = u;

				}
                //link the current node next to last node.
				tempPrevPointer.nextNode = lastNode;
				unvisitedVertexList.remove(0);//remove the vertex from the list.
			} else {
				unvisitedVertexList.remove(0);//remove the vertex from the list
			}
		}

	}
	/**
	 * Method to return the unvisited edge for the given vertex u.
	 * @param a
	 *       :Vertex u -the vertex input to find unvisited edge.
	 * @return
	 *       :Edge - the unvisited edge.
	 * 
	 */
	public static Edge getUnvisitedEdge(Vertex u) {
		
		int position = u.getAdjPosition();// get the position of vertex u in the adjacency list.
        //check to determine if all edges in the adjacency list of vertex is visited or not.
		if (position < u.Adj.size()) {
			// retrieve the first edge which is still unvisited in the adjacency list.
			Edge e = u.Adj.get(position);
			int nextPosition = position + 1;
			while (e.isVisited() && nextPosition < u.Adj.size()) {

				e = u.Adj.get(nextPosition);
				nextPosition = nextPosition + 1;
			}
            //if entire edge in the adjacency list is visited return null;
			if (e.isVisited()) {
				return null;
			} else

			{
				
				u.setAdjPosition(nextPosition);//update the current position in the adjacency list.
				e.setVisited(true);
				u.degree--;//decrement the adjacency list count of first vertex.
				Vertex v = e.otherEnd(u);
				v.degree--;//decrement the adjacency list count of second vertex.
				return e;
			}

		}
		return null;

	}

	/**
	 * Method to print the edges in the doubly linked list.
	 * 
	 * @param :Node<Edge>
	 *            doublyLinkedList - the linked list which contain all edges.
	 */

	public static <T> void printNodes(Node<Edge> doublyLinkedList) throws IOException {
		Node<Edge> temp = doublyLinkedList;
        //Iterate through the loop till all edges in the list is visited.
		while (temp != null && null != temp.data) {
			Edge edge = temp.data;
			System.out.println(edge.toString());
			temp = temp.nextNode;

		}
		System.out.println();
	}

	/**
	 * Method to verify the Euler Tour.
	 * @param a
	 *        :Graph g - the graph input.
	 * @param b
	 *        :Node<Edge> doublyLinkedList - doubly linked list input.
	 * @param c
	 *        :Vertex start- the start vertex.
	 * @return
	 *         boolean status- the status of verify tour.
	 *       
	 */
	public static boolean verifyTour(Graph g, Node<Edge> doublyLinkedList, Vertex start) {
		boolean status = true;//default status.
		
		if (null != doublyLinkedList) {

			Node<Edge> head = doublyLinkedList;
			//retrieve the first and second edges from the doubly linked list.
			Edge firstEdge = head.data;
			Edge secondEdge = head.nextNode.data;
            Vertex firstVertex = null;
			Vertex nextCommon = null;//vertex which will become common to adjacent edges.
			if (firstEdge.From.equals(secondEdge.From) || firstEdge.To.equals(secondEdge.From)) {
				nextCommon = secondEdge.To;
				firstVertex=firstEdge.otherEnd(secondEdge.From);
				

			} else if (firstEdge.From.equals(secondEdge.To) || firstEdge.To.equals(secondEdge.To)) {
				nextCommon = secondEdge.From;
				firstVertex=firstEdge.otherEnd(secondEdge.To);

			} else {
				//if there is no common vertex between first and second edges.
				status = false;
				return status;
			}
			head = head.nextNode;
			firstEdge = head.data;
			secondEdge = head.nextNode.data;//get the next edge.
			//Iterate thorough the doubly linked list to check connectivity between edges.
			//if there is no common node present between two adjacent edges then tour is wrong.
			while (null != firstEdge && null != secondEdge) {
                
				if (nextCommon.equals(secondEdge.From)) {

					nextCommon = secondEdge.To;

				} else if (nextCommon.equals(secondEdge.To)) {
					nextCommon = secondEdge.From;
				} else {
					status = false;
					return status;
				}
				head = head.nextNode;
				firstEdge = head.data;
				secondEdge = head.nextNode.data;
			}
			if (g.isEulerCiruit && null != firstEdge) {

				if (!firstEdge.From.equals(firstVertex) && !firstEdge.To.equals(firstVertex)) {
					status = false;
					return status;
				}
			}
			Integer size = size(doublyLinkedList);
			if (size != g.numEdges) {
				status = false;
				return status;
			}
		}

		return status;

	}

	/**
	 * Method to return the number of edges in the doubly linked list.
	 * 
	 * @param :Node<Edge>
	 *            doublyLinkedList - the linked list which contain all edges.
	 * 
	 */
	public static Integer size(Node<Edge> doublyLinkedList) {
		
		Integer count = 0;//count to keep track of number of edges.
		Node<Edge> temp = doublyLinkedList;//point to the head of doubly linked list.
		//Iterate through the loop till all edges in the list is visited.
		while (temp != null && temp.data != null) {
			count = count + 1;
			temp = temp.nextNode;

		}
		return count;
	}
	
	/**
	 * Timer to calculate the running time
	 */
	public static void timer() {
		if (phase == 0) {
			startTime = System.currentTimeMillis();
			phase = 1;
		} else {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			System.out.println("Time: " + elapsedTime + " msec.");

			phase = 0;
		}
	}
	
	/**
	 * Test main.
	 * 
	 */
	public static void main(String[] args) throws IOException {

		Scanner in = null;
    	File file = null;
		try {
			if (args.length > 0) {
				file = new File(args[0]);

				in = new Scanner(file);
			}

			else {
				System.out.println("Enter File Path");
				Scanner inFile = new Scanner(System.in);
				file = new File(inFile.next());
				inFile.close();
				in = new Scanner(file);
			}			
			Graph g = Graph.readGraph(in, false);
			timer();// start the timer.
			// start the eulerTour on the graph.
			Node<Edge> eulerTourOutput = findEulerTour(g);
			if (null != eulerTourOutput) {
				// verify tour.
				boolean status = verifyTour(g, eulerTourOutput, g.verts.get(1));
				// if status is true then print the nodes.
				if (status == true) {
					timer();
					System.out.println("Edges are");
					printNodes(eulerTourOutput);
				} else {
					System.out.println("Error in finding Euler path.");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
//doubly linked list class.
class Node<T> {

	Node<T> nextNode; //next node to doubly linked list.
	Node<T> previousNode;//previous node to doubly linked list.
	T data;//data 

    /**Constructor for Node class with previous node and data
    *
    *@param previousNode :
    *                     previousNode of the node
    *@param data         :
    *                     data stored in the node
    *
    */
	public Node(Node<T> previousNode, T data) {
		this.previousNode = previousNode;
		this.data = data;
	}

    /**Constructor for Node class with previous node, data and next node
    *
    *@param previousNode : 
    *                      previousNode of the current node
    *@param data         : 
    *                      data stored in the current node
    *@param nextNode     : 
    *                      nextNode of the current node    
    */
	public Node(Node<T> previousNode, T data, Node<T> nextNode) {

		this.nextNode = nextNode;
		this.data = data;
		this.previousNode = previousNode;
	}
    /**Constructor for Node class with parameter data
    *
    * @param data: 
    *               data stored in the current node
    * 
    */
	public Node(T data) {
		this.data = data;
		this.previousNode=null;
		this.nextNode=null;
	
	}
    
    
}



/**
 * Sample Input:
 * 
 *  5 8 
    1 2 1
    1 3 1
    1 4 1
    2 3 1
    3 4 1
    3 5 1
    4 5 1
    2 4 1
    
 *Output: 
 * Graph has an Eulerian Path between vertices 1 and  2.
    Time: 0 msec.
    (1,2)
    (1,3)
    (3,4)
    (4,5)
    (3,5)
    (2,3)
    (2,4)
    (1,4)
 * 
*/
