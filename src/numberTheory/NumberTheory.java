package numberTheory;

import java.util.ArrayList;
import java.util.stream.LongStream;

import structure5.Association;

/**
 * This class holds methods that calculate various algorithms 
 * and other computations generally used in a general 
 * Number Theory
 * 
 * These methods are addapted and put into the State class in order
 * to be used with the calculator
 * 
 * @author Rae Yan Yan Harris
 * 
 */
public class NumberTheory {
	
	public NumberTheory(){
		
	}		
	
	//WORKS
	/**
	 * Creates the index table for mod p using base b
	 * @param b The base being raised by the order e_p(a)
	 * @param p from base^I(a) congruent a (mod p)
	 */
	public void indexA(int b, int p){
		ArrayList<Integer> indexA = new ArrayList<Integer>(); //Holds I(a)
		ArrayList<Integer> a = new ArrayList<Integer>(); //Holds a
	
		ArrayList<Association<Integer,Integer>> sortingAI = new ArrayList<Association<Integer, Integer>>(); //Hold (a,I(a))
		ArrayList<Association<Integer,Integer>> sortingIA = new ArrayList<Association<Integer, Integer>>(); //Hold (I(a),a)
		ArrayList<Integer> sortedIndexA = new ArrayList<Integer>(); //Holds I(a)
		ArrayList<Integer> sortedA = new ArrayList<Integer>(); //Holds a
		int i = 1,
			n=0,
			base = b,
			result;
		
		//Create arraylists where I(a) in order
		while (i<p){
			result = Math.floorMod(base,p); // congruent b^i mod p
			base = result*b;
			indexA.add(i);
			a.add(result);
			sortingAI.add(new Association<Integer, Integer>(result,i));
			sortingIA.add(new Association<Integer, Integer>(i,result));
			i++;
		}
		
		//Sort so a in order
		i=1;
		while(i<p){ //iterate through a
			
			//Go till key equals the i 
			while(sortingAI.get(n).getKey()!=i && n<sortingAI.size()-1){
				n++;
			}
			//Got key, put into array
			sortedA.add(i);
			sortedIndexA.add(sortingAI.get(n).getValue());
			n=0; //reset n to search again
			i++;
		}
		System.out.println("I(a): "+indexA);
		System.out.println("a:    "+a);
		System.out.println("");
		System.out.println("sorted a:    " + sortedA);
		System.out.println("sorted I(a): " + sortedIndexA);
		System.out.println("");
		i=0;
	}
	
	//WORKS
	/**
	 * Method that checks if two int are relatively prime
	 * @param first First integer
	 * @param second Second integer
	 * @return Whether the two numbers are relatively prime to one another
	 */
	public boolean relativePrime (int first, int second){	
		if(this.gcd(first, second) == 1 ){
			System.out.println("Relatively Prime = "+true);
			return true;
		}
		else {
			System.out.println("Relatively Prime = "+false);
			return false;
			}
	}

	/**
	 * Checks if a number is a prime
	 * @param n The number to check
	 * @return whether the number n is prime or not
	 */
	public boolean isPrime(long n) {
		return n > 1 && LongStream.rangeClosed(2, (long)Math.sqrt(n)).
		noneMatch(divisor -> n % divisor == 0);
		}
	
	//WORKS
	/** 
	 * Prints the GCD calculated using gcd method
	 * @param x First integer
	 * @param y Second integer
	 * @post Prints out the GCD of int x and y and returns gcd	
	 */
	public int gcdPrint(int x, int y){
		System.out.println("GCD = "+this.gcd(x, y));
		System.out.println("");
		return this.gcd(x, y);
	}
	
	//WORKS
	/** 
	 * Method that calculates the GCD of integers x and y
	 * @param first integer x
	 * @param second integer y
	 * @return gcd(x,y)
	 */
	private int gcd(int x, int y){
		int remainder = x % y,	
				 newX = y,
				 newY = x % y,
				 gcdInt = y;

		if(remainder == 0 ){
			return gcdInt;
		}
		else{
			gcdInt = this.gcd(newX, newY);
		}
		return gcdInt;
	}
	
	//WORKS
	/**
	 * Prints Euclidean Algorithm using helper method euclidean() 
	 * @param x First number
	 * @param y Second number
	 */
	public void printEuclidean(int x, int y){
		System.out.println("Euclidean Algorithm");
		this.euclidean(x, y);	
	}
	
	//WORKS
	/** 
	 * Helper Method which prints out the euclidean algorithm of gcd(x,y)
	 * @param x first int
	 * @param y second int
	 */
	private void euclidean(int x, int y){
		int remainder = x%y,	
				 newX = y,
				 newY = x % y,
				 quotient = x/y;
		if(remainder > 0){
			System.out.println(x + "=" + quotient + "*" + y 
					+ "+"+remainder);
			this.euclidean(newX,newY);
		}
		else{
			System.out.println(x + "=" + quotient + "*" + y + "+" + remainder);
			System.out.println("Finished");	
			System.out.println("");		
		}
		System.out.println("");
	}
	
	//WORKS
	/**
	 * Factor the number int =2^n+2^n-1...
	 * @param num The power being factored
	 * @return An arraylist holding the powers 2 is raised
	 */
	private ArrayList<Integer> squaring(int num){
		int x = 1;
		ArrayList<Integer> powers = new ArrayList<Integer>();
		while(x!=0){//while x is not 0, continue
			int a =(int) (num-Math.pow(2, x));
			int b = (int)(num-Math.pow(2, x-1));
			
			//If remainder of num-2^x is 0, then x is all we want
			if(a==0){
				powers.add(x);
				x=0;	
			}
			else if(a<0 && b > 0){//found max n of 2^n
				x=x-1;//reduce to next power down
				powers.add(x);//remember the power found
				num = (int) (num-Math.pow(2, x));//Get the remainder
			}
			else if((num-Math.pow(2, x)) > 0)//there is a larger power for x to be
				x++;
			else //
				x--;
		}
		//check if n=1, if so add 0 to arraylist
		if(num==1)
			powers.add(0);
		
		return powers;
	}
	
	//WORKS
	/**
	 * Performs successive squaring on num^pow modular mod
	 * @param num The number being raised
	 * @param pow The power used
	 * @param mod modualr mod
	 * @return a where a is congruent to num^pow modular mod
	 */
	public int successiveSquaring(int num, int pow,int mod){
		ArrayList<Integer> powers = squaring(pow);
		ArrayList<Integer> sucPow = new ArrayList<Integer> ();
		sucPow.add(Math.floorMod(num,mod)); // n^2^0 mod mod
		int x = powers.get(0),//largest power
			y = 0,
			n = num;
		//Calculate all the squaring mods for the highest exponent of 
		//the 2^exp that makes up num
		while(y<x){
			n = Math.floorMod(n*n, mod);//n^2 modular mod
			sucPow.add(n); // add the 2^y'th version of n^2^y modular mod			
			y++;
		}
		
		int p = 1;
 		for(int s:powers){
			p = Math.floorMod(p*sucPow.get(s),mod);
		}
		return p;
	}
	
	//WORKS
	/**
	 * Calculates n!
	 * @param a number to be factorialized
	 * @return n!
	 */
	public double factorial(double a){
		double temp = a;
		double result = 1;
		
		while(temp>1){
			result = result * (temp);
			temp--;
		}
		return result;
	}

	//FIXING
	/**
	 * Pollar's p-1 factorization algorithm
	 * @param N Integer to be factored
	 */
	public int pollards(int a,int N,int bound){
		int j = 2;	
		boolean success = false;
		int d=1;
		
		System.out.println("N = "+ 1739);
		if(d > 1 && d < N){//initial check if a works
			System.out.println("j = " +j);
			return d;
		}
		else{
			while(!success && j < bound){
				//Calculate a and d
				a = successiveSquaring(a,j,N);
				d = gcd(a-1,N);
				//If 1<d<N, then d, else increment j and loop
				
				System.out.println("j is             " + j);
				System.out.println("a^j mod N ==     " + a);
				System.out.println("d = gcd(a-1,N) = " + d);
				System.out.println("");
				
				if(d > 1 && d < N){
					success = true;
					System.out.println("j = " +j);
					return d;
				}
				else{
					j++;
				}
			}
		}
		System.out.println("");
		return d;				
	}
	
}



