package numberTheory;

import javax.swing.JTextField;

import structure5.Association;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.LongStream;

import javax.swing.*;

/**
 * Class representing the internal state of the calculator. It is responsible
 * for keeping track of numbers entered and performing operations when buttons
 * are clicked. It tells the display what number to show.
 * 
 * @author Rae Harris
 * 4 October 2017
 * 
 * (Kim Bruce wrote isPrime())
 * 
 */

public class State {
	// Display on which results are written
	protected JTextArea calcDisplay;
	
	// Number Display of what numbers are being used
	protected JTextField one,
						two,
						three,
						four;
	// Display's instructions
	protected JTextArea instruct;
	
	// Represents the number the user is making
	protected int number = 0; 
	
	// Represents which field is being used (0 for first,...)
	protected int current = 0; 
	
	// Used to combine digits without adding them
	protected String numberS = ""; 
	
	// Remember if number constructed is used
	protected boolean numberUsed = true; 

	// Remembers which of the four numbers have been used
	private boolean[] used = new boolean[4];
	
	//Remembers if display can be saved to one of four numbers
	protected boolean displayNum = true; 
	
	// Holds the JTextFields that each of the four numbers are displayed on
	ArrayList<JTextField> numberDisplays = new ArrayList<JTextField>();
	
	// Hold the integer values of the four numbers used
	protected int[] numbers;
	
	/**
	 * Constructor of the State class
	 * @param display		JTextArea that displays the results
	 * @param instruction	JTextArea that displays the instructions
	 * @param numberOne		JTextField that displays the first number
	 * @param numberTwo		JTextField that displays the second number
	 * @param numberThree	JTextField that displays the third number
	 * @param numberFour	JTextField that displays the fourth number
	 */
	public State(JTextArea display,JTextArea instruction,JTextField numberOne,JTextField numberTwo,
					JTextField numberThree,JTextField numberFour) {
		calcDisplay = display;
		instruct = instruction;
		this.one = numberOne;
		this.two = numberTwo;
		this.three = numberThree;
		this.four = numberFour;
		
		numberDisplays.add(this.one);
		numberDisplays.add(this.two);
		numberDisplays.add(this.three);
		numberDisplays.add(this.four);
				
		// Each are 0
		numbers  = new int[4];
		numbers[0]=0;
		numbers[1]=0;
		numbers[2]=0;
		numbers[3]=0;
		
		//Each Number is not used yet
		used[0] = false;
		used[1] = false;
		used[2] = false;
		used[3] = false;		
	}

	/***
	 * User clicked on a digit button. Saves the digit for later use
	 * Combines with previous pressed digits being displayed
	 * 
	 * @param value the number displayed on the button the user pressed
	 */
	public void addDigit(int value) {
		
		//If the number is not used in an equation, add digit to previous digits
		if(!used[current]){
			if(numbers[current] == 0){
				numberS = "";
			}
			else{
				numberS = "" + numbers[current];				
			}
			numberS = numberS+value;			
			number = Integer.parseInt(numberS);
			numbers[current] = number;
			numberDisplays.get(current).setText(numberS);
		}
		
		else{//The number was used in an equation, change to new number
			numberS = "";
			numberS = numberS +value;
			number = Integer.parseInt(numberS);
			numbers[current] = number;
			numberDisplays.get(current).setText(numberS);
			used[0] = false;
			used[1] = false;
			used[2] = false;
			used[3] = false;		
		}
	}

	/***
	 * User has clicked on operator button. 
	 * Performs the operation on the top two numbers on the stack
	 * 
	 */
	public void doOp(char op) {
		if(op == '1'){
			current = 0;
		}
		else if(op == '2'){
			current = 1;
		}
		else if(op == '3'){
			current = 2;
		}
		else if(op == '4'){
			current = 3;
		}
	}

	
	//The general actions that do not do calculations 
/************************************************************/	

	
	/** WORKS
	 * User clicked on clear key, returns all to initial state
	 */
	public void clear() {
		numberS = ""; //reset string representing number			
		number = 0; //reset number
		numberUsed = false; 
		
		//Reset displays
		calcDisplay.setText(""+0);
		one.setText(""+0);
		two.setText(""+0);
		three.setText(""+0);
		four.setText(""+0);
		
		//Reset array to 0's
		for(int i=0;i<numbers.length;i++){
			numbers[i]=0;
			used[i]=false;
		}
		
		displayNum = true;
		calcDisplay.setText("0");
		instruct.setText("Instructions");	
	}
	
	/**
	 * WORKS
	 * Clears only the current number
	 */
	public void backspace() {
		numbers[current] =0;
		numberDisplays.get(current).setText(""+numbers[current]);
		used[current] = false;	
	}

	/**
	 * WORKS
	 * Takes the current number and change it's sign
	 */
	public void swapSign() {
		numbers[current] = -numbers[current];
		numberDisplays.get(current).setText(""+numbers[current]);		
	}

	// The methods that do calculations that result in 
	// a number which can be saved into one of the numbers
/************************************************************/	

	/**
	 * Squares the number selected and displays it
	 */	
	public void square(){
		int temp = numbers[current]; 
		temp = temp * temp; 
		calcDisplay.setText(""+temp);
		instruct.setText("Squares current number: n^2");
		displayNum = true;
	}
	
	/**
	 * Factor the number int 2^n+2^n-1...
	 * @param num The power being factored
	 * @return
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
	
	/**
	 * Performs successive squaring on num^pow modular mod
	 * @param num The number being raised
	 * @param pow The power used
	 * @param mod modualr mod
	 * @return
	 */
	public void successiveSquaring(){
		int num = numbers[0];
		int pow = numbers[1];
		int mod=numbers[2];
		if(mod == 0){
			calcDisplay.setText("mod must be a number");
			instruct.setText("Performs successive squaring on num^pow modular mod"
					+ "\nFirst = num being raised"
					+ "\nSecond = pow, the power "
					+ "\nThird =  mod");
			displayNum = false;	
		}
		else if(pow == 0){
			calcDisplay.setText(""+1);
			instruct.setText("Performs successive squaring on num^pow modular mod"
					+ "\nFirst = num being raised"
					+ "\nSecond = pow, the power "
					+ "\nThird =  mod");
			displayNum = true;	
			used[1]=true;
		}
		else{
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
			
			calcDisplay.setText(""+p);
			instruct.setText("Performs successive squaring on num^pow modular mod"
					+ "\nFirst = num being raised"
					+ "\nSecond = pow, the power "
					+ "\nThird =  mod");
			
			displayNum = true;	
			used[0] = true;
			used[1] = true;			
			used[2] = true;
		}
	}
	

	
	/**
	 * Helper version of successive squaring on num^pow modular mod
	 * @param num The number being raised
	 * @param pow The power used
	 * @param mod modualr mod
	 * @return
	 */
	private int successiveSquaring(int num, int pow,int mod){
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

	/**
	 * Pollar's p-1 factorization algorithm
	 * @param a the "starting seed"
	 * @param N modulo N
	 * @param bound How large a j to check before stopping
	 * @return
	 */
	public void pollards(){
		int a = numbers[0];
		int N = numbers[1];
		int bound=numbers[2];

		int j = 2;	
		boolean success = false;
		int d=1;
		if(N == 0){
			calcDisplay.setText("N must be a number");
			instruct.setText("Pollar's p-1 factorization algorithm"
					+ "\nFirst = a, the 'seed' starting point being raised"
					+ "\nSecond = N, the mod value"
					+ "\nThird = the bound, how far before giving up"
					+ "\n-1 means no answer, the a or bound is too small");
			displayNum = false;	
		}
		else if(d > 1 && d < N){//initial check if a works
		}
		else{
			while(!success && j < bound){
				//Calculate a and d
				a = successiveSquaring(a,j,N);
				d = gcdHelper(a-1,N);
				//If 1<d<N, then d, else increment j and loop
				if(d > 1 && d < N){
					success = true;
				}
				else{
					j++;
				}
			}
		}
		
		calcDisplay.setText(""+d);
		instruct.setText("Pollar's p-1 factorization algorithm"
				+ "\nFirst = a, the 'seed' starting point being raised"
				+ "\nSecond = N, the mod value"
				+ "\nThird = the bound, how far before giving up"
				+ "\n-1 means no answer, the a or bound is too small");
		
		displayNum = true;	
		used[0] = true;
		used[1] = true;			
		used[2] = true;
	}

	/**
	 * Applies factorial to the number and displays it
	 * @post number n! is displayed 
	 */
	public void factorial(){
		double temp = (double)numbers[current];
		double result = 1.0;
		
		while(temp>1){
			result = result * (temp);
			temp--;
		}
		int r = (int) result;

		calcDisplay.setText(""+r);
		instruct.setText("Factorial of current number: n!");
		displayNum = true;		
	}
	
	/**
	 * Calculates the GCD of the first and second numbers
	 * @return
	 */
	public int gcd() {
		if(numbers[1] == 0){
			displayNum = true;
			calcDisplay.setText(0+"");
			instruct.setText("Calculates the GCD between First and Second");
			used[0] = true;
			used[1] = true;			
			return 0;
		}
		else{
			int gcd = gcdHelper(numbers[0],numbers[1]);
			displayNum = true;
			calcDisplay.setText(gcd+"");
			instruct.setText("Calculates the GCD between First and Second");
			used[0] = true;
			used[1] = true;
			return gcd;				
		}
	}
	
	/**WORKS
	 * Private method that calculates the gcd between two numbers
	 * @param x First number
	 * @param y Second number
	 * @return GCD int
	 */
	private int gcdHelper(int x, int y){
			int remainder = x % y,	
					 newX = y,
					 newY = x % y,
					 //quotient = Math.floorDiv(x, y),
					 gcdInt = y;

			if(remainder == 0 ){
				return gcdInt;
			}
			else{
				gcdInt = this.gcdHelper(newX, newY);
			}
			return gcdInt;
	}

	// Methods that do calculations that result in answers 
	// that cannot be stored into the numbers
/************************************************************/	
	
	/**
	 * Tells if number is prime
	 * @author Kim Bruce
	 */
	public void isPrime(){
		boolean prime = isPrimeHelp(numbers[current]);
		calcDisplay.setText("Is "+numbers[current]+" prime? "+prime);
		instruct.setText("Checks if the Current number is prime or not");
		displayNum = false;	
		used[current] = true;
	}
	
	/**
	 * Helper for public version isPrime()
	 * @param n The prime to check
	 * @return
	 */
	private boolean isPrimeHelp(long n) {
		return n > 1 && LongStream.rangeClosed(2, (long)Math.sqrt(n)).
		noneMatch(divisor -> n % divisor == 0);
		}

	/**
	 * Calculates whether the first and second numbers are relatively prime
	 */
	public void relativelyPrime() {
		String prime;
		if(this.gcd()==1){
			prime = "Relatively Prime = true";
		}
		else{
			prime = "Relatively Prime = false";
		}

		instruct.setText("Tells if first and second numbers are relatively prime");
		calcDisplay.setText(prime);	
		displayNum = false;
		used[0] = true;
		used[1] = true;
	}

	/**
	 * Creates the index table for mod p using base b
	 * @param b The base being raised by the order e_p(a)
	 * @param p from base^I(a) congruent a (mod p)
	 */
	public void IndexA() {
		int b = numbers[0];
		int p = numbers[1];
	
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
		i=0;

		String print = "I(a): " + indexA +"\n" 
				+ "a:    "+ a 
				+"\n" +"\n" 
				+"sorted a:    " + sortedA +"\n" +"sorted I(a): " + sortedIndexA;
		
		String inst = "first number is the base being raised by the order e_p(a) \n"
				+ "second number is p from base^I(a) congruent a (mod p)";
		
		calcDisplay.setText(print);
		instruct.setText(inst);
		displayNum = false;
		
	}

	/**
	 * Calculates the Euclidean Algorithm
	 */
	public void euclidean(){
		int x=numbers[0];
		int y=numbers[1];
		String s = "";
		if(y == 0){
			String result = "Nothing times "+y+ "=" + x+"\n No result";
			used[0] = true;
			used[1] = true;
			calcDisplay.setText(result);
			instruct.setText("Calculating Euclidean ALgorithm using First and Second");
			displayNum = false;
		}
		else{
			String result = euclideanHelp(x,y,s);
			used[0] = true;
			used[1] = true;
			calcDisplay.setText(result);
			instruct.setText("Calculating Euclidean ALgorithm using First and Second");
			displayNum = false;
		}
	}
	
	/** 
	 * Prints out the euclidean algorithm of gcd(x,y)
	 */
	private String euclideanHelp(int x, int y, String result) {

		int remainder = x%y,	
				 newX = y,
				 newY = x % y,
				 quotient = x/y;
		if(remainder > 0){
			result = result + "\n" + x+ "=" + quotient + "*" + y + "+"+remainder;
			return this.euclideanHelp(newX,newY,result);
		}
		else{
			result = result + "\n" + x+ "=" + quotient + "*" + y + "+"+remainder;
			return result;				
		}
	}
	
	/**	
	 *  Writes out the Factoring of n^(2^power) with 
	 *  Successive Squaring
	 */
	public void squareFactoring(){
		int current = numbers[0],
				power = numbers[1],
				m = numbers[2];

		String s = current +" mod " +m +"\n";
		
		if(m==0){
			s = "Error, m must be a nonzero";
		}
		else{
			//int previous;
			for(int i=1; i<=power;i++){	
				if(current ==1){
					i=power;
				}
				else{
				//	previous = current;
					current = current * current;
					current = Math.floorMod(current, m);
					s = s +current +" mod " +m +"\n";
				}
			}
		}
		calcDisplay.setText(s);
		instruct.setText("Using successive squaring, compute n^(2^power) mod m \n"
				+ "with n=First, power = Second, and m = Third");
		displayNum = false;
		
		used[0] = true;
		used[1] = true;
		used[2] = true;
	}


	/**
	 * If the Display is showing a number, store in currently 
	 * selected Number and remember number has not been used
	 */
	public void displayToNum() {
		instruct.setText("If the Display is showing a number, "
				+ "\n then store in currently selected Number");	
		
		if(displayNum){
			int disp = Integer.parseInt(calcDisplay.getText());
			numbers[current] = disp;
			numberDisplays.get(current).setText(disp+"");
		}		
	}
}




