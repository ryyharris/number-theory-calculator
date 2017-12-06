package numberTheory;

import java.awt.event.*;

/**
 * Rae Harris 
 * 2 October 2017
 * 
 * Class representing buttons with numbers on them.
 * 
 * Rae Harris
 * 4 October 2017
 * 
 * @author Kim B. Bruce
 * @version 1/97
 */
public class DigitButtonListener implements ActionListener {

	private State calcState; // Does all computation - buttons informed of event
	private int buttonInt;	// Integer lable of button
	
	/**
	 * Button knows own value and the state so can communicate with it.
	 * 
	 * @param newValue The number displayed on the JButton that is implementing DigitButtonListener
	 * @param state State that keeps track of calculator's internal
	 */
	public DigitButtonListener(int newValue, State state) 
	{
		calcState = state;
		buttonInt = newValue;
	}
	
	/**
	 * 
	 * @pre:  User clicked on the button.
	 * @post:  Informed state that it was clicked on and what its value is.
	 */
	public void actionPerformed(ActionEvent evt) 
	{
		calcState.addDigit(buttonInt);
	}
}
