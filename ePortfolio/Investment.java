package ePortfolio;

import java.util.*;

/**
 * The class is the parent class which has two children classes, mutualfund and
 * stock. The investment class holds
 * the values that both children class need for theit investments. There are
 * also some general computations that
 * are in this class.
 * 
 * @author Betty Vuong
 */
public abstract class Investment {
	protected String type;
	protected String symbol;
	protected String name;
	protected int quantity;
	protected double price;
	protected double bookVal;
	protected String saleObj;

	/**
	 * Constructor method
	 */
	public Investment() {
		type = "";
		symbol = "";
		name = "";
		quantity = 0;
		price = 0;
		bookVal = 0;
	}

	/**
	 * Overloaded constructor method
	 * 
	 * @param newType    provided value for type
	 * @param newSymbol  provided value for symbol
	 * @param newName    provided value for name
	 * @param newQuant   provided value for quantity
	 * @param newPrice   provided value for price
	 * @param newBookVal provided value for book value
	 */
	public Investment(String newType, String newSymbol, String newName, int newQuant, double newPrice,
			double newBookVal) {
		type = newType;
		symbol = newSymbol;
		name = newName;
		quantity = newQuant;
		price = newPrice;
		bookVal = newBookVal;
	}

	/**
	 * Clone constructor for abstract class
	 * @param copy
	 */
	public Investment(Investment copy){
		this(copy.getType(), copy.getSymbol(), copy.getName(), copy.getQuantity(), copy.getPrice(), copy.getBookVal());
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input type into type
	 * </p>
	 * 
	 * @param newType is the type from user
	 */
	public void setType(String newType) {
		type = newType;
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return String value for type
	 */
	public String getType() {
		return type;
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input symbol into symbol
	 * </p>
	 * 
	 * @param newSymbol is the symbol input from user
	 */
	public void setSymbol(String newSymbol) {
		try {
			symbol = newSymbol;
			if (!symbol.matches("^\\S*$")|| symbol.isEmpty()) {
				throw new IllegalArgumentException("Invalid symbol input.");
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Invalid symbol input.");
		}
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return String value for symbol
	 */
	public String getSymbol() {
		if (!symbol.matches("^\\S*$") || symbol.isEmpty()) {
			throw new IllegalArgumentException("Invalid symbol input.");
		}
		return symbol;
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input name into name
	 * </p>
	 * 
	 * @param newName is the name input from user
	 */
	public void setName(String newName) {
		try{
		name = newName;
		if(name.isEmpty()){
			throw new IllegalArgumentException("Name input is empty");
		}
		} catch(IllegalArgumentException ex){

		}
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return String value for name
	 */
	public String getName() {
		if(name.isEmpty()){
			throw new IllegalArgumentException("Name input is empty");
		}
		return name;
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input into quantity
	 * </p>
	 * 
	 * @param newQuantity the quantity input from user
	 */
	public void setQuantity(int newQuantity) {
		try {
			quantity = newQuantity;
			if (quantity <= 0) {
				throw new IllegalArgumentException("Quantity is less than or equal to zero.");
			}
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Quantity has invalid characters");
		}
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return Int value for quantity
	 */
	public int getQuantity() {
		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity is less than or equal to zero.");
		}
		return quantity;
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input into price
	 * </p>
	 * 
	 * @param newPrice the price input from user
	 */
	public void setPrice(double newPrice) {
		try {
			price = newPrice;
			if (price <= 0) {
				throw new IllegalArgumentException("Price is less than or equal to zero.");
			}
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Input for price is not valid");
		}
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return Double value for price
	 */
	public double getPrice() {
		if (price <= 0) {
			throw new IllegalArgumentException("Price must be greater or equal to zero");
		}
		return price;
	}

	/**
	 * <b>Modifier</b>
	 * <p>
	 * Sets the user input into book value
	 * </p>
	 * 
	 * @param newBookVal user input for book value
	 */
	public void setBookVal(double newBookVal) {
		bookVal = newBookVal;
	}

	/**
	 * <b>Constructor</b>
	 * 
	 * @return Double value for book value
	 */
	public double getBookVal() {
		return bookVal;
	}

	/**
	 * Rounds monetary values by two decimal places
	 * 
	 * @param gain value to be rounded
	 * @return Double type gain value rounded
	 */
	public double round(double gain) {
		gain = (double) Math.round(gain * 100) / 100;
		return gain;
	}

	public void setSaleObj(String newSaleObj) {
		saleObj = newSaleObj;
	}

	public String getSaleObj() {
		return saleObj;
	}

	/**
	 * used for overriding to child
	 * @param newQuant value is for user quantity
	 * @param newPrice value is for user price
	 */
	public abstract boolean sell(int newQuant, double newPrice);

	/**
	 * used for overriding to child gain which can compute using its variables local
	 * to its scope.
	 */
	public abstract double getGain();

	/**
	 * used for overriding to string
	 */
	public abstract String toString();

	/**
	 * used to override equals
	 */
	public abstract boolean equals(String symb1, String symb2);
	

}
