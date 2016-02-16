import java.awt.GraphicsConfiguration;
import java.util.Stack;

/**
 * @file        CalcEngineStackApi.java
 * @author      Dean Gaffney 20067423
 * @assignment  Calculator engine using the java stack class.
 * @brief       Creating a calculator engine with the standard java stack class.
 *
 * @notes       
 * 				
 */

public class CalcEngineStackApi {

	Stack calcStack = new Stack();
	Stack <Integer> postStack = new Stack<Integer>();

	//give each operator a level of precedence.
	final int ADD_MINUS_PRECEDENCE = 1;
	final int MULTIPLY_DIVIDE_PRECEDENCE = 2;
	final int POWER_OF = 3;
	final int BRACKET_PRECEDENCE = 4;
	final int DECIMAL_POINT = 5;
	
	int operand;
	String displayValue;
	char operator;
    String expression; // create a string with the full expression
	String postfixExpression;

	public CalcEngineStackApi(){
		operator = ' ';
		displayValue = "";
		operand = 0;
		expression= "";
		postfixExpression ="";
	}
	
	//Method adds any command (i.e number or operator) to a string expression.
	public void addToExpression(char character){
		expression += character;
		System.out.println(expression);
	}

	public String getDisplayValue() {
		return displayValue;
	}
	
	public void setDisplayValue(String newChar){
		displayValue = newChar;
	}

	public int plus(int number1,int number2){
		int result = number1+number2;
		System.out.println("Result form plus method:" + result);
		return result;
	}
	
	public int minus(int number1,int number2){
		int result = number2 - number1;
		System.out.println("Result from minus method is : "+ result);
		return result;
	}
	
	public int multiply(int number1, int number2){
		int result = number1 * number2;
		System.out.println("Result from multiply method is: "+ result);
		return result;
	}
	
	public int divide(int number1,int number2){
		int result = number1 / number2;
		System.out.println("Result from divide method is: "+result);
		return result;
	}
	//return to this method
	public double powerTo(int number1,int number2){
		double result = (Math.pow(number2, number1));
		return result;
	}
	public void decimalPoint(){
		
	}
	public void equals(){
		// do infix to postfix transition and calculate
		convertToPostfix(expression);
		setDisplayValue(calculatePostFix(postfixExpression));
	}
	
	//this method calculates the result of a given postfix sum.
	private String calculatePostFix(String postExpression) {
		for(int i = 0; i < postExpression.length();i++){
			char currentChar = postExpression.charAt(i);
			switch(currentChar){
			case '+':
				postStack.push(plus((int)postStack.pop(),(int)postStack.pop()));
				break;
			case '-':
				postStack.push(minus((int)postStack.pop(),(int)postStack.pop()));
				break;
			case '*':
				postStack.push(multiply((int)postStack.pop(),(int)postStack.pop()));
				break;
			case '/':
				postStack.push(divide((int)postStack.pop(),(int)postStack.pop()));
				break;
			case '^':
				postStack.push((int)(powerTo((int)postStack.pop(), (int)postStack.pop())));
				break;
			default:
				postStack.push((int)(Character.getNumericValue(currentChar))); //this will mean its a number so push onto stack.
				System.out.println("Stack is now " + postStack);
			}
		}
		return Integer.toString(postStack.pop());
	}

	public void hasPrecedenceOver(char thisChar,int precedence1){
		while(!calcStack.isEmpty()){
			char topChar = (char) calcStack.pop();
			if(topChar == '('){
				calcStack.push(topChar);
				break;
			}else{
				int precedence2 = 0; //default value
				switch(topChar){
				case '+':
				case '-':
					precedence2 = ADD_MINUS_PRECEDENCE; //i.e. 1
					break;
				case '/':
				case '*':
					precedence2 = MULTIPLY_DIVIDE_PRECEDENCE; //i.e 2
					break;
				case '^':
					precedence2 = POWER_OF; //i.e 3
					break;
				}
				if(precedence1 > precedence2){
					calcStack.push(topChar);
					break;
				}else{
					postfixExpression += topChar;
				}
			}
		}
		calcStack.push(thisChar); // if there is nothing on the stack just push element on.
	}
	
	// this will pop everything off the stack until the parenthesis match each other.
	public void getEndBracket(char character){
		while(!calcStack.isEmpty()){
			char topChar = (char) calcStack.pop();
			if(topChar == '(') break;
			else
				postfixExpression += topChar;
		}
	}
	
	//this methods converts a given infix sum to postfix.
	public void convertToPostfix(String expression){
		for(int i = 0; i < expression.length();i++){
			char currentChar = expression.charAt(i); //get the current character
			
			switch(currentChar){ // switch on character for different operators
			case '(':
				calcStack.push(currentChar);  //push onto the stack
				break;
			case ')':
				getEndBracket(currentChar); // see if there is a matching bracket to end sum.
				break;
			case '+':
			case '-':
				hasPrecedenceOver(currentChar,1);
				break;
			case '/':
			case '*':
				hasPrecedenceOver(currentChar, MULTIPLY_DIVIDE_PRECEDENCE);
				break;
			case '^':
				hasPrecedenceOver(currentChar, POWER_OF);
				break;
			default:
				postfixExpression += currentChar; 	//this will be an operand.
			}
		}
		
		while(!calcStack.isEmpty()){
			postfixExpression += calcStack.pop();
			System.out.println(postfixExpression);
		}
		System.out.println(postfixExpression);
	}
	
	//method clears the calculator screen and resets any calculations.
    public void clear()
    {
        displayValue = "";
        expression = "";	
        postfixExpression = "";// reset expressions to nothing.
		operand = 0;
		calcStack.removeAllElements(); // reset stacks on clear
		postStack.removeAllElements(); 
    } 

	 public String getTitle()
    {
        return("My Calculator");
    }

	 public String getAuthor()
    {
        return("Dean Gaffney");
    }

    /**
     * Return the version number of this engine. This string is displayed as 
     * it is, so it should say something like "Version 1.1".
     */
    public String getVersion()
    {
        return("Ver. 1.0");
    }

	

}
