Name: Betty Vuong
Date: 11/29/24
Student ID: 1271673
Course: CIS2430

General Problem

The program mimics an investment portfolio where a user can keep track of their investment activities. 
For this specific program the eportfolio contains two types of investments (stocks and mutual funds), 
the action's performed on the investments are buying and selling investments, searching for relevant 
attributes of investments, updating investment prices, and calculating the total gain of the portfolio. 
All the calculations are automatic and displayed for the user. The user will be able to interact with the
program using the GUI interface implemented for the program.

Assumptions and Limitations

Although the program is designed to be robust. This means that many inputs and actions are handled
by the program by exception handling. The user is providing the data for each investment meaning 
that the investments may not be accurate to the market. The program is built for user interaction
through the GUI interface only. This means that the terminal is not used for the program.

User Guide

To run the program follow these command lines in a command-line interface:
Compile: javac ePortfolio/*.java
Bundle JAR: jar cvfe ePortfolio.jar ePortfolio.Portfolio ePortfolio/*.class
Run: java -jar ePortfolio.jar

Once the program is running, the window will pop up on the screen. The user is prompted a 
main page that lets the user understand how to navigate the application. The user can then click
the commands on the upper left part of the window and will be displayed an array of options for 
the portfolio. A similar visual is provided below of what the array of options would look like
if user clicks on command.

-----------------------------------------
|Commands:                              |
-----------------------------------------
|Buy investments                        |
|Sell investments                       |
|Update investments                     |
|Get total gain of portfolio            |
|Search investments                     |
|Quit program                           |
-----------------------------------------

Depending on their choice selection, the window will change accordingly.

From here, a test plan will be discussed to look into the program with further depth. Each selection
has a different visual, thus, the test plan goes by choice.

Test Plan

Objectives:
1. Compute correct calculations for investments by buying, selling, updating, and calculating total 
   gain of the portfolio.
2. Ensuring that data is refreshed and up to date after each action is performed. This includes:
	a. Costs such as book value, gains, quantity, and payment are correct.
	b. Removing unnecessary investments once the quantities are zero.
3. Ensuring that search with the implementation of hashmaps is smooth, all searches are correct and robust.
   this means the program should be able to handle more than one key word.
4. Ensure that the GUI interface has the correct components.
5. Ensure that the GUI interface has the correct behaviors.
6. The program can handle different input cases using exception handling without breaking the program.
7. The outputs from front to back end are accurate to the previous interface results.
	
Defensive Programming:
1. The program can accept input and provide refined and specific feedback if user input is not correct.
3. For numerical inputs that contribute to the programs calculations, measures are considered to ensure that
   the inputs are within bound and will not affect the investments.
4. The program considers certain actions where there is an immediate reprompt from the action if new actions
 are required.
5. Ensuring there is no repetition when uniqueness in data is required.
6. Inputs are not case-sensitive.
7. Numerical inputs are to be positive.
8. Inputs are handled and input verification is provided feedback along with the ability to retry the input.
9. User cannot modify areas of the GUI according to the purpose of each area.

Test Cases:

Message/search result panels:
- User cannot edit the areas
- There are scroll bars horizontally and vertically for when necessary scrolling is required.

Buy:
- Click reset
action: the textfields for user input is cleared
- Click "Buy" with missing fields
for example, all empty fields: "Input is not valid"
- all the actions/outputs below are when fields are inputted and user clicks "buy"
- Do not select the type from the combo box
output/action: the investment is automatically a stock unless selected otherwise.
- Enter symbol with various cases (ApPl)
output/action: the symbol will automatically be capitalized, will be able to see after clicking "buy"
- Enter a symbol that exists for a different investment type (e.g "SSTEX" in stock)
output/action: "This symbol exists, but not for the investment type you are trying to buy. Please enter a valid Symbol"
- Enter a symbol that is not in the portfolio (NVDA)
output/action: new investment is created with the exact inputs from user
- Enter price as an int (100)
output/action: program will auto convert to double. User can see in the message output.
- Enter price that is negative
output/action: "Price is less than or equal to zero."
- Enter quantity as negative
output/action: "Quantity is less than or equal to zero."
- Enter a new investment to buy, this checks the hashmap is working right

Sell:
- Click reset
action: the textfields for user input is cleared
- Click "sell" with no investments in portfolio
output:"There are no investments in your portfolio to sell, start buying investments to sell them!"
- Click "sell with investments in portfolio and matches symbol
output: "Investment: AAPL Payment: $1490.01 Gain: $485.02"
- All actions/outputs below are when fields are inputted and user clicks sell
- Enter a symbol that portfolio does not contain (e.g. GOOG)
output:"The investment you want to sell doesn't exist in your portfolio, please enter an investment you own."
- Enter a quantity greater than the quantity of the investment owned (e.g. 600)
output: "Quantity entered is greater than the quantity you own."
- Enter price that is negative
output/action: "Price is less than or equal to zero."
- Enter quantity as negative
output/action: "Quantity is less than or equal to zero."
- Enter the the quantity of how much of the investment is owned, this means the investment will be removed from the list and hashmap
output instruction: go to search option and select the "search" button, the portfolio will no longer display
the stock that is completely sold.

Update:
- Empty portfolio
output: "No investments are in the portfolio to update."
- Prev button
- Click prev on an empty portfolio
action: button is disabled
- Click Prev when portfolio has investments
action: the fields with symbol, name, and price will change to the prior investment in the list
- Click Prev at the very first investment in the portfolio
action: button is disabled
- Next button
- Click next on an empty portfolio
action: button is disabled
- Click next on a portfolio that has investments
action: the fields wit symbol, name and price will change to the next investment in the list
- Click next on the last investment in the list
action: button is disabled
- Save button
- Enter a new price for the stock (e.g. $340.09) on screen, click "save"
output: "AAPL: apple inc Quantity: 30 shares Price: $340.09 Book Value: $6009.99"
- Enter price as a negative or zero, click "save
output: "Price is less than or equal to zero."
- Click "save" without updating the price input
output: program will display the stock with the same price

Total Gain:
- Try clicking the text areas
action: editing the areas are disabled
- When portfolio is empty
output: everything is empty, total gain will display "$0.0"

Search:
- Click reset
action: the textfields for user input is cleared
- All actions below are when fields are entered and user clicks "search"
- Click search with all fields empty
output: displays all the investments in portfolio
- Enter symbol and key words with various cases (e.g. ApPl, or Bank of america)
action: if cases are found, regardless of multiple words for key, or case for symbol, program will output
investment if found
- Enter input only for symbol and new line for the other prompts (e.g. APPL)
output: If investment matches symbol, the investment is outputted
- Enter input only for key words and new line for other prompts (e.g. bank america)
output: If investment matches key words, the investments are outputted
- Enter input only for range and new line for other prompts (e.g. 100)
output: If investment matches prices, the investments are outputted
- Enter input for range with different ranges (e.g. min = 100, min = 210.09 and max = 320, 
min = 80 and max = 80, max = 230)
output: If investment matches price range, the investments are outputted accordingly
- Enter a combination of search and new line for other prompts, varying from symbol, key words, and range
  (e.g. APPL and 100, or "bank montreal" and min = 210)
  output: If investments matches the filled in attributes, the investments are outputted
- Enter a symbol with two words
output: "Invalid symbol input."
- search for a keyword that has been deleted or removed from investment
output: message area is blank
- search for keyword that the stock just got added
output: message area will print the investment that had the keyword

Quit:
- Click "quit"
action: window is closed and program is terminated

Improvements

My program could be better if I was able to refine my search and make it more efficient. I feel the way I 
approached search was very divide and conquer. I could've cut my code my a lot of lines if I was more
efficient.
