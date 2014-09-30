/*
 * Classname : PermComb
 */




import java.util.Arrays;
import java.util.Scanner;

/**
 *  
         This class implements the Algorithm for permutation of n numbers, Given in "A DRAFT OF SECTION 7.2.1.2: GENERATING ALL PERMUTATIONS " in 
         "THE ART OF COMPUTER PROGRAMMING PRE-FASCICLE 2B" By Dr. DONALD E. KNUTH 

 *
 * @version      
         1.0, 13 Sep 2014  
 * @author          
        Manuj Singh 
 */
public class PermComb
{
	/**  
    	count keeps track of the total number of permutation.
	 */
	static int count = 0;

	/**  
	"v" indicates the verbose mode. 
	 "0" for non verbose 
	 "1" for verbose
	 */
	static int v;

	/**  
	"n" is the total number of the objects we need to sort of type key
	 */
	static int n;

	/**  
	"A" is array that holds the element whose permutation has to be generated
	 */
	static int[] A;

	/**
	 * The first method which gets the input from the user through standard input
	 *
	 *
	 * @param args 			the string array which contains ant argument passed to the main.
	 *
	 */
	@SuppressWarnings("resource")
	public static void main(final String[] args) { 
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter Value of n between 0 and 1000:");
		// getting the input from the user
		n = scanner.nextInt();

		// validating input
		do
		{
			if(n<0 || n > 1000)
			{
				System.out.println("Please enter a value between 0 and 1000");	
				n = scanner.nextInt();
			}

		}while(n<0 || n > 1000);


		System.out.println("Enter Value of v,\n a) 0 for non-verbose \n b) 1 for verbose");

		// getting value of "v"
		v = scanner.nextInt();

		// validating input
		do
		{
			if( v!= 0 && v != 1)
			{
				System.out.println("Only 0 and 1 are allowed");	
				v = scanner.nextInt();
			}

		} while (v!= 0 && v != 1);
		scanner.nextLine(); 
		System.out.println("Enter array elements seperated by spaces:");

		// getting elements to be sorted
		String arr =scanner.nextLine();

		// splitting the array so as to get the individual elements
		String[] str = arr.split("\\s+");
		do
		{
			if(str.length != n)
			{
				System.out.println("you did not enter the correct number of elements. Try Again");
				arr =scanner.nextLine();
				str = arr.split("\\s+");
			}

		}while(str.length != n);

		A = new int[n];
		for(int i =0;i<str.length;i++)
		{
			A[i] = Integer.parseInt(str[i]);
		}

		// sorting the input
		Arrays.sort(A);

		// starting the clock to measure running time
		long start = System.currentTimeMillis();

		// calling PermDup to find the permutaion
		PermDup(A);
		
		long last = System.currentTimeMillis();

		// printing out the count and the time taken
		System.out.println( count +"," + (last-start) );
	}

	/**
	 * this methods generates permutation for n elements
	 *
	 * @param A 			the array which contains the elements whose permutaion is to be generated
	 * 
	 * @preCondition : this function assumes that all the elements in the array are sorted for the first call .i.e a1<=a2<=.......an
	 */

	static void PermDup(int[] A)
	{
		// visiting the permutation
		visit(A,n,v);

		// j Represents the first element we need to swap in order to permute the elements
		int j = FindCorrectJ(A);

		// if j is "-9999" then all the permutations are generated and we need to stop the recursion
		// this is the base case
		if(j == -9999)
		{
			return;
		}

		// l Represents the second element we need to swap in order to permute the elements
		int l = FindCorrectL(A,j);			

		// swap elements at index j and l to generate new permutation
		swap(A,j,l);

		// after the above step all the elements between jth index and nth index will be out of order
		// to get them in order we need to swap elements after index j till last element so that
		// aj+1 <= aj+2.... <= an
		// basically we reverse all the elements from index j+1 to last index of the array, so that we get the next lexicographic permutation
		int k = j+1;
		l = n-1;
		while(k < l)
		{
			swap(A,k,l);
			k++;
			l--;
		}

		// calling again the permDup to visit the new permutation
		PermDup(A);
	}

	/**
	 * this methods swaps two elements
	 *
	 * @param A 			the array which contains the elements which need to be swaped
	 * 
	 * @param a				index of first element
	 * 
	 * @param b				index of second element
	 * 
	 */
	private static void swap(int []A ,int a , int b)
	{
		int temp1 = A[a];
		A[a] = A[b];
		A[b] = temp1;
	}

	/**
	 * this methods finds the correct index of the first element that need to be sorted in order to generate a new valid permutation
	 *
	 * @param A 			the array which contains the elements whose permutation is to be generated
	 * 
	 * @return : 			correct index of j or "-9999", if we reach the beginning of the array and no more permutation can be generated
	 */
	static int FindCorrectJ(int[] A)
	{
		//pointing to  second last element to the array
		int j = n-2;
		// making sure that the element represented by j is less then the next element in the array
		while(A[j] >= A[j+1])
		{
			j--;
			if(j < 0)
			{
				// if j< 0 then we have reach the begining of the array , and we have found all the permutations 
				// so we need to stop,returning a sentinel no to represnent that all the correct permuttaion have been generated
				return -9999;
			}
		}
		return j;
	}

	/**
	 * this methods finds the correct index of the second element that need to be sorted in order to generate a new valid permutation
	 *
	 * @param A 			the array which contains the elements whose permutation is to be generated
	 * 
	 * @param j          	index of j 	 
	 * @return : 			correct index of L
	 */
	static int FindCorrectL(int[] A , int j)
	{
		// pointing to the last element of the array
		int l = n-1;

		// making sure that the element represented by the index l is greater then the element represented by the index j
		while(A[j] >= A[l])
		{
			l--;
		}

		return l;
	}
	static void visit(final int[] A, final int n, final int verbose)
	{
		if (verbose == 1) 
		{
			for(int i = 0; i < n; i++)
			{				
				System.out.print(A[i] + " ");
				
			}

			System.out.println();


		}

		// incrementing the count of the permutation
		count++;
	}

}
