import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {

    // The environment stores variable bindings and functions
    private static Map<String, Integer> variables = new HashMap<>();

    public static void main(String[] args) {
        // Initialize the environment with predefined variables and functions
        initializeVariables();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();

        // Check if input is enclosed in double quotes (for string handling)
        if (input.startsWith("\"") && input.endsWith("\"")) {
            System.out.println(input.substring(1, input.length() - 1));
            System.exit(0);
        }
        
        // Check if input is a known variable (e.g., x, v, i)
        else if (variables.containsKey(input)) {

            // Print the value of the variable and exit

            System.out.println(variables.get(input));

            System.exit(0);

        }
        // Check if the input is a valid function call
        else if (input.startsWith("(") && input.endsWith(")")) {
            // Clean up the input by adding spaces around parentheses and splitting
            String[] parts = input.replace("(", "( ").replace(")", " ) ").split("\\s+");

            // Call function handler to evaluate the expression
            int result = evaluateExpression(parts, new int[]{0}); 

            System.out.println(result);
        } else if (isValidInteger(input)) {
            // If input is a valid integer, print it
            System.out.println(input);
            System.exit(0);
        } else {
            System.err.println("Error: Invalid expression");
            System.exit(1);
        }
    }

    // Checks if a string is a valid integer
    private static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Initializes the environment with predefined variables
    private static void initializeVariables() {
        variables.put("x", 10);
        variables.put("v", 5);
        variables.put("i", 1);
    }

    // Recursive function to evaluate expressions like (add 5 x)
    private static int evaluateExpression(String[] tokens, int[] index) {
        if (tokens[index[0]].equals("(")) {
            index[0]++; // Skip the opening parenthesis
            String operation = tokens[index[0]]; // Get the operation (add, sub)
            index[0]++; // Move to the first argument

            // Evaluate the left and right arguments recursively
            int left = evaluateExpression(tokens, index);
            int right = evaluateExpression(tokens, index);

            // Skip the closing parenthesis
            index[0]++;

            // Perform the operation
            switch (operation) {
                case "add":
                    return left + right;
                case "sub":
                    return left - right;
                default:
                    throw new RuntimeException("Unknown operation: " + operation);
            }
        } else if (isValidInteger(tokens[index[0]])) {
            // If the token is an integer, parse and return it
            return Integer.parseInt(tokens[index[0]++]);
        } else if (variables.containsKey(tokens[index[0]])) {
            // If the token is a variable, return its value
            return variables.get(tokens[index[0]++]);
        } else {
            throw new RuntimeException("Unknown token: " + tokens[index[0]]);
        }
    }
}
