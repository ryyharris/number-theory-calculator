package numberTheory;

import javax.swing.*;
//import javax.swing.event.*;
//import javax.swing.text.DefaultCaret;

import java.awt.*;
import java.awt.event.*;

/**
 * 
 * Application for the calculator
 * 
 * @author yanyanharris
 *
 */
@SuppressWarnings("serial") //We do not need the class to be Serializable
public class Calculator extends JFrame {
	private static final int HEIGHT = 600,
							WIDTH = 400;
	
	private JScrollPane scrollDisplay,
						scrollInstruction;

	private JTextArea display, //Shows end result
						instruction;

	private JTextField   // Tells user equation or what occurs with numbers
						numberOne,	  // Show first number to be used
						numberTwo,	  //Show second to be used
						numberThree, 	//Show third to be used
						numberFour; 	//Show fourth to be used
	
	private State calcState; //Object w/memory of computation in progress
	private JPanel calcPanel; //Holds buttons and display

	private JButton[] digitButton; // Array of buttons representing 10 digits


	private JButton clearButton, 		 //Clear out numbers
					enterButton,		 //execute Equations from dispCombo 
					calculateButton,	 //execute Formulas from dispCombo
					backspaceButton,	 //Removes constructed number from selected field
					pulsMinusButton,	 //Swaps number's sign
					
					numberOneButton,	 //select first number JTextField
					numberTwoButton,	 //select second number JTextField
					numberThreeButton,	 //select third number JTextField
					numberFourButton, 	 //select fourth number JTextField
					displayToNum;
		
					
/*					factorButton,		 //Factors number
					squareButton,		 //Squares number
	
					gcd,	// Buttons that perform equations
					euclidean,
					indexA,
					relativePrime;
*/	
	 //Hold type of calculation to occur, cannot put in 4 number boxes
	private JComboBox<String>  dispCombo,
								numCombo;
	

	
	
	public Calculator(){
		calcPanel = new JPanel();
		calcPanel.setLayout(new GridLayout(9,1,5,5)); //5 rows w/1 col

		instruction =  new JTextArea("Instructions"); //Display is right-justified
		instruction.setEditable(false);
		instruction.setLineWrap(true);	
		scrollInstruction = new JScrollPane(instruction);
		calcPanel.add(scrollInstruction);

		display = new JTextArea("0"); //Display is right-justified
		display.setEditable(false);
		display.setLineWrap(true);
		scrollDisplay = new JScrollPane(display);
	    calcPanel.add(scrollDisplay);
		
		
	/*****/
		JPanel numberPanel = new JPanel(); //Panel holding numbers' displays
		numberPanel.setLayout(new GridLayout(1, 4, 5, 5)); // lay out 1 row w/4 cols
		
		numberOne = new JTextField("0");
		numberOne.setEditable(false);
		numberOne.setHorizontalAlignment(JTextField.CENTER);		
		numberTwo = new JTextField("0");
		numberTwo.setEditable(false);
		numberTwo.setHorizontalAlignment(JTextField.CENTER);
		numberThree = new JTextField("0");
		numberThree.setEditable(false);
		numberThree.setHorizontalAlignment(JTextField.CENTER);
		numberFour = new JTextField("0");
		numberFour.setEditable(false);
		numberFour.setHorizontalAlignment(JTextField.CENTER);	
		
		calcState = new State(display,instruction,numberOne,numberTwo,numberThree,numberFour); 

		
		numberPanel.add(numberOne);
		numberPanel.add(numberTwo);
		numberPanel.add(numberThree);
		numberPanel.add(numberFour);
		
		calcPanel.add(numberPanel);
		numberPanel.setSize(210, 100);
		
	//*****//*
		
		JPanel topPanel = new JPanel(); // Panel for top two rows
		topPanel.setLayout(new GridLayout(1, 4, 5, 5)); // lay out 1 row w/4 cols

		// cols
		numberOneButton = new JButton("First");
		numberOneButton.addActionListener((ActionEvent evt) ->{
			numberOneButton.setBackground(Color.BLUE);
			numberTwoButton.setBackground(Color.WHITE);
			numberThreeButton.setBackground(Color.WHITE);
			numberFourButton.setBackground(Color.WHITE);
			calcState.doOp('1');
			numberOneButton.setOpaque(true);
		});
		topPanel.add(numberOneButton);
		
		numberTwoButton = new JButton("Second");
		numberTwoButton.addActionListener((ActionEvent evt) ->{
			numberOneButton.setBackground(Color.WHITE);
			numberTwoButton.setBackground(Color.BLUE);
			numberThreeButton.setBackground(Color.WHITE);
			numberFourButton.setBackground(Color.WHITE);
			calcState.doOp('2');
			numberTwoButton.setOpaque(true);
		});
		topPanel.add(numberTwoButton);
		
		numberThreeButton = new JButton("Third");
		numberThreeButton.addActionListener((ActionEvent evt) ->{
			numberOneButton.setBackground(Color.WHITE);
			numberTwoButton.setBackground(Color.WHITE);
			numberThreeButton.setBackground(Color.BLUE);
			numberFourButton.setBackground(Color.WHITE);
			calcState.doOp('3');
			numberThreeButton.setOpaque(true);
		});
		topPanel.add(numberThreeButton);
		
		numberFourButton = new JButton("Fourth");
		numberFourButton.addActionListener((ActionEvent evt) ->{
			numberOneButton.setBackground(Color.WHITE);
			numberTwoButton.setBackground(Color.WHITE);
			numberThreeButton.setBackground(Color.WHITE);
			numberFourButton.setBackground(Color.BLUE);
			calcState.doOp('4');
			numberFourButton.setOpaque(true);
		});
		topPanel.add(numberFourButton);
		
		calcPanel.add(topPanel);
		
	
		
		JPanel midPanel;
		digitButton = new JButton[10]; // Set up all buttons to enter digits

		for (int row = 0; row < 3; row++) {
			midPanel = new JPanel();
			midPanel.setLayout(new GridLayout(1, 4, 5, 5));

			for (int col = 0; col < 3; col++) {
				int digit = (2 - row) * 3 + col + 1;
				digitButton[digit] = new JButton("" + digit);
				digitButton[digit].addActionListener(new DigitButtonListener(
						digit, calcState));
				// digitButton[digit].setBackground(Color.cyan);
				midPanel.add(digitButton[digit]);
			}
			switch (row) {
			case 0:
				backspaceButton = new JButton("BackSpace");
				backspaceButton.addActionListener((ActionEvent evt) -> {calcState.backspace();});
				midPanel.add(backspaceButton);
				break;
			case 1:
				clearButton = new JButton("clear");
				clearButton.addActionListener((ActionEvent evt) -> {calcState.clear();});
				midPanel.add(clearButton);
				break;
			case 2:
				pulsMinusButton = new JButton("+/-");
				pulsMinusButton.addActionListener((ActionEvent evt) -> {calcState.swapSign();});
				
				midPanel.add(pulsMinusButton);
				break;
			}

			calcPanel.add(midPanel);

			setSize(210, 300);
		}
		
		
		JPanel bottomMidPanel = new JPanel();
		bottomMidPanel.setLayout(new GridLayout(1, 4, 5, 5));


		enterButton = new JButton("Enter");
		enterButton.addActionListener((ActionEvent evt) -> {
			String selected = dispCombo.getSelectedItem()+"";
			
			if(selected.compareTo("Relatively Prime") == 0){
				calcState.relativelyPrime();
			}
			else if(selected.compareTo("Index(A)") == 0){
				calcState.IndexA();
			}
			else if(selected.compareTo("Euclidean") == 0){
				calcState.euclidean();
			}
			else if(selected.compareTo("squareFactoring")==0){
				calcState.squareFactoring();
			}
			else if(selected.compareTo("Is Prime?")==0){
				calcState.isPrime();
			}
		});		
		enterButton.setBackground(Color.red);
		enterButton.setOpaque(true);

		bottomMidPanel.add(enterButton);
		
		
		digitButton[0] = new JButton("" + 0);
		digitButton[0].addActionListener(new DigitButtonListener(0, calcState));
		bottomMidPanel.add(digitButton[0]);
		
		
		calculateButton = new JButton("Calculate");
		calculateButton.addActionListener((ActionEvent evt) -> {
			String selected = numCombo.getSelectedItem()+"";
			if(selected.compareTo("Square") == 0){
				calcState.square();
			}
			else if(selected.compareTo("n!")==0){
				calcState.factorial();
			}
			else if(selected.compareTo("GCD")==0){
				calcState.gcd();
			}			
			else if(selected.compareTo("Pollar's p-1")==0){
				calcState.pollards();
			} 
			else if(selected.compareTo("Successive Squaring")==0){
				calcState.successiveSquaring();
			} 
		});
		calculateButton.setBackground(Color.green);
		calculateButton.setOpaque(true);
		bottomMidPanel.add(calculateButton);
		
		
		calcPanel.add(bottomMidPanel);
		
		
		
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2, 5, 5));//1 row 4 col

		dispCombo = new JComboBox<String>();
		dispCombo.addItem("Relatively Prime");
		dispCombo.addItem("Index(A)");
		dispCombo.addItem("Euclidean");
		dispCombo.addItem("Is Prime?");		
		dispCombo.setBackground(Color.red);
		dispCombo.setOpaque(true);
		bottomPanel.add(dispCombo);

		displayToNum = new JButton("Copy");
		displayToNum.addActionListener((ActionEvent evt) -> {calcState.displayToNum();});
		bottomPanel.add(displayToNum);
		
		numCombo = new JComboBox<String>();
		numCombo.addItem("Square");
		numCombo.addItem("n!");
		numCombo.addItem("GCD");
		numCombo.addItem("Pollar's p-1");
		numCombo.addItem("Successive Squaring");
		numCombo.setBackground(Color.green);
		numCombo.setOpaque(true);
		bottomPanel.add(numCombo);

		calcPanel.add(bottomPanel);

		// Default layout for Frames is BorderLayout.
		getContentPane().add(BorderLayout.CENTER, calcPanel); // Adds calcPanel
																// in center of
																// Frame.
	}
	
	
	


	/**
	 * Post: Put border around calcPanel in applet This is a rather bizarre
	 * procedure inherited from Container. Overriding this method provides new
	 * inset values when components are added to this container. Makes sure to
	 * leave the specified amount of space along each edge of Frame.
	 **/
	public Insets getInsets() {
		return new Insets(40, 10, 10, 10);
	}

	/**
	 * Create and show Calculator so it can respond to events
	 * 
	 * @param args
	 *            -- ignored
	 */
	public static void main(String[] args){
		Calculator myCalc = new Calculator();
		myCalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myCalc.setSize(WIDTH, HEIGHT);
		myCalc.setVisible(true);
	}
}









