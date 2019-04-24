
/****************************************************************************************
 * Tiffany Wise										  
 * CMSC 451						
 * Project 1
 * 
 * This class creates an Exception that will be thrown if he heap sort is not sorting the 
 * arrays in the correct order. 
 * 			  
 *****************************************************************************************
 */

public class UnsortedException extends Exception {

	private int difference;
	
	public UnsortedException(int difference){
		this.difference = difference;
	}
	
	public int getDifference(){
		return difference;
	}
	
}
