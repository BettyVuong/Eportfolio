package ePortfolio;
import java.util.*;

/**
 * The class is an object class that acts like a Mutual Fund, it is a child class that inherits Investment. 
 * The class can calculate book value, payment values, modify and calculate investment sales as well as their 
 * gain, calculate a predicted gain of the investment, and refine the output of the monetary values. 
 * @author Betty Vuong
 */
public class MutualFund extends Investment{

	private final double REDEMPTION = 45.00;
	
	/**
	 * Constructor method
	 */
	public MutualFund() {
		super();
	}
	
	/**
	 * Overloaded constructor method
	 * @param newType provided value for type
	 * @param newSymbol provided value for symbol
	 * @param newName provided value for name
	 * @param newQuant provided value for quantity
	 * @param newPrice provided value for price
	 * @param newBookVal provided value for book value
	 */
	public MutualFund(String newType, String newSymbol, String newName, int newQuant, double newPrice, double newBookVal){
		super(newType, newSymbol, newName, newQuant, newPrice, newBookVal);
	}
	
	/**
	 * toString override that formats the MutualFund with it's correlating data
	 * @return the formatted string fro MutualFund
	 */
	public String toString() {
		return "\n\n" + symbol + ": " + name + "\nQuantity: " + quantity 
				+ " units \nPrice: $" + price + "\nBook Value: $" + bookVal + "\n";
	}
	
	/**
	 * Calculates book value given the users input, this is for buy
	 * @param units user quantity
	 * @param price user updated price
	 * @return the calculated book value of this transaction
	 */
	public double calculateBookVal(int units, double price) {
		bookVal += ( (double) units * price);
		bookVal = super.round(bookVal);
		return bookVal;
	}
	
	/**
	 * Calculates book value, prints out the payment and gain for the investment and updates the investments
	 * book value, price, and quantity.
	 * @param newQuant user input quantity
	 * @param newPrice user input updated price
	 * @return Boolean value to indicate if the investment has any quantity remaining
	 */
	public boolean sell(int newQuant, double newPrice) {
		boolean empty = false;
		double pay = payment(newQuant, newPrice);
		int remainQuant = quantity - newQuant;
		double remainBookVal = 0, soldBookVal = 0, gain = 0;

		// determining if it's a full or partial sale
		if (remainQuant != 0) { // if it's a partial sale
			remainBookVal = bookVal* ((double)remainQuant/(double)quantity); // calculating remaining book value
			soldBookVal = bookVal - remainBookVal; // calculating how much is sold
			gain = pay - soldBookVal; // calculating gain
			gain = round(gain);
			
			//updating object
			bookVal = remainBookVal; // updating book value
			price = newPrice; //updating price
			quantity = remainQuant; //updating quantity

			saleObj = "Payment: $" + pay + " Gain: $" + gain;
			empty = false;
		} else { // full sale
			remainBookVal = bookVal * ((double)remainQuant/(double)quantity); // calculating remaining book value
			soldBookVal = bookVal - remainBookVal; // calculating how much is sold
			gain = pay - soldBookVal; // calculating gain
			gain = round(gain);

			saleObj = "Payment: $" + pay + " Gain: $" + gain;
			empty = true;
		}

		return empty;
	}

	/**
	 *  Calculating payment which is used for other helper methods
	 *  
	 * @param newQuant user input quantity
	 * @param newPrice user input updated price
	 * @return Double type payment total
	 */
	public double payment(int newQuant, double newPrice) {
		double payVal = ((double) newQuant * newPrice)-REDEMPTION;

		return payVal;
	}
	
	/**
	 * Calculates hypothetical gain per investment without changing the stock attributes
	 * @return Double type hypothetical gain
	 */
	public double getGain() {
		double gain = 0;
		double pay = payment(quantity, price);
		gain = pay - bookVal;
		gain = super.round(gain);
		
		return gain;
	}
	/**
	 * overridden equals to compare if symbols are the same
	 * @return if symbols are the same, return true, otherwise false.
	 */
	public boolean equals(String symb1, String symb2){
		if(symb1.equalsIgnoreCase(symb2)){
			return true;
		}
		return false;
	}
	
}
