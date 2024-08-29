import java.util.Scanner;

public class Interpreter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine().trim();

        if (!isValidInteger(input)) {
            System.err.println("Error: Invalid input.");
            return;
        }
        try {
            long number = Long.parseLong(input);
            System.out.println(number);
        } catch (NumberFormatException e) {
            System.err.println("Error");
        }
    }

    private static boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

