
/****************************************************************************************
 * Tiffany Wise										  
 * CMSC 451						
 * Project 1
 * 
 * This class provides the basic methods required in the HeapSort class. It serves as a 
 * blueprint for how to perform the sort algorithm and calculate performance measures
 * 			  
 *****************************************************************************************
 */

public interface SortInterface {

	public void recursiveSort(int[] list);
	
	public void iterativeSort(int[] list);
	
	public int getCount();
	
	public long getTime();
}
