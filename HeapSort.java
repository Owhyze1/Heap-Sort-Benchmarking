
/****************************************************************************************
 * Tiffany Wise										  
 * CMSC 451						
 * Project 1
 * 
 * This class performs recursive and iterative heap sort algorithms on an unsorted array
 * and measures the efficiency of both algorithms based on counts of critical operations
 * and actual run time of the algorithm.					  
 * 
 * The code for the recursive heap sort of the HeapSort class are from GeeksforGeeks
 * website. The sort, heapify, and printArray methods are located at the following
 * website. The methods have been modified slightly to account for performance measures.
 * 
 * http://www.geeksforgeeks.org/heap-sort/			  
 *				
 *
 * The code for the iterative heap sort algorithm is from the Math and Computer Science
 * department of the University of Richmond. The iterativeHeap method is from their 
 * website below. It has also been modified for performance measuring.
 * 
 * http://www.mathcs.richmond.edu/~barnett/cs221/source/HeapSort.java
 * 
 * 			  
 *****************************************************************************************
 */

// Java program for implementation of Heap Sort
public class HeapSort implements SortInterface
{ 
	
	// ******************************************* //
	// Modified code: critical counting operation  //
	// ******************************************* //
	
    // For a recursive sort, count is the number of calls to the heapify method. For
	// an iterative sort, count is the number of comparisons 
    private int count;    
    private long time;
    
    
    /**
     * Uses a recursive heap sort algorithm to sort an array of
     * integers into ascending order
     */
    public void recursiveSort(int[] list){        	
    	sort(list);    	
    }
    
    /**
     * Uses a iterative heap sort algorithm to sort an array of 
     * integers into ascending order
     */
    public void iterativeSort(int[] list){
    	iterativeHeap(list);
    }
    
    /**
     * Returns the total number of calls to the heapify method 
     */
    public int getCount(){    	
    	return count;
    }
    
    /**
     * Returns run total run time for either recursive or
     * iterative algorithm
     */
    public long getTime(){    	
    	return time;
    }
        
    // ****************** //
	// End Modified Code  //
	// ****************** //
    
    
    
    
    /**
     * Recusrive heap sort whose critical operation is calls
     * to the Heapify method
     * @param arr
     */
    public void sort(int arr[])
    {
    	count = 0;
    	time = 0;
    	
    	long startTotal, endTotal;    	
    	startTotal = System.nanoTime();
    	
        int n = arr.length;
 
        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
        {
        	try 						{ heapify(arr, n, i); }
        	catch (UnsortedException e) { e.printStackTrace();}
        }
 
        // One by one extract an element from heap
        for (int i=n-1; i>0; i--)
        {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            
            // call max heapify on the reduced heap
            try 						{ heapify(arr, i, 0); }
        	catch (UnsortedException e) { e.printStackTrace();}
          
        }
        
        // measure end time and calculate total time for algorithm
        endTotal = System.nanoTime();    	
    	time = endTotal - startTotal;
    }
 
    
    /**
     * To heapify a subtree rooted with node i which is
     * an index in arr[]. n is size of heap
     * @param arr		array to be sorted
     * @param n			size of the heap
     * @param i			position (or node) in the heap
     * @throws UnsortedException
     */
    void heapify(int arr[], int n, int i) throws UnsortedException
    {
    	
    	// ********************************************* //
    	// Modified code: Count calls to heapify method  //
    	// and measure actual run time					 //
    	// ********************************************* //
    	count++;
    	long start= System.nanoTime();
    	
    	
    	
        int largest = i;  // Initialize largest as root
        int l = 2*i + 1;  // left = 2*i + 1
        int r = 2*i + 2;  // right = 2*i + 2
 
        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;
 
        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;
 
        // If largest is not root
        if (largest != i) 
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            int difference = arr[i] - arr[largest];
            
            if ( difference < 0 ){
            	throw new UnsortedException(difference);
            }
 
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
        
        // ********************************** //
    	// Modified code: Calculate end time  //
    	// ********************************** //
        long end =  System.nanoTime();
        time += end - start;
    }
 
    /* A utility function to print array of size n */
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
     
	// ***************************************** //
    // End of code from Geeks for Geeks website  //
	// ***************************************** //

    
    
    
    
   
    // ************************************************************************** //
    // The iterative heap sort code is from the following website. It has been
    // modified to allow for counting of performance parameters.
    //
    // Source: 
    // http://www.mathcs.richmond.edu/~barnett/cs221/source/HeapSort.java
    // *************************************************************************** //
    
    
    /**
     * The critical count operation for the iterative heap sort method is the number of 
     * comparisons performed using if statements. The count is incremented on the line 
     * before the if statement because even when an if statement proves false, the 
     * comparison still must be performed
     */
    public void iterativeHeap(int[] array){

    	count = 0;
    	time = System.nanoTime();
    	
    	/* This method performs an in-place heapsort. Starting
    	 * from the beginning of the array, the array is swapped
    	 * into a binary max heap.  Then elements are removed
    	 * from the heap, and added to the front of the sorted
    	 * section of the array. */

    	/* Insertion onto heap */
    	for (int heapsize = 0; heapsize < array.length; heapsize++) {

    		/* Step one in adding an element to the heap in the
    		 * place that element at the end of the heap array-
    		 * in this case, the element is already there. */

    		int n = heapsize; // the index of the inserted int

    		
    		
    		while (n > 0) { // until we reach the root of the heap

    			// the index of the parent of n
    			int p = (n-1)/2; 

    			// if the child is larger than parent, swap.
    			// Otherwise, break
    			if (array[n] > array[p]) { 
    				
    				arraySwap(array, n, p); 
    				// check parent
    				n = p; 
    			}
    			else 
    				break; 
    		}
    	}

    	/* Removal from heap */
    	for (int heapsize = array.length; heapsize> 0;) {

    		// swap root with the last heap element
    		arraySwap(array, 0, --heapsize); 

    		// index of the element being moved down the tree
    		int n = 0; 

    		while (true) {

    			int left = (n*2)+1;

    			// if the node has no left child, we have reached the
    			// bottom. The heap is heapified
    			if (left >= heapsize)    				
    				break; 
    				
    			
    			int right = left+1;
				
    			// if the node has a left child, but no right child, 
    			// and the left child is greater than the node, swap
    			// left child and node
    			if (right >= heapsize) { 
    				if (array[left] > array[n]) 
    					arraySwap(array, left, n); 
    				
    				break; // heap is heapified
    			}

				// if left child is greater than the node and the right
				// child, swap the left child with the node
    			if (array[left] > array[n]) { // (left > n)
    				
    				if (array[left] > array[right]) { 
    					arraySwap(array, left, n);
    					n = left; 
    					continue; // continue on left child
    				} else { 
    					// Otherwise right > left > n
    					arraySwap(array, right, n);
    					n = right; 
    					continue; // continue on right child
    				}
    				
    			// if right > n > left, swap right child with node
    			} else 
    				if (array[right] > array[n]) {
    					arraySwap(array, right, n);
    					n = right; 
    					continue; // continue on right child
    			// if node is greater than both children, the heap is heapified
    				} else { 
    					break; 
    				}
    			}
    		} // end while loop
    		time = System.nanoTime() - time;
    	}
    

    /**
     * Swaps the value at index n with the value at index p of an array
     * @param array		array whose values will be swapped
     * @param n			index position of first value
     * @param p			index position of second value
     */
    public void arraySwap(int[] array, int n, int p){
    	count++;
    	int temp = array[n];
    	array[n] = array[p];
    	array[p] = temp;
    }    
}