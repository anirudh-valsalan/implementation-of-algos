

import java.util.Comparator;

public class BinaryHeap<T> implements PQ<T> {
    T[] pq;
    int curSize;
    Comparator<T> c;
    /** Build a priority queue with a given array q */
    BinaryHeap(T[] q, Comparator<T> comp) {
		pq = q;
		c = comp;
		curSize = pq.length-1;
		buildHeap();
    }

    /** Create an empty priority queue of given maximum size */
    BinaryHeap(int n, Comparator<T> comp) {
    	c=comp;
    	curSize=0;
    	pq = (T[]) new Integer[n];  
    }

    public void insert(T x) {
	add(x);
    }

    public T deleteMin() {
	return remove();
    }

    public T min() { 
	return peek();
    }

    public void add(T x) { 
    	int size = pq.length;
    	curSize++;
    	pq[curSize]=x;
    	percolateUp(curSize);
    	
    }

    public T remove() {
    	T min = pq[1];
    	pq[1]=pq[curSize--];
    	percolateDown(1);
    	return min;
    }

    public T peek() { 
	return null;
    }

    /** pq[i] may violate heap order with parent */
    void percolateUp(int i) { 
    	pq[0]=pq[i];
    	
    	while(c.compare(pq[i/2], pq[0]) > 0){
    		pq[i]=pq[i/2];
    		updateIndex(i);
    		i=i/2;
    	}
    	pq[i]=pq[0];
    	updateIndex(i);
    	
    }

    /** Empty function which will be overriden in IndexedHeap class*/
   public void updateIndex(int x){
    }
    
    /** pq[i] may violate heap order with children */
    void percolateDown(int i) { 
    	T x=pq[i];
    	int schild;
    	while(2*i <= curSize){
    		if(2*i == curSize){
    			if(c.compare(x, pq[2*i])>0){
    				pq[i]=pq[2*i];
    				updateIndex(i);
    				i=2*i;    				
    			}
    			else break;
    		}
    		else{
    			if(c.compare(pq[2*i], pq[2*i+1])<0){
    				schild = 2*i;
    			}
    			else{
    				schild = 2*i+1;
    			}
    			if(c.compare(x, pq[schild])>0){
    				pq[i]=pq[schild];
    				updateIndex(i);
    				
    				i=schild;
    			}
    			else break;
    		}
    		
    	}
    	pq[i]=x;
    	updateIndex(i);
    }

    public boolean isSizeEmpty(){
    	if(curSize==0)
    		return true;
		return false;
    }
    /** Create a heap.  Precondition: none. */
    void buildHeap() {
    	int n = curSize;
    	for(int i=(n)/2;i>0;i--)
    		percolateDown(i);  
    }

    /* sort array A[1..n].  A[0] is not used. 
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
     */
    public static<T> void heapSort(T[] A, Comparator<T> comp) { 
    	BinaryHeap<T> binaryHeap = new BinaryHeap<>(A, comp);
    	int size = binaryHeap.curSize;
    	for(int n=size;n>0;n--){
    		T min = binaryHeap.deleteMin();
    		A[n]=min;    		
    	}
    }
}
