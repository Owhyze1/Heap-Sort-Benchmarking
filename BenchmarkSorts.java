

/****************************************************************************************
 * Tiffany Wise										  
 * CMSC 451						
 * Project 1
 * 
 * This class initiates the recursive and iterative heap sort algorithms from the HeapSort 
 * class and calculates the average and standard deviations for the critical operation 
 * counters and the run time. A GUI is created of the data measured from data sets made of
 * 50 arrays of varying array lengths. 
 * 			  
 *****************************************************************************************
 */



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;



public class BenchmarkSorts {

	// array containing the different array lengths of the data sets
	private int[] sizes;

	// average values for critical operation count and run time
	double averageCountR;
	double averageTimeR;
	double averageCountI;
	double averageTimeI;
	
	// standard deviation of crtical operation count and run time
	double DeviationCountRecursion;
	double DeviationTimeRecursion;
	double DeviationCountIterative;
	double DeviationTimeIterative;

	// object for holding all data for both algorithms
	Object[][] recursiveData;
	Object[][] iterativeData;

	
	
	/**
	 * Constructor: passes array of array length sizes for data sets
	 * @param sizes		array lengths 
	 */
	public BenchmarkSorts(int[] sizes){
		this.sizes = sizes;
	}
	
	


	/**
	 * Runs both the recursive and iterative versions of heap sort
	 */
	public void runSorts(){

		// highest randomly-generated number in the array
		int   max = 50000;

		// number of arrays for at each array length n
		int   dataSetSize = 50;

		// number of data set currently being sorted
		int   currentDataSet = 1;

		// Arrays containing randomly-generated numbers.
		int[] arrayHolderRecursive;
		int[] arrayHolderIterative;

		// Arrays to store the critical operation counts and total run time for all
		// data sets of both algorithms. Used to calculate average and standard deviation.
		long[] totalCountRecursion;
		long[] totalTimeRecursion;
		long[] totalCountIter;
		long[] totalTimeIter;


		// arrays containing the array lengths for the data sets of each algorithm
		recursiveData = new Object[ sizes.length ][5];
		iterativeData = new Object[ sizes.length ][5];

		// HeapSort objects that will sort unsorted arrays using either an 
		// iterative or recursive heap sort algorithm
		HeapSort heapR = new HeapSort();	
		HeapSort heapI = new HeapSort();
		
		// generates random numbers for arrays to be sorted in heap sort
		Random numCreator = new Random( max );
		
		// display progress of algorithm to user
		JFrame frame = new JFrame();
		JTextField text = new JTextField("Adding integers to array");
		text.setFont( new Font("Helvetica", Font.PLAIN, 30));

		frame.add(text);
		text.setBackground(Color.YELLOW);
		frame.setSize(new Dimension(400,100));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		// update progress
		try 						   { Thread.sleep(750);  }
		catch (InterruptedException e) { e.printStackTrace(); }
		text.setText("Starting heap sort");
		
		// create an array of length n, a value in the sizes array, of
		// random numbers from 1 to 50,000
		for (int i = 0; i < sizes.length; i++){

			// initialize arrays of unsorted integers
			arrayHolderRecursive= new int  [ sizes[i] ];
			arrayHolderIterative= new int  [ sizes[i] ];
			
			// initialize time and counter arrays that will hold the total time and 
			// operation count for individual data sets of both algorithms
			totalTimeRecursion  = new long [ dataSetSize ];
			totalCountRecursion = new long [ dataSetSize ];
			totalTimeIter		= new long [ dataSetSize ];
			totalCountIter		= new long [ dataSetSize ];

			
			
				
			
			// generate 50 data sets for each value of n
			while ( currentDataSet <= dataSetSize ){


				for (int n = 0; n < sizes[i]; n++){
					
					// Each sort algorithm has separate data sets to ensure that second
					// algorithm is not sorting on arrays that have been sorted by the 
					// first algorithm
					arrayHolderRecursive[n] = numCreator.nextInt( max );
					
					
					// store array lengths for each data set in the first column of the
					// tables for each algorithm
					recursiveData[i][0] = String.format("%,d", sizes[i]);
				}

				// rearrange some of the integers in the recursive array and use the newly
				// arrange array for the iterative sort
				arrayHolderIterative = randomizeArray(arrayHolderRecursive);
				iterativeData[i][0] = String.format("%,d", sizes[i]);

				
				// sort the arrays
				heapR.recursiveSort( arrayHolderRecursive );
				heapI.iterativeSort( arrayHolderIterative );
				

				// find total operation counts for entire data set
				totalCountRecursion[currentDataSet-1] = heapR.getCount();
				totalTimeRecursion [currentDataSet-1] = heapR.getTime();
				totalCountIter	   [currentDataSet-1] = heapI.getCount();
				totalTimeIter	   [currentDataSet-1] = heapI.getTime();

				// create next data set
				currentDataSet++;				
			}
			// reset data set counter for next set of array sizes
			currentDataSet = 1;

			// determine average critical operation count and execution time
			averageCountR = average( totalCountRecursion );
			averageTimeR  = average( totalTimeRecursion  );
			averageCountI = average( totalCountIter		 );
			averageTimeI  = average( totalTimeIter 		 );



			// determine standard deviation for critical operation count and 
			// execution time
			DeviationCountRecursion = standardDev( totalCountRecursion );
			DeviationTimeRecursion  = standardDev( totalTimeRecursion  );
			DeviationCountIterative = standardDev( totalCountIter );
			DeviationTimeIterative  = standardDev( totalTimeIter );


			// enter average and standard deviations values for critical operation counts
			// and run time in the tables for both algorithms
			recursiveData[i][1] = String.format("%,.0f", averageCountR);
			recursiveData[i][2] = String.format("%,.1f", DeviationCountRecursion);
			recursiveData[i][3] = String.format("%,.0f", averageTimeR);
			recursiveData[i][4] = String.format("%,.0f", DeviationTimeRecursion);

			iterativeData[i][1] = String.format("%,.0f", averageCountI);
			iterativeData[i][2] = String.format("%,.1f", DeviationCountIterative);
			iterativeData[i][3] = String.format("%,.0f", averageTimeI);
			iterativeData[i][4] = String.format("%,.0f", DeviationTimeIterative);
		}
		text.setText("Sort Complete!");
	}

	/**
	 * Creates GUI with two JTable's that display the performance statistics for
	 * the recursive and iterative heap sort algorithm
	 */
	public void displayReport(){

		// width and height of GUI window
		final int WIDTH = 900;
		final int HEIGHT = 1300;

		// components of GUI
		JFrame frame;
		JScrollPane topScroll;
		JScrollPane bottomScroll;

		// tables for average and standard deviation data
		JTable recursionTable;
		JTable iterativeTable;
		
		// Contains title of each table
		JTextField top;
		JTextField top2;
		JTextField bottom;
		JTextField bottom2;

		
		// constants for GUI formatting
		final int TEXTFIELD_FONT = 60;
		final int SUBTITLE_FONT = 20;
		final int ROW_HEIGHT = 40;
		final int ROW_WIDTH = 200;
		final int ROW_FONT = 22;
		final int HEADER_FONT = 20;
		final int HEADER_HEIGHT = 100;
		final int COLUMN_WIDTH = 150;
		final int FIRST_COLUMN_WIDTH = 30;
		final String FONT_TYPE = "Helvetica";
		
		
		
		
		
		// JFrame formatting
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Heap Sort Performance");
		frame.setSize(WIDTH,HEIGHT);
//		frame.setResizable(false);

		// JTextfield formatting
		top	   = new JTextField("Recursive Heap Sort");
		top2   = new JTextField("Critical Counting operation: Recursive calls to Heapify");
		bottom = new JTextField("Iterative Heap Sort");
		bottom2 = new JTextField("Critical Counting Operation: Number of array swaps");

		top.setEditable(false);
		top.setFont( new Font("Serif", Font.BOLD, TEXTFIELD_FONT) );
		top.setHorizontalAlignment(JTextField.CENTER);
		
		top2.setFont( new Font(FONT_TYPE, Font.PLAIN, SUBTITLE_FONT) );
		top2.setHorizontalAlignment(JTextField.CENTER);
		top2.setSize( new Dimension(WIDTH, 10));
		
		bottom.setEditable(false);
		bottom.setFont( new Font("Serif", Font.BOLD, TEXTFIELD_FONT) );
		bottom.setHorizontalAlignment(JTextField.CENTER);
		
		bottom2.setFont( new Font(FONT_TYPE, Font.PLAIN, SUBTITLE_FONT ));
		bottom2.setHorizontalAlignment(JTextField.CENTER);
		bottom2.setSize( new Dimension(WIDTH, 10));

		// Column titles for table
		String[] tableColumns = 
			{ 
					"<html><center>Data Set Size<br>(n)</html>",
					
					"<html><center>Average Critical <br>"
					+ "Operation Count</html>",
					
					"<html><center>Standard Deviation <br>"
					+ "of Count</html>",
					
					"<html><center>Average Execution <br>Time <br>"
					+ "<font size='4'>(nanoseconds)</font></html>",
					
					"<html><center>Standard Deviation <br>of Time <br>"
					+ "<font size='4'>(nanoseconds)</font></html>"
			};

		// add data to JTables
		recursionTable = new JTable(recursiveData, tableColumns);
		iterativeTable = new JTable(iterativeData, tableColumns);
				
		// format JTable
		recursionTable.setFont( new Font(FONT_TYPE, Font.PLAIN, ROW_FONT) );
		iterativeTable.setFont( new Font(FONT_TYPE, Font.PLAIN, ROW_FONT) );

		recursionTable.setEnabled(false);
		iterativeTable.setEnabled(false);

		recursionTable.setRowHeight(ROW_HEIGHT);
		iterativeTable.setRowHeight(ROW_HEIGHT);
					
		// add tables to JScrollPanes
		topScroll 	 = new JScrollPane(recursionTable);
		bottomScroll = new JScrollPane(iterativeTable);
		
		
		// formatting to center alignment of text
		DefaultTableCellRenderer center = new DefaultTableCellRenderer();
		center.setHorizontalAlignment(SwingConstants.CENTER);
		recursionTable.getColumnModel().getColumn(0).setCellRenderer(center);
		

		// Format JTable column widths and text alignment
		TableColumn rCol, iCol;
		for (int i = 0; i < 5; i++)
		{
			rCol = recursionTable.getColumnModel().getColumn(i);
			iCol = iterativeTable.getColumnModel().getColumn(i);
			iterativeTable.getColumnModel().getColumn(i).setCellRenderer(center);
			recursionTable.getColumnModel().getColumn(i).setCellRenderer(center);

			if (i > 0){
				rCol.setPreferredWidth( COLUMN_WIDTH );
				iCol.setPreferredWidth( COLUMN_WIDTH );
			}
			else{
				rCol.setPreferredWidth( FIRST_COLUMN_WIDTH );
				iCol.setPreferredWidth( FIRST_COLUMN_WIDTH );
			}
		}
		
		// format table headers
		JTableHeader topTitle 	 = recursionTable.getTableHeader();
		JTableHeader bottomTitle = iterativeTable.getTableHeader();

		topTitle   .setPreferredSize( new Dimension( ROW_WIDTH, HEADER_HEIGHT));
		bottomTitle.setPreferredSize( new Dimension( ROW_WIDTH, HEADER_HEIGHT));

		topTitle   .setFont(new Font(FONT_TYPE, Font.PLAIN, HEADER_FONT));
		bottomTitle.setFont(new Font(FONT_TYPE, Font.PLAIN, HEADER_FONT));



		// add components to frame
		frame.setLayout( new GridLayout(2,1) );
		JPanel t = new JPanel();
		JPanel b = new JPanel();
					
		t.setLayout( new BorderLayout() );
		b.setLayout( new BorderLayout() );
		
		t.add(top, BorderLayout.NORTH);
		t.add(top2, BorderLayout.CENTER);
		t.add(topScroll, BorderLayout.SOUTH);
		
		b.add(bottom, BorderLayout.NORTH);
		b.add(bottom2, BorderLayout.CENTER);
		b.add(bottomScroll, BorderLayout.SOUTH);
		
		frame.add(t);
		frame.add(b);
		frame.setVisible(true);
	}


	/**
	 * Calculates average of all values contained in an array
	 * @param arr		array of long values
	 * @return			average
	 */
	public static long average(long[] arr ){

		long total = 0;

		for (long t : arr){
			total += t; 
		}

		return total / arr.length;
	}


	/**
	 * Calculates standard deviation of values contained in an array
	 * @param arr		array whose values will be used to calculate 
	 * 					standard deviation
	 * @return			standard deviation
	 */
	public static double standardDev(long[] arr){

		long differences = 0;
		long average = average(arr);

		for (long a : arr){
			differences += ( a - average) * ( a - average);
		}

		return Math.sqrt( differences / arr.length );
	}


	/**
	 *  Randomly assigns values in an array to a new position. Used to turn a
	 *  sorted array into an unsorted array
	 * @param array		array to be rearranged
	 * @return			rearranged array
	 */
	public static int[] randomizeArray(int[] array){
		
		Random r= new Random(array.length-1);
		int temp;
		int num;
		
		for (int i = 0; i < array.length/4; i++){				
			temp = array[i];
			num = r.nextInt(array.length-1);
			array[i] = array[num];
			array[num] = temp;
		}
		return array;
	}
}
