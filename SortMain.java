
/****************************************************************************************
 * Tiffany Wise										  
 * CMSC 451						
 * Project 1
 * 
 * This class contains the array of array lengths to be used in the BenchmarkSorks class 
 * to create the data sets for testing. 
 * 			  
 *****************************************************************************************
 */

public class SortMain {

	public static void main(String[] args) {

		
		int[] arraySizing = {1,10,100,500,1000,3000,6000,9000,12000,15000};

		BenchmarkSorts bs = new BenchmarkSorts(arraySizing);
		bs.runSorts();

		bs.displayReport();
	}

}
