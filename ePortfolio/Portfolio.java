/**
 * @author Betty Vuong
 * @version 11/29/24
 * Student ID: 1271673
 * Command lines: 
 * Compile: javac ePortfolio/*.java
 * Bundle JAR: jar cvfe ePortfolio.jar ePortfolio.Portfolio ePortfolio/*.class
 * Run: java -jar ePortfolio.jar
 */

package ePortfolio;

import java.util.*;

/**
 * The main class that computes majority of the portfolo functions and has
 * various helper methods that are used throughout the main class. The class
 * acts as an eportfolio for a users investments.
 * 
 * @author Betty Vuong
 */
public class Portfolio {

	/**
	 * The main class that maintains two types of investments (stocks and mutual
	 * funds), they're children classes of the parent class Investment. Which the
	 * main class proceeds to perform functions for the portfolio
	 * investments such as buying, selling, updating, computing the total gain per
	 * investment and overall portfolio , and searching each investment for matched
	 * user input attributes. This updated program implements hashmaps to search for
	 * key words.
	 * 
	 * @param args main class parameter
	 */

	static ArrayList<Investment> investments = new ArrayList<Investment>();
	static HashMap<String, ArrayList<Integer>> keyWords = new HashMap<String, ArrayList<Integer>>();
	static ArrayList<Integer> compList = new ArrayList<Integer>();
	public static String totalGainStr;

	public static void main(String[] args) {

		// create an instance of the class
		Portfolio portfolio = new Portfolio();
		// invoke gui window
		MainWindow mainWindow = new MainWindow(portfolio);
		mainWindow.setVisible(true);

	}

	/**
	 * Compares user input symbol with the symbols in the array list of type
	 * investment to find a matching object.
	 * 
	 * @param symbol      of the stock
	 * @param investments which is the array list for investment
	 * @return the index of the object that matches the symbol
	 */
	public static int compSymbol(String symbol, ArrayList<Investment> investments) {
		int index = -1;
		for (int i = 0; i < investments.size(); i++) { // traversing arraylist to find a matching symbol
			Investment current = investments.get(i);
			if (symbol.equalsIgnoreCase(current.getSymbol())) {
				return i;
			}
		}
		return index;
	}

	/**
	 * Ensures and checks that the quantity the user wants to sell of the investment
	 * is within the range of quantity availible in their portfolio. Making the
	 * program robust.
	 * 
	 * @param investType  String type of the type of investment
	 * @param symbol      of the investment
	 * @param index       of the object in the array list
	 * @param investments array list for all investments (mutual fund and stock)
	 * @return a valid int user input of the number of quantity they want to sell of
	 *         the investment
	 */
	public static int validQuant(String symbol, int index, String quantity, ArrayList<Investment> investments) {
		int userVal, investQuant;

		// checking if there's enough quantity to sell
		// ensure quantity is positive
		userVal = quantInput(quantity); // parsing value from front end first

		// check the quantity and make user enter correct amount
		Investment instance = investments.get(index);// getting the object user wants to sell
		investQuant = instance.getQuantity();
		// checking quantity is in range
		if (userVal <= investQuant) { // if in range
			// range = true;
		} else { // if not in range
			userVal = -1;
		}

		return userVal;
	}

	/**
	 * Searches and matches the user input range with the object prices
	 * 
	 * @param range       user input of the range of price they want to search
	 * @param index       of the object in the array list
	 * @param investments array list for all investments (mutual fund and stock)
	 * @return a boolean value indicating if the object is within the user input
	 *         range or not
	 */
	public static boolean searchRange(String range, int index, ArrayList<Investment> investments) {
		double price, match, exactMatch;
		Investment instance = investments.get(index);
		price = instance.getPrice();

		String[] rangeWords = range.split("-");

		// finding the bounds of the user range input
		if (range.contains("-")) {
			if (!rangeWords[0].isEmpty()) { // min case
				match = priceInput(rangeWords[0]);
				if (price >= match) {
					return true;
				}
			} else if (!rangeWords[1].isEmpty()) { // max only
				match = priceInput(rangeWords[1]);

				if (price <= match) {
					return true;
				}
			} else if ((rangeWords.length) == 2) { // range has a min and max
				double minMatch, maxMatch;
				minMatch = priceInput(rangeWords[0]);
				maxMatch = priceInput(rangeWords[1]);
				if (minMatch <= price && price <= maxMatch) {
					return true;
				}
			}

		} else { // exact value search
			exactMatch = priceInput(range.trim());
			if (exactMatch == price) { // comparing exact match to object price
				return true;
			}
		}

		return false;
	}

	/**
	 * Populates the hashmap with the key and their corresponding list indexes
	 * 
	 * @param keyWords    is the hashmap of all the keywords available and the
	 *                    corresponding indexes
	 * @param investments array list for all investments (mutual fund and stock)
	 */
	public static void updateHashMap(HashMap<String, ArrayList<Integer>> keyWords, ArrayList<Investment> investments) {
		String name = "", delimiter = " ";
		int tokens = 0;
		for (int i = 0; i < investments.size(); i++) {
			Investment temp = investments.get(i);
			name = temp.getName().toLowerCase();
			// splitting each word to create keys
			String[] nameWords = name.split("[ \n\t]+");
			// create keys or find them
			for (int j = 0; j < nameWords.length; j++) {
				if (keyWords.containsKey(nameWords[j])) { // traverse each word
					// key exists
					ArrayList<Integer> index = keyWords.get(nameWords[j]);
					index.add(i);// adds the index of the array list element
					keyWords.put(nameWords[j], index);
				} else { // create a new key
					String key = nameWords[j];
					ArrayList<Integer> index = new ArrayList<Integer>();
					index.add(i);// adds the index of the array list element
					keyWords.put(key, index);
				}
			}
		}
	}

	/**
	 * Changes indexes of each object within each key that is affected by the
	 * removal of an object from
	 * the list
	 * 
	 * @param index    the index of the object that is removed from the array list
	 * @param keyWords is the hashmap of all keywords available and the
	 *                 corresponding indexes
	 */
	public static void reduceHashMap(int index, HashMap<String, ArrayList<Integer>> keyWords) {
		for (String key : keyWords.keySet()) { // traverse hashmap
			ArrayList<Integer> indexes = keyWords.get(key);
			ListIterator<Integer> iterator = indexes.listIterator();
			// int i = 0;

			// traverse elements in list
			while (iterator.hasNext()) {
				int current = iterator.next();

				if (current == index) { // remove object
					iterator.remove();
				} else if (current > index) { // decrement index for all other objects after the object removed
					current -= 1;
					iterator.set(current);
				}
				// i++;
			}
		}
	}

	/**
	 * Method is invoked when the user slects buy investment from the front end GUI. This will either create a new investment with the  
	 * according type and either update or create the investment with the user inputted values.
	 * @param investInpt user investment type input
	 * @param symbInp user symbol input
	 * @param nameInp user name input
	 * @param quantInp user quantity input
	 * @param priceInp user price input
	 * @return string that contains the output for the investment with the proper toString() formatting of the investment and the 
	 * attribues.
	 */
	public static String buy(String investInpt, String symbInp, String nameInp, String quantInp, String priceInp) {
		int unit, quant, dupeSymbol;
		double price;
		String investType, symbol, name, message;
		boolean flag = false;

		investType = investInpt;

		flag = false;
		// finding investment symbol
		symbol = symbolInput(symbInp.trim());
		symbol = symbol.toUpperCase();

		// checking if there is a duplicate
		dupeSymbol = compSymbol(symbol, investments);

		// considering the investment type
		if (investType.equalsIgnoreCase("stock")) { // for stock
			if (dupeSymbol != -1) { // there's a duplicate for an investment
				// checking what match type it is
				Investment instance = investments.get(dupeSymbol);
				String testType = instance.getType();

				// checking if its within stock or not
				if (testType.equalsIgnoreCase("stock")) { // investment is found and exists
					// update stock info
					// downcast to stock object
					Stock buyStock = (Stock) instance;
					quant = buyStock.getQuantity();

					// for price
					price = priceInput(priceInp);

					// for units
					unit = quantInput(quantInp);

					// updating info
					buyStock.calculateBookVal(unit, price);
					buyStock.setQuantity(quant + unit);
					buyStock.setPrice(price);

					// update hashmap
					updateHashMap(keyWords, investments);

					// creating output for message
					message = "Action completed.\n";
					message = message + buyStock.toString();
					return message;

				} else { // duplicate of a mutual fund
					message = "This symbol exists, but not for the investment type you are trying to buy."
							+ "\nPlease enter a valid Symbol";
					return message;
				}

			} else { // new investment needs to be created
				// create a new investment
				Stock newStock = new Stock();

				name = nameInp;

				// for price
				price = priceInput(priceInp);

				// for units
				unit = quantInput(quantInp);

				// placing values into object
				newStock.setType("stock");
				newStock.setSymbol(symbol);
				newStock.calculateBookVal(unit, price);
				newStock.setQuantity(unit);
				newStock.setPrice(price);
				newStock.setName(name);

				// adding object to arraylist
				investments.add(newStock);

				// update hashmap
				updateHashMap(keyWords, investments);

				// creating output for message
				message = "Action completed.\n";
				message = message + newStock.toString();
				return message;
			}
		} else { // for mutual fund
			if (dupeSymbol != -1) { // there's a duplicate
				// checking what match type it is
				Investment instance = investments.get(dupeSymbol);
				String testType = instance.getType();

				// checking if the match is of mutual fund type
				if (testType.equalsIgnoreCase("mutual fund")) { // investment is mutual fund
					// update MF info
					// downcast from investment to mutual fund
					MutualFund buyMutual = (MutualFund) instance;
					quant = buyMutual.getQuantity();

					// for price
					price = priceInput(priceInp);

					// for units
					unit = quantInput(quantInp);

					// updating info
					buyMutual.calculateBookVal(unit, price);
					buyMutual.setQuantity(quant + unit);
					buyMutual.setPrice(price);
					// update hashmap
					updateHashMap(keyWords, investments);

					// creating output for message
					message = "Action completed.\n";
					message = message + buyMutual.toString();
					return message;
				} else { // investment is found but deosnt match what user wants
					message = "This symbol exists, but not for the investment type you are trying to buy."
							+ "\nPlease enter a valid Symbol";
					return message;
				}

			} else { // new investment needs to be created
				// create a new investment
				MutualFund newMutual = new MutualFund();

				// name input
				name = nameInp;

				// for price
				price = priceInput(priceInp);

				// for units
				unit = quantInput(quantInp);

				// placing values into object
				newMutual.setType("mutual fund");
				newMutual.setSymbol(symbol);
				newMutual.calculateBookVal(unit, price);
				newMutual.setQuantity(unit);
				newMutual.setPrice(price);
				newMutual.setName(name);

				// adding object to arraylist
				investments.add(newMutual);
				// update hashmap
				updateHashMap(keyWords, investments);

				// creating output for message
				message = "Action completed.\n";
				message = message + newMutual.toString();
				return message;
			}
		}

	}

	/**
	 * Method is used for selling and is nvoked when user selects sell in front end GUI.
	 * Investments are sold and prices are updated and changed according to user input. This will also update the hashmap according 
	 * to how the arraylist is changed.
	 * @param symbInp user symbol input.
	 * @param quantInp user quantity input.
	 * @param priceInp user price input.
	 * @return Message is a string that gets returned with the approriate output of the stock updated, which is the gain and payment.
	 */
	public static String sell(String symbInp, String quantInp, String priceInp) {
		int quant, dupeSymbol;
		double price;
		String investType, symbol, message;
		boolean flag = false, remainInvest = false;
		int investSize = investments.size();

		// ensuring there are investments to sell
		if (investSize != 0) {

			// calling method checking for user input, user must enter an input that is
			// reasonable, if not they're reprompted with refined instructions
			// for now comment
			// investType = investInput(flag, investType);
			flag = false;

			// finding investment symbol and if it's in the portfolio
			do {
				// finding investment symbol
				symbol = symbolInput(symbInp.trim());
				symbol = symbol.toUpperCase();

				dupeSymbol = compSymbol(symbol, investments);

				// ensuring there is a match overall
				if (dupeSymbol != -1) {

					// finding duplicates

					// checking what match type it is
					Investment instance = investments.get(dupeSymbol);
					// String testType = instance.getType();

					// Stock newStock = (Stock) instance;

					// call valid quant to check
					quant = validQuant(symbol, dupeSymbol, quantInp, investments);

					// for validity purposes
					if (quant == -1) {
						throw new IllegalArgumentException("Quantity entered is greater than the quantity you own.\n");
					}

					// asking for updated price
					// for price
					price = priceInput(priceInp);

					// processing sale
					remainInvest = instance.sell(quant, price);

					// removing investment since it's fully sold
					if (remainInvest) {
						// updating hashmap
						reduceHashMap(dupeSymbol, keyWords);
						investments.remove(dupeSymbol);
					}

					flag = true;

					// get message for the sale
					message = instance.getSaleObj();
					return message;

				} else { // no match to the lists
					message = "The investment you want to sell doesn't exist in your portfolio, please enter an investment you own.";
					return message;
				}
			} while (!flag);
		} else { // handling case where there are no investments
			message = "There are no investments in your portfolio to sell, start buying investments to sell them!\n";
			return message;
		}
	}

	/**
	 * method updates the investment price that user wants to update.
	 * @param priceStr user input for price.
	 * @param index index for the investment the user wants to update.
	 * @return returns the invesment output that is updated as a string for front end GUI.
	 */
	public String update(String priceStr, int index) {
		String message;
		double newPrice = 0;
		Investment update = investments.get(index);

		// getting the price by parse
		newPrice = priceInput(priceStr);

		// update price for the object
		update.setPrice(newPrice);

		message = update.toString();
		return message;
	}

	/**
	 * Method calculates gain for the front end gain GUI. This calculates total gain and gain for each investment.
	 * @return a string of the gain for each investment.
	 */
	public static String gain() {
		String symbol, name, message = "";
		double total = 0, currentGain = 0;
		totalGainStr = ""; // clear global strign to ensure the output will be correct
		// accumulating total for investments
		for (int i = 0; i < investments.size(); i++) {
			Investment newInvest = investments.get(i);

			// calculating gain for investments
			symbol = newInvest.getSymbol();
			name = newInvest.getName();
			currentGain = newInvest.getGain();
			total += currentGain;
			// to look at how each investment is doing
			message = message + "\nCurrent total gain for " + symbol + " - " + name + ": $" + currentGain;
		}

		totalGainStr = "$" + total;

		return message; // returning for front end interface
	}

	/**
	 * The method is used for searching investments using attributes provided from user in the search GUI.
	 * 
	 * @param symbInp user symbol input.
	 * @param nameKeyInp user key words input.
	 * @param lowInp user string value for minimum price input.
	 * @param highInp user string value for minimum price input.
	 * @return A string of all investments that suit that attributes user inputted. Used to display in front end GUI.
	 */
	public static String search(String symbInp, String nameKeyInp, String lowInp, String highInp) {
		int choice = 0, unit, quant, dupeSymbol;
		double price;
		String investType, symbol, name, menu, message = "", range;
		boolean flag = false, remainInvest = false;

		// getting symbol fron user front end
		symbol = symbolInput(symbInp.trim());

		// input for keywords
		String words = nameKeyInp.trim();

		// input for numbers
		String min = lowInp.trim();
		String max = highInp.trim();
		// to make the range work for the method
		if (!min.isEmpty() || !max.isEmpty()) { // if there is input
			//range validation
			rangeValid(min, max);
			range = min + "-" + max;
		} else {
			range = "";
		}

		// matching according to case
		if (!symbol.isEmpty() && words.isEmpty() && range.isEmpty()) { // only search for symbol
			// for investments
			int found = compSymbol(symbol, investments);
			if (found != -1) { // if found
				Investment instance = investments.get(found);
				message = instance.toString();
			}
		} else if (symbol.isEmpty() && !words.isEmpty() && range.isEmpty()) { // only search for key words
			// for easier and consistent validation
			words = words.toLowerCase();
			String[] splitWords = words.split("[ \n\t]+");

			// clear comparison list
			compList.clear();

			// find objects that match key words
			for (int i = 0; i < splitWords.length; i++) {
				if (keyWords.containsKey(splitWords[i])) {
					ArrayList<Integer> temp = keyWords.get(splitWords[i]); // get the matching objects for
																			// keyword
					if (i == 0) { // first instance
						// ensuring no dupes
						for (int j = 0; j < temp.size(); j++) {
							Integer pos = temp.get(j);
							if (!compList.contains(pos)) {
								compList.add(pos);
							}
						}

					} else {
						// retains any common objects
						compList.retainAll(temp);
					}
				}

			}

			// add objects to message for front end
			for (int i = 0; i < compList.size(); i++) {
				int index = compList.get(i); // grabbing index from list
				Investment temp = investments.get(index); // getting an instance of object from given index

				message = message + temp.toString();
			}

		} else if (symbol.isEmpty() && words.isEmpty() && !range.isEmpty()) { // only search for price range
			boolean foundRange;
			// traverse investment array list
			for (int i = 0; i < investments.size(); i++) {
				foundRange = searchRange(range, i, investments);
				// add to message if found
				if (foundRange) {
					Investment instance = investments.get(i);

					message = message + instance.toString();
				}
			}

		} else if (!symbol.isEmpty() && !words.isEmpty() && range.isEmpty()) { // search for symbol and key
																				// words
			// compare symbols
			int found = compSymbol(symbol, investments);
			if (found != -1) { // if found

				// search key words
				// for easier and consistent validation
				words = words.toLowerCase();
				String[] splitWords = words.split("[ \n\t]+");

				// clear comparison list
				compList.clear();

				// find objects that match key words
				for (int i = 0; i < splitWords.length; i++) {
					if (keyWords.containsKey(splitWords[i])) {
						ArrayList<Integer> temp = keyWords.get(splitWords[i]); // get the matching objects for
																				// keyword
						if (i == 0) { // first instance
							// ensuring no dupes
							for (int j = 0; j < temp.size(); j++) {
								Integer pos = temp.get(j);
								if (!compList.contains(pos)) {
									compList.add(pos);
								}
							}
						} else {
							// retains any common objects
							compList.retainAll(temp);
						}
					}

				}

				// if the only value matches, it adds the object to message
				if (compList.get(0) == found) {// if true print
					Investment instance = investments.get(found);
					message = message + instance.toString();
				}
			}
		} else if (!symbol.isEmpty() && words.isEmpty() && !range.isEmpty()) { // search for symbol and price
																				// range
			boolean valid = false;
			// for stocks
			int found = compSymbol(symbol, investments);
			if (found != -1) { // if found
				// compare range
				valid = searchRange(range, found, investments);
				if (valid) {// if true print
					Investment instance = investments.get(found);

					// finding which object type matches the symbol search
					message = message + instance.toString();
				}
			}

		} else if (symbol.isEmpty() && !words.isEmpty() && !range.isEmpty()) { // search for key words and price
																				// range
			boolean foundRange;

			// search for key words
			// for easier and consistent validation
			words = words.toLowerCase();
			String[] splitWords = words.split("[ \n\t]+");

			// clear comparison list
			compList.clear();

			// find objects that match key words
			for (int i = 0; i < splitWords.length; i++) {
				if (keyWords.containsKey(splitWords[i])) {
					ArrayList<Integer> temp = keyWords.get(splitWords[i]); // get the matching objects for
																			// keyword
					if (i == 0) { // first instance
						// ensuring no dupes
						for (int j = 0; j < temp.size(); j++) {
							Integer pos = temp.get(j);
							if (!compList.contains(pos)) {
								compList.add(pos);
							}
						}
					} else {
						// retains any common objects
						compList.retainAll(temp);
					}
				}

			}

			// now search for prices with the indexes matched from key words
			for (int i = 0; i < compList.size(); i++) {
				int currentInd = compList.get(i);
				// search price range
				foundRange = searchRange(range, currentInd, investments);
				if (foundRange) {// if true add to message
					Investment instance = investments.get(currentInd);

					// finding which object type matches the symbol search
					message = message + instance.toString();
				}
			}

		} else if (!symbol.isEmpty() && !words.isEmpty() && !range.isEmpty()) {

			boolean valid = false;
			// for stocks
			int found = compSymbol(symbol, investments);
			if (found != -1) { // if found
				// compare range
				valid = searchRange(range, found, investments);
				if (valid) {// if true compare key word
					// compare words
					// for easier and consistent validation
					words = words.toLowerCase();
					String[] splitWords = words.split("[ \n\t]+");

					// clear comparison list
					compList.clear();

					// find objects that match key words
					for (int i = 0; i < splitWords.length; i++) {
						if (keyWords.containsKey(splitWords[i])) {
							ArrayList<Integer> temp = keyWords.get(splitWords[i]); // get the matching objects
																					// for
																					// keyword

							if (i == 0) { // first instance
								// ensuring no dupes
								for (int j = 0; j < temp.size(); j++) {
									Integer pos = temp.get(j);
									if (!compList.contains(pos)) {
										compList.add(pos);
									}
								}
							} else {
								// retains any common objects
								compList.retainAll(temp);
							}
						}

					}
					if (compList.get(0) == found) { // if true add to message
						Investment instance = investments.get(found);

						// finding which object matches the symbol search
						message = message + instance.toString();
					}
				}

			}
		} else { // no fields are entered, display all
			for (int i = 0; i < investments.size(); i++) {
				Investment instance = investments.get(i);
				message = message + instance.toString();
			}
		}

		return message;

	}

	/**
	 * Ensures that price is a valid input and parses correctly. Method handles cases such as neg or zero values and parsing.
	 * @param priceStr User input for price from GUI.
	 * @return the parsed value for price.
	 */
	public static double priceInput(String priceStr) {
		double price;
		// for price
		try {
			price = Double.parseDouble(priceStr);
			if (price <= 0) {
				throw new IllegalArgumentException("Price is less than or equal to zero.");
			}

		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Input is not valid");
		}
		return price;
	}

	/**
	 * Ensures that the quantity input from user is a valid input and parses correctly. Method handles the case where quantity
	 * is neg or zero. Or if the value input is not a number.
	 * @param quantStr User input for quantity from front end GUI.
	 * @return the parsed quantity input from user.
	 */
	public static int quantInput(String quantStr) {
		int quant;
		// for quant
		try {
			quant = Integer.parseInt(quantStr);
			//is quantity a postive value
			if (quant <= 0) {
				throw new IllegalArgumentException("Quantity is less than or equal to zero.");
			}

		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Input for Quantity is not valid");
		}
		return quant;
	}

	/**
	 * Ensures that user input is the correct parameters for symbol.
	 * @param symbolTxt user input for symbol from front end GUI.
	 * @return string value of user input for symbol.
	 */
	public static String symbolInput(String symbolTxt) {
		String symbol = "";

		try {
			symbol = symbolTxt.trim();
			if (!symbol.matches("^\\S*$")) {
				throw new IllegalArgumentException("Invalid symbol input.");
			}
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("Invalid symbol input.");
		}

		return symbol;
	}

	/**
	 * Method identifies if user inputs a range with both max and min and ensures that the range is appropriate.
	 * @param min is the string min value inputted from user in front end
	 * @param max is the string max value inputted from user in front end
	 */
	public static void rangeValid(String min, String max){
		double minNum = priceInput(min);
		double maxNum = priceInput(max);
		if(minNum != 0 && maxNum != 0){ //compare to ensure the range for the values are correct
			if(minNum > maxNum){
				// means that min is greater than max
				throw new IllegalArgumentException("Range is incorrect, min should be less than or equal to max.");
			}
			// so search can continue
		}
	}
}
